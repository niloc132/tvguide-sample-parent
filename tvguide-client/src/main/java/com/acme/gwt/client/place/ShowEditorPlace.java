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
package com.acme.gwt.client.place;

import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

/**
 * @author colin
 *
 */
public class ShowEditorPlace extends EntityPlace<TvShowProxy> {
	public ShowEditorPlace(EntityProxyId<TvShowProxy> showId) {
		super(showId);
	}
	@Prefix("show-edit")
	public static class Tokenizer
			extends
				EntityTokenizer<TvShowProxy, ShowEditorPlace>
			implements
				PlaceTokenizer<ShowEditorPlace> {
		@Override
		public ShowEditorPlace getPlace(EntityProxyId<TvShowProxy> id) {
			return new ShowEditorPlace(id);
		}
	}
}