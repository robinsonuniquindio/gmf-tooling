/*
 * Copyright (c) 2005 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.graphdef.codegen;

import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.gmf.common.UnexpectedBehaviourException;
import org.eclipse.gmf.common.codegen.NullImportAssistant;
import org.eclipse.gmf.gmfgraph.CustomFigure;
import org.eclipse.gmf.gmfgraph.DecorationFigure;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.PolylineConnection;
import org.eclipse.gmf.gmfgraph.Shape;
import org.eclipse.gmf.graphdef.codegen.templates.ConnectionGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.CustomFigureGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.DecorationFigureGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.LabelGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.ShapeAttrsGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.ShapeGenerator;
import org.eclipse.gmf.internal.graphdef.codegen.DispatcherImpl;
import org.eclipse.gmf.internal.graphdef.codegen.NoSuchTemplateException;
import org.eclipse.gmf.internal.graphdef.codegen.StaticTemplateRegistry;
import org.eclipse.gmf.internal.graphdef.codegen.TemplateRegistry;
import org.eclipse.gmf.internal.graphdef.codegen.YAEmitterFactory;
import org.osgi.framework.Bundle;

/**
 * @author artem
 *
 */
public class FigureGenerator {
	private final String packageName;
	private YAEmitterFactory myFactory;
	private Dispatcher myDispatcher;

	public FigureGenerator() {
		this(null);
	}

	public FigureGenerator(String aPackageName) {
		packageName = aPackageName;
		final Bundle thisBundle = Platform.getBundle("org.eclipse.gmf.graphdef.codegen");
		final ArrayList variables = new ArrayList();
		variables.add("org.eclipse.gmf.graphdef");
		variables.add("org.eclipse.emf.ecore");
		variables.add("org.eclipse.emf.common");
		variables.add("org.eclipse.gmf.common");
		variables.add("org.eclipse.gmf.graphdef.codegen");

		myFactory = new YAEmitterFactory(thisBundle.getEntry("/"), fill(), true, variables, true);
		myDispatcher = new DispatcherImpl(myFactory);
	}

	/**
	 * @return possibly <code>null</code>
	 */
	public String getPackageStatement() {
		return packageName;
	}

	private static TemplateRegistry fill() {
		StaticTemplateRegistry tr = new StaticTemplateRegistry();
		tr.put(PolylineConnection.class, "/templates/PolylineConnection.javajet", ConnectionGenerator.class);
		tr.put(DecorationFigure.class, "/templates/DecorationFigure.javajet", DecorationFigureGenerator.class);
		tr.put(Shape.class, "/templates/ConcreteShape.javajet", ShapeGenerator.class);
		tr.put(Label.class, "/templates/Label.javajet", LabelGenerator.class);
		tr.put(CustomFigure.class, "/templates/CustomFigure.javajet", CustomFigureGenerator.class);
		tr.put("ShapeAttrs", "/templates/ShapeAttrs.javajet", ShapeAttrsGenerator.class);
		return tr;
	}

	public String go(Figure fig) throws JETException {
		String res = null;
		try {
		if (fig instanceof PolylineConnection) {
			res = generate(fig, myFactory.acquireEmitter(PolylineConnection.class));
		} else if (fig instanceof DecorationFigure) {
			res = generate(fig, myFactory.acquireEmitter(DecorationFigure.class));
		} else if (fig instanceof Shape) {
			res = generate(fig, myFactory.acquireEmitter(Shape.class));
		} else if (fig instanceof CustomFigure) {
			res = generate(fig, myFactory.acquireEmitter(CustomFigure.class));
		} else if (fig instanceof Label) {
			res = generate(fig, myFactory.acquireEmitter(Label.class));
		}
// TODO: } else if (fig instanceof LabeledContainer) {
		if (res == null) {
			throw new IllegalStateException();
		}
		} catch (UnexpectedBehaviourException ex) {
			throw new IllegalStateException(ex);
		} catch (NoSuchTemplateException ex) {
			throw new IllegalStateException(ex);
		}
		return packageName == null ? res : "package " + packageName + ";\n" + res;
	}

	private String generate(Figure fig, JETEmitter emitter) throws JETException {
		Object argument = new Object[] {fig, new NullImportAssistant(), myDispatcher};
		return emitter.generate(new BasicMonitor.Printing(System.out), new Object[] {argument});
	}
}
