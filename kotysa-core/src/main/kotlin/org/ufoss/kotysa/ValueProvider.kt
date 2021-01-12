/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public interface ValueProvider {
    public operator fun <T : Any, U : Any> get(column: ColumnNotNull<T, U>): U
    public operator fun <T : Any, U : Any> get(column: ColumnNullable<T, U>): U?
}

internal class FieldValueProvider<T> internal constructor(
        private val properties: DefaultSqlClientSelect.Properties<T>,
) : ValueProvider {
    private val selectedFieldNames = mutableListOf<String>()

    /*override fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)?, alias: String?): Long =
            this[fieldIndexMap.filterKeys { field ->
                field is CountField<*, *> && field.dsl == dsl && field.alias == alias
            }.values.first()]!!*/

    override fun <T : Any, U : Any> get(column: ColumnNotNull<T, U>): Nothing {
        addSelectedFieldName(column.getFieldName(properties.tables.allColumns))
        return null as Nothing
    }

    override fun <T : Any, U : Any> get(column: ColumnNullable<T, U>): U? {
        addSelectedFieldName(column.getFieldName(properties.tables.allColumns))
        return null
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
