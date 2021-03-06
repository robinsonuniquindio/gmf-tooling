/**
 * Copyright (c) 2007, 2009, 2012 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - API extracted to gmf.tooling.runtime (#372479)  	  
 *                               - #386838 - migrate to Xtend2
 */
package xpt.diagram.updater

import com.google.inject.Inject
import org.eclipse.gmf.codegen.gmfgen.GenDiagramUpdater
import xpt.Common

@com.google.inject.Singleton class LinkDescriptor {
	@Inject extension Common;

	def className(GenDiagramUpdater it) '''«linkDescriptorClassName»'''
	
	def packageName(GenDiagramUpdater it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagramUpdater it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagramUpdater it) '''«qualifiedClassName(it)»'''

	def LinkDescriptor(GenDiagramUpdater it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {
			«refOnlyLinkConstructor(it)»
			
			«typeLinkConstructor(it)»
			
			«additions(it)»
		}
	'''

	def extendsList(GenDiagramUpdater it) '''extends org.eclipse.gmf.tooling.runtime.update.UpdaterLinkDescriptor'''

	def refOnlyLinkConstructor(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.emf.ecore.EObject source, org.eclipse.emf.ecore.EObject destination, org.eclipse.gmf.runtime.emf.type.core.IElementType elementType, int linkVID) {
			super(source, destination, elementType, linkVID);
		}
	'''

	def typeLinkConstructor(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.emf.ecore.EObject source, org.eclipse.emf.ecore.EObject destination, org.eclipse.emf.ecore.EObject linkElement, org.eclipse.gmf.runtime.emf.type.core.IElementType elementType, int linkVID) {
			super(source, destination, linkElement, elementType, linkVID);
		}
	'''

	def additions(GenDiagramUpdater it) ''''''
}
