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
import kotlin.reflect.KFunction


@KotysaMarker
public abstract class TableDsl<T : Any, U : TableDsl<T, U>>(
        private val init: U.(TableColumnPropertyProvider) -> Unit,
        private val tableClass: KClass<T>
) : TableColumnPropertyProvider {

    public lateinit var name: String
    private val columns = mutableMapOf<(T) -> Any?, Column<T, *>>()
    public lateinit var primaryKey: PrimaryKey

    override fun <V : Any> get(getter: (V) -> String): NotNullStringColumnProperty<V> = NotNullStringColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> String?): NullableStringColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableStringColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> LocalDateTime): NotNullLocalDateTimeColumnProperty<V> = NotNullLocalDateTimeColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> LocalDateTime?): NullableLocalDateTimeColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableLocalDateTimeColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> LocalDate): NotNullLocalDateColumnProperty<V> = NotNullLocalDateColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> LocalDate?): NullableLocalDateColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableLocalDateColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> OffsetDateTime): NotNullOffsetDateTimeColumnProperty<V> = NotNullOffsetDateTimeColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> OffsetDateTime?): NullableOffsetDateTimeColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableOffsetDateTimeColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> LocalTime): NotNullLocalTimeColumnProperty<V> = NotNullLocalTimeColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> LocalTime?): NullableLocalTimeColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableLocalTimeColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> Boolean): NotNullBooleanColumnProperty<V> = NotNullBooleanColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> UUID): NotNullUuidColumnProperty<V> = NotNullUuidColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> UUID?): NullableUuidColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableUuidColumnProperty(getter)
    }

    override fun <V : Any> get(getter: (V) -> Int): NotNullIntColumnProperty<V> = NotNullIntColumnProperty(getter)

    override fun <V : Any> get(getter: (V) -> Int?): NullableIntColumnProperty<V> {
        checkNullableGetter(getter)
        return NullableIntColumnProperty(getter)
    }

    private fun <V : Any> checkNullableGetter(getter: (V) -> Any?) {
        if (getter !is KFunction<*>) {
            require(getter.toCallable().returnType.isMarkedNullable) { "\"$getter\" doesn't have a nullable return type" }
        }
    }

    public inline fun <reified V : Any> foreignKey(vararg columnProperties: ColumnProperty<T>): ForeignKeyBuilder<V> {
        return ForeignKeyBuilder()
    }

    protected fun addColumn(column: Column<T, *>) {
        require(!columns.containsKey(column.entityGetter)) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        require(tableClass.members.contains(column.entityGetter.toCallable())) {
            "Trying to map property \"${column.entityGetter}\", which is not a property of entity class \"${tableClass.qualifiedName}\""
        }
        if (column.isPrimaryKey) {
            check(!::primaryKey.isInitialized) {
                "Table must not declare more than one Primary Key"
            }
            primaryKey = SinglePrimaryKey(column.pkName, column)
        }
        columns[column.entityGetter] = column
    }

    @PublishedApi
    internal fun initialize(initialize: U): Table<*> {
        init(initialize, initialize)
        if (!::name.isInitialized) {
            name = tableClass.simpleName!!
        }
        require(::primaryKey.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }
        val table = TableImpl(tableClass, name, columns, primaryKey)
        // associate table to all its columns
        columns.forEach { (_, c) -> c.table = table }
        return table
    }
}
