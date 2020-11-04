/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

internal class SqlClientDeleteSqLite private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class Delete<T : Any> internal constructor(
        override val client: SQLiteDatabase,
        override val tables: Tables,
        override val tableClass: KClass<T>
    ) : SqlClientDeleteOrUpdate.DeleteOrUpdate<T>(), DeleteOrUpdate<T>, Return<T> {
        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(
            joinClass: KClass<U>,
            alias: String?,
            type: JoinType
        ): SqlClientDeleteOrUpdate.Joinable =
            Joinable(client, properties, joinClass, alias, type)

        override fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): SqlClientDeleteOrUpdate.TypedWhere<T> {
            val where = TypedWhere(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

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
    }

    private class TypedWhere<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.TypedWhere<T>, SqlClientDeleteOrUpdate.TypedWhere<T>, Return<T> {

        override fun and(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): SqlClientDeleteOrUpdate.TypedWhere<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): SqlClientDeleteOrUpdate.TypedWhere<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>,
        SqlClientDeleteOrUpdate.Return {
        val client: SQLiteDatabase

        override fun execute() = with(properties) {
            val statement = client.compileStatement(deleteFromTableSql())

            // add all values from where part
            bindWhereArgs(statement)

            statement.executeUpdateDelete()
        }
    }
}
