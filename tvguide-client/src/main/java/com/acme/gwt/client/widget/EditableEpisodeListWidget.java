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

import com.acme.gwt.client.view.EditableEpisodeListView;
import com.acme.gwt.shared.TvEpisodeProxy;
import com.colinalworth.celltable.columns.client.ColumnsWithFactory;
import com.colinalworth.celltable.columns.client.HasDataFlushableEditor;
import com.colinalworth.celltable.columns.client.NumberConverter;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * Facilitates editing episode info inline, not requiring any list, focus, edit, save, list cycle.
 * Also shows off the CellTable-Tools, for better or worse.
 * 
 * 
 * @author colin
 */
@Singleton
public class EditableEpisodeListWidget extends Composite
implements
EditableEpisodeListView, IsEditor<HasDataFlushableEditor<TvEpisodeProxy>> {
	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, EditableEpisodeListWidget> {
	}

	interface EpisodeColumns extends ColumnsWithFactory<TvEpisodeProxy, EditableEpisodeListWidget> {
		@Sortable
		@ConvertedWith(NumberConverter.class)
		EditTextCell season();//erp int != String
		@ConvertedWith(NumberConverter.class)
		EditTextCell episodeNumber();// int != String

		EditTextCell name();

		@Header("")
		@Path("")
		ActionCell<TvEpisodeProxy> deleteCell();
	}

	private EpisodeColumns columns = GWT.create(EpisodeColumns.class);
	@Path("")
	HasDataFlushableEditor<TvEpisodeProxy> listEd;
	@UiField(provided = true)
	CellTable<TvEpisodeProxy> list = new CellTable<TvEpisodeProxy>();

	private Presenter presenter;

	public EditableEpisodeListWidget() {
		listEd = HasDataFlushableEditor.of(list);

		columns.setFactory(this);
		columns.configure(list);

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasDataFlushableEditor<TvEpisodeProxy> asEditor() {
		return listEd;
	}

	@Override
	public List<TvEpisodeProxy> getList() {
		return listEd.getList();
	}

	public void setPresenter(EditableEpisodeListView.Presenter p) {
		this.presenter = p;
	}

	ActionCell<TvEpisodeProxy> deleteCell() {
		return new ActionCell<TvEpisodeProxy>("X", new Delegate<TvEpisodeProxy>() {
			@Override
			public void execute(TvEpisodeProxy episode) {
				// right now, changing the edited list here, since it doesnt
				// have a real effect, until saved
				//listEd.getList().remove(episode);
				// or use a presenter?
				presenter.removeEpisode(episode);
			}
		});
	}

	@UiHandler("add")
	void addClick(ClickEvent evt) {
		// same question as delete episode button - doesn't the presenter 
		// need to have at least some involvement here?
		//listEd.getList().add(presenter.createEpisode());
		presenter.addEpisode();
	}
}
