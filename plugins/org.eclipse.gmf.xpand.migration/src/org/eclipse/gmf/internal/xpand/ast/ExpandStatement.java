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
package org.eclipse.gmf.internal.xpand.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel;
import org.eclipse.gmf.internal.xpand.expression.AnalysationIssue;
import org.eclipse.gmf.internal.xpand.expression.EvaluationException;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContext;
import org.eclipse.gmf.internal.xpand.expression.Variable;
import org.eclipse.gmf.internal.xpand.expression.ast.Expression;
import org.eclipse.gmf.internal.xpand.expression.ast.Identifier;
import org.eclipse.gmf.internal.xpand.migration.ExpandAnalyzeTrace;
import org.eclipse.gmf.internal.xpand.model.XpandDefinition;
import org.eclipse.gmf.internal.xpand.model.XpandExecutionContext;

/**
 * @author Sven Efftinge
 */
public class ExpandStatement extends Statement {

    private final boolean foreach;

    private final Expression[] parameters;

    private final Expression separator;

    private final Expression target;

    private final Identifier definition;

    public ExpandStatement(final int start, final int end, final int line, final int startOffset, final int endOffset, final Identifier definition,
            final Expression target, final Expression separator, final Expression[] parameters, final boolean foreach) {
        super(start, end, line, startOffset, endOffset);
        this.definition = definition;
        this.target = target;
        this.separator = separator;
        this.parameters = parameters != null ? parameters : new Expression[0];
        this.foreach = foreach;
    }

    public Identifier getDefinition() {
        return definition;
    }

    public boolean isForeach() {
        return foreach;
    }

    public Expression[] getParameters() {
        return parameters;
    }

    public Expression getSeparator() {
        return separator;
    }

    public Expression getTarget() {
        return target;
    }

