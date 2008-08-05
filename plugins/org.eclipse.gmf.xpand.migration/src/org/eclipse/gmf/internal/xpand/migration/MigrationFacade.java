/**
 * Copyright (c) 2008 Borland Software Corp.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alexander Shatalin (Borland) - initial API and implementation
 */
package org.eclipse.gmf.internal.xpand.migration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel;
import org.eclipse.gmf.internal.xpand.ResourceManager;
import org.eclipse.gmf.internal.xpand.expression.AnalysationIssue;
import org.eclipse.gmf.internal.xpand.expression.EvaluationException;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContext;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContextImpl;
import org.eclipse.gmf.internal.xpand.expression.SyntaxConstants;
import org.eclipse.gmf.internal.xpand.expression.ast.BooleanLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.BooleanOperation;
import org.eclipse.gmf.internal.xpand.expression.ast.Cast;
import org.eclipse.gmf.internal.xpand.expression.ast.ChainExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.CollectionExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.ConstructorCallExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.Expression;
import org.eclipse.gmf.internal.xpand.expression.ast.FeatureCall;
import org.eclipse.gmf.internal.xpand.expression.ast.IfExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.IntegerLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.LetExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.ListLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.NullLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.OperationCall;
import org.eclipse.gmf.internal.xpand.expression.ast.RealLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.StringLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.SwitchExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.TypeSelectExpression;
import org.eclipse.gmf.internal.xpand.migration.MigrationException.Type;
import org.eclipse.gmf.internal.xpand.xtend.ast.CreateExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.ExpressionExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.Extension;
import org.eclipse.gmf.internal.xpand.xtend.ast.JavaExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.WorkflowSlotExtensionStatement;
import org.eclipse.gmf.internal.xpand.xtend.ast.XtendResource;
import org.eclipse.ocl.ecore.PrimitiveType;

public class MigrationFacade {

	static final String LF = System.getProperty("line.separator");

	private static final String OCL_PATH_SEPARATOR = "::";

	private ResourceManager resourceManager;

	private StringBuilder output = new StringBuilder();

	private String resourceName;

	private ModeltypeImports modeltypeImports;

	private boolean injectUnusedImports;

	private ExecutionContext rootExecutionContext;

	private Stack<Expression> expressionsStack = new Stack<Expression>();

	public MigrationFacade(ResourceManager resourceManager, String xtendResourceName, boolean injectUnusedImports) {
		this(resourceManager, xtendResourceName);
		this.injectUnusedImports = injectUnusedImports;
	}

	public MigrationFacade(ResourceManager resourceManager, String xtendResourceName, ExecutionContext executionContext) {
		this(resourceManager, xtendResourceName);
		rootExecutionContext = executionContext;
	}

	public MigrationFacade(ResourceManager resourceManager, String xtendResourceName) {
		this.resourceManager = resourceManager;
		this.resourceName = xtendResourceName;
	}

	public StringBuilder migrateXtendResource() throws MigrationException {
		XtendResource xtendResource = resourceManager.loadXtendResource(resourceName);
		if (xtendResource == null) {
			throw new MigrationException(Type.RESOURCE_NOT_FOUND, "Unable to load resource: " + resourceName);
		}
		ExecutionContext ctx = (rootExecutionContext != null ? rootExecutionContext : new ExecutionContextImpl(resourceManager)).cloneWithResource(xtendResource);
		Set<AnalysationIssue> issues = new HashSet<AnalysationIssue>();
		xtendResource.analyze(ctx, issues);
		if (issues.size() > 0) {
			throw new MigrationException(issues);
		}

		String shortResourceName = getLastSegment(resourceName, SyntaxConstants.NS_DELIM);
		if (shortResourceName.length() == 0) {
			throw new MigrationException(Type.INCORRECT_RESOURCE_NAME, resourceName);
		}

		modeltypeImports = new ModeltypeImports(output, injectUnusedImports);

		for (String namespace : xtendResource.getImportedNamespaces()) {
			modeltypeImports.registerModeltype(namespace);
		}

		addLibraryImports(xtendResource, false);

		writeln("library " + shortResourceName + ";" + LF);

		for (Extension extension : xtendResource.getExtensions()) {
			migrateExtension(extension, ctx);
		}
		modeltypeImports.injectImports();
		return output;
	}

