package com.acme.gwt.client;

import com.acme.gwt.shared.TvChannelProxy;
import com.acme.gwt.shared.TvGuideRequest;
import com.acme.gwt.shared.TvShowProxy;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.requestfactory.shared.RequestFactory;


/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 4:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MyRequestFactory extends RequestFactory {

  TvGuideRequest reqGuide();

  TvChannelProxy.TvChannelRequest reqChannel();

  TvShowProxy.TvShowRequest reqShow();

  TvViewerProxy.TvViewerRequest reqViewer();
}
