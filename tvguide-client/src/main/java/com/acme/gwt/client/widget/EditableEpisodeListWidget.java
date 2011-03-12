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
import com.colinalworth.celltable.columns.client.HasDataFlushableEditor;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author colin
 */
public class EditableEpisodeListWidget extends Composite {
  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, EditableEpisodeListWidget> {
  }

  interface EpisodeColumns extends Columns<TvEpisodeProxy> {
    EditTextCell season();//erp int != String

    EditTextCell episodeNumber();// int != String

    EditTextCell name();
  }

  private EpisodeColumns columns = GWT.create(EpisodeColumns.class);
  @Path("")
  HasDataFlushableEditor<TvEpisodeProxy> listEd;
  @UiField(provided = true)
  CellTable<TvEpisodeProxy> list = new CellTable<TvEpisodeProxy>();

  public EditableEpisodeListWidget() {
    listEd = HasDataFlushableEditor.of(list);

    columns.configure(list, listEd);

    initWidget(uiBinder.createAndBindUi(this));
  }

}
