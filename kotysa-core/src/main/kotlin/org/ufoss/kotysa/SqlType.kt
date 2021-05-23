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
    TEXT("TEXT"),

    // numbers
    BIT("BIT"),
    INT("INTEGER"),
    BIGINT("BIGINT"),
    SERIAL("SERIAL"),
    BIGSERIAL("BIGSERIAL"),

    // date
    TIMESTAMP("TIMESTAMP"),
    DATE("DATE"),
    DATE_TIME("DATETIME"),
    TIME("TIME"),
    TIMESTAMP_WITH_TIME_ZONE("TIMESTAMP WITH TIME ZONE"),

    BOOLEAN("BOOLEAN"),

    UUID("UUID")
}
