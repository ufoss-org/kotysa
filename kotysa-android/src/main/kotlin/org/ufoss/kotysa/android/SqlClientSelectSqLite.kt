/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Selectable<T : Any> internal constructor(
            client: SQLiteDatabase,
            tables: Tables,
    ) : DefaultSqlClientSelect.Selectable<T, SqlClientSelect.FirstSelect<T>>(tables), SqlClientSelect.Selectable<T> {
        override val select: DefaultSqlClientSelect.Select<T, *, SqlClientSelect.FirstSelect<T>> =
                FirstSelect(client, properties)
    }

    internal class FirstSelect<T : Any> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T, SqlClientSelect.From<T>, SqlClientSelect.FirstSelect<T>>(),
            SqlClientSelect.FirstSelect<T> {
        override val from: DefaultSqlClientSelect.From<T, SqlClientSelect.From<T>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.FirstSelect<T> = this

        override fun <U : Any> and(column: Column<*, U>): SqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(table: Table<U>): SqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }
    }

    internal class SecondSelect<T : Any, U : Any> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>, SqlClientSelect.From<Pair<T, U>>,
            SqlClientSelect.SecondSelect<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        override val from: DefaultSqlClientSelect.From<Pair<T, U>, SqlClientSelect.From<Pair<T, U>>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.SecondSelect<T, U> = this

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }
    }

    internal class ThirdSelect<T : Any, U : Any, V : Any> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>, SqlClientSelect.From<Triple<T, U, V>>,
            SqlClientSelect.ThirdSelect<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        override val from: DefaultSqlClientSelect.From<Triple<T, U, V>, SqlClientSelect.From<Triple<T, U, V>>, *> by lazy {
            From(client, properties)
        }
        override val select: SqlClientSelect.ThirdSelect<T, U, V> = this

        override fun <W : Any> and(column: Column<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(table: Table<W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any>>).apply { addSelectTable(table) }
    }

    internal class Select internal constructor(
            client: SQLiteDatabase,
            override val properties: Properties<List<Any>>,
    ) : DefaultSqlClientSelect.Select<List<Any>, SqlClientSelect.From<List<Any>>, SqlClientSelect.Select>(),
            SqlClientSelect.Select {
        override val from: DefaultSqlClientSelect.From<*, SqlClientSelect.From<List<Any>>, *> = From(client, properties)
        override val select: SqlClientSelect.Select = this

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.Select = this.apply { addSelectTable(table) }
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
