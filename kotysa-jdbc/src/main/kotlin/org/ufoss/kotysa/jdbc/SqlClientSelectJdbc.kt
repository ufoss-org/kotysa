/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.jdbcBindWhereParams
import org.ufoss.kotysa.core.jdbc.toRow
import java.math.BigDecimal
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

@Suppress("UNCHECKED_CAST")
internal class SqlClientSelectJdbc private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
        private val jdbcConnection: JdbcConnection,
        private val tables: Tables,
    ) : SqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.JDBC, Module.JDBC)

        override fun <T : Any> select(column: Column<*, T>): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
            SelectWithDsl(jdbcConnection, properties(), dsl)

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(jdbcConnection, properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSelect.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(jdbcConnection, properties()).apply { addAvgColumn(column) }

        override fun selectSum(column: IntColumn<*>): SqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(jdbcConnection, properties()).apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(jdbcConnection, properties()).apply { addSelectSubQuery(dsl) }
    }

    private class FirstSelect<T : Any>(
        private val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), SqlClientSelect.FirstSelect<T> {
        private val from: From<T, *> by lazy {
            From<T, Any>(jdbcConnection, properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
            addFromTable(table, from as From<T, U>)

        override fun <U : Any> and(column: Column<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): SqlClientSelect.SecondSelect<T, U> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): SqlClientSelect.SecondSelect<T, Long> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SqlClientSelect.SecondSelect<T?, BigDecimal> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): SqlClientSelect.SecondSelect<T?, Long> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(jdbcConnection, properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }
    }

    private class SecondSelect<T, U>(
        private val jdbcConnection: JdbcConnection,
        override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        private val from: From<Pair<T, U>, *> by lazy {
            From<Pair<T, U>, Any>(jdbcConnection, properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.From<Pair<T, U>, V> =
            addFromTable(table, from as From<Pair<T, U>, V>)

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): SqlClientSelect.ThirdSelect<T, U, V> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(
                jdbcConnection,
                properties as Properties<Triple<T, U, BigDecimal>>
            ).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): SqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(jdbcConnection, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }
    }

    private class ThirdSelect<T, U, V>(
        private val jdbcConnection: JdbcConnection,
        override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        private val from: From<Triple<T, U, V>, *> by lazy {
            From<Triple<T, U, V>, Any>(jdbcConnection, properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSelect.From<Triple<T, U, V>, W> =
            addFromTable(table, from as From<Triple<T, U, V>, W>)

        override fun <W : Any> and(column: Column<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun andSum(column: IntColumn<*>): SqlClientSelect.Select =
            Select(jdbcConnection, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSelect.Select = Select(jdbcConnection, properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }
    }

    private class Select(
        jdbcConnection: JdbcConnection,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.Select {
        private val from: From<List<Any?>, *> = From<List<Any?>, Any>(jdbcConnection, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<List<Any?>, U> =
            addFromTable(table, from as From<List<Any?>, U>)

        override fun <V : Any> and(column: Column<*, V>): SqlClientSelect.Select =
            this.apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): SqlClientSelect.Select = this.apply { addSelectTable(table) }
        override fun <V : Any> andCount(column: Column<*, V>): SqlClientSelect.Select =
            this.apply { addCountColumn(column) }

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
        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.Select = this.apply { addSelectSubQuery(dsl) }
    }

    private class SelectWithDsl<T : Any>(
        jdbcConnection: JdbcConnection,
        properties: Properties<T>,
        dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), SqlClientSelect.Fromable<T> {
        private val from: From<T, *> = From<T, Any>(jdbcConnection, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.From<T, U> =
            addFromTable(table, from as From<T, U>)
    }

    private class From<T : Any, U : Any>(
        override val jdbcConnection: JdbcConnection,
        properties: Properties<T>,
    ) : FromWhereable<T, U, SqlClientSelect.From<T, U>, SqlClientSelect.Where<T>,
            SqlClientSelect.LimitOffset<T>, SqlClientSelect.GroupByPart2<T>,
            SqlClientSelect.OrderByPart2<T>>(properties), SqlClientSelect.From<T, U>, GroupBy<T>, OrderBy<T>,
        SqlClientSelect.LimitOffset<T> {
        override val from = this
        override val where by lazy { Where(jdbcConnection, properties) }
        override val limitOffset by lazy { LimitOffset(jdbcConnection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(jdbcConnection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(jdbcConnection, properties) }
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.From<T, V> =
            addFromTable(table, from as From<T, V>)
    }

    private class Where<T : Any>(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupByPart2<T>, SqlClientSelect.OrderByPart2<T>>(), SqlClientSelect.Where<T>,
        GroupBy<T>, OrderBy<T>, SqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(jdbcConnection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(jdbcConnection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(jdbcConnection, properties) }
    }

    private interface GroupBy<T : Any> : DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
        SqlClientSelect.GroupBy<T>, Return<T>

    private class GroupByPart2<T : Any>(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByPart2<T, SqlClientSelect.GroupByPart2<T>>, SqlClientSelect.GroupByPart2<T>,
        DefaultSqlClientSelect.OrderBy<T, SqlClientSelect.OrderByPart2<T>>,
        DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(jdbcConnection, properties) }
        override val orderByPart2 by lazy { OrderByPart2(jdbcConnection, properties) }
        override val groupByPart2 = this
    }

    private interface OrderBy<T : Any> : DefaultSqlClientSelect.OrderBy<T, SqlClientSelect.OrderByPart2<T>>,
        SqlClientSelect.OrderBy<T>, Return<T>

    private class OrderByPart2<T : Any>(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByPart2<T, SqlClientSelect.OrderByPart2<T>>, SqlClientSelect.OrderByPart2<T>,
        DefaultSqlClientSelect.GroupBy<T, SqlClientSelect.GroupByPart2<T>>,
        DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(jdbcConnection, properties) }
        override val groupByPart2 by lazy { GroupByPart2(jdbcConnection, properties) }
        override val orderByPart2 = this
    }

    private class LimitOffset<T : Any>(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, SqlClientSelect.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val jdbcConnection: JdbcConnection

        override fun fetchOne() = fetchOneOrNull() ?: throw NoResultException()

        override fun fetchOneOrNull(): T? = jdbcConnection.execute { connection ->
            val rs = executeQuery(connection)
            if (!rs.next()) {
                return null
            }
            val result = properties.select(rs.toRow())
            if (rs.next()) {
                throw NonUniqueResultException()
            }
            return result
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

        private fun fetchAllNullable(): List<T?> = jdbcConnection.execute { connection ->
            val rs = executeQuery(connection)
            val row = rs.toRow()
            val results = arrayListOf<T?>()
            while (rs.next()) {
                results.add(properties.select(row))
                row.resetIndex()
            }
            return results
        }

        private fun executeQuery(connection: Connection): ResultSet {
            val statement = connection.prepareStatement(selectSql())
            buildParameters(statement)
            return statement.executeQuery()
        }

        private fun buildParameters(statement: PreparedStatement) {
            with(properties) {
                // 1) add all values from where part
                jdbcBindWhereParams(statement)
                // 2) add limit and offset (order is different depending on DbType)
                if (DbType.MSSQL == tables.dbType) {
                    offsetParam(statement)
                    limitParam(statement)
                } else {
                    limitParam(statement)
                    offsetParam(statement)
                }
            }
        }

        private fun offsetParam(statement: PreparedStatement) {
            with(properties) {
                if (offset != null) {
                    statement.setObject(++index, offset)
                }
            }
        }

        private fun limitParam(statement: PreparedStatement) {
            with(properties) {
                if (limit != null) {
                    statement.setObject(++index, limit)
                }
            }
        }
    }
}
