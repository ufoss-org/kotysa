/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal

@Suppress("UNCHECKED_CAST")
internal class SqlClientSubQueryImpl internal constructor() : DefaultSqlClientSelect() {

    internal class Scope internal constructor(
        private val initialProps: DefaultSqlClientCommon.Properties,
    ) : SqlClientSubQuery.Scope {
        internal lateinit var properties: Properties<*>
        private fun <T : Any> properties(): Properties<T> {
            val props = Properties<T>(
                initialProps.tables, initialProps.dbAccessType, initialProps.module,
                initialProps.availableColumns
            )
            props.index = initialProps.index
            properties = props
            return props
        }

        override fun <T : Any> select(column: Column<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSubQuery.FirstSelect<Long> =
            FirstSelect<Long>(properties()).apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.DISTINCT) }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.MIN) }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.FirstSelect<T> =
            FirstSelect<T>(properties()).apply { addSelectColumn(column, FieldClassifier.MAX) }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSubQuery.FirstSelect<BigDecimal> =
            FirstSelect<BigDecimal>(properties()).apply { addAvgColumn(column) }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SqlClientSubQuery.FirstSelect<Long> =
            FirstSelect<Long>(properties()).apply { addLongSumColumn(column) }

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.FirstSelect<Float> =
            FirstSelect<Float>(properties()).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.FirstSelect<T> = FirstSelect<T>(properties()).apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.SelectCaseWhenExistsFirst<T> = SelectCaseWhenExistsFirst(properties(), dsl)

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.From<T> = FirstSelect<T>(properties()).selectStarFrom(dsl)

        override fun selects(): SqlClientSubQuery.Selects = Selects(properties())
    }

    private class SelectCaseWhenExistsFirst<T : Any>(
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSubQuery.SelectCaseWhenExistsFirst<T> {
        override fun <U : Any> then(value: U): SqlClientSubQuery.SelectCaseWhenExistsFirstPart2<T, U> =
            SelectCaseWhenExistsFirstPart2(properties as Properties<U>, dsl, value)
    }

    private class SelectCaseWhenExistsFirstPart2<T : Any, U : Any>(
        private val properties: Properties<U>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSubQuery.SelectCaseWhenExistsFirstPart2<T, U> {
        override fun `else`(value: U): SqlClientSubQuery.FirstSelect<U> =
            FirstSelect(properties).apply { addSelectCaseWhenExistsSubQuery(dsl, then, value) }
    }

    private class FirstSelect<T : Any>(override val properties: Properties<T>) : DefaultSqlClientSelect.Select<T>(),
        SqlClientSubQuery.FirstSelect<T> {

        private val from: FromTable<T, *> by lazy {
            FromTable<T, Any>(properties)
        }

        override fun <U : Any> from(table: Table<U>): SqlClientSubQuery.FromTable<T, U> =
            addFromTable(table, from as FromTable<T, U>) as SqlClientSubQuery.FromTable<T, U>

        override fun <U : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSubQuery.From<T> = addFromSubQuery(dsl, from as FromTable<T, U>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.From<T> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSubQuery.Froms<T> = addFroms(from)

        fun <U : Any> selectStarFrom(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSubQuery.From<T> =
            addFromSubQuery(dsl, from as FromTable<T, U>, true)

        override fun <U : Any> and(column: Column<*, U>): SqlClientSubQuery.SecondSelect<T?, U?> =
            SecondSelect(properties as Properties<Pair<T?, U?>>).apply { addSelectColumn(column) }

        override fun <U : Any> and(table: Table<U>): SqlClientSubQuery.SecondSelect<T, U> =
            SecondSelect(properties as Properties<Pair<T, U>>).apply { addSelectTable(table) }

        override fun <U : Any> andCount(column: Column<*, U>): SqlClientSubQuery.SecondSelect<T, Long> =
            SecondSelect(properties as Properties<Pair<T, Long>>).apply { addCountColumn(column) }

        override fun <U : Any> andDistinct(column: Column<*, U>): SqlClientSubQuery.SecondSelect<T?, U?> =
            SecondSelect(properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SqlClientSubQuery.SecondSelect<T?, U?> =
            SecondSelect(properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SqlClientSubQuery.SecondSelect<T?, U?> =
            SecondSelect(properties as Properties<Pair<T?, U?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SqlClientSubQuery.SecondSelect<T?, BigDecimal> =
            SecondSelect(properties as Properties<Pair<T?, BigDecimal>>).apply { addAvgColumn(column) }

        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): SqlClientSubQuery.SecondSelect<T?, Long> =
            SecondSelect(properties as Properties<Pair<T?, Long>>).apply { addLongSumColumn(column) }

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.SecondSelect<T?, Float> =
            SecondSelect(properties as Properties<Pair<T?, Float>>).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun <U : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSubQuery.SecondSelect<T?, U?> =
            SecondSelect(properties as Properties<Pair<T?, U?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): SqlClientSubQuery.AndCaseWhenExistsSecond<T, U> = AndCaseWhenExistsSecond(properties, dsl)

        override fun `as`(alias: String): SqlClientSubQuery.FirstSelect<T> = this.apply { aliasLastColumn(alias) }
    }

    private class Selects(override val properties: Properties<List<Any?>>) :
        DefaultSqlClientSelect.Select<List<Any?>>(), SqlClientSubQuery.SelectsPart2 {
        init {
            properties.isConditionalSelect = true
        }

        private val from: FromTable<List<Any?>, *> by lazy {
            FromTable<List<Any?>, Any>(properties)
        }
        private val froms: Froms<List<Any?>, *> by lazy {
            Froms<List<Any?>, Any>(properties)
        }

        override fun <T : Any> from(table: Table<T>): SqlClientSubQuery.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>) as SqlClientSubQuery.FromTable<List<Any?>, T>

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.From<List<Any?>> = addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSubQuery.Froms<List<Any?>> = addFroms(froms)

        override fun <T : Any> select(column: Column<*, T>): SqlClientSubQuery.SelectsPart2 =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> select(table: Table<T>): SqlClientSubQuery.SelectsPart2 =
            this.apply { addSelectTable(table) }

        override fun <T : Any> selectCount(column: Column<*, T>?): SqlClientSubQuery.SelectsPart2 =
            this.apply { addCountColumn(column) }

        override fun <T : Any> selectDistinct(column: Column<*, T>): SqlClientSubQuery.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.SelectsPart2 = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SqlClientSubQuery.SelectsPart2 = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SqlClientSubQuery.SelectsPart2 =
            this.apply { addLongSumColumn(column) }

        override fun <T : Any> select(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.SelectsPart2 = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.SelectsCaseWhenExists<T> = SelectsCaseWhenExists(properties, dsl)

        override fun selectTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.SelectsPart2 = this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun `as`(alias: String): SqlClientSubQuery.SelectsPart2 = this.apply { aliasLastColumn(alias) }
    }

    private class SelectsCaseWhenExists<T : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSubQuery.SelectsCaseWhenExists<T> {
        override fun <U : Any> then(value: U): SqlClientSubQuery.SelectsCaseWhenExistsPart2<T, U> =
            SelectsCaseWhenExistsPart2(properties, dsl, value)
    }

    private class SelectsCaseWhenExistsPart2<T : Any, U : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSubQuery.SelectsCaseWhenExistsPart2<T, U> {
        override fun `else`(value: U): SqlClientSubQuery.SelectsPart2 =
            Selects(properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class AndCaseWhenExistsSecond<T : Any, U : Any>(
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
    ) : SqlClientSubQuery.AndCaseWhenExistsSecond<T, U> {
        override fun <V : Any> then(value: V): SqlClientSubQuery.AndCaseWhenExistsSecondPart2<T, U, V> =
            AndCaseWhenExistsSecondPart2(properties, dsl, value)
    }

    private class AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any>(
        private val properties: Properties<T>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
        private val then: V,
    ) : SqlClientSubQuery.AndCaseWhenExistsSecondPart2<T, U, V> {
        override fun `else`(value: V): SqlClientSubQuery.SecondSelect<T?, V> =
            SecondSelect(properties as Properties<Pair<T?, V>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class SecondSelect<T, U>(override val properties: Properties<Pair<T, U>>) :
        DefaultSqlClientSelect.Select<Pair<T, U>>(), SqlClientSubQuery.SecondSelect<T, U> {

        private val from: FromTable<Pair<T, U>, *> by lazy {
            FromTable<Pair<T, U>, Any>(properties)
        }

        override fun <V : Any> from(table: Table<V>): SqlClientSubQuery.FromTable<Pair<T, U>, V> =
            addFromTable(table, from as FromTable<Pair<T, U>, V>) as SqlClientSubQuery.FromTable<Pair<T, U>, V>

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.From<Pair<T, U>> = addFromSubQuery(dsl, from as FromTable<Pair<T, U>, V>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.From<Pair<T, U>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSubQuery.Froms<Pair<T, U>> = addFroms(from)

        override fun <V : Any> and(column: Column<*, V>): SqlClientSubQuery.ThirdSelect<T, U, V?> =
            ThirdSelect(properties as Properties<Triple<T, U, V?>>).apply { addSelectColumn(column) }

        override fun <V : Any> and(table: Table<V>): SqlClientSubQuery.ThirdSelect<T, U, V> =
            ThirdSelect(properties as Properties<Triple<T, U, V>>).apply { addSelectTable(table) }

        override fun <V : Any> andCount(column: Column<*, V>): SqlClientSubQuery.ThirdSelect<T, U, Long> =
            ThirdSelect(properties as Properties<Triple<T, U, Long>>).apply { addCountColumn(column) }

        override fun <V : Any> andDistinct(column: Column<*, V>): SqlClientSubQuery.ThirdSelect<T, U, V?> =
            ThirdSelect(properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): SqlClientSubQuery.ThirdSelect<T, U, V?> =
            ThirdSelect(properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): SqlClientSubQuery.ThirdSelect<T, U, V?> =
            ThirdSelect(properties as Properties<Triple<T, U, V?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <V : Any> andAvg(column: NumericColumn<*, V>): SqlClientSubQuery.ThirdSelect<T, U, BigDecimal> =
            ThirdSelect(properties as Properties<Triple<T, U, BigDecimal>>).apply { addAvgColumn(column) }

        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): SqlClientSubQuery.ThirdSelect<T, U, Long> =
            ThirdSelect(properties as Properties<Triple<T, U, Long>>).apply { addLongSumColumn(column) }

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.ThirdSelect<T, U, Float> =
            ThirdSelect(properties as Properties<Triple<T, U, Float>>).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.ThirdSelect<T, U, V?> =
            ThirdSelect(properties as Properties<Triple<T, U, V?>>).apply {
                addSelectSubQuery(dsl)
            }

        override fun <V : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.AndCaseWhenExistsThird<T, U, V> = AndCaseWhenExistsThird(properties, dsl)

        override fun `as`(alias: String): SqlClientSubQuery.SecondSelect<T, U> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsThird<T, U, V : Any>(
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>
    ) : SqlClientSubQuery.AndCaseWhenExistsThird<T, U, V> {
        override fun <W : Any> then(value: W): SqlClientSubQuery.AndCaseWhenExistsThirdPart2<T, U, V, W> =
            AndCaseWhenExistsThirdPart2(properties, dsl, value)
    }

    private class AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any>(
        private val properties: Properties<Pair<T, U>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>,
        private val then: W,
    ) : SqlClientSubQuery.AndCaseWhenExistsThirdPart2<T, U, V, W> {
        override fun `else`(value: W): SqlClientSubQuery.ThirdSelect<T, U, W> =
            ThirdSelect(properties as Properties<Triple<T, U, W>>).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class ThirdSelect<T, U, V>(override val properties: Properties<Triple<T, U, V>>) :
        DefaultSqlClientSelect.Select<Triple<T, U, V>>(), SqlClientSubQuery.ThirdSelect<T, U, V> {

        private val from: FromTable<Triple<T, U, V>, *> by lazy {
            FromTable<Triple<T, U, V>, Any>(properties)
        }

        override fun <W : Any> from(table: Table<W>): SqlClientSubQuery.FromTable<Triple<T, U, V>, W> =
            addFromTable(
                table,
                from as FromTable<Triple<T, U, V>, W>
            ) as SqlClientSubQuery.FromTable<Triple<T, U, V>, W>

        override fun <W : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSubQuery.From<Triple<T, U, V>> =
            addFromSubQuery(dsl, from as FromTable<Triple<T, U, V>, W>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.From<Triple<T, U, V>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSubQuery.Froms<Triple<T, U, V>> = addFroms(from)

        override fun <W : Any> and(column: Column<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addSelectColumn(column) }

        override fun <W : Any> and(table: Table<W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addSelectTable(table) }

        override fun <W : Any> andCount(column: Column<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addCountColumn(column) }

        override fun <W : Any> andDistinct(column: Column<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.DISTINCT)
            }

        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MIN)
            }

        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply {
                addSelectColumn(column, FieldClassifier.MAX)
            }

        override fun <W : Any> andAvg(column: NumericColumn<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addAvgColumn(column) }

        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addLongSumColumn(column) }

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.Select =
            Select(properties as Properties<List<Any?>>).apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun <W : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSubQuery.Select = Select(properties as Properties<List<Any?>>).apply {
            addSelectSubQuery(dsl)
        }

        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): SqlClientSubQuery.AndCaseWhenExistsLast<W> =
            AndCaseWhenExistsLast(properties as Properties<List<Any?>>, dsl)

        override fun `as`(alias: String): SqlClientSubQuery.ThirdSelect<T, U, V> = this.apply { aliasLastColumn(alias) }
    }

    private class AndCaseWhenExistsLast<T : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ) : SqlClientSubQuery.AndCaseWhenExistsLast<T> {
        override fun <U : Any> then(value: U): SqlClientSubQuery.AndCaseWhenExistsLastPart2<T, U> =
            AndCaseWhenExistsLastPart2(properties, dsl, value)
    }

    private class AndCaseWhenExistsLastPart2<T : Any, U : Any>(
        private val properties: Properties<List<Any?>>,
        private val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
        private val then: U,
    ) : SqlClientSubQuery.AndCaseWhenExistsLastPart2<T, U> {
        override fun `else`(value: U): SqlClientSubQuery.Select =
            Select(properties).apply {
                addSelectCaseWhenExistsSubQuery(dsl, then, value)
            }
    }

    private class Select(override val properties: Properties<List<Any?>>) : DefaultSqlClientSelect.Select<List<Any?>>(),
        SqlClientSubQuery.Select {
        private val from: FromTable<List<Any?>, *> = FromTable<List<Any?>, Any>(properties)

        override fun <T : Any> from(table: Table<T>): SqlClientSubQuery.FromTable<List<Any?>, T> =
            addFromTable(table, from as FromTable<List<Any?>, T>) as SqlClientSubQuery.FromTable<List<Any?>, T>

        override fun <T : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.From<List<Any?>> =
            addFromSubQuery(dsl, from as FromTable<List<Any?>, T>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.From<List<Any?>> = addFromTsquery(tsquery, from)

        override fun froms(): SqlClientSubQuery.Froms<List<Any?>> = addFroms(from)

        override fun <T : Any> and(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addSelectColumn(column) }

        override fun <T : Any> and(table: Table<T>): SqlClientSubQuery.Select = this.apply { addSelectTable(table) }

        override fun <T : Any> andCount(column: Column<*, T>): SqlClientSubQuery.Select =
            this.apply { addCountColumn(column) }

        override fun <T : Any> andDistinct(column: Column<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.DISTINCT)
        }

        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MIN)
        }

        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addSelectColumn(column, FieldClassifier.MAX)
        }

        override fun <T : Any> andAvg(column: NumericColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addAvgColumn(column)
        }

        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): SqlClientSubQuery.Select = this.apply {
            addLongSumColumn(column)
        }

        override fun andTsRankCd(
            tsvectorColumn: TsvectorColumn<*>,
            tsquery: Tsquery,
        ): SqlClientSubQuery.Select = this.apply { addTsRankCd(tsvectorColumn, tsquery) }

        override fun <T : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.Select = this.apply { addSelectSubQuery(dsl) }

        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SqlClientSubQuery.AndCaseWhenExistsLast<T> = AndCaseWhenExistsLast(properties, dsl)

        override fun `as`(alias: String): SqlClientSubQuery.Select = this.apply { aliasLastColumn(alias) }
    }

    private class FromTable<T : Any, U : Any>(
        properties: Properties<T>,
    ) : FromWhereableSubQuery<T, U, SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByAndable<T>>(properties), SqlClientSubQuery.FromTable<T, U>,
        SqlClientSubQuery.From<T>, GroupableBy<T>, SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val fromTable = this

        override val where by lazy { Where(properties) }
        private val wheres by lazy { Wheres(properties) }
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByAndable(properties) }
        private val groupsBy by lazy { GroupsBy(properties) }
        override fun groupsBy(): SqlClientSubQuery.GroupsBy<T> = groupsBy

        override fun <V : Any> and(table: Table<V>): SqlClientSubQuery.FromTable<T, V> =
            addFromTable(table, fromTable as FromTable<T, V>)

        override fun <V : Any> and(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.From<T> =
            addFromSubQuery(dsl, fromTable as FromTable<T, V>)

        override fun and(tsquery: Tsquery): SqlClientSubQuery.From<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): SqlClientSubQuery.FromTable<T, U> =
            fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): SqlClientSubQuery.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): Joinable<U, V, SqlClientSubQuery.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.INNER) as Joinable<U, V, SqlClientSubQuery.FromTable<T, V>>

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): Joinable<U, V, SqlClientSubQuery.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER) as Joinable<U, V, SqlClientSubQuery.FromTable<T, V>>

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): Joinable<U, V, SqlClientSubQuery.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER) as Joinable<U, V, SqlClientSubQuery.FromTable<T, V>>

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): Joinable<U, V, SqlClientSubQuery.FromTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER) as Joinable<U, V, SqlClientSubQuery.FromTable<T, V>>
    }

    private class Froms<T : Any, U : Any>(properties: Properties<T>) : FromWhereableSubQuery<T, U,
            SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByAndable<T>>(properties), SqlClientSubQuery.FromsTable<T, U>, GroupableBy<T>,
        SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val fromTable = this

        override val where by lazy { Where(properties) }
        private val wheres by lazy { Wheres(properties) }
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByAndable(properties) }
        private val groupsBy by lazy { GroupsBy(properties) }
        override fun groupsBy(): SqlClientSubQuery.GroupsBy<T> = groupsBy

        override fun <V : Any> from(table: Table<V>): SqlClientSubQuery.FromsTable<T, V> =
            addFromTable(table, fromTable as Froms<T, V>)

        override fun <V : Any> from(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>
        ): SqlClientSubQuery.FromsPart2<T> = addFromSubQuery(dsl, fromTable as Froms<T, V>)

        override fun from(tsquery: Tsquery): SqlClientSubQuery.FromsPart2<T> = addFromTsquery(tsquery, fromTable)

        override fun `as`(alias: String): SqlClientSubQuery.Froms<T> = fromTable.apply { aliasLastFrom(alias) }

        override fun wheres(): SqlClientSubQuery.Wheres<T> = wheres

        override fun <V : Any> innerJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, SqlClientSubQuery.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.INNER)

        override fun <V : Any> leftJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, SqlClientSubQuery.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.LEFT_OUTER)

        override fun <V : Any> rightJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, SqlClientSubQuery.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.RIGHT_OUTER)

        override fun <V : Any> fullJoin(
            table: Table<V>
        ): SqlClientQuery.Joinable<U, V, SqlClientSubQuery.FromsTable<T, V>> =
            joinProtected(table, JoinClauseType.FULL_OUTER)
    }

    private class Where<T : Any>(
        override val properties: Properties<T>,
    ) : WhereSubQuery<T, SqlClientSubQuery.Where<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByAndable<T>>(), SqlClientSubQuery.Where<T>, GroupableBy<T>,
        SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByAndable(properties) }
        private val groupsBy by lazy { GroupsBy(properties) }
        override fun groupsBy(): SqlClientSubQuery.GroupsBy<T> = groupsBy
    }

    private class Wheres<T : Any>(
        override val properties: Properties<T>,
    ) : WhereSubQuery<T, SqlClientSubQuery.Wheres<T>, SqlClientSubQuery.LimitOffset<T>,
            SqlClientSubQuery.GroupByAndable<T>>(), SqlClientSubQuery.Wheres<T>, GroupableBy<T>,
        SqlClientSubQuery.LimitOffset<T>, Return<T> {
        override val where = this
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 by lazy { GroupByAndable(properties) }
        private val groupsBy by lazy { GroupsBy(properties) }
        override fun groupsBy(): SqlClientSubQuery.GroupsBy<T> = groupsBy
    }

    private interface GroupableBy<T : Any> : DefaultSqlClientSelect.GroupableBy<T, SqlClientSubQuery.GroupByAndable<T>>,
        SqlClientSubQuery.GroupableBy<T>

    private class GroupByAndable<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.GroupByAndable<T, SqlClientSubQuery.GroupByAndable<T>>,
        SqlClientSubQuery.GroupByAndable<T>,
        DefaultSqlClientSelect.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 = this
    }

    private class GroupsBy<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.GroupableBy<T, SqlClientSubQuery.GroupsBy<T>>,
        SqlClientSubQuery.GroupsBy<T>,
        DefaultSqlClientSelect.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, Return<T> {
        override val limitOffset by lazy { LimitOffset(properties) }
        override val groupByPart2 = this
    }

    private class LimitOffset<T : Any>(
        override val properties: Properties<T>,
    ) : DefaultSqlClientSelect.LimitOffset<T, SqlClientSubQuery.LimitOffset<T>>, SqlClientSubQuery.LimitOffset<T>,
        Return<T> {
        override val limitOffset = this
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSubQuery.Return<T> {
        override fun sql(parentProperties: DefaultSqlClientCommon.Properties): String {
            // must take care of index
            properties.index = parentProperties.index
            val select = selectSql(true)
            parentProperties.index = properties.index
            return select
        }
    }
}