	private void addLibraryImports(XtendResource xtendResource, boolean reexportedOnly) throws MigrationException {
		for (String extension : xtendResource.getImportedExtensions()) {
			if (!reexportedOnly || xtendResource.isReexported(extension)) {
				writeln("import " + extension.replaceAll("::", ".") + ";");
				XtendResource referencedResource = resourceManager.loadXtendResource(extension);
				if (referencedResource == null) {
					throw new MigrationException(Type.RESOURCE_NOT_FOUND, "Unable to load extension file: " + extension);
				}
				addLibraryImports(referencedResource, true);
			}
		}
	}

	private void migrateExtension(Extension extension, ExecutionContext ctx) throws MigrationException {
		try {
			extension.init(ctx);
		} catch (EvaluationException e) {
			throw new MigrationException(Type.ANALYZATION_PROBLEMS, e);
		}

		write("helper ");
		write(extension.getName());
		write("(");

		assert extension.getParameterTypes().size() > 0;
		assert extension.getParameterNames().size() == extension.getParameterTypes().size();
		Iterator<String> parameterNames = extension.getParameterNames().iterator();
		Iterator<EClassifier> parameterTypes = extension.getParameterTypes().iterator();
		while (parameterNames.hasNext()) {
			write(parameterNames.next());
			write(" : ");
			write(getQvtFQName(parameterTypes.next()));
			if (parameterNames.hasNext()) {
				write(", ");
			}
		}
		write(") : ");
		// TODO: check it!
		write(getQvtFQName(getReturnType(extension, ctx)));
		writeln(" {");

		if (extension instanceof ExpressionExtensionStatement) {
			migrateExpressionExtension((ExpressionExtensionStatement) extension, ctx);
		} else if (extension instanceof JavaExtensionStatement) {
			migrateJavaExtension((JavaExtensionStatement) extension);
		} else if (extension instanceof CreateExtensionStatement) {
			migrateCreateExtension((CreateExtensionStatement) extension);
		} else if (extension instanceof WorkflowSlotExtensionStatement) {
			migrateWorkflowSlotExtension((WorkflowSlotExtensionStatement) extension);
		} else {
			throw new MigrationException(Type.UNSUPPORTED_EXTENSION, extension.getClass().getName());
		}
		writeln("}");
	}

	private EClassifier getReturnType(Extension extension, ExecutionContext ctx) throws MigrationException {
		Set<AnalysationIssue> issues = new HashSet<AnalysationIssue>();
		EClassifier returnType = extension.getReturnType(extension.getParameterTypes().toArray(new EClassifier[extension.getParameterNames().size()]), ctx, issues);
		if (issues.size() > 0) {
			throw new MigrationException(issues);
		}
		if (returnType == null) {
			throw new MigrationException(Type.TYPE_NOT_FOUND, extension.getReturnTypeIdentifier().getValue());
		}
		return returnType;
	}

	private String getQvtFQName(EClassifier classifier) throws MigrationException {
		if (classifier instanceof EDataType) {
			/**
			 * Handling QVT primitive types here.
			 */
			if (EcorePackage.eINSTANCE.getEString() == classifier) {
				return PrimitiveType.STRING_NAME;
			} else if (EcorePackage.eINSTANCE.getEBoolean() == classifier) {
				return PrimitiveType.BOOLEAN_NAME;
			} else if (EcorePackage.eINSTANCE.getEInt() == classifier) {
				return PrimitiveType.INTEGER_NAME;
			}
		}
		if (BuiltinMetaModel.isCollectionType(classifier)) {
			StringBuilder sb = new StringBuilder();
			if (classifier.getName().endsWith(BuiltinMetaModel.SET)) {
				sb.append("Set(");
			} else if (classifier.getName().endsWith(BuiltinMetaModel.LIST)) {
				sb.append("Sequence(");
			} else {
				sb.append("Collection(");
			}
			sb.append(getQvtFQName(BuiltinMetaModel.getInnerType(classifier)));
			return sb.append(")").toString();
		}
		EPackage ePackage = classifier.getEPackage();
		assert ePackage != null;
		String alias = modeltypeImports.getModeltypeAlias(ePackage);
		return alias + OCL_PATH_SEPARATOR + classifier.getName();
	}

