/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

@Suppress("UNCHECKED_CAST")
public open class DefaultSqlClientCommon protected constructor() : SqlClientQuery() {

    public interface Properties {
        public val dbAccessType: DbAccessType
        public val tables: Tables
        public val module: Module
        public val parameters: MutableList<Any>
        public val fromClauses: MutableList<FromClause<*>>
        public val whereClauses: MutableList<WhereClauseWithType>

        public val availableTables: MutableMap<Table<*>, KotysaTable<*>>
        public val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
        public var index: Int
    }

    public interface WithProperties {
        public val properties: Properties
    }

    public abstract class FromWhereable<T : Any, U : From<T, U>, V : SqlClientQuery.Where<V>> :
        Whereable<V>(), From<T, U> {
        protected abstract val from: U
        private val joinable: Joinable<T, U, *> by lazy {
            Joinable<T, U, Any>(properties, from)
        }

        protected fun <W : Any> addFromTable(properties: Properties, kotysaTable: KotysaTable<W>) {
            makeAvailable(properties, kotysaTable)
            properties.fromClauses.add(FromClause(kotysaTable.table))
        }

        internal fun <W : Any> addFromTable(table: Table<W>): U = with(properties) {
            addFromTable(this, tables.getTable(table))
            from
        }

        override fun <W : Any> innerJoin(table: Table<W>): SqlClientQuery.Joinable<T, U, W> {
            makeAvailable(properties, properties.tables.getTable(table))
            val joinable = (joinable as Joinable<T, U, W>)
            joinable.type = JoinClauseType.INNER
            joinable.table = table
            return joinable
        }

        private fun <W : Any> makeAvailable(properties: Properties, kotysaTable: KotysaTable<W>) {
            properties.apply {
                // This table becomes available
                availableTables[kotysaTable.table] = kotysaTable
                // All columns of this table become available
                kotysaTable.columns.forEach { column -> availableColumns[column.column] = column }
            }
        }
    }

    public class Joinable<T : Any, U : From<T, U>, V : Any> internal constructor(
        override val properties: Properties,
        from: U,
    ) : SqlClientQuery.Joinable<T, U, V>, WithProperties {
        internal lateinit var type: JoinClauseType
        internal lateinit var table: Table<V>

        private val join = Join<T, U, V>(properties, from)

        override fun on(column: Column<T, *>): SqlClientQuery.Join<T, U, V> =
            join.apply {
                type = this@Joinable.type
                table = this@Joinable.table
                this.column = column
            }
    }

    public class Join<T : Any, U : From<T, U>, V : Any> internal constructor(
        override val properties: Properties,
        private val from: U,
    ) : SqlClientQuery.Join<T, U, V>, WithProperties {
        internal lateinit var type: JoinClauseType
        internal lateinit var table: Table<V>
        internal lateinit var column: Column<T, *>

        override fun eq(column: Column<V, *>): U = with(properties) {
            // get last from
            val joinClause = JoinClause(table, mapOf(Pair(this@Join.column, column)), type)
            (fromClauses[fromClauses.size - 1] as FromClause<T>).joinClauses.add(joinClause)
            from
        }

    }

