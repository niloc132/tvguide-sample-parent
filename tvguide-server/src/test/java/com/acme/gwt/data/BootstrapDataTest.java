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

import org.junit.Test;

import com.acme.gwt.server.Bootstrap;
import com.acme.gwt.server.TvGuideServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Tests that the Bootstrap script can do something. In the event that we have the persistence.xml
 * set to hand out something other than h2:mem, this will need to find a way to hand off an
 * EntityManager that can be tested.
 * 
 * @author colin
 *
 */
public class BootstrapDataTest {
	private static final Injector i = Guice
			.createInjector(new TvGuideServiceModule());

	@Test
	public void bootstrapTest() {
		i.getInstance(Bootstrap.class).init();
	}
}
