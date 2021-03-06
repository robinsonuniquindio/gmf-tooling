/*
 * <copyright>
 *
 * Copyright (c) 2005-2006 Sven Efftinge and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sven Efftinge - Initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.gmf.internal.xpand.xtend.parser;

import java.util.List;

import lpg.lpgjavaruntime.IToken;

import org.eclipse.gmf.internal.xpand.expression.ast.DeclaredParameter;
import org.eclipse.gmf.internal.xpand.expression.ast.Expression;
import org.eclipse.gmf.internal.xpand.expression.ast.Identifier;
import org.eclipse.gmf.internal.xpand.expression.ast.StringLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.SyntaxElement;
import org.eclipse.gmf.internal.xpand.expression.parser.ExpressionFactory;
import org.eclipse.gmf.internal.xpand.xtend.ast.CreateExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.ExpressionExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.Extension;
import org.eclipse.gmf.internal.xpand.xtend.ast.ExtensionFile;
import org.eclipse.gmf.internal.xpand.xtend.ast.ImportStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.JavaExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.WorkflowSlotExtensionStatement;

public class ExtensionFactory extends ExpressionFactory {

	public ExtensionFactory(String string) {
		super(string);
	}

	public ExtensionFile createExtensionFile(final List<SyntaxElement> nsimports, final List<SyntaxElement> extimports, final List<SyntaxElement> extensions) {
		return handle(new ExtensionFile(0, -1, 1, 0, -1, nsimports, extimports, extensions));
	}

	public ImportStatement createNsImport(final IToken s, final IToken e, final StringLiteral st) {
		return handle(new ImportStatement(start(s), end(e), line(s), s.getStartOffset(), e.getEndOffset(), st.getValue()));
	}

	public ImportStatement createExtensionFileImport(final IToken s, final IToken e, final Identifier t, final IToken exported) {
		return handle(new ImportStatement(start(s), end(e), line(s), s.getStartOffset(), e.getEndOffset(), t, exported != null));
	}

	public JavaExtensionStatement createJavaExtension(final IToken name, final IToken endSemi, final Identifier type, final List<DeclaredParameter> params, final Identifier fqn, final List<Identifier> javaParamTypes, final IToken cached, final IToken isPrivate, final Identifier instanceSlot) {
		final String txt = fqn.getValue();
		final int index = txt.lastIndexOf('.');
		final String typeName = txt.substring(0, index);
		final String methodName = txt.substring(index + 1);
		final int start = getExtensionStart(cached, type, name);
		final int startOffset = getExtensionStartOffset(cached, type, name);
		final int line = getExtensionLine(cached, type, name);
		final Identifier javaMethod = new Identifier(fqn.getStart() + index + 1, fqn.getEnd(), fqn.getLine(), fqn.getStartOffset() + index + 1, fqn.getEndOffset(), methodName);
		final Identifier javaType = new Identifier(fqn.getStart(), fqn.getStart() + index, fqn.getLine(), fqn.getStartOffset(), fqn.getStartOffset() + index, typeName);
		return handle(new JavaExtensionStatement(start, end(endSemi), line, startOffset, endSemi.getEndOffset(), createIdentifier(name), params, type, javaType, javaMethod, javaParamTypes, cached != null, isPrivate != null, instanceSlot));
	}

	public WorkflowSlotExtensionStatement createWorkflowSlotExtension(final IToken name, final IToken e, final Identifier type, final List<DeclaredParameter> params, final Identifier slotName, final IToken cached, final IToken priv) {
		final int start = getExtensionStart(cached, type, name);
		final int startOffset = getExtensionStartOffset(cached, type, name);
		final int line = getExtensionLine(cached, type, name);
		return handle(new WorkflowSlotExtensionStatement(start, end(e), line, startOffset, e.getEndOffset(), createIdentifier(name), params, type, slotName, cached != null, priv != null));
	}

	protected static int getExtensionStart(final IToken resentful, final Identifier type, final IToken name) {
		// XXX syntax ordering is smth like "resentful type name", and it's odd why start(type) gets higher precedence
		int start = start(name);
		if (resentful != null) {
			start = start(resentful);
		} else if (type != null) {
			start = type.getStart();
		}
		return start;
	}
	
	protected static int getExtensionStartOffset(final IToken resentful, final Identifier type, final IToken name) {
		if (resentful != null) {
			return resentful.getStartOffset();
		} else if (type != null) {
			return type.getStartOffset();
		}
		return name.getStartOffset();
	}

	protected static int getExtensionLine(final IToken resentful, final Identifier type, final IToken name) {
		int line = line(name);
		if (resentful != null) {
			line = line(resentful);
		} else if (type != null) {
			line = type.getLine();
		}
		return line;
	}

	public ExpressionExtensionStatement createExpressionExtension(final IToken name, final IToken e, final Identifier returnType, final List<DeclaredParameter> params, final Expression expr, final IToken cached, final IToken priv) {
		final int start = getExtensionStart(cached, returnType, name);
		final int startOffset = getExtensionStartOffset(cached, returnType, name);
		final int line = getExtensionLine(cached, returnType, name);
		return handle(new ExpressionExtensionStatement(start, end(e), line, startOffset, e.getEndOffset(), createIdentifier(name), returnType, params, expr, cached != null, priv != null));
	}

	public Extension createCreateExtension(final IToken create, final IToken e, final Identifier returnType, final IToken rtn, final IToken name, final List<DeclaredParameter> params, final Expression expr, final IToken priv) {
		int line = line(create);
		IToken startElement = create;
		if (priv != null) {
			startElement = priv;
			line = line(priv);
		}
		return handle(new CreateExtensionStatement(start(startElement), end(e), line, startElement.getStartOffset(), e.getEndOffset(), createIdentifier(name), returnType, rtn != null ? createIdentifier(rtn) : null, params, expr, priv != null));
	}

}
