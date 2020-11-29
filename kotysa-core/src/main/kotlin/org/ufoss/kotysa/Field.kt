/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public interface Field<T : Any>{
    public fun fields(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): List<String>
}

public interface FieldNotNull<T : Any> : Field<T>

public interface FieldNullable<T : Any> : Field<T>

public class CountField<T : Any, U : Any> internal constructor(
        private val column: Column<T, U>?
) : FieldNotNull<Int> {
    override fun fields(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): List<String> {
        val counted = column?.getFieldName(availableColumns) ?: "*"
        return listOf("COUNT($counted)")
    }
}

public sealed class ColumnField<T : Any, U: Any> (
        private val column: Column<T, U>
) : Field<U> {
    final override fun fields(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): List<String> =
            listOf(column.getFieldName(availableColumns))

    internal fun fieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>) =
            column.getFieldName(availableColumns)
}

public class ColumnFieldNotNull<T : Any, U : Any> internal constructor(
        column: ColumnNotNull<T, U>
) : ColumnField<T, U>(column), FieldNotNull<U>

public class ColumnFieldNullable<T : Any, U : Any> internal constructor(
        private val column: ColumnNullable<T, U>
) : ColumnField<T, U>(column), FieldNotNull<U>

/**
 * Not sure if null or not
 */
public class TableField<T : Any> internal constructor(
        internal val table: Table<T>
) : Field<T> {
    override fun fields(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): List<String> =
            table.columns.map { column -> column.getFieldName(availableColumns) }
}

// Extension functions

internal fun <T : Any, U : Any> Column<T, U>.toField(): ColumnField<T, U> {
    if (this is ColumnNotNull<T, U>) {
        return ColumnFieldNotNull(this)
    }
    return ColumnFieldNullable(this as ColumnNullable<T, U>)
}

private fun Column<*, *>.getFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    val kotysaColumn = this.getKotysaColumn(availableColumns)
    val kotysaTable = kotysaColumn.table
    return if (kotysaTable is AliasedTable<*>) {
        "${kotysaTable.alias}."
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
