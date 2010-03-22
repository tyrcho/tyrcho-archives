package com.tyrcho.dictionary.view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tyrcho.dictionary.model.SessionParameters;
import com.tyrcho.dictionary.model.TwoWayDictionary;
import com.tyrcho.gui.component.JTextField;
import com.tyrcho.gui.toolkit.RadioButtonGroup;

public class SessionParametersPanel extends JPanel {
    private static final long serialVersionUID = 7727334170064237821L;
    private RadioButtonGroup  languageButtonGroup;
    private JTextField        questionCountField;
    private JCheckBox         randomCheckBox;
    private TwoWayDictionary  dictionary;

    public SessionParametersPanel(TwoWayDictionary dictionary) {
        this.dictionary = dictionary;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        Map<String, Boolean> languages = new HashMap<String, Boolean>();
        languages.put(dictionary.getFirstLanguage(), true);
        languages.put(dictionary.getSecondLanguage(), false);
        languageButtonGroup = new RadioButtonGroup(RadioButtonGroup.VERTICAL, languages);
        languageButtonGroup.setCurrentValue(Boolean.FALSE);
        add(languageButtonGroup, BorderLayout.NORTH);
        questionCountField = new JTextField("10");
        add(questionCountField, BorderLayout.SOUTH);
        randomCheckBox=new JCheckBox("Aléatoire", false);
        add (randomCheckBox, BorderLayout.CENTER);
    }

    public SessionParameters getSessionParameters() {
        return new SessionParameters(dictionary, ((Boolean) languageButtonGroup.getCurrentValue()).booleanValue(), Integer.parseInt(questionCountField.getText()), randomCheckBox.isSelected());
    }

    public static SessionParameters showSessionParametersDialog(JFrame frame, String title, TwoWayDictionary dictionary) {
        SessionParametersPanel panel = new SessionParametersPanel(dictionary);
        int action = JOptionPane.showConfirmDialog(frame, panel, "Paramètres de session", JOptionPane.OK_CANCEL_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            return panel.getSessionParameters();
        } else {
            return null;
        }
    }
}