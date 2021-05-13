/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.dbValues

internal fun DefaultSqlClientCommon.Properties.bindWhereParams(
        parameters: MapSqlParameterSource
) {
    with(this) {
        whereClauses
                .dbValues(tables)
                .forEach { dbValue -> parameters.addValue("k${index++}", dbValue) }
    }
}
