/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.KFunction


public interface TableColumnPropertyProvider<T : Any> {

    public operator fun get(getter: (T) -> String): NotNullStringColumnProperty<T>

    public operator fun get(getter: (T) -> String?): NullableStringColumnProperty<T>

    public operator fun get(getter: (T) -> LocalDateTime): NotNullLocalDateTimeColumnProperty<T>

    public operator fun get(getter: (T) -> LocalDateTime?): NullableLocalDateTimeColumnProperty<T>

    public operator fun get(getter: (T) -> LocalDate): NotNullLocalDateColumnProperty<T>

    public operator fun get(getter: (T) -> LocalDate?): NullableLocalDateColumnProperty<T>

    public operator fun get(getter: (T) -> OffsetDateTime): NotNullOffsetDateTimeColumnProperty<T>

    public operator fun get(getter: (T) -> OffsetDateTime?): NullableOffsetDateTimeColumnProperty<T>

    public operator fun get(getter: (T) -> LocalTime): NotNullLocalTimeColumnProperty<T>

    public operator fun get(getter: (T) -> LocalTime?): NullableLocalTimeColumnProperty<T>

    public operator fun get(getter: (T) -> Boolean): NotNullBooleanColumnProperty<T>

    public operator fun get(getter: (T) -> UUID): NotNullUuidColumnProperty<T>

    public operator fun get(getter: (T) -> UUID?): NullableUuidColumnProperty<T>

    public operator fun get(getter: (T) -> Int): NotNullIntColumnProperty<T>

    public operator fun get(getter: (T) -> Int?): NullableIntColumnProperty<T>
}

public abstract class AbstractTableColumnPropertyProvider<T : Any> internal constructor() : TableColumnPropertyProvider<T> {
    override fun get(getter: (T) -> String): NotNullStringColumnProperty<T> = NotNullStringColumnProperty(getter)

    override fun get(getter: (T) -> String?): NullableStringColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableStringColumnProperty(getter)
    }

    override fun get(getter: (T) -> LocalDateTime): NotNullLocalDateTimeColumnProperty<T> = NotNullLocalDateTimeColumnProperty(getter)

    override fun get(getter: (T) -> LocalDateTime?): NullableLocalDateTimeColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableLocalDateTimeColumnProperty(getter)
    }

    override fun get(getter: (T) -> LocalDate): NotNullLocalDateColumnProperty<T> = NotNullLocalDateColumnProperty(getter)

    override fun get(getter: (T) -> LocalDate?): NullableLocalDateColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableLocalDateColumnProperty(getter)
    }

    override fun get(getter: (T) -> OffsetDateTime): NotNullOffsetDateTimeColumnProperty<T> = NotNullOffsetDateTimeColumnProperty(getter)

    override fun get(getter: (T) -> OffsetDateTime?): NullableOffsetDateTimeColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableOffsetDateTimeColumnProperty(getter)
    }

    override fun get(getter: (T) -> LocalTime): NotNullLocalTimeColumnProperty<T> = NotNullLocalTimeColumnProperty(getter)

    override fun get(getter: (T) -> LocalTime?): NullableLocalTimeColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableLocalTimeColumnProperty(getter)
    }

    override fun get(getter: (T) -> Boolean): NotNullBooleanColumnProperty<T> = NotNullBooleanColumnProperty(getter)

    override fun get(getter: (T) -> UUID): NotNullUuidColumnProperty<T> = NotNullUuidColumnProperty(getter)

    override fun get(getter: (T) -> UUID?): NullableUuidColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableUuidColumnProperty(getter)
    }

    override fun get(getter: (T) -> Int): NotNullIntColumnProperty<T> = NotNullIntColumnProperty(getter)

    override fun get(getter: (T) -> Int?): NullableIntColumnProperty<T> {
        checkNullableGetter(getter)
        return NullableIntColumnProperty(getter)
    }

    private fun checkNullableGetter(getter: (T) -> Any?) {
        if (getter !is KFunction<*>) {
            require(getter.toCallable().returnType.isMarkedNullable) { "\"$getter\" doesn't have a nullable return type" }
        }
    }
}
