/*
 * Copyright (c) 2006 Eclipse.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dmitry Stadnik - initial API and implementation
 */
package org.eclipse.gmf.internal.codegen.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.internal.codegen.resolver.StructureBuilder;
import org.eclipse.gmf.internal.codegen.resolver.StructureResolver;
import org.eclipse.gmf.tooldef.presentation.GMFToolModelWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class GMFToolSimpleModelWizard extends GMFToolModelWizard {

	protected DefinitionPage toolDefinitionPage;

	protected FileDomainModelSource domainModelSource = new FileDomainModelSource();

	protected EObject createInitialModel() {
		ToolDefBuilder builder = new ToolDefBuilder();
		return builder.process(toolDefinitionPage.getModel());
	}

	public void addPages() {
		super.addPages();

		if (selection != null && !selection.isEmpty()) {
			Object selected = selection.getFirstElement();
			if (selected instanceof IFile) {
				newFileCreationPage.setFileName(WizardUtil.getDefaultFileName((IFile) selected, "gmftool"));
			}
		}

		initialObjectCreationPage.dispose();
		initialObjectCreationPage = new PredefinedInitialObjectCreationPage("Whatever2");

		DomainModelSelectionPage domainModelSelectionPage = new DomainModelSelectionPage("DomainModelSelectionPage", selection, domainModelSource);
		domainModelSelectionPage.setTitle("Domain Model");
		domainModelSelectionPage.setDescription("Select file with ecore domain model");
		addPage(domainModelSelectionPage);

		toolDefinitionPage = new DefinitionPage("ToolDefinitionPage", new StructureBuilder(new StructureResolver()), domainModelSource);
		toolDefinitionPage.setTitle("Tooling Definition");
		toolDefinitionPage.setDescription("Specify basic tooling definition of the domain model");
		addPage(toolDefinitionPage);
	}

	public void addPage(IWizardPage page) {
		if (page == initialObjectCreationPage) {
			return;
		}
		super.addPage(page);
	}

	public class PredefinedInitialObjectCreationPage extends GMFToolModelWizardInitialObjectCreationPage {

		public PredefinedInitialObjectCreationPage(String pageId) {
			super(pageId);
		}

		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			setControl(composite);
		}

		protected boolean validatePage() {
			return true;
		}

		public void setVisible(boolean visible) {
			getControl().setVisible(visible);
		}

		public String getInitialObjectName() {
			return gmfToolPackage.getToolRegistry().getName();
		}

		public String getEncoding() {
			return "UTF-8";
		}
	}
}
