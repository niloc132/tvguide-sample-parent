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

import javax.persistence.EntityManager;

import com.acme.gwt.data.TvViewer;
import com.google.inject.AbstractModule;

/**
 * @author colin
 */
public class TvGuideServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		// TvViewers will be handed out by this, depending on how auth is scoped
		bind(TvViewer.class).toProvider(AuthenticatedViewerProvider.class);

		// Ensure that something has session stuff ready
		requireBinding(AuthenticatedViewerProvider.class);

		// Ensure that something is bringing a EntityManager to the party
		requireBinding(EntityManager.class);

		// TvViewer must be statically injected, otherwise it has no access to the great 
		// Guicy world of data
		requestStaticInjection(TvViewer.class);
	}
}
