package xpt.plugin

import com.google.inject.Inject
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.gmf.codegen.gmfgen.GenPlugin
import plugin.Activator
import xpt.Common
import xpt.editor.DocumentProvider
import xpt.expressions.OCLExpressionFactory
import xpt.expressions.getExpression
import xpt.providers.ElementInitializers

class ActivatorImpl {
	@Inject extension Common;
	
	@Inject Activator xptActivator;
	@Inject DocumentProvider xptDocProvider;
	@Inject ElementInitializers xptElementInitializers;
	@Inject getExpression xptExpr;
	@Inject OCLExpressionFactory oclFactory;
	
	def ActivatorImpl(GenPlugin it)'''
		«copyright(editorGen)»
		package «xptActivator.packageName(it)»;

		«generatedClassComment»
		public class «xptActivator.className(it)» extends org.eclipse.ui.plugin.AbstractUIPlugin {

		«attrs»
		«constructor»
		«start»
		«stop(editorGen)»
		«getInstance»
		«createAdapterFactory(editorGen.diagram)»
		«fillItemProviderFactories(editorGen)»
		«getItemProvidersAdaptorFactory»
		«getItemImageDescriptor»
		«getBundleDescriptorImage»
		«findImageDescriptor»
		«getBundleImage»
		«getString»
		«documentProviderGetter(editorGen.diagram)»
		«linkConstraint(editorGen.diagram)»
		«initializerGetter(editorGen.diagram)»
		«initializerSetter(editorGen.diagram)»
		«providersAccessMethods»
		«logError»
		«logInfo»
		«debug»
		«additions»
	}
'''

def attrs(GenPlugin it)'''
	«generatedMemberComment»
	public static final String ID = "«ID»"; //$NONNLS1$

	«generatedMemberComment»
	public static final org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint DIAGRAM_PREFERENCES_HINT =
			new org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint(ID);

	«generatedMemberComment»
	private static «xptActivator.className(it)» instance;

	«generatedMemberComment»
	private org.eclipse.emf.edit.provider.ComposedAdapterFactory adapterFactory;	

	«generatedMemberComment»
	private «xptDocProvider.qualifiedClassName(editorGen.diagram)» documentProvider;

	«IF editorGen.diagram.links.exists(l| !l.sansDomain)»
		«generatedMemberComment»
		private «editorGen.diagram.getLinkCreationConstraintsQualifiedClassName()» linkConstraints;
	«ENDIF»

	«generatedMemberComment»
	private «xptElementInitializers.qualifiedClassName(editorGen.diagram)» initializers;

	«IF it.editorGen.expressionProviders != null»
		«FOR p : it.editorGen.expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
			«generatedMemberComment»
			private «xptExpr.getExpressionInterpriterQualifiedClassName(p)» «p.language»Factory;
		«ENDFOR»
	«ENDIF»
'''

def constructor(GenPlugin it)'''
	«generatedMemberComment»
	public «xptActivator.className(it)»() {
	}
'''

def start(GenPlugin it)'''
	«generatedMemberComment»
	public void start(org.osgi.framework.BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint.registerPreferenceStore(DIAGRAM_PREFERENCES_HINT, getPreferenceStore());
		adapterFactory = createAdapterFactory();
	}
'''

def stop(GenEditorGenerator it)'''
	«generatedMemberComment»
	public void stop(org.osgi.framework.BundleContext context) throws Exception {
		adapterFactory.dispose();
		adapterFactory = null;
		«IF diagram.links.exists(l| !l.sansDomain)»
			linkConstraints = null;
		«ENDIF»
		initializers = null;
		«IF expressionProviders != null»
			«FOR p : expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
				«p.language»Factory = null;
			«ENDFOR»
		«ENDIF»
		instance = null;
		super.stop(context);
	}
'''

def getInstance(GenPlugin it)'''
	«generatedMemberComment»
	public static «xptActivator.className(it)» getInstance() {
		return instance;
	}
'''

def createAdapterFactory(GenDiagram it)'''
	«generatedMemberComment»
	protected org.eclipse.emf.edit.provider.ComposedAdapterFactory createAdapterFactory() {
		java.util.ArrayList<org.eclipse.emf.common.notify.AdapterFactory> factories = new java.util.ArrayList<org.eclipse.emf.common.notify.AdapterFactory>();
		fillItemProviderFactories(factories);
		return new org.eclipse.emf.edit.provider.ComposedAdapterFactory(factories);
	}
'''

def fillItemProviderFactories(GenEditorGenerator it)'''
	«generatedMemberComment»
	protected void fillItemProviderFactories(java.util.List<org.eclipse.emf.common.notify.AdapterFactory> factories) {
		«populateItemProviderFactories('factories', it)»
		factories.add(new org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory());
		factories.add(new org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory());
	}
'''

