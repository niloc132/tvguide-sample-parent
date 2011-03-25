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

import com.acme.gwt.shared.TvEpisodeProxy;
import com.colinalworth.celltable.columns.client.Columns;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.HasDataEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Facilitates editing episode info inline, not requiring any list, focus, edit, save, list cycle.
 * Also shows off the CellTable-Tools, for better or worse.
 * 
 * 
 * @author colin
 */
public class EditableEpisodeListWidget extends Composite
		implements
			IsEditor<HasDataEditor<TvEpisodeProxy>> {
	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, EditableEpisodeListWidget> {
	}

	interface EpisodeColumns extends Columns<TvEpisodeProxy> {
		NumberCell season();//erp int != String

		NumberCell episodeNumber();// int != String

		EditTextCell name();
	}

	private EpisodeColumns columns = GWT.create(EpisodeColumns.class);
	@Path("")
	HasDataEditor<TvEpisodeProxy> listEd;
	@UiField(provided = true)
	CellTable<TvEpisodeProxy> list = new CellTable<TvEpisodeProxy>();

	public EditableEpisodeListWidget() {
		listEd = HasDataEditor.of(list);

		columns.configure(list);

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasDataEditor<TvEpisodeProxy> asEditor() {
		return listEd;
	}
}
