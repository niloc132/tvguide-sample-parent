package com.acme.gwt.server;

import com.acme.gwt.data.TvSetupCallFactory;
import com.acme.gwt.data.TvShow;
import com.google.inject.Inject;

public class TvSetupService {
	@Inject TvSetupCallFactory factory;
	public TvShow saveShow(TvShow tvShow) throws Exception {
		return factory.saveShow(tvShow).call();
	}
}
