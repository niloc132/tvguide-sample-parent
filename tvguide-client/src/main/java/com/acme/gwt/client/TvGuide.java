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
package com.acme.gwt.client;


import com.acme.gwt.shared.TvViewerProxy;
import com.acme.gwt.shared.util.Md5;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author colin
 */
public class TvGuide implements EntryPoint {
  public MyRequestFactory rf;
  public EventBus eventBus;
  private static TvGuide instance;

  {
    instance = this;
    eventBus = GWT.create(SimpleEventBus.class);
    rf = GWT.create(MyRequestFactory.class);
    rf.initialize(eventBus);
  }

  static TvGuide getInstance() {
    return instance;
  }

  public void onModuleLoad() {
    final GateKeeper[] gateKeeper = new GateKeeper[1];
    gateKeeper[0] = new GateKeeper();
    new DialogBox() {{
      final TextBox email = new TextBox() {{
        setText("you@example.com");
      }};
      final PasswordTextBox passwordTextBox = new PasswordTextBox();
      setText("please log in");
      setWidget(new VerticalPanel() {{
        add(new HorizontalPanel() {{
          add((IsWidget) new Label("email"));
          add((IsWidget) email);
        }});
        add(new HorizontalPanel() {{
          add((IsWidget) new Label("Password"));
          add((IsWidget) passwordTextBox);
        }});
        add(new Button("OK!!") {{
          addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              String text = passwordTextBox.getText();
              String digest = Md5.md5Hex(text);
              Request<TvViewerProxy> authenticate = TvGuide.getInstance().rf.reqViewer().authenticate(email.getText(), digest);
              authenticate.with("geo", "name",
                  "favoriteShows.name", "favoriteShows.description"
              ).to(gateKeeper[0]).fire(new Receiver<Void>() {
                @Override
                public void onSuccess(Void response) {
                  hide(); //todo: review for a purpose
                  removeFromParent();
                }
              });
            }
          });
        }});
      }});
      center();
      show();
    }};
  }

}
