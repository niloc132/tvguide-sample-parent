/**
 *  Copyright 2011 Colin Alworth
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.acme.gwt.server;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Callable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletScopes;

/**
 * @author colin
 *
 */
@Singleton
public class EMBootstrapFilter implements Filter {
	@Inject
	Provider<Bootstrap> bootstrap;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		arg2.doFilter(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//pretend we've got a Request scope to let this work
		try {
			ServletScopes.scopeRequest(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					bootstrap.get().init();
					return null;
				}
			}, Collections.EMPTY_MAP).call();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
