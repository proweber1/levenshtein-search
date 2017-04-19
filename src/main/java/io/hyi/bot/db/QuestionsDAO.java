package io.hyi.bot.db;

import io.hyi.bot.core.Questions;

import java.util.Optional;

public interface QuestionsDAO {
    Optional<Questions> findWithLevenshteinAlgorithm(String query);
}
