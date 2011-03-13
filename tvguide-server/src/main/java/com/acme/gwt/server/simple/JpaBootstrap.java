package com.acme.gwt.server.simple;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 1:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class JpaBootstrap {
	@Inject
	PersistService service;

	public JpaBootstrap() {
		service.start();
	}
}
