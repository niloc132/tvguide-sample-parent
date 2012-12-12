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

import java.lang.reflect.Method;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * @author colin
 */
@Singleton
public class InjectableServiceLayerDecorator extends ServiceLayerDecorator {
	@Inject
	Injector injector;
	
	@Inject Logger logger;

	/**
	 * call tiny bootstrap's init before data is used
	 */
	@Inject
	public void setBootstrap(TinyBootstrap bootstrap) {
		bootstrap.go();
	}

	@Override
	public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	@Override
	public <T extends ServiceLocator> T createServiceLocator(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
	
	@Override
	public Object invoke(Method domainMethod, Object... args) {
		try {
			return super.invoke(domainMethod, args);
		} catch (Exception ex) {
			logger.severe(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}
