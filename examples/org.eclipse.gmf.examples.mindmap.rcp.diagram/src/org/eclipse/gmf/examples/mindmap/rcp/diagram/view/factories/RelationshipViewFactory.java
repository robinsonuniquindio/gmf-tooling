package org.eclipse.gmf.examples.mindmap.rcp.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;

import org.eclipse.gmf.examples.mindmap.rcp.diagram.edit.parts.MapEditPart;
import org.eclipse.gmf.examples.mindmap.rcp.diagram.edit.parts.RelationshipEditPart;
import org.eclipse.gmf.examples.mindmap.rcp.diagram.edit.parts.RelationshipLabelEditPart;

import org.eclipse.gmf.examples.mindmap.rcp.diagram.part.MindmapVisualIDRegistry;

import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;

import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class RelationshipViewFactory extends ConnectionViewFactory {

	/**
	 * @generated 
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		return styles;
	}

	/**
	 * @generated
	 */
	protected void decorateView(View containerView, View view,
			IAdaptable semanticAdapter, String semanticHint, int index,
			boolean persisted) {
		if (semanticHint == null) {
			semanticHint = MindmapVisualIDRegistry
					.getType(RelationshipEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint,
				index, persisted);
		IAdaptable eObjectAdapter = null;
		EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			eObjectAdapter = new EObjectAdapter(eObject);
		}
		getViewService().createNode(
				eObjectAdapter,
				view,
				MindmapVisualIDRegistry
						.getType(RelationshipLabelEditPart.VISUAL_ID),
				ViewUtil.APPEND, true, getPreferencesHint());
	}

}
