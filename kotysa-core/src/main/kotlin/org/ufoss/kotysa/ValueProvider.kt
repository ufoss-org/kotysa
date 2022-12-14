/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface ValueProvider {
    public operator fun <T : Any, U : Any> get(column: Column<T, U>): U?
}

internal class FieldValueProvider<T : Any> internal constructor(
        private val properties: DefaultSqlClientSelect.Properties<T>,
) : ValueProvider {
    private val selectedFieldNames = mutableListOf<String>()

    /*override fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)?, alias: String?): Long =
            this[fieldIndexMap.filterKeys { field ->
                field is CountField<*, *> && field.dsl == dsl && field.alias == alias
            }.values.first()]!!*/

    override fun <T : Any, U : Any> get(column: Column<T, U>): U {
        addSelectedFieldName(column.getFieldName(properties.tables.allColumns, properties.tables.dbType))
        val columnClass = column.getKotysaColumn(properties.tables.allColumns).columnClass
        @Suppress("UNCHECKED_CAST")
        return when (columnClass) {
            String::class -> ""
            LocalDateTime::class -> LocalDateTime.MAX
            LocalDate::class -> LocalDate.MAX
            OffsetDateTime::class -> OffsetDateTime.now()
            LocalTime::class -> LocalTime.MAX
            Boolean::class -> false
            UUID::class -> UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
            Int::class -> 42
            else -> when (columnClass.qualifiedName) {
                "kotlinx.datetime.LocalDate" -> kotlinx.datetime.LocalDate(2016, 2, 15)
                "kotlinx.datetime.LocalDateTime" -> kotlinx.datetime.LocalDateTime(2016, 2, 15, 16, 57, 0, 0)
                else -> throw RuntimeException("$columnClass is not supported yet")
            }
        } as U
    }

    private fun addSelectedFieldName(fieldName: String) {
        require(!selectedFieldNames.contains(fieldName)) {
            "\"$fieldName\" is already selected, you cannot select the same field multiple times"
        }
        selectedFieldNames.add(fieldName)
    }

    internal fun initialize(init: (ValueProvider) -> T): List<String> {
        init(this)
        return selectedFieldNames
    }
}
