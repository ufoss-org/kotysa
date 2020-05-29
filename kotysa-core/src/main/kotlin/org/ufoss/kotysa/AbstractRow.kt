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
 * Represents a row returned from a query.
 */
@Suppress("UNCHECKED_CAST")
public abstract class AbstractRow(private val fieldIndexMap: Map<Field, Int>) : SelectDslApi(), ValueProvider {

    override fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)?, alias: String?): Long =
            this[fieldIndexMap.filterKeys { field ->
                field is CountField<*, *> && field.dsl == dsl && field.alias == alias
            }.values.first()]!!

    override operator fun <T : Any> get(getter: (T) -> String, alias: String?): String =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> String?, alias: String?, `_`: Nullable): String? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime, alias: String?): LocalDateTime =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> LocalDateTime?, alias: String?, `_`: Nullable): LocalDateTime? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> LocalDate, alias: String?): LocalDate =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> LocalDate?, alias: String?, `_`: Nullable): LocalDate? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime, alias: String?): OffsetDateTime =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> OffsetDateTime?, alias: String?, `_`: Nullable): OffsetDateTime? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> LocalTime, alias: String?): LocalTime =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> LocalTime?, alias: String?, `_`: Nullable): LocalTime? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> Boolean, alias: String?): Boolean =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> UUID, alias: String?): UUID =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> UUID?, alias: String?, `_`: Nullable): UUID? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    override operator fun <T : Any> get(getter: (T) -> Int, alias: String?): Int =
            this[getIndexdByGetterAndAlias(getter, alias)]!!

    override operator fun <T : Any> get(getter: (T) -> Int?, alias: String?, `_`: Nullable): Int? =
            this[getIndexdByGetterAndAlias(getter, alias)]

    /**
     * Returns the element at the specified index in the list of returned fields of row
     */
    protected abstract fun <T> get(index: Int, type: Class<T>): T?

    private inline operator fun <reified T> get(index: Int) = get(index, T::class.java)

    private fun <T : Any, U> getIndexdByGetterAndAlias(getter: (T) -> U, alias: String?) =
            fieldIndexMap.filterKeys { field ->
                field is ColumnField<*, *> && field.column.entityGetter == getter && field.alias == alias
            }.values.first()
}
