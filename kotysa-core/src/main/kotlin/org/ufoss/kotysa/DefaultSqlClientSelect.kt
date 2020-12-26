/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

private val logger = Logger.of<DefaultSqlClientSelect>()

@Suppress("UNCHECKED_CAST")
public open class DefaultSqlClientSelect protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T>(
            override val tables: Tables,
    ) : DefaultSqlClientCommon.Properties {
        internal val selectedFields = mutableListOf<Field<*>>()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()

        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()

        public lateinit var select: (Row) -> T
    }

    protected interface WithProperties<T> : DefaultSqlClientCommon.WithProperties {
        override val properties: Properties<T>
    }

    public abstract class Select<T> protected constructor() :
            Fromable, Andable, WithProperties<T> {

        /**
         * 'select' phase is finished, start 'from' phase
         */
        public fun <U : Any, V : From<U, V>> addFromTable(table: Table<U>, from: FromWhereable<T, U, V, *>): V = with(properties) {
            select = when (selectedFields.size) {
                1 -> selectedFields[0].builder as (Row) -> T
                2 -> {
                    { row: Row -> Pair(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row)) } as (Row) -> T
                }
                3 -> {
                    { row: Row ->
                        Triple(selectedFields[0].builder.invoke(row), selectedFields[1].builder.invoke(row),
                                selectedFields[2].builder.invoke(row))
                    } as (Row) -> T
                }
                else -> {
                    { row: Row -> selectedFields.map { selectedField -> selectedField.builder.invoke(row) } } as (Row) -> T
                }
            }
            from.apply { addFromTable(table) } as V
        }

        public fun <U : Any> addSelectTable(table: Table<U>) {
            properties.selectedFields.add(table.toField(properties))
        }

        public fun <U : Any> addSelectColumn(column: ColumnNotNull<*, U>) {
            properties.selectedFields.add(column.toField(properties))
        }

        public fun <U : Any> addSelectColumn(column: ColumnNullable<*, U>) {
            properties.selectedFields.add(column.toField(properties))
        }
    }

    public abstract class FromWhereable<T, U : Any, V : From<U, V>, W : SqlClientQuery.Where<Any, W>> protected constructor(
            final override val properties: Properties<T>,
    ) : DefaultSqlClientCommon.FromWhereable<U, V, Any, W>(), Return<T>

    /*public abstract class SelectDsl<T : Any> : Instruction, WithProperties<T> {
        public abstract val dsl: () -> T
    }*/

    //protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>

    public abstract class Where<T, U : SqlClientQuery.Where<Any, U>> : DefaultSqlClientCommon.Where<Any, U>(), Return<T>

    protected interface Return<T> : DefaultSqlClientCommon.Return, WithProperties<T> {
        public fun selectSql(): String = with(properties) {
            val selects = selectedFields.joinToString(prefix = "SELECT ") { field -> field.fieldNames.joinToString() }
            /*val froms = fromClauses
                    .map(FromClause<*>::table)
                    //.filterNot { aliasedTable -> joinClauses.map { joinClause -> joinClause.table }.contains(aliasedTable) }
                    .joinToString(prefix = "FROM ") { table -> table.getKotysaTable(properties.availableTables).name }*/
            val froms = froms()
            val wheres = wheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $selects $froms $wheres" }

            "$selects $froms $wheres"
        }
    }
}
