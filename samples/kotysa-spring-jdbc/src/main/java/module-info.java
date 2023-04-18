module kotysa.spring.jdbc.sample {
    requires java.sql;
    requires kotlin.stdlib;
    requires kotysa.spring.jdbc;
    requires spring.boot.autoconfigure;

    exports com.sample;
}