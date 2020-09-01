/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.AbstractRow
import org.ufoss.kotysa.DefaultSqlClientSelect
import org.ufoss.kotysa.Field
import io.r2dbc.spi.Row
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.RowsFetchSpec
import java.time.LocalDate
import java.time.LocalDateTime


internal abstract class AbstractSqlClientSelectR2dbc protected constructor() : DefaultSqlClientSelect() {

    protected interface Return<T : Any> : DefaultSqlClientSelect.Return<T> {

        val client: DatabaseClient

        fun fetch(): RowsFetchSpec<T> = with(properties) {
            var executeSpec = client.sql(selectSql())

            whereClauses
                    .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                    .forEachIndexed { index, value ->
                        executeSpec = executeSpec.bind(index, tables.getDbValue(value)!!)
                    }

            executeSpec.map { r, _ ->
                val row = R2dbcRow(r, selectInformation.fieldIndexMap)
                selectInformation.select(row, row)
            }
        }

        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        private class R2dbcRow(
                private val r2bcRow: Row,
                fieldIndexMap: Map<Field, Int>
        ) : AbstractRow(fieldIndexMap) {
            override fun <T> get(index: Int, type: Class<T>) =
                    when {
                        kotlinx.datetime.LocalDate::class.java.isAssignableFrom(type) ->
                            r2bcRow.get(index, LocalDate::class.java)?.toKotlinLocalDate()
                        kotlinx.datetime.LocalDateTime::class.java.isAssignableFrom(type) ->
                            r2bcRow.get(index, LocalDateTime::class.java)?.toKotlinLocalDateTime()
                        else -> r2bcRow.get(index, type)
                    } as T
        }
    }
}
