/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

public open class DefaultSqlClientCommon protected constructor() : SqlClientQuery() {

    public interface Properties {
        public val tables: Tables
        public val fromClauses: MutableList<FromClause<*>>
        public val whereClauses: MutableList<WhereClauseWithType<*>>

        public val availableTables: MutableMap<Table<*>, KotysaTable<*>>
        public val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
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

        @Suppress("UNCHECKED_CAST")
        protected fun <U : Any> addFromTable(properties: Properties, table: KotysaTable<U>) {
            properties.apply {
                // This table becomes available
                availableTables[table.table] = table
                // All columns of this table become available
                table.columns.forEach { column -> availableColumns[column.column] = column }
                // Add table from clause list
                fromClauses.add(FromClause(table.table))
            }
        }

        internal fun <X : Any> addFromTable(table: Table<X>): U = with(properties) {
            addFromTable(this, tables.getTable(table))
            from
        }

        override fun <X : Any> innerJoin(table: Table<X>): SqlClientQuery.Joinable<T, U, X> {
            joinable.type = JoinClauseType.INNER
            return joinable as SqlClientQuery.Joinable<T, U, X>
        }
    }

    public class Joinable<U : Any, V : From<U, V>, W : Any> internal constructor(
            override val properties: Properties,
            from: V,
    ) : SqlClientQuery.Joinable<U, V, W>, WithProperties {
        internal lateinit var type: JoinClauseType

        private val join = Join<U, V, W>(properties, from)

        override fun on(column: Column<U, *>): SqlClientQuery.Join<U, V, W> =
                join.apply {
                    type = this@Joinable.type
                    this.column = column
                }
    }

    public class Join<U : Any, V : From<U, V>, W : Any> internal constructor(
            override val properties: Properties,
            private val from: V,
    ) : SqlClientQuery.Join<U, V, W>, WithProperties {
        internal lateinit var type: JoinClauseType
        internal lateinit var column: Column<U, *>

        override fun eq(column: Column<W, *>): V = with(properties) {
            // get last from
            val joinClause = JoinClause(mapOf(Pair(this@Join.column, column)), type)
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

    /*protected interface Join : WithProperties, Instruction {
        public fun <T : Any> addJoinClause(dsl: (FieldProvider) -> ColumnField<*, *>, joinClass: KClass<T>, alias: String?, type: JoinType) {
            properties.apply {
                tables.checkTable(joinClass)
                val aliasedTable = AliasedKotysaTable(tables.getTable(joinClass), alias)
                joinClauses.add(JoinDsl(dsl, aliasedTable, type, availableColumns, tables.dbType).initialize())
                addAvailableColumnsFromTable(this, aliasedTable)
            }
        }
    }*/

    public interface WhereOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> : WhereCommon<T> {
        public val where: U
        public var column: Column<T, V>
        public var type: WhereClauseType
    }

    public interface WhereInOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
            WhereOpColumn<T, U, V>, SqlClientQuery.WhereInOpColumn<T, U, V> {
        override infix fun `in`(values: Collection<V>): U = where.apply { addClause(column, Operation.IN, values, type) }
    }

    public interface WhereOpColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
            WhereOpColumn<T, U, V>, SqlClientQuery.WhereOpColumnNotNull<T, U, V> {
        override infix fun eq(value: V): U = where.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V): U = where.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public interface WhereOpColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> :
            WhereOpColumn<T, U, V>, SqlClientQuery.WhereOpColumnNullable<T, U, V> {
        override infix fun eq(value: V?): U = where.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V?): U = where.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public abstract class AbstractWhereOpColumn<T : Any, U : SqlClientQuery.Where<T, U>, V : Any> internal constructor() :
            Where<T, U>(), WhereOpColumn<T, U, V> {
        override lateinit var column: Column<T, V>
        override lateinit var type: WhereClauseType
    }

    public interface WhereOpStringColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
            WhereInOpColumn<T, U, String>, SqlClientQuery.WhereOpStringColumn<T, U> {
        override infix fun contains(value: String): U = where.apply { addClause(column, Operation.CONTAINS, "%$value%", type) }
        override infix fun startsWith(value: String): U = where.apply { addClause(column, Operation.STARTS_WITH, "$value%", type) }
        override infix fun endsWith(value: String): U = where.apply { addClause(column, Operation.ENDS_WITH, "%$value", type) }
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
        override infix fun before(value: V): U = where.apply { addClause(column, Operation.INF, value, type) }
        override infix fun after(value: V): U = where.apply { addClause(column, Operation.SUP, value, type) }
        override infix fun beforeOrEq(value: V): U = where.apply { addClause(column, Operation.INF_OR_EQ, value, type) }
        override infix fun afterOrEq(value: V): U = where.apply { addClause(column, Operation.SUP_OR_EQ, value, type) }
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
                        addClause(column, Operation.EQ, intValue, type)
                    } else {
                        addClause(column, Operation.EQ, value, type)
                    }
                }
    }

