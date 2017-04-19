package io.hyi.bot.core;

import javax.persistence.*;

/**
 * The essence of questions and answers to them
 */
@Entity
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String answer;

    public Questions() {

    }

    public Questions(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
