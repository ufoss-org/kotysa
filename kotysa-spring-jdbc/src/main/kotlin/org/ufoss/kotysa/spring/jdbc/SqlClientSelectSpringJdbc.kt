/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.toRow
import java.math.BigDecimal
import java.util.stream.Stream


@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectSpringJdbc private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
            private val client: NamedParameterJdbcOperations,
            private val tables: Tables,
    ) : SqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.JDBC, Module.SPRING_JDBC)

        override fun <T : Any> select(column: Column<*, T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
                FirstSelect<T>(client, properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
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
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), SqlClientSelect.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<T, U> =
                addFromTable(table, from as FromTable<T, U>)

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
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.FromTable<Pair<T, U>, V> =
                addFromTable(table, from as FromTable<Pair<T, U>, V>)

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
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSelect.FromTable<Triple<T, U, V>, W> =
                addFromTable(table, from as FromTable<Triple<T, U, V>, W>)

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
            client: NamedParameterJdbcOperations,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.Select {
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<List<Any?>, U> =
                addFromTable(table, from as FromTable<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.Select = this.apply { addSelectColumn(column) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): SqlClientSelect.Select = this.apply { addCountColumn(column) }
        override fun <V : Any> andDistinct(column: Column<*, V>): SqlClientSelect.Select = this.apply {
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
            client: NamedParameterJdbcOperations,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), SqlClientSelect.Fromable<T> {
        private val from: FromTable<T, *> = FromTable<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<T, U> =
                addFromTable(table, from as FromTable<T, U>)
    }

    private class FromTable<T : Any, U : Any>(
            override val client: NamedParameterJdbcOperations,
            properties: Properties<T>,
    ) : FromWhereable<T, U, SqlClientSelect.FromTable<T, U>, SqlClientSelect.Where<T>,
            SqlClientSelect.LimitOffset<T>, SqlClientSelect.GroupByPart2<T>,
            SqlClientSelect.OrderByPart2<T>>(properties), SqlClientSelect.FromTable<T, U>, GroupBy<T>, OrderBy<T>,
            SqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.FromTable<T, V> =
            addFromTable(table, from as FromTable<T, V>)
    }

    private class Where<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupByPart2<T>, SqlClientSelect.OrderByPart2<T>>(), SqlClientSelect.Where<T>,
            GroupBy<T>, OrderBy<T>, SqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 by lazy { OrderByPart2(client, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
            SqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
            override val client: NamedParameterJdbcOperations,
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
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, SqlClientSelect.OrderByPart2<T>>, SqlClientSelect.OrderByPart2<T>,
            DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
            DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByPart2(client, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, SqlClientSelect.LimitOffset<T>,
            Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val client: NamedParameterJdbcOperations

        override fun fetchOne() = fetchOneOrNull() ?: throw NoResultException()

        override fun fetchOneOrNull(): T? =
                try {
                    client.queryForObject(selectSql(), buildParameters()) { rs, _ ->
                        properties.select(rs.toRow())
                    }
                } catch (e: IncorrectResultSizeDataAccessException) {
                    if (e.actualSize > 0) {
                        throw NonUniqueResultException()
                    }
                    null
                }

        override fun fetchFirst(): T? = with(fetchAll()) {
            if (isEmpty()) {
                throw NoResultException()
            }
            this[0]
        }

        override fun fetchFirstOrNull() = fetchAllNullable().firstOrNull()

        override fun fetchAll(): List<T> =
                fetchAllNullable()
                        .filterNotNull()

        override fun fetchAllStream(): Stream<T> =
                client.queryForStream(selectSql(), buildParameters()) { rs, _ ->
                    properties.select(rs.toRow())
                }

        private fun fetchAllNullable(): List<T?> =
                client.query(selectSql(), buildParameters()) { rs, _ ->
                    properties.select(rs.toRow())
                }

        private fun buildParameters(): SqlParameterSource = with(properties) {
            val parameters = MapSqlParameterSource()
            // 1) add all values from where part
            bindWhereParams(parameters)
            // 2) add limit and offset (order is different depending on DbType)
            if (DbType.MSSQL == tables.dbType) {
                offsetParam(parameters)
                limitParam(parameters)
            } else {
                limitParam(parameters)
                offsetParam(parameters)
            }

            parameters
        }

        private fun offsetParam(parameters: MapSqlParameterSource) {
            with(properties) {
                if (offset != null) {
                    parameters.addValue("k${index++}", offset)
                }
            }
        }

        private fun limitParam(parameters: MapSqlParameterSource) {
            with(properties) {
                if (limit != null) {
                    parameters.addValue("k${index++}", limit)
                }
            }
        }
    }
}
