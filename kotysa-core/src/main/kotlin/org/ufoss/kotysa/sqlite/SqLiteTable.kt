/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.*
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
public abstract class SqLiteTable<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    protected fun <V : Any> foreignKey(
            referencedTable: SqLiteTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }

    protected fun <U : Column<T, *>, V : Any> U.foreignKey(referencedTable: SqLiteTable<V>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(referencedTable, listOf(this), fkName))
        return this
    }

    protected fun text(getter: (T) -> String, name: String? = null): StringDbTextColumnNotNull<T> =
            StringDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(getter: (T) -> String?, name: String? = null, defaultValue: String? = null): StringDbTextColumnNullable<T> =
            StringDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDateTime, name: String? = null): LocalDateTimeDbTextColumnNotNull<T> =
            LocalDateTimeDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> LocalDateTime?, name: String? = null, defaultValue: LocalDateTime? = null
    ): LocalDateTimeDbTextColumnNullable<T> =
            LocalDateTimeDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(
            getter: (T) -> kotlinx.datetime.LocalDateTime, name: String? = null
    ): KotlinxLocalDateTimeDbTextColumnNotNull<T> =
            KotlinxLocalDateTimeDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, name: String? = null, defaultValue: kotlinx.datetime.LocalDateTime? = null
    ): KotlinxLocalDateTimeDbTextColumnNullable<T> =
            KotlinxLocalDateTimeDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDate, name: String? = null): LocalDateDbTextColumnNotNull<T> =
            LocalDateDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> LocalDate?, name: String? = null, defaultValue: LocalDate? = null
    ): LocalDateDbTextColumnNullable<T> =
            LocalDateDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(
            getter: (T) -> kotlinx.datetime.LocalDate, name: String? = null
    ): KotlinxLocalDateDbTextColumnNotNull<T> =
            KotlinxLocalDateDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> kotlinx.datetime.LocalDate?, name: String? = null, defaultValue: kotlinx.datetime.LocalDate? = null
    ): KotlinxLocalDateDbTextColumnNullable<T> =
            KotlinxLocalDateDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> OffsetDateTime, name: String? = null): OffsetDateTimeDbTextColumnNotNull<T> =
            OffsetDateTimeDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> OffsetDateTime?, name: String? = null, defaultValue: OffsetDateTime? = null
    ): OffsetDateTimeDbTextColumnNullable<T> =
            OffsetDateTimeDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalTime, name: String? = null): LocalTimeDbTextColumnNotNull<T> =
            LocalTimeDbTextColumnNotNull(getter, name).also { addColumn(it) }

    protected fun text(
            getter: (T) -> LocalTime?, name: String? = null, defaultValue: LocalTime? = null
    ): LocalTimeDbTextColumnNullable<T> =
            LocalTimeDbTextColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, name: String? = null): IntDbIntegerColumnNotNull<T> =
            IntDbIntegerColumnNotNull(getter, name, false).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int?, name: String? = null, defaultValue: Int? = null): IntDbIntegerColumnNullable<T> =
            IntDbIntegerColumnNullable(getter, name, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, name: String? = null): IntDbIntegerColumnNotNull<T> =
            IntDbIntegerColumnNotNull(getter, name, true).also { addColumn(it) }

    protected fun integer(getter: (T) -> Boolean, name: String? = null): BooleanDbIntegerColumnNotNull<T> =
            BooleanDbIntegerColumnNotNull(getter, name).also { addColumn(it) }
}