    public abstract class Whereable<T : SqlClientQuery.Where<T>> protected constructor() : WithWhere<T>(),
        SqlClientQuery.Whereable<T>, WithProperties {

        override fun <U : Any> where(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T> =
            whereOpStringColumnNotNull(stringColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T> =
            whereOpStringColumnNullable(stringColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull(localDateTimeColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable(localDateTimeColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull(kotlinxLocalDateTimeColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable(kotlinxLocalDateTimeColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate> =
            whereOpLocalDateColumnNotNull(localDateColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate> =
            whereOpLocalDateColumnNullable(localDateColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull(kotlinxLocalDateColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable(kotlinxLocalDateColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull(offsetDateTimeColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable(offsetDateTimeColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime> =
            whereOpLocalTimeColumnNotNull(localTimeColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime> =
            whereOpLocalTimeColumnNullable(localTimeColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T> =
            whereOpBooleanColumnNotNull(booleanColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T> =
            whereOpIntColumnNotNull(intColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T> =
            whereOpIntColumnNullable(intColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T> =
            whereOpLongColumnNotNull(longColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T> =
            whereOpLongColumnNullable(longColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> where(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T> =
            whereOpUuidColumnNotNull(uuidColumnNotNull, WhereClauseType.WHERE)

        override fun <U : Any> where(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T> =
            whereOpUuidColumnNullable(uuidColumnNullable, WhereClauseType.WHERE)

        override fun <U : Any> whereExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T {
            properties.whereClauses.add(WhereClauseWithType(WhereClauseExists(dsl), WhereClauseType.WHERE))
            return where
        }

        // Where with alias
        override fun where(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T> =
            whereOpStringAliasNotNull(stringAliasNotNull, WhereClauseType.WHERE)

        override fun where(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T> =
            whereOpStringAliasNullable(stringAliasNullable, WhereClauseType.WHERE)

        override infix fun where(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNotNull(localDateTimeAliasNotNull, WhereClauseType.WHERE)

        override fun where(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNotNull(kotlinxLocalDateTimeAliasNotNull, WhereClauseType.WHERE)

        override fun where(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T> =
            whereOpLocalDateAliasNotNull(localDateAliasNotNull, WhereClauseType.WHERE)

        override fun where(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNotNull(kotlinxLocalDateAliasNotNull, WhereClauseType.WHERE)

        override fun where(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNotNull(offsetDateTimeAliasNotNull, WhereClauseType.WHERE)

        override fun where(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T> =
            whereOpLocalTimeAliasNotNull(localTimeAliasNotNull, WhereClauseType.WHERE)

        override fun where(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNullable(localDateTimeAliasNullable, WhereClauseType.WHERE)

        override fun where(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNullable(kotlinxLocalDateTimeAliasNullable, WhereClauseType.WHERE)

        override fun where(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T> =
            whereOpLocalDateAliasNullable(localDateAliasNullable, WhereClauseType.WHERE)

        override fun where(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNullable(kotlinxLocalDateAliasNullable, WhereClauseType.WHERE)

        override fun where(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNullable(offsetDateTimeAliasNullable, WhereClauseType.WHERE)

        override fun where(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T> =
            whereOpLocalTimeAliasNullable(localTimeAliasNullable, WhereClauseType.WHERE)

        override fun where(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T> =
            whereOpBooleanAliasNotNull(booleanAliasNotNull, WhereClauseType.WHERE)

        override fun where(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T> =
            whereOpIntAliasNotNull(intAliasNotNull, WhereClauseType.WHERE)

        override fun where(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T> =
            whereOpIntAliasNullable(intAliasNullable, WhereClauseType.WHERE)

        override fun where(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T> =
            whereOpLongAliasNotNull(longAliasNotNull, WhereClauseType.WHERE)

        override fun where(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T> =
            whereOpLongAliasNullable(longAliasNullable, WhereClauseType.WHERE)

        override fun where(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T> =
            whereOpUuidAliasNotNull(uuidAliasNotNull, WhereClauseType.WHERE)

        override fun where(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T> =
            whereOpUuidAliasNullable(uuidAliasNullable, WhereClauseType.WHERE)
    }

    public interface WhereColumnCommon : WithProperties {
        public fun <T : Any> addClauseValue(
            column: Column<T, *>,
            operation: Operation,
            value: Any?,
            whereClauseType: WhereClauseType
        ) {
            // Add value to parameters, if not null
            if (value != null) {
                properties.parameters.add(value)
            }
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseValueWithColumn(column, operation, value),
                    whereClauseType,
                )
            )
        }

        public fun <T : Any> addClauseColumn(
            column: Column<T, *>,
            operation: Operation,
            otherColumn: Column<*, *>,
            whereClauseType: WhereClauseType
        ) {
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseColumnWithColumn(column, operation, otherColumn),
                    whereClauseType,
                )
            )
        }
    }

    public interface WhereAliasCommon : WithProperties {
        public fun <T> addClauseValue(
            alias: QueryAlias<T>,
            operation: Operation,
            value: Any?,
            whereClauseType: WhereClauseType
        ) {
            // Add value to parameters, if not null
            if (value != null) {
                properties.parameters.add(value)
            }
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseValueWithAlias(alias, operation, value),
                    whereClauseType,
                )
            )
        }

        public fun <T> addClauseColumn(
            alias: QueryAlias<T>,
            operation: Operation,
            otherColumn: Column<*, *>,
            whereClauseType: WhereClauseType
        ) {
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseColumnWithAlias(alias, operation, otherColumn),
                    whereClauseType,
                )
            )
        }
    }

    public interface WhereOpColumn<T : Any, U : SqlClientQuery.Where<U>, V : Any> : WhereColumnCommon {
        public val where: U
        public val column: Column<T, V>
        public val type: WhereClauseType
    }

    public interface WhereOpAlias<T, U : SqlClientQuery.Where<U>> : WhereAliasCommon {
        public val where: U
        public val alias: QueryAlias<T>
        public val type: WhereClauseType
    }

    public interface WhereInOpColumn<T : Any, U : SqlClientQuery.Where<U>, V : Any> :
        WhereOpColumn<T, U, V>, WhereInOp<T, U, V> {
        override infix fun `in`(values: Collection<V>): U =
            where.apply { addClauseValue(column, Operation.IN, values, type) }
    }

    public interface WhereInOpAlias<T, U: Any, V : SqlClientQuery.Where<V>> :
        WhereOpAlias<T, V>, WhereInOp<U, V, U> {
        override infix fun `in`(values: Collection<U>): V =
            where.apply { addClauseValue(alias, Operation.IN, values, type) }
    }

    public interface WhereInOpAliasNotNull<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T, T, U>, WhereOpAliasNotNull<T, U>

    public interface WhereInOpAliasNullable<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T?, T, U>, WhereOpAliasNullable<T, U>

    public interface WhereOpColumnNotNull<T : Any, U : SqlClientQuery.Where<U>, V : Any> :
        WhereOpColumn<T, U, V>, WhereOpNotNull<T, U, V> {
        override infix fun eq(value: V): U = where.apply { addClauseValue(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V): U = where.apply { addClauseValue(column, Operation.NOT_EQ, value, type) }
    }

    public interface WhereOpAliasNotNull<T : Any, U : SqlClientQuery.Where<U>> :
        WhereOpAlias<T, U>, WhereOpNotNull<T, U, T> {
        override infix fun eq(value: T): U = where.apply { addClauseValue(alias, Operation.EQ, value, type) }
        override infix fun notEq(value: T): U = where.apply { addClauseValue(alias, Operation.NOT_EQ, value, type) }
    }

    public interface WhereOpColumnNullable<T : Any, U : SqlClientQuery.Where<U>, V : Any> :
        WhereOpColumn<T, U, V>, WhereOpNullable<T, U, V> {
        override infix fun eq(value: V?): U = where.apply { addClauseValue(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V?): U = where.apply { addClauseValue(column, Operation.NOT_EQ, value, type) }
    }

    public interface WhereOpAliasNullable<T : Any, U : SqlClientQuery.Where<U>> :
        WhereOpAlias<T?, U>, WhereOpNullable<T, U, T> {
        override infix fun eq(value: T?): U = where.apply { addClauseValue(alias, Operation.EQ, value, type) }
        override infix fun notEq(value: T?): U = where.apply { addClauseValue(alias, Operation.NOT_EQ, value, type) }
    }

    public abstract class AbstractWhereOpColumn<T : Any, U : SqlClientQuery.Where<U>, V : Any> internal constructor() :
        Where<U>(), WhereOpColumn<T, U, V>

    public abstract class AbstractWhereOpAliasNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor() :
        Where<U>(), WhereOpAlias<T, U>

    public abstract class AbstractWhereOpAliasNullable<T : Any, U : SqlClientQuery.Where<U>> internal constructor() :
        Where<U>(), WhereOpAlias<T?, U>

    public interface WhereOpStringColumn<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpColumn<T, U, String>, WhereOpString<T, U> {
        override infix fun contains(value: String): U =
            where.apply { addClauseValue(column, Operation.CONTAINS, "%$value%", type) }

        override infix fun startsWith(value: String): U =
            where.apply { addClauseValue(column, Operation.STARTS_WITH, "$value%", type) }

        override infix fun endsWith(value: String): U =
            where.apply { addClauseValue(column, Operation.ENDS_WITH, "%$value", type) }

        override infix fun eq(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherStringColumn, type) }

        override infix fun notEq(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherStringColumn, type) }

        override infix fun contains(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.CONTAINS, otherStringColumn, type) }

        override infix fun startsWith(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.STARTS_WITH, otherStringColumn, type) }

        override infix fun endsWith(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.ENDS_WITH, otherStringColumn, type) }
    }

    public interface WhereOpStringAlias<T, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T, String, U>, WhereOpString<String, U> {
        override infix fun contains(value: String): U =
            where.apply { addClauseValue(alias, Operation.CONTAINS, "%$value%", type) }

        override infix fun startsWith(value: String): U =
            where.apply { addClauseValue(alias, Operation.STARTS_WITH, "$value%", type) }

        override infix fun endsWith(value: String): U =
            where.apply { addClauseValue(alias, Operation.ENDS_WITH, "%$value", type) }

        override infix fun eq(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.EQ, otherStringColumn, type) }

        override infix fun notEq(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.NOT_EQ, otherStringColumn, type) }

        override infix fun contains(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.CONTAINS, otherStringColumn, type) }

        override infix fun startsWith(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.STARTS_WITH, otherStringColumn, type) }

        override infix fun endsWith(otherStringColumn: StringColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.ENDS_WITH, otherStringColumn, type) }
    }

    public class WhereOpStringColumnNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, String>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, String>(), WhereOpStringColumn<T, U>,
        WhereOpColumnNotNull<T, U, String>, WhereOpStringNotNull<T, U>

    public class WhereOpStringAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<String>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<String, T>(), WhereOpStringAlias<String, T>,
        WhereOpAliasNotNull<String, T>, WhereOpStringNotNull<String, T>

    public class WhereOpStringColumnNullable<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, String>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, String>(), WhereOpStringColumn<T, U>,
        WhereOpColumnNullable<T, U, String>, WhereOpStringNullable<T, U>

    public class WhereOpStringAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<String?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<String, T>(), WhereOpStringAlias<String?, T>,
        WhereOpAliasNullable<String, T>, WhereOpStringNullable<String, T>

    public interface WhereOpDateColumn<T : Any, U : SqlClientQuery.Where<U>, V : Any> :
        WhereInOpColumn<T, U, V>, WhereOpDate<T, U, V> {
        override infix fun before(value: V): U = where.apply { addClauseValue(column, Operation.INF, value, type) }
        override infix fun after(value: V): U = where.apply { addClauseValue(column, Operation.SUP, value, type) }
        override infix fun beforeOrEq(value: V): U =
            where.apply { addClauseValue(column, Operation.INF_OR_EQ, value, type) }

        override infix fun afterOrEq(value: V): U =
            where.apply { addClauseValue(column, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherDateColumn, type) }

        override infix fun notEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherDateColumn, type) }

        override infix fun before(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.INF, otherDateColumn, type) }

        override infix fun after(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.SUP, otherDateColumn, type) }

        override infix fun beforeOrEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.INF_OR_EQ, otherDateColumn, type) }

        override infix fun afterOrEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(column, Operation.SUP_OR_EQ, otherDateColumn, type) }
    }

    public interface WhereOpDateAlias<T, U : SqlClientQuery.Where<U>, V : Any> :
        WhereInOpAlias<T, V, U>, WhereOpDate<V, U, V> {
        override infix fun before(value: V): U = where.apply { addClauseValue(alias, Operation.INF, value, type) }
        override infix fun after(value: V): U = where.apply { addClauseValue(alias, Operation.SUP, value, type) }
        override infix fun beforeOrEq(value: V): U =
            where.apply { addClauseValue(alias, Operation.INF_OR_EQ, value, type) }

        override infix fun afterOrEq(value: V): U =
            where.apply { addClauseValue(alias, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.EQ, otherDateColumn, type) }

        override infix fun notEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.NOT_EQ, otherDateColumn, type) }

        override infix fun before(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.INF, otherDateColumn, type) }

        override infix fun after(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.SUP, otherDateColumn, type) }

        override infix fun beforeOrEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.INF_OR_EQ, otherDateColumn, type) }

        override infix fun afterOrEq(otherDateColumn: Column<*, V>): U =
            where.apply { addClauseColumn(alias, Operation.SUP_OR_EQ, otherDateColumn, type) }
    }

    public class WhereOpDateColumnNotNull<T : Any, U : SqlClientQuery.Where<U>, V : Any> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, V>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, V>(), WhereOpDateColumn<T, U, V>,
        WhereOpColumnNotNull<T, U, V>, WhereOpDateNotNull<T, U, V>

    public interface WhereOpDateAliasNotNull<T : SqlClientQuery.Where<T>, U : Any> : WhereOpDateAlias<U, T, U>,
        WhereOpAliasNotNull<U, T>, WhereOpDateNotNull<U, T, U>

    public class WhereOpLocalDateTimeAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalDateTime>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<LocalDateTime, T>(), WhereOpDateAliasNotNull<T, LocalDateTime>,
        WhereOpLocalDateTimeNotNull<LocalDateTime, T>

    public class WhereOpKotlinxLocalDateTimeAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<kotlinx.datetime.LocalDateTime>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<kotlinx.datetime.LocalDateTime, T>(), WhereOpDateAliasNotNull<T, kotlinx.datetime.LocalDateTime>,
        WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T>

    public class WhereOpLocalDateAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalDate>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<LocalDate, T>(), WhereOpDateAliasNotNull<T, LocalDate>,
        WhereOpLocalDateNotNull<LocalDate, T>

    public class WhereOpKotlinxLocalDateAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<kotlinx.datetime.LocalDate>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<kotlinx.datetime.LocalDate, T>(), WhereOpDateAliasNotNull<T, kotlinx.datetime.LocalDate>,
        WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T>

    public class WhereOpOffsetDateTimeAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<OffsetDateTime>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<OffsetDateTime, T>(), WhereOpDateAliasNotNull<T, OffsetDateTime>,
        WhereOpOffsetDateTimeNotNull<OffsetDateTime, T>

    public class WhereOpLocalTimeAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalTime>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<LocalTime, T>(), WhereOpDateAliasNotNull<T, LocalTime>,
        WhereOpLocalTimeNotNull<LocalTime, T>

    public class WhereOpDateColumnNullable<T : Any, U : SqlClientQuery.Where<U>, V : Any> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, V>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, V>(), WhereOpDateColumn<T, U, V>,
        WhereOpColumnNullable<T, U, V>, WhereOpDateNullable<T, U, V>

    public interface WhereOpDateAliasNullable<T : SqlClientQuery.Where<T>, U : Any> : WhereOpDateAlias<U?, T, U>,
        WhereOpAliasNullable<U, T>, WhereOpDateNullable<U, T, U>

    public class WhereOpLocalDateTimeAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalDateTime?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<LocalDateTime, T>(), WhereOpDateAliasNullable<T, LocalDateTime>,
        WhereOpLocalDateTimeNullable<LocalDateTime, T>

    public class WhereOpKotlinxLocalDateTimeAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<kotlinx.datetime.LocalDateTime?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<kotlinx.datetime.LocalDateTime, T>(), WhereOpDateAliasNullable<T, kotlinx.datetime.LocalDateTime>,
        WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T>

    public class WhereOpLocalDateAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalDate?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<LocalDate, T>(), WhereOpDateAliasNullable<T, LocalDate>,
        WhereOpLocalDateNullable<LocalDate, T>

    public class WhereOpKotlinxLocalDateAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<kotlinx.datetime.LocalDate?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<kotlinx.datetime.LocalDate, T>(), WhereOpDateAliasNullable<T, kotlinx.datetime.LocalDate>,
        WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T>

    public class WhereOpOffsetDateTimeAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<OffsetDateTime?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<OffsetDateTime, T>(), WhereOpDateAliasNullable<T, OffsetDateTime>,
        WhereOpOffsetDateTimeNullable<OffsetDateTime, T>

    public class WhereOpLocalTimeAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<LocalTime?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<LocalTime, T>(), WhereOpDateAliasNullable<T, LocalTime>,
        WhereOpLocalTimeNullable<LocalTime, T>

    public class WhereOpBooleanColumnNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, Boolean>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, Boolean>(), WhereOpBooleanNotNull<T, U> {
        override infix fun eq(value: Boolean): U =
            where.apply { addClauseValue(column, Operation.EQ, value, type) }

        override infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherBooleanColumn, type) }
    }

    public class WhereOpBooleanAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<Boolean>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<Boolean, T>(), WhereOpBooleanNotNull<Boolean, T> {
        override infix fun eq(value: Boolean): T =
            where.apply { addClauseValue(alias, Operation.EQ, value, type) }

        override infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): T =
            where.apply { addClauseColumn(alias, Operation.EQ, otherBooleanColumn, type) }
    }

    public interface WhereOpIntColumn<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpColumn<T, U, Int>, WhereOpInt<T, U> {
        override infix fun inf(value: Int): U = where.apply { addClauseValue(column, Operation.INF, value, type) }
        override infix fun sup(value: Int): U = where.apply { addClauseValue(column, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Int): U =
            where.apply { addClauseValue(column, Operation.INF_OR_EQ, value, type) }

        override infix fun supOrEq(value: Int): U =
            where.apply { addClauseValue(column, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherIntColumn, type) }

        override infix fun notEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherIntColumn, type) }

        override infix fun inf(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.INF, otherIntColumn, type) }

        override infix fun sup(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.SUP, otherIntColumn, type) }

        override infix fun infOrEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.INF_OR_EQ, otherIntColumn, type) }

        override infix fun supOrEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.SUP_OR_EQ, otherIntColumn, type) }
    }

    public interface WhereOpIntAlias<T, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T, Int, U>, WhereOpInt<Int, U> {
        override infix fun inf(value: Int): U = where.apply { addClauseValue(alias, Operation.INF, value, type) }
        override infix fun sup(value: Int): U = where.apply { addClauseValue(alias, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Int): U =
            where.apply { addClauseValue(alias, Operation.INF_OR_EQ, value, type) }

        override infix fun supOrEq(value: Int): U =
            where.apply { addClauseValue(alias, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.EQ, otherIntColumn, type) }

        override infix fun notEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.NOT_EQ, otherIntColumn, type) }

        override infix fun inf(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.INF, otherIntColumn, type) }

        override infix fun sup(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.SUP, otherIntColumn, type) }

        override infix fun infOrEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.INF_OR_EQ, otherIntColumn, type) }

