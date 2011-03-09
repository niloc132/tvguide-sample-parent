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

import static junit.framework.Assert.assertFalse;

import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

import com.acme.gwt.shared.SetupRequest;
import com.acme.gwt.shared.TvGuideRequest;
import com.google.gwt.requestfactory.server.RequestFactoryInterfaceValidator;
import com.google.gwt.requestfactory.server.RequestFactoryInterfaceValidator.ClassLoaderLoader;

/**
 * Basic tests to verify that the RF stuff in this project will work, given what the server
 * provides. This stuff will fail until the server matches what tvguide-shared says it has.
 * 
 * @author colin
 *
 */
public class EntityValidatorTest {

	@Test
	@Ignore
	public void verifyTvGuideRequestAndEntities() {
		Logger logger = Logger.getLogger("");
		RequestFactoryInterfaceValidator v = new RequestFactoryInterfaceValidator(
				logger, new ClassLoaderLoader(TvGuideRequest.class.getClassLoader()));
		v.validateRequestContext(TvGuideRequest.class.getName());
		assertFalse(v.isPoisoned());
	}


	@Test
	@Ignore
	public void verifySetupRequestAndEntities() {
		Logger logger = Logger.getLogger("");
		RequestFactoryInterfaceValidator v = new RequestFactoryInterfaceValidator(
				logger, new ClassLoaderLoader(SetupRequest.class.getClassLoader()));
		v.validateRequestContext(SetupRequest.class.getName());
		assertFalse(v.isPoisoned());
	}
}
