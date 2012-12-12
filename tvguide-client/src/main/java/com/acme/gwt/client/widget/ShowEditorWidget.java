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

import com.acme.gwt.client.view.ShowEditorView;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;

/**
 * @author colin
 *
 */
@Singleton
public class ShowEditorWidget extends Composite implements ShowEditorView {
	interface Binder extends UiBinder<Widget, ShowEditorWidget> {
	}
	interface Driver
			extends
				RequestFactoryEditorDriver<TvShowProxy, ShowEditorWidget> {
	}

	private static Binder uiBinder = GWT.create(Binder.class);

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Button save;

	@UiField
	TextBox name;

	@UiField
	TextArea description;

	@UiField
	EditableEpisodeListWidget episodes;

	private Presenter presenter;
	public ShowEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		driver.initialize(this);
	}

	@Override
	public void setPresenter(Presenter p) {
		presenter = p;
	}

	@Override
	public RequestFactoryEditorDriver<TvShowProxy, ?> getEditor() {
		return driver;
	}

	@Override
	public void setIsSaved(boolean saved) {
		save.setText(saved ? "Saved" : "Save");
		save.setEnabled(!saved);
	}

	@UiHandler("back")
	void onBackClicked(ClickEvent evt) {
		presenter.back();
	}
	@UiHandler("save")
	void onSaveClicked(ClickEvent evt) {
		//workaround for late blur in celltable
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				presenter.save();
			}
		});
	}
}
