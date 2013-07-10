/**
 * Copyright (c) 2007, 2010, 2013 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactored javaInitilizers not to use methods from GMFGen model
 *                               [221347] Got rid of generated interfaces 
 *                               (IObjectInitializer, IFeatureInitializer) and implementation thereof
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 */
package xpt.providers

import com.google.inject.Inject
import java.util.List
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.gmf.codegen.gmfgen.GenElementInitializer
import org.eclipse.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.gmf.codegen.gmfgen.GenExpressionProviderBase
import org.eclipse.gmf.codegen.gmfgen.GenFeatureInitializer
import org.eclipse.gmf.codegen.gmfgen.GenFeatureSeqInitializer
import org.eclipse.gmf.codegen.gmfgen.GenFeatureValueSpec
import org.eclipse.gmf.codegen.gmfgen.GenJavaExpressionProvider
import org.eclipse.gmf.codegen.gmfgen.GenLanguage
import org.eclipse.gmf.codegen.gmfgen.GenLink
import org.eclipse.gmf.codegen.gmfgen.GenNode
import org.eclipse.gmf.codegen.gmfgen.GenReferenceNewElementSpec
import org.eclipse.gmf.codegen.gmfgen.ModelFacet
import org.eclipse.gmf.codegen.gmfgen.TypeModelFacet
import org.eclipse.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.Common
import xpt.Common_qvto
import xpt.expressions.getExpression

/**
 * XXX should generate this class only when there is initialization logic defined in the model
 */
class ElementInitializers {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension ElementInitializers_qvto;

	@Inject Activator xptActivator;
	@Inject MetaModel xptMetaModel
	@Inject getExpression xptGetExpression;

	@MetaDef def initMethodCall(GenCommonBase linkOrNode, TypeModelFacet modelFacet, String newElementVar) '''
		«IF modelFacet.modelElementInitializer != null»
			«elementInitializersInstanceCall(linkOrNode)».init_«linkOrNode.uniqueIdentifier»(«newElementVar»);
		«ENDIF»
	'''

	@MetaDef protected def elementInitializersInstanceCall(GenCommonBase base) // 
	'''«base.diagram.elementInitializersPackageName».«base.diagram.elementInitializersClassName».getInstance()'''

	def ElementInitializers(GenDiagram it) '''
		«copyright(editorGen)»
		package «getElementInitializersPackageName()»;
		
		«generatedClassComment»
		public class «getElementInitializersClassName()» {
		
			protected «getElementInitializersClassName()»() {
				// use #getInstance to access cached instance
			} 
		
			«Initializers(it)»
			«JavaSupport(it)»
			«additions(it)»
		
			«generatedMemberComment»
			public static «getElementInitializersClassName()» getInstance() {
				«getElementInitializersClassName()» cached = «xptActivator.instanceAccess(editorGen)».getElementInitializers();
				if (cached == null) {
					«xptActivator.instanceAccess(editorGen)».setElementInitializers(cached = new «getElementInitializersClassName()»());
				}
				return cached;
			}
		}
	'''

	def Initializers(GenDiagram it) '''
		«FOR next : getAllNodes()»
			«initMethod(next)»
		«ENDFOR»
		«FOR next : links»
			«initMethod(next)»
		«ENDFOR»
	'''

	def JavaSupport(GenDiagram it) '''
		«IF editorGen.expressionProviders != null &&
			editorGen.expressionProviders.providers.filter(typeof(GenJavaExpressionProvider)).notEmpty»
			«FOR next : getAllNodes()»
				«javaMethod(next)»
			«ENDFOR»
			«FOR next : links»
				«javaMethod(next)»
			«ENDFOR»
		«ENDIF»
	'''

	def additions(GenDiagram it) ''''''

	def dispatch CharSequence initMethod(GenNode it) '''«initMethod(it.modelFacet, it)»'''

	def dispatch CharSequence initMethod(GenLink it) '''«initMethod(it.modelFacet, it)»'''

	def dispatch CharSequence initMethod(ModelFacet it, GenCommonBase diagramElement) ''''''

	def dispatch CharSequence initMethod(TypeModelFacet it, GenCommonBase diagramElement) '''
		«IF it.modelElementInitializer != null»
			«initMethod(it.modelElementInitializer, diagramElement)»
		«ENDIF»
	'''

