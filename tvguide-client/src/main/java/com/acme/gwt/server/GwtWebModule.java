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
import com.google.inject.servlet.ServletModule;

/**
 * @author colin
 */
public class GwtWebModule extends ServletModule {
	private final String requestFactoryPath;

	public GwtWebModule() {
		this("/" + DefaultRequestTransport.URL);
	}

	public GwtWebModule(String requestFactoryPath) {
		this.requestFactoryPath = requestFactoryPath;
	}

	@Override
	protected void configureServlets() {
		serve(requestFactoryPath).with(RequestFactoryServlet.class);
		serve("/").with(HomepageServlet.class);
		filter("/*").through(PersistFilter.class);
	}

	@Inject
	@Singleton
	@Provides
	RequestFactoryServlet provideRequestFactoryServlet(
			InjectableServiceLayerDecorator injectableSLD) {
		return new RequestFactoryServlet(new DefaultExceptionHandler(),
				injectableSLD);
	}
}
