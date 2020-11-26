/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public open class DefaultSqlClientCommon protected constructor() : SqlClientQuery() {

    public interface Properties {
        public val tables: Tables
        public val whereClauses: MutableList<TypedWhereClause<*>>
        //public val joinClauses: MutableSet<JoinClause<*, *>>
        public val availableColumns: MutableSet<KotysaColumn<*, *>>
    }

    protected interface Instruction {
        @Suppress("UNCHECKED_CAST")
        public fun <T : Any> addAvailableColumnsFromTable(
                properties: Properties,
                table: KotysaTable<T>
        ) {
            properties.apply {
                table.columns.forEach { column -> availableColumns.add(column) }
            }
        }
    }

    public interface WithProperties {
        public val properties: Properties
    }

    protected interface Whereable : WithProperties

    public abstract class TypedWhereable<T : Any, U : TypedWhere<T>> protected constructor() :
            SqlClientQuery.TypedWhereable<T, U>, WithProperties {
        protected abstract val typedWhere: U

        private val typedWhereOpStringColumnNotNull: TypedWhereOpStringColumnNotNull<T, U> by lazy {
            TypedWhereOpStringColumnNotNull(typedWhere)
        }
        private val typedWhereOpStringColumnNullable: TypedWhereOpStringColumnNullable<T, U> by lazy {
            TypedWhereOpStringColumnNullable(typedWhere)
        }
        /*private val typedWhereOpIntColumnNotNull: TypedWhereOpIntColumnNotNull<T, U> by lazy {
            TypedWhereOpIntColumnNotNull(typedWhere)
        }*/

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

        /*override fun where(intColumnNotNull: IntColumnNotNull<T>): TypedWhereOpIntColumnNotNull<T, U> =
                typedWhereOpIntColumnNotNull.apply {
                    this.intColumnNotNull = intColumnNotNull
                    type = WhereClauseType.WHERE
                }*/
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

    public interface TypedWhereOpColumn<T : Any, U : TypedWhere<T>, V: Any> {
        public val typedWhere: U
        public var column: Column<T, V>
        public var type: WhereClauseType
    }

    public interface TypedWhereInOpColumn<T : Any, U : TypedWhere<T>, V: Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereInOpColumn<T, U, V> {
        override infix fun `in`(values: Collection<V>): U =
                typedWhere.apply { addClause(column, Operation.IN, values, type) }
    }

    public interface TypedWhereOpColumnNotNull<T : Any, U : TypedWhere<T>, V: Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereOpColumnNotNull<T, U, V> {
        override infix fun eq(value: V): U =
                typedWhere.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V): U =
                typedWhere.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public interface TypedWhereOpColumnNullable<T : Any, U : TypedWhere<T>, V: Any> :
            TypedWhereOpColumn<T, U, V>, SqlClientQuery.TypedWhereOpColumnNullable<T, U, V> {
        override infix fun eq(value: V?): U =
                typedWhere.apply { addClause(column, Operation.EQ, value, type) }
        override infix fun notEq(value: V?): U =
                typedWhere.apply { addClause(column, Operation.NOT_EQ, value, type) }
    }

    public abstract class AbstractTypedWhereOpColumn<T : Any, U : TypedWhere<T>, V: Any> : TypedWhereOpColumn<T, U, V> {
        override lateinit var column: Column<T, V>
        override lateinit var type: WhereClauseType
    }

    public interface TypedWhereOpStringColumn<T : Any, U : TypedWhere<T>> :
            TypedWhereInOpColumn<T, U, String>, SqlClientQuery.TypedWhereOpStringColumn<T, U> {
        override infix fun contains(value: String): U =
                typedWhere.apply { addClause(column, Operation.CONTAINS, value, type) }

        override infix fun startsWith(value: String): U =
                typedWhere.apply { addClause(column, Operation.STARTS_WITH, value, type) }

        override infix fun endsWith(value: String): U =
                typedWhere.apply { addClause(column, Operation.ENDS_WITH, value, type) }
    }

    public class TypedWhereOpStringColumnNotNull<T : Any, U : TypedWhere<T>>(
            override val typedWhere: U,
    ) : AbstractTypedWhereOpColumn<T, U, String>(), TypedWhereOpStringColumn<T, U>,
            TypedWhereOpColumnNotNull<T, U, String>, SqlClientQuery.TypedWhereOpStringColumnNotNull<T, U>

    public class TypedWhereOpStringColumnNullable<T : Any, U : TypedWhere<T>>(
            override val typedWhere: U,
    ) : AbstractTypedWhereOpColumn<T, U, String>(), TypedWhereOpStringColumn<T, U>,
            TypedWhereOpColumnNullable<T, U, String>, SqlClientQuery.TypedWhereOpStringColumnNullable<T, U>

    /*public class TypedWhereOpIntColumnNotNull<T : Any, U : TypedWhere<T>>(
            private val typedWhere: U,
    ) : SqlClientQuery.TypedWhereOpIntColumnNotNull<T, U> {
        internal lateinit var intColumnNotNull: IntColumnNotNull<T>
        internal lateinit var type: WhereClauseType

        override fun eq(value: Int): U =
            typedWhere.apply { addClause(intColumnNotNull, Operation.EQ, value, type) }

        override fun notEq(value: Int): U =
                typedWhere.apply { addClause(intColumnNotNull, Operation.NOT_EQ, value, type) }
    }*/

    protected interface Where : WithProperties {
        public fun addClause(whereClause: WhereClause<*>, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(TypedWhereClause(whereClause, whereClauseType))
        }
    }

    public interface TypedWhere<T : Any> : SqlClientQuery.TypedWhere<T>, WithProperties {
        public fun addClause(column: Column<T, *>, operation: Operation, value: Any?, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(TypedWhereClause(WhereClause(column, operation, value), whereClauseType))
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
                                        "${column.getFieldName(tables.allColumns)} IS NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${column.getFieldName(tables.allColumns)} = ?"
                                        } else {
                                            "${column.getFieldName(tables.allColumns)} = :k${index++}"
                                        }
                                    }
                                Operation.NOT_EQ ->
                                    if (value == null) {
                                        "${column.getFieldName(tables.allColumns)} IS NOT NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${column.getFieldName(tables.allColumns)} <> ?"
                                        } else {
                                            "${column.getFieldName(tables.allColumns)} <> :k${index++}"
                                        }
                                    }
                                Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} LIKE ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} LIKE :k${index++}"
                                    }
                                Operation.INF ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} < ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} < :k${index++}"
                                    }
                                Operation.INF_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} <= ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} <= :k${index++}"
                                    }
                                Operation.SUP ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} > ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} > :k${index++}"
                                    }
                                Operation.SUP_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} >= ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} >= :k${index++}"
                                    }
                                Operation.IS ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${column.getFieldName(tables.allColumns)} IS ?"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} IS :k${index++}"
                                    }
                                Operation.IN ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        // must put as much '?' as
                                        "${column.getFieldName(tables.allColumns)} IN (${(value as Collection<*>).joinToString { "?" }})"
                                    } else {
                                        "${column.getFieldName(tables.allColumns)} IN (:k${index++})"
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