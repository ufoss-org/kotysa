/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface Field {
    public val fieldName: String
    public val alias: String?
}


public interface NotNullField : Field


public interface NullableField : Field

public class CountField<T : Any, U> internal constructor(
        internal val dsl: ((FieldProvider) -> ColumnField<T, *>)?,
        columnField: ColumnField<T, U>?,
        override val alias: String?
) : NotNullField {
    override val fieldName: String

    init {
        val counted = columnField?.fieldName ?: "*"
        fieldName = "COUNT($counted)"
    }
}


@Suppress("UNCHECKED_CAST")
public abstract class ColumnField<T : Any, U> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> Any?,
        final override val alias: String?,
        internal val dbType: DbType
) : Field {

    internal val column: Column<T, U>

    init {
        if (alias != null) {
            require(alias.isNotBlank()) { "An alias must not be empty or blank" }
        }
        require(availableColumns.containsKey(getter)) { "Requested field \"$getter\" is not mapped" }
        column = availableColumns[getter]!! as Column<T, U>
    }

    override val fieldName: String =
            if (alias != null) {
                "$alias."
            } else {
                "${column.table.name}."
            } + column.name
}


public class NotNullStringColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> String,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, String>(availableColumns, getter, alias, dbType), NotNullField


public class NullableStringColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> String?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, String?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalDateTime,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalDateTime>(availableColumns, getter, alias, dbType), NotNullField


public class NullableLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalDateTime?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalDateTime?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalDate,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalDate>(availableColumns, getter, alias, dbType), NotNullField


public class NullableLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalDate?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalDate?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> OffsetDateTime,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, OffsetDateTime>(availableColumns, getter, alias, dbType), NotNullField


public class NullableOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> OffsetDateTime?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, OffsetDateTime?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalTime,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalTime>(availableColumns, getter, alias, dbType), NotNullField


public class NullableLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> LocalTime?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, LocalTime?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullBooleanColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> Boolean,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, Boolean>(availableColumns, getter, alias, dbType), NotNullField


public class NotNullUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> UUID,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, UUID>(availableColumns, getter, alias, dbType), NotNullField


public class NullableUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> UUID?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, UUID?>(availableColumns, getter, alias, dbType), NullableField


public class NotNullIntColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> Int,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, Int>(availableColumns, getter, alias, dbType), NotNullField


public class NullableIntColumnField<T : Any> internal constructor(
        availableColumns: Map<out (Any) -> Any?, Column<*, *>>,
        getter: (T) -> Int?,
        dbType: DbType,
        alias: String? = null
) : ColumnField<T, Int?>(availableColumns, getter, alias, dbType), NullableField
