package org.eclipse.gmf.ecore.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ListItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

import org.eclipse.gmf.ecore.edit.policies.EStringToStringMapEntryItemSemanticEditPolicy;

import org.eclipse.gmf.ecore.providers.EcoreElementTypes;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;

/**
 * @generated
 */
public class EStringToStringMapEntryEditPart extends ListItemEditPart {

	/**
	 * @generated
	 */
	public EStringToStringMapEntryEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new EStringToStringMapEntryItemSemanticEditPolicy());
	}

	/**
	 * @generated
	 */
	protected String getLabelText() {
		String text = super.getLabelText();
		if (text == null || text.length() == 0) {
			return "<...>";
		}
		return text;
	}

	/**
	 * @generated
	 */
	public IParser getParser() {
		if (parser == null) {
			String parserHint = ((View) getModel()).getType();
			EObject element = resolveSemanticElement();
			if (element != null) {
				ParserHintAdapter hintAdapter = new ParserHintAdapter(element, parserHint) {

					public Object getAdapter(Class adapter) {
						if (IElementType.class.equals(adapter)) {
							return EcoreElementTypes.EStringToStringMapEntry_2009;
						} else if (TransactionalEditingDomain.class.equals(adapter)) {
							return getEditingDomain();
						}
						return super.getAdapter(adapter);
					}
				};
				parser = ParserService.getInstance().getParser(hintAdapter);
			}
		}
		return parser;
	}
}
