/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa
/*
/**
 * All methods return an unused value
 */
public class SelectDsl<T> internal constructor(
        private val init: SelectDslApi.() -> T,
        private val tables: Tables
) : SelectDslApi {

    private val fieldAccess = FieldAccess(tables.allColumns, tables.dbType)
    private var fieldIndex = 0
    private val fieldIndexMap = mutableMapOf<Field, Int>()
    private val selectedColumns = mutableListOf<Column<*, *>>()
    private val selectedFields = mutableListOf<Field>()

    override fun count(tableOrColumn: TableOrColumn): Long {
        val columnField = dsl?.invoke(SimpleFieldProvider(tables.allColumns, tables.dbType))
        val aliasedTable = AliasedKotysaTableOld(tables.getTable(resultClass), alias)
        if (!selectedTables.contains(aliasedTable)) {
            selectedTables.add(aliasedTable)
        }
        addField(CountField(dsl, columnField, alias))
        return Long.MAX_VALUE
    }

    override operator fun <T : Any> get(getter: (T) -> String): String {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return ""
    }

    override operator fun <T : Any> get(getter: (T) -> String?, `_`: Nullable): String? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime): LocalDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalDateTime.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime?, `_`: Nullable): LocalDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDateTime): kotlinx.datetime.LocalDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return kotlinx.datetime.LocalDateTime(2016, 2, 15, 16, 57, 0, 0)
    }

    override operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, `_`: Nullable
    ): kotlinx.datetime.LocalDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDate): LocalDate {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalDate.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalDate?, `_`: Nullable): LocalDate? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDate): kotlinx.datetime.LocalDate {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return kotlinx.datetime.LocalDate(2016, 2, 15)
    }

    override operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate?, `_`: Nullable
    ): kotlinx.datetime.LocalDate? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime): OffsetDateTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return OffsetDateTime.now()
    }

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime?, `_`: Nullable): OffsetDateTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> LocalTime): LocalTime {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return LocalTime.MAX
    }

    override operator fun <T : Any> get(getter: (T) -> LocalTime?, `_`: Nullable): LocalTime? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override operator fun <T : Any> get(getter: (T) -> Boolean): Boolean {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return false
    }

    override fun <T : Any> get(getter: (T) -> UUID): UUID {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f")
    }

    override fun <T : Any> get(getter: (T) -> UUID?, `_`: Nullable): UUID? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override fun <T : Any> get(getter: (T) -> Int): Int {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return 1
    }

    override fun <T : Any> get(getter: (T) -> Int?, `_`: Nullable): Int? {
        val field = fieldAccess.getField(getter, alias)
        addColumnField(getter, field, alias)
        return null
    }

    override fun <T : Any> StringColumnNotNull<T>.value(): String {
        val field = fieldAccess.getField(this)
        addFieldAndColumn(field, this)
        return ""
    }

    private fun <T : Any> addFieldAndColumn(field: Field, column: Column<T, *>) {
        require(!selectedColumns.contains(column)) {
            "\"$column\" is already selected, you cannot select the same field multiple times"
        }
        selectedColumns.add(column)
        addField(field)
    }

    private fun addField(field: Field) {
        selectedFields.add(field)
        fieldIndexMap[field] = fieldIndex++
    }

    internal fun initialize(): SelectInformation<T> {
        init(this)
        require(fieldIndexMap.isNotEmpty()) { "Table must declare at least one column" }
        return SelectInformation(fieldIndexMap, selectedFields, init)
    }
}*/
