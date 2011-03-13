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
package com.acme.gwt.server.simple;

import com.acme.gwt.server.AuthenticatedViewerProvider.SessionProvider;
import com.google.inject.Singleton;

/**
 * Dirt simple {@link SessionProvider} impl, suitable for testing or batched records only.
 * 
 * @author colin
 *
 */
@Singleton
public class SingletonSessionProvider implements SessionProvider {
	private Long id;
	@Override
	public Long get() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setActiveViewerId(Long viewerId) {
		id = viewerId;
	}
}
