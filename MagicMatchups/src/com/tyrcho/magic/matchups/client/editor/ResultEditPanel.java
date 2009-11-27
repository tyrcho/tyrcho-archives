package com.tyrcho.magic.matchups.client.editor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.model.Result;
import com.tyrcho.magic.matchups.client.widget.GridEditors;
import com.tyrcho.magic.matchups.client.widget.RadioButtonGroup;
import com.tyrcho.magic.matchups.client.widget.sae.AbstractEditor;

public class ResultEditPanel extends
		AbstractEditor<Result, ResultEditPanel.ResultEditPanelWidget> {
	public static class ResultEditPanelWidget extends GridEditors {
		private static Map<String, HasValue<?>> buildEditors() {
			LinkedHashMap<String, HasValue<?>> map = new LinkedHashMap<String, HasValue<?>>();
			map.put(LABEL_PLAYER1, new TextBox());
			map.put(LABEL_PLAYER2, new TextBox());
			map.put(LABEL_DECK1, new TextBox());
			map.put(LABEL_DECK2, new TextBox());
			map.put(LABEL_DECK1_WINS_BEFORE, new CheckBox());
			map.put(LABEL_DECK2_WINS_AFTER, new RadioButtonGroup<WinsEnum>(LABEL_DECK2_WINS_AFTER, WinsEnum.values()));
			map.put(LABEL_DECK1_WINS_AFTER, new RadioButtonGroup<WinsEnum>(LABEL_DECK1_WINS_AFTER, WinsEnum.values()));
			return map;
		}

		public ResultEditPanelWidget() {
			super(buildEditors());
			setEnabled(false);
		}
	}

	public enum WinsEnum {
		A("0", 0),
		B("1", 1),
		C("2", 2),
		D("3", 3),
		;
		private final int value;

		private final String label;

		private WinsEnum(String label, int value) {
			this.label = label;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return label;
		}
	}
	private static final String LABEL_PLAYER1 = "Player 1";
	private static final String LABEL_PLAYER2 = "Player 2";
	private static final String LABEL_DECK1 = "Deck 1";
	private static final String LABEL_DECK2 = "Deck 2";
	private static final String LABEL_DECK1_WINS_BEFORE = "1 Wins before side";
	private static final String LABEL_DECK2_WINS_AFTER = "1 Wins after side";
	private static final String LABEL_DECK1_WINS_AFTER = "2 Wins after side";

	private Event event;

	@Override
	protected Result buildEmptyElement() {
		Result result = new Result();
		result.setEvent(event);
		return result;
	}

	@Override
	protected ResultEditPanelWidget buildWidget() {
		return new ResultEditPanelWidget();
	}

	@Override
	public void clear() {
		getWidget().clear();

	}

	@Override
	public void setEnabled(boolean enabled) {
		getWidget().setEnabled(enabled);
	}

	public void setEvent(Event event) {
		this.event = event;
		setEnabled(event!=null);
	}

	@Override
	protected void updateFields() {
//		getWidget().setValue(LABEL_DATE, getData().getDate());
//		getWidget().setValue(LABEL_LEVEL, getData().getLevel());
//		getWidget().setValue(LABEL_NAME, getData().getName());
	}

	@Override
	protected void updateValue() {
//		getData().setDate((Date) getWidget().getValue(LABEL_DATE));
//		getData().setLevel((EventLevel) getWidget().getValue(LABEL_LEVEL));
//		getData().setName((String) getWidget().getValue(LABEL_NAME));

	}

}
