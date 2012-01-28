package com.acme.gwt.server;

import com.acme.gwt.data.AuthenticationCallFactory;
import com.acme.gwt.data.TvViewer;
import com.google.inject.Inject;

public class TvViewerService {
	@Inject AuthenticationCallFactory factory;
	public TvViewer authenticate(String email, String digest) throws Exception {
		return factory.authenticate(email, digest).call();
	}
	public TvViewer register(String email, String digest) throws Exception {
		return factory.register(email, digest).call();
	}
	public void deauth() throws Exception {
		factory.deauth().call();
	}
}
