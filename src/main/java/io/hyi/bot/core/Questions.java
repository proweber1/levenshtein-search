package io.hyi.bot.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * The essence of questions and answers to them
 */
@Entity
@NamedNativeQuery(
        name = Questions.SEARCH_NATIVE_NAMED_QUERY,
        query = "SELECT * FROM " +
                "(SELECT id, question, answer, levenshtein(lower(question), lower(:question)) as destinationIndex FROM questions) as q " +
                "WHERE destinationIndex BETWEEN 0 and :maxDestination ORDER BY destinationIndex ASC",
        resultClass = Questions.class
)
public class Questions {
    public final static String SEARCH_NATIVE_NAMED_QUERY = "questions.native.search";

    /**
     * Unique id for questions entity, primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Question name
     */
    @NotEmpty
    @Length(max = 255)
    private String question;

    /**
     * Answer on question
     */
    @NotEmpty
    private String answer;

    /**
     * Livenshtein index
     */
    private int destinationIndex;

    /**
     * @return id value
     */
    public Long getId() {
        return id;
    }

    /**
     * @return question name
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question question name
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return answer for question
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer answer for question
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return Livenshtein destination index
     */
    public int getDestinationIndex() {
        return destinationIndex;
    }

    /**
     * @param destinationIndex Livenshtein destination index
     */
    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }
}
