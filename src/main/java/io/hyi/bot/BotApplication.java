package io.hyi.bot;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.hyi.bot.core.Questions;
import io.hyi.bot.db.QuestionsDAO;
import io.hyi.bot.db.QuestionsDAOImpl;
import io.hyi.bot.resources.QuestionsResource;

public class BotApplication extends Application<BotConfiguration> {
    private final static String DB_SERVICE_NAME = "postgres";

    private final HibernateBundle<BotConfiguration> hibernate = buildHibernate();

    /**
     * @return Hibernate bundle
     */
    private HibernateBundle<BotConfiguration> buildHibernate() {
        return new HibernateBundle<BotConfiguration>(Questions.class) {
            public PooledDataSourceFactory getDataSourceFactory(BotConfiguration configuration) {
                return configuration.getDatabase();
            }
        };
    }

    /**
     * @return Migrations bundle
     */
    private MigrationsBundle<BotConfiguration> buildMigrationsBundle() {
        return new MigrationsBundle<BotConfiguration>() {
            public PooledDataSourceFactory getDataSourceFactory(BotConfiguration configuration) {
                return configuration.getDatabase();
            }
        };
    }

    @Override
    public void initialize(Bootstrap<BotConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(buildMigrationsBundle());
    }

    /**
     * {@inheritDoc}
     */
    public void run(BotConfiguration botConfiguration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        factory.build(environment, botConfiguration.getDatabase(), DB_SERVICE_NAME);

        final QuestionsDAO questionsDAO = new QuestionsDAOImpl(
                botConfiguration.getMaxDestination(),
                hibernate.getSessionFactory()
        );
        environment.jersey().register(new QuestionsResource(questionsDAO));
    }

    public static void main(String[] args) throws Exception {
        new BotApplication().run(args);
    }
}
