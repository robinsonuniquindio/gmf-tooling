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
package org.eclipse.gmf.tests.xpand.migration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContextImpl;
import org.eclipse.gmf.internal.xpand.migration.MigrationException;
import org.eclipse.gmf.internal.xpand.migration.MigrationFacade;
import org.eclipse.gmf.tests.xpand.TestsResourceManager;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.compiler.CompiledModule;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompiler;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompilerOptions;

public class XtendMigrationTest extends TestCase {

	private TestsResourceManager testResourceManager;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testResourceManager = new TestsResourceManager();
	}

	// Test for primitive type translation + several parameters in query
	public void testPrimitiveTypeParameters() throws IOException, MigrationException, MdaException {
		String resourceName = "PrimitiveTypeParameters";
		String resourceContent = checkMigration(resourceName);
		checkQVTCompilation(resourceName, resourceContent);
	}

	// Test for user-definet type parameters and import migration
	public void testImportedModels() throws IOException, MigrationException {
		checkMigration("ImportedModels");
	}

	public void testImportedModelsWithUnusedImports() throws IOException, MigrationException {
		checkMigration(new MigrationFacade(testResourceManager, getResourceName("ImportedModels"), true), "ImportedModelsWithUnusedImports");
	}

	public void testImportedExtensions() throws IOException, MigrationException {
		checkMigration("ImportedExtensions");
	}

	public void testImportedExtensionsWReexport() throws IOException, MigrationException {
		checkMigration("ImportedExtensionsWReexport");
	}

	public void testFeatureCall() throws IOException, MigrationException {
		String resourceName = "FeatureCall";
		checkMigration(new MigrationFacade(testResourceManager, getResourceName(resourceName), new ExecutionContextImpl(testResourceManager, GenModelPackage.eINSTANCE)), resourceName);
	}

	public void testOperationCall() throws IOException, MigrationException {
		checkMigration("OperationCall");
	}
	
	public void testCollectionExpression() throws IOException, MigrationException {
		checkMigration("CollectionExpression");
	}
	
	public void testTypeSelect() throws IOException, MigrationException {
		checkMigration("TypeSelect");
	}

	private String checkMigration(String xtendResourceName) throws IOException, MigrationException {
		return checkMigration(new MigrationFacade(testResourceManager, getResourceName(xtendResourceName)), xtendResourceName);
	}

	private String checkMigration(MigrationFacade facade, String xtendResourceName) throws IOException, MigrationException {
		String content = facade.migrateXtendResource().toString();
		assertTrue(content.length() > 0);

		BufferedReader reader = new BufferedReader(new InputStreamReader(testResourceManager.loadFile(getResourceName(xtendResourceName), "qvto")));
		String etalon = "";
		for (String nextLine = ""; nextLine != null; nextLine = reader.readLine()) {
			etalon += nextLine + " ";
		}
		etalon = normalize(etalon);
		content = normalize(content.toString());
		assertEquals(etalon, content);
		return content;
	}

	private void checkQVTCompilation(String resourceName, String resourceContent) throws MdaException, UnsupportedEncodingException {
		QvtCompiler qvtCompiler = new QvtCompiler(new ImportResolver());
		QvtCompilerOptions options = new QvtCompilerOptions();
		options.setGenerateCompletionData(false);
		options.setShowAnnotations(false);
		CompiledModule module = qvtCompiler.compile(new CFileImpl(resourceName, resourceContent), options, null).getModule();
		assertTrue(module.getErrors().length == 0);
	}

	private static String normalize(String text) {
		text = text.replaceAll("\\s+", " ");
		text = text.trim();
		return text;
	}

	private static String getResourceName(String shortName) {
		return "org::eclipse::gmf::tests::xpand::migration::" + shortName;
	}

}