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
package com.acme.gwt.client.ioc;

import com.acme.gwt.client.TvGuide;
import com.acme.gwt.client.place.TvGuidePlaceHistoryMapper.TvGuidePlaceTokenizers;
import com.acme.gwt.client.presenter.AboutPresenter;
import com.acme.gwt.client.presenter.LoginPresenter;
import com.acme.gwt.client.presenter.ShowDetailPresenter;
import com.acme.gwt.client.presenter.WelcomePresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * @author colin
 */
@GinModules({TvGuideClientModule.class})
public interface TvGuideGinjector extends Ginjector, TvGuidePlaceTokenizers {
	/** Provide injection for the entrypoint */
	void inject(TvGuide tvGuide);

	/** Inject stuff into proxied instance - we need this to have access to the RF, app, etc */
	void inject(LoginPresenter proxiedInstance);

	// Provide injection for each Presenter. Consider replacing this mess with assisted inject
	void injectPresenter(WelcomePresenter presenter);
	void injectPresenter(ShowDetailPresenter presenter);

	//...
}
