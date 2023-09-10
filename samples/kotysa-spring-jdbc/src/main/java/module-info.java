module kotysa.spring.jdbc.sample {
    requires java.sql;
    requires kotlin.stdlib;
    requires kotysa.core;
    requires kotysa.spring.jdbc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.jdbc;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.web;
    requires spring.webmvc;

    exports com.sample;
}