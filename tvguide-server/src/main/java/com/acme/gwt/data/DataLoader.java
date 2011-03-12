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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Simple window into the data store, whatever that is. This should be made into an interface, with
 * a proper impl somewhere.
 *
 * @author colin
 */
public class DataLoader {

  //  { /*one-time use of these statements to entice intellij
  // to add maven deps, just uncomment*/
  //    org.apache.openjpa.jdbc.sql.H2Dictionary h2;
  //    com.mysql.jdbc.Driver mysql;
  //  }

  @Inject
  Injector injector;
  @Inject
  Provider<EntityManager> emProvider;

  public <T extends HasVersionAndId> T create(Class<? extends T> clazz) {
    return injector.getInstance(clazz);
  }

  public <T extends HasVersionAndId> T find(Class<? extends T> clazz, Long id) {
    return emProvider.get().find(clazz, id);
  }
}
