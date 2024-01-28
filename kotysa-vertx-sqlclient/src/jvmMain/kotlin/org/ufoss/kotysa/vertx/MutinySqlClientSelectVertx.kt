/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.smallrye.mutiny.Uni
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal

@Suppress("UNCHECKED_CAST")
internal class MutinySqlClientSelectVertx private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
        private val pool: Pool,
        private val tables: Tables,
    ) : MutinySqlClientSelect.Selectable {
        private fun <T : Any> properties() = Properties<T>(tables, DbAccessType.VERTX, Module.VERTX_SQL_CLIENT)

        override fun <T : Any> select(column: Column<*, T>): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): MutinySqlClientSelect.Fromable<T> =
            SelectWithDsl(pool, properties(), dsl)

        override fun <T : Any> selectCount(column: Column<*, T>?): MutinySqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(pool, properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): MutinySqlClientSelect.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(pool, properties()).apply { addAvgColumn(column) }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): MutinySqlClientSelect.FirstSelect<Long> =
            FirstSelect<Long>(pool, properties()).apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.FirstSelect<T> =
            FirstSelect<T>(pool, properties()).apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.SelectCaseWhenExistsFirst<T> = SelectCaseWhenExistsFirst(pool, properties(), dsl)

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.From<T> = FirstSelect<T>(pool, properties()).selectStarFrom(dsl)

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): MutinySqlClientSelect.FirstSelect<Float> =
            FirstSelect<Float>(pool, properties()).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun selects(): MutinySqlClientSelect.Selects = Selects(pool, properties())
    }

    private class SelectCaseWhenExistsFirst<T : Any>(
        private val pool: Pool,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : MutinySqlClientSelect.SelectCaseWhenExistsFirst<T> {
        override fun <U : Any> then(value: U): MutinySqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> =
            SelectCaseWhenExistsFirstPart2(pool, properties as Properties<U>, dsl, value)
    }

    private class SelectCaseWhenExistsFirstPart2<T : Any, U : Any>(
        private val pool: Pool,
        private val properties: Properties<U>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : MutinySqlClientSelect.SelectCaseWhenExistsFirstPart2<T, U> {
        override fun `else`(value: U): MutinySqlClientSelect.FirstSelect<U> =
            FirstSelect(pool, properties).apply { addSelectCaseWhenExistsSubQuery(dsl, then, value) }
    }

    private class FirstSelect<T : Any>(
        private val pool: Pool,
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.Select<T>(), MutinySqlClientSelect.FirstSelect<T> {
        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(pool, properties)
        }
        private val froms: Froms<T, *> by lazy {
            Froms<T, Any>(pool, properties)
        }

        override fun <U : Any> from(table: Table<U>): MutinySqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): MutinySqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<T> = addFroms(froms)

        fun <U : Any> selectStarFrom(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): MutinySqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>, true)

        override fun <U : Any> and(column: Column<*, U>): MutinySqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(pool, properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): MutinySqlClientSelect.SecondSelect<T, U> =
            SecondSelect(pool, properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): MutinySqlClientSelect.SecondSelect<T, Long> =
            SecondSelect(pool, properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): MutinySqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(pool, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): MutinySqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(pool, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): MutinySqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(pool, properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): MutinySqlClientSelect.SecondSelect<T?, BigDecimal> =
            SecondSelect(pool, properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }

        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): MutinySqlClientSelect.SecondSelect<T?, Long> =
            SecondSelect(pool, properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): MutinySqlClientSelect.SecondSelect<T?, U?> =
            SecondSelect(pool, properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): MutinySqlClientSelect.AndCaseWhenExistsSecond<T, U> = AndCaseWhenExistsSecond(pool, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): MutinySqlClientSelect.SecondSelect<T?, Float> =
            SecondSelect(pool, properties as Properties<Pair<T?, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): MutinySqlClientSelect.FirstSelect<T> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsSecond<T : Any, U : Any>(
        private val pool: Pool,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
    ) : MutinySqlClientSelect.AndCaseWhenExistsSecond<T, U> {
        override fun <V : Any> then(value: V): MutinySqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> =
            AndCaseWhenExistsSecondPart2(pool, properties, dsl, value)
    }

    private class AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any>(
        private val pool: Pool,
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        private val then: V,
    ) : MutinySqlClientSelect.AndCaseWhenExistsSecondPart2<T, U, V> {
        override fun `else`(value: V): MutinySqlClientSelect.SecondSelect<T?, V> =
            SecondSelect(pool, properties as Properties<Pair<T?, V>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SecondSelect<T, U>(
        private val pool: Pool,
        override val properties: Properties<Pair<T, U>>,
    ) : DefaultSqlClientSelect.Select<Pair<T, U>>(), MutinySqlClientSelect.SecondSelect<T, U> {
        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(pool, properties)
        }
        private val froms: Froms<Pair<T, U>, *> by lazy {
            Froms<Pair<T, U>, Any>(pool, properties)
        }

        override fun <V : Any> from(table: Table<V>): MutinySqlClientSelect.FromTable<Pair<T, U>, V> =
            addFromTable(table, from as FromTable<Pair<T, U>, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): MutinySqlClientSelect.From<Pair<T, U>> = addFromSubQuery(dsl, from as FromTable<Pair<T, U>, V>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<Pair<T, U>> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<Pair<T, U>> = addFroms(froms)

        override fun <V : Any> and(column: Column<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): MutinySqlClientSelect.ThirdSelect<T, U, V> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(
                pool,
                properties as Properties<Triple<T, U, BigDecimal>>
            ).apply { addAvgColumn(column) }

        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): MutinySqlClientSelect.ThirdSelect<T, U, Long> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): MutinySqlClientSelect.ThirdSelect<T, U, V?> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <V : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): MutinySqlClientSelect.AndCaseWhenExistsThird<T, U, V> = AndCaseWhenExistsThird(pool, properties, dsl)

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery
        ): MutinySqlClientSelect.ThirdSelect<T, U, Float> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, Float>>)
                .apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): MutinySqlClientSelect.SecondSelect<T, U> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsThird<T, U, V : Any>(
        private val pool: Pool,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
    ) : MutinySqlClientSelect.AndCaseWhenExistsThird<T, U, V> {
        override fun <W : Any> then(value: W): MutinySqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> =
            AndCaseWhenExistsThirdPart2(pool, properties, dsl, value)
    }

    private class AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any>(
        private val pool: Pool,
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>,
        private val then: W,
    ) : MutinySqlClientSelect.AndCaseWhenExistsThirdPart2<T, U, V, W> {
        override fun `else`(value: W): MutinySqlClientSelect.ThirdSelect<T, U, W> =
            ThirdSelect(pool, properties as Properties<Triple<T, U, W>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class ThirdSelect<T, U, V>(
        private val pool: Pool,
        override val properties: Properties<Triple<T, U, V>>,
    ) : DefaultSqlClientSelect.Select<Triple<T, U, V>>(), MutinySqlClientSelect.ThirdSelect<T, U, V> {
        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(pool, properties)
        }
        private val froms: Froms<Triple<T, U, V>, *> by lazy {
            Froms<Triple<T, U, V>, Any>(pool, properties)
        }

        override fun <W : Any> from(table: Table<W>): MutinySqlClientSelect.FromTable<Triple<T, U, V>, W> =
            addFromTable(table, from as FromTable<Triple<T, U, V>, W>)

        override fun <W : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): MutinySqlClientSelect.From<Triple<T, U, V>> =
            addFromSubQuery(dsl, from as FromTable<Triple<T, U, V>, W>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<Triple<T, U, V>> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<Triple<T, U, V>> = addFroms(froms)

        override fun <W : Any> and(column: Column<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): MutinySqlClientSelect.Select = Select(pool, properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }

        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): MutinySqlClientSelect.AndCaseWhenExistsLast<W> =
            AndCaseWhenExistsLast(pool, properties as Properties<List<Any?>>, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): MutinySqlClientSelect.Select =
            Select(pool, properties as Properties<List<Any?>>).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): MutinySqlClientSelect.ThirdSelect<T, U, V> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val pool: Pool,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : MutinySqlClientSelect.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): MutinySqlClientSelect.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(pool, properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val pool: Pool,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : MutinySqlClientSelect.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): MutinySqlClientSelect.Select =
            Select(pool, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(
        private val pool: Pool,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), MutinySqlClientSelect.Select {
        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(pool, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(pool, properties)

        override fun <T : Any> from(table: Table<T>): MutinySqlClientSelect.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> and(column: Column<*, T>): MutinySqlClientSelect.Select =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> and(table: Table<T>): MutinySqlClientSelect.Select = this.apply { addSelectTable(table) }

        override fun <T : Any> andCount(column: Column<*, T>): MutinySqlClientSelect.Select =
            this.apply { addCountColumn(column) }

        override fun <T : Any> andDistinct(column: Column<*, T>): MutinySqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): MutinySqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): MutinySqlClientSelect.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): MutinySqlClientSelect.Select = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): MutinySqlClientSelect.Select =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.AndCaseWhenExistsLast<T> = AndCaseWhenExistsLast(pool, properties, dsl)

        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): MutinySqlClientSelect.Select =
            Select(pool, properties).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): MutinySqlClientSelect.Select = this.apply { aliasLastColumn(alias) }
    }

    private class Selects(
        private val pool: Pool,
        override val properties: Properties<List<Any?>>,
    ) : DefaultSqlClientSelect.Select<List<Any?>>(), MutinySqlClientSelect.SelectsPart2 {
        init {
            properties.isConditionalSelect = true
        }

        // todo lazy
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(pool, properties)
        private val froms: Froms<List<Any?>, *> = Froms<List<Any?>, Any>(pool, properties)

        override fun <T : Any> from(table: Table<T>): MutinySqlClientSelect.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>)

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> select(column: Column<*, T>): MutinySqlClientSelect.SelectsPart2 =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): MutinySqlClientSelect.SelectsPart2 =
            this.apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): MutinySqlClientSelect.SelectsPart2 =
            this.apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): MutinySqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): MutinySqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): MutinySqlClientSelect.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): MutinySqlClientSelect.SelectsPart2 = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): MutinySqlClientSelect.SelectsPart2 =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.SelectsPart2 = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): MutinySqlClientSelect.SelectsCaseWhenExists<T> = SelectsCaseWhenExists(pool, properties, dsl)

        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): MutinySqlClientSelect.SelectsPart2 =
            this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): MutinySqlClientSelect.SelectsPart2 = this.apply { aliasLastColumn(alias) }
    }

    private class SelectsCaseWhenExists<T : Any>(
        private val pool: Pool,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : MutinySqlClientSelect.SelectsCaseWhenExists<T> {
        override fun <U : Any> then(value: U): MutinySqlClientSelect.SelectsCaseWhenExistsPart2<T, U> =
            SelectsCaseWhenExistsPart2(pool, properties, dsl, value)
    }

    private class SelectsCaseWhenExistsPart2<T : Any, U : Any>(
        private val pool: Pool,
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : MutinySqlClientSelect.SelectsCaseWhenExistsPart2<T, U> {
        override fun `else`(value: U): MutinySqlClientSelect.SelectsPart2 =
            Selects(pool, properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SelectWithDsl<T : Any>(
        pool: Pool,
        properties: Properties<T>,
        dsl: (ValueProvider) -> T,
    ) : DefaultSqlClientSelect.SelectWithDsl<T>(properties, dsl), MutinySqlClientSelect.Fromable<T> {
        // todo lazy
        private val from: FromTable<T, *> = FromTable<T, Any>(pool, properties)
        private val froms: Froms<T, *> = Froms<T, Any>(pool, properties)

        override fun <U : Any> from(table: Table<U>): MutinySqlClientSelect.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>)

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): MutinySqlClientSelect.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): MutinySqlClientSelect.Froms<T> = addFroms(froms)

        override fun `as`(alias: String): Nothing {
            throw IllegalArgumentException("No Alias for selectAndBuild")
        }
    }

    private class FromTable<T : Any, U : Any>(
        override val pool: Pool,
        properties: Properties<T>,
    ) : FromWhereable<T, U, MutinySqlClientSelect.Where<T>, MutinySqlClientSelect.LimitOffset<T>, MutinySqlClientSelect.GroupBy<T>,
            MutinySqlClientSelect.OrderBy<T>>(properties), MutinySqlClientSelect.FromTable<T, U>, MutinySqlClientSelect.From<T>,
        GroupableBy<T>, OrderableBy<T>, MutinySqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(pool, properties) }
        private val wheres by lazy { Wheres(pool, properties) }
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> and(table: Table<V>): MutinySqlClientSelect.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): MutinySqlClientSelect.From<T> = addFromSubQuery(dsl, fromTable as FromTable<T, V>)

        override fun and(tsquery: Tsquery): MutinySqlClientSelect.From<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): MutinySqlClientSelect.FromTable<T, U> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): MutinySqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Froms<T : Any, U : Any>(
        override val pool: Pool,
        properties: Properties<T>,
    ) : FromWhereable<T, U, MutinySqlClientSelect.Where<T>, MutinySqlClientSelect.LimitOffset<T>,
            MutinySqlClientSelect.GroupBy<T>, MutinySqlClientSelect.OrderBy<T>>(properties),
        MutinySqlClientSelect.FromsTable<T, U>, GroupableBy<T>, OrderableBy<T>, MutinySqlClientSelect.LimitOffset<T> {
        override val fromTable = this

        override val where by lazy { Where(pool, properties) }
        private val wheres by lazy { Wheres(pool, properties) }
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy

        override fun <V : Any> from(table: Table<V>): MutinySqlClientSelect.FromsTable<T, V> =
            addFromTable(table, fromTable as Froms<T, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): MutinySqlClientSelect.FromsPart2<T> = addFromSubQuery(dsl, fromTable as Froms<T, V>)

        override fun from(tsquery: Tsquery): MutinySqlClientSelect.FromsPart2<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): MutinySqlClientSelect.Froms<T> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): MutinySqlClientSelect.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromsTable<T, V>> = joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<V, MutinySqlClientSelect.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Where<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, MutinySqlClientSelect.Where<T>, MutinySqlClientSelect.LimitOffset<T>,
            MutinySqlClientSelect.GroupBy<T>, MutinySqlClientSelect.OrderBy<T>>(), MutinySqlClientSelect.Where<T>,
        GroupableBy<T>, OrderableBy<T>, MutinySqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy
    }

    private class Wheres<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, MutinySqlClientSelect.Wheres<T>, MutinySqlClientSelect.LimitOffset<T>,
            MutinySqlClientSelect.GroupBy<T>, MutinySqlClientSelect.OrderBy<T>>(), MutinySqlClientSelect.Wheres<T>,
        GroupableBy<T>, OrderableBy<T>, MutinySqlClientSelect.LimitOffset<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy
    }

    private interface GroupableBy<T : Any> : DefaultSqlClientSelect.GroupableBy<T, MutinySqlClientSelect.GroupBy<T>>,
        MutinySqlClientSelect.GroupableBy<T>, Return<T>

    private class GroupByAndable<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupByAndable<T, MutinySqlClientSelect.GroupBy<T>>,
        MutinySqlClientSelect.GroupBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, MutinySqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, MutinySqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
    }

    private class GroupsBy<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.GroupableBy<T, MutinySqlClientSelect.GroupsBy<T>>,
        MutinySqlClientSelect.GroupsBy<T>,
        DefaultSqlClientSelect.OrderableBy<T, MutinySqlClientSelect.OrderBy<T>>,
        OrderableBy<T>, DefaultSqlClientSelect.LimitOffset<T, MutinySqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val orderBy by lazy { OrderByAndable(pool, properties) }
        override val groupByPart2 = this
        private val ordersBy by lazy { OrdersBy(pool, properties) }
        override fun ordersBy(): MutinySqlClientSelect.OrdersBy<T> = ordersBy
    }

    private interface OrderableBy<T : Any> : DefaultSqlClientSelect.OrderableBy<T, MutinySqlClientSelect.OrderBy<T>>,
        MutinySqlClientSelect.OrderableBy<T>, Return<T> {

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.DESC)
    }

    private class OrdersBy<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ): DefaultSqlClientSelect.OrderableBy<T, MutinySqlClientSelect.OrdersBy<T>>, MutinySqlClientSelect.OrdersBy<T>, GroupableBy<T>,
        DefaultSqlClientSelect.LimitOffset<T, MutinySqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val orderBy = this
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrdersBy<T>> =
            OrderByCaseWhenExists(properties, orderBy, dsl, Order.ASC)

        override fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrdersBy<T>> =
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
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.OrderByAndable<T, MutinySqlClientSelect.OrderBy<T>>, MutinySqlClientSelect.OrderBy<T>,
        DefaultSqlClientSelect.GroupableBy<T, MutinySqlClientSelect.GroupBy<T>>,
        DefaultSqlClientSelect.LimitOffset<T, MutinySqlClientSelect.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(pool, properties) }
        override val groupByPart2 by lazy { GroupByAndable(pool, properties) }
        override val orderByPart2 = this
        private val groupsBy by lazy { GroupsBy(pool, properties) }
        override fun groupsBy(): MutinySqlClientSelect.GroupsBy<T> = groupsBy

        override fun <U : Any> andAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.ASC)

        override fun <U : Any> andDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientQuery.OrderByCaseWhenExists<U, MutinySqlClientSelect.OrderBy<T>> =
            OrderByCaseWhenExists(properties, orderByPart2, dsl, Order.DESC)
    }

    private class LimitOffset<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.LimitOffset<T, MutinySqlClientSelect.LimitOffset<T>>,
        MutinySqlClientSelect.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, MutinySqlClientSelect.Return<T> {
        val pool: Pool

        override fun fetchOne(): Uni<T> =
            fetchAll()
                .map { results ->
                    if (results.isEmpty()) {
                        null
                    } else if (results.size > 1) {
                        throw NonUniqueResultException()
                    } else {
                        results.first()
                    }
                }

        override fun fetchFirst(): Uni<T> =
            fetchAll()
                .map { results ->
                    if (results.isEmpty()) {
                        null
                    } else {
                        results.first()
                    }
                }

        override fun fetchAll(): Uni<List<T>> =
            pool.getVertxConnection().executeUni { connection ->
                connection.preparedQuery(selectSql())
                    .execute(buildTuple())
                    .map { rowSet ->
                        rowSet.mapNotNull { row ->
                            properties.select(row.toRow())
                        }
                    }
            }

        private fun buildTuple() = with(properties) {
            val tuple = Tuple.tuple()
            // 1) add all values from where part
            vertxBindParams(tuple)

            // 2) add limit and offset (order is different depending on DbType)
            if (DbType.MSSQL == tables.dbType || DbType.ORACLE == tables.dbType) {
                offsetParam(tuple)
                limitParam(tuple)
            } else {
                limitParam(tuple)
                offsetParam(tuple)
            }
            tuple
        }

        private fun offsetParam(tuple: Tuple) {
            with(properties) {
                if (offset != null) {
                    tuple.addValue(offset)
                }
            }
        }

        private fun limitParam(tuple: Tuple) {
            with(properties) {
                if (limit != null) {
                    tuple.addValue(limit)
                }
            }
        }
    }
}
