package com.tyrcho.dictionary.view;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.tyrcho.dictionary.DictionaryConstants;
import com.tyrcho.dictionary.model.DictionaryEntry;
import com.tyrcho.dictionary.model.Question;
import com.tyrcho.dictionary.model.Session;
import com.tyrcho.dictionary.model.SessionCompleteEvent;
import com.tyrcho.dictionary.model.SessionCompleteListener;
import com.tyrcho.dictionary.model.factory.XstreamDictionaryFactory;
import com.tyrcho.gui.component.console.CommandEvent;
import com.tyrcho.gui.component.console.CommandEventListener;
import com.tyrcho.gui.component.console.ConsolePanel;

@SuppressWarnings("serial")
public class VocabularyTestFrame extends JFrame {
    private static final int              questionCount = 10;
    private static final boolean          firstLanguage = true;
    private ConsolePanel                  console;
    private List<SessionCompleteListener> listeners     = new LinkedList<SessionCompleteListener>();
    private Session                       session;
    private Iterator<Question>            iterator;
    private Question                      currentQuestion;
    private CommandEventListener          listener      = new CommandEventListener() {
                                                            public void commandPerformed(CommandEvent e) {
                                                                questionAnswered(e.getText().trim());
                                                            }
                                                        };
    private StringBuffer errors=new StringBuffer();                                                    

    public VocabularyTestFrame(String title, Session session) {
        super(title);
        this.session = session;
        console = new ConsolePanel();
        console.setFont(DictionaryConstants.FONT);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(console.getPanel(), BorderLayout.CENTER);
    }

    public String getErrors() {
        return errors.toString();
    }

    public void addSessionCompleteListener(SessionCompleteListener listener) {
        listeners.add(listener);
    }

    public void removeSessionCompleteListener(SessionCompleteListener listener) {
        listeners.remove(listener);
    }

    protected void fireSessionCompleteEvent(String score) {
        SessionCompleteEvent event = new SessionCompleteEvent(this, score);
        for (SessionCompleteListener listener : listeners) {
            listener.sessionComplete(event);
        }
    }

    public synchronized void runSession() {
        pack();
        setVisible(true);
        console.clear();
        console.requestFocus();
        iterator = session.iterator();
        console.addCommandEventListener(listener);
        nextQuestion();
    }

    private void questionAnswered(String answer) {
        currentQuestion.setInputTranslation(answer);
        DictionaryEntry dictionaryEntry = currentQuestion.getDictionaryEntry();
        if (currentQuestion.isAnswerValid()) {
            session.updateScore();
            dictionaryEntry.incrementGoodAnswers();
            int goodAnswers = dictionaryEntry.getGoodAnswers();
            String score="("+goodAnswers+"/"+(goodAnswers+dictionaryEntry.getWrongAnswers())+")";
            console.println("Bravo "+score);
        } else {
            int translations=dictionaryEntry.getTranslations().size();
            dictionaryEntry.incrementWrongAnswers();
            int goodAnswers = dictionaryEntry.getGoodAnswers();
            String score="("+goodAnswers+"/"+(goodAnswers+dictionaryEntry.getWrongAnswers())+")";
            String message= (translations>1 ? "les traductions possible pour " : "la bonne traduction pour ")+currentQuestion.getWord()+" : "+ currentQuestion.getTranslation();
            errors.append(message+" et non <"+answer+">");
            errors.append(System.getProperty("line.separator"));
            console.println("*** ERREUR *** "+score+", "+message);
            
        }
        String example=dictionaryEntry.getExplaination();
        if (example!=null && example.trim()!="") {
            console.println(example);
        }
        console.println();
        nextQuestion();
    }

    private void nextQuestion() {
        if (iterator.hasNext()) {
            currentQuestion = iterator.next();
            console.println(currentQuestion.toString());
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    console.removeCommandEventListener(listener);
                    fireSessionCompleteEvent(session.getScore());
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        // Création d'une nouvelle session
        final String fileName      = "D:/-= DOCUMENTS =-/dictionnaire.dict";
        XstreamDictionaryFactory factory = new XstreamDictionaryFactory();
        factory.setFileName(fileName);
		final Session session = new Session(factory.load(), firstLanguage, questionCount, true, "");
        final VocabularyTestFrame frame = new VocabularyTestFrame("Test", session);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.runSession();
        frame.addSessionCompleteListener(new SessionCompleteListener() {
            public void sessionComplete(SessionCompleteEvent e) {
                System.out.println("Score total : " + session.getScore());
                frame.dispose();
            }
        });
    }
}
