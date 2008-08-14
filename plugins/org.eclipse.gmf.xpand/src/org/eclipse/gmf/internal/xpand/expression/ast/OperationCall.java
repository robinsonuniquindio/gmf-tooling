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
package org.eclipse.gmf.internal.xpand.expression.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel.Operation;
import org.eclipse.gmf.internal.xpand.expression.AnalysationIssue;
import org.eclipse.gmf.internal.xpand.expression.EvaluationException;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContext;
import org.eclipse.gmf.internal.xpand.expression.Variable;
import org.eclipse.gmf.internal.xpand.xtend.ast.Extension;

/**
 * @author Sven Efftinge
 * @author Arno Haase
 */
public class OperationCall extends FeatureCall {

    private Expression[] params;

    public OperationCall(final int start, final int end, final int line, final Identifier name,
            final Expression target, final Expression[] params) {
        super(start, end, line, name, target);
        this.params = params;
    }

    public Expression[] getParams() {
        return params;
    }

    @Override
    public Object evaluateInternal(final ExecutionContext ctx) {

        final Object[] params = new Object[getParams().length];
        final EClassifier[] paramTypes = new EClassifier[params.length];
        for (int i = 0; i < getParams().length; i++) {
            params[i] = getParams()[i].evaluate(ctx);
            paramTypes[i] = BuiltinMetaModel.getType(params[i]);
        }
        
        Object targetObj = null;
        if (getTarget() == null) {
            // extension
            final Extension f = ctx.getExtension(getName().getValue(), paramTypes);
            if (f != null) {
				return f.evaluate(params, ctx);
			}

            // implicite
            final Variable var = ctx.getVariable(ExecutionContext.IMPLICIT_VARIABLE);
            if (var == null) {
            	// FIXME "could not find *context*, not *extension*?
				throw new EvaluationException("Couldn't find extension '" + getName().getValue()
                        + getParamTypes(params, ctx) + "'!", this);
			}
            targetObj = var.getValue();
        } else {
            targetObj = getTarget().evaluate(ctx);
        }

        // operation
        Operation op = BuiltinMetaModel.executableOperation(getName().getValue(), params, targetObj);
        if (op != null) {
			return op.evaluate();
		}
        // extension as members
        EClassifier[] ps = new EClassifier[paramTypes.length + 1];
        ps[0] = BuiltinMetaModel.getType(targetObj);
        System.arraycopy(paramTypes, 0, ps, 1, paramTypes.length);
        Extension f = ctx.getExtension(getName().getValue(), ps);
        if (f != null) {
            try {
            	Object[] paramsAll = new Object[params.length + 1];
            	paramsAll[0] = targetObj;
            	System.arraycopy(params, 0, paramsAll, 1, params.length);
                return f.evaluate(paramsAll, ctx);
            } finally {

            }
        }

        if (targetObj instanceof Collection) {
            final List<Object> result = new ArrayList<Object>();
            final Collection col = (Collection) targetObj;
            for (final Iterator iter = col.iterator(); iter.hasNext();) {
                final Object element = iter.next();
                // operation
                op = BuiltinMetaModel.executableOperation(getName().getValue(), params, element);
                if (op != null) {
                    final Object r = op.evaluate();
                    if (r instanceof Collection) {
                        result.addAll((Collection<?>) r);
                    } else {
                        result.add(r);
                    }
                } else {
                    // extension as members
                    ps = new EClassifier[paramTypes.length + 1];
                    ps[0] = BuiltinMetaModel.getType(element);
                    System.arraycopy(paramTypes, 0, ps, 1, paramTypes.length);
                    f = ctx.getExtension(getName().getValue(), ps);
                    if (f != null) {
                    	Object[] paramsAll = new Object[params.length + 1];
                    	paramsAll[0] = element;
                    	System.arraycopy(params, 0, paramsAll, 1, params.length);
                        final Object r = f.evaluate(paramsAll, ctx);
                        if (r instanceof Collection) {
                            result.addAll((Collection<?>) r);
                        } else {
                            result.add(r);
                        }
                    } else {
						throw new EvaluationException("Couldn't find operation '" + getName().getValue()
                                + getParamTypes(params, ctx) + "' for " + BuiltinMetaModel.getType(targetObj).getName() + "!", this);
					}
                }
            }
            return result;
        }

        if ((targetObj != null) && (f == null) && (op == null)) {
			throw new EvaluationException("Couldn't find operation '" + getName().getValue()
                    + getParamTypes(params, ctx) + "' for " + BuiltinMetaModel.getType(targetObj).getName() + ".", this);
		} else {
			return null;
		}

    }

