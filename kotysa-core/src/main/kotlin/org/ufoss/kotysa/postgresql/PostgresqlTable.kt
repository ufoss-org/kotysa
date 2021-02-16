/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a PostgreSQL Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
 * @param T Entity type associated with this table
 */
public abstract class PostgresqlTable<T : Any> protected constructor(tableName: String? = null) : AbstractTable<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: PostgresqlTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
        return this
    }

    protected fun varchar(getter: (T) -> String, columnName: String? = null, size: Int? = null): StringDbVarcharColumnNotNull<T> =
            StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
                          size: Int? = null): StringDbVarcharColumnNullable<T> =
            StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntegerColumnNotNull<T> =
            IntDbIntegerColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int?, columnName: String? = null, defaultValue: Int? = null): IntDbIntegerColumnNullable<T> =
            IntDbIntegerColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun serial(getter: (T) -> Int?, columnName: String? = null): IntDbSerialColumnNotNull<T> =
            IntDbSerialColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun boolean(getter: (T) -> Boolean, columnName: String? = null): BooleanDbBooleanColumnNotNull<T> =
            BooleanDbBooleanColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbDateColumnNotNull<T> =
            LocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate?, columnName: String? = null, defaultValue: LocalDate? = null): LocalDateDbDateColumnNullable<T> =
            LocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun date(getter: (T) -> kotlinx.datetime.LocalDate, columnName: String? = null): KotlinxLocalDateDbDateColumnNotNull<T> =
            KotlinxLocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> kotlinx.datetime.LocalDate?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDate? = null): KotlinxLocalDateDbDateColumnNullable<T> =
            KotlinxLocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun timestamp(getter: (T) -> LocalDateTime, columnName: String? = null, precision: Int? = null): LocalDateTimeDbTimestampColumnNotNull<T> =
            LocalDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestamp(getter: (T) -> LocalDateTime?, columnName: String? = null, defaultValue: LocalDateTime? = null, precision: Int? = null): LocalDateTimeDbTimestampColumnNullable<T> =
            LocalDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestamp(getter: (T) -> kotlinx.datetime.LocalDateTime, columnName: String? = null, precision: Int? = null): KotlinxLocalDateTimeDbTimestampColumnNotNull<T> =
            KotlinxLocalDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestamp(getter: (T) -> kotlinx.datetime.LocalDateTime?, columnName: String? = null, defaultValue: kotlinx.datetime.LocalDateTime? = null, precision: Int? = null): KotlinxLocalDateTimeDbTimestampColumnNullable<T> =
            KotlinxLocalDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(getter: (T) -> OffsetDateTime, columnName: String? = null, precision: Int? = null): OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T> =
            OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(getter: (T) -> OffsetDateTime?, columnName: String? = null, defaultValue: OffsetDateTime? = null, precision: Int? = null): OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T> =
            OffsetDateTimeDbTimestampWithTimeZoneColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun time(getter: (T) -> LocalTime, columnName: String? = null, precision: Int? = null): LocalTimeDbTimeColumnNotNull<T> =
            LocalTimeDbTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun time(getter: (T) -> LocalTime?, columnName: String? = null, defaultValue: LocalTime? = null, precision: Int? = null): LocalTimeDbTimeColumnNullable<T> =
            LocalTimeDbTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun uuid(getter: (T) -> UUID, columnName: String? = null): UuidDbUuidColumnNotNull<T> =
            UuidDbUuidColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun uuid(getter: (T) -> UUID?, columnName: String? = null, defaultValue: UUID? = null): UuidDbUuidColumnNullable<T> =
            UuidDbUuidColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }
}
