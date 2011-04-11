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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Visual wrapper for the rest of the running app. 
 * 
 * @author colin
 */
@Singleton
public class TvGuideAppShell extends Composite implements HasOneWidget {
	interface Binder extends UiBinder<Widget, TvGuideAppShell> {
	}
	private Binder uiBinder = GWT.create(Binder.class);
	
	private static final Logger logger = Logger.getLogger(TvGuideAppShell.class.getName());

	@UiField
	LayoutPanel display;
	
	@UiField
	MenuItem profileMenuItem, logoutMenuItem, aboutMenuItem, helpMenuItem;
	
	@Inject
	public TvGuideAppShell(ActivityManager activityManager, TvViewerProxy user) {
		initWidget(uiBinder.createAndBindUi(this));
		activityManager.setDisplay(this);
		//set all the menuItem Commands.. do stuff when they are clicked.
		profileMenuItem.setCommand(new Command() {
			@Override
			public void execute() {
				logger.log(Level.FINE,"TODO, fire the real command...");
			}
		});
		logoutMenuItem.setCommand(new Command() {
			@Override
			public void execute() {
				logger.log(Level.FINE,"TODO, fire the real command...");
			}
		});
		aboutMenuItem.setCommand(new Command() {
			@Override
			public void execute() {
				logger.log(Level.FINE,"TODO, fire the real command...");
			}
		});
		helpMenuItem.setCommand(new Command() {
			@Override
			public void execute() {
				logger.log(Level.FINE,"TODO, fire the real command...");
			}
		});
	}

	@Override
	public Widget getWidget() {
		return display.getWidgetCount() == 0 ? null : display.getWidget(0);
	}

	@Override
	public void setWidget(Widget widget) {
		display.clear();
		if (widget != null) {
			display.add(widget);
		}
	}

	public void setWidget(IsWidget w) {
		setWidget(w == null ? null : w.asWidget());
	}
}
