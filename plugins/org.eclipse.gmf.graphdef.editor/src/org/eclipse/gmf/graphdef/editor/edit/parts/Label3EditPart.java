/*
 *  Copyright (c) 2006, 2008 Borland Software Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      Borland Software Corporation - initial API and implementation
 */
package org.eclipse.gmf.graphdef.editor.edit.parts;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.graphdef.editor.edit.policies.Label3ItemSemanticEditPolicy;
import org.eclipse.gmf.graphdef.editor.edit.polocies.FigureContainerXYLayoutEditPolicy;
import org.eclipse.gmf.graphdef.editor.part.GMFGraphVisualIDRegistry;
import org.eclipse.gmf.graphdef.editor.sheet.AttachAdapter;
import org.eclipse.gmf.graphdef.editor.sheet.ChangeTracker;
import org.eclipse.gmf.graphdef.editor.sheet.FeatureTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class Label3EditPart extends AbstractFigureEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3028;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public Label3EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new Label3ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		return new FigureContainerXYLayoutEditPolicy(getMapMode());
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		BorderedLabelFigure figure = new BorderedLabelFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public BorderedLabelFigure getPrimaryShape() {
		return (BorderedLabelFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof LabelText3EditPart) {
			((LabelText3EditPart) childEditPart).setLabel(getPrimaryShape().getFigureBorderedLabelFigure_TextLabel());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
		result.setMinimumSize(new Dimension(0, 0));
		return result;
	}

	/**
	 * @generated
	 */
	public EditPolicy getPrimaryDragEditPolicy() {
		EditPolicy result = super.getPrimaryDragEditPolicy();
		if (result instanceof ResizableEditPolicy) {
			ResizableEditPolicy ep = (ResizableEditPolicy) result;
			ep.setResizeDirections(PositionConstants.NONE);
		}
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(GMFGraphVisualIDRegistry.getType(LabelText3EditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	protected void handleNotificationEvent(Notification notification) {
		Object feature = notification.getFeature();
		if (NotationPackage.eINSTANCE.getFillStyle_FillColor().equals(feature)) {
			return;
		} else if (NotationPackage.eINSTANCE.getLineStyle_LineColor().equals(feature)) {
			return;
		}
		super.handleNotificationEvent(notification);
	}

	/**
	 * @generated
	 */
	public class BorderedLabelFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureBorderedLabelFigure_TextLabel;

		/**
		 * @generated
		 */
		public BorderedLabelFigure() {
			this.setLayoutManager(new StackLayout());
			this.setFill(false);
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureBorderedLabelFigure_TextLabel = new WrappingLabel();
			fFigureBorderedLabelFigure_TextLabel.setText("<<Label>>");

			this.add(fFigureBorderedLabelFigure_TextLabel);

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureBorderedLabelFigure_TextLabel() {
			return fFigureBorderedLabelFigure_TextLabel;
		}

	}

	/**
	 * @generated
	 */
	private Collection<Adapter> myDomainElementAdapters = new ArrayList<Adapter>();

	/**
	 * @generated
	 */
	private Label getGmfgraphElement() {
		View view = getNotationView();
		if (view == null) {
			return null;
		}
		EObject element = view.getElement();
		if (element instanceof Label) {
			Label modelFigureElement = (Label) element;
			return modelFigureElement;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected void removeSemanticListeners() {
		Label modelElement = getGmfgraphElement();
		if (modelElement != null) {
			modelElement.eAdapters().removeAll(myDomainElementAdapters);
			myDomainElementAdapters.clear();
		}
		super.removeSemanticListeners();
	}

	/**
	 * @generated
	 */
	protected void setFigure(IFigure figure) {
		super.setFigure(figure);
		Label modelElement = getGmfgraphElement();
		if (modelElement != null) {
			getPrimaryShape().setBackgroundColor(getColor(modelElement.getBackgroundColor()));
			getPrimaryShape().setForegroundColor(getColor(modelElement.getForegroundColor()));
			refreshFont();
		}
	}

	/**
	 * @generated
	 */
	public void activate() {
		if (isActive()) {
			return;
		}
		final Label modelElement = getGmfgraphElement();
		if (modelElement == null) {
			super.activate();
			return;
		}

		ChangeTracker backgroundColorTracker = new ChangeTracker() {

			public void modelChanged(Notification msg) {
				getPrimaryShape().setBackgroundColor(getColor(modelElement.getBackgroundColor()));
			}
		};
		myDomainElementAdapters.add(new AttachAdapter(GMFGraphPackage.eINSTANCE.getFigure_BackgroundColor(), backgroundColorTracker, new FeatureTracker(backgroundColorTracker,
				GMFGraphPackage.eINSTANCE.getConstantColor_Value()), new FeatureTracker(backgroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Red()), new FeatureTracker(
				backgroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Green()), new FeatureTracker(backgroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Blue())));

		ChangeTracker foregroundColorTracker = new ChangeTracker() {

			public void modelChanged(Notification msg) {
				getPrimaryShape().setForegroundColor(getColor(modelElement.getForegroundColor()));
			}
		};
		myDomainElementAdapters.add(new AttachAdapter(GMFGraphPackage.eINSTANCE.getFigure_ForegroundColor(), foregroundColorTracker, new FeatureTracker(foregroundColorTracker,
				GMFGraphPackage.eINSTANCE.getConstantColor_Value()), new FeatureTracker(foregroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Red()), new FeatureTracker(
				foregroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Green()), new FeatureTracker(foregroundColorTracker, GMFGraphPackage.eINSTANCE.getRGBColor_Blue())));

		ChangeTracker refreshFontTracker = new ChangeTracker() {

			public void modelChanged(Notification msg) {
				refreshFont();
			}
		};
		myDomainElementAdapters.add(new AttachAdapter(GMFGraphPackage.eINSTANCE.getFigure_Font(), refreshFontTracker, new FeatureTracker(refreshFontTracker, GMFGraphPackage.eINSTANCE
				.getBasicFont_FaceName()), new FeatureTracker(refreshFontTracker, GMFGraphPackage.eINSTANCE.getBasicFont_Height()), new FeatureTracker(refreshFontTracker, GMFGraphPackage.eINSTANCE
				.getBasicFont_Style())));
		modelElement.eAdapters().addAll(myDomainElementAdapters);
		super.activate();
	}

}