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

import com.acme.gwt.data.HasVersionAndId;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Simple Locator for entity types, with basic data creation and finding pass along to another
 * service, provided by the server module
 *
 * @author colin
 */
public class InjectingLocator<T extends HasVersionAndId>
		extends
			Locator<T, Long> {
	@Inject
	Provider<EntityManager> data;

	@Inject
	Injector injector;

	@Override
	public T create(Class<? extends T> clazz) {
		T instance = injector.getInstance(clazz);
		data.get().persist(instance);
		return instance;
	}

	@Override
	public T find(Class<? extends T> clazz, Long id) {
		return data.get().find(clazz, id);
	}

	@Override
	public Class<T> getDomainType() {
		throw new UnsupportedOperationException();//unused, and if it becomes used, we're in trouble
	}

	@Override
	public Long getId(T domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Object getVersion(T domainObject) {
		return domainObject.getVersion();
	}
}
