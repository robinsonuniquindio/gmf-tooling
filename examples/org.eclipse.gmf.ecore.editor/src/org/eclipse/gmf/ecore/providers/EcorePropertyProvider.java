/*
 * Copyright (c) 2006 Borland Software Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 */
package org.eclipse.gmf.ecore.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.gmf.runtime.emf.ui.properties.providers.GenericEMFPropertiesProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.ecore.edit.parts.EPackageEditPart;

import org.eclipse.gmf.ecore.part.EcoreVisualIDRegistry;

/**
 * @generated
 */
public class EcorePropertyProvider extends GenericEMFPropertiesProvider implements IPropertiesProvider {

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetPropertySourceOperation) {
			Object object = ((GetPropertySourceOperation) operation).getObject();
			return getSemanticElement(object) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public ICompositePropertySource getPropertySource(Object object) {
		EObject element = getSemanticElement(object);
		if (element != null) {
			return super.getPropertySource(element);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement(Object object) {
		View view = null;
		if (object instanceof View) {
			view = (View) object;
		} else if (object instanceof EditPart) {
			EditPart editPart = (EditPart) object;
			if (editPart.getModel() instanceof View) {
				view = (View) editPart.getModel();
			}
		}
		if (view != null && EPackageEditPart.MODEL_ID.equals(EcoreVisualIDRegistry.getModelID(view))) {
			return view.getElement();
		}
		return null;
	}

}