	private void migrateExpressionExtension(ExpressionExtensionStatement extension, ExecutionContext ctx) throws MigrationException {
		write("return ");
		migrateExpression(extension.getExpression(), ctx);
		write(" ");
	}

	// TODO: java should be migrated separately from library - java class should
	// be created with the additional declaration in plugin.xml
	private void migrateJavaExtension(JavaExtensionStatement extension) throws MigrationException {
		throw new MigrationException(Type.UNSUPPORTED_EXTENSION, extension.getClass().getName());
	}

	private void migrateCreateExtension(CreateExtensionStatement extension) throws MigrationException {
		throw new MigrationException(Type.UNSUPPORTED_EXTENSION, extension.getClass().getName());
	}

	private void migrateWorkflowSlotExtension(WorkflowSlotExtensionStatement extension) throws MigrationException {
		throw new MigrationException(Type.UNSUPPORTED_EXTENSION, extension.getClass().getName());
	}

	private void migrateExpression(Expression expression, ExecutionContext ctx) throws MigrationException {
		expressionsStack.push(expression);
		try {
			if (expression instanceof BooleanOperation) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof Cast) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof ChainExpression) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof ConstructorCallExpression) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof CollectionExpression) {
				migrateCollectionExpression((CollectionExpression) expression, ctx);
			} else if (expression instanceof OperationCall) {
				migrateOperationCall((OperationCall) expression, ctx);
			} else if (expression instanceof TypeSelectExpression) {
				migrateTypeSelectExpression((TypeSelectExpression) expression, ctx);
			} else if (expression instanceof FeatureCall) {
				migrateFeatureCall((FeatureCall) expression, ctx);
			} else if (expression instanceof IfExpression) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof LetExpression) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof ListLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof BooleanLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof IntegerLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof NullLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof RealLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof StringLiteral) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else if (expression instanceof SwitchExpression) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			} else {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			}
		} finally {
			expressionsStack.pop();
		}
	}

	private void migrateTypeSelectExpression(TypeSelectExpression typeSelectExpression, ExecutionContext ctx) throws MigrationException {
		migrateExpression(typeSelectExpression.getTarget(), ctx);
		EClassifier type = ctx.getTypeForName(typeSelectExpression.getTypeLiteral().getValue());
		if (type == null) {
			throw new MigrationException(Type.TYPE_NOT_FOUND, typeSelectExpression.getTypeLiteral().getValue());
		}
		write("->select(element | element.oclIsKindOf(");
		write(getQvtFQName(type));
		write("))->collect(element | element.oclAsType(");
		write(getQvtFQName(type));
		write("))");
	}

	private void migrateCollectionExpression(CollectionExpression collectionExpression, ExecutionContext ctx) throws MigrationException {
		if (collectionExpression.getTarget() == null) {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Collection expression without target specified: " + collectionExpression.toString());
		}
		int placeHolder = getCurrentPosition();
		boolean hasNegation = false;
		migrateExpression(collectionExpression.getTarget(), ctx);
		write("->");
		// TODO: replace all these if() with single one +
		// write(collectionExpression.getName().getValue())?
		if (collectionExpression.getName().getValue().equals(SyntaxConstants.COLLECT)) {
			write("collect");
		} else if (collectionExpression.getName().getValue().equals(SyntaxConstants.SELECT)) {
			write("select");
		} else if (collectionExpression.getName().getValue().equals(SyntaxConstants.REJECT)) {
			write("reject");
		} else if (collectionExpression.getName().getValue().equals(SyntaxConstants.EXISTS)) {
			write("exists");
		} else if (collectionExpression.getName().getValue().equals(SyntaxConstants.NOT_EXISTS)) {
			hasNegation = true;
			write("not ", placeHolder);
			write("exists");
		} else if (collectionExpression.getName().getValue().equals(SyntaxConstants.FOR_ALL)) {
			write("forAll");
		} else {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, collectionExpression.getName().getValue());
		}
		write("(");
		write(collectionExpression.getElementName());
		write(" | ");
		migrateExpression(collectionExpression.getClosure(), ctx);
		write(")");
		if (hasNegation) {
			addBraces(placeHolder);
		}
	}

	private void addBraces(int placeHolder) {
		if (expressionsStack.size() == 1) {
			return;
		}
		// TODO: check for the type of parent expression here + add braces
		// conditionaly
		// Expression parentExpression =
		// expressionsStack.get(expressionsStack.size() - 2);
		// check for the type of parent expression;
		write("(", placeHolder);
		write(")");
	}

	private void migrateOperationCall(OperationCall operationCall, ExecutionContext ctx) throws MigrationException {
		// TODO: if (target == null) then it can be a call to self.<operation>
		// in this case operation call call should be processed
		// specially (respecting self multiplicity).
		int placeholder = getCurrentPosition();
		if (operationCall.getTarget() != null) {
			// TODO: support different multiplicity of target - different
			// collections have to be created here. (->asList()..)
			migrateExpression(operationCall.getTarget(), ctx);
		}

		if (isInfixOperation(operationCall)) {
			insertInfixOperationCall(operationCall, placeholder);
		} else {
			if (operationCall.getTarget() != null) {
				write(".");
			}
			write(getQVTOperationName(operationCall));
			write("(");
		}

		for (int i = 0; i < operationCall.getParams().length; i++) {
			if (i > 0) {
				write(", ");
			}
			migrateExpression(operationCall.getParams()[i], ctx);
		}
		if (!isInfixOperation(operationCall)) {
			write(")");
		} else if (needsSurroundingBraces(operationCall)) {
			// Currently supported infix operations has 0 or 1 parameter
			// Enclosing with braces for "not" expression here
			addBraces(placeholder);
		}
	}

	private String getQVTOperationName(OperationCall operationCall) {
		String operationName = operationCall.getName().getValue();
		// TODO: In addition check target type (should be one of primitive
		// types) here
		if ("toFirstUpper".equals(operationName)) {
			return "firstToUpper";
		}
		return operationName;
	}

	private boolean needsSurroundingBraces(OperationCall operationCall) {
		return "!".equals(operationCall.getName().getValue());
	}

	private void insertInfixOperationCall(OperationCall operationCall, int placeholder) throws MigrationException {
		// TODO: add other infix operations to this list
		if ("!".equals(operationCall.getName().getValue())) {
			write("not ", placeholder);
		} else {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Incorrect infix operation: " + operationCall.getName().getValue());
		}
	}

	private boolean isInfixOperation(OperationCall operationCall) {
		// TODO: add other infix operations to this list
		return "!".equals(operationCall.getName().getValue());
	}

	private void migrateFeatureCall(FeatureCall featureCall, ExecutionContext ctx) throws MigrationException {
		if (featureCall.getTarget() == null) {
			EEnumLiteral enumLiteral = featureCall.getEnumLiteral(ctx);
			if (enumLiteral != null) {
				String modelType = modeltypeImports.getModeltypeAlias(enumLiteral.getEEnum().getEPackage());
				write(modelType);
				write("::");
				write(enumLiteral.getEEnum().getName());
				write("::");
				write(enumLiteral.getName());
				return;
			}
			// TODO: It could be a call to environment variable or
			// self.<feature> in case of "self" this call should be processed
			// specially (respecting self multiplicity).
		}
		if (featureCall.getTarget() != null) {
			// TODO: support different multiplicity of target - different
			// collections have to be created here. (->asList()..)
			migrateExpression(featureCall.getTarget(), ctx);
			write(".");
		}
		write(featureCall.getName().getValue());
	}

	private static String getLastSegment(String string, String separator) {
		int delimeterIndex = string.lastIndexOf(separator);
		if (delimeterIndex > 0) {
			return string.substring(delimeterIndex + separator.length());
		} else {
			return string;
		}
	}

	private int getCurrentPosition() {
		return output.length();
	}

	private void write(String word, int index) {
		output.insert(index, word);
	}

	private void write(String word) {
		output.append(word);
	}

	private void writeln(String line) {
		output.append(line);
		output.append(LF);
	}

}