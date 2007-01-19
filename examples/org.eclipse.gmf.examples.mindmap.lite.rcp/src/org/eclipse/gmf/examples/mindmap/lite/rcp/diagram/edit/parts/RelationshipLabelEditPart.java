package org.eclipse.gmf.examples.mindmap.lite.rcp.diagram.edit.parts;

import java.text.MessageFormat;
import java.text.ParseException;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.emf.common.command.CompoundCommand;

import org.eclipse.emf.ecore.EAttribute;

import org.eclipse.emf.edit.command.SetCommand;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

import org.eclipse.emf.transaction.util.TransactionUtil;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;

import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.DirectEditPolicy;

import org.eclipse.gef.requests.DirectEditRequest;

import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

import org.eclipse.gmf.examples.mindmap.MindmapPackage;
import org.eclipse.gmf.examples.mindmap.Relationship;

import org.eclipse.gmf.runtime.lite.commands.WrappingCommand;

import org.eclipse.gmf.runtime.lite.edit.parts.update.IUpdatableEditPart;

import org.eclipse.gmf.runtime.lite.services.ParserUtil;

import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class RelationshipLabelEditPart extends AbstractGraphicalEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 6001;

	/**
	 * @generated
	 */
	private DirectEditManager manager;

	/**
	 * @generated
	 */
	private String defaultText;

	/**
	 * @generated
	 */
	public RelationshipLabelEditPart(View view) {
		assert view instanceof Node;
		setModel(view);
	}

	/**
	 * @generated
	 */
	private Node getDiagramNode() {
		return (Node) getModel();
	}

	/**
	 * @generated
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy() {
					public EditPart getHost() {
						return getUpdatableParent();
					}
				});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
			protected void showCurrentEditValue(DirectEditRequest request) {
				String value = (String) request.getCellEditor().getValue();
				getLabel().setText(value);
			}

			protected Command getDirectEditCommand(DirectEditRequest request) {
				String value = (String) request.getCellEditor().getValue();
				if (value == null) {
					//Invalid value is transformed into a null by the validator.
					//XXX: implement validator
					return UnexecutableCommand.INSTANCE;
				}
				final Object[] parseResult;
				try {
					parseResult = new MessageFormat(EDIT_PATTERN).parse(value);
				} catch (IllegalArgumentException e) {
					return UnexecutableCommand.INSTANCE;
				} catch (ParseException e) {
					return UnexecutableCommand.INSTANCE;
				}
				TransactionalEditingDomain editingDomain = TransactionUtil
						.getEditingDomain(getUpdatableParent().getDiagramEdge()
								.getDiagram().getElement());
				if (parseResult.length != 1) {
					return UnexecutableCommand.INSTANCE;
				}
				org.eclipse.emf.common.command.Command domainModelCommand = createDomainModelCommand(
						editingDomain, parseResult);
				return new WrappingCommand(editingDomain, domainModelCommand);
			}

			org.eclipse.emf.common.command.Command createDomainModelCommand(
					TransactionalEditingDomain editingDomain, Object[] values) {
				Relationship element = resolveSemanticElement();
				CompoundCommand result = new CompoundCommand();
				Object valueToSet;
				EAttribute feature = (EAttribute) MindmapPackage.eINSTANCE
						.getRelationship_Label();
				try {
					valueToSet = ParserUtil.parseValue(feature, values[0]);
				} catch (IllegalArgumentException e) {
					return org.eclipse.emf.common.command.UnexecutableCommand.INSTANCE;
				}
				result.append(SetCommand.create(editingDomain, element,
						feature, valueToSet));
				return result;
			}
		});

	}

	/**
	 * @generated
	 */
	public void performRequest(Request req) {
		if (RequestConstants.REQ_DIRECT_EDIT.equals(req.getType())) {
			performDirectEdit();
			return;
		}
		if (RequestConstants.REQ_OPEN.equals(req.getType())) {
			Command command = getCommand(req);
			if (command != null && command.canExecute()) {
				getViewer().getEditDomain().getCommandStack().execute(command);
			}
			return;
		}
		super.performRequest(req);
	}

	/**
	 * @generated
	 */
	protected DirectEditManager getManager() {
		if (manager == null) {
			manager = new DirectEditManager(this, TextCellEditor.class,
					new CellEditorLocator() {
						public void relocate(CellEditor celleditor) {
							Rectangle rect = getLabel().getTextBounds();
							getLabel().translateToAbsolute(rect);
							ZoomManager zoomManager = (ZoomManager) getViewer()
									.getProperty(ZoomManager.class.toString());
							double zoomLevel = zoomManager == null ? 1.0
									: zoomManager.getZoom();
							if (zoomLevel > 1.0 && getLabel().getFont() != null) {
								FontData[] datas = getLabel().getFont()
										.getFontData();
								for (int i = 0; i < datas.length; i++) {
									datas[i].height *= zoomLevel;
								}
								final Font font = new Font(getLabel().getFont()
										.getDevice(), datas);
								celleditor.getControl().setFont(font);
								celleditor.getControl().addDisposeListener(
										new DisposeListener() {
											public void widgetDisposed(
													DisposeEvent e) {
												font.dispose();
											}
										});
							}
							int avr = FigureUtilities.getFontMetrics(
									celleditor.getControl().getFont())
									.getAverageCharWidth();
							rect.setSize(new Dimension(celleditor.getControl()
									.computeSize(SWT.DEFAULT, SWT.DEFAULT))
									.expand(avr * 2, 0));
							celleditor.getControl().setBounds(rect.x, rect.y,
									rect.width, rect.height);
						}
					}) {
				protected void initCellEditor() {
					getCellEditor().setValue(getLabelEditText());
				}
			};
		}
		return manager;
	}

	/**
	 * @generated
	 */
	protected void performDirectEdit() {
		getManager().show();
	}

	/**
	 * @generated
	 */
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshLabel();
		refreshFont();
		refreshFontColor();
		refreshBounds();
	}

	/**
	 * @generated
	 */
	protected void refreshBounds() {
		Node node = getDiagramNode();
		assert node.getLayoutConstraint() instanceof Location;
		final Location location = (Location) node.getLayoutConstraint();
		RelationshipEditPart parent = getUpdatableParent();
		Connection connection = (Connection) parent.getFigure();
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), new ConnectionLocator(connection,
						ConnectionLocator.MIDDLE) {
					protected Point getReferencePoint() {
						return super.getReferencePoint().translate(
								location.getX(), location.getY());
					}
				});
	}

	/**
	 * @generated
	 */
	protected void refreshLabel() {
		getLabel().setText(getLabelText());
		getLabel().setIcon(getLabelIcon());
	}

	/**
	 * @generated
	 */
	public static final String VIEW_PATTERN = "{0}";

	/**
	 * @generated
	 */
	public static final String EDIT_PATTERN = "{0}";

	/**
	 * @generated
	 */
	protected String getLabelText() {
		Relationship element = resolveSemanticElement();
		if (element == null) {
			return defaultText;
		}
		String result = buildTextByPattern(element, VIEW_PATTERN);
		if (result == null) {
			return defaultText;
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected String getLabelEditText() {
		Relationship element = resolveSemanticElement();
		if (element == null) {
			return defaultText;
		}
		String result = buildTextByPattern(element, EDIT_PATTERN);
		if (result == null) {
			return defaultText;
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static String buildTextByPattern(Relationship element, String pattern) {
		return MessageFormat.format(pattern,
				new Object[] { element.getLabel() });
	}

	/**
	 * @generated
	 */
	protected void refreshFont() {
		FontStyle style = (FontStyle) getUpdatableParent().getDiagramEdge()
				.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		Font toDispose = createdFont;
		if (style != null) {
			String fontName = style.getFontName();
			int fontHeight = style.getFontHeight();
			int fontStyle = SWT.NORMAL;
			if (style.isBold()) {
				fontStyle |= SWT.BOLD;
			}
			if (style.isItalic()) {
				fontStyle |= SWT.ITALIC;
			}
			Font currentFont = getFigure().getFont();
			if (currentFont != null) {
				FontData currentFontData = currentFont.getFontData()[0];
				if (currentFontData.getName().equals(fontName)
						&& currentFontData.getHeight() == fontHeight
						&& currentFontData.getStyle() == fontStyle) {
					return;
				}
			}
			createdFont = new Font(null, fontName, fontHeight, fontStyle);
			getFigure().setFont(createdFont);
		} else {
			//revert to the default font
			getFigure().setFont(getViewer().getControl().getFont());
			createdFont = null;
		}
		if (toDispose != null) {
			toDispose.dispose();
		}
	}

	/**
	 * The font (created by {@link #refreshFont()}) currently assigned to the label (unless the default font is assigned).
	 * Whenever another non-default font is assigned to it, it is safe to dispose the previous one.
	 * @generated
	 */
	private Font createdFont;

	/**
	 * @generated
	 */
	protected void refreshFontColor() {
		FontStyle style = (FontStyle) getUpdatableParent().getDiagramEdge()
				.getStyle(NotationPackage.eINSTANCE.getFontStyle());
		Color toDispose = createdFontColor;
		if (style != null) {
			int fontColor = style.getFontColor();
			int red = fontColor & 0x000000FF;
			int green = (fontColor & 0x0000FF00) >> 8;
			int blue = (fontColor & 0x00FF0000) >> 16;
			Color currentColor = getLabel().getForegroundColor();
			if (currentColor != null && currentColor.getRed() == red
					&& currentColor.getGreen() == green
					&& currentColor.getBlue() == blue) {
				return;
			}
			createdFontColor = new Color(null, red, green, blue);
			getFigure().setForegroundColor(createdFontColor);
		} else {
			getFigure().setForegroundColor(
					getViewer().getControl().getForeground());
			createdFontColor = null;
		}
		if (toDispose != null) {
			toDispose.dispose();
		}
	}

	/**
	 * The color (created by {@link #refreshFontColor()}) currently assigned to the label.
	 * Whenever another color is assigned to it, it is safe to dispose the previous one.
	 * @generated
	 */
	private Color createdFontColor;

	/**
	 * @generated
	 */
	protected Image getLabelIcon() {
		return null;
	}

	/**
	 * @generated
	 */
	private Relationship resolveSemanticElement() {
		RelationshipEditPart parent = getUpdatableParent();
		if (parent == null || parent.getModel() instanceof Edge == false) {
			return null;
		}
		View view = (View) parent.getModel();
		return (Relationship) view.getElement();
	}

	/**
	 * @generated
	 */
	private RelationshipEditPart getUpdatableParent() {
		for (EditPart editPart = getParent(); editPart != null; editPart = editPart
				.getParent()) {
			if (editPart instanceof RelationshipEditPart) {
				return (RelationshipEditPart) editPart;
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public void activate() {
		super.activate();
		RelationshipEditPart updatableParent = getUpdatableParent();
		if (updatableParent != null) {
			updatableParent.addRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontColor(), fontColorRefresher);
			updatableParent.addRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontHeight(), fontRefresher);
			updatableParent.addRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontName(), fontRefresher);
			updatableParent.addRefresher(NotationPackage.eINSTANCE
					.getFontStyle_Bold(), fontRefresher);
			updatableParent.addRefresher(NotationPackage.eINSTANCE
					.getFontStyle_Italic(), fontRefresher);
			updatableParent.addRefresher(MindmapPackage.eINSTANCE
					.getRelationship_Label(), labelRefresher);
		}
	}

	/**
	 * @generated
	 */
	public void deactivate() {
		super.deactivate();
		RelationshipEditPart updatableParent = getUpdatableParent();
		if (updatableParent != null) {
			updatableParent.removeRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontColor(), fontColorRefresher);
			updatableParent.removeRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontHeight(), fontRefresher);
			updatableParent.removeRefresher(NotationPackage.eINSTANCE
					.getFontStyle_FontName(), fontRefresher);
			updatableParent.removeRefresher(NotationPackage.eINSTANCE
					.getFontStyle_Bold(), fontRefresher);
			updatableParent.removeRefresher(NotationPackage.eINSTANCE
					.getFontStyle_Italic(), fontRefresher);
			updatableParent.removeRefresher(MindmapPackage.eINSTANCE
					.getRelationship_Label(), labelRefresher);
		}
	}

	/**
	 * @generated
	 */
	private IUpdatableEditPart.Refresher labelRefresher = new IUpdatableEditPart.Refresher() {
		public void refresh() {
			refreshLabel();
		}
	};

	/**
	 * @generated
	 */
	private IUpdatableEditPart.Refresher fontColorRefresher = new IUpdatableEditPart.Refresher() {
		public void refresh() {
			refreshFontColor();
		}
	};

	/**
	 * @generated
	 */
	private IUpdatableEditPart.Refresher fontRefresher = new IUpdatableEditPart.Refresher() {
		public void refresh() {
			refreshFont();
		}
	};

	/**
	 * @generated
	 */
	protected Label createLabel() {
		return new Label();
	}

	/**
	 * @generated
	 */
	protected IFigure createFigure() {
		Label label = createLabel();
		defaultText = label.getText();
		return label;
	}

	/**
	 * @generated
	 */
	public Label getLabel() {
		return (Label) getFigure();
	}

	/**
	 * @generated
	 */
	public void setLabel(IFigure figure) {
		unregisterVisuals();
		setFigure(figure);
		defaultText = ""; //$NON-NLS-1$
		registerVisuals();
		refreshVisuals();
	}

}
