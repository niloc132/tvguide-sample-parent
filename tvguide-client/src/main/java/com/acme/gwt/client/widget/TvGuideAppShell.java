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

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author colin
 */
@Singleton
public class TvGuideAppShell extends Composite implements HasOneWidget {
  interface TvGuideAppShellUiBinder extends UiBinder<Widget, TvGuideAppShell> {
  }

  private static TvGuideAppShellUiBinder uiBinder = GWT
      .create(TvGuideAppShellUiBinder.class);

  @UiField
  LayoutPanel display;

  @Inject
  public TvGuideAppShell(ActivityManager activityManager) {
    initWidget(uiBinder.createAndBindUi(this));

    activityManager.setDisplay(this);
  }

  @Override
  public Widget getWidget() {
    return display.getWidgetCount() == 0 ? null : display.getWidget(0);
  }

  @Override
  public void setWidget(Widget widget) {
    display.clear();
    if (widget != null) {
      display.add(widget);
    }
  }

  public void setWidget(IsWidget w) {
    setWidget(w.asWidget());
  }
}
