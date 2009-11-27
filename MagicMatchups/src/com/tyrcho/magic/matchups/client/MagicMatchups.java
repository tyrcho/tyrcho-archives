package com.tyrcho.magic.matchups.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tyrcho.magic.matchups.client.editor.EventEditPanel;
import com.tyrcho.magic.matchups.client.editor.ResultEditPanel;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.model.LoginInfo;
import com.tyrcho.magic.matchups.client.model.Result;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.client.service.LoginService;
import com.tyrcho.magic.matchups.client.service.LoginServiceAsync;
import com.tyrcho.magic.matchups.client.service.ResultService;
import com.tyrcho.magic.matchups.client.widget.sae.AsyncSAEService;
import com.tyrcho.magic.matchups.client.widget.sae.SearchAndEdit;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MagicMatchups implements EntryPoint {

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
		AsyncSAEService<Event> eventService = GWT.create(EventService.class);
		verticalPanel.add(new SearchAndEdit<Event, EventEditPanel>(
				eventService, new EventEditPanel()));
		AsyncSAEService<Result> resultService = GWT.create(ResultService.class);
		verticalPanel.add(new SearchAndEdit<Result, ResultEditPanel>(
				resultService, new ResultEditPanel()));
		RootPanel.get().add(verticalPanel);
	
	}



	
}
