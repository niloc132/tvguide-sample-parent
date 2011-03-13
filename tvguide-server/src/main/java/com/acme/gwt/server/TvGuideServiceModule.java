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

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.not;
import static com.google.inject.matcher.Matchers.subclassesOf;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.acme.gwt.AuthenticatedViewerProvider;
import com.acme.gwt.data.TvViewer;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.finder.Finder;

/**
 * @author colin
 */
public class TvGuideServiceModule extends AbstractModule {
	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("tvgtest");

	@Override
	protected void configure() {
		bind(TvViewer.class).toProvider(AuthenticatedViewerProvider.class);

		//workaround for TvViewer which wants a static reference to a EntityManager provider
		// from the wiki page ...
		// Methods that return a Request object in the
		// client interface are implemented as static methods on the entity.
		// Alternatively, they may be implemented as instance methods in a
		// service returned by a ServiceLocator.
		// no longer requires statics, i changed the request to the injectingslo,
		//  -jn

		requestStaticInjection(TvViewer.class);

		Matcher<Object> disabled = not(any());
		bindInterceptor(disabled, Matchers.annotatedWith(Transactional.class),
				new MethodInterceptor() {
					@Override
					public Object invoke(MethodInvocation invocation)
							throws Throwable {
						//em.begin()
						Object ret = null;
						//ret=invoke()
						//em.commit()
						//rollback errors
						//releaseAll/evictAll/detachAll if necessary
						//cache and reuse unclosed em

						return ret; //todo: review for a purpose
					}
				});

		bindInterceptor(disabled, Matchers
				.returns(subclassesOf(EntityManager.class)),
				new MethodInterceptor() {
					@Override
					public Object invoke(MethodInvocation invocation)
							throws Throwable {
						//return emf matching requested value or default
						// createEmf(null).createEntityManager

						return null; //todo: review for a purpose
					}
				});
		bindInterceptor(disabled, annotatedWith(Finder.class),
				new MethodInterceptor() {
					@Override
					public Object invoke(MethodInvocation invocation)
							throws Throwable {
						//parse, split on case change, decode expression, and generate oql....
						//then find @Named params per something to send into finder params

						return null; //todo: review for a purpose
					}
				});
	}

	@Provides
	EntityManagerFactory provideEMF() {
		return emf;
	}

	/**
	 * Gets an instance of a EntityManager, and will only be called once per servlet Request
	 *
	 * @param emf
	 * @return
	 */
	@Provides
	EntityManager provideEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}
	public interface UnscopedEntityManagerProvider
			extends
				Provider<EntityManager> {

	}
}
