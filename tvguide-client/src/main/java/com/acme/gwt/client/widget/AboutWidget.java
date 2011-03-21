package com.acme.gwt.client.widget;

import com.acme.gwt.client.view.AboutView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutWidget extends Composite implements AboutView {

	private static AboutWidgetUiBinder uiBinder = GWT
			.create(AboutWidgetUiBinder.class);

	interface AboutWidgetUiBinder extends UiBinder<Widget, AboutWidget> {
	}
	
	private Presenter presenter;

	public AboutWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button backButton;

	@UiHandler("backButton")
	void onClick(ClickEvent e) {
		presenter.goBack();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}