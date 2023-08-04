/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal


@Suppress("UNCHECKED_CAST")
internal class ReactorSqlClientSelectSpringR2dbc private constructor() : AbstractSqlClientSelectSpringR2dbc() {

    internal class Selectable internal constructor(
        private val client: DatabaseClient,
        private val tables: Tables,
    ) : ReactorSqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.R2DBC, Module.SPRING_R2DBC)

        override fun <T : Any> select(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T> =
            SelectWithDsl(client, properties(), dsl)

        override fun <T : Any> selectCount(column: Column<*, T>?): ReactorSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(client, properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): ReactorSqlClientSelect.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(client, properties()).apply { addAvgColumn(column) }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): ReactorSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.FirstSelect<T> = FirstSelect<T>(client, properties()).apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.SelectCaseWhenExistsFirst<T> = SelectCaseWhenExistsFirst(client, properties(), dsl)

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.From<T> = FirstSelect<T>(client, properties()).selectStarFrom(dsl)

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): ReactorSqlClientSelect.FirstSelect<Float> =
            FirstSelect<Float>(client, properties()).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun selects(): ReactorSqlClientSelect.Selects = Selects(client, properties())
    }

    private class SelectCaseWhenExistsFirst<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : ReactorSqlClientSelect.SelectCaseWhenExistsFirst<T> {
        override fun <U : Any> then(value: U): ReactorSqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> =
            SelectCaseWhenExistsFirstPart2(client, properties as Properties<U>, dsl, value)
    }

    private class SelectCaseWhenExistsFirstPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<U>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : ReactorSqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> {
        override fun `else`(value: U): ReactorSqlClientSelect.FirstSelect<U> =
            FirstSelect(client, properties).apply { addSelectCaseWhenExistsSubQuery(dsl, then, value) }
    }

    private class FirstSelect<T : Any>(
        private val client: DatabaseClient,
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), ReactorSqlClientSelect.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(client, properties)
        }
        private val froms: Froms<T, *> by lazy {
            Froms<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): ReactorSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<T> = addFroms(froms)

        fun <U : Any> selectStarFrom(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): ReactorSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>, true)

        override fun <U : Any> and(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): ReactorSqlClientSelect.SecondSelect<T, U> =
            SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T, Long> =
            SecondSelect(client, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, BigDecimal> =
            SecondSelect(
                client,
                properties as Properties<Pair<T?, BigDecimal>>
            ).apply { addAvgColumn(column) }

        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): ReactorSqlClientSelect.SecondSelect<T?, Long> =
            SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): ReactorSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): ReactorSqlClientSelect.AndCaseWhenExistsSecond<T, U> =
            AndCaseWhenExistsSecond(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): ReactorSqlClientSelect.SecondSelect<T?, Float> =
            SecondSelect(client, properties as Properties<Pair<T?, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): ReactorSqlClientSelect.FirstSelect<T> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsSecond<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
    ) : ReactorSqlClientSelect.AndCaseWhenExistsSecond<T, U> {
        override fun <V : Any> then(value: V): ReactorSqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> =
            AndCaseWhenExistsSecondPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        private val then: V,
    ) : ReactorSqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> {
        override fun `else`(value: V): ReactorSqlClientSelect.SecondSelect<T?, V> =
            SecondSelect(client, properties as Properties<Pair<T?, V>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SecondSelect<T, U>(
        private val client: DatabaseClient,
        override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), ReactorSqlClientSelect.SecondSelect<T, U> {
        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(client, properties)
        }
        private val froms: Froms<Pair<T, U>, *> by lazy {
            Froms<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): ReactorSqlClientSelect.FromTable<Pair<T, U>, V> =
            addFromTable(table, from as FromTable<Pair<T, U>, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): ReactorSqlClientSelect.From<Pair<T, U>> = addFromSubQuery(dsl, from as FromTable<Pair<T, U>, V>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<Pair<T, U>> = addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<Pair<T, U>> = addFroms(froms)

        override fun <V : Any> and(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.ThirdSelect<T, U, V> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(
                client,
                properties as Properties<Triple<T, U, Long>>
            ).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(client, properties as Properties<Triple<T, U, BigDecimal>>).apply {
                addAvgColumn(
                    column
                )
            }

        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): ReactorSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(
                client,
                properties as Properties<Triple<T, U, Long>>
            ).apply { addLongSumColumn(column) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): ReactorSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <V : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): ReactorSqlClientSelect.AndCaseWhenExistsThird<T, U, V> =
            AndCaseWhenExistsThird(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): ReactorSqlClientSelect.ThirdSelect<T, U, Float> =
            ThirdSelect(client, properties as Properties<Triple<T, U, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): ReactorSqlClientSelect.SecondSelect<T, U> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsThird<T, U, V : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
    ) : ReactorSqlClientSelect.AndCaseWhenExistsThird<T, U, V> {
        override fun <W : Any> then(value: W): ReactorSqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> =
            AndCaseWhenExistsThirdPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>,
        private val then: W,
    ) : ReactorSqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> {
        override fun `else`(value: W): ReactorSqlClientSelect.ThirdSelect<T, U, W> =
            ThirdSelect(client, properties as Properties<Triple<T, U, W>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class ThirdSelect<T, U, V>(
        private val client: DatabaseClient,
        override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), ReactorSqlClientSelect.ThirdSelect<T, U, V> {
        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(client, properties)
        }
        private val froms: Froms<Triple<T, U, V>, *> by lazy {
            Froms<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): ReactorSqlClientSelect.FromTable<Triple<T, U, V>, W> =
            addFromTable(table, from as FromTable<Triple<T, U, V>, W>)

        override fun <W : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): ReactorSqlClientSelect.From<Triple<T, U, V>> =
            addFromSubQuery(dsl, from as FromTable<Triple<T, U, V>, W>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<Triple<T, U, V>> =
            addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<Triple<T, U, V>> = addFroms(froms)

        override fun <W : Any> and(column: Column<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): ReactorSqlClientSelect.Select = Select(client, properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }

        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): ReactorSqlClientSelect.AndCaseWhenExistsLast<W> =
            AndCaseWhenExistsLast(client, properties as Properties<List<Any?>>, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery)
                : ReactorSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): ReactorSqlClientSelect.ThirdSelect<T, U, V> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : ReactorSqlClientSelect.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): ReactorSqlClientSelect.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : ReactorSqlClientSelect.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): ReactorSqlClientSelect.Select =
            Select(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(
        private val client: DatabaseClient,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), ReactorSqlClientSelect.Select {
        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.FromTable<List<Any?>, U> =
            addFromTable(table, from as FromTable<List<Any?>, U>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <V : Any> and(column: Column<*, V>): ReactorSqlClientSelect.Select =
            this.apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.Select =
            this.apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): ReactorSqlClientSelect.Select =
            this.apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): ReactorSqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): ReactorSqlClientSelect.Select =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.AndCaseWhenExistsLast<T> =
            AndCaseWhenExistsLast(client, properties, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery)
                : ReactorSqlClientSelect.Select =
            Select(client, properties).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): ReactorSqlClientSelect.Select = this.apply { aliasLastColumn(alias) }
    }

    private class Selects(
        private val client: DatabaseClient,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), ReactorSqlClientSelect.SelectsPart2 {
        init {
            properties.isConditionalSelect = true
        }

        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <T : Any> from(table: Table<T>): ReactorSqlClientSelect.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> select(column: Column<*, T>): ReactorSqlClientSelect.SelectsPart2 =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.SelectsPart2 =
            this.apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): ReactorSqlClientSelect.SelectsPart2 =
            this.apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): ReactorSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): ReactorSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): ReactorSqlClientSelect.SelectsPart2 = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): ReactorSqlClientSelect.SelectsPart2 =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.SelectsPart2 = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): ReactorSqlClientSelect.SelectsCaseWhenExists<T> = SelectsCaseWhenExists(client, properties, dsl)

        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): ReactorSqlClientSelect.SelectsPart2 =
            this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): ReactorSqlClientSelect.SelectsPart2 = this.apply { aliasLastColumn(alias) }
    }

    private class SelectsCaseWhenExists<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : ReactorSqlClientSelect.SelectsCaseWhenExists<T> {
        override fun <U : Any> then(value: U): ReactorSqlClientSelect.SelectsCaseWhenExistsPart2<T, U> =
            SelectsCaseWhenExistsPart2(client, properties, dsl, value)
    }

    private class SelectsCaseWhenExistsPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : ReactorSqlClientSelect.SelectsCaseWhenExistsPart2<T, U> {
        override fun `else`(value: U): ReactorSqlClientSelect.SelectsPart2 =
            Selects(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SelectWithDsl<T : Any>(
        client: DatabaseClient,
        properties: Properties<T>,
        dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), ReactorSqlClientSelect.Fromable<T> {
        // todo lazy
        private val from: FromTable<T, *> = FromTable<T, Any>(client, properties)
        private val froms: Froms<T, *> = Froms<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): ReactorSqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): ReactorSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): ReactorSqlClientSelect.Froms<T> = addFroms(froms)

        override fun `as`(alias: String): Nothing {
            throw IllegalArgumentException("No Alias for selectAndBuild")
        }
    }

    private class FromTable<T : Any, U : Any>(
        override val client: DatabaseClient,
        properties: Properties<T>,
    ) : FromWhereable<T, U, ReactorSqlClientSelect.Where<T>,
            ReactorSqlClientSelect.LimitOffset<T>, ReactorSqlClientSelect.GroupBy<T>,
            ReactorSqlClientSelect.OrderBy<T>>(properties), ReactorSqlClientSelect.FromTable<T, U>,
        ReactorSqlClientSelect.From<T>, GroupableBy<T>, OrderableBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> and(table: Table<V>): ReactorSqlClientSelect.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): ReactorSqlClientSelect.From<T> = addFromSubQuery(dsl, fromTable as FromTable<T, V>)

        override fun and(tsquery: Tsquery): ReactorSqlClientSelect.From<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): ReactorSqlClientSelect.FromTable<T, U> =
            fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): ReactorSqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Froms<T : Any, U : Any>(
        override val client: DatabaseClient,
        properties: Properties<T>,
    ) : FromWhereable<T, U, ReactorSqlClientSelect.Where<T>, ReactorSqlClientSelect.LimitOffset<T>,
            ReactorSqlClientSelect.GroupBy<T>, ReactorSqlClientSelect.OrderBy<T>>(properties),
        ReactorSqlClientSelect.FromsTable<T, U>, GroupableBy<T>, OrderableBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> from(table: Table<V>): ReactorSqlClientSelect.FromsTable<T, V> =
            addFromTable(table, fromTable as Froms<T, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): ReactorSqlClientSelect.FromsPart2<T> = addFromSubQuery(dsl, fromTable as Froms<T, V>)

        override fun from(tsquery: Tsquery): ReactorSqlClientSelect.FromsPart2<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): ReactorSqlClientSelect.Froms<T> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): ReactorSqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromsTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, ReactorSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Where<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, ReactorSqlClientSelect.Where<T>, ReactorSqlClientSelect.LimitOffset<T>,
            ReactorSqlClientSelect.GroupBy<T>, ReactorSqlClientSelect.OrderBy<T>>(),
        ReactorSqlClientSelect.Where<T>, GroupableBy<T>, OrderableBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy
    }

    private class Wheres<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, ReactorSqlClientSelect.Wheres<T>, ReactorSqlClientSelect.LimitOffset<T>,
            ReactorSqlClientSelect.GroupBy<T>, ReactorSqlClientSelect.OrderBy<T>>(),
        ReactorSqlClientSelect.Wheres<T>, GroupableBy<T>, OrderableBy<T>, ReactorSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy
    }

    private interface GroupableBy<T : Any> : DefaultSqlClientSelect.GroupableBy<T,
            ReactorSqlClientSelect.GroupBy<T>>, ReactorSqlClientSelect.GroupableBy<T>,
        Return<T>

    private class GroupByAndable<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByAndable<T, ReactorSqlClientSelect.GroupBy<T>>,
        ReactorSqlClientSelect.GroupBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, ReactorSqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
        Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
    }

    private class GroupsBy<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupableBy<T, ReactorSqlClientSelect.GroupsBy<T>>,
        ReactorSqlClientSelect.GroupsBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, ReactorSqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
        Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): ReactorSqlClientSelect.OrdersBy<T> = ordersBy
    }

    private interface OrderableBy<T : Any> : DefaultSqlClientSelect.OrderableBy<T,
            ReactorSqlClientSelect.OrderBy<T>>, ReactorSqlClientSelect.OrderableBy<T>,
        Return<T> {

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.DESC)
    }

    private class OrdersBy<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ): DefaultSqlClientSelect.OrderableBy<T, ReactorSqlClientSelect.OrdersBy<T>>,
        ReactorSqlClientSelect.OrdersBy<T>, GroupableBy<T>, DefaultSqlClientSelect.LimitOffset<T,
                ReactorSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy = this
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrdersBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrdersBy<T>> =
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
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByAndable<T, ReactorSqlClientSelect.OrderBy<T>>,
        ReactorSqlClientSelect.OrderBy<T>,
        DefaultSqlClientSelect.GroupableBy<T, ReactorSqlClientSelect.GroupBy<T>>,
        DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
        Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderByPart2 = this
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): ReactorSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> andAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.ASC)

        override fun <U : Any> andDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, ReactorSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.DESC)
    }

    private class LimitOffset<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, ReactorSqlClientSelect.LimitOffset<T>>,
        ReactorSqlClientSelect.LimitOffset<T>, Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : AbstractSqlClientSelectSpringR2dbc.Return<T>, ReactorSqlClientSelect.Return<T> {

        override fun fetchOne(): Mono<T> =
            fetch().one()
                .handle { opt, sink ->
                    opt.ifPresent(sink::next)
                }
                .onErrorMap(IncorrectResultSizeDataAccessException::class.java) { NonUniqueResultException() }

        override fun fetchFirst(): Mono<T> =
            fetch().first()
                .handle { opt, sink ->
                    opt.ifPresent(sink::next)
                }

        override fun fetchAll(): Flux<T> =
            fetch().all()
                .handle { opt, sink ->
                    opt.ifPresent(sink::next)
                }
    }
}
