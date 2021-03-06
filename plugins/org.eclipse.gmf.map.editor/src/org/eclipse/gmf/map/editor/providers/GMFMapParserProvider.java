/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dmitry Stadnik (Borland) - initial API and implementation
 */
package org.eclipse.gmf.map.editor.providers;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.map.editor.edit.parts.CanvasMappingInfoEditPart;
import org.eclipse.gmf.map.editor.edit.parts.CompartmentMappingInfoEditPart;
import org.eclipse.gmf.map.editor.edit.parts.DesignLabelMapping2EditPart;
import org.eclipse.gmf.map.editor.edit.parts.DesignLabelMappingEditPart;
import org.eclipse.gmf.map.editor.edit.parts.FeatureLabelMapping2EditPart;
import org.eclipse.gmf.map.editor.edit.parts.FeatureLabelMappingEditPart;
import org.eclipse.gmf.map.editor.edit.parts.LabelMapping2EditPart;
import org.eclipse.gmf.map.editor.edit.parts.LabelMappingEditPart;
import org.eclipse.gmf.map.editor.edit.parts.LinkMappingInfoEditPart;
import org.eclipse.gmf.map.editor.edit.parts.NodeMappingInfoEditPart;
import org.eclipse.gmf.map.editor.edit.parts.ReferenceInfo2EditPart;
import org.eclipse.gmf.map.editor.edit.parts.ReferenceInfoEditPart;
import org.eclipse.gmf.map.editor.part.GMFMapVisualIDRegistry;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.DesignLabelMapping;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.mappings.LabelMapping;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.NodeReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class GMFMapParserProvider extends AbstractProvider implements IParserProvider {

	private IParser canvasMappingInfoParser;

	private IParser getCanvasMappingInfoParser() {
		if (canvasMappingInfoParser == null) {
			canvasMappingInfoParser = createCanvasMappingInfoParser();
		}
		return canvasMappingInfoParser;
	}

	protected IParser createCanvasMappingInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(EcorePackage.eINSTANCE.getENamedElement_Name()) {

			protected EObject getDomainElement(EObject element) {
				return element instanceof CanvasMapping ? ((CanvasMapping) element).getDomainMetaElement() : null;
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getCanvasMapping_DomainMetaElement();
			}
		};
		return parser;
	}

	private IParser nodeMappingInfoParser;

	private IParser getNodeMappingInfoParser() {
		if (nodeMappingInfoParser == null) {
			nodeMappingInfoParser = createNodeMappingInfoParser();
		}
		return nodeMappingInfoParser;
	}

	protected IParser createNodeMappingInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(EcorePackage.eINSTANCE.getENamedElement_Name()) {

			protected EObject getDomainElement(EObject element) {
				return element instanceof NodeMapping ? ((NodeMapping) element).getDomainMetaElement() : null;
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getMappingEntry_DomainMetaElement();
			}
		};
		return parser;
	}

	private IParser referenceInfoParser;

	private IParser getReferenceInfoParser() {
		if (referenceInfoParser == null) {
			referenceInfoParser = createReferenceInfoParser();
		}
		return referenceInfoParser;
	}

	protected IParser createReferenceInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(null) {

			public String getPrintString(IAdaptable adapter, int flags) {
				StringBuffer sb = new StringBuffer();
				EObject element = (EObject) adapter.getAdapter(EObject.class);
				if (element instanceof NodeReference) {
					NodeReference ref = (NodeReference) element;
					if (ref.getContainmentFeature() != null) {
						sb.append(ref.getContainmentFeature().getEContainingClass().getName());
						sb.append(':');
						sb.append(getStringByPattern(ref.getContainmentFeature(), EcorePackage.eINSTANCE.getENamedElement_Name(), DEFAULT_PROCESSOR));
					}
					if (ref.getChildrenFeature() != null) {
						sb.append(' ');
						sb.append('(');
						sb.append(ref.getChildrenFeature().getEContainingClass().getName());
						sb.append(':');
						sb.append(getStringByPattern(ref.getChildrenFeature(), EcorePackage.eINSTANCE.getENamedElement_Name(), DEFAULT_PROCESSOR));
						sb.append(')');
					}
				}
				return sb.toString();
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getNeedsContainment_ContainmentFeature() || eventFeature == GMFMapPackage.eINSTANCE.getNodeReference_ChildrenFeature();
			}

			public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
				return UnexecutableCommand.INSTANCE;
			}
		};
		return parser;
	}

	private IParser compartmentMappingInfoParser;

	private IParser getCompartmentMappingInfoParser() {
		if (compartmentMappingInfoParser == null) {
			compartmentMappingInfoParser = createCompartmentMappingInfoParser();
		}
		return compartmentMappingInfoParser;
	}

	protected IParser createCompartmentMappingInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(GMFGraphPackage.eINSTANCE.getIdentity_Name()) {

			protected EObject getDomainElement(EObject element) {
				return element instanceof CompartmentMapping ? ((CompartmentMapping) element).getCompartment() : null;
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getCompartmentMapping_Compartment();
			}
		};
		return parser;
	}

	private IParser linkMappingInfoParser;

	private IParser getLinkMappingInfoParser() {
		if (linkMappingInfoParser == null) {
			linkMappingInfoParser = createLinkMappingInfoParser();
		}
		return linkMappingInfoParser;
	}

	protected IParser createLinkMappingInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(EcorePackage.eINSTANCE.getENamedElement_Name()) {

			protected EObject getDomainElement(EObject element) {
				return element instanceof LinkMapping ? ((LinkMapping) element).getDomainMetaElement() : null;
			}

			public String getPrintString(IAdaptable adapter, int flags) {
				StringBuffer sb = new StringBuffer();
				EObject element = (EObject) adapter.getAdapter(EObject.class);
				if (element instanceof LinkMapping) {
					LinkMapping mapping = (LinkMapping) element;
					sb.append(super.getPrintString(adapter, flags));
					sb.append('(');
					if (mapping.getSourceMetaFeature() != null) {
						sb.append(mapping.getSourceMetaFeature().getEContainingClass().getName());
						sb.append(':');
						sb.append(getStringByPattern(mapping.getSourceMetaFeature(), EcorePackage.eINSTANCE.getENamedElement_Name(), DEFAULT_PROCESSOR));
					}
					sb.append('-');
					sb.append('>');
					if (mapping.getLinkMetaFeature() != null) {
						sb.append(mapping.getLinkMetaFeature().getEContainingClass().getName());
						sb.append(':');
						sb.append(getStringByPattern(mapping.getLinkMetaFeature(), EcorePackage.eINSTANCE.getENamedElement_Name(), DEFAULT_PROCESSOR));
					}
					sb.append(')');
				}
				return sb.toString();
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getMappingEntry_DomainMetaElement() || eventFeature == GMFMapPackage.eINSTANCE.getLinkMapping_SourceMetaFeature()
						|| eventFeature == GMFMapPackage.eINSTANCE.getLinkMapping_LinkMetaFeature();
			}

			public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
				return UnexecutableCommand.INSTANCE;
			}
		};
		return parser;
	}

	private IParser labelMappingInfoParser;

	private IParser getLabelMappingInfoParser() {
		if (labelMappingInfoParser == null) {
			labelMappingInfoParser = createLabelMappingInfoParser();
		}
		return labelMappingInfoParser;
	}

	protected IParser createLabelMappingInfoParser() {
		GMFMapStructuralFeatureParser parser = new GMFMapStructuralFeatureParser(null) {

			public String getPrintString(IAdaptable adapter, int flags) {
				StringBuffer sb = new StringBuffer();
				EObject element = (EObject) adapter.getAdapter(EObject.class);
				if (element instanceof FeatureLabelMapping) {
					FeatureLabelMapping mapping = (FeatureLabelMapping) element;
					for (Iterator it = mapping.getFeatures().iterator(); it.hasNext();) {
						EStructuralFeature feature = (EStructuralFeature) it.next();
						sb.append(feature.getEContainingClass().getName());
						sb.append(':');
						sb.append(getStringByPattern(feature, EcorePackage.eINSTANCE.getENamedElement_Name(), DEFAULT_PROCESSOR));
						if (it.hasNext()) {
							sb.append(',');
							sb.append(' ');
						}
					}
				} else if (element instanceof DesignLabelMapping) {
					sb.append("<design>");
				} else if (element instanceof LabelMapping) {
					sb.append("<custom>");
				}
				return sb.toString();
			}

			protected boolean isAffectingFeature(Object eventFeature) {
				return eventFeature == GMFMapPackage.eINSTANCE.getFeatureLabelMapping_Features();
			}

			public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
				return UnexecutableCommand.INSTANCE;
			}
		};
		return parser;
	}

	protected IParser getParser(int visualID) {
		switch (visualID) {
		case CanvasMappingInfoEditPart.VISUAL_ID:
			return getCanvasMappingInfoParser();
		case NodeMappingInfoEditPart.VISUAL_ID:
			return getNodeMappingInfoParser();
		case ReferenceInfoEditPart.VISUAL_ID:
		case ReferenceInfo2EditPart.VISUAL_ID:
			return getReferenceInfoParser();
		case CompartmentMappingInfoEditPart.VISUAL_ID:
			return getCompartmentMappingInfoParser();
		case LinkMappingInfoEditPart.VISUAL_ID:
			return getLinkMappingInfoParser();
		case FeatureLabelMappingEditPart.VISUAL_ID:
		case FeatureLabelMapping2EditPart.VISUAL_ID:
		case DesignLabelMappingEditPart.VISUAL_ID:
		case DesignLabelMapping2EditPart.VISUAL_ID:
		case LabelMappingEditPart.VISUAL_ID:
		case LabelMapping2EditPart.VISUAL_ID:
			return getLabelMappingInfoParser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(GMFMapVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(GMFMapVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (GMFMapElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}
}
