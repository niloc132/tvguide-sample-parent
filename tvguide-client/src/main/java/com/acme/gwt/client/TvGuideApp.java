package com.acme.gwt.client;

import com.acme.gwt.client.widget.TvGuideAppShell;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;

/**
 * Start the app up and running with the given user.
 */
public class TvGuideApp {
	@Inject
	TvGuideAppShell shell;
	@Inject
	PlaceHistoryHandler historyHandler;

	public void setUser(TvViewerProxy user) {
		// Attach the root view to the page
		RootLayoutPanel.get().add(shell);

		// Go! Fire the current history token
		historyHandler.handleCurrentHistory();
	}
}
