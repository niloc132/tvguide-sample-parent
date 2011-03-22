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
package com.acme.gwt.client.view;

import com.acme.gwt.shared.TvEpisodeProxy;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author colin
 *
 */
public interface ShowDetailView extends IsWidget, Editor<TvShowProxy> {
	void setPresenter(Presenter presenter);
	RequestFactoryEditorDriver<TvShowProxy, ?> getEditor();

	public interface Presenter {
		void focusEpisode(TvEpisodeProxy episode);
		void back();
	}
}
