/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*
import java.math.BigDecimal

@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
            private val client: SQLiteDatabase,
            private val tables: Tables,
    ) : SqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.ANDROID)

        override fun <T : Any> select(column: Column<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }
        override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectTable(table) }
        override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
                SelectWithDsl(client, properties(), dsl)
        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addCountColumn(column) }
        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSelect.FirstSelect<BigDecimal> =
                FirstSelect<BigDecimal>(client, properties()).apply { addAvgColumn(column) }
        override fun selectSum(column: IntColumn<*>): SqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }
    }

    private class FirstSelect<T : Any>(
            private val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), SqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: Column<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }
        override fun <U : Any> and(table: Table<U>): SqlClientSelect.SecondSelect<T, U> =
                SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }
        override fun <U : Any> andCount(column: Column<*, U>): SqlClientSelect.SecondSelect<T, Long> =
                SecondSelect(client, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }
        override fun <U : Any> andDistinct(column: Column<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
                SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SqlClientSelect.SecondSelect<T?, BigDecimal> =
                SecondSelect(client, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): SqlClientSelect.SecondSelect<T?, Long> =
                SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }
    }

    private class SecondSelect<T, U>(
            private val client: SQLiteDatabase,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.From<Pair<T, U>, V> =
                addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.ThirdSelect<T, U, V> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
                ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, BigDecimal> =
                ThirdSelect(client, properties as Properties<Triple<T, U, BigDecimal>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): SqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }
    }

    private class ThirdSelect<T, U, V>(
            private val client: SQLiteDatabase,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSelect.From<Triple<T, U, V>, W> =
                addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: Column<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }
        override fun <W : Any> and(table: Table<W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }
        override fun <W : Any> andCount(column: Column<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addCountColumn(column) }
        override fun <W : Any> andDistinct(column: Column<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.DISTINCT)
                }
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MIN)
                }
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply {
                    addSelectColumn(column, FieldClassifier.MAX)
                }
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }
        override fun andSum(column: IntColumn<*>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }
    }

    private class Select(
            client: SQLiteDatabase,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(client, properties)

        override fun <T : Any> from(table: Table<T>): SqlClientSelect.From<List<Any?>, T> =
                addFromTable(table, from as From<List<Any?>, T>)

        override fun <T : Any> and(column: Column<*, T>): SqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <T : Any> and(table: Table<T>): SqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <T : Any> andCount(column: Column<*, T>): SqlClientSelect.Select = this.apply { addCountColumn(column) }
        override fun <T : Any> andDistinct(column: Column<*, T>): SqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): SqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): SqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): SqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }
        override fun andSum(column: IntColumn<*>): SqlClientSelect.Select = this.apply { addLongSumColumn(column) }
    }

    private class SelectWithDsl<T : Any>(
            client: SQLiteDatabase,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), SqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
                addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
            override val client: SQLiteDatabase,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.FromWhereable<T, U, SqlClientSelect.From<T, U>, SqlClientSelect.Where<T>,
            SqlClientSelect.LimitOffset<T>, SqlClientSelect.GroupByPart2<T>,
            SqlClientSelect.OrderByPart2<T>>(properties), SqlClientSelect.From<T, U>, GroupBy<T>, OrderBy<T>,
            SqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
    }

    private class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupByPart2<T>, SqlClientSelect.OrderByPart2<T>>(), SqlClientSelect.Where<T>, GroupBy<T>,
            OrderBy<T>, SqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
            SqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByPart2<T, SqlClientSelect.GroupByPart2<T>>, SqlClientSelect.GroupByPart2<T>,
            DefaultSqlClientSelect.OrderBy<T, SqlClientSelect.OrderByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override val groupByPart2 = this
    }

    private interface OrderBy<T : Any> : DefaultSqlClientSelect.OrderBy<T, SqlClientSelect.OrderByPart2<T>>,
            SqlClientSelect.OrderBy<T>, Return<T>

    private class OrderByPart2<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, SqlClientSelect.OrderByPart2<T>>, SqlClientSelect.OrderByPart2<T>,
            DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, SqlClientSelect.LimitOffset<T>,
            Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val client: SQLiteDatabase

        override fun fetchOne() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            if (!cursor.isLast) throw NonUniqueResultException()
            select(cursor.toRow()) ?: throw NoResultException()
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
                val result = select(row)
                if (result != null) {
                    results.add(result)
                }
                row.resetIndex()
            }
            results
        }

        private fun fetch(): Cursor = with(properties) {
            val sql = selectSql()
            val selectionArgs = buildWhereArgs()
            if (limit != null) {
                selectionArgs.add(stringValue(limit))
            }
            if (offset != null) {
                selectionArgs.add(stringValue(offset))
            }

            return client.rawQuery(sql, selectionArgs.toTypedArray())
        }
    }
}