    @Override
    public EClassifier analyze(final ExecutionContext ctx, final Set<AnalysationIssue> issues) {
        final EClassifier[] paramTypes = new EClassifier[getParams().length];
        for (int i = 0; i < getParams().length; i++) {
            paramTypes[i] = getParams()[i].analyze(ctx, issues);
            if (paramTypes[i] == null) {
				return null;
			}
        }

        // extension
        EClassifier targetType = null;
        if (getTarget() == null) {
            Extension f = null;
            try {
                f = ctx.getExtension(getName().getValue(), paramTypes);
            } catch (final Exception e) {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR, "Error parsing extensions : "
                        + e.getMessage(), this));
            }
            if (f != null) {
				return f.getReturnType(paramTypes, ctx, issues);
			}
            final Variable var = ctx.getVariable(ExecutionContext.IMPLICIT_VARIABLE);
            if (var != null) {
                targetType = (EClassifier) var.getValue();
            } else {
            	// FIXME could not find *this*
                issues.add(new AnalysationIssue(AnalysationIssue.Type.FEATURE_NOT_FOUND, "Couldn't find extensions : "
                        + toString(), this));
            }
        } else {
            targetType = getTarget().analyze(ctx, issues);
        }
        if (targetType == null) {
			return null;
		}
        // operation
        EOperation op = BuiltinMetaModel.findOperation(targetType, getName().getValue(), paramTypes);
        if (op != null) {
			return op.getEType() == null ? BuiltinMetaModel.VOID : BuiltinMetaModel.getTypedElementType(op);
		}
        // extension as members
        final int issueSize = issues.size();
        EClassifier rt = getExtensionsReturnType(ctx, issues, paramTypes, targetType);
        if (rt != null) {
			return rt;
		} else if (issueSize < issues.size()) {
			return null;
		}
        String additionalMsg = "";
        if (BuiltinMetaModel.isParameterizedType(targetType)) {
            final EClassifier innerType = BuiltinMetaModel.getInnerType(targetType);
            op = BuiltinMetaModel.findOperation(innerType, getName().getValue(), paramTypes);
            if (op != null) {
                rt = op.getEType() == null ? BuiltinMetaModel.VOID : op.getEType();
                if (BuiltinMetaModel.isParameterizedType(rt)) {
                    rt = BuiltinMetaModel.getInnerType(rt);
                }
                return BuiltinMetaModel.getListType(rt);
            }
            rt = getExtensionsReturnType(ctx, issues, paramTypes, innerType);
            if (rt != null) {
				return BuiltinMetaModel.getListType(rt);
			}
            additionalMsg = " or type '" + innerType + "'";
        }

        issues.add(new AnalysationIssue(AnalysationIssue.Type.FEATURE_NOT_FOUND, "Couldn't find operation '"
                + getName().getValue() + getParamsString(paramTypes) + "' for type '" + targetType.getName() + "'"
                + additionalMsg, this));
        return null;

    }

    private EClassifier getExtensionsReturnType(final ExecutionContext ctx, final Set<AnalysationIssue> issues, final EClassifier[] paramEClassifiers,
            final EClassifier targetEClassifier) {
        final EClassifier[] pts = new EClassifier[paramEClassifiers.length + 1];
        pts[0] = targetEClassifier;
        System.arraycopy(paramEClassifiers, 0, pts, 1, paramEClassifiers.length);
        Extension f = null;
        try {
            f = ctx.getExtension(getName().getValue(), pts);
        } catch (final Exception e) {
            issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR, "Error parsing extensions : "
                    + e.getMessage(), this));
        }
        if (f != null) {
            final Set<AnalysationIssue> temp = new HashSet<AnalysationIssue>();
            final EClassifier rt = f.getReturnType(pts, ctx, temp);
            if (rt == null) {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR,
                        "couldn't resolve return type for extension " + f + "! Errors : " + temp.toString(), this));
            }
            return rt;
        } else if (getTarget() == null) { // try without implicite this
            try {
                f = ctx.getExtension(getName().getValue(), paramEClassifiers);
            } catch (final Exception e) {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR, "Error parsing extensions : "
                        + e.getMessage(), this));
            }
            if (f != null) {
                final Set<AnalysationIssue> temp = new HashSet<AnalysationIssue>();
                final EClassifier rt = f.getReturnType(pts, ctx, temp);
                if (rt == null) {
                    issues.add(new AnalysationIssue(AnalysationIssue.Type.INTERNAL_ERROR,
                            "couldn't resolve return type for extension " + f + "! Errors : " + temp.toString(), this));
                }
                return rt;
            }
        }
        return null;
    }

    private String getParamTypes(final Object[] params2, final ExecutionContext ctx) {
        final StringBuffer buff = new StringBuffer("(");
        for (int i = 0; i < params2.length; i++) {
            final EClassifier type = BuiltinMetaModel.getType(params2[i]);
            buff.append(type.getName());
            if (i + 1 < params2.length) {
                buff.append(",");
            }
        }
        return buff.append(")").toString();
    }

    private String getParamsString(final EClassifier[] paramTypes) {
        final StringBuffer buff = new StringBuffer("(");
        for (int i = 0; i < paramTypes.length; i++) {
            final EClassifier type = paramTypes[i];
            buff.append(type.getName());
            if (i + 1 < paramTypes.length) {
                buff.append(",");
            }
        }
        return buff.append(")").toString();
    }

    @Override
    public String toString() {
        return (getTarget() != null ? getTarget().toString() + "." : "") + getName()
                + getParamsExpressionString(getParams());
    }

    private String getParamsExpressionString(final Expression[] params2) {
        final StringBuffer buff = new StringBuffer("(");
        for (int i = 0; i < params2.length; i++) {
            buff.append(params2[i]);
            if (i + 1 < params2.length) {
                buff.append(",");
            }
        }
        return buff.append(")").toString();
    }
}