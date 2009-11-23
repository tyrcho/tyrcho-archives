package com.tyrcho.magic.matchups.client.widget.sae;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tyrcho.magic.matchups.client.callback.CallbackFactory;
import com.tyrcho.magic.matchups.client.callback.SuccessCallback;

public class SearchAndEdit<T, E extends Widget & Editor<T>> extends DockPanel {
	private TextBox search = new TextBox();
	private Button add = new Button("New");
	private Button edit = new Button("Edit");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	private ListBox elements = new ListBox(true);
	private E editor;
	private HorizontalPanel editorHeader = new HorizontalPanel();
	private final SAEService<T> service;
	private List<T> allData;

	public SearchAndEdit(SAEService<T> service, E editor) {
		this.service = service;
		this.editor = editor;
		initComponents();
		initListeners();
		initData();
	}

	private void initData() {
		service.selectAll(CallbackFactory.buildDefault("select all",
				new SuccessCallback<List<T>>() {
					@Override
					public void onSuccess(List<T> result) {
						allData = result;
						for (T t : allData) {
							elements.addItem(t.toString());
						}
					}
				}));
	}

	private void initListeners() {
		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doEdit();
			}
		});
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSave();
			}
		});
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doCancel();
			}
		});
		elements.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				showSelected();
			}
		});

	}

	protected void doCancel() {
		showSelected();
		setEditButtonVisible(true);
	}

	private void setEditButtonVisible(boolean b) {
		if (b) {
			editor.setEnabled(false);
			editorHeader.clear();
			editorHeader.add(edit);
		} else {
			editor.setEnabled(true);
			editorHeader.clear();
			editorHeader.add(save);
			editorHeader.add(cancel);
		}
	}

	protected void doSave() {
		service.add(editor.getValue(), CallbackFactory.buildDefault("save"));
		setEditButtonVisible(true);
	}

	protected void doEdit() {
		setEditButtonVisible(false);
	}

	private void initComponents() {
		HorizontalPanel top = new HorizontalPanel();
		top.add(search);
		top.add(add);
		add(top, DockPanel.NORTH);
		HorizontalPanel center = new HorizontalPanel();
		center.add(elements);
		VerticalPanel editorPanel = new VerticalPanel();
		editorHeader.add(edit);
		editorPanel.add(editorHeader);
		editorPanel.add(editor);
		center.add(editorPanel);
		add(center, DockPanel.CENTER);
		editor.setEnabled(false);
	}

	protected void showSelected() {
		int selectedIndex = elements.getSelectedIndex();
		editor.setValue(allData.get(selectedIndex));
	}
}
