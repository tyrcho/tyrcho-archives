package com.tyrcho.dictionary.model;

import java.util.Collection;
import java.util.HashSet;

public class DictionaryEntry {
    private Collection<String> translations=new HashSet<String>();
    private String explaination;
    private int goodAnswers;
    private int wrongAnswers;
    
    public int getGoodAnswers() {
        return goodAnswers;
    }
    public void incrementGoodAnswers() {
        ++goodAnswers;
    }
    public int getWrongAnswers() {
        return wrongAnswers;
    }
    public void incrementWrongAnswers() {
        ++wrongAnswers;
    }
    
    public int getTotalAnswers() {
        return wrongAnswers+goodAnswers;
    }
    
    /**
     * Gets the ratio in order to sort on it.
     * @return the ratio, or -1 if only wrong answers, or 0 if no answer has been recorded
     */
    public float getGoodAnswerProportion() {
        int total=getTotalAnswers();
        if (total==0) {
            return 0; 
        } else if (goodAnswers==0) {
                return -1;
        } else {
            return ((float)goodAnswers)/total;
        }            
    }
    /**
     * @return  Returns the explaination.
     * @uml.property  name="explaination"
     */
    public String getExplaination() {
        return explaination;
    }
    /**
     * @param explaination  The explaination to set.
     * @uml.property  name="explaination"
     */
    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }
    /**
     * @return  Returns the translations.
     * @uml.property  name="translations"
     */
    public Collection<String> getTranslations() {
        return translations;
    }
    public void addTranslation(String translation) {
        translations.add(translation);
    }
    
    public void removeTranslation(String translation) {
        translations.remove(translation);
    }
    
}
