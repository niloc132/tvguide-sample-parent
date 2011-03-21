package com.acme.gwt.client.presenter;

import com.acme.gwt.client.view.AboutView;
import com.acme.gwt.client.view.AboutView.Presenter;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class AboutPresenter extends AbstractActivity implements Presenter {
	
	@Inject
	AboutView aboutView;
	
	@Inject
	PlaceController placeController;	

	@Override
	public void goBack() {
		Window.alert("TODO, how can we go 'back' in AboutPresenter.goBack()?'");
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		GWT.log("Start called... yay!");
		panel.setWidget(aboutView);
	}

}