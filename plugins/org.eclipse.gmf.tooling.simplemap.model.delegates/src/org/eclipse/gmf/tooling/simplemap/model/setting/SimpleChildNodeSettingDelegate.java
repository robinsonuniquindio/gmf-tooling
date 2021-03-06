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
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleChildNode;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleCompartment;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleMapping;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleNode;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimpleParentNode;
import org.eclipse.gmf.tooling.simplemap.simplemappings.SimplemappingsPackage;

public class SimpleChildNodeSettingDelegate extends Stateless {

	public SimpleChildNodeSettingDelegate(EStructuralFeature arg0) {
		super(arg0);
	}

	@Override
	protected Object get(InternalEObject owner, boolean resolve, boolean coreType) {

		if (eStructuralFeature.getEContainingClass() == SimplemappingsPackage.Literals.SIMPLE_CHILD_NODE) {
			switch (eStructuralFeature.getEContainingClass().getEAllStructuralFeatures().indexOf(eStructuralFeature)) {
			case SimplemappingsPackage.SIMPLE_CHILD_NODE__PARENT_MAPPING:
				return getParentMapping((SimpleChildNode) owner);
			case SimplemappingsPackage.SIMPLE_CHILD_NODE__PARENT:
				return getParent((SimpleChildNode) owner);
			case SimplemappingsPackage.SIMPLE_CHILD_NODE__PARENT_META_ELEMENT:
				return getParentMetaElement((SimpleChildNode) owner);
			}

		}

		return null;
	}

	private SimpleNode getParent(SimpleChildNode owner) {

		SimpleParentNode parent = owner.getParentNode();

		if (parent instanceof SimpleCompartment)
			return getParent((SimpleCompartment) parent);

		if (parent instanceof SimpleNode)
			return (SimpleNode) parent;

		return null;
	}

	private SimpleMapping getParentMapping(SimpleChildNode owner) {

		SimpleParentNode parent = owner.getParentNode();

		while (parent instanceof SimpleChildNode)
			parent = ((SimpleChildNode) parent).getParentNode();

		if (parent instanceof SimpleMapping)
			return (SimpleMapping) parent;

		return null;
	}

	private Object getParentMetaElement(SimpleChildNode owner) {

		if (owner.getParent() instanceof SimpleNode) {
			SimpleNode parent = (SimpleNode) owner.getParent();
			return parent.getDomainMetaElement();
		}

		if (owner.getParent() instanceof SimpleCompartment) {
			SimpleNode parent = (SimpleNode) ((SimpleCompartment) owner.getParent()).getParent();
			return parent.getDomainMetaElement();
		}

		if (owner.getParentNode() instanceof SimpleMapping) {
			SimpleMapping parent = (SimpleMapping) owner.getParentNode();
			return parent.getMapping().getDiagram().getDomainMetaElement();
		}

		return null;
	}

	@Override
	protected boolean isSet(InternalEObject owner) {

		return false;
	}

}
