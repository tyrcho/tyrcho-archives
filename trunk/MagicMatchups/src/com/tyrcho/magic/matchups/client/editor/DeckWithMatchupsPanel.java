package com.tyrcho.magic.matchups.client.editor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.tyrcho.magic.matchups.client.model.Deck;
import com.tyrcho.magic.matchups.client.widget.GridEditors;
import com.tyrcho.magic.matchups.client.widget.sae.AbstractEditor;

public class DeckWithMatchupsPanel extends
		AbstractEditor<Deck, DeckWithMatchupsPanel.DeckWithMatchupsPanelWidget> {
	private static final String LABEL_DECK_NAME = "Deck name";

	public static class DeckWithMatchupsPanelWidget extends Composite {
		private GridEditors gridEditors;

		private static Map<String, HasValue<?>> buildEditors() {
			LinkedHashMap<String, HasValue<?>> map = new LinkedHashMap<String, HasValue<?>>();
			map.put(LABEL_DECK_NAME, new TextBox());
			return map;
		}

		public DeckWithMatchupsPanelWidget(DeckWithMatchupsPanel deckWithMatchupsPanel) {
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			gridEditors = new GridEditors(buildEditors());
			gridEditors.setEnabled(false);
			horizontalPanel.add(gridEditors);
//			horizontalPanel.add(new SearchAndEdit<Matchup, MatchupEditor>(
//					new MatchupService(deckWithMatchupsPanel), new MatchupEditor()));
			initWidget(horizontalPanel);
		}
	}



	@Override
	protected Deck buildEmptyElement() {
		return new Deck();
	}

	@Override
	protected DeckWithMatchupsPanelWidget buildWidget() {
		return new DeckWithMatchupsPanelWidget(this);
	}

	@Override
	protected void updateFields() {
		getWidget().gridEditors.setValue(LABEL_DECK_NAME, getData().getName());
	}

	@Override
	protected void updateValue() {
		getData().setName(
				(String) getWidget().gridEditors.getValue(LABEL_DECK_NAME));
	}

	@Override
	public void clear() {
		getWidget().gridEditors.clear();
	}

	@Override
	public void setEnabled(boolean enabled) {
		getWidget().gridEditors.setEnabled(enabled);
	}

}
