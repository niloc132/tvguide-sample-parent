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
package com.acme.gwt.client.bootstrap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author colin
 *
 */
public class LoginWidget extends Widget implements ClickHandler {
	interface Binder extends UiBinder<Element, LoginWidget> {
	}
	private Binder uiBinder = GWT.create(Binder.class);

	@UiField
	InputElement email;
	@UiField
	InputElement password;
	@UiField
	ButtonElement login;
	@UiField
	ButtonElement register;
	private final Presenter presenter;

	public LoginWidget(Presenter presenter) {
		setElement(uiBinder.createAndBindUi(this));

		// Listen to all dom elts for clicks, handler function will deal with the useful ones
		this.addDomHandler(this, ClickEvent.getType());

		// Keep a reference to the presenter, and make them aware of the widget
		// this will allow the presenter to hide the login widget
		this.presenter = presenter;
		presenter.setView(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		// Check if the incoming target is part of either of the buttons
		Node target = event.getNativeEvent().getEventTarget().<Node> cast();
		if (login.isOrHasChild(target)) {
			presenter.login(email.getValue(), password.getValue());
		} else if (register.isOrHasChild(target)) {
			presenter.register(email.getValue(), password.getValue());
		}
	}

	// kick this outta here
	public interface Presenter {
		void setView(LoginWidget w);
		void login(String email, String password);
		void register(String email, String password);
	}
}
