/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.internal.bridge.wizards.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.provider.GMFGraphItemProviderAdapterFactory;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.GMFMapFactory;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeReference;
import org.eclipse.gmf.mappings.provider.GMFMapItemProviderAdapterFactory;
import org.eclipse.gmf.tooldef.ToolRegistry;
import org.eclipse.gmf.tooldef.provider.GMFToolItemProviderAdapterFactory;

/**
 * @author artem
 */
public class WizardInput {
	private EPackage myDomainModel;
	private Canvas myCanvas;
	private ToolRegistry myRegistry;
	private Mapping mapInstance;
	private EditingDomain myEditingDomain;
	private AdapterFactory myAdapterFactory;
	private IFile myResultFile = null;
	private MapDefFeeder myFeeder;

	public WizardInput() {
	}
	
	public AdapterFactory getAdapterFactory() {
		if (myAdapterFactory == null) {
			List factories = new ArrayList();
			factories.add(new ResourceItemProviderAdapterFactory());
			factories.add(new GMFMapItemProviderAdapterFactory());
			factories.add(new GMFGraphItemProviderAdapterFactory());
			factories.add(new GMFToolItemProviderAdapterFactory());
			factories.add(new ReflectiveItemProviderAdapterFactory());

			myAdapterFactory = new ComposedAdapterFactory(factories);
		}
		return myAdapterFactory;
	}
	
	public EditingDomain getEditingDomain() {
		if (myEditingDomain == null) {
			myEditingDomain = new AdapterFactoryEditingDomain(getAdapterFactory(), new BasicCommandStack());
		}
		return myEditingDomain;
	}

	public ResourceSet getResourceSet() {
		return getEditingDomain().getResourceSet();
	}

	public void setDomainModel(EPackage aPackage) {
		checkUnload(myDomainModel);
		myDomainModel = aPackage;
	}

	public void setGraphDef(Canvas canvas) {
		checkUnload(myCanvas);
		myCanvas = canvas;
	}

	public void setToolDef(ToolRegistry registry) {
		checkUnload(myRegistry);
		myRegistry = registry;
	}

	public void setMappingFile(IFile resultFile) {
//		if (myResultFile != null && resultFile != myResultFile) {
//			// perhaps, no reason to allow even 'touch'?
//			throw new IllegalStateException("Did't expect file to be chosen more than once");
//		}
		myResultFile = resultFile;
	}

	public IFile getMappingFile() {
		return myResultFile;
	}

	public Mapping getMapping() {
		if (mapInstance == null) {
			mapInstance = GMFMapFactory.eINSTANCE.createMapping();
			URI res = URI.createPlatformResourceURI(getMappingFile().getFullPath().toString());
			getResourceSet().createResource(res).getContents().add(mapInstance);
		}
		return mapInstance;
	}

	public boolean isReady2Go() {
		return myDomainModel != null && myCanvas != null /*&& myRegistry != null*/;
	}

	private void checkUnload(EObject eobj) {
		if (eobj == null) {
			return;
		}
		if (eobj.eResource().getResourceSet() == getResourceSet() && eobj.eResource().isLoaded()) {
			eobj.eResource().unload();
		}
	}
	public List/*<EClass>*/ getCanvasElementCandidates() {
		UniqueEList rv = new UniqueEList();
		for (Iterator it = myDomainModel.getEClassifiers().iterator(); it.hasNext();) {
			Object next = it.next();
			if (next instanceof EClass) {
				EClass eClass = (EClass) next;
				if (!eClass.isAbstract() && !eClass.isInterface() && !eClass.getEAllContainments().isEmpty()) {
					rv.add(eClass);
				}
			}
		}
		return rv;
	}

	public void selectCanvasElement(EClass eClass) {
		assert eClass.getEPackage() == myDomainModel;
		CanvasMapping cm;
		if (getMapping().getDiagram() == null) {
			cm =  GMFMapFactory.eINSTANCE.createCanvasMapping();
			cm.setDiagramCanvas(myCanvas);
			// +palette/gmfgraph.canvas
		} else {
			cm = getMapping().getDiagram();
		}
		cm.setDomainMetaElement(eClass);
		cm.setDomainModel(eClass.getEPackage());
		getMapping().setDiagram(cm);
	}

	public void feedDefaultMapping() {
		myFeeder = new MapDefFeeder(this);
		myFeeder.feedDefaultMapping();
	}

	public NodeReference[] nodeCandidates() {
		return myFeeder.getInitialNodes();
	}

	public LinkMapping[] linkCandidates() {
		return myFeeder.getInitialLinks();
	}
}
