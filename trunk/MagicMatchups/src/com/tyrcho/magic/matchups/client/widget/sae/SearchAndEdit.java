package com.tyrcho.magic.matchups.client.widget.sae;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tyrcho.magic.matchups.client.callback.CallbackFactory;
import com.tyrcho.magic.matchups.client.callback.SuccessCallback;
import com.tyrcho.magic.matchups.client.model.Result;

public class SearchAndEdit<T, E extends Editor<T>> extends DockPanel implements
		HasChangeHandlers {
	private TextBox search = new TextBox();
	private Button add = new Button("New");
	private Button edit = new Button("Edit");
	private Button delete = new Button("Delete");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	private ListBox elements = new ListBox(true);
	private E editor;
	private HorizontalPanel editorHeader = new HorizontalPanel();
	private final AsyncSAEService<T> service;
	private List<T> allData;
	private boolean addMode;
	private SuccessCallback<Void> dataUpdated = new SuccessCallback<Void>() {
		@Override
		public void onSuccess(Void result) {
			dataUpdated();
		}
	};

	public SearchAndEdit(AsyncSAEService<T> service, E editor) {
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
						elements.clear();
						allData = result;
						for (T t : allData) {
							elements.addItem(t.toString());
						}
					}
				}));
	}

	private void initListeners() {
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doAdd();
			}
		});
		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doEdit();
			}
		});
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doDelete();
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
		setReadOnlyMode(true);
	}

	private void setReadOnlyMode(boolean b) {
		if (b) {
			editor.setEnabled(false);
			editorHeader.clear();
			setElementSelected(elements.getSelectedIndex() >= 0);
			editorHeader.add(add);
			editorHeader.add(edit);
			editorHeader.add(delete);
			add.setEnabled(true);
		} else {
			editor.setEnabled(true);
			editorHeader.clear();
			editorHeader.add(save);
			editorHeader.add(cancel);
		}
	}

	protected void doSave() {
		if (addMode) {
			service.add(editor.getValue(), CallbackFactory.buildDefault("add",
					dataUpdated));
		} else {
			service.save(editor.getValue(), CallbackFactory.buildDefault(
					"save", dataUpdated));
		}
	}

	protected void doDelete() {
		service.delete(editor.getValue(), CallbackFactory.buildDefault(
				"delete", dataUpdated));
	}

	protected void doEdit() {
		setReadOnlyMode(false);
	}

	protected void doAdd() {
		editor.createEmpty();
		addMode = true;
		setReadOnlyMode(false);
	}

	private void initComponents() {
		// HorizontalPanel top = new HorizontalPanel();
		// // top.add(search);
		// top.add(add);
		// add(top, DockPanel.NORTH);
		HorizontalPanel center = new HorizontalPanel();
		center.add(elements);
		VerticalPanel editorPanel = new VerticalPanel();
		setReadOnlyMode(true);
		editorPanel.add(editorHeader);
		editorPanel.add(editor.getWidget());
		center.add(editorPanel);
		add(center, DockPanel.CENTER);
		editor.setEnabled(false);
	}

	protected void showSelected() {
		T selected = getSelected();
		boolean hasSelection = selected != null;
		if (selected != null) {
			editor.setValue(selected);
		} else {
			editor.clear();
		}
		setElementSelected(hasSelection);
	}

	private void setElementSelected(boolean selected) {
		add.setEnabled(selected);
		edit.setEnabled(selected);
		delete.setEnabled(selected);
	}

	private void dataUpdated() {
		initData();
		addMode = false;
		setReadOnlyMode(true);
	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return elements.addChangeHandler(handler);
	}

	public T getSelected() {
		int selectedIndex = elements.getSelectedIndex();
		boolean hasSelection = selectedIndex >= 0;
		return hasSelection ? allData.get(selectedIndex) : null;
	}

	public E getEditor() {
		return editor;
	}
}
