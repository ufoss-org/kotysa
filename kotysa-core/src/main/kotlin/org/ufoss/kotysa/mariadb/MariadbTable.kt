/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mariadb

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.ForeignKey
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Represents a MariaDB Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [MariaDB Data types](https://mariadb.com/kb/en/data-types/)
 * @param T Entity type associated with this table
 */
public abstract class MariadbTable<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: MariadbTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U {
        kotysaForeignKeys.add(ForeignKey(mapOf(this to references), fkName))
        return this
    }

    protected fun varchar(getter: (T) -> String, columnName: String? = null, size: Int = 255): StringDbVarcharColumnNotNull<T> =
            StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
                          size: Int = 255): StringDbVarcharColumnNullable<T> =
            StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun tinyText(getter: (T) -> String, columnName: String? = null): StringDbTinyTextColumnNotNull<T> =
        StringDbTinyTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun tinyText(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null
    ): StringDbTinyTextColumnNullable<T> = StringDbTinyTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> String, columnName: String? = null): StringDbTextColumnNotNull<T> =
        StringDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null
    ): StringDbTextColumnNullable<T> = StringDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun mediumText(getter: (T) -> String, columnName: String? = null): StringDbMediumTextColumnNotNull<T> =
        StringDbMediumTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun mediumText(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null
    ): StringDbMediumTextColumnNullable<T> = StringDbMediumTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun longText(getter: (T) -> String, columnName: String? = null): StringDbLongTextColumnNotNull<T> =
        StringDbLongTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun longText(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null
    ): StringDbLongTextColumnNullable<T> = StringDbLongTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
            IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int?, columnName: String? = null, defaultValue: Int? = null): IntDbIntColumnNullable<T> =
            IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T> =
            IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
            LongDbBigIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long?, columnName: String? = null, defaultValue: Long? = null): LongDbBigIntColumnNullable<T> =
            LongDbBigIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementBigInt(getter: (T) -> Long?, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
            LongDbBigIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun boolean(getter: (T) -> Boolean, columnName: String? = null): BooleanDbBooleanColumnNotNull<T> =
            BooleanDbBooleanColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbDateColumnNotNull<T> =
            LocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate?, columnName: String? = null, defaultValue: LocalDate? = null): LocalDateDbDateColumnNullable<T> =
            LocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun dateTime(getter: (T) -> LocalDateTime, columnName: String? = null, precision: Int? = null): LocalDateTimeDbDateTimeColumnNotNull<T> =
            LocalDateTimeDbDateTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun dateTime(getter: (T) -> LocalDateTime?, columnName: String? = null, defaultValue: LocalDateTime? = null, precision: Int? = null): LocalDateTimeDbDateTimeColumnNullable<T> =
            LocalDateTimeDbDateTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

//     protected fun timestamp(getter: (T) -> OffsetDateTime, columnName: String? = null, precision: Int? = null): OffsetDateTimeDbTimestampColumnNotNull<T> =
//             OffsetDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }
//
//     protected fun timestamp(getter: (T) -> OffsetDateTime?, columnName: String? = null, defaultValue: OffsetDateTime? = null, precision: Int? = null): OffsetDateTimeDbTimestampColumnNullable<T> =
//             OffsetDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun time(getter: (T) -> LocalTime, columnName: String? = null, precision: Int? = null): LocalTimeDbTimeColumnNotNull<T> =
            LocalTimeDbTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun time(getter: (T) -> LocalTime?, columnName: String? = null, defaultValue: LocalTime? = null, precision: Int? = null): LocalTimeDbTimeColumnNullable<T> =
            LocalTimeDbTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun blob(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBlobColumnNotNull<T> =
        ByteArrayDbBlobColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun blob(
        getter: (T) -> ByteArray?,
        columnName: String? = null,
        defaultValue: ByteArray? = null
    ): ByteArrayDbBlobColumnNullable<T> =
        ByteArrayDbBlobColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun binary(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBinaryColumnNotNull<T> =
        ByteArrayDbBinaryColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun binary(
        getter: (T) -> ByteArray?,
        columnName: String? = null,
        defaultValue: ByteArray? = null
    ): ByteArrayDbBinaryColumnNullable<T> =
        ByteArrayDbBinaryColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }
}