	def dispatch CharSequence initMethod(GenElementInitializer it, GenCommonBase diagramElement) '''«ERROR(
		'No idea how to init using ' + it)»'''

	def dispatch CharSequence initMethod(GenFeatureSeqInitializer it, GenCommonBase diagramElement) '''
		«generatedMemberComment»
		public void init_«diagramElement.getUniqueIdentifier()»(«xptMetaModel.QualifiedClassName(elementClass)» instance) {
			try {
				«FOR i : it.initializers»
					«performInit(i, diagramElement, 'instance', elementClass, <Integer>newLinkedList(initializers.indexOf(i)))»
				«ENDFOR»
			} catch(RuntimeException e) {
				«diagramElement.getDiagram().editorGen.plugin.activatorQualifiedClassName».getInstance().logError("Element initialization failed", e); //$NON-NLS-1$						
			}
		}
	'''

	def dispatch CharSequence performInit(GenFeatureInitializer it, GenCommonBase diagramElement, String instanceVar,
		GenClass instanceClass, List<Integer> counters) ''''''

	/**
	 * FIXME: need cleaner appoach to provider's language switch (not to mix if == literal and polymorphism)
	 */
	def dispatch CharSequence performInit(GenFeatureValueSpec it, GenCommonBase diagramElement, String instanceVar,
		GenClass instanceClass, List<Integer> counters) '''
		«IF it.value.provider.getLanguage() == GenLanguage::LITERAL_LITERAL»
			«xptMetaModel.modifyFeature(feature, instanceVar, instanceClass, value.body)»
			«extraLineBreak»
		«ELSE»
			«var expressionVarName = getVariableName('value', counters)»
			Object «expressionVarName» = «evaluateExpr(value.provider, diagramElement, it, instanceVar)»;
			«IF feature.listType»
				if («expressionVarName» instanceof java.util.Collection) {
					«xptMetaModel.getFeatureValue(feature, instanceVar, instanceClass, true)».clear();
					«IF feature.typeGenClassifier.expressionResultNeedsCast()»
						for (java.util.Iterator it = ((java.util.Collection) «expressionVarName»).iterator(); it.hasNext(); ) {
							Object next = «diagramElement.getDiagram().editorGen.expressionProviders.getAbstractExpressionQualifiedClassName()».performCast(it.next(), «xptMetaModel.
			MetaClass(feature.typeGenClassifier)»);
							«xptMetaModel.getFeatureValue(feature, instanceVar, instanceClass, true)».add((«xptMetaModel.
			QualifiedClassName(feature.typeGenClassifier/*XXX sorta hack, better would be MM::setFeatureValue that supports lists*/)») next);
						}
					«ELSE»
						«xptMetaModel.getFeatureValue(feature, instanceVar, instanceClass, true)».addAll(((java.util.Collection) «expressionVarName»));
					«ENDIF»
				} else {
					«IF feature.typeGenClassifier.expressionResultNeedsCast()»
						«expressionVarName» = «diagramElement.getDiagram().editorGen.expressionProviders.
			getAbstractExpressionQualifiedClassName()».performCast(«expressionVarName», «xptMetaModel.MetaClass(
			feature.typeGenClassifier)»);
					«ENDIF»
					«xptMetaModel.getFeatureValue(feature, instanceVar, instanceClass, true)».add((«xptMetaModel.
			QualifiedClassName(feature.typeGenClassifier/*XXX sorta hack, better would be MM::setFeatureValue that supports lists*/)») «expressionVarName»);
				}
			«ELSE»
				«IF feature.typeGenClassifier.expressionResultNeedsCast()»
					«extraLineBreak»
					«expressionVarName» = «diagramElement.getDiagram().editorGen.expressionProviders.
			getAbstractExpressionQualifiedClassName()».performCast(«expressionVarName», «xptMetaModel.MetaClass(
			feature.typeGenClassifier)»);
					«ENDIF»
					«xptMetaModel.setFeatureValue(feature, instanceVar, instanceClass, expressionVarName, true)»;
				«ENDIF/*isListType*/»
			«ENDIF/*is literal expression*/»
		'''

