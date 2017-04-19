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
    @SuppressWarnings("unchecked")
    public Optional<Questions> findWithLevenshteinAlgorithm(String searchQuery) {
        return (Optional<Questions>) currentSession().getNamedNativeQuery(Questions.SEARCH_NATIVE_NAMED_QUERY)
                .setParameter("question", searchQuery)
                .setParameter("maxDestination", maxDestination)
                .setMaxResults(1)
                .uniqueResultOptional();
    }
}
