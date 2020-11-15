/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

internal fun Column<*, *>?.getCountFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    val counted = this?.getFieldName(availableColumns) ?: "*"
    return "COUNT($counted)"
}

internal fun Column<*, *>.getFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    val kotysaColumn = requireNotNull(availableColumns[this]) { "Requested column \"$this\" is not mapped" }
    return if (this is AliasedTable<*>) {
        "$alias."
    } else {
        "${kotysaColumn.table.name}."
    } + kotysaColumn.name
}

/*
public interface Field {
    public val fieldName: String
}


public interface NotNullField : Field


public interface NullableField : Field

public class CountField<T : Any, U : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: Column<T, U>?,
        internal val dbType: DbType
) : NotNullField {
    override val fieldName: String

    init {
        val counted = if (column != null) {
            column.getFieldName()
        } else {
            "*"
        }
        fieldName = "COUNT($counted)"
    }
}


@Suppress("UNCHECKED_CAST")
public abstract class ColumnField<T : Any, U : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: Column<T, U>,
        internal val dbType: DbType
) : Field {

    internal val kotysaColumn: KotysaColumn<T, U>

    init {
        require(availableColumns.containsKey(column)) { "Requested column \"$column\" is not mapped" }
        kotysaColumn = availableColumns[column] as KotysaColumn<T, U>
    }

    override val fieldName: String = "${kotysaColumn.table.name}.${kotysaColumn.name}"
}


public class NotNullStringColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: StringColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, String>(availableColumns, column, dbType), NotNullField


public class NullableStringColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: StringColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, String>(availableColumns, column, dbType), NullableField


public class NotNullLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalDateTime>(availableColumns, column, dbType), NotNullField


public class NullableLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalDateTime>(availableColumns, column, dbType), NullableField

public class NotNullKotlinxLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDateTime>(availableColumns, column, dbType), NotNullField


public class NullableKotlinxLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDateTime>(availableColumns, column, dbType), NullableField


public class NotNullLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalDate>(availableColumns, column, dbType), NotNullField


public class NullableLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalDate>(availableColumns, column, dbType), NullableField

public class NotNullKotlinxLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDate>(availableColumns, column, dbType), NotNullField


public class NullableKotlinxLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDate>(availableColumns, column, dbType), NullableField


public class NotNullOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: OffsetDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, OffsetDateTime>(availableColumns, column, dbType), NotNullField


public class NullableOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: OffsetDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, OffsetDateTime>(availableColumns, column, dbType), NullableField


public class NotNullLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalTime>(availableColumns, column, dbType), NotNullField


public class NullableLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalTime>(availableColumns, column, dbType), NullableField


public class NotNullBooleanColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: BooleanColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, Boolean>(availableColumns, column, dbType), NotNullField


public class NotNullUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: UuidColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, UUID>(availableColumns, column, dbType), NotNullField


public class NullableUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: UuidColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, UUID>(availableColumns, column, dbType), NullableField


public class NotNullIntColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: IntColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, Int>(availableColumns, column, dbType), NotNullField


public class NullableIntColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: IntColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, Int>(availableColumns, column, dbType), NullableField
*/
