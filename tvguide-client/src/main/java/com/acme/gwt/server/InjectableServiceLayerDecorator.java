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
import java.util.concurrent.Callable;

import com.google.gwt.requestfactory.server.ServiceLayerDecorator;
import com.google.gwt.requestfactory.shared.Locator;
import com.google.gwt.requestfactory.shared.ServiceLocator;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author colin
 */
@Singleton
public class InjectableServiceLayerDecorator extends ServiceLayerDecorator {
	@Inject
	Injector injector;

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
	public Object createServiceInstance(Method contextMethod,
			Method domainMethod) {
		// Check if the request needs a service locator
		Class<? extends ServiceLocator> locatorType = getTop()
				.resolveServiceLocator(contextMethod, domainMethod);
		assert locatorType != null;

		// Inject an instance of the locator itself, and then get an instance from it
		ServiceLocator locator = injector.getInstance(locatorType);
		return locator.getInstance(domainMethod.getDeclaringClass());
	}

	@Override
	public Object invoke(Method domainMethod, Object... args) {
		Object response = super.invoke(domainMethod, args);
		try {
			if (response instanceof Callable) {
				return ((Callable<?>) response).call();
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
