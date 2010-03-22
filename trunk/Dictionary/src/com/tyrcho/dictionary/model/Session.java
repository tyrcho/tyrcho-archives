package com.tyrcho.dictionary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Session {
    private int score;
    private int questionCount;
    private boolean firstLanguage;
    private TwoWayDictionary dictionary;
    private List<String> entries;
    private boolean random;
	private final String ignoredChars;
    
    public void resetQuestions() {
        Set<String> dictEntries = dictionary.getEntries(firstLanguage);            
        if (random) {
            entries=new ArrayList<String>(dictEntries);
            Collections.shuffle(entries);
        } else {
            entries=new ArrayList<String>(dictEntries);
            int maxAnswers=0;
            for (String string : dictEntries) {
                DictionaryEntry entry = dictionary.getEntry(string, firstLanguage);
                int answers=entry.getTotalAnswers();
                maxAnswers=Math.max(maxAnswers, answers);
            }
            final float finalMaxAnswers=maxAnswers;
            Collections.sort(entries, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    DictionaryEntry entry1 = dictionary.getEntry(s1, firstLanguage);
                    DictionaryEntry entry2 = dictionary.getEntry(s2, firstLanguage);
                    float proportionDifference = entry1.getGoodAnswerProportion()-entry2.getGoodAnswerProportion();
                    float totalAnswersDifference=(entry1.getTotalAnswers()-entry2.getTotalAnswers())/finalMaxAnswers;
                    float difference=proportionDifference+totalAnswersDifference;
//                    String message1=s1+ "("+entry1.getGoodAnswers()+"/"+entry1.getTotalAnswers()+")";
//                    String message2=s2+ "("+entry2.getGoodAnswers()+"/"+entry2.getTotalAnswers()+")";                    
                    if (difference==0) {
                        //System.out.println(message1+" egal "+message2);
                        return 0;
                    } else if (difference>0) {
                        //System.out.println(message1+" apres "+message2);
                        return 1;
                    } else {
                        //System.out.println(message1+" avant "+message2);
                        return -1;
                    }
                }
            });
        }
        
        score = 0;
        int entriesCount = entries.size();
        if (entriesCount <= questionCount)  {
            this.questionCount=entriesCount;
        } else {
            Collections.reverse(entries);
            Iterator<String> i = entries.iterator();
            while (entries.size()>questionCount) {
                i.next();
                i.remove();
            }
            Collections.reverse(entries);
        }
    }
    
    public Session(SessionParameters parameters) {
        this(parameters.getDictionary(), parameters.isFirstLanguage(), parameters.getQuestionCount(), parameters.isRandom(), parameters.getIgnoredChars());
    }
    
    public Session(final TwoWayDictionary dictionary, final boolean firstLanguage,int questionCount, boolean random, String ignoredChars){
        this.questionCount = questionCount;
        this.firstLanguage = firstLanguage;
        this.dictionary = dictionary;
        this.random=random;
		this.ignoredChars = ignoredChars;
        resetQuestions();
    }
    
    public void switchLanguage() {
        Set<String> newEntries = new HashSet<String>();
        for (String word : entries) {
            newEntries.addAll(dictionary.getEntry(word, firstLanguage).getTranslations());         
        }
        entries=new ArrayList<String>(newEntries);
        Collections.shuffle(entries);
        questionCount=entries.size();
        firstLanguage=!firstLanguage;
    }
    
    //Mise à jour du score de la session
    public void updateScore(){
        score = score + 1;
    }
    
    public void resetScore() {
        score=0;
    }
    
    //Récupération du score de la session
    public String getScore(){
        return score + "/" + questionCount;
    }
    
    //Récupération de l'itérateur de la session
    public Iterator<Question> iterator(){
        return new SessionIterator();
    }

    class SessionIterator implements Iterator<Question> {
        private Iterator<String> iterator;
        
        SessionIterator() {
            iterator=entries.iterator();
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public Question next() {
            String word=iterator.next();
            return new Question(word,dictionary.getEntry(word, firstLanguage), ignoredChars);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

    public boolean isFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(boolean firstLanguage) {
        this.firstLanguage = firstLanguage;
    }
}
