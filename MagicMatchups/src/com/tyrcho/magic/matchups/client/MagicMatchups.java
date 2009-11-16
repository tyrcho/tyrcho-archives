package com.tyrcho.magic.matchups.client;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MagicMatchups implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private FlexTable matchups = new FlexTable();
	private List<String> decks = new ArrayList<String>();
	private Button addDeck = new Button("Add");
	private TextBox deckName = new TextBox();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		deckName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addDeck.click();
				}
			}
		});
		matchups.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

			}
		});
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(matchups);
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(deckName);
		hPanel.add(addDeck);
		verticalPanel.add(hPanel);
		RootPanel.get().add(verticalPanel);

		// Focus the cursor on the name field when the app loads
		resetDeckName();

		// Add a handler to close the DialogBox
		addDeck.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addDeck();
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
	
	private void setLabel(final String originalText, String currentText, final int x, final int y) {
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
