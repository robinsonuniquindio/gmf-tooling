package org.eclipse.gmf.examples.eclipsecon.library.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gef.commands.UnexecutableCommand;

import org.eclipse.gmf.examples.eclipsecon.library.Employee;
import org.eclipse.gmf.examples.eclipsecon.library.Shelf;

import org.eclipse.gmf.examples.eclipsecon.library.diagram.providers.Library07ElementTypes;

/**
 * @generated
 */
public class EmployeeItemSemanticEditPolicy extends
		Library07BaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		});
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (Library07ElementTypes.EmployeeShelves_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingEmployee_Shelves3001Command(req)
					: null;
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingEmployee_Shelves3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof Employee
				|| (targetEObject != null && false == targetEObject instanceof Shelf)) {
			return UnexecutableCommand.INSTANCE;
		}
		Employee source = (Employee) sourceEObject;
		Shelf target = (Shelf) targetEObject;
		if (!Library07BaseItemSemanticEditPolicy.LinkConstraints
				.canCreateEmployeeShelves_3001(source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}
}
