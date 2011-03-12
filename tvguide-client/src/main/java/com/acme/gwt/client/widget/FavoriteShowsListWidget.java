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
package com.acme.gwt.client.widget;

import java.util.List;

import com.acme.gwt.shared.TvShowProxy;
import com.colinalworth.celltable.columns.client.Columns;
import com.colinalworth.celltable.columns.client.HasDataFlushableEditor;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author colin
 * @TODO consider pushing the basics of this to a shows list widget, and wrapping it for use for
 * Favorites
 */
public class FavoriteShowsListWidget extends Composite
		implements
			Editor<List<TvShowProxy>> {
	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, FavoriteShowsListWidget> {
	}

	interface ShowColumns extends Columns<TvShowProxy> {
		TextCell name();

		TextCell description();
	}

	private ShowColumns columns = GWT.create(ShowColumns.class);
	@Path("")
	HasDataFlushableEditor<TvShowProxy> listEditor;
	@UiField(provided = true)
	CellTable<TvShowProxy> list = new CellTable<TvShowProxy>();

	public FavoriteShowsListWidget() {
		listEditor = HasDataFlushableEditor.of(list);

		columns.configure(list, listEditor);

		initWidget(uiBinder.createAndBindUi(this));
	}
}
