/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa
/*
import org.ufoss.kolog.Logger
import kotlin.reflect.KClass

private val logger = Logger.of<DefaultSqlClientDeleteOrUpdate>()


public open class DefaultSqlClientDeleteOrUpdate protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any> internal constructor(
            override val tables: Tables,
            /**
             * targeted table to update
             */
            public val tableOld: KotysaTableOld<T>,
            override val availableColumns: MutableMap<(Any) -> Any?, KotysaColumn<*, *>>
    ) : DefaultSqlClientCommon.Properties {
        override val whereClauses: MutableList<TypedWhereClause> = mutableListOf()
        override val joinClauses: MutableList<JoinClause> = mutableListOf()
        public val setValues: MutableMap<KotysaColumn<T, *>, Any?> = mutableMapOf()
    }

    public interface WithProperties<T : Any> {
        public val properties: Properties<T>
    }

    protected interface DeleteOrUpdate<T : Any> : Instruction {

        public val tables: Tables
        public val tableClass: KClass<T>

        public fun initProperties(): Properties<T> {
            tables.checkTable(tableClass)
            val table = tables.getTable(tableClass)
            val properties = Properties(tables, table, mutableMapOf())
            // init availableColumns with table columns
            addAvailableColumnsFromTable(properties, table)
            return properties
        }
    }

    protected interface Update<T : Any> : DeleteOrUpdate<T>, WithProperties<T> {
        public fun addSetValue(dsl: (FieldSetter<T>) -> Unit) {
            properties.apply {
                val setValue = UpdateSetDsl(dsl, availableColumns, tables.dbType).initialize()
                setValues[setValue.first.kotysaColumn] = setValue.second
            }
        }
    }

    protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>, Instruction

    protected interface Where<T : Any> : DefaultSqlClientCommon.Where, WithProperties<T>

    protected interface TypedWhere<T : Any> : DefaultSqlClientCommon.TypedWhere<T>, WithProperties<T>

    public interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {

        public fun deleteFromTableSql(): String = with(properties) {
            val deleteSql = "DELETE FROM ${tableOld.name}"
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

            "$deleteSql $joinsAndWheres"
        }

        public fun updateTableSql(): String = with(properties) {
            val updateSql = "UPDATE ${tableOld.name}"
            var index = 0
            val setSql = setValues.keys.joinToString(prefix = "SET ") { column ->
                if (DbType.SQLITE == tables.dbType) {
                    "${column.name} = ?"
                } else {
                    "${column.name} = :k${index++}"
                }
            }
            val joinsAndWheres = joinsWithExistsAndWheres(offset = index)
            logger.debug { "Exec SQL (${tables.dbType.name}) : $updateSql $setSql $joinsAndWheres" }

            "$updateSql $setSql $joinsAndWheres"
        }

        /**
         * Handle joins as EXISTS + nested SELECT
         * Then other WHERE clauses
         */
        public fun joinsWithExistsAndWheres(withWhere: Boolean = true, offset: Int = 0): String = with(properties) {
            val joins = joinsWithExists()

            var wheres = wheres(false, offset)

            if (joins.isEmpty() && wheres.isEmpty()) {
                ""
            } else {
                val prefix = if (withWhere) {
                    "WHERE "
                } else {
                    ""
                }
                if (joins.isNotEmpty()) {
                    if (wheres.isNotEmpty()) {
                        wheres = "AND $wheres"
                    }
                    "$prefix$joins $wheres )"
                } else {
                    "$prefix$wheres"
                }
            }
        }

        /**
         * Handle joins as EXISTS + nested SELECT
         */
        private fun joinsWithExists() = with(properties) {
            val rootJoinClause = rootJoinClause()
            if (rootJoinClause != null) {
                // fixme handle multiple columns
                val joinedTableFieldName = "${rootJoinClause.table.prefix}.${rootJoinClause.table.primaryKey.columns[0].name}"
                // remaining joins
                val joins = joins()

                "EXISTS ( SELECT * FROM ${rootJoinClause.table.declaration} $joins WHERE ${rootJoinClause.field.fieldName} = $joinedTableFieldName"
            } else {
                ""
            }
        }

        /**
         * Try to find a "root" joinClause on one column of the table this query targets
         */
        private fun rootJoinClause() = with(properties) {
            if (joinClauses.isNotEmpty()) {
                val rootJoinClause = joinClauses
                        .firstOrNull { joinClause ->
                            joinClause.field.kotysaColumn.tableOld == tableOld
                                    && JoinType.INNER == joinClause.type
                        }
                        ?: throw IllegalArgumentException("There must be a join clause on one column of the table this query targets")
                joinClauses.remove(rootJoinClause)

                rootJoinClause
            } else {
                null
            }
        }
    }
}*/
