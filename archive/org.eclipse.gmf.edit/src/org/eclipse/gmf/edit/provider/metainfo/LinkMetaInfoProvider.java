/*
 * Copyright (c) 2005 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.edit.provider.metainfo;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.diagramrt.DiagramLink;

/**
 * Meta features of domain elements represented as diagram links
 * @author artem
 */
public interface LinkMetaInfoProvider {

	EStructuralFeature getTargetFeature(DiagramLink diagramLink);
	EReference getContainmentFeature(DiagramLink diagramLink);
}
