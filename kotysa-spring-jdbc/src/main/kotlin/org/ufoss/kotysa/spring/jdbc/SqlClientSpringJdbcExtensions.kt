/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.set
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.dbValues

internal fun DefaultSqlClientCommon.Properties.springJdbcBindParams(
    parameters: MapSqlParameterSource
) {
    dbValues()
        .forEach { dbValue -> parameters["k${index++}"] = dbValue }
}
