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

import com.acme.gwt.client.view.ShowDetailView;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * @author colin
 *
 */
@Singleton
public class ShowDetailWidget extends Composite implements ShowDetailView {
	interface Binder extends UiBinder<Widget, ShowDetailWidget> {
	}
	interface Driver
			extends
				RequestFactoryEditorDriver<TvShowProxy, ShowDetailWidget> {
	}

	private static Binder uiBinder = GWT.create(Binder.class);

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label name;
	@UiField
	Label description;

	@UiField
	ScrollPanel details;

	@UiField
	EditableEpisodeListWidget episodes;

	private Presenter p;
	public ShowDetailWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.p = presenter;
	}

	@Override
	public RequestFactoryEditorDriver<TvShowProxy, ?> getEditor() {
		return driver;
	}

	@UiHandler("back")
	void onBackClick(ClickEvent evt) {
		p.back();
	}
}
