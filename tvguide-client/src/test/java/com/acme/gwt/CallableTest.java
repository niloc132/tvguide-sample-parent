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

import java.util.Date;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acme.gwt.data.TvGuideCallFactory;
import com.acme.gwt.data.TvShow;
import com.acme.gwt.server.GwtWebModule;
import com.acme.gwt.server.TvGuideServiceModule;
import com.acme.gwt.server.TvGuideWebServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * @author colin
 *
 */
public class CallableTest {
	public static final Injector i = Guice.createInjector(new TvGuideServiceModule(), new TvGuideWebServiceModule(),new JpaPersistModule("tvgtest"), new GwtWebModule());

	@Before public void setup() {
		i.getInstance(PersistService.class).start();
	}
	@Test
	public void testEpisodesCall() throws Exception {

		TvGuideCallFactory factory = i.getInstance(TvGuideCallFactory.class);

		TestCase.assertEquals(0, factory.findEpisodesByShowAndDateBetween(new TvShow(), new Date(), new Date()).call().size());
	}

	@After public void teardown() {
		i.getInstance(PersistService.class).stop();
	}
}
