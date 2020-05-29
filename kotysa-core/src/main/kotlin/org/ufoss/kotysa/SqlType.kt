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
    INTEGER("INTEGER"),
    SERIAL("SERIAL"),

    // date
    TIMESTAMP("TIMESTAMP"),
    DATE("DATE"),
    DATE_TIME("DATETIME"),
    TIME("TIME"),
    TIMESTAMP_WITH_TIME_ZONE("TIMESTAMP WITH TIME ZONE"),
    TIME9("TIME(9)"), // time9 with fractional seconds precision to match with java.time.LocalTime's value

    BOOLEAN("BOOLEAN"),

    UUID("UUID")
}
