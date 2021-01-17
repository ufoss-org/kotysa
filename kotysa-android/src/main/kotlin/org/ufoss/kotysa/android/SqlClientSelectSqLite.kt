/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
            private val client: SQLiteDatabase,
            private val tables: Tables,
    ) : SqlClientSelect.Selectable {
        override fun <T : Any> select(column: ColumnNotNull<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectColumn(column) }
        override fun <T : Any> select(column: ColumnNullable<*, T>): SqlClientSelect.FirstSelect<T?> =
                FirstSelect<T?>(client, Properties(tables)).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, Properties(tables)).apply { addSelectTable(table) }
        override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
                SelectWithDsl(client, Properties(tables), dsl)
        override fun <T : Any> count(column: Column<*, T>): SqlClientSelect.FirstSelect<Int> =
                FirstSelect<Int>(client, Properties(tables)).apply { addCountColumn(column) }
    }

    internal class FirstSelect<T> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), SqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: ColumnNotNull<*, U>): SqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(column: ColumnNullable<*, U>): SqlClientSelect.SecondSelect<T, U?> =
                SecondSelect(client, properties as Properties<Pair<T, U?>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(table: Table<U>): SqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }
    }

    internal class SecondSelect<T, U> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.From<Pair<T, U>, V> =
                addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: ColumnNotNull<*, V>): SqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(column: ColumnNullable<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }
    }

    internal class ThirdSelect<T, U, V> internal constructor(
            private val client: SQLiteDatabase,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSelect.From<Triple<T, U, V>, W> =
                addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: ColumnNotNull<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(column: ColumnNullable<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(table: Table<W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }
    }

    internal class Select internal constructor(
            client: SQLiteDatabase,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<List<Any?>, U> =
                addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: ColumnNotNull<*, V>): SqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(column: ColumnNullable<*, V>): SqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.Select = this.apply { addSelectTable(table) }
    }

    internal class SelectWithDsl<T : Any> internal constructor(
            client: SQLiteDatabase,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), SqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    internal class From<T, U : Any> internal constructor(
            override val client: SQLiteDatabase,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.FromWhereable<T, U, SqlClientSelect.From<T, U>, SqlClientSelect.Where<T>>(properties), SqlClientSelect.From<T, U>, Return<T> {
        override val where = Where(client, properties)
        override val from = this
    }

    internal class Where<T>(
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

    private interface Return<T> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val client: SQLiteDatabase

        override fun fetchOne() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            if (!cursor.isLast) throw NonUniqueResultException()
            select(cursor.toRow())
        }



        override fun fetchOneOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                if (!cursor.isLast) throw NonUniqueResultException()
                select(cursor.toRow())
            }
        }

        override fun fetchFirst() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            select(cursor.toRow())
        }

        override fun fetchFirstOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                select(cursor.toRow())
            }
        }

        override fun fetchAll() = with(properties) {
            val cursor = fetch()
            val row = cursor.toRow()
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
