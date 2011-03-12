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

import com.acme.gwt.data.TvViewer;
import com.google.inject.Provider;

/**
 * Stores the current user. Currently it keeps a ref to the user object, but this might cause 
 * issues, in which case it will probably just keep the id, and get the user each time it is asked.
 * 
 * Any other auth logic or tracking can go here as well, and it might be wise to push the login
 * code here too, or at least let this class deal with it, so that something like OpenID can be
 * wired in easily.
 * 
 * @author colin
 *
 */
public class AuthenticatedViewerProvider implements Provider<TvViewer> {
	private TvViewer viewer;
	@Override
	public TvViewer get() {
		return viewer;
	}
	public void setCurrentViewer(TvViewer viewer) {
		this.viewer = viewer;
	}
}