        override infix fun supOrEq(otherIntColumn: IntColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.SUP_OR_EQ, otherIntColumn, type) }
    }

    public class WhereOpIntColumnNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, Int>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, Int>(), WhereOpIntColumn<T, U>,
        WhereOpColumnNotNull<T, U, Int>, WhereOpIntNotNull<T, U>

    public class WhereOpIntAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<Int>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<Int, T>(), WhereOpIntAlias<Int, T>,
        WhereOpAliasNotNull<Int, T>, WhereOpIntNotNull<Int, T>

    public class WhereOpIntColumnNullable<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, Int>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, Int>(), WhereOpIntColumn<T, U>,
        WhereOpColumnNullable<T, U, Int>, WhereOpIntNullable<T, U>

    public class WhereOpIntAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<Int?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<Int, T>(), WhereOpIntAlias<Int?, T>,
        WhereOpAliasNullable<Int, T>, WhereOpIntNullable<Int, T>

    public interface WhereOpLongColumn<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpColumn<T, U, Long>, WhereOpLong<T, U> {
        override infix fun inf(value: Long): U = where.apply { addClauseValue(column, Operation.INF, value, type) }
        override infix fun sup(value: Long): U = where.apply { addClauseValue(column, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Long): U =
            where.apply { addClauseValue(column, Operation.INF_OR_EQ, value, type) }

        override infix fun supOrEq(value: Long): U =
            where.apply { addClauseValue(column, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherLongColumn, type) }

        override infix fun notEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherLongColumn, type) }

        override infix fun inf(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.INF, otherLongColumn, type) }

        override infix fun sup(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.SUP, otherLongColumn, type) }

        override infix fun infOrEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.INF_OR_EQ, otherLongColumn, type) }

        override infix fun supOrEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.SUP_OR_EQ, otherLongColumn, type) }
    }

    public interface WhereOpLongAlias<T, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T, Long, U>, WhereOpLong<Long, U> {
        override infix fun inf(value: Long): U = where.apply { addClauseValue(alias, Operation.INF, value, type) }
        override infix fun sup(value: Long): U = where.apply { addClauseValue(alias, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Long): U =
            where.apply { addClauseValue(alias, Operation.INF_OR_EQ, value, type) }

        override infix fun supOrEq(value: Long): U =
            where.apply { addClauseValue(alias, Operation.SUP_OR_EQ, value, type) }

        override infix fun eq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.EQ, otherLongColumn, type) }

        override infix fun notEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.NOT_EQ, otherLongColumn, type) }

        override infix fun inf(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.INF, otherLongColumn, type) }

        override infix fun sup(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.SUP, otherLongColumn, type) }

        override infix fun infOrEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.INF_OR_EQ, otherLongColumn, type) }

        override infix fun supOrEq(otherLongColumn: LongColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.SUP_OR_EQ, otherLongColumn, type) }
    }

    public class WhereOpLongColumnNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, Long>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, Long>(), WhereOpLongColumn<T, U>,
        WhereOpColumnNotNull<T, U, Long>, WhereOpLongNotNull<T, U>

    public class WhereOpLongAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<Long>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<Long, T>(), WhereOpLongAlias<Long, T>,
        WhereOpAliasNotNull<Long, T>, WhereOpLongNotNull<Long, T>

    public class WhereOpLongColumnNullable<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, Long>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, Long>(), WhereOpLongColumn<T, U>,
        WhereOpColumnNullable<T, U, Long>, WhereOpLongNullable<T, U>

    public class WhereOpLongAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<Long?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<Long, T>(), WhereOpLongAlias<Long?, T>,
        WhereOpAliasNullable<Long, T>, WhereOpLongNullable<Long, T>

    public interface WhereOpUuidColumn<T : Any, U : SqlClientQuery.Where<U>> :
        WhereInOpColumn<T, U, UUID>, WhereOpUuid<T, U> {
        override infix fun eq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherUuidColumn, type) }

        override infix fun notEq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherUuidColumn, type) }
    }

    public interface WhereOpUuidAlias<T, U : SqlClientQuery.Where<U>> :
        WhereInOpAlias<T, UUID, U>, WhereOpUuid<UUID, U> {
        override infix fun eq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.EQ, otherUuidColumn, type) }

        override infix fun notEq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(alias, Operation.NOT_EQ, otherUuidColumn, type) }
    }

    public class WhereOpUuidColumnNotNull<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, UUID>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereOpUuidColumn<T, U>,
        WhereOpColumnNotNull<T, U, UUID>, WhereOpUuidNotNull<T, U>

    public class WhereOpUuidAliasNotNull<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<UUID>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNotNull<UUID, T>(), WhereOpUuidAlias<UUID, T>,
        WhereOpAliasNotNull<UUID, T>, WhereOpUuidNotNull<UUID, T>

    public class WhereOpUuidColumnNullable<T : Any, U : SqlClientQuery.Where<U>> internal constructor(
        override val where: U,
        override val properties: Properties,
        override val column: Column<T, UUID>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereOpUuidColumn<T, U>,
        WhereOpColumnNullable<T, U, UUID>, WhereOpUuidNullable<T, U>

    public class WhereOpUuidAliasNullable<T : SqlClientQuery.Where<T>> internal constructor(
        override val where: T,
        override val properties: Properties,
        override val alias: QueryAlias<UUID?>,
        override val type: WhereClauseType,
    ) : AbstractWhereOpAliasNullable<UUID, T>(), WhereOpUuidAlias<UUID?, T>,
        WhereOpAliasNullable<UUID, T>, WhereOpUuidNullable<UUID, T>

    public abstract class Where<T : SqlClientQuery.Where<T>> internal constructor() : WithWhere<T>(),
        SqlClientQuery.Where<T>, WhereColumnCommon, WhereAliasCommon {

        override fun <U : Any> and(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T> =
            whereOpStringColumnNotNull(stringColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T> =
            whereOpStringColumnNullable(stringColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull(localDateTimeColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable(localDateTimeColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull(kotlinxLocalDateTimeColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable(kotlinxLocalDateTimeColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate> =
            whereOpLocalDateColumnNotNull(localDateColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate> =
            whereOpLocalDateColumnNullable(localDateColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull(kotlinxLocalDateColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable(kotlinxLocalDateColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull(offsetDateTimeColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable(offsetDateTimeColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime> =
            whereOpLocalTimeColumnNotNull(localTimeColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime> =
            whereOpLocalTimeColumnNullable(localTimeColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T> =
            whereOpBooleanColumnNotNull(booleanColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T> =
            whereOpIntColumnNotNull(intColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T> =
            whereOpIntColumnNullable(intColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T> =
            whereOpLongColumnNotNull(longColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T> =
            whereOpLongColumnNullable(longColumnNullable, WhereClauseType.AND)

        override fun <U : Any> and(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T> =
            whereOpUuidColumnNotNull(uuidColumnNotNull, WhereClauseType.AND)

        override fun <U : Any> and(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T> =
            whereOpUuidColumnNullable(uuidColumnNullable, WhereClauseType.AND)

        override fun <U : Any> andExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T {
            properties.whereClauses.add(WhereClauseWithType(WhereClauseExists(dsl), WhereClauseType.AND))
            return where
        }

        // And with alias
        override fun and(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T> =
            whereOpStringAliasNotNull(stringAliasNotNull, WhereClauseType.AND)

        override fun and(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T> =
            whereOpStringAliasNullable(stringAliasNullable, WhereClauseType.AND)

        override infix fun and(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNotNull(localDateTimeAliasNotNull, WhereClauseType.AND)

        override fun and(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNotNull(kotlinxLocalDateTimeAliasNotNull, WhereClauseType.AND)

        override fun and(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T> =
            whereOpLocalDateAliasNotNull(localDateAliasNotNull, WhereClauseType.AND)

        override fun and(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNotNull(kotlinxLocalDateAliasNotNull, WhereClauseType.AND)

        override fun and(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNotNull(offsetDateTimeAliasNotNull, WhereClauseType.AND)

        override fun and(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T> =
            whereOpLocalTimeAliasNotNull(localTimeAliasNotNull, WhereClauseType.AND)

        override fun and(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNullable(localDateTimeAliasNullable, WhereClauseType.AND)

        override fun and(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNullable(kotlinxLocalDateTimeAliasNullable, WhereClauseType.AND)

        override fun and(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T> =
            whereOpLocalDateAliasNullable(localDateAliasNullable, WhereClauseType.AND)

        override fun and(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNullable(kotlinxLocalDateAliasNullable, WhereClauseType.AND)

        override fun and(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNullable(offsetDateTimeAliasNullable, WhereClauseType.AND)

        override fun and(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T> =
            whereOpLocalTimeAliasNullable(localTimeAliasNullable, WhereClauseType.AND)

        override fun and(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T> =
            whereOpBooleanAliasNotNull(booleanAliasNotNull, WhereClauseType.AND)

        override fun and(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T> =
            whereOpIntAliasNotNull(intAliasNotNull, WhereClauseType.AND)

        override fun and(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T> =
            whereOpIntAliasNullable(intAliasNullable, WhereClauseType.AND)

        override fun and(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T> =
            whereOpLongAliasNotNull(longAliasNotNull, WhereClauseType.AND)

        override fun and(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T> =
            whereOpLongAliasNullable(longAliasNullable, WhereClauseType.AND)

        override fun and(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T> =
            whereOpUuidAliasNotNull(uuidAliasNotNull, WhereClauseType.AND)

        override fun and(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T> =
            whereOpUuidAliasNullable(uuidAliasNullable, WhereClauseType.AND)

        override fun <U : Any> or(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T> =
            whereOpStringColumnNotNull(stringColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T> =
            whereOpStringColumnNullable(stringColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull(localDateTimeColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable(localDateTimeColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull(kotlinxLocalDateTimeColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable(kotlinxLocalDateTimeColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate> =
            whereOpLocalDateColumnNotNull(localDateColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate> =
            whereOpLocalDateColumnNullable(localDateColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull(kotlinxLocalDateColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable(kotlinxLocalDateColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull(offsetDateTimeColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable(offsetDateTimeColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime> =
            whereOpLocalTimeColumnNotNull(localTimeColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime> =
            whereOpLocalTimeColumnNullable(localTimeColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T> =
            whereOpBooleanColumnNotNull(booleanColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T> =
            whereOpIntColumnNotNull(intColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T> =
            whereOpIntColumnNullable(intColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T> =
            whereOpLongColumnNotNull(longColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T> =
            whereOpLongColumnNullable(longColumnNullable, WhereClauseType.OR)

        override fun <U : Any> or(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T> =
            whereOpUuidColumnNotNull(uuidColumnNotNull, WhereClauseType.OR)

        override fun <U : Any> or(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T> =
            whereOpUuidColumnNullable(uuidColumnNullable, WhereClauseType.OR)

        override fun <U : Any> orExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T {
            properties.whereClauses.add(WhereClauseWithType(WhereClauseExists(dsl), WhereClauseType.OR))
            return where
        }

        // Or with alias
        override fun or(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T> =
            whereOpStringAliasNotNull(stringAliasNotNull, WhereClauseType.OR)

        override fun or(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T> =
            whereOpStringAliasNullable(stringAliasNullable, WhereClauseType.OR)

        override infix fun or(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNotNull(localDateTimeAliasNotNull, WhereClauseType.OR)

        override fun or(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNotNull(kotlinxLocalDateTimeAliasNotNull, WhereClauseType.OR)

        override fun or(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T> =
            whereOpLocalDateAliasNotNull(localDateAliasNotNull, WhereClauseType.OR)

        override fun or(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNotNull(kotlinxLocalDateAliasNotNull, WhereClauseType.OR)

        override fun or(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNotNull(offsetDateTimeAliasNotNull, WhereClauseType.OR)

        override fun or(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T> =
            whereOpLocalTimeAliasNotNull(localTimeAliasNotNull, WhereClauseType.OR)

        override fun or(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T> =
            whereOpLocalDateTimeAliasNullable(localDateTimeAliasNullable, WhereClauseType.OR)

        override fun or(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T> =
            whereOpKotlinxLocalDateTimeAliasNullable(kotlinxLocalDateTimeAliasNullable, WhereClauseType.OR)

        override fun or(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T> =
            whereOpLocalDateAliasNullable(localDateAliasNullable, WhereClauseType.OR)

        override fun or(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T> =
            whereOpKotlinxLocalDateAliasNullable(kotlinxLocalDateAliasNullable, WhereClauseType.OR)

        override fun or(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T> =
            whereOpOffsetDateTimeAliasNullable(offsetDateTimeAliasNullable, WhereClauseType.OR)

        override fun or(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T> =
            whereOpLocalTimeAliasNullable(localTimeAliasNullable, WhereClauseType.OR)

        override fun or(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T> =
            whereOpBooleanAliasNotNull(booleanAliasNotNull, WhereClauseType.OR)

        override fun or(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T> =
            whereOpIntAliasNotNull(intAliasNotNull, WhereClauseType.OR)

        override fun or(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T> =
            whereOpIntAliasNullable(intAliasNullable, WhereClauseType.OR)

        override fun or(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T> =
            whereOpLongAliasNotNull(longAliasNotNull, WhereClauseType.OR)

        override fun or(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T> =
            whereOpLongAliasNullable(longAliasNullable, WhereClauseType.OR)

        override fun or(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T> =
            whereOpUuidAliasNotNull(uuidAliasNotNull, WhereClauseType.OR)

        override fun or(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T> =
            whereOpUuidAliasNullable(uuidAliasNullable, WhereClauseType.OR)
    }

    public interface Return : WithProperties {

        /**
         * Used exclusively by SqLite
         */
        public fun stringValue(value: Any?): String = value.dbValue(properties.tables.dbType)

        public fun froms(withFrom: Boolean = true): String = with(properties) {
            val prefix = if (withFrom) {
                "FROM "
            } else {
                ""
            }
            fromClauses.joinToString(prefix = prefix) { fromClause ->
                fromClause.table.getFieldName(availableTables) + " " + fromClause.joinClauses.joinToString { joinClause ->
                    val ons = joinClause.references.entries.joinToString("and ") { reference ->
                        "${reference.key.getFieldName(availableColumns)} = ${
                            reference.value.getFieldName(
                                availableColumns
                            )
                        }"
                    }

                    "${joinClause.type.sql} ${joinClause.table.getFieldName(availableTables)} ON $ons"
                }
            }
        }

        public fun wheres(withWhere: Boolean = true): String = with(properties) {
            if (whereClauses.isEmpty()) {
                return ""
            }
            val where = StringBuilder()
            if (withWhere) {
                where.append("WHERE ")
            }
            whereClauses.forEach { typedWhereClause ->
                where.append(
                    when (typedWhereClause.type) {
                        WhereClauseType.AND -> " AND "
                        WhereClauseType.OR -> " OR "
                        else -> ""
                    }
                )
                where.append("(")
                typedWhereClause.whereClause.apply {
                    where.append(
                        when (this) {
                            is WhereClauseExists<*> -> {
                                val (_, result) = properties.executeSubQuery(
                                    dsl as SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<Any>
                                )
                                "EXISTS (${result.sql()})"
                            }
                            else -> {
                                val fieldName = if (this is WhereClauseWithColumn<*>) {
                                    column.getFieldName(availableColumns)
                                } else {
                                    (this as WhereClauseWithAlias<*>).alias.alias
                                }
                                when (operation) {
                                    Operation.EQ ->
                                        when (this) {
                                            is WhereClauseValue ->
                                                if (value == null) {
                                                    "$fieldName IS NULL"
                                                } else {
                                                    "$fieldName = ${variable()}"
                                                }
                                            is WhereClauseColumn -> "$fieldName = ${
                                                otherColumn.getFieldName(
                                                    availableColumns
                                                )
                                            }"
                                            else -> throw UnsupportedOperationException("$operation is not supported, should not happen !")
                                        }
                                    Operation.NOT_EQ ->
                                        when (this) {
                                            is WhereClauseValue ->
                                                if (value == null) {
                                                    "$fieldName IS NOT NULL"
                                                } else {
                                                    "$fieldName <> ${variable()}"
                                                }
                                            is WhereClauseColumn -> "$fieldName <> ${
                                                otherColumn.getFieldName(
                                                    availableColumns
                                                )
                                            }"
                                            else -> throw UnsupportedOperationException("$operation is not supported, should not happen !")
                                        }
                                    Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                        "$fieldName LIKE ${variable()}"
                                    Operation.INF -> "$fieldName < ${variable()}"
                                    Operation.INF_OR_EQ -> "$fieldName <= ${variable()}"
                                    Operation.SUP -> "$fieldName > ${variable()}"
                                    Operation.SUP_OR_EQ -> "$fieldName >= ${variable()}"
                                    Operation.IN ->
                                        when (this) {
                                            is WhereClauseValue ->
                                                when (module) {
                                                    // SQLITE, JDBC and R2DBC : must put as much params as collection size
                                                    Module.SQLITE, Module.JDBC ->
                                                        "$fieldName IN (${(value as Collection<*>).joinToString { "?" }})"
                                                    Module.R2DBC ->
                                                        when (tables.dbType) {
                                                            DbType.MYSQL -> "$fieldName IN (${(value as Collection<*>).joinToString { "?" }})"
                                                            DbType.H2, DbType.POSTGRESQL ->
                                                                "$fieldName IN (${(value as Collection<*>).joinToString { "$${++index}" }})"
                                                            DbType.MSSQL ->
                                                                "$fieldName IN (${(value as Collection<*>).joinToString { "@p${++index}" }})"
                                                            else ->
                                                                "$fieldName IN (${(value as Collection<*>).joinToString { ":k${index++}" }})"
                                                        }
                                                    else -> "$fieldName IN (:k${index++})"
                                                }
                                            is WhereClauseColumn -> TODO()
                                            else -> throw UnsupportedOperationException("$operation is not supported, should not happen !")
                                        }
                                    /*Operation.IS ->
                                        if (DbType.SQLITE == tables.dbType) {
                                            "$fieldName IS ?"
                                        } else {
                                            "$fieldName IS :k${index++}"
                                        }*/
                                    else -> throw UnsupportedOperationException("$operation is not supported, should not happen !")
                                }
                            }
                        }
                    )
                }
                where.append(")")
            }
            return where.toString()
        }
    }

    public abstract class WithWhere<T : SqlClientQuery.Where<T>> internal constructor() : WithProperties {
        protected abstract val where: T

        internal fun <U : Any> whereOpStringColumnNotNull(
            stringColumnNotNull: StringColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpStringColumnNotNull(where, properties, stringColumnNotNull, whereClauseType)
        
        internal fun <U : Any> whereOpStringColumnNullable(
            stringColumnNullable: StringColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpStringColumnNullable(where, properties, stringColumnNullable, whereClauseType)
        
        internal fun <U : Any> whereOpLocalDateTimeColumnNotNull(
            localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, localDateTimeColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpLocalDateTimeColumnNullable(
            localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, localDateTimeColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpKotlinxLocalDateTimeColumnNotNull(
            localDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, localDateTimeColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpKotlinxLocalDateTimeColumnNullable(
            localDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, localDateTimeColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpLocalDateColumnNotNull(
            localDateColumnNotNull: LocalDateColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, localDateColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpLocalDateColumnNullable(
            localDateColumnNullable: LocalDateColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, localDateColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpKotlinxLocalDateColumnNotNull(
            localDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, localDateColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpKotlinxLocalDateColumnNullable(
            localDateColumnNullable: KotlinxLocalDateColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, localDateColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpOffsetDateTimeColumnNotNull(
            offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, offsetDateTimeColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpOffsetDateTimeColumnNullable(
            offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, offsetDateTimeColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpLocalTimeColumnNotNull(
            localTimeColumnNotNull: LocalTimeColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNotNull(where, properties, localTimeColumnNotNull, whereClauseType)

        internal fun <U : Any> whereOpLocalTimeColumnNullable(
            localTimeColumnNullable: LocalTimeColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpDateColumnNullable(where, properties, localTimeColumnNullable, whereClauseType)

        internal fun <U : Any> whereOpBooleanColumnNotNull(
            booleanColumnNotNull: BooleanColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpBooleanColumnNotNull(where, properties, booleanColumnNotNull, whereClauseType)
        
        internal fun <U : Any> whereOpIntColumnNotNull(
            intColumnNotNull: IntColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpIntColumnNotNull(where, properties, intColumnNotNull, whereClauseType)
        
        internal fun <U : Any> whereOpIntColumnNullable(
            intColumnNullable: IntColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpIntColumnNullable(where, properties, intColumnNullable, whereClauseType)
        
        internal fun <U : Any> whereOpLongColumnNotNull(
            longColumnNotNull: LongColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLongColumnNotNull(where, properties, longColumnNotNull, whereClauseType)
        
        internal fun <U : Any> whereOpLongColumnNullable(
            longColumnNullable: LongColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLongColumnNullable(where, properties, longColumnNullable, whereClauseType)
        
        internal fun <U : Any> whereOpUuidColumnNotNull(
            uuidColumnNotNull: UuidColumnNotNull<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpUuidColumnNotNull(where, properties, uuidColumnNotNull, whereClauseType)
        
        internal fun <U : Any> whereOpUuidColumnNullable(
            uuidColumnNullable: UuidColumnNullable<U>,
            whereClauseType: WhereClauseType,
        ) = WhereOpUuidColumnNullable(where, properties, uuidColumnNullable, whereClauseType)

        internal fun whereOpStringAliasNotNull(
            stringAliasNotNull: QueryAlias<String>,
            whereClauseType: WhereClauseType,
        ) = WhereOpStringAliasNotNull(where, properties, stringAliasNotNull, whereClauseType)

        internal fun whereOpStringAliasNullable(
            stringAliasNullable: QueryAlias<String?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpStringAliasNullable(where, properties, stringAliasNullable, whereClauseType)

        internal fun whereOpLocalDateTimeAliasNotNull(
            localDateTimeAliasNotNull: QueryAlias<LocalDateTime>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalDateTimeAliasNotNull(where, properties, localDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpKotlinxLocalDateTimeAliasNotNull(
            kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>,
            whereClauseType: WhereClauseType,
        ) = WhereOpKotlinxLocalDateTimeAliasNotNull(where, properties, kotlinxLocalDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpLocalDateAliasNotNull(
            localDateAliasNotNull: QueryAlias<LocalDate>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalDateAliasNotNull(where, properties, localDateAliasNotNull, whereClauseType)

        internal fun whereOpKotlinxLocalDateAliasNotNull(
            kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>,
            whereClauseType: WhereClauseType,
        ) = WhereOpKotlinxLocalDateAliasNotNull(where, properties, kotlinxLocalDateAliasNotNull, whereClauseType)

        internal fun whereOpOffsetDateTimeAliasNotNull(
            offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>,
            whereClauseType: WhereClauseType,
        ) = WhereOpOffsetDateTimeAliasNotNull(where, properties, offsetDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpLocalTimeAliasNotNull(
            localTimeAliasNotNull: QueryAlias<LocalTime>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalTimeAliasNotNull(where, properties, localTimeAliasNotNull, whereClauseType)

        internal fun whereOpLocalDateTimeAliasNullable(
            localDateTimeAliasNotNull: QueryAlias<LocalDateTime?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalDateTimeAliasNullable(where, properties, localDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpKotlinxLocalDateTimeAliasNullable(
            kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpKotlinxLocalDateTimeAliasNullable(where, properties, kotlinxLocalDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpLocalDateAliasNullable(
            localDateAliasNotNull: QueryAlias<LocalDate?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalDateAliasNullable(where, properties, localDateAliasNotNull, whereClauseType)

        internal fun whereOpKotlinxLocalDateAliasNullable(
            kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpKotlinxLocalDateAliasNullable(where, properties, kotlinxLocalDateAliasNotNull, whereClauseType)

        internal fun whereOpOffsetDateTimeAliasNullable(
            offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpOffsetDateTimeAliasNullable(where, properties, offsetDateTimeAliasNotNull, whereClauseType)

        internal fun whereOpLocalTimeAliasNullable(
            localTimeAliasNotNull: QueryAlias<LocalTime?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLocalTimeAliasNullable(where, properties, localTimeAliasNotNull, whereClauseType)

        internal fun whereOpBooleanAliasNotNull(
            booleanAliasNotNull: QueryAlias<Boolean>,
            whereClauseType: WhereClauseType,
        ) = WhereOpBooleanAliasNotNull(where, properties, booleanAliasNotNull, whereClauseType)

        internal fun whereOpIntAliasNotNull(
            intAliasNotNull: QueryAlias<Int>,
            whereClauseType: WhereClauseType,
        ) = WhereOpIntAliasNotNull(where, properties, intAliasNotNull, whereClauseType)

        internal fun whereOpIntAliasNullable(
            intAliasNullable: QueryAlias<Int?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpIntAliasNullable(where, properties, intAliasNullable, whereClauseType)

        internal fun whereOpLongAliasNotNull(
            longAliasNotNull: QueryAlias<Long>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLongAliasNotNull(where, properties, longAliasNotNull, whereClauseType)

        internal fun whereOpLongAliasNullable(
            longAliasNullable: QueryAlias<Long?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpLongAliasNullable(where, properties, longAliasNullable, whereClauseType)

        internal fun whereOpUuidAliasNotNull(
            uuidAliasNotNull: QueryAlias<UUID>,
            whereClauseType: WhereClauseType,
        ) = WhereOpUuidAliasNotNull(where, properties, uuidAliasNotNull, whereClauseType)

        internal fun whereOpUuidAliasNullable(
            uuidAliasNullable: QueryAlias<UUID?>,
            whereClauseType: WhereClauseType,
        ) = WhereOpUuidAliasNullable(where, properties, uuidAliasNullable, whereClauseType)
    }
}

internal fun DefaultSqlClientCommon.Properties.variable() = when {
    module == Module.SQLITE || module == Module.JDBC
            || module == Module.R2DBC && tables.dbType == DbType.MYSQL -> "?"
    module == Module.R2DBC && (tables.dbType == DbType.H2 || tables.dbType == DbType.POSTGRESQL) -> "$${++this.index}"
    module == Module.R2DBC && tables.dbType == DbType.MSSQL -> "@p${++index}"
    else -> ":k${this.index++}"
}

internal data class SubQueryResult<T : Any>(
    internal val subQueryProperties: DefaultSqlClientSelect.Properties<T>,
    internal val result: SqlClientSubQuery.Return<T>,
)

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> DefaultSqlClientCommon.Properties.executeSubQuery(
    dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
): SubQueryResult<T> {
    val subQuery = SqlClientSubQueryImpl.Selectable(this)
// invoke sub-query
    val result = dsl(subQuery)
// add all sub-query parameters, if any, to parent's properties
    if (subQuery.properties.parameters.isNotEmpty()) {
        this.parameters.addAll(subQuery.properties.parameters)
    }
    return SubQueryResult(subQuery.properties as DefaultSqlClientSelect.Properties<T>, result)
}
