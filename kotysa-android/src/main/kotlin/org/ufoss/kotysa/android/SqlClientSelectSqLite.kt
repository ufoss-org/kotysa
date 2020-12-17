/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Selectable<T : Any> internal constructor(
            client: SQLiteDatabase,
            tables: Tables,
    ) : DefaultSqlClientSelect.Selectable<T, SqlClientSelect.FirstSelect<T, Any, Any>, SqlClientSelect.From<T>>(tables),
            SqlClientSelect.Selectable<T> {
        override val select:
                DefaultSqlClientSelect.Select<T, SqlClientSelect.FirstSelect<T, Any, Any>, SqlClientSelect.From<T>, *, *, *> =
                FirstSelect(client, properties)
    }

    internal class FirstSelect<T : Any, U : Any, V : Any> internal constructor(
            client: SQLiteDatabase,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T, SqlClientSelect.FirstSelect<T, U, V>, SqlClientSelect.From<T>, Pair<T, U>,
            SqlClientSelect.SecondSelect<T, U, V>, SqlClientSelect.From<Pair<T, U>>>(properties), SqlClientSelect.FirstSelect<T, U, V> {
        override val from: DefaultSqlClientSelect.From<T, SqlClientSelect.From<T>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.FirstSelect<T, U, V> = this
        override val nextSelect:
                DefaultSqlClientSelect.Select<Pair<T, U>, SqlClientSelect.SecondSelect<T, U, V>, *, *, *, *> by lazy {
            @Suppress("UNCHECKED_CAST")
            SecondSelect(client, properties as Properties<Pair<T, U>>)
        }
    }

    internal class SecondSelect<T : Any, U : Any, V : Any> internal constructor(
            client: SQLiteDatabase,
            properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>, SqlClientSelect.SecondSelect<T, U, V>, SqlClientSelect.From<Pair<T, U>>,
            Triple<T, U, V>, SqlClientSelect.ThirdSelect<T, U, V>, SqlClientSelect.From<Triple<T, U, V>>>(properties),
            SqlClientSelect.SecondSelect<T, U, V> {
        override val from: DefaultSqlClientSelect.From<Pair<T, U>, SqlClientSelect.From<Pair<T, U>>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.SecondSelect<T, U, V> = this
        override val nextSelect: DefaultSqlClientSelect.Select<Triple<T, U, V>,
                SqlClientSelect.ThirdSelect<T, U, V>, *, *, *, *> by lazy {
            @Suppress("UNCHECKED_CAST")
            ThirdSelect(client, properties as Properties<Triple<T, U, V>>)
        }
    }

    internal class ThirdSelect<T : Any, U : Any, V : Any> internal constructor(
            client: SQLiteDatabase,
            properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>, SqlClientSelect.ThirdSelect<T, U, V>,
            SqlClientSelect.From<Triple<T, U, V>>, List<Any>, SqlClientSelect.Select,
            SqlClientSelect.From<List<Any>>>(properties), SqlClientSelect.ThirdSelect<T, U, V> {
        override val from: DefaultSqlClientSelect.From<Triple<T, U, V>, SqlClientSelect.From<Triple<T, U, V>>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.ThirdSelect<T, U, V> = this
        override val nextSelect: DefaultSqlClientSelect.Select<List<Any>, SqlClientSelect.Select, *, *, *, *> by lazy {
            @Suppress("UNCHECKED_CAST")
            Select(client, properties as Properties<List<Any>>)
        }
    }

    internal class Select internal constructor(
            client: SQLiteDatabase,
            properties: Properties<List<Any>>,
    ) : DefaultSqlClientSelect.Select<List<Any>, SqlClientSelect.Select, SqlClientSelect.From<List<Any>>, List<Any>,
            SqlClientSelect.Select, SqlClientSelect.From<List<Any>>>(properties), SqlClientSelect.Select {
        override val from: DefaultSqlClientSelect.From<*, SqlClientSelect.From<List<Any>>, *> = From(client, properties)
        override val select: SqlClientSelect.Select = this
        override val nextSelect: DefaultSqlClientSelect.Select<List<Any>, SqlClientSelect.Select, *, *, *, *> = this
    }

    internal class From<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.From<T, SqlClientSelect.From<T>, SqlClientSelect.Where<T>>(properties), SqlClientSelect.From<T>, Return<T> {
        override val where = Where(client, properties)
        override val from: SqlClientSelect.From<T> = this

        /*override fun <U : Any> join(
                joinClass: KClass<U>,
                alias: String?,
                type: JoinType
            ): SqlClientSelect.Joinable<T> =
                Joinable(client, properties, joinClass, alias, type)*/
    }

    /*
    private class Joinable<T : Any, U : Any>(
        private val client: SQLiteDatabase,
        private val properties: Properties<T>,
        private val joinClass: KClass<U>,
        private val alias: String?,
        private val type: JoinType
    ) : SqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): SqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, SqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, SqlClientSelect.Whereable<T> {
        val client: SQLiteDatabase

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }
     */

    internal class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>>(), SqlClientSelect.Where<T>, Return<T> {
        override val where = this

        /*override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }*/
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val client: SQLiteDatabase

        override fun fetchOne() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            if (!cursor.isLast) throw NonUniqueResultException()
            val row = SqLiteRow(cursor)
            select(row)
        }

        override fun fetchOneOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                if (!cursor.isLast) throw NonUniqueResultException()
                val row = SqLiteRow(cursor)
                select(row)
            }
        }

        override fun fetchFirst() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            val row = SqLiteRow(cursor)
            select(row)
        }

        override fun fetchFirstOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                val row = SqLiteRow(cursor)
                select(row)
            }
        }

        override fun fetchAll() = with(properties) {
            val cursor = fetch()
            val row = SqLiteRow(cursor)
            val results = mutableListOf<T>()
            while (cursor.moveToNext()) {
                results.add(select(row))
                row.resetIndex()
            }
            results
        }

        private fun fetch() = client.rawQuery(selectSql(), buildWhereArgs())
    }
}
