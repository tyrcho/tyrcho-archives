package com.tyrcho.magic.matchups.client.editor;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tyrcho.magic.matchups.client.model.Matchup;
import com.tyrcho.magic.matchups.client.widget.sae.AbstractEditor;

public class MatchupEditor extends
		AbstractEditor<Matchup, MatchupEditor.MatchupPanelWidget> {

	public static class MatchupPanelWidget extends Composite {
		

		protected Label title;

		public MatchupPanelWidget() {
			VerticalPanel verticalPanel=new VerticalPanel();
			title = new Label();
			verticalPanel.add(title);
			initWidget(verticalPanel);
		}
	}

	@Override
	protected Matchup buildEmptyElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MatchupPanelWidget buildWidget() {
		return new MatchupPanelWidget();
	}

	@Override
	protected void updateFields() {
		getWidget().title.setText(getData().getDeck1().getName()+" VS "+getData().getDeck2().getName());
		
	}

	@Override
	protected void updateValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}
}
