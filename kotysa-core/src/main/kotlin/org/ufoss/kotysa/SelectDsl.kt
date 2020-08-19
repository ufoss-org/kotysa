/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.KClass

/**
 * All methods return an unused value
 */
public class SelectDsl<T> internal constructor(
        private val init: SelectDslApi.(ValueProvider) -> T,
        private val tables: Tables
) : SelectDslApi(), ValueProvider {

    private val fieldAccess = FieldAccess(tables.allColumns, tables.dbType)
    private var fieldIndex = 0
    private val fieldIndexMap = mutableMapOf<Field, Int>()
    private val selectedGetters = mutableListOf<(Any) -> Any?>()
    private val selectedFields = mutableListOf<Field>()
    private val selectedTables = mutableSetOf<AliasedTable<*>>()

    override fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)?, alias: String?): Long {
        if (dsl == null) {
            tables.checkTable(resultClass)
        }
        val columnField = dsl?.invoke(SimpleFieldProvider(tables.allColumns, tables.dbType))
        val aliasedTable = AliasedTable(tables.getTable(resultClass), alias)
        if (!selectedTables.contains(aliasedTable)) {
            selectedTables.add(aliasedTable)
        }
        addField(CountField(dsl, columnField, alias))
        return Long.MAX_VALUE
    }

    override operator fun <T : Any> get(getter: (T) -> String, alias: String?): String {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return ""
    }

    override operator fun <T : Any> get(getter: (T) -> String?, alias: String?, `_`: Nullable): String? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime, alias: String?): LocalDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalDateTime.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime?, alias: String?, `_`: Nullable): LocalDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String?): kotlinx.datetime.LocalDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return kotlinx.datetime.LocalDateTime(2016, 2, 15, 16, 57, 0, 0)
    }

    override operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String?, `_`: Nullable
    ): kotlinx.datetime.LocalDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDate, alias: String?): LocalDate {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalDate.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDate?, alias: String?, `_`: Nullable): LocalDate? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDate, alias: String?): kotlinx.datetime.LocalDate {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return kotlinx.datetime.LocalDate(2016, 2, 15)
    }

    override operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String?, `_`: Nullable
    ): kotlinx.datetime.LocalDate? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime, alias: String?): OffsetDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return OffsetDateTime.now()
    }

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime?, alias: String?, `_`: Nullable): OffsetDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalTime, alias: String?): LocalTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalTime.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalTime?, alias: String?, `_`: Nullable): LocalTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> Boolean, alias: String?): Boolean {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return false
    }

    override fun <T : Any> get(getter: (T) -> UUID, alias: String?): UUID {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
    }

    override fun <T : Any> get(getter: (T) -> UUID?, alias: String?, `_`: Nullable): UUID? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override fun <T : Any> get(getter: (T) -> Int, alias: String?): Int {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return 1
    }

    override fun <T : Any> get(getter: (T) -> Int?, alias: String?, `_`: Nullable): Int? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    private fun <T : Any> addColumnField(getter: (T) -> Any?, columnField: ColumnField<*, *>, alias: String?) {
        addFieldAndGetter(columnField, getter)
        val aliasedTable = AliasedTable(columnField.column.table, alias)
        if (!selectedTables.contains(aliasedTable)) {
            selectedTables.add(aliasedTable)
        }
    }

    private fun <T : Any> addFieldAndGetter(field: Field, getter: (T) -> Any?) {
        require(!selectedGetters.contains(getter)) {
            "\"$getter\" is already selected, you cannot select the same field multiple times"
        }
        @Suppress("UNCHECKED_CAST")
        selectedGetters.add(getter as (Any) -> Any?)
        addField(field)
    }

    private fun addField(field: Field) {
        selectedFields.add(field)
        fieldIndexMap[field] = fieldIndex++
    }

    internal fun initialize(): SelectInformation<T> {
        init(this)
        require(fieldIndexMap.isNotEmpty()) { "Table must declare at least one column" }
        return SelectInformation(fieldIndexMap, selectedFields, selectedTables, init)
    }
}
