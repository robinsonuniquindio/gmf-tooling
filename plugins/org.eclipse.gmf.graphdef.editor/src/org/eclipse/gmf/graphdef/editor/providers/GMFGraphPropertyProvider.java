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
package org.eclipse.gmf.graphdef.editor.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.gmf.runtime.emf.ui.properties.providers.GenericEMFPropertiesProvider;
import org.eclipse.gmf.runtime.notation.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

import org.eclipse.gmf.gmfgraph.GMFGraphFactory;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.gmfgraph.Layoutable;
import org.eclipse.gmf.gmfgraph.Point;
import org.eclipse.gmf.gmfgraph.Polyline;

import org.eclipse.gmf.graphdef.editor.edit.parts.CanvasEditPart;

import org.eclipse.gmf.graphdef.editor.part.GMFGraphDiagramEditorPlugin;
import org.eclipse.gmf.graphdef.editor.part.GMFGraphVisualIDRegistry;

import org.eclipse.gmf.runtime.common.ui.services.properties.descriptors.CompositePropertySource;

import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositePropertySource;
import org.eclipse.gmf.runtime.emf.ui.properties.descriptors.EMFCompositeSourcePropertyDescriptor;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * @generated
 */
public class GMFGraphPropertyProvider extends GenericEMFPropertiesProvider implements IPropertiesProvider {

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
			if (element instanceof Layoutable) {
				CompositePropertySource compositeSource = new CompositePropertySource(element);
				compositeSource.addPropertySource(super.getPropertySource(element));

				class ChildMetaclassItemPropertyDescriptor extends ItemPropertyDescriptor {

					private EObject[] myValues;

					ChildMetaclassItemPropertyDescriptor(AdapterFactory adapterFactory, String displayName, String description, EStructuralFeature feature, boolean isSettable, EObject[] valueInstances) {
						super(adapterFactory, displayName, description, feature, isSettable);
						myValues = valueInstances;
					}

					protected Collection getComboBoxObjects(Object object) {
						if (object instanceof EObject) {
							EObject eObject = (EObject) object;
							Object currentValue = getValue(eObject, feature);
							Collection result = new ArrayList();
							result.add(currentValue);
							EClass valueEClass = currentValue instanceof EObject ? ((EObject) currentValue).eClass() : null;
							for (int i = 0; i < myValues.length; i++) {
								if (myValues[i].eClass() == valueEClass) {
									continue;
								}
								result.add(myValues[i]);
							}
							if (currentValue != null) {
								result.add(null);
							}
							return result;
						}
						return null;
					}

					public void setPropertyValue(Object object, Object value) {
						if (value instanceof EObject) {
							EClass eClass = ((EObject) value).eClass();
							value = eClass.getEPackage().getEFactoryInstance().create(eClass);
						}
						super.setPropertyValue(object, value);
					}
				}

				class SingleDescriptorPropertySource implements IItemPropertySource {

					private IItemPropertyDescriptor myPropertyDescriptor;

					private List myDescriptors = new LinkedList();

					SingleDescriptorPropertySource(IItemPropertyDescriptor propertyDescriptor) {
						myPropertyDescriptor = propertyDescriptor;
						myDescriptors.add(myPropertyDescriptor);
					}

					public List getPropertyDescriptors(Object object) {
						return myDescriptors;
					}

					public IItemPropertyDescriptor getPropertyDescriptor(Object object, Object propertyID) {
						return myPropertyDescriptor;
					}

					public Object getEditableValue(Object object) {
						return object;
					}

				}

				IItemPropertyDescriptor layoutPropertyDescriptor = new ChildMetaclassItemPropertyDescriptor(GMFGraphDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory(),
						"Layout Manager", "Layout Manager", GMFGraphPackage.eINSTANCE.getLayoutable_Layout(), true, new EObject[] { GMFGraphFactory.eINSTANCE.createBorderLayout(),
								GMFGraphFactory.eINSTANCE.createCustomLayout(), GMFGraphFactory.eINSTANCE.createFlowLayout(), GMFGraphFactory.eINSTANCE.createGridLayout(),
								GMFGraphFactory.eINSTANCE.createStackLayout(), GMFGraphFactory.eINSTANCE.createXYLayout() });
				compositeSource.addPropertySource(new EMFCompositePropertySource(element, new SingleDescriptorPropertySource(layoutPropertyDescriptor), "EMF")); //$NON-NLS-1$
				IItemPropertyDescriptor layoutDataPropertyDescriptor = new ChildMetaclassItemPropertyDescriptor(GMFGraphDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory(),
						"Layout Data", "Layout Data", GMFGraphPackage.eINSTANCE.getLayoutable_LayoutData(), true, new EObject[] { GMFGraphFactory.eINSTANCE.createBorderLayoutData(),
								GMFGraphFactory.eINSTANCE.createCustomLayoutData(), GMFGraphFactory.eINSTANCE.createGridLayoutData(), GMFGraphFactory.eINSTANCE.createXYLayoutData() });
				compositeSource.addPropertySource(new EMFCompositePropertySource(element, new SingleDescriptorPropertySource(layoutDataPropertyDescriptor), "EMF")); //$NON-NLS-1$

				if (element instanceof Polyline) {
					int counter = 1;
					for (Iterator it = ((Polyline) element).getTemplate().iterator(); it.hasNext(); counter++) {
						Point nextPoint = (Point) it.next();
						final String titleX = "Point " + counter + " X";
						IItemPropertyDescriptor nextPointPropertyDescriptorX = new ItemPropertyDescriptor(GMFGraphDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory(), titleX, titleX,
								GMFGraphPackage.eINSTANCE.getPoint_X(), true, "Template");
						compositeSource.addPropertySource(new EMFCompositePropertySource(nextPoint, new SingleDescriptorPropertySource(nextPointPropertyDescriptorX), "EMF") {

							protected IPropertyDescriptor newPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
								return new EMFCompositeSourcePropertyDescriptor(object, itemPropertyDescriptor, getCategory()) {

									public Object getId() {
										return titleX;
									}
								};
							}
						});

						final String titleY = "Point " + counter + " Y";
						IItemPropertyDescriptor nextPointPropertyDescriptorY = new ItemPropertyDescriptor(GMFGraphDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory(), titleY, titleY,
								GMFGraphPackage.eINSTANCE.getPoint_Y(), true, "Template");
						compositeSource.addPropertySource(new EMFCompositePropertySource(nextPoint, new SingleDescriptorPropertySource(nextPointPropertyDescriptorY), "EMF") {

							protected IPropertyDescriptor newPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
								return new EMFCompositeSourcePropertyDescriptor(object, itemPropertyDescriptor, getCategory()) {

									public Object getId() {
										return titleY;
									}
								};
							}
						});
					}
				}

				return compositeSource;
			}
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
		if (view != null && CanvasEditPart.MODEL_ID.equals(GMFGraphVisualIDRegistry.getModelID(view))) {
			return view.getElement();
		}
		return null;
	}

}
