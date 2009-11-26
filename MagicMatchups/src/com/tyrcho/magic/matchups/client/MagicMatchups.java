package com.tyrcho.magic.matchups.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tyrcho.magic.matchups.client.callback.CallbackFactory;
import com.tyrcho.magic.matchups.client.callback.SuccessCallback;
import com.tyrcho.magic.matchups.client.editor.EventEditPanel;
import com.tyrcho.magic.matchups.client.editor.EventsList;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.model.LoginInfo;
import com.tyrcho.magic.matchups.client.service.DeckNamesService;
import com.tyrcho.magic.matchups.client.service.DeckNamesServiceAsync;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.client.service.LoginService;
import com.tyrcho.magic.matchups.client.service.LoginServiceAsync;
import com.tyrcho.magic.matchups.client.widget.sae.SAEService;
import com.tyrcho.magic.matchups.client.widget.sae.SearchAndEdit;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MagicMatchups implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final DeckNamesServiceAsync deckNamesService = GWT.create(DeckNamesService.class);

	private FlexTable matchups = new FlexTable();
	private List<String> decks = new ArrayList<String>();
	private Button addDeck = new Button("Add");
	private Button save = new Button("Save");
	private TextBox deckName = new TextBox();
	private EventsList eventsList = new EventsList();

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							loadApplication();
						} else {
							loadLogin();
						}
					}
				});
	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get().add(loginPanel);
	}

	protected void loadApplication() {
		VerticalPanel verticalPanel = new VerticalPanel();
		HorizontalPanel logoutPanel=new HorizontalPanel();
		logoutPanel.add(new Label("Welcome, "+loginInfo.getNickname()));
		signOutLink.setHref(loginInfo.getLogoutUrl());
		logoutPanel.add(new Label(" "));
		logoutPanel.add(signOutLink);
		verticalPanel.add(logoutPanel);
		verticalPanel.add(new MenuBar());
		verticalPanel.add(matchups);
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(eventsList);
		hPanel.add(deckName);
		hPanel.add(addDeck);
		verticalPanel.add(hPanel);
		verticalPanel.add(save);
		SAEService<Event> eventService = GWT.create(EventService.class);
		verticalPanel.add(new SearchAndEdit<Event, EventEditPanel>(
				eventService, new EventEditPanel()));
		RootPanel.get().add(verticalPanel);
		resetDeckName();
		registerHandlers();
		initDeckNames();
	}

	private void initDeckNames() {
		deckNamesService.getDecks(CallbackFactory.buildDefault(
				"list deck names", new SuccessCallback<String[]>() {
					@Override
					public void onSuccess(String[] result) {
						decks = Arrays.asList(result);
					}
				}));
	}

	private void registerHandlers() {
		deckName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addDeck.click();
				}
			}
		});
		addDeck.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addDeck();
			}
		});
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSave();
			}
		});
	}

	protected void doSave() {
		deckNamesService.saveDecks(decks.toArray(new String[0]),
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						Window.alert("OK");
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}

	private void resetDeckName() {
		deckName.setFocus(true);
		deckName.setText("deck name");
		deckName.selectAll();
	}

	protected void addDeck() {
		String name = deckName.getText();
		int count = decks.size();
		matchups.setText(0, count + 1, name);
		matchups.setText(count + 1, 0, name);
		int i = 1;
		for (String deck : decks) {
			setLabel(name + " vs " + deck, count + 1, i);
			setLabel(deck + " vs " + name, i, count + 1);
			i++;
		}
		matchups.setText(count + 1, count + 1, "X");
		decks.add(name);
		resetDeckName();
	}

	private void setLabel(final String text, final int x, final int y) {
		setLabel(text, text, x, y);
	}

	private void setLabel(final String originalText, String currentText,
			final int x, final int y) {
		final Label l1 = new Label(currentText);
		l1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setText(x, y, originalText);
			}
		});
		matchups.setWidget(x, y, l1);
	}

	protected void setText(final int x, final int y, final String text) {
		final TextBox textBox = new TextBox();
		textBox.setTitle(text);
		textBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				setLabel(text, textBox.getText(), x, y);
			}
		});
		textBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					setLabel(text, textBox.getText(), x, y);
				}
			}
		});
		matchups.setWidget(x, y, textBox);
		textBox.setFocus(true);
	}
}
