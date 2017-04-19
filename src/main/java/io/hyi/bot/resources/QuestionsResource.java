package io.hyi.bot.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.hyi.bot.core.Questions;
import io.hyi.bot.db.QuestionsDAO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * This is a simple drop wizard controller that uses Jersey.
 * This controller encapsulates all methods that can work with questions
 */
@Path("/questions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionsResource {
    private final QuestionsDAO questionsDAO;

    /**
     * @param questionsDAO Used to search for answers
     */
    public QuestionsResource(QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    /**
     * Attempts to find the answer to a user question in the database
     *
     * @param query User question
     * @return Answer on user question
     */
    @GET
    @Timed
    @UnitOfWork
    public Questions find(@NotEmpty @QueryParam("query") String query) {
        Optional<Questions> question = questionsDAO.findWithLevenshteinAlgorithm(query);
        
        if (!question.isPresent()) {
            throw new NotFoundException("Answer for this question not found!");
        }
        
        return question.get();
    }
}
