package com.eventsourcing.market.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * @author Piotr `Athlan` Pelczar
 * Taken from https://athlan.pl/spring-data-mongodb-remove-_class-define-explicitly/
 * and https://gist.github.com/athlan/6497c74cc515131e1336
 */
@Configuration
public class MongoTypeConfig {

    @Autowired
    private MappingMongoConverter converter;

    @Autowired
    private MongoDatabaseFactory databaseFactory;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String[] basePackages = new String[] { "com.eventsourcing.market.*"};
        TypeInformationMapper typeMapper1 = new AnnotationTypeInformationMapper.Builder()
                .withBasePackages(basePackages).build();

        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(
                "type",
                Arrays.asList(typeMapper1));

        converter.setTypeMapper(typeMapper);

        return new MongoTemplate(databaseFactory, converter);
    }
}
