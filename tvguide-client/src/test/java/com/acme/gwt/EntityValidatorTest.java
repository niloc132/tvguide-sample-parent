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

import org.junit.Test;

import com.acme.gwt.shared.TvSetupRequest;
import com.acme.gwt.shared.TvChannelProxy.TvChannelRequest;
import com.acme.gwt.shared.TvGuideRequest;
import com.acme.gwt.shared.TvShowProxy.TvShowRequest;
import com.acme.gwt.shared.TvViewerProxy.TvViewerRequest;
import com.google.gwt.requestfactory.server.RequestFactoryInterfaceValidator;
import com.google.gwt.requestfactory.server.RequestFactoryInterfaceValidator.ClassLoaderLoader;
import com.google.gwt.requestfactory.shared.RequestContext;

/**
 * Basic tests to verify that the RF stuff in this project will work, given what the server
 * provides. This stuff will fail until the server matches what tvguide-shared says it has.
 *
 * @author colin
 */
public class EntityValidatorTest {

  @Test
  public void verifyTvGuideRequestAndEntities() {
    assertIsValidRequestContext(TvGuideRequest.class);
  }

  @Test
  public void verifySetupRequestAndEntities() {
    assertIsValidRequestContext(TvSetupRequest.class);
  }

  @Test
  public void verifyTvViewerRequestAndEntities() {
    assertIsValidRequestContext(TvViewerRequest.class);
  }

  @Test
  public void verifyTvShowRequestAndEntities() {
    assertIsValidRequestContext(TvShowRequest.class);
  }

  @Test
  public void verifyTvChannelRequestAndEntities() {
    assertIsValidRequestContext(TvChannelRequest.class);
  }


  private void assertIsValidRequestContext(Class<? extends RequestContext> clazz) {
    Logger logger = Logger.getLogger("");
    RequestFactoryInterfaceValidator v = new RequestFactoryInterfaceValidator(
        logger, new ClassLoaderLoader(clazz.getClassLoader()));
    v.validateRequestContext(clazz.getName());
    assertFalse(v.isPoisoned());
  }
}
