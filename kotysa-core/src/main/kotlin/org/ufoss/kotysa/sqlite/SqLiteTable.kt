/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.ForeignKey
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * Represents a SqLite Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [SqLite Data types](https://www.sqlite.org/datatype3.html)
 * @param T Entity type associated with this table
 */
public abstract class SqLiteTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractTable<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: SqLiteTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
        return this
    }

    protected fun text(getter: (T) -> String, columnName: String? = null): StringDbTextColumnNotNull<T> =
        StringDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> String?,
        columnName: String? = null,
        defaultValue: String? = null
    ): StringDbTextColumnNullable<T> =
        StringDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDateTime, columnName: String? = null): LocalDateTimeDbTextColumnNotNull<T> =
        LocalDateTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalDateTime?, columnName: String? = null, defaultValue: LocalDateTime? = null
    ): LocalDateTimeDbTextColumnNullable<T> =
        LocalDateTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbTextColumnNotNull<T> =
        LocalDateDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalDate?, columnName: String? = null, defaultValue: LocalDate? = null
    ): LocalDateDbTextColumnNullable<T> =
        LocalDateDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(
        getter: (T) -> OffsetDateTime,
        columnName: String? = null
    ): OffsetDateTimeDbTextColumnNotNull<T> =
        OffsetDateTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> OffsetDateTime?, columnName: String? = null, defaultValue: OffsetDateTime? = null
    ): OffsetDateTimeDbTextColumnNullable<T> =
        OffsetDateTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalTime, columnName: String? = null): LocalTimeDbTextColumnNotNull<T> =
        LocalTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalTime?, columnName: String? = null, defaultValue: LocalTime? = null
    ): LocalTimeDbTextColumnNullable<T> =
        LocalTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T> =
        IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun integer(getter: (T) -> Long, columnName: String? = null): LongDbIntColumnNotNull<T> =
            LongDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
            getter: (T) -> Long?,
            columnName: String? = null,
            defaultValue: Long? = null
    ): LongDbIntColumnNullable<T> =
            LongDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Long?, columnName: String? = null): LongDbIntColumnNotNull<T> =
            LongDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun integer(getter: (T) -> Boolean, columnName: String? = null): BooleanDbIntColumnNotNull<T> =
        BooleanDbIntColumnNotNull(getter, columnName).also { addColumn(it) }
}
