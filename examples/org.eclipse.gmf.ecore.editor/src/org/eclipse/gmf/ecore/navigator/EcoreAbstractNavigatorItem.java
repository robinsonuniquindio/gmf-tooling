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
package org.eclipse.gmf.ecore.navigator;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * @generated
 */
public abstract class EcoreAbstractNavigatorItem implements IAdaptable {

	/**
	 * @generated
	 */
	private Object myParent;

	/**
	 * @generated
	 */
	protected EcoreAbstractNavigatorItem(Object parent) {
		myParent = parent;
	}

	/**
	 * @generated
	 */
	abstract public String getModelID();

	/**
	 * @generated
	 */
	public Object getParent() {
		return myParent;
	}

	/**
	 * @generated
	 */
	public Object getAdapter(Class adapter) {
		if (ITabbedPropertySheetPageContributor.class.isAssignableFrom(adapter)) {
			return new ITabbedPropertySheetPageContributor() {

				public String getContributorId() {
					return "org.eclipse.gmf.ecore.editor";
				}
			};
		}
		return null;
	}

}
