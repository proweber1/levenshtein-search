package io.hyi.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

class BotConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    @JsonProperty
    private int maxDestination = 30;

    DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    int getMaxDestination() {
        return maxDestination;
    }

    public void setMaxDestination(int maxDestination) {
        this.maxDestination = maxDestination;
    }
}
