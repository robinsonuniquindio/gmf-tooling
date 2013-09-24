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
 * 	  Michael Golubev (Montages) - API extracted to GMF-T runtime, migrated to Xtend2 
 */
package xpt.expressions

import com.google.inject.Inject
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import xpt.Common
import xpt.QualifiedClassNameProvider

class AbstractExpression {
	@Inject extension Common;
	@Inject extension QualifiedClassNameProvider;

	def extendsList(GenDiagram it) ''''''

	def className(GenDiagram it) '''«it.editorGen.expressionProviders.abstractExpressionClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.expressionProviders.expressionsPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def AbstractExpression(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public abstract class «className(it)» «extendsList(it)»{
		
		«status(it)»
		
		«body(it)»
		
		«context(it)»
		
		«constructor(it)»
		
		«evaluate(it)»
		
		«performCast(it)»
		
		«additions(it)»
		}
	'''

	def additions(GenDiagram it) ''''''

	def status(GenDiagram it) '''
			«generatedMemberComment»
			private org.eclipse.core.runtime.IStatus status = org.eclipse.core.runtime.Status.OK_STATUS;	
		
			«generatedMemberComment»
			protected void setStatus(int severity, String message, Throwable throwable) {		
				String pluginID = «getActivatorQualifiedClassName(editorGen.plugin)».ID;
				this.status = new org.eclipse.core.runtime.Status(severity, pluginID, -1, (message != null) ? message : "", throwable); «nonNLS(
			1)»
				if(!this.status.isOK()) {
					«getActivatorQualifiedClassName(editorGen.plugin)».getInstance().logError("Expression problem:" + message + "body:"+ body(), throwable); «nonNLS(
			1)» «nonNLS(2)»
				}
			}
		
			«generatedMemberComment»
			public org.eclipse.core.runtime.IStatus getStatus() {
				return status;
			}
	'''

	def body(GenDiagram it) '''
			«generatedMemberComment»
			private final String myBody;
		
			«generatedMemberComment»
			public String body() {
				return myBody;
			}
	'''

	def context(GenDiagram it) '''
			«generatedMemberComment»
			private final org.eclipse.emf.ecore.EClassifier myContext;
		
			«generatedMemberComment»
			public org.eclipse.emf.ecore.EClassifier context() {
				return myContext;
			}
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		protected «className(it)»(String body, org.eclipse.emf.ecore.EClassifier context) {
			myBody = body;
			myContext = context;
		}
	'''

	def evaluate(GenDiagram it) '''
			«generatedMemberComment»
			@SuppressWarnings("rawtypes")
			protected abstract Object doEvaluate(Object context, java.util.Map env);
		
			«generatedMemberComment»
			public Object evaluate(Object context) {
				return evaluate(context, java.util.Collections.EMPTY_MAP);
			}
		
			«generatedMemberComment»
			@SuppressWarnings("rawtypes")
			public Object evaluate(Object context, java.util.Map env) {
				if(context().isInstance(context)) {
					try {
						return doEvaluate(context, env);
					} catch(Exception e) {
						«getActivatorQualifiedClassName(editorGen.plugin)».getInstance().logError("Expression evaluation failure: " + body(), e); «nonNLS(
			1)»
					}
				}
				return null;
			}
	'''

	def performCast(GenDiagram it) '''
		«generatedMemberComment(it,
			'Expression may return number value which is not directly compatible with feature type (e.g. Double when Integer is expected), or EEnumLiteral meta-object when literal instance is expected')»
		public static Object performCast(Object value, org.eclipse.emf.ecore.EDataType targetType) {
			if (targetType instanceof org.eclipse.emf.ecore.EEnum) {
				if (value instanceof org.eclipse.emf.ecore.EEnumLiteral) {
					org.eclipse.emf.ecore.EEnumLiteral literal = (org.eclipse.emf.ecore.EEnumLiteral) value;
					return (literal.getInstance() != null) ? literal.getInstance() : literal;
				}
			}
			if (false == value instanceof Number || targetType == null || targetType.getInstanceClass() == null) {
				return value;
			}
			Class<?> targetClass = targetType.getInstanceClass();
			Number num = (Number) value;
			Class<?> valClass = value.getClass();
			Class<?> targetWrapperClass = targetClass;
			if (targetClass.isPrimitive()) {
				targetWrapperClass = org.eclipse.emf.ecore.util.EcoreUtil.wrapperClassFor(targetClass);
			}
			if (valClass.equals(targetWrapperClass)) {
				return value;
			}
			if (Number.class.isAssignableFrom(targetWrapperClass)) {
				if (targetWrapperClass.equals(Byte.class)) return new Byte(num.byteValue());
				if (targetWrapperClass.equals(Integer.class)) return new Integer(num.intValue());
				if (targetWrapperClass.equals(Short.class)) return new Short(num.shortValue());
				if (targetWrapperClass.equals(Long.class)) return new Long(num.longValue());
				if (targetWrapperClass.equals(java.math.BigInteger.class)) return java.math.BigInteger.valueOf(num.longValue());
				if (targetWrapperClass.equals(Float.class)) return new Float(num.floatValue());
				if (targetWrapperClass.equals(Double.class)) return new Double(num.doubleValue());
				if (targetWrapperClass.equals(java.math.BigDecimal.class)) return new java.math.BigDecimal(num.doubleValue());
			}
			return value;
		}
	'''

}