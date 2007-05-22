/*
 * Copyright (c) 2007 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Borland - initial API and implementation
 */
package org.eclipse.gmf.internal.common.migrate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;

public class MigrationHelper extends XMIHelperImpl {
	private final MigrationHelperDelegate myDelegate;
	private boolean myIsDelegateDisabled = true;
	private Map<EStructuralFeature, EStructuralFeature> myNarrowedFeatureTypes;

	public MigrationHelper(XMLResource resource, MigrationHelperDelegate delegate) {
		super(resource);
		assert delegate != null;
		myDelegate = delegate;
	}

	void enableDelegate(boolean enabled) {
		myIsDelegateDisabled = !enabled;
	}

	boolean isEnabled() {
		return !myIsDelegateDisabled;
	}
	
	@Override
	public EObject createObject(EFactory factory, EClassifier type) {
		if (myIsDelegateDisabled) {
			return super.createObject(factory, type);
		}
		EObject result = myDelegate.createObject(factory, type);
		if (result == null) {
			result = super.createObject(factory, type);
		}
		myDelegate.processObject(result);
		return result;
	}

	@Override
	public void setValue(EObject object, EStructuralFeature feature, Object value, int position) {
		if (myIsDelegateDisabled) {
			super.setValue(object, feature, value, position);
			return; 
		}
		EStructuralFeature originalFeature = getOriginalFeature(feature);
		if (originalFeature != null) {
			feature = originalFeature;
		}
		if (!myDelegate.setValue(object, feature, value, position)) {
			super.setValue(object, feature, value, position);
		}
	}

	@Override
	public EStructuralFeature getFeature(EClass eClass, String namespaceURI, String name, boolean isElement) {
		if (myIsDelegateDisabled) {
			return super.getFeature(eClass, namespaceURI, name, isElement);
		}
		EStructuralFeature result = myDelegate.getFeature(eClass, namespaceURI, name, isElement);
		if (result == null) {
			result = super.getFeature(eClass, namespaceURI, name, isElement);
		}
		EClass narrow = myDelegate.getStructuralFeatureType(result);
		if (narrow != null) {
			EStructuralFeature fake = addNarrowedFeature(result);
			fake.setEType(narrow);
			return fake;
		}
		return result;
	}
	
	@Override
	public EClassifier getType(EFactory factory, String typeName) {
		if (myIsDelegateDisabled) {
			return super.getType(factory, typeName);
		}
		EClassifier result = myDelegate.getType(factory, typeName);
		if (result == null) {
			result = super.getType(factory, typeName);
		}
		return result;
	}

	@Override
	public void popContext() {
		super.popContext();
		if (myIsDelegateDisabled) {
			return;
		}
		myDelegate.postProcess();
	}
	
	@Override
	public void addPrefix(String prefix, String uri) {
		super.addPrefix(prefix, uri);
		if (myDelegate.isOldVersionDetected(uri)) {
			enableDelegate(true);
		}
	}

	protected EStructuralFeature getOriginalFeature(EStructuralFeature feature) {
		return myNarrowedFeatureTypes == null ? null : myNarrowedFeatureTypes.get(feature);
	}
	
	protected EStructuralFeature addNarrowedFeature(EStructuralFeature originalFeature) {
		if (myNarrowedFeatureTypes == null) {
			myNarrowedFeatureTypes = new HashMap<EStructuralFeature, EStructuralFeature>();
		}
		EStructuralFeature result = (EStructuralFeature) EcoreUtil.copy(originalFeature);
		myNarrowedFeatureTypes.put(result, originalFeature);
		return result;
	}
}
