package com.codetest.app.myretail.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

public class MongoConfig {
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "productdb";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        final MongoClientOptions options = MongoClientOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .retryWrites(true)
                .build();
        final MongoClient mongoClient = new MongoClient(MONGO_DB_URL, options);
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return mongoTemplate;
    }
}
