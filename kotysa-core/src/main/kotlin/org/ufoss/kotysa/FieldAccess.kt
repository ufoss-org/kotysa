/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa
/*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


@Suppress("UNCHECKED_CAST")
internal class FieldAccess internal constructor(
        private val availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        private val dbType: DbType,
) {

    internal fun <T : Any> getField(column: StringColumnNotNull<T>) =
            NotNullStringColumnField(availableColumns, column, dbType)

    internal fun <T : Any> getField(column: StringColumnNullable<T>) =
            NullableStringColumnField(availableColumns, column, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalDateTime) =
            NotNullLocalDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalDateTime?) =
            NullableLocalDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> kotlinx.datetime.LocalDateTime) =
            NotNullKotlinxLocalDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> kotlinx.datetime.LocalDateTime?) =
            NullableKotlinxLocalDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalDate) =
            NotNullLocalDateColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalDate?) =
            NullableLocalDateColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> kotlinx.datetime.LocalDate) =
            NotNullKotlinxLocalDateColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> kotlinx.datetime.LocalDate?) =
            NullableKotlinxLocalDateColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> OffsetDateTime) =
            NotNullOffsetDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> OffsetDateTime?) =
            NullableOffsetDateTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalTime) =
            NotNullLocalTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> LocalTime?) =
            NullableLocalTimeColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> Boolean) =
            NotNullBooleanColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> UUID) =
            NotNullUuidColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> UUID?) =
            NullableUuidColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> Int) =
            NotNullIntColumnField(availableColumns, getter, dbType)

    internal fun <T : Any> getField(getter: (T) -> Int?) =
            NullableIntColumnField(availableColumns, getter, dbType)
}*/
