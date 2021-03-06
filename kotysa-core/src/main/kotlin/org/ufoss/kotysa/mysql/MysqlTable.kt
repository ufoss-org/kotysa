package org.ufoss.kotysa.mysql

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.ForeignKey
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Represents a MySQL Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
 * @param T Entity type associated with this table
 */
public abstract class MysqlTable<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: MysqlTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
        return this
    }

    protected fun varchar(getter: (T) -> String, columnName: String? = null, size: Int = 255): StringDbVarcharColumnNotNull<T> =
            StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
                          size: Int = 255): StringDbVarcharColumnNullable<T> =
            StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntegerColumnNotNull<T> =
            IntDbIntegerColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int?, columnName: String? = null, defaultValue: Int? = null): IntDbIntegerColumnNullable<T> =
            IntDbIntegerColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntegerColumnNotNull<T> =
            IntDbIntegerColumnNotNull(getter, columnName, true).also { addColumn(it) }

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
}
