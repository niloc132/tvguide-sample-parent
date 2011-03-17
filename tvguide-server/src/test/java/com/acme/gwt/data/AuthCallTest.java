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
package com.acme.gwt.data;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.gwt.server.AuthenticatedViewerProvider;
import com.acme.gwt.server.AuthenticatedViewerProvider.SessionProvider;
import com.acme.gwt.server.TinyBootstrap;
import com.acme.gwt.server.TvGuideServiceModule;
import com.acme.gwt.server.simple.SingletonSessionProvider;
import com.acme.gwt.shared.util.Md5;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * @author colin
 *
 */
public class AuthCallTest {
	private static final Injector i = Guice.createInjector(
			new TvGuideServiceModule(), new TestModule(), new JpaPersistModule(
					"tvgtest"));
	static class TestModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(SessionProvider.class).to(SingletonSessionProvider.class)
					.asEagerSingleton();
		}
	}
	@BeforeClass
	public static void setup() {
		i.getInstance(PersistService.class).start();
	}
	@Before
	public void startWorkUnit() {
		i.getInstance(EntityManager.class).getTransaction().begin();
		i.getInstance(TinyBootstrap.class).go();
	}

	@Test
	public void testValidAuth() throws Exception {
		AuthenticationCallFactory factory = i
				.getInstance(AuthenticationCallFactory.class);
		Assert.assertNotNull(factory.authenticate("you@example.com",
				Md5.md5Hex("sa")).call());

		Assert.assertNotNull(i.getInstance(AuthenticatedViewerProvider.class)
				.get());
	}

	@Test
	public void testInvalidAuth() throws Exception {
		AuthenticationCallFactory factory = i
				.getInstance(AuthenticationCallFactory.class);
		try {
			factory.authenticate("you@example.com", Md5.md5Hex("asdfdsfa"))
					.call();
			Assert.fail("Login error should have occured");
		} catch (Exception ex) {
			Assert.assertNull(i.getInstance(AuthenticatedViewerProvider.class)
					.get());
		}

		try {
			factory.authenticate("you@fdsfd", Md5.md5Hex("sa")).call();
			Assert.fail("Login error should have occured");
		} catch (Exception ex) {
			Assert.assertNull(i.getInstance(AuthenticatedViewerProvider.class)
					.get());
		}
	}

	@Test
	public void testDeauth() throws Exception {
		//first, log in
		AuthenticationCallFactory factory = i
				.getInstance(AuthenticationCallFactory.class);
		Assert.assertNotNull(factory.authenticate("you@example.com",
				Md5.md5Hex("sa")).call());

		//then, try deauth, from a new factory (not that this should matter...)
		factory = i.getInstance(AuthenticationCallFactory.class);
		factory.deauth().call();
	}

	@After
	public void endWorkUnit() {
		i.getInstance(EntityManager.class).getTransaction().rollback();
	}

	@AfterClass
	public static void teardown() {
		i.getInstance(PersistService.class).stop();
	}
}
