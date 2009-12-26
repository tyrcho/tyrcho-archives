package com.tyrcho.magic.matchups.client.editor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.tyrcho.magic.matchups.client.model.Deck;
import com.tyrcho.magic.matchups.client.widget.GridEditors;
import com.tyrcho.magic.matchups.client.widget.sae.AbstractEditor;

public class DeckWithMatchupsPanel
		extends
		AbstractEditor<Deck, DeckWithMatchupsPanel.DeckWithMatchupsPanelWidget> {
	private static final String LABEL_DECK_NAME = null;

	public static class DeckWithMatchupsPanelWidget extends GridEditors {
		private static Map<String, HasValue<?>> buildEditors() {
			LinkedHashMap<String, HasValue<?>> map = new LinkedHashMap<String, HasValue<?>>();
			map.put(LABEL_DECK_NAME, new TextBox());
			return map;
		}

		public DeckWithMatchupsPanelWidget() {
			super(buildEditors());
			setEnabled(false);
		}
	}

	@Override
	protected Deck buildEmptyElement() {
		return new Deck();
	}

	@Override
	protected DeckWithMatchupsPanelWidget buildWidget() {
		return new DeckWithMatchupsPanelWidget();
	}

	@Override
	protected void updateFields() {
		getWidget().setValue(LABEL_DECK_NAME, getData().getName());
	}

	@Override
	protected void updateValue() {
		getData().setName((String) getWidget().getValue(LABEL_DECK_NAME));
	}

	@Override
	public void clear() {
		getWidget().clear();
	}

	@Override
	public void setEnabled(boolean enabled) {
		getWidget().setEnabled(enabled);
	}

}
