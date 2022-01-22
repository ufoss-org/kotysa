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
        public val fromClauses: MutableList<FromClause<*>>
        public val whereClauses: MutableList<WhereClauseWithType<*>>

        public val availableTables: MutableMap<Table<*>, KotysaTable<*>>
        public val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
        public var index: Int
    }

    public interface WithProperties {
        public val properties: Properties
    }

    public abstract class FromWhereable<T : Any, U : From<T, U>, V : Any, W : SqlClientQuery.Where<V, W>> :
        Whereable<V, W>(), From<T, U> {
        protected abstract val from: U
        private val joinable: Joinable<T, U, *> by lazy {
            Joinable<T, U, Any>(properties, from)
        }

        protected fun <X : Any> addFromTable(properties: Properties, kotysaTable: KotysaTable<X>) {
            isAvailable(properties, kotysaTable)
            properties.fromClauses.add(FromClause(kotysaTable.table))
        }

        internal fun <X : Any> addFromTable(table: Table<X>): U = with(properties) {
            addFromTable(this, tables.getTable(table))
            from
        }

        override fun <X : Any> innerJoin(table: Table<X>): SqlClientQuery.Joinable<T, U, X> {
            isAvailable(properties, properties.tables.getTable(table))
            val joinable = (joinable as Joinable<T, U, X>)
            joinable.type = JoinClauseType.INNER
            joinable.table = table
            return joinable
        }

        private fun <X : Any> isAvailable(properties: Properties, kotysaTable: KotysaTable<X>) {
            properties.apply {
                // This table becomes available
                availableTables[kotysaTable.table] = kotysaTable
                // All columns of this table become available
                kotysaTable.columns.forEach { column -> availableColumns[column.column] = column }
            }
        }
    }

    public class Joinable<U : Any, V : From<U, V>, W : Any> internal constructor(
        override val properties: Properties,
        from: V,
    ) : SqlClientQuery.Joinable<U, V, W>, WithProperties {
        internal lateinit var type: JoinClauseType
        internal lateinit var table: Table<W>

        private val join = Join<U, V, W>(properties, from)

        override fun on(column: Column<U, *>): SqlClientQuery.Join<U, V, W> =
            join.apply {
                type = this@Joinable.type
                table = this@Joinable.table
                this.column = column
            }
    }

    public class Join<U : Any, V : From<U, V>, W : Any> internal constructor(
        override val properties: Properties,
        private val from: V,
    ) : SqlClientQuery.Join<U, V, W>, WithProperties {
        internal lateinit var type: JoinClauseType
        internal lateinit var table: Table<W>
        internal lateinit var column: Column<U, *>

        override fun eq(column: Column<W, *>): V = with(properties) {
            // get last from
            val joinClause = JoinClause(table, mapOf(Pair(this@Join.column, column)), type)
            (fromClauses[fromClauses.size - 1] as FromClause<U>).joinClauses.add(joinClause)
            from
        }

    }

    public abstract class Whereable<T : Any, U : SqlClientQuery.Where<T, U>> protected constructor() :
        WithWhere<T, U>(), SqlClientQuery.Whereable<T, U>, WithProperties {

        override fun where(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U> =
            whereOpStringColumnNotNull.apply {
                this.column = stringColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U> =
            whereOpStringColumnNullable.apply {
                this.column = stringColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull.apply {
                this.column = localDateTimeColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable.apply {
                this.column = localDateTimeColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull.apply {
                this.column = kotlinxLocalDateTimeColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable.apply {
                this.column = kotlinxLocalDateTimeColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate> =
            whereOpLocalDateColumnNotNull.apply {
                this.column = localDateColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate> =
            whereOpLocalDateColumnNullable.apply {
                this.column = localDateColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull.apply {
                this.column = kotlinxLocalDateColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable.apply {
                this.column = kotlinxLocalDateColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull.apply {
                this.column = offsetDateTimeColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable.apply {
                this.column = offsetDateTimeColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime> =
            whereOpLocalTimeColumnNotNull.apply {
                this.column = localTimeColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime> =
            whereOpLocalTimeColumnNullable.apply {
                this.column = localTimeColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U> =
            whereOpBooleanColumnNotNull.apply {
                this.column = booleanColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U> =
            whereOpIntColumnNotNull.apply {
                this.column = intColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U> =
            whereOpIntColumnNullable.apply {
                this.column = intColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T, U> =
            whereOpLongColumnNotNull.apply {
                this.column = longColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T, U> =
            whereOpLongColumnNullable.apply {
                this.column = longColumnNullable
                type = WhereClauseType.WHERE
            }

        override fun where(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U> =
            whereOpUuidColumnNotNull.apply {
                this.column = uuidColumnNotNull
                type = WhereClauseType.WHERE
            }

        override fun where(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U> =
            whereOpUuidColumnNullable.apply {
                this.column = uuidColumnNullable
                type = WhereClauseType.WHERE
            }
    }

    public interface WhereOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> : WhereCommon<T> {
        public val where: U
        public var column: Column<T, V>
        public var type: WhereClauseType
    }

    public interface WhereInOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
        WhereOpColumn<T, U, V>, SqlClientQuery.WhereInOpColumn<T, U, V> {
        override infix fun `in`(values: Collection<V>): U =
            where.apply { addClauseValue(column, Operation.IN, values, type) }
    }

    public interface WhereOpColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
        WhereOpColumn<T, U, V>, SqlClientQuery.WhereOpColumnNotNull<T, U, V> {
        override infix fun eq(value: V): U = where.apply { addClauseValue(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V): U = where.apply { addClauseValue(column, Operation.NOT_EQ, value, type) }
    }

    public interface WhereOpColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
        WhereOpColumn<T, U, V>, SqlClientQuery.WhereOpColumnNullable<T, U, V> {
        override infix fun eq(value: V?): U = where.apply { addClauseValue(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V?): U = where.apply { addClauseValue(column, Operation.NOT_EQ, value, type) }
    }

    public abstract class AbstractWhereOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> internal constructor() :
        Where<T, U>(), WhereOpColumn<T, U, V> {
        override lateinit var column: Column<T, V>
        override lateinit var type: WhereClauseType
    }

    public interface WhereOpStringColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
        WhereInOpColumn<T, U, String>, SqlClientQuery.WhereOpStringColumn<T, U> {
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

    public class WhereOpStringColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, String>(), WhereOpStringColumn<T, U>,
        WhereOpColumnNotNull<T, U, String>, SqlClientQuery.WhereOpStringColumnNotNull<T, U>

    public class WhereOpStringColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, String>(), WhereOpStringColumn<T, U>,
        WhereOpColumnNullable<T, U, String>, SqlClientQuery.WhereOpStringColumnNullable<T, U>

    public interface WhereOpDateColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
        WhereInOpColumn<T, U, V>, SqlClientQuery.WhereOpDateColumn<T, U, V> {
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

    public class WhereOpDateColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, V>(), WhereOpDateColumn<T, U, V>,
        WhereOpColumnNotNull<T, U, V>, SqlClientQuery.WhereOpDateColumnNotNull<T, U, V>

    public class WhereOpDateColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, V>(), WhereOpDateColumn<T, U, V>,
        WhereOpColumnNullable<T, U, V>, SqlClientQuery.WhereOpDateColumnNullable<T, U, V>

    public class WhereOpBooleanColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, Boolean>(), SqlClientQuery.WhereOpBooleanColumnNotNull<T, U> {
        override infix fun eq(value: Boolean): U =
            where.apply {
                // SqLite does not support Boolean literal
                if (properties.tables.dbType == DbType.SQLITE) {
                    val intValue = if (value) 1 else 0
                    addClauseValue(column, Operation.EQ, intValue, type)
                } else {
                    addClauseValue(column, Operation.EQ, value, type)
                }
            }

        override infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherBooleanColumn, type) }
    }

    public interface WhereOpIntColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
        WhereInOpColumn<T, U, Int>, SqlClientQuery.WhereOpIntColumn<T, U> {
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

    public class WhereOpIntColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, Int>(), WhereOpIntColumn<T, U>,
        WhereOpColumnNotNull<T, U, Int>, SqlClientQuery.WhereOpIntColumnNotNull<T, U>

    public class WhereOpIntColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, Int>(), WhereOpIntColumn<T, U>,
        WhereOpColumnNullable<T, U, Int>, SqlClientQuery.WhereOpIntColumnNullable<T, U>

    public interface WhereOpLongColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
        WhereInOpColumn<T, U, Long>, SqlClientQuery.WhereOpLongColumn<T, U> {
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

    public class WhereOpLongColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, Long>(), WhereOpLongColumn<T, U>,
        WhereOpColumnNotNull<T, U, Long>, SqlClientQuery.WhereOpLongColumnNotNull<T, U>

    public class WhereOpLongColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, Long>(), WhereOpLongColumn<T, U>,
        WhereOpColumnNullable<T, U, Long>, SqlClientQuery.WhereOpLongColumnNullable<T, U>

    public interface WhereOpUuidColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
        WhereInOpColumn<T, U, UUID>, SqlClientQuery.WhereOpUuidColumn<T, U> {
        override infix fun eq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.EQ, otherUuidColumn, type) }

        override infix fun notEq(otherUuidColumn: UuidColumn<*>): U =
            where.apply { addClauseColumn(column, Operation.NOT_EQ, otherUuidColumn, type) }
    }

    public class WhereOpUuidColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereOpUuidColumn<T, U>,
        WhereOpColumnNotNull<T, U, UUID>, SqlClientQuery.WhereOpUuidColumnNotNull<T, U>

    public class WhereOpUuidColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
        override val where: U,
        override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereOpUuidColumn<T, U>,
        WhereOpColumnNullable<T, U, UUID>, SqlClientQuery.WhereOpUuidColumnNullable<T, U>

    public interface WhereCommon<T : Any> : WithProperties {
        public fun addClauseValue(
            column: Column<T, *>,
            operation: Operation,
            value: Any?,
            whereClauseType: WhereClauseType
        ) {
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseValue(column, operation, value),
                    whereClauseType,
                )
            )
        }

        public fun addClauseColumn(
            column: Column<T, *>,
            operation: Operation,
            otherColumn: Column<*, *>,
            whereClauseType: WhereClauseType
        ) {
            properties.whereClauses.add(
                WhereClauseWithType(
                    WhereClauseColumn(column, operation, otherColumn),
                    whereClauseType,
                )
            )
        }
    }

    public abstract class Where<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor() : WithWhere<T, U>(),
        SqlClientQuery.Where<T, U>, WhereCommon<T> {

        override fun and(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U> =
            whereOpStringColumnNotNull.apply {
                this.column = stringColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U> =
            whereOpStringColumnNullable.apply {
                this.column = stringColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull.apply {
                this.column = localDateTimeColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable.apply {
                this.column = localDateTimeColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull.apply {
                this.column = kotlinxLocalDateTimeColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable.apply {
                this.column = kotlinxLocalDateTimeColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate> =
            whereOpLocalDateColumnNotNull.apply {
                this.column = localDateColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate> =
            whereOpLocalDateColumnNullable.apply {
                this.column = localDateColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull.apply {
                this.column = kotlinxLocalDateColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable.apply {
                this.column = kotlinxLocalDateColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull.apply {
                this.column = offsetDateTimeColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable.apply {
                this.column = offsetDateTimeColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime> =
            whereOpLocalTimeColumnNotNull.apply {
                this.column = localTimeColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime> =
            whereOpLocalTimeColumnNullable.apply {
                this.column = localTimeColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U> =
            whereOpBooleanColumnNotNull.apply {
                this.column = booleanColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U> =
            whereOpIntColumnNotNull.apply {
                this.column = intColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U> =
            whereOpIntColumnNullable.apply {
                this.column = intColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T, U> =
            whereOpLongColumnNotNull.apply {
                this.column = longColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T, U> =
            whereOpLongColumnNullable.apply {
                this.column = longColumnNullable
                type = WhereClauseType.AND
            }

        override fun and(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U> =
            whereOpUuidColumnNotNull.apply {
                this.column = uuidColumnNotNull
                type = WhereClauseType.AND
            }

        override fun and(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U> =
            whereOpUuidColumnNullable.apply {
                this.column = uuidColumnNullable
                type = WhereClauseType.AND
            }

        override fun or(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U> =
            whereOpStringColumnNotNull.apply {
                this.column = stringColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U> =
            whereOpStringColumnNullable.apply {
                this.column = stringColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNotNull.apply {
                this.column = localDateTimeColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime> =
            whereOpLocalDateTimeColumnNullable.apply {
                this.column = localDateTimeColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNotNull.apply {
                this.column = kotlinxLocalDateTimeColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> =
            whereOpKotlinxLocalDateTimeColumnNullable.apply {
                this.column = kotlinxLocalDateTimeColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate> =
            whereOpLocalDateColumnNotNull.apply {
                this.column = localDateColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate> =
            whereOpLocalDateColumnNullable.apply {
                this.column = localDateColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNotNull.apply {
                this.column = kotlinxLocalDateColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> =
            whereOpKotlinxLocalDateColumnNullable.apply {
                this.column = kotlinxLocalDateColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNotNull.apply {
                this.column = offsetDateTimeColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime> =
            whereOpOffsetDateTimeColumnNullable.apply {
                this.column = offsetDateTimeColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime> =
            whereOpLocalTimeColumnNotNull.apply {
                this.column = localTimeColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime> =
            whereOpLocalTimeColumnNullable.apply {
                this.column = localTimeColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U> =
            whereOpBooleanColumnNotNull.apply {
                this.column = booleanColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U> =
            whereOpIntColumnNotNull.apply {
                this.column = intColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U> =
            whereOpIntColumnNullable.apply {
                this.column = intColumnNullable
                type = WhereClauseType.OR
            }

        override fun or(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U> =
            whereOpUuidColumnNotNull.apply {
                this.column = uuidColumnNotNull
                type = WhereClauseType.OR
            }

        override fun or(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U> =
            whereOpUuidColumnNullable.apply {
                this.column = uuidColumnNullable
                type = WhereClauseType.OR
            }
    }

    public interface Return : WithProperties {

        /**
         * Used exclusively by SqLite
         */
        public fun stringValue(value: Any?): String = value.dbValue()

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
                    val fieldName = column.getFieldName(availableColumns)
                    where.append(
                        when (operation) {
                            Operation.EQ ->
                                when (this) {
                                    is WhereClauseValue<*> ->
                                        if (value == null) {
                                            "$fieldName IS NULL"
                                        } else {
                                            "$fieldName = ${variable()}"
                                        }
                                    is WhereClauseColumn -> "$fieldName = ${otherColumn.getFieldName(availableColumns)}"
                                }
                            Operation.NOT_EQ ->
                                when (this) {
                                    is WhereClauseValue<*> ->
                                        if (value == null) {
                                            "$fieldName IS NOT NULL"
                                        } else {
                                            "$fieldName <> ${variable()}"
                                        }
                                    is WhereClauseColumn -> "$fieldName <> ${otherColumn.getFieldName(availableColumns)}"
                                }
                            Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                "$fieldName LIKE ${variable()}"
                            Operation.INF -> "$fieldName < ${variable()}"
                            Operation.INF_OR_EQ -> "$fieldName <= ${variable()}"
                            Operation.SUP -> "$fieldName > ${variable()}"
                            Operation.SUP_OR_EQ -> "$fieldName >= ${variable()}"
                            Operation.IN ->
                                when (this) {
                                    is WhereClauseValue<*> ->
                                        when (module) {
                                            Module.SQLITE, Module.JDBC ->
                                                // must put as much '?' as collection size
                                                "$fieldName IN (${(value as Collection<*>).joinToString { "?" }})"
                                            Module.R2DBC ->
                                                // must put as much '$X' as collection size
                                                "$fieldName IN (${(value as Collection<*>).joinToString { "$${++index}" }})"
                                            else -> "$fieldName IN (:k${index++})"
                                        }
                                    is WhereClauseColumn -> TODO()
                                }
                            /*Operation.IS ->
                                if (DbType.SQLITE == tables.dbType) {
                                    "$fieldName IS ?"
                                } else {
                                    "$fieldName IS :k${index++}"
                                }*/
                        }
                    )
                }
                where.append(")")
            }
            return where.toString()
        }
    }

    public abstract class WithWhere<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor() : WithProperties {
        protected abstract val where: U

        internal val whereOpStringColumnNotNull: WhereOpStringColumnNotNull<T, U> by lazy {
            WhereOpStringColumnNotNull(where, properties)
        }
        internal val whereOpStringColumnNullable: WhereOpStringColumnNullable<T, U> by lazy {
            WhereOpStringColumnNullable(where, properties)
        }
        internal val whereOpLocalDateTimeColumnNotNull: WhereOpDateColumnNotNull<T, U, LocalDateTime> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpLocalDateTimeColumnNullable: WhereOpDateColumnNullable<T, U, LocalDateTime> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpKotlinxLocalDateTimeColumnNotNull: WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpKotlinxLocalDateTimeColumnNullable: WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpLocalDateColumnNotNull: WhereOpDateColumnNotNull<T, U, LocalDate> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpLocalDateColumnNullable: WhereOpDateColumnNullable<T, U, LocalDate> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpKotlinxLocalDateColumnNotNull: WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpKotlinxLocalDateColumnNullable: WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpOffsetDateTimeColumnNotNull: WhereOpDateColumnNotNull<T, U, OffsetDateTime> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpOffsetDateTimeColumnNullable: WhereOpDateColumnNullable<T, U, OffsetDateTime> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpLocalTimeColumnNotNull: WhereOpDateColumnNotNull<T, U, LocalTime> by lazy {
            WhereOpDateColumnNotNull(where, properties)
        }
        internal val whereOpLocalTimeColumnNullable: WhereOpDateColumnNullable<T, U, LocalTime> by lazy {
            WhereOpDateColumnNullable(where, properties)
        }
        internal val whereOpBooleanColumnNotNull: WhereOpBooleanColumnNotNull<T, U> by lazy {
            WhereOpBooleanColumnNotNull(where, properties)
        }
        internal val whereOpIntColumnNotNull: WhereOpIntColumnNotNull<T, U> by lazy {
            WhereOpIntColumnNotNull(where, properties)
        }
        internal val whereOpIntColumnNullable: WhereOpIntColumnNullable<T, U> by lazy {
            WhereOpIntColumnNullable(where, properties)
        }
        internal val whereOpLongColumnNotNull: WhereOpLongColumnNotNull<T, U> by lazy {
            WhereOpLongColumnNotNull(where, properties)
        }
        internal val whereOpLongColumnNullable: WhereOpLongColumnNullable<T, U> by lazy {
            WhereOpLongColumnNullable(where, properties)
        }
        internal val whereOpUuidColumnNotNull: WhereOpUuidColumnNotNull<T, U> by lazy {
            WhereOpUuidColumnNotNull(where, properties)
        }
        internal val whereOpUuidColumnNullable: WhereOpUuidColumnNullable<T, U> by lazy {
            WhereOpUuidColumnNullable(where, properties)
        }
    }
}

internal fun DefaultSqlClientCommon.Properties.variable() = when {
    module == Module.SQLITE || module == Module.JDBC
            || module == Module.R2DBC && tables.dbType == DbType.MYSQL -> "?"
    module == Module.R2DBC && (tables.dbType == DbType.H2 || tables.dbType == DbType.POSTGRESQL) -> "$${++this.index}"
    module == Module.R2DBC && tables.dbType == DbType.MSSQL -> "@p${++index}"
    else -> ":k${this.index++}"
}
