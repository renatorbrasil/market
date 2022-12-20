package com.eventsourcing.market.config.mongo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Piotr `Athlan` Pelczar
 * Taken from https://athlan.pl/spring-data-mongodb-remove-_class-define-explicitly/
 * and https://gist.github.com/athlan/6497c74cc515131e1336
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocumentType {
    public String value() default "";
}
