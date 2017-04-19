package io.hyi.bot.db;

import io.dropwizard.hibernate.AbstractDAO;
import io.hyi.bot.core.Questions;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class QuestionsDAOImpl extends AbstractDAO<Questions> implements QuestionsDAO {
    private final int maxDestination;

    public QuestionsDAOImpl(int maxDestination, SessionFactory sessionFactory) {
        super(sessionFactory);
        this.maxDestination = maxDestination;
    }

    /**
     * Calls a named query and tries to find answers to a user question
     *
     * @param searchQuery User question
     * @return Possible answer
     */
    public Optional<Questions> findWithLevenshteinAlgorithm(String searchQuery) {
        return query("SELECT q FROM Questions q WHERE levenshtein(lower(q.question), lower(:query)) BETWEEN 0 AND :maxDestination")
                .setParameter("query", searchQuery)
                .setParameter("maxDestination", maxDestination)
                .uniqueResultOptional();
    }
}
