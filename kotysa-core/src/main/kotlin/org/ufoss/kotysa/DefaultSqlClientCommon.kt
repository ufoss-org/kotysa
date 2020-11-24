/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public open class DefaultSqlClientCommon protected constructor() {

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

    protected interface TypedWhereable<T : Any> : WithProperties

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

    protected interface TypedWhereOpIntColumnNotNull<T : Any> : WithProperties

    protected interface Where : WithProperties {
        public fun addClause(whereClause: WhereClause<*>, whereClauseType: WhereClauseType) {
            properties.whereClauses.add(TypedWhereClause(whereClause, whereClauseType))
        }
    }

    protected interface TypedWhere<T : Any> : WithProperties {
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