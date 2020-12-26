/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

internal class SqlClientUpdateSqLite private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            override val tables: Tables,
            override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            SqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Update<T>>(),
            SqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val from = this
    }

    /*
    private class Joinable<T : Any, U : Any>(
        private val client: SQLiteDatabase,
        private val properties: Properties<T>,
        private val joinClass: KClass<U>,
        private val alias: String?,
        private val type: JoinType
    ) : SqlClientDeleteOrUpdate.Joinable {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): SqlClientDeleteOrUpdate.Join {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Join<T>, SqlClientDeleteOrUpdate.Join, Return<T> {
        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientDeleteOrUpdate.Where {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Where, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientDeleteOrUpdate.Where {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientDeleteOrUpdate.Where {
            addOrClause(dsl)
            return this
        }
    }*/

    internal class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this

        /*override fun and(whereClause: WhereClause<T>): SqlClientDeleteOrUpdate.TypedWhere<T> {
            addClause(whereClause, WhereClauseType.AND)
            return this
        }

        override fun or(whereClause: WhereClause<T>): SqlClientDeleteOrUpdate.TypedWhere<T> {
            addClause(whereClause, WhereClauseType.OR)
            return this
        }*/
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val client: SQLiteDatabase

        override fun execute() = with(properties) {
            val statement = client.compileStatement(updateTableSql())

            var index = 1
            // 1) add all values from set part
            setValues.values.forEach { value -> statement.bind(index++, value) }
            // 2) add all values from where part
            bindWhereArgs(statement, index)

            statement.executeUpdateDelete()
        }
    }
}
