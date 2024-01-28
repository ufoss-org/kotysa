/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.core.jdbc.toRow
import org.ufoss.kotysa.postgresql.Tsquery
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

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SqlClientSelect.FirstSelect<Long> =
                FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.SelectCaseWhenExistsFirst<T> = SelectCaseWhenExistsFirst(client, properties(), dsl)

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.From<T> = FirstSelect<T>(client, properties()).selectStarFrom(dsl)

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): SqlClientSelect.FirstSelect<Float> =
            FirstSelect<Float>(client, properties()).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun selects(): SqlClientSelect.Selects = Selects(client, properties())
    }

    private class SelectCaseWhenExistsFirst<T : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSelect.SelectCaseWhenExistsFirst<T> {
        override fun <U : Any> then(value: U): SqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> =
            SelectCaseWhenExistsFirstPart2(client, properties as Properties<U>, dsl, value)
    }

    private class SelectCaseWhenExistsFirstPart2<T : Any, U : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<U>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> {
        override fun `else`(value: U): SqlClientSelect.FirstSelect<U> =
            FirstSelect(client, properties).apply { addSelectCaseWhenExistsSubQuery(dsl, then, value) }
    }

    private class FirstSelect<T : Any>(
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), SqlClientSelect.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(client, properties)
        }
        private val froms: Froms<T, *> by lazy {
            Froms<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<T, U> =
                addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<T> = addFroms(froms)
        
        fun <U : Any> selectStarFrom(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>, true)

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

        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): SqlClientSelect.SecondSelect<T?, Long> =
                SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.AndCaseWhenExistsSecond<T, U> = AndCaseWhenExistsSecond(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): SqlClientSelect.SecondSelect<T?, Float> =
            SecondSelect(client, properties as Properties<Pair<T?, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSelect.FirstSelect<T> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsSecond<T : Any, U : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
    ) : SqlClientSelect.AndCaseWhenExistsSecond<T, U> {
        override fun <V : Any> then(value: V): SqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> =
            AndCaseWhenExistsSecondPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        private val then: V,
    ) : SqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> {
        override fun `else`(value: V): SqlClientSelect.SecondSelect<T?, V> =
            SecondSelect(client, properties as Properties<Pair<T?, V>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SecondSelect<T, U>(
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSelect.SecondSelect<T, U> {
        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(client, properties)
        }
        private val froms: Froms<Pair<T, U>, *> by lazy {
            Froms<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.FromTable<Pair<T, U>, V> =
                addFromTable(table, from as FromTable<Pair<T, U>, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.From<Pair<T, U>> = addFromSubQuery(dsl, from as FromTable<Pair<T, U>, V>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<Pair<T, U>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<Pair<T, U>> = addFroms(froms)

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

        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): SqlClientSelect.ThirdSelect<T, U, Long> =
                ThirdSelect(client, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <V : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.AndCaseWhenExistsThird<T, U, V> = AndCaseWhenExistsThird(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): SqlClientSelect.ThirdSelect<T, U, Float> =
            ThirdSelect(client, properties as Properties<Triple<T, U, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSelect.SecondSelect<T, U> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsThird<T, U, V : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
    ) : SqlClientSelect.AndCaseWhenExistsThird<T, U, V> {
        override fun <W : Any> then(value: W): SqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> =
            AndCaseWhenExistsThirdPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>,
        private val then: W,
    ) : SqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> {
        override fun `else`(value: W): SqlClientSelect.ThirdSelect<T, U, W> =
            ThirdSelect(client, properties as Properties<Triple<T, U, W>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class ThirdSelect<T, U, V>(
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSelect.ThirdSelect<T, U, V> {
        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(client, properties)
        }
        private val froms: Froms<Triple<T, U, V>, *> by lazy {
            Froms<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSelect.FromTable<Triple<T, U, V>, W> =
                addFromTable(table, from as FromTable<Triple<T, U, V>, W>)

        override fun <W : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSelect.From<Triple<T, U, V>> =
            addFromSubQuery(dsl, from as FromTable<Triple<T, U, V>, W>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<Triple<T, U, V>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<Triple<T, U, V>> = addFroms(froms)

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

        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): SqlClientSelect.Select =
                Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSelect.Select = Select(client, properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }

        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSelect.AndCaseWhenExistsLast<W> =
            AndCaseWhenExistsLast(client, properties as Properties<List<Any?>>, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSelect.ThirdSelect<T, U, V> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSelect.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): SqlClientSelect.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSelect.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): SqlClientSelect.Select =
            Select(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(
            private val client: NamedParameterJdbcOperations,
            override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.Select {
        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<List<Any?>, U> =
                addFromTable(table, from as FromTable<List<Any?>, U>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<List<Any?>> = addFroms(froms)

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

        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): SqlClientSelect.Select =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.AndCaseWhenExistsLast<T> = AndCaseWhenExistsLast(client, properties, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SqlClientSelect.Select =
            Select(client, properties).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSelect.Select = this.apply { aliasLastColumn(alias) }
    }

    private class Selects(
        private val client: NamedParameterJdbcOperations,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSelect.SelectsPart2 {
        init {
            properties.isConditionalSelect = true
        }

        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <T : Any> from(table: Table<T>): SqlClientSelect.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> select(column: Column<*, T>): SqlClientSelect.SelectsPart2 =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): SqlClientSelect.SelectsPart2 =
            this.apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSelect.SelectsPart2 =
            this.apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSelect.SelectsPart2 = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SqlClientSelect.SelectsPart2 =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.SelectsPart2 = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSelect.SelectsCaseWhenExists<T> = SelectsCaseWhenExists(client, properties, dsl)

        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SqlClientSelect.SelectsPart2 =
            this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSelect.SelectsPart2 = this.apply { aliasLastColumn(alias) }
    }

    private class SelectsCaseWhenExists<T : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSelect.SelectsCaseWhenExists<T> {
        override fun <U : Any> then(value: U): SqlClientSelect.SelectsCaseWhenExistsPart2<T, U> =
            SelectsCaseWhenExistsPart2(client, properties, dsl, value)
    }

    private class SelectsCaseWhenExistsPart2<T : Any, U : Any>(
        private val client: NamedParameterJdbcOperations,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSelect.SelectsCaseWhenExistsPart2<T, U> {
        override fun `else`(value: U): SqlClientSelect.SelectsPart2 =
            Selects(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SelectWithDsl<T : Any>(
            client: NamedParameterJdbcOperations,
            properties: Properties<T>,
            dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), SqlClientSelect.Fromable<T> {
        // todo lazy
        private val from: FromTable<T, *> = FromTable<T, Any>(client, properties)
        private val froms: Froms<T, *> = Froms<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): SqlClientSelect.FromTable<T, U> =
                addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): SqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSelect.Froms<T> = addFroms(froms)

        override fun `as`(alias: String): Nothing {
            throw IllegalArgumentException("No Alias for selectAndBuild")
        }
    }

    private class FromTable<T : Any, U : Any>(
            override val client: NamedParameterJdbcOperations,
            properties: Properties<T>,
    ) : FromWhereable<T, U, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>, SqlClientSelect.GroupBy<T>,
            SqlClientSelect.OrderBy<T>>(properties), SqlClientSelect.FromTable<T, U>, SqlClientSelect.From<T>,
        GroupableBy<T>, OrderableBy<T>, SqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy
        
        override fun <V : Any> and(table: Table<V>): SqlClientSelect.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.From<T> = addFromSubQuery(dsl, fromTable as FromTable<T, V>)

        override fun and(tsquery: Tsquery): SqlClientSelect.From<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): SqlClientSelect.FromTable<T, U> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): SqlClientSelect.Wheres<T> = wheres
        
        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Froms<T : Any, U : Any>(
        override val client: NamedParameterJdbcOperations,
        properties: Properties<T>,
    ) : FromWhereable<T, U, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupBy<T>, SqlClientSelect.OrderBy<T>>(properties),
        SqlClientSelect.FromsTable<T, U>, GroupableBy<T>, OrderableBy<T>, SqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> from(table: Table<V>): SqlClientSelect.FromsTable<T, V> =
            addFromTable(table, fromTable as Froms<T, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSelect.FromsPart2<T> = addFromSubQuery(dsl, fromTable as Froms<T, V>)

        override fun from(tsquery: Tsquery): SqlClientSelect.FromsPart2<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): SqlClientSelect.Froms<T> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): SqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromsTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, SqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Where<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupBy<T>, SqlClientSelect.OrderBy<T>>(), SqlClientSelect.Where<T>,
        GroupableBy<T>, OrderableBy<T>, SqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy
    }

    private class Wheres<T : Any>(
        override val client: NamedParameterJdbcOperations,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Wheres<T>, SqlClientSelect.LimitOffset<T>,
            SqlClientSelect.GroupBy<T>, SqlClientSelect.OrderBy<T>>(), SqlClientSelect.Wheres<T>,
        GroupableBy<T>, OrderableBy<T>, SqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy
    }

    private interface GroupableBy<T : Any> : DefaultSqlClientSelect.GroupableBy<T, SqlClientSelect.GroupBy<T>>,
        SqlClientSelect.GroupableBy<T>, Return<T>

    private class GroupByAndable<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByAndable<T, SqlClientSelect.GroupBy<T>>,
        SqlClientSelect.GroupBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, SqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
    }

    private class GroupsBy<T : Any>(
        override val client: NamedParameterJdbcOperations,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupableBy<T, SqlClientSelect.GroupsBy<T>>,
        SqlClientSelect.GroupsBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, SqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): SqlClientSelect.OrdersBy<T> = ordersBy
    }

    private interface OrderableBy<T : Any> : DefaultSqlClientSelect.OrderableBy<T, SqlClientSelect.OrderBy<T>>,
        SqlClientSelect.OrderableBy<T>, Return<T> {

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.DESC)
    }

    private class OrdersBy<T : Any>(
        override val client: NamedParameterJdbcOperations,
        override val properties: Properties<T>
    ): DefaultSqlClientSelect.OrderableBy<T, SqlClientSelect.OrdersBy<T>>, SqlClientSelect.OrdersBy<T>, GroupableBy<T>,
        DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy = this
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrdersBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrdersBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.DESC)
    }

    private class OrderByCaseWhenExists<T : Any, U : Any, V : OrderBy<V>>(
        override val properties: Properties<T>,
        override val orderByPart2: V,
        override val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        override val order: Order
    ) : DefaultSqlClientSelect.OrderByCaseWhenExists<T, U, V> {
        override fun <W : Any> then(value: W): SqlClientQuery.OrderByCaseWhenExistsPart2<U, W, V> {
            return OrderByCaseWhenExistsPart2(properties, orderByPart2, dsl, value, order)
        }
    }

    private class OrderByCaseWhenExistsPart2<T : Any, U : Any, V : Any, W : OrderBy<W>>(
        override val properties: Properties<T>,
        override val orderByPart2: W,
        override val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        override val then: V,
        override val order: Order
    ) : DefaultSqlClientSelect.OrderByCaseWhenExistsPart2<T, U, V, W>

    private class OrderByAndable<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByAndable<T, SqlClientSelect.OrderBy<T>>, SqlClientSelect.OrderBy<T>,
            DefaultSqlClientSelect.GroupableBy<T, SqlClientSelect.GroupBy<T>>,
            DefaultSqlClientSelect.LimitOffset<T, SqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderByPart2 = this
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): SqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> andAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.ASC)

        override fun <U : Any> andDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, SqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.DESC)
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
            springJdbcBindParams(parameters)
            // 2) add limit and offset (order is different depending on DbType)
            if (DbType.MSSQL == tables.dbType || DbType.ORACLE == tables.dbType) {
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
