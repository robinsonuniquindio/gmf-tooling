/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmf.mappings;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.diadef.Connection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getDiagramLink <em>Diagram Link</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getDomainMetaElement <em>Domain Meta Element</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getContainmentFeature <em>Containment Feature</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getLabelEditFeature <em>Label Edit Feature</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getLabelDisplayFeature <em>Label Display Feature</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LinkMapping#getLinkMetaFeature <em>Link Meta Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping()
 * @model
 * @generated
 */
public interface LinkMapping extends MappingEntry{
	/**
	 * Returns the value of the '<em><b>Diagram Link</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Link</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Link</em>' reference.
	 * @see #setDiagramLink(Connection)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_DiagramLink()
	 * @model required="true"
	 * @generated
	 */
	Connection getDiagramLink();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getDiagramLink <em>Diagram Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Link</em>' reference.
	 * @see #getDiagramLink()
	 * @generated
	 */
	void setDiagramLink(Connection value);

	/**
	 * Returns the value of the '<em><b>Domain Meta Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Meta Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Meta Element</em>' reference.
	 * @see #setDomainMetaElement(EClass)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_DomainMetaElement()
	 * @model
	 * @generated
	 */
	EClass getDomainMetaElement();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getDomainMetaElement <em>Domain Meta Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Meta Element</em>' reference.
	 * @see #getDomainMetaElement()
	 * @generated
	 */
	void setDomainMetaElement(EClass value);

	/**
	 * Returns the value of the '<em><b>Containment Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containment Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment Feature</em>' reference.
	 * @see #setContainmentFeature(EReference)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_ContainmentFeature()
	 * @model annotation="http://www.eclipse.org/gmf/2005/constraints ocl='oclIsUndefined(containmentFeature) or (not oclIsUndefined(domainMetaElement) and domainMetaElement.eAllReferences->includes(containmentFeature))'"
	 * @generated
	 */
	EReference getContainmentFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getContainmentFeature <em>Containment Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment Feature</em>' reference.
	 * @see #getContainmentFeature()
	 * @generated
	 */
	void setContainmentFeature(EReference value);

	/**
	 * Returns the value of the '<em><b>Label Edit Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Edit Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Edit Feature</em>' reference.
	 * @see #setLabelEditFeature(EAttribute)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_LabelEditFeature()
	 * @model
	 * @generated
	 */
	EAttribute getLabelEditFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getLabelEditFeature <em>Label Edit Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Edit Feature</em>' reference.
	 * @see #getLabelEditFeature()
	 * @generated
	 */
	void setLabelEditFeature(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Label Display Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Display Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * By default, same as editFeature, once latter is set
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Display Feature</em>' reference.
	 * @see #setLabelDisplayFeature(EAttribute)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_LabelDisplayFeature()
	 * @model
	 * @generated
	 */
	EAttribute getLabelDisplayFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getLabelDisplayFeature <em>Label Display Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Display Feature</em>' reference.
	 * @see #getLabelDisplayFeature()
	 * @generated
	 */
	void setLabelDisplayFeature(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Link Meta Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Meta Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Meta Feature</em>' reference.
	 * @see #setLinkMetaFeature(EStructuralFeature)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLinkMapping_LinkMetaFeature()
	 * @model required="true"
	 * @generated
	 */
	EStructuralFeature getLinkMetaFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LinkMapping#getLinkMetaFeature <em>Link Meta Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Meta Feature</em>' reference.
	 * @see #getLinkMetaFeature()
	 * @generated
	 */
	void setLinkMetaFeature(EStructuralFeature value);

} // LinkMapping
