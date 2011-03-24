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
package com.acme.gwt.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.acme.gwt.data.TvViewer;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.autobean.shared.AutoBean;
import com.google.gwt.autobean.shared.AutoBeanCodex;
import com.google.gwt.autobean.shared.AutoBeanUtils;
import com.google.gwt.autobean.shared.Splittable;
import com.google.gwt.requestfactory.server.ServiceLayer;
import com.google.gwt.requestfactory.server.SimpleRequestProcessor;
import com.google.gwt.requestfactory.shared.DefaultProxyStore;
import com.google.gwt.requestfactory.shared.impl.MessageFactoryHolder;
import com.google.gwt.requestfactory.shared.messages.IdMessage;
import com.google.gwt.requestfactory.shared.messages.IdMessage.Strength;
import com.google.gwt.requestfactory.shared.messages.InvocationMessage;
import com.google.gwt.requestfactory.shared.messages.MessageFactory;
import com.google.gwt.requestfactory.shared.messages.OperationMessage;
import com.google.gwt.requestfactory.shared.messages.RequestMessage;
import com.google.gwt.requestfactory.shared.messages.ResponseMessage;
import com.google.gwt.user.server.Base64Utils;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author colin
 *
 */
public class TvViewerJsonBootstrap {
	@Inject
	InjectableServiceLayerDecorator isld;
	@Inject
	Provider<TvViewer> viewerProvider;

	public String getViewerAsJson() {
		SimpleRequestProcessor p = new SimpleRequestProcessor(ServiceLayer
				.create(isld));
		TvViewer user = viewerProvider.get();
		if (user == null) {
			return "";
		}

		MessageFactory factory = MessageFactoryHolder.FACTORY;
		AutoBean<IdMessage> id = factory.id();
		try {
			id.as().setServerId(
					Base64Utils.toBase64(user.getId().toString().getBytes(
							"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		id.as().setStrength(Strength.PERSISTED);
		id.as().setTypeToken(TvViewerProxy.class.getName());

		AutoBean<RequestMessage> msg = factory.request();
		msg.as().setInvocations(new ArrayList<InvocationMessage>());
		InvocationMessage invocation = factory.invocation().as();
		invocation
				.setOperation("com.google.gwt.requestfactory.shared.impl.FindRequest::find");
		invocation.setParameters(new ArrayList<Splittable>());
		invocation.getParameters().add(AutoBeanCodex.encode(id));
		msg.as().getInvocations().add(invocation);

		String value = p.process(AutoBeanCodex.encode(msg).getPayload());

		ResponseMessage response = AutoBeanCodex.decode(factory,
				ResponseMessage.class, value).as();
		OperationMessage opMsg = response.getOperations().get(0);
		DefaultProxyStore store = new DefaultProxyStore();
		store.put(TvViewerProxy.STORE_KEY, AutoBeanCodex.encode(AutoBeanUtils
				.getAutoBean(opMsg)));

		return store.encode();
	}
}
