/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * All supported SQL types
 */
public enum class SqlType(internal val fullType: String) {
    // text
    VARCHAR("VARCHAR"),
    TINYTEXT("TINYTEXT"),
    TEXT("TEXT"),
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGTEXT("LONGTEXT"),

    // numbers
    BIT("BIT"),
    INT("INTEGER"),
    BIGINT("BIGINT"),
    SERIAL("SERIAL"),
    BIGSERIAL("BIGSERIAL"),
    FLOAT("FLOAT"),
    DOUBLE_PRECISION("DOUBLE PRECISION"),
    REAL("REAL"),
    DECIMAL("DECIMAL"),
    NUMERIC("NUMERIC"),

    // date
    TIMESTAMP("TIMESTAMP"),
    DATE("DATE"),
    DATE_TIME("DATETIME"),
    TIME("TIME"),
    TIMESTAMP_WITH_TIME_ZONE("TIMESTAMP WITH TIME ZONE"),

    BOOLEAN("BOOLEAN"),

    UUID("UUID"),

    // Binary
    BLOB("BLOB"),
    BINARY("BINARY"),
    BYTEA("BYTEA"),
    
    // Text search / full-text search
    TSVECTOR("TSVECTOR"),
}