	def dispatch CharSequence performInit(GenReferenceNewElementSpec it, GenCommonBase diagramElement,
		String instanceVar, GenClass instanceClass, List<Integer> counters) '''
		«FOR newElemInit : it.newElementInitializers»
			«var initializerCounters = newListAppending(counters, it.newElementInitializers.indexOf(newElemInit))»
			«var newInstanceVar = getVariableName('newInstance', initializerCounters)»
			«xptMetaModel.NewInstance(newElemInit.elementClass, newInstanceVar)»
			«xptMetaModel.modifyFeature(feature, instanceVar, instanceClass, newInstanceVar)»
			«FOR i : newElemInit.initializers»
				«performInit(i, diagramElement, newInstanceVar, newElemInit.elementClass,
			newListAppending(initializerCounters, newElemInit.initializers.indexOf(i)))»
			«ENDFOR»
		«ENDFOR»
	'''

	/////////////////////////////////
	def dispatch evaluateExpr(GenExpressionProviderBase it, GenCommonBase diagramElement, GenFeatureValueSpec valueExpr,
		String instanceVar) ''''''

	def dispatch evaluateExpr(GenExpressionInterpreter it, GenCommonBase diagramElement, GenFeatureValueSpec valueExpr,
		String instanceVar) '''
		«xptGetExpression.getExpression(it, valueExpr.value, valueExpr.featureSeqInitializer.elementClass)».evaluate(«instanceVar»)
	'''

	/**
	 * XXX revisit: if emf java merge doesn't support genereated NOT methods with modified
	 * return type, there's no much sense to keep Object value = invokeJavaMethodWithSpecificReturnType,
	 * as client won't benefit from such code (he can't modify return type and thus would get duplicated methods on regeneration)
	 * However, if merge does ignore method return type when merging, allowing Object as return type may help.
	 */
	def dispatch evaluateExpr(GenJavaExpressionProvider it, GenCommonBase diagramElement, GenFeatureValueSpec valueExpr,
		String instanceVar) '''
		«javaMethodName(diagramElement, valueExpr)»(«instanceVar»)«»
	'''

	/////////////////////////////////
	def dispatch CharSequence javaMethod(GenNode it) '''«javaMethod(it.modelFacet, it)»'''

	def dispatch CharSequence javaMethod(GenLink it) '''«javaMethod(it.modelFacet, it)»'''

	def dispatch CharSequence javaMethod(ModelFacet it, GenCommonBase diagramElement) ''''''

	def dispatch CharSequence javaMethod(TypeModelFacet it, GenCommonBase diagramElement) '''
	«IF modelElementInitializer != null»
	«javaMethod(modelElementInitializer, diagramElement)»
	«ENDIF»
	'''

	def dispatch CharSequence javaMethod(GenElementInitializer it, GenCommonBase diagramElement) '''«ERROR(
		'No idea how to handle ' + it + " for " + diagramElement)»'''

	def dispatch CharSequence javaMethod(GenFeatureSeqInitializer it, GenCommonBase diagramElement) '''
	«FOR vs : recurseCollectValueSpec(it)»
	«javaMethod(vs.value.provider, diagramElement, vs)»
	«ENDFOR»
	'''

	def dispatch CharSequence javaMethod(GenExpressionProviderBase it, GenCommonBase diagramElement,
		GenFeatureValueSpec vs) ''''''

	def dispatch CharSequence javaMethod(GenJavaExpressionProvider it, GenCommonBase diagramElement,
		GenFeatureValueSpec vs) '''
		«generatedMemberComment»
		private «xptMetaModel.featureTargetType(vs.feature)» «javaMethodName(diagramElement, vs)»(«xptMetaModel.
			QualifiedClassName(vs.featureSeqInitializer.elementClass)» it) {
		«IF injectExpressionBody && (!vs.value.body.nullOrEmpty)»
			«vs.value.body»
		«ELSEIF throwException || (injectExpressionBody && vs.value.body.nullOrEmpty)»
			// TODO: implement this method to return value  
			// for «xptMetaModel.MetaFeature(vs.feature)»
			// Ensure that you remove @generated or mark it @generated NOT
			throw new java.lang.UnsupportedOperationException("No user java implementation provided in '«javaMethodName(diagramElement, vs)»' operation"); «nonNLS(1)»
		«ELSE»
			return null;
		«ENDIF»	
		}
	'''

}