    public interface WhereOpIntColumn<T : Any, U : SqlClientQuery.Where<T, U>> :
            WhereInOpColumn<T, U, Int>, SqlClientQuery.WhereOpIntColumn<T, U> {
        override infix fun inf(value: Int): U = where.apply { addClause(column, Operation.INF, value, type) }
        override infix fun sup(value: Int): U = where.apply { addClause(column, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Int): U = where.apply { addClause(column, Operation.INF_OR_EQ, value, type) }
        override infix fun supOrEq(value: Int): U = where.apply { addClause(column, Operation.SUP_OR_EQ, value, type) }
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

    public class WhereOpUuidColumnNotNull<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
            override val where: U,
            override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereInOpColumn<T, U, UUID>,
            WhereOpColumnNotNull<T, U, UUID>, SqlClientQuery.WhereOpUuidColumnNotNull<T, U>

    public class WhereOpUuidColumnNullable<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor(
            override val where: U,
            override val properties: Properties,
    ) : AbstractWhereOpColumn<T, U, UUID>(), WhereInOpColumn<T, U, UUID>,
            WhereOpColumnNullable<T, U, UUID>, SqlClientQuery.WhereOpUuidColumnNullable<T, U>

    public interface WhereCommon<T : Any> : WithProperties {
        public fun addClause(column: Column<T, *>, operation: Operation, value: Any?, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(
                    WhereClauseWithType(
                            WhereClause(column, operation, value),
                            whereClauseType,
                    )
            )
        }
    }

    public abstract class Where<T : Any, U : SqlClientQuery.Where<T, U>> internal constructor() : WithWhere<T, U>(), SqlClientQuery.Where<T, U>, WhereCommon<T> {

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

        /*public fun joins(): String = with(properties) {
            properties.joinClauses.joinToString { joinClause ->
                val ons = joinClause.references.entries.joinToString("and ") { reference ->
                    "${reference.key.getFieldName(tables.allColumns)} = ${reference.value.getFieldName(tables.allColumns)}"
                }

                "${joinClause.type.sql} ${tables.getTable(joinClause.references.values.first()).getFieldName()} ON $ons"
            }
        }*/

        public fun wheres(withWhere: Boolean = true, offset: Int = 0): String = with(properties) {
            if (whereClauses.isEmpty()) {
                return ""
            }
            val where = StringBuilder()
            if (withWhere) {
                where.append("WHERE ")
            }
            var index = offset
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
                                    if (value == null) {
                                        "$fieldName IS NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "$fieldName = ?"
                                        } else {
                                            "$fieldName = :k${index++}"
                                        }
                                    }
                                Operation.NOT_EQ ->
                                    if (value == null) {
                                        "$fieldName IS NOT NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "$fieldName <> ?"
                                        } else {
                                            "$fieldName <> :k${index++}"
                                        }
                                    }
                                Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName LIKE ?"
                                    } else {
                                        "$fieldName LIKE :k${index++}"
                                    }
                                Operation.INF ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName < ?"
                                    } else {
                                        "$fieldName < :k${index++}"
                                    }
                                Operation.INF_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName <= ?"
                                    } else {
                                        "$fieldName <= :k${index++}"
                                    }
                                Operation.SUP ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName > ?"
                                    } else {
                                        "$fieldName > :k${index++}"
                                    }
                                Operation.SUP_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName >= ?"
                                    } else {
                                        "$fieldName >= :k${index++}"
                                    }
                                /*Operation.IS ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "$fieldName IS ?"
                                    } else {
                                        "$fieldName IS :k${index++}"
                                    }*/
                                Operation.IN ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        // must put as much '?' as
                                        "$fieldName IN (${(value as Collection<*>).joinToString { "?" }})"
                                    } else {
                                        "$fieldName IN (:k${index++})"
                                    }
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
        internal val whereOpUuidColumnNotNull: WhereOpUuidColumnNotNull<T, U> by lazy {
            WhereOpUuidColumnNotNull(where, properties)
        }
        internal val whereOpUuidColumnNullable: WhereOpUuidColumnNullable<T, U> by lazy {
            WhereOpUuidColumnNullable(where, properties)
        }
    }
}