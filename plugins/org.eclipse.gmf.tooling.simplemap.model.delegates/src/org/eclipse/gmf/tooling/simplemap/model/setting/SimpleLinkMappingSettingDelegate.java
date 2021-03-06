/**
 * Copyright (c) 2010-2012 ISBAN S.L
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 * 		Ruben De Dios (ISBAN S.L)
 * 		Andrez Alvarez Mattos (ISBAN S.L)
 */
package org.eclipse.gmf.tooling.simplemap.model.setting;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicSettingDelegate.Stateless;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.tooldef.AbstractTool;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleLinkMapping;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimplemappingsPackage;

public class SimpleLinkMappingSettingDelegate extends Stateless {

	public SimpleLinkMappingSettingDelegate(EStructuralFeature arg0) {
		super(arg0);
	}

	@Override
	protected Object get(InternalEObject owner, boolean resolve, boolean coreType) {

		if (eStructuralFeature.getEContainingClass() == SimplemappingsPackage.Literals.SIMPLE_LINK_MAPPING) {
			switch (eStructuralFeature.getEContainingClass().getEAllStructuralFeatures().indexOf(eStructuralFeature)) {
			case SimplemappingsPackage.SIMPLE_LINK_MAPPING__DIAGRAM_LINK:
				return getDiagramLink((SimpleLinkMapping) owner);
			case SimplemappingsPackage.SIMPLE_LINK_MAPPING__DIAGRAM_LABEL:
				return getDiagramLabel((SimpleLinkMapping) owner);
			case SimplemappingsPackage.SIMPLE_LINK_MAPPING__TOOL:
				return getTool((SimpleLinkMapping) owner);
			}

		}

		return null;
	}

	private Connection getDiagramLink(SimpleLinkMapping owner) {

		if (owner.getLinkMapping() != null)
			return owner.getLinkMapping().getDiagramLink();

		return null;
	}

	/**
	 * Devuelve el label por defecto (El primero definido)
	 * @param owner
	 * @return
	 */
	private DiagramLabel getDiagramLabel(SimpleLinkMapping owner) {

		LinkMapping linkMapping = owner.getLinkMapping();

		if (linkMapping != null && !linkMapping.getLabelMappings().isEmpty())
			//Devolvemos el primer label definido
			return linkMapping.getLabelMappings().get(0).getDiagramLabel();

		return null;
	}

	private AbstractTool getTool(SimpleLinkMapping owner) {

		LinkMapping linkMapping = owner.getLinkMapping();

		if (linkMapping != null)
			return linkMapping.getTool();

		return null;
	}

	@Override
	protected boolean isSet(InternalEObject owner) {

		return false;
	}

}
