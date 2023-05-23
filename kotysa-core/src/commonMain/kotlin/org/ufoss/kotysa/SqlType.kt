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
    VARCHAR2("VARCHAR2"),
    TINYTEXT("TINYTEXT"),
    TEXT("TEXT"),
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGTEXT("LONGTEXT"),

    // numbers
    BIT("BIT"),
    BINARY_FLOAT("BINARY_FLOAT"),
    BINARY_DOUBLE("BINARY_DOUBLE"),
    INT("INTEGER"),
    BIGINT("BIGINT"),
    SERIAL("SERIAL"),
    BIGSERIAL("BIGSERIAL"),
    FLOAT("FLOAT"),
    DOUBLE_PRECISION("DOUBLE PRECISION"),
    REAL("REAL"),
    DECIMAL("DECIMAL"),
    NUMERIC("NUMERIC"),
    NUMBER("NUMBER"),

    // date
    TIMESTAMP("TIMESTAMP"),
    DATE("DATE"),
    DATE_TIME("DATETIME"),
    TIME("TIME"),
    TIMESTAMP_WITH_TIME_ZONE("TIMESTAMP WITH TIME ZONE"),
    DATETIMEOFFSET("DATETIMEOFFSET"),

    BOOLEAN("BOOLEAN"),

    UUID("UUID"),
    UNIQUEIDENTIFIER("UNIQUEIDENTIFIER"),

    // Binary
    BLOB("BLOB"),
    BINARY("BINARY"),
    BYTEA("BYTEA"),
    RAW("RAW"),
    
    // Text search / full-text search
    TSVECTOR("TSVECTOR"),
}
