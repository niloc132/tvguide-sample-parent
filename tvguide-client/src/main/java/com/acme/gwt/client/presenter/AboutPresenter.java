package com.acme.gwt.client.presenter;

import com.acme.gwt.client.view.AboutView;
import com.acme.gwt.client.view.AboutView.Presenter;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class AboutPresenter extends AbstractActivity implements Presenter {
	
	private final AboutView view;

	@Inject
	public AboutPresenter(final AboutView view){
		this.view = view;
		view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
		//History.newItem("1234");
	}

	@Override
	public void goBack() {
		GWT.log("AboutPresenter.goBack() has been invoked...");
		History.back();
	}

}