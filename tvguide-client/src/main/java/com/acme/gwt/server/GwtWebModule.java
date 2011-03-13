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

import com.google.gwt.requestfactory.client.DefaultRequestTransport;
import com.google.gwt.requestfactory.server.DefaultExceptionHandler;
import com.google.gwt.requestfactory.server.RequestFactoryServlet;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

/**
 * @author colin
 */
public class GwtWebModule extends ServletModule {
	private final String requestFactoryPath;
	private boolean scopingIsABetterSolutionThanThisFlag = false;

	public GwtWebModule() {
		this("/" + DefaultRequestTransport.URL);
	}

	public GwtWebModule(String requestFactoryPath) {
		this.requestFactoryPath = requestFactoryPath;
	}

	@Override
	protected void configureServlets() {
		serve(requestFactoryPath).with(RequestFactoryServlet.class);
		install(new JpaPersistModule("tvgtest")); // like we saw earlier.
		filter("/*").through(PersistFilter.class);

	}

	@Inject
	@Singleton
	@Provides
	RequestFactoryServlet provideRequestFactoryServlet(
			InjectableServiceLayerDecorator injectableSLD) {

		//the goal here was to find some injected method and put a one-time
		// bootstrap call with a sync guard because im unclear on the lifecycle
		// spot best suited.

		//@Inject static BootStrap forgetMeAfterUse;
		// would also work, sort of.

		/*
		   if (!scopingIsABetterSolutionThanThisFlag)
		    synchronized (getClass()) {
		       if (!scopingIsABetterSolutionThanThisFlag) {
		         scopingIsABetterSolutionThanThisFlag = true;
		         injectableSLD.injector.getInstance(JpaBootstrap.class);
		       }
		     }*/
		return new RequestFactoryServlet(new DefaultExceptionHandler(),
				injectableSLD);
	}
}
