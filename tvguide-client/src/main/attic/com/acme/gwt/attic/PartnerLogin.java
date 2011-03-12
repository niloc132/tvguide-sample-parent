package com.acme.gwt.attic;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

import static com.acme.gwt.attic.PartnerLogin.ModelProvider.App;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class PartnerLogin implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    System.err.println("host base " + GWT.getHostPageBaseURL());
    System.err.println("module base " + GWT.getModuleBaseURL());
    System.err.println("module name " + GWT.getModuleName());
    System.err.println("module strong name " + GWT.getPermutationStrongName());
    String sessionID = Cookies.getCookie("sid");
    if (sessionID != null) {
      System.err.println("sid found: " + sessionID);
      proceed(sessionID);
    } else {
      System.err.println("displaying login");
      displayLoginBox();
    }

  }

  @RemoteServiceRelativePath("AuthProvider")
  public interface AuthProvider extends RpcService {
    boolean isSessionAdmin(String sid);

    List<String> getRoles(String kioskId);

    String getSessionId(String text, String text1) throws Exception;

    /**
     * Utility/Convenience class.
     * Use AuthProvider.App.getInstance() to access static instance of AuthProviderAsync
     */
    public static class App {
      private static final AuthProviderAsync ourInstance = (AuthProviderAsync) GWT.create(AuthProvider.class);

      public static AuthProviderAsync getInstance() {
        return ourInstance;
      }
    }
  }

  public interface AuthProviderAsync {


    void getRoles(String kioskId, AsyncCallback<List<String>> async);


    void getSessionId(String text, String text1, AsyncCallback<String> async) throws Exception;

    void isSessionAdmin(String sid, AsyncCallback<Boolean> async);
  }

  /**
   * <html>
   * <body>
   * <div id="loginDiv">
   * <p></p>username<input id="username" name="username"></input>
   * <p/>
   * <p></p>password:<input type="password" id="password" name="password"></input><br/><button type="button" id="loginButton" name="loginButton">login</button>
   * </div>
   * <script type="text/javascript" language="javascript" src="PartnerLogin/PartnerLogin.nocache.js"></script>
   * </body>
   * </html>
   */
  private void displayLoginBox() {

    System.err.println("div set visible");
    final com.google.gwt.dom.client.Element usernameElement = Document.get().getElementById("username");
    System.err.println("got username element");

    final TextBox partnerTextBox = TextBox.wrap(usernameElement);
    System.err.println("wrapped username");
    final Element passElement = Document.get().getElementById("pass");
    System.err.println("got pass element");
    final PasswordTextBox passwordTextBox = PasswordTextBox.wrap(passElement);
    System.err.println("wrapped password");
    final Element elementById = Document.get().getElementById("loginButton");
    System.err.println("got button element");
    final Button loginButton = Button.wrap(elementById);
    System.err.println("button wrapped");

    RootPanel.get("loginDiv").setVisible(true);
    loginButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        System.err.println("button clicked");
        try {
          AuthProvider.App.getInstance().getSessionId(partnerTextBox.getText(), passwordTextBox.getText(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
              System.err.println("failure in button");
              dumpError(caught, RootPanel.get());
            }

            @Override
            public void onSuccess(String result) {
              System.err.println("success in button");
              String sessionID = result;
              final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
              Date expires = new Date(System.currentTimeMillis() + DURATION);
              Cookies.setCookie("sid", sessionID, expires, null, "/", false);
              Window.Location.reload();
            }
          });
        } catch (Exception e) {
          dumpError(e, RootPanel.get());
        }
      }
    });
    System.err.println("button handler added");
  }

  private void proceed(String sessionID) {
    System.err.println("proceeding");
    RootPanel.get("loginDiv").setVisible(false);
    final TabPanel mainTabs = new TabPanel();
    mainTabs.setAnimationEnabled(true);
    RootPanel.get().add(mainTabs);
    final String[] tabNames = {"Kiosk", "MerchantGateway", "ProductModel", "Contact", "Charge", "Rental", "Adam", "Slot", "SlotEvent"};
    for (String tabTitle : tabNames) {
      addEditorPanel(tabTitle, mainTabs, sessionID);
    }
    mainTabs.addSelectionHandler(new SelectionHandler<Integer>() {
      @Override
      public void onSelection(SelectionEvent<Integer> integerSelectionEvent) {
        History.newItem(tabNames[integerSelectionEvent.getSelectedItem()]);
      }
    });

    History.addValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
        System.err.println("firing history change");
      }
    });
  }

  static final int ORIGVALUE = 0;
  static final int TEXTBOXVALUE = 1;
  static final int CHANGEDVALUE = 2;

  private static void addEditorPanel(final String tabTitle, TabPanel mainTabs, final String sid) {
    final Panel flowPanel = new FlowPanel();
    flowPanel.add(new Label("Please Enter " + tabTitle + " Id"));
    mainTabs.add(flowPanel, tabTitle);
    mainTabs.selectTab(0);
    final TextBox idBox = new TextBox();
    flowPanel.add(idBox);
    idBox.addValueChangeHandler(new ValueChangeHandler<String>() {

      @Override
      public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {

        try {
          final String oid = idBox.getText();
          final String className = "laptopsanytime.model." + tabTitle;
          renderDrillDown(oid, className, sid, flowPanel/*, tabTitle*/);
        } catch (Exception e) {
          Logger.getAnonymousLogger().warning(Arrays.toString(e.getStackTrace()));  //To
          // change body of catch statement use File | Settings | File Templates.
        }

      }
    });
    History.newItem("0");

  }

  @RemoteServiceRelativePath("ModelProvider")
  interface ModelProvider extends RpcService {
    Map<String, String> find(String classname, String id, String sid);

    boolean merge(String tabTitle, String id, Map<String, String> updateMap, String sid);

    /**
     * Utility/Convenience class.
     * Use ModelProvider.App.getInstance() to access static instance of ModelProviderAsync
     */
    public static class App {
      private static final ModelProviderAsync ourInstance = (ModelProviderAsync) GWT.create(ModelProvider.class);

      public static ModelProviderAsync getInstance() {
        return ourInstance;
      }
    }
  }

  public interface
      ModelProviderAsync {
//    void find(Class c, String id, AsyncCallback<Map<String, String>> async);

    void find(String classname, String id, String sid, AsyncCallback<Map<String, String>> async);

    void merge(String tabTitle, String id, Map<String, String> updateMap, String sid, AsyncCallback<Boolean> async);
  }


  static void renderDrillDown(final String oid, final String className, final String sid, final Panel flowPanel/*, final String tabTitle*/) {

    final StackPanel panel;
    panel = new StackPanel();
    flowPanel.clear();
    flowPanel.add(panel);
    App.getInstance().find(className, oid, sid,
        new AsyncCallback<Map<String, String>>() {

          @Override
          public void onFailure(Throwable caught) {
            dumpError(caught, panel);
          }

          @Override
          public void onSuccess(Map<String, String> origMap) {
            History.newItem(className + "/" + oid);
            System.err.println(Arrays.toString(new Object[]{oid, className, sid, flowPanel.toString()}));

            final Panel fieldPanel = new FlowPanel();
            final Panel listPanel = new FlowPanel();
            panel.add(fieldPanel, "Fields");
            panel.add(listPanel, "Links");
            final Map<String, Object[]> map = new TreeMap<String, Object[]>();
            for (Map.Entry<String, String> entry : origMap.entrySet()) {
              String s = entry.getKey();
              String val = entry.getValue();
              if (s.startsWith("@")) {
                render12m(s, val, listPanel, flowPanel, sid);
              } else if (s.startsWith("+")) {
                render121(s, val, listPanel, flowPanel, sid);
              } else {
                if (!s.equals("id")) {
                  renderBasic(map, s, val, fieldPanel);
                }
              }
            }

            renderResetButton(map, flowPanel);
            renderDoneButton(map, flowPanel, className, oid, sid);

            History.fireCurrentHistoryState();

          }

        });
  }

  private static void renderDoneButton(final Map<String, Object[]> map, final Panel flowPanel, final String tabTitle, final String oid, final String sid) {
    final Button doneButton = new Button("done");
    flowPanel.add(doneButton);
    doneButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {

        final Map<String, String> updateMap = new TreeMap<String, String>();
        for (Map.Entry<String, Object[]> entry : map.entrySet()) {

          final Object[] value = entry.getValue();


          if (null != value[CHANGEDVALUE]) {
            updateMap.put(entry.getKey(), (String) value[CHANGEDVALUE]);
          }


        }
        if (!updateMap.isEmpty())
          App.getInstance().merge(tabTitle, oid, updateMap, sid, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {

              dumpError(caught, flowPanel);

            }

            @Override
            public void onSuccess(Boolean result) {
              flowPanel.clear();
              flowPanel.add(new Label("value updated, please reload yoru browser to re-edit this tab."));
            }
          });

      }
    });
  }

  private static void renderResetButton(final Map<String, Object[]> map, Panel flowPanel) {
    Button rollbackButton1 = new Button("reset");
    rollbackButton1.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        for (Map.Entry<String, Object[]> stringEntry : map.entrySet()) {
          stringEntry.getValue()[CHANGEDVALUE] = null;
          ((TextBox) stringEntry.getValue()[TEXTBOXVALUE]).setText(((String) stringEntry.getValue()[ORIGVALUE]));
        }
      }
    });
    Button rollbackButton = rollbackButton1;


    flowPanel.add(rollbackButton);
  }

  private static void renderBasic(Map<String, Object[]> map, String s, String val, Panel flowPanel) {
    final TextBox box = new TextBox();
    box.setText(val);
    box.setTitle(s);
    final Object[] edit = {val, box, null};
    map.put(s, edit);
    box.addValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
        edit[CHANGEDVALUE] = box.getText();
      }
    });
    flowPanel.add(new Label(s));
    flowPanel.add(box);
  }

  private static void render12m(final String s, String val, Panel flowPanel, final Panel origPanel, final String sid) {
    final String[] keys = s.split("@");
    flowPanel.add(new Label(keys[1]));
    final ListBox listBox = new ListBox();
    flowPanel.add(listBox);
    final String s1 = val.substring(1, val.length() - 1).trim();
    if (!s1.isEmpty()) {

      for (String string : s1.split(",")) {
        listBox.addItem(keys[2] + ":" + string, string);
      }
    }
    listBox.setVisibleItemCount(Math.min(10, listBox.getItemCount()));
    listBox.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        final int index = listBox.getSelectedIndex();
        final String[] strings = listBox.getItemText(index).split(":");
        final String oid = strings[1];
        final String className = strings[0];
        subRenderDrillDown(oid, className, origPanel, sid);

      }
    });
  }

  private static void subRenderDrillDown(String oid, String className, Panel origPanel, String sid) {
    origPanel.add(new Label(className + " : " + oid));
    final FlowPanel panel = new FlowPanel();
    origPanel.add(panel);
    PartnerLogin.renderDrillDown(oid, className, sid, panel/*,className */);
  }

  private static void render121(final String s, final String val, Panel flowPanel, final Panel origPanel, final String sid) {
    final String[] keys = s.split("[+]");
    final Button linkTo = new Button(keys[1] + ":" + keys[2] + "->" + val);
    flowPanel.add(linkTo);
    linkTo.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        String oid = val;
        String className = keys[2];
        subRenderDrillDown(oid, className, origPanel, sid);

      }
    });

  }

  private static void dumpError(Throwable caught, Panel flowPanel) {
    flowPanel.add(new HTML(caught.getMessage()));
  }

}