	def populateItemProviderFactories(String factoryListVar, GenEditorGenerator it)'''
		«FOR genPackage : it.getAllDomainGenPackages(true)»
			«factoryListVar».add(new «genPackage.qualifiedItemProviderAdapterFactoryClassName»());
		«ENDFOR»
	'''

def getItemProvidersAdaptorFactory(GenPlugin it)'''
	«generatedMemberComment»
	public org.eclipse.emf.common.notify.AdapterFactory getItemProvidersAdapterFactory() {
		return adapterFactory;
	}
'''

def getItemImageDescriptor(GenPlugin it)'''
	«generatedMemberComment»
	public org.eclipse.jface.resource.ImageDescriptor getItemImageDescriptor(Object item) {
		org.eclipse.emf.edit.provider.IItemLabelProvider labelProvider =
				(org.eclipse.emf.edit.provider.IItemLabelProvider) adapterFactory.adapt(
						item, org.eclipse.emf.edit.provider.IItemLabelProvider.class);
		if (labelProvider != null) {
			return org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry.getInstance().getImageDescriptor(
					labelProvider.getImage(item));
		}
		return null;
	}
'''

def getBundleDescriptorImage(GenPlugin it)'''
	«generatedMemberComment(
	  'Returns an image descriptor(the image file at the given\n'
	+ 'plugin relative path.\n',
	  'param path the path\n'
	+ '@return the image descriptor')»
	public static org.eclipse.jface.resource.ImageDescriptor getBundledImageDescriptor(String path) {
		return org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
	}
'''

def findImageDescriptor(GenPlugin it)'''
	«generatedMemberComment(
	  'Respects images residing in any plugin. If path is relative,\n'
	+ 'then this bundle is looked up(the image, otherwise,(absolute\n'
	+ 'path, first segment is taken as id of plugin with image\n',
	  '@param path the path to image, either absolute (with plugin id as first segment), or relative(bundled images\n'
	+ '@return the image descriptor')»
	public static org.eclipse.jface.resource.ImageDescriptor findImageDescriptor(String path) {
		final org.eclipse.core.runtime.IPath p = new org.eclipse.core.runtime.Path(path);
		if (p.isAbsolute() && p.segmentCount() > 1) {
			return org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin(
					p.segment(0), p.removeFirstSegments(1).makeAbsolute().toString());
		} else {
			return getBundledImageDescriptor(p.makeAbsolute().toString());
		} 
	}
	
'''

def getBundleImage(GenPlugin it)'''
	«generatedMemberComment('Returns an image(the image file at the given plugin relative path.\n'
	+ 'Client do not need to dispose this image. Images will be disposed automatically.\n',
	  'param path the path\n'
	+ '@return image instance')»
	public org.eclipse.swt.graphics.Image getBundledImage(String path) {
		org.eclipse.swt.graphics.Image image = getImageRegistry().get(path);
		if (image == null) {
			getImageRegistry().put(path, getBundledImageDescriptor(path));
			image = getImageRegistry().get(path);
		}
		return image;
	}
'''

def getString(GenPlugin it)'''
	«generatedMemberComment('Returns string from plugins resource bundle')»
	public static String getString(String key) {
		return org.eclipse.core.runtime.Platform.getResourceString(
				getInstance().getBundle(), "%" + key); «nonNLS»
	}
'''

def documentProviderGetter(GenDiagram it)'''
	«generatedMemberComment»
	public «xptDocProvider.qualifiedClassName(it)» getDocumentProvider() {
		if (documentProvider == null) {
			documentProvider = new «xptDocProvider.qualifiedClassName(it)»();
		}
		return documentProvider;
	}
'''

def linkConstraint(GenDiagram it)'''
	«IF links.exists(l| !l.sansDomain) »
		«linkConstraintsGetter(it)»
		
		«linkConstraintsSetter(it)»
	«ENDIF»
'''

def linkConstraintsGetter(GenDiagram it)'''
	«generatedMemberComment»
	public «getLinkCreationConstraintsQualifiedClassName()» getLinkConstraints() {
		return linkConstraints;
	}
'''

def linkConstraintsSetter(GenDiagram it)'''
	«generatedMemberComment»
	public void setLinkConstraints(«getLinkCreationConstraintsQualifiedClassName()» lc) {
		this.linkConstraints = lc;
	}
'''

def initializerGetter(GenDiagram it)'''
	«generatedMemberComment»
	public «xptElementInitializers.qualifiedClassName(it)» getElementInitializers() {
		return initializers;
	}
'''

def initializerSetter(GenDiagram it)'''
	«generatedMemberComment»
	public void setElementInitializers(«xptElementInitializers.qualifiedClassName(it)» i) {
		this.initializers = i;
	}
'''

def providersAccessMethods(GenPlugin it)'''
	«IF it.editorGen.expressionProviders != null»
		«FOR p : it.editorGen.expressionProviders.providers.filter(typeof(GenExpressionInterpreter))»
			«providerGetter(p)»
	
			«providerSetter(p)»
		«ENDFOR»
	«ENDIF»
'''

def providerGetter(GenExpressionInterpreter it)'''
	«generatedMemberComment»
	public «oclFactory.qualifiedClassName(it)» get«oclFactory.className(it)»() {
		return «language»Factory;
	}
'''

def providerSetter(GenExpressionInterpreter it)'''
	«generatedMemberComment»
	public void set«oclFactory.className(it)»(«oclFactory.qualifiedClassName(it)» f) {
		this.«language»Factory = f;
	}
'''

def logError(GenPlugin it)'''
	«generatedMemberComment»
	public void logError(String error) {
		logError(error, null);
	}
	
	«generatedMemberComment»
	public void logError(String error, Throwable throwable) {
		if (error == null && throwable != null) {
			error = throwable.getMessage();
		}
		getLog().log(new org.eclipse.core.runtime.Status(
				org.eclipse.core.runtime.IStatus.ERROR,
				«xptActivator.className(it)».ID,
				org.eclipse.core.runtime.IStatus.OK,
				error, throwable));
		debug(error, throwable);
	}
'''

def logInfo(GenPlugin it)'''
	«generatedMemberComment»
	public void logInfo(String message) {
		logInfo(message, null);
	}

	«generatedMemberComment»
	public void logInfo(String message, Throwable throwable) {
		if (message == null && throwable != null) {
			message = throwable.getMessage();
		}
		getLog().log(new org.eclipse.core.runtime.Status(
				org.eclipse.core.runtime.IStatus.INFO,
				«xptActivator.className(it)».ID,
				org.eclipse.core.runtime.IStatus.OK,
				message, throwable));
		debug(message, throwable);
	}
'''

def debug(GenPlugin it)'''
	«generatedMemberComment»
	private void debug(String message, Throwable throwable) {
		if (!isDebugging()) {
			return;
		}
		if (message != null) {
			System.err.println(message);
		}
		if (throwable != null) {
			throwable.printStackTrace();
		}
	}
'''

// Perhaps, xpt:editor::Editor or some xpt::CommonCode would be better place for
// this accessor.
// XXX besides, consider using preference store directly, without a hint (see comment in Editor.xpt#getPreferencesHint)
def preferenceHintAccess(GenEditorGenerator it)'''«xptActivator.qualifiedClassName(plugin)».DIAGRAM_PREFERENCES_HINT'''

def additions(GenPlugin it)''''''
}