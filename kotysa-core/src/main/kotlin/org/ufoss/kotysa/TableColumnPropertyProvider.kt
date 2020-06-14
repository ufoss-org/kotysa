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


public interface TableColumnPropertyProvider {

    public operator fun <T : Any> get(getter: (T) -> String): NotNullStringColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> String?): NullableStringColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime): NotNullLocalDateTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime?): NullableLocalDateTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDate): NotNullLocalDateColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalDate?): NullableLocalDateColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime): NotNullOffsetDateTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime?): NullableOffsetDateTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalTime): NotNullLocalTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> LocalTime?): NullableLocalTimeColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> Boolean): NotNullBooleanColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> UUID): NotNullUuidColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> UUID?): NullableUuidColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> Int): NotNullIntColumnProperty<T>

    public operator fun <T : Any> get(getter: (T) -> Int?): NullableIntColumnProperty<T>
}

public abstract class AbstractTableColumnPropertyProvider internal constructor() : TableColumnPropertyProvider {
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
}
