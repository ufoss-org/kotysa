/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public enum class Module {
    SQLITE, JDBC, R2DBC, SPRING_JDBC, SPRING_R2DBC, VERTX_SQL_CLIENT
}

internal fun Module.isR2dbcOrVertxSqlClient() = this == Module.R2DBC || this == Module.VERTX_SQL_CLIENT
