/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.ForeignKey
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a H2 Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
 * @param T Entity type associated with this table
 */
public abstract class H2Table<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: H2Table<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U =
        this.also {
            foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
        }

    protected fun varchar(
        getter: (T) -> String,
        columnName: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNotNull<T> =
        StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNullable<T> =
        StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T> = IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
            LongDbBigIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun bigInt(
            getter: (T) -> Long?,
            columnName: String? = null,
            defaultValue: Long? = null
    ): LongDbBigIntColumnNullable<T> =
            LongDbBigIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementBigInt(getter: (T) -> Long?, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
            LongDbBigIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun boolean(getter: (T) -> Boolean, columnName: String? = null): BooleanDbBooleanColumnNotNull<T> =
        BooleanDbBooleanColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbDateColumnNotNull<T> =
        LocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(
        getter: (T) -> LocalDate?,
        columnName: String? = null,
        defaultValue: LocalDate? = null
    ): LocalDateDbDateColumnNullable<T> =
        LocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun dateTime(
        getter: (T) -> LocalDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalDateTimeDbDateTimeColumnNotNull<T> =
        LocalDateTimeDbDateTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun dateTime(
        getter: (T) -> LocalDateTime?,
        columnName: String? = null,
        defaultValue: LocalDateTime? = null,
        precision: Int? = null
    ): LocalDateTimeDbDateTimeColumnNullable<T> =
        LocalDateTimeDbDateTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestamp(
        getter: (T) -> LocalDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalDateTimeDbTimestampColumnNotNull<T> =
        LocalDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestamp(
        getter: (T) -> LocalDateTime?,
        columnName: String? = null,
        defaultValue: LocalDateTime? = null,
        precision: Int? = null
    ): LocalDateTimeDbTimestampColumnNullable<T> =
        LocalDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(
        getter: (T) -> OffsetDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T> =
        OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(
        getter: (T) -> OffsetDateTime?,
        columnName: String? = null,
        defaultValue: OffsetDateTime? = null,
        precision: Int? = null
    ): OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T> =
        OffsetDateTimeDbTimestampWithTimeZoneColumnNullable(
            getter,
            columnName,
            defaultValue,
            precision
        ).also { addColumn(it) }

    protected fun time(
        getter: (T) -> LocalTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalTimeDbTimeColumnNotNull<T> =
        LocalTimeDbTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun time(
        getter: (T) -> LocalTime?,
        columnName: String? = null,
        defaultValue: LocalTime? = null,
        precision: Int? = null
    ): LocalTimeDbTimeColumnNullable<T> =
        LocalTimeDbTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun uuid(getter: (T) -> UUID, columnName: String? = null): UuidDbUuidColumnNotNull<T> =
        UuidDbUuidColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun uuid(
        getter: (T) -> UUID?,
        columnName: String? = null,
        defaultValue: UUID? = null
    ): UuidDbUuidColumnNullable<T> =
        UuidDbUuidColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }
}
