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
        public val whereClauses: MutableList<TypedWhereClause<*>>

        //public val joinClauses: MutableSet<JoinClause<*, *>>
        public val availableTables: MutableMap<Table<*>, KotysaTable<*>>
        public val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
    }

    protected interface Instruction {
        @Suppress("UNCHECKED_CAST")
        public fun <T : Any> addAvailableTable(
                properties: Properties,
                table: KotysaTable<T>,
        ) {
            properties.apply {
                availableTables[table.table] = table
                table.columns.forEach { column -> availableColumns[column.column] = column }
            }
        }
    }

    public interface WithProperties {
        public val properties: Properties
    }

    protected interface Whereable : WithProperties

    public abstract class TypedWhereable<T : Any, U : SqlClientQuery.TypedWhere<T>> protected constructor() :
            SqlClientQuery.TypedWhereable<T, U>, WithProperties {
        protected abstract val typedWhere: U

        private val typedWhereOpStringColumnNotNull: TypedWhereOpStringColumnNotNull<T, U> by lazy {
            TypedWhereOpStringColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpStringColumnNullable: TypedWhereOpStringColumnNullable<T, U> by lazy {
            TypedWhereOpStringColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpLocalDateTimeColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, LocalDateTime> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpLocalDateTimeColumnNullable: TypedWhereOpDateColumnNullable<T, U, LocalDateTime> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpKotlinxLocalDateTimeColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpKotlinxLocalDateTimeColumnNullable: TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpLocalDateColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, LocalDate> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpLocalDateColumnNullable: TypedWhereOpDateColumnNullable<T, U, LocalDate> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpKotlinxLocalDateColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpKotlinxLocalDateColumnNullable: TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpOffsetDateTimeColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, OffsetDateTime> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpOffsetDateTimeColumnNullable: TypedWhereOpDateColumnNullable<T, U, OffsetDateTime> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpLocalTimeColumnNotNull: TypedWhereOpDateColumnNotNull<T, U, LocalTime> by lazy {
            TypedWhereOpDateColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpLocalTimeColumnNullable: TypedWhereOpDateColumnNullable<T, U, LocalTime> by lazy {
            TypedWhereOpDateColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpBooleanColumnNotNull: TypedWhereOpBooleanColumnNotNull<T, U> by lazy {
            TypedWhereOpBooleanColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpIntColumnNotNull: TypedWhereOpIntColumnNotNull<T, U> by lazy {
            TypedWhereOpIntColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpIntColumnNullable: TypedWhereOpIntColumnNullable<T, U> by lazy {
            TypedWhereOpIntColumnNullable(typedWhere, properties)
        }
        private val typedWhereOpUuidColumnNotNull: TypedWhereOpUuidColumnNotNull<T, U> by lazy {
            TypedWhereOpUuidColumnNotNull(typedWhere, properties)
        }
        private val typedWhereOpUuidColumnNullable: TypedWhereOpUuidColumnNullable<T, U> by lazy {
            TypedWhereOpUuidColumnNullable(typedWhere, properties)
        }

        override fun where(stringColumnNotNull: StringColumnNotNull<T>): TypedWhereOpStringColumnNotNull<T, U> =
                typedWhereOpStringColumnNotNull.apply {
                    this.column = stringColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(stringColumnNullable: StringColumnNullable<T>): TypedWhereOpStringColumnNullable<T, U> =
                typedWhereOpStringColumnNullable.apply {
                    this.column = stringColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalDateTime> =
                typedWhereOpLocalDateTimeColumnNotNull.apply {
                    this.column = localDateTimeColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalDateTime> =
                typedWhereOpLocalDateTimeColumnNullable.apply {
                    this.column = localDateTimeColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime> =
                typedWhereOpKotlinxLocalDateTimeColumnNotNull.apply {
                    this.column = kotlinxLocalDateTimeColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime> =
                typedWhereOpKotlinxLocalDateTimeColumnNullable.apply {
                    this.column = kotlinxLocalDateTimeColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(localDateColumnNotNull: LocalDateColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalDate> =
                typedWhereOpLocalDateColumnNotNull.apply {
                    this.column = localDateColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(localDateColumnNullable: LocalDateColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalDate> =
                typedWhereOpLocalDateColumnNullable.apply {
                    this.column = localDateColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate> =
                typedWhereOpKotlinxLocalDateColumnNotNull.apply {
                    this.column = kotlinxLocalDateColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate> =
                typedWhereOpKotlinxLocalDateColumnNullable.apply {
                    this.column = kotlinxLocalDateColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, OffsetDateTime> =
                typedWhereOpOffsetDateTimeColumnNotNull.apply {
                    this.column = offsetDateTimeColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, OffsetDateTime> =
                typedWhereOpOffsetDateTimeColumnNullable.apply {
                    this.column = offsetDateTimeColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalTime> =
                typedWhereOpLocalTimeColumnNotNull.apply {
                    this.column = localTimeColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(localTimeColumnNullable: LocalTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalTime> =
                typedWhereOpLocalTimeColumnNullable.apply {
                    this.column = localTimeColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(booleanColumnNotNull: BooleanColumnNotNull<T>): TypedWhereOpBooleanColumnNotNull<T, U> =
                typedWhereOpBooleanColumnNotNull.apply {
                    this.column = booleanColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(intColumnNotNull: IntColumnNotNull<T>): TypedWhereOpIntColumnNotNull<T, U> =
                typedWhereOpIntColumnNotNull.apply {
                    this.column = intColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(intColumnNullable: IntColumnNullable<T>): TypedWhereOpIntColumnNullable<T, U> =
                typedWhereOpIntColumnNullable.apply {
                    this.column = intColumnNullable
                    type = WhereClauseType.WHERE
                }

        override fun where(uuidColumnNotNull: UuidColumnNotNull<T>): TypedWhereOpUuidColumnNotNull<T, U> =
                typedWhereOpUuidColumnNotNull.apply {
                    this.column = uuidColumnNotNull
                    type = WhereClauseType.WHERE
                }

        override fun where(uuidColumnNullable: UuidColumnNullable<T>): TypedWhereOpUuidColumnNullable<T, U> =
                typedWhereOpUuidColumnNullable.apply {
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

    public interface TypedWhereOpColumn<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> : TypedWhere<T> {
        public val typedWhere: U
        public var column: Column<T, V>
        public var type: WhereClauseType
    }

    public interface TypedWhereInOpColumn<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereInOpColumn<T, U, V> {
        override infix fun `in`(values: Collection<V>): U = typedWhere.apply { addClause(column, Operation.IN, values, type) }
    }

    public interface TypedWhereOpColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereOpColumnNotNull<T, U, V> {
        override infix fun eq(value: V): U = typedWhere.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V): U = typedWhere.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public interface TypedWhereOpColumnNullable<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereOpColumnNullable<T, U, V> {
        override infix fun eq(value: V?): U = typedWhere.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V?): U = typedWhere.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public abstract class AbstractTypedWhereOpColumn<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> protected constructor() : TypedWhereOpColumn<T, U, V> {
        override lateinit var column: Column<T, V>
        override lateinit var type: WhereClauseType
    }

    public interface TypedWhereOpStringColumn<T : Any, U : SqlClientQuery.TypedWhere<T>> :
            TypedWhereInOpColumn<T, U, String>, SqlClientQuery.TypedWhereOpStringColumn<T, U> {
        override infix fun contains(value: String): U = typedWhere.apply { addClause(column, Operation.CONTAINS, value, type) }
        override infix fun startsWith(value: String): U = typedWhere.apply { addClause(column, Operation.STARTS_WITH, value, type) }
        override infix fun endsWith(value: String): U = typedWhere.apply { addClause(column, Operation.ENDS_WITH, value, type) }
    }

    public class TypedWhereOpStringColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, String>(), TypedWhereOpStringColumn<T, U>,
            TypedWhereOpColumnNotNull<T, U, String>, SqlClientQuery.TypedWhereOpStringColumnNotNull<T, U>

    public class TypedWhereOpStringColumnNullable<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, String>(), TypedWhereOpStringColumn<T, U>,
            TypedWhereOpColumnNullable<T, U, String>, SqlClientQuery.TypedWhereOpStringColumnNullable<T, U>

    public interface TypedWhereOpDateColumn<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> :
            TypedWhereInOpColumn<T, U, V>, SqlClientQuery.TypedWhereOpDateColumn<T, U, V> {
        override infix fun before(value: V): U = typedWhere.apply { addClause(column, Operation.INF, value, type) }
        override infix fun after(value: V): U = typedWhere.apply { addClause(column, Operation.SUP, value, type) }
        override infix fun beforeOrEq(value: V): U = typedWhere.apply { addClause(column, Operation.INF_OR_EQ, value, type) }
        override infix fun afterOrEq(value: V): U = typedWhere.apply { addClause(column, Operation.SUP_OR_EQ, value, type) }
    }

    public class TypedWhereOpDateColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, V>(), TypedWhereOpDateColumn<T, U, V>,
            TypedWhereOpColumnNotNull<T, U, V>, SqlClientQuery.TypedWhereOpDateColumnNotNull<T, U, V>

    public class TypedWhereOpDateColumnNullable<T : Any, U : SqlClientQuery.TypedWhere<T>, V : Any> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, V>(), TypedWhereOpDateColumn<T, U, V>,
            TypedWhereOpColumnNullable<T, U, V>, SqlClientQuery.TypedWhereOpDateColumnNullable<T, U, V>

    public class TypedWhereOpBooleanColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, Boolean>(), SqlClientQuery.TypedWhereOpBooleanColumnNotNull<T, U> {
        override infix fun eq(value: Boolean): U = typedWhere.apply { addClause(column, Operation.EQ, value, type) }
    }

    public interface TypedWhereOpIntColumn<T : Any, U : SqlClientQuery.TypedWhere<T>> :
            TypedWhereInOpColumn<T, U, Int>, SqlClientQuery.TypedWhereOpIntColumn<T, U> {
        override infix fun inf(value: Int): U = typedWhere.apply { addClause(column, Operation.INF, value, type) }
        override infix fun sup(value: Int): U = typedWhere.apply { addClause(column, Operation.SUP, value, type) }
        override infix fun infOrEq(value: Int): U = typedWhere.apply { addClause(column, Operation.INF_OR_EQ, value, type) }
        override infix fun supOrEq(value: Int): U = typedWhere.apply { addClause(column, Operation.SUP_OR_EQ, value, type) }
    }

    public class TypedWhereOpIntColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, Int>(), TypedWhereOpIntColumn<T, U>,
            TypedWhereOpColumnNotNull<T, U, Int>, SqlClientQuery.TypedWhereOpIntColumnNotNull<T, U>

    public class TypedWhereOpIntColumnNullable<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, Int>(), TypedWhereOpIntColumn<T, U>,
            TypedWhereOpColumnNullable<T, U, Int>, SqlClientQuery.TypedWhereOpIntColumnNullable<T, U>

    public class TypedWhereOpUuidColumnNotNull<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, UUID>(), TypedWhereInOpColumn<T, U, UUID>,
            TypedWhereOpColumnNotNull<T, U, UUID>, SqlClientQuery.TypedWhereOpUuidColumnNotNull<T, U>

    public class TypedWhereOpUuidColumnNullable<T : Any, U : SqlClientQuery.TypedWhere<T>> internal constructor(
            override val typedWhere: U,
            override val properties: Properties,
    ) : AbstractTypedWhereOpColumn<T, U, UUID>(), TypedWhereInOpColumn<T, U, UUID>,
            TypedWhereOpColumnNullable<T, U, UUID>, SqlClientQuery.TypedWhereOpUuidColumnNullable<T, U>


    public interface Where : WithProperties {
        public fun addClause(whereClause: WhereClause<*>, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(TypedWhereClause(whereClause, whereClauseType))
        }
    }

    public interface TypedWhere<T : Any> : WithProperties {
        public fun addClause(column: Column<T, *>, operation: Operation, value: Any?, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(
                    TypedWhereClause(
                            WhereClause(column.toField(properties), operation, value),
                            whereClauseType,
                    )
            )
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
                    where.append(
                            when (operation) {
                                Operation.EQ ->
                                    if (value == null) {
                                        "${columnField.fieldName} IS NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${columnField.fieldName} = ?"
                                        } else {
                                            "${columnField.fieldName} = :k${index++}"
                                        }
                                    }
                                Operation.NOT_EQ ->
                                    if (value == null) {
                                        "${columnField.fieldName} IS NOT NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${columnField.fieldName} <> ?"
                                        } else {
                                            "${columnField.fieldName} <> :k${index++}"
                                        }
                                    }
                                Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} LIKE ?"
                                    } else {
                                        "${columnField.fieldName} LIKE :k${index++}"
                                    }
                                Operation.INF ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} < ?"
                                    } else {
                                        "${columnField.fieldName} < :k${index++}"
                                    }
                                Operation.INF_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} <= ?"
                                    } else {
                                        "${columnField.fieldName} <= :k${index++}"
                                    }
                                Operation.SUP ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} > ?"
                                    } else {
                                        "${columnField.fieldName} > :k${index++}"
                                    }
                                Operation.SUP_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} >= ?"
                                    } else {
                                        "${columnField.fieldName} >= :k${index++}"
                                    }
                                Operation.IS ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${columnField.fieldName} IS ?"
                                    } else {
                                        "${columnField.fieldName} IS :k${index++}"
                                    }
                                Operation.IN ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        // must put as much '?' as
                                        "${columnField.fieldName} IN (${(value as Collection<*>).joinToString { "?" }})"
                                    } else {
                                        "${columnField.fieldName} IN (:k${index++})"
                                    }
                            }
                    )
                }
                where.append(")")
            }
            return where.toString()
        }
    }
}