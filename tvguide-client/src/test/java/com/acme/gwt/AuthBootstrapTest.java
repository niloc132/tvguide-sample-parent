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
package com.acme.gwt;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acme.gwt.data.AuthenticationCallFactory;
import com.acme.gwt.server.AuthenticatedViewerProvider.SessionProvider;
import com.acme.gwt.server.GwtWebModule;
import com.acme.gwt.server.TvGuideServiceModule;
import com.acme.gwt.server.TvViewerJsonBootstrap;
import com.acme.gwt.server.simple.SingletonSessionProvider;
import com.acme.gwt.shared.TvViewerProxy;
import com.acme.gwt.shared.util.Md5;
import com.google.gwt.requestfactory.shared.DefaultProxyStore;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * @author colin
 *
 */
public class AuthBootstrapTest {
	public static final Injector i = Guice.createInjector(
			new TvGuideServiceModule(), new JpaPersistModule("tvgtest"),
			new GwtWebModule(), new TestModule());

	static class TestModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(SessionProvider.class).to(SingletonSessionProvider.class);
		}
	}
	@Before
	public void setup() {
		i.getInstance(PersistService.class).start();

	}

	@Test
	public void testCreateJsonAuth() throws Exception {

		TvViewerJsonBootstrap bootstrap = i
				.getInstance(TvViewerJsonBootstrap.class);
		AuthenticationCallFactory factory = i
				.getInstance(AuthenticationCallFactory.class);
		factory.authenticate("you@example.com", Md5.md5Hex("sa")).call();

		String json = bootstrap.getViewerAsJson();
		System.out.println(json);
		DefaultProxyStore store = new DefaultProxyStore(json);
		Assert.assertNotNull(store.get(TvViewerProxy.STORE_KEY));
	}

	@After
	public void teardown() {
		i.getInstance(PersistService.class).stop();
	}
}
