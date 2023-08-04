/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.r2dbc.core.*
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal


@Suppress("UNCHECKED_CAST")
internal class CoroutinesSqlClientSelectSpringR2Dbc private constructor() : AbstractSqlClientSelectSpringR2dbc() {

    internal class Selectable internal constructor(
        private val client: DatabaseClient,
        private val tables: Tables,
    ) : CoroutinesSqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.R2DBC, Module.SPRING_R2DBC)

        override fun <T : Any> select(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
            SelectWithDsl(client, properties(), dsl)

        override fun <T : Any> selectCount(column: Column<*, T>?): CoroutinesSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(client, properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(client, properties()).apply { addAvgColumn(column) }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(client, properties()).apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(client, properties()).apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> =
            SelectCaseWhenExistsFirst(client, properties(), dsl)

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.From<T> = FirstSelect<T>(client, properties()).selectStarFrom(dsl)

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): CoroutinesSqlClientSelect.FirstSelect<Float> =
            FirstSelect<Float>(client, properties()).apply { addTsRankCd(tsvectorColumn, tsquery) }
        override fun selects(): CoroutinesSqlClientSelect.Selects = Selects(client, properties())
    }

    private class SelectCaseWhenExistsFirst<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> {
        override fun <U : Any> then(value: U): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> =
            SelectCaseWhenExistsFirstPart2(client, properties as Properties<U>, dsl, value)
    }

    private class SelectCaseWhenExistsFirstPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<U>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : CoroutinesSqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> {
        override fun `else`(value: U): CoroutinesSqlClientSelect.FirstSelect<U> =
            FirstSelect(client, properties).apply { addSelectCaseWhenExistsSubQuery(dsl, then, value) }
    }

    private class FirstSelect<T : Any>(
        private val client: DatabaseClient,
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), CoroutinesSqlClientSelect.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(client, properties)
        }
        private val froms: Froms<T, *> by lazy {
            Froms<T, Any>(client, properties)
        }

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): CoroutinesSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<T> = addFroms(froms)

        fun <U : Any> selectStarFrom(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): CoroutinesSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>, true)

        override fun <U : Any> and(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): CoroutinesSqlClientSelect.SecondSelect<T, U> =
            SecondSelect(client, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T, Long> =
            SecondSelect(client, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, BigDecimal> =
            SecondSelect(
                client,
                properties as Properties<Pair<T?, BigDecimal>>
            ).apply { addAvgColumn(column) }

        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): CoroutinesSqlClientSelect.SecondSelect<T?, Long> =
            SecondSelect(client, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): CoroutinesSqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(client, properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): CoroutinesSqlClientSelect.AndCaseWhenExistsSecond<T, U> =
            AndCaseWhenExistsSecond(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): CoroutinesSqlClientSelect.SecondSelect<T?, Float> =
            SecondSelect(client, properties as Properties<Pair<T?, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): CoroutinesSqlClientSelect.FirstSelect<T> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsSecond<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsSecond<T, U> {
        override fun <V : Any> then(value: V): CoroutinesSqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> =
            AndCaseWhenExistsSecondPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        private val then: V,
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> {
        override fun `else`(value: V): CoroutinesSqlClientSelect.SecondSelect<T?, V> =
            SecondSelect(client, properties as Properties<Pair<T?, V>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SecondSelect<T, U>(
        private val client: DatabaseClient,
        override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), CoroutinesSqlClientSelect.SecondSelect<T, U> {
        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(client, properties)
        }
        private val froms: Froms<Pair<T, U>, *> by lazy {
            Froms<Pair<T, U>, Any>(client, properties)
        }

        override fun <V : Any> from(table: Table<V>): CoroutinesSqlClientSelect.FromTable<Pair<T, U>, V> =
            addFromTable(table, from as FromTable<Pair<T, U>, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): CoroutinesSqlClientSelect.From<Pair<T, U>> = addFromSubQuery(dsl, from as FromTable<Pair<T, U>, V>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<Pair<T, U>> = addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<Pair<T, U>> = addFroms(froms)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(
                client,
                properties as Properties<Triple<T, U, Long>>
            ).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(client, properties as Properties<Triple<T, U, BigDecimal>>).apply {
                addAvgColumn(
                    column
                )
            }

        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): CoroutinesSqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(
                client,
                properties as Properties<Triple<T, U, Long>>
            ).apply { addLongSumColumn(column) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): CoroutinesSqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(client, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <V : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): CoroutinesSqlClientSelect.AndCaseWhenExistsThird<T, U, V> =
            AndCaseWhenExistsThird(client, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): CoroutinesSqlClientSelect.ThirdSelect<T, U, Float> =
            ThirdSelect(client, properties as Properties<Triple<T, U, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): CoroutinesSqlClientSelect.SecondSelect<T, U> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsThird<T, U, V : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsThird<T, U, V> {
        override fun <W : Any> then(value: W): CoroutinesSqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> =
            AndCaseWhenExistsThirdPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>,
        private val then: W,
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> {
        override fun `else`(value: W): CoroutinesSqlClientSelect.ThirdSelect<T, U, W> =
            ThirdSelect(client, properties as Properties<Triple<T, U, W>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class ThirdSelect<T, U, V>(
        private val client: DatabaseClient,
        override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), CoroutinesSqlClientSelect.ThirdSelect<T, U, V> {
        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(client, properties)
        }
        private val froms: Froms<Triple<T, U, V>, *> by lazy {
            Froms<Triple<T, U, V>, Any>(client, properties)
        }

        override fun <W : Any> from(table: Table<W>): CoroutinesSqlClientSelect.FromTable<Triple<T, U, V>, W> =
            addFromTable(table, from as FromTable<Triple<T, U, V>, W>)

        override fun <W : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): CoroutinesSqlClientSelect.From<Triple<T, U, V>> =
            addFromSubQuery(dsl, from as FromTable<Triple<T, U, V>, W>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<Triple<T, U, V>> =
            addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<Triple<T, U, V>> = addFroms(froms)

        override fun <W : Any> and(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): CoroutinesSqlClientSelect.Select = Select(client, properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }

        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): CoroutinesSqlClientSelect.AndCaseWhenExistsLast<W> =
            AndCaseWhenExistsLast(client, properties as Properties<List<Any?>>, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery)
                : CoroutinesSqlClientSelect.Select =
            Select(client, properties as Properties<List<Any?>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): CoroutinesSqlClientSelect.ThirdSelect<T, U, V> =
            this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): CoroutinesSqlClientSelect.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(client, properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : CoroutinesSqlClientSelect.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): CoroutinesSqlClientSelect.Select =
            Select(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(
        private val client: DatabaseClient,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), CoroutinesSqlClientSelect.Select {
        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.FromTable<List<Any?>, U> =
            addFromTable(table, from as FromTable<List<Any?>, U>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <V : Any> and(column: Column<*, V>): CoroutinesSqlClientSelect.Select =
            this.apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.Select =
            this.apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): CoroutinesSqlClientSelect.Select =
            this.apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): CoroutinesSqlClientSelect.Select =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.AndCaseWhenExistsLast<T> =
            AndCaseWhenExistsLast(client, properties, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery)
                : CoroutinesSqlClientSelect.Select =
            Select(client, properties).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): CoroutinesSqlClientSelect.Select = this.apply { aliasLastColumn(alias) }
    }

    private class Selects(
        private val client: DatabaseClient,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), CoroutinesSqlClientSelect.SelectsPart2 {
        init {
            properties.isConditionalSelect = true
        }

        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(client, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(client, properties)

        override fun <T : Any> from(table: Table<T>): CoroutinesSqlClientSelect.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> select(column: Column<*, T>): CoroutinesSqlClientSelect.SelectsPart2 =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.SelectsPart2 =
            this.apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): CoroutinesSqlClientSelect.SelectsPart2 =
            this.apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): CoroutinesSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): CoroutinesSqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): CoroutinesSqlClientSelect.SelectsPart2 = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): CoroutinesSqlClientSelect.SelectsPart2 =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.SelectsPart2 = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): CoroutinesSqlClientSelect.SelectsCaseWhenExists<T> = SelectsCaseWhenExists(client, properties, dsl)

        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): CoroutinesSqlClientSelect.SelectsPart2 =
            this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): CoroutinesSqlClientSelect.SelectsPart2 = this.apply { aliasLastColumn(alias) }
    }

    private class SelectsCaseWhenExists<T : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : CoroutinesSqlClientSelect.SelectsCaseWhenExists<T> {
        override fun <U : Any> then(value: U): CoroutinesSqlClientSelect.SelectsCaseWhenExistsPart2<T, U> =
            SelectsCaseWhenExistsPart2(client, properties, dsl, value)
    }

    private class SelectsCaseWhenExistsPart2<T : Any, U : Any>(
        private val client: DatabaseClient,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : CoroutinesSqlClientSelect.SelectsCaseWhenExistsPart2<T, U> {
        override fun `else`(value: U): CoroutinesSqlClientSelect.SelectsPart2 =
            Selects(client, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SelectWithDsl<T : Any>(
        client: DatabaseClient,
        properties: Properties<T>,
        dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), CoroutinesSqlClientSelect.Fromable<T> {
        // todo lazy
        private val from: FromTable<T, *> = FromTable<T, Any>(client, properties)
        private val froms: Froms<T, *> = Froms<T, Any>(client, properties)

        override fun <U : Any> from(table: Table<U>): CoroutinesSqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): CoroutinesSqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): CoroutinesSqlClientSelect.Froms<T> = addFroms(froms)

        override fun `as`(alias: String): Nothing {
            throw IllegalArgumentException("No Alias for selectAndBuild")
        }
    }

    private class FromTable<T : Any, U : Any>(
        override val client: DatabaseClient,
        properties: Properties<T>,
    ) : FromWhereable<T, U, CoroutinesSqlClientSelect.Where<T>,
            CoroutinesSqlClientSelect.LimitOffset<T>, CoroutinesSqlClientSelect.GroupBy<T>,
            CoroutinesSqlClientSelect.OrderBy<T>>(properties), CoroutinesSqlClientSelect.FromTable<T, U>,
        CoroutinesSqlClientSelect.From<T>, GroupableBy<T>, OrderableBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> and(table: Table<V>): CoroutinesSqlClientSelect.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): CoroutinesSqlClientSelect.From<T> = addFromSubQuery(dsl, fromTable as FromTable<T, V>)

        override fun and(tsquery: Tsquery): CoroutinesSqlClientSelect.From<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): CoroutinesSqlClientSelect.FromTable<T, U> =
            fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): CoroutinesSqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Froms<T : Any, U : Any>(
        override val client: DatabaseClient,
        properties: Properties<T>,
    ) : FromWhereable<T, U, CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupBy<T>, CoroutinesSqlClientSelect.OrderBy<T>>(properties),
        CoroutinesSqlClientSelect.FromsTable<T, U>, GroupableBy<T>, OrderableBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(client, properties) }
        private val wheres by lazy { Wheres(client, properties) }
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> from(table: Table<V>): CoroutinesSqlClientSelect.FromsTable<T, V> =
            addFromTable(table, fromTable as Froms<T, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): CoroutinesSqlClientSelect.FromsPart2<T> = addFromSubQuery(dsl, fromTable as Froms<T, V>)

        override fun from(tsquery: Tsquery): CoroutinesSqlClientSelect.FromsPart2<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): CoroutinesSqlClientSelect.Froms<T> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): CoroutinesSqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromsTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, CoroutinesSqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Where<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, CoroutinesSqlClientSelect.Where<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupBy<T>, CoroutinesSqlClientSelect.OrderBy<T>>(),
        CoroutinesSqlClientSelect.Where<T>, GroupableBy<T>, OrderableBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy
    }

    private class Wheres<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, CoroutinesSqlClientSelect.Wheres<T>, CoroutinesSqlClientSelect.LimitOffset<T>,
            CoroutinesSqlClientSelect.GroupBy<T>, CoroutinesSqlClientSelect.OrderBy<T>>(),
        CoroutinesSqlClientSelect.Wheres<T>, GroupableBy<T>, OrderableBy<T>, CoroutinesSqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        private val ordersBy by lazy { OrdersBy(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy
    }

    private interface GroupableBy<T : Any> : DefaultSqlClientSelect.GroupableBy<T,
            CoroutinesSqlClientSelect.GroupBy<T>>, CoroutinesSqlClientSelect.GroupableBy<T>, Return<T>

    private class GroupByAndable<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByAndable<T, CoroutinesSqlClientSelect.GroupBy<T>>,
        CoroutinesSqlClientSelect.GroupBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, CoroutinesSqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
    }

    private class GroupsBy<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupableBy<T, CoroutinesSqlClientSelect.GroupsBy<T>>,
        CoroutinesSqlClientSelect.GroupsBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, CoroutinesSqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy by lazy { OrderByAndable(client, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(client, properties) }
        override fun ordersBy(): CoroutinesSqlClientSelect.OrdersBy<T> = ordersBy
    }

    private interface OrderableBy<T : Any> : DefaultSqlClientSelect.OrderableBy<T,
            CoroutinesSqlClientSelect.OrderBy<T>>, CoroutinesSqlClientSelect.OrderableBy<T>, Return<T> {

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.DESC)
    }

    private class OrdersBy<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ): DefaultSqlClientSelect.OrderableBy<T, CoroutinesSqlClientSelect.OrdersBy<T>>,
        CoroutinesSqlClientSelect.OrdersBy<T>, GroupableBy<T>, DefaultSqlClientSelect.LimitOffset<T,
                CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val orderBy = this
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrdersBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrdersBy<T>> =
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
    ) : DefaultSqlClientSelect.OrderByAndable<T, CoroutinesSqlClientSelect.OrderBy<T>>,
        CoroutinesSqlClientSelect.OrderBy<T>,
        DefaultSqlClientSelect.GroupableBy<T, CoroutinesSqlClientSelect.GroupBy<T>>,
        DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(client, properties) }
        override val groupByPart2 by lazy { GroupByAndable(client, properties) }
        override val orderByPart2 = this
        private val groupsBy by lazy { GroupsBy(client, properties) }
        override fun groupsBy(): CoroutinesSqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> andAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.ASC)

        override fun <U : Any> andDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, CoroutinesSqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.DESC)
    }

    private class LimitOffset<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, CoroutinesSqlClientSelect.LimitOffset<T>>,
        CoroutinesSqlClientSelect.LimitOffset<T>, Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : AbstractSqlClientSelectSpringR2dbc.Return<T>,
        CoroutinesSqlClientSelect.Return<T> {

        override suspend fun fetchOne(): T? =
            try {
                val opt = fetch().awaitOne()
                if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: EmptyResultDataAccessException) {
                throw NoResultException()
            } catch (_: IncorrectResultSizeDataAccessException) {
                throw NonUniqueResultException()
            }

        override suspend fun fetchOneOrNull(): T? {
            try {
                val opt = fetch().awaitOneOrNull() ?: return null
                return if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: IncorrectResultSizeDataAccessException) {
                throw NonUniqueResultException()
            }
        }

        override suspend fun fetchFirst(): T? =
            try {
                val opt = fetch().awaitSingle()
                if (opt.isPresent) {
                    opt.get()
                } else {
                    null
                }
            } catch (_: EmptyResultDataAccessException) {
                throw NoResultException()
            }

        override suspend fun fetchFirstOrNull(): T? {
            val opt = fetch().awaitSingleOrNull() ?: return null
            return if (opt.isPresent) {
                opt.get()
            } else {
                null
            }
        }

        override fun fetchAll(): Flow<T> =
            fetch().flow()
                .filter { opt -> opt.isPresent }
                .map { opt -> opt.get() }
    }
}
