/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.internal.graphdef.codegen;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.gmf.common.UnexpectedBehaviourException;

/**
 * Yet Another Emitter Factory
 * 
 * @author artem
 */
public class YAEmitterFactory {

	private final URL myBaseURL;

	private final TemplateRegistry myTemplates;

	private final boolean myUsePrecompiled;

	private final List/* <String> */myVariables;

	private final Map myCache;

	/**
	 * FIXME variables - either String[] or wrap as UnmodifiableList
	 * 
	 * @param baseURL
	 * @param templates
	 * @param usePrecompiled
	 * @param variables
	 */
	public YAEmitterFactory(URL baseURL, TemplateRegistry templates, boolean usePrecompiled, List/* <String> */variables, boolean cache) {
		assert baseURL != null && templates != null;
		assert variables == null || !variables.contains(null);
		myBaseURL = baseURL;
		myTemplates = templates;
		myUsePrecompiled = usePrecompiled;
		myVariables = variables == null ? Collections.EMPTY_LIST : variables;
		if (cache) {
			myCache = createCache();
		} else {
			myCache = null;
		}
	}

	/**
	 * Instantiates {@link HashMap} by default. NOTE, this method is invoked
	 * from constructor, object may not be fully initialized, don't use this
	 * method for anything but cache instantiation.
	 */
	protected Map/* <Object, JETEmitter> */createCache() {
		return new HashMap();
	}

	/**
	 * This is primary way to get emitters from this factory. 
	 * Checks cache (if there's one) first. Produces new emitter (with {@link #newEmitter(Object)}), caches and returns its outcome.
	 */
	public JETEmitter acquireEmitter(Object key) throws JETException, NoSuchTemplateException, UnexpectedBehaviourException {
		JETEmitter em = checkCache(key);
		if (em != null) {
			return em;
		}
		em = newEmitter(key);
		cache(key, em);
		return em;
	}

	/**
	 * Explicit way to produce new instance of emitter, passing over cache (if any).
	 */
	public JETEmitter newEmitter(Object key) throws UnexpectedBehaviourException, NoSuchTemplateException, JETException {
		JETEmitter em;
		String fullPath = constructPath(key);
		ClassLoader cl;
		if (precompiledInUse(key)) {
			cl = myTemplates.getGeneratorClass(key).getClassLoader();
		} else {
			cl = getClass().getClassLoader();
		}
		em = new JETEmitter(fullPath, cl);
		feedVariables(em);
		initPrecompiled(key, em);
		return em;
	}

	private boolean precompiledInUse(Object key) {
		return myUsePrecompiled && myTemplates.hasGeneratorClass(key);
	}

	private void initPrecompiled(Object key, JETEmitter em) throws UnexpectedBehaviourException {
		try {
			if (precompiledInUse(key)) {
				Method m = myTemplates.getGeneratorClass(key).getMethod("generate", new Class[] { Object.class });
				em.setMethod(m);
			}
		} catch (NoSuchMethodException ex) {
			throw new UnexpectedBehaviourException("Bad template class", ex);
		}
	}

	private String constructPath(Object key) throws UnexpectedBehaviourException, NoSuchTemplateException {
		try {
			String path = myTemplates.getTemplatePath(key);
			if (path == null) {
				throw new NoSuchTemplateException(String.valueOf(key));
			}
			return new URL(myBaseURL, path).toString();
		} catch (MalformedURLException ex) {
			throw new UnexpectedBehaviourException(ex);
		}
	}

	private void feedVariables(JETEmitter em) throws JETException {
		for (Iterator it = myVariables.iterator(); it.hasNext();) {
			em.addVariable(null, (String) it.next());
		}
	}

	private JETEmitter checkCache(Object key) {
		if (myCache != null) {
			return (JETEmitter) myCache.get(key);
		}
		return null;
	}

	private void cache(Object key, JETEmitter emitter) {
		if (myCache != null) {
			myCache.put(key, emitter);
		}
	}
}