    public void analyze(final XpandExecutionContext ctx, final Set<AnalysationIssue> issues) {
        final EClassifier[] paramTypes = new EClassifier[getParameters().length];
        for (int i = 0; i < getParameters().length; i++) {
            paramTypes[i] = getParameters()[i].analyze(ctx, issues);

        }
        EClassifier separatorType = null;
        if (separator != null) {
        	separatorType = separator.analyze(ctx, issues);
        }
        EClassifier targetType = null;
        if (isForeach()) {
            targetType = target.analyze(ctx, issues);
            if (BuiltinMetaModel.isCollectionType(targetType)) {
            	// XXX [artem] though COLLECTION TYPE *is* ParameterizedType, perhaps
            	// reason to check for instanceof ParameterizedType here
            	// is to avoid cases when targetType is VOID
            	assert BuiltinMetaModel.isParameterizedType(targetType) : "Just curious (is it ever == false): ";
                if (BuiltinMetaModel.isParameterizedType(targetType)) {
                    targetType = BuiltinMetaModel.getInnerType(targetType);
                } else {
                    targetType = EcorePackage.eINSTANCE.getEJavaObject();
                }
            } else {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INCOMPATIBLE_TYPES, "Collection type expected!", target));
                return;
            }
        } else {
            final Variable var = ctx.getVariable(ExecutionContext.IMPLICIT_VARIABLE);
            if (var == null) {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR,
                        "No implicite variable 'this' could be found!", target));
                return;
            }
            targetType = (EClassifier) var.getValue();
            if (target != null) {
                targetType = target.analyze(ctx, issues);
            }
        }
        createAnalyzeTrace(ctx, new ExpandAnalyzeTrace(getParameters(), paramTypes, separatorType, targetType));
        if ((targetType == null) || Arrays.asList(paramTypes).contains(null)) {
			return;
		}
        final XpandDefinition def = ctx.findDefinition(getDefinition().getValue(), targetType, paramTypes);
        if (def == null) {
            issues.add(new AnalysationIssue(AnalysationIssue.Type.DEFINITION_NOT_FOUND,
                    "Couldn't find definition " + getDefinition().getValue() + getParamTypeString(paramTypes)
                            + " for type " + targetType.getName(), this));
        }
    }

    @Override
    public void evaluateInternal(final XpandExecutionContext ctx) {
        final Object[] params = new Object[getParameters().length];
        for (int i = 0; i < getParameters().length; i++) {
            params[i] = getParameters()[i].evaluate(ctx);
        }
        final EClassifier[] paramTypes = new EClassifier[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = BuiltinMetaModel.getType(params[i]);
        }
        final String defName = getDefinition().getValue();
        final String sep = (String) (separator != null ? separator.evaluate(ctx) : null);
        Object targetObject = null;
        if (isForeach()) {
            targetObject = target.evaluate(ctx);
            if (!(targetObject instanceof Collection)) {
				throw new EvaluationException("Collection expected!", target);
			}

            final Collection<?> col = (Collection<?>) targetObject;
            for (final Iterator<?> iter = col.iterator(); iter.hasNext();) {
                final Object targetObj = iter.next();
                invokeDefinition(defName, targetObj, params, paramTypes, ctx);
                if ((sep != null) && iter.hasNext()) {
                    ctx.getOutput().write(sep);
                }
            }

        } else {
            if (target != null) {
                targetObject = target.evaluate(ctx);
            } else {
                final Variable var = ctx.getVariable(ExecutionContext.IMPLICIT_VARIABLE);
                targetObject = var.getValue();
            }
            if (targetObject != null) {
            	invokeDefinition(defName, targetObject, params, paramTypes, ctx);
            } else {
            	// XXX logInfo that feature value is null or conditionally fail?
            	// perhaps, could check if target is feature and multiplicity of the feature is at least 1 and fail then?
            	// though all these checks are not template's tasks
            }
        }
    }

    private void invokeDefinition(final String defName, final Object targetObj, final Object[] params,
            final EClassifier[] paramTypes, XpandExecutionContext ctx) {
        final EClassifier t = BuiltinMetaModel.getType(targetObj);
        final XpandDefinition def = ctx.findDefinition(defName, t, paramTypes);
        if (def == null) {
			throw new EvaluationException("No Definition '" + defName + getParamTypeString(paramTypes) + " for "
                    + t.getName() + "' found!", this);
		}

        // register variables
        ctx = ctx.cloneWithoutVariables();
        ctx = ctx.cloneWithVariable(new Variable(ExecutionContext.IMPLICIT_VARIABLE, targetObj));
        for (int i = 0; i < def.getParams().length; i++) {
            final String name = def.getParams()[i].getName().getValue();
            final Object val = params[i];
            ctx = ctx.cloneWithVariable(new Variable(name, val));
        }
        if (def.getOwner() != null) {
            ctx = ctx.cloneWithResource(def.getOwner());
        }
        def.evaluate(ctx);

    }

    private String getParamTypeString(final EClassifier[] paramTypes) {
        if (paramTypes.length == 0) {
			return "";
		}
        final StringBuffer buff = new StringBuffer("(");
        for (int i = 0; i < paramTypes.length; i++) {
            final EClassifier type = paramTypes[i];
            buff.append(type.getName());
            if (i + 1 < paramTypes.length) {
                buff.append(", ");
            }
        }
        return buff.append(")").toString();
    }

    private String getParamString(final Expression[] paramTypes) {
        if (paramTypes.length == 0) {
			return "";
		}
        final StringBuffer buff = new StringBuffer("(");
        for (int i = 0; i < paramTypes.length; i++) {
            final Expression type = paramTypes[i];
            buff.append(type);
            if (i + 1 < paramTypes.length) {
                buff.append(", ");
            }
        }
        return buff.append(")").toString();
    }

    @Override
    public String toString() {
        return "EXPAND " + definition + getParamString(getParameters())
                + (target != null ? (isForeach() ? " FOREACH " : " FOR ") + target : "")
                + (separator != null ? " SEPARATOR " + separator : "");
    }

}
