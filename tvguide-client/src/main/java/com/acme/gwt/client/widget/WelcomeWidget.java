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
package com.acme.gwt.client.widget;

import java.util.List;

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.view.WelcomeView;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author colin
 *
 */
@Singleton
public class WelcomeWidget extends Composite
		implements
			RequiresResize,
			WelcomeView {
	interface FavoritesDriver
			extends
				RequestFactoryEditorDriver<List<TvShowProxy>, FavoriteShowsListWidget> {
	}

	FavoritesDriver driver = GWT.create(FavoritesDriver.class);
	@Inject
	public WelcomeWidget(TvGuideRequestFactory rf,
			FavoriteShowsListWidget listView) {
		LayoutPanel panel = new LayoutPanel();
		initWidget(panel);

		panel
				.add(new Label(
						"Welcome, rest of app will be here soon. (Delivery in 4-6 weeks)"));
		panel.add(listView);
		//BAD: all layout done by hand, and quick, without UiBinder's help or CssResource
		panel.setWidgetTopHeight(listView, 0, Unit.PX, 100, Unit.PCT);
		listView.getElement().setAttribute("margin-top", "25px");

		// Attach the data - easy way to bind a views editor to something accessible from a presenter
		driver.initialize(rf, listView);
	}

	public RequestFactoryEditorDriver<List<TvShowProxy>, ?> getDriver() {
		return driver;
	}

	@Override
	public void onResize() {
		//?
	}

	@Override
	public void setPresenter(Presenter p) {
		// useless, ignore it
	}
}
