/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal

public class SqlClientSubQuery private constructor() {

    public interface SingleScope : SqlClientQuery.SelectableSingle {
        override fun <T : Any> select(column: Column<*, T>): SelectFromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): SelectFromable<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): SelectFromable<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SelectFromable<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SelectFromable<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): SelectFromable<BigDecimal>
        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SelectFromable<Long>
        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SelectFromable<Float>
    }

    public interface Scope : SingleScope, SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): FirstSelect<BigDecimal>
        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): FirstSelect<Long>
        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): FirstSelect<Float>
        override fun <T : Any> selectCaseWhenExists(
            dsl: SingleScope.() -> Return<T>
        ): SelectCaseWhenExistsFirst<T>
        public fun <T : Any> selectStarFromSubQuery(
            dsl: Scope.() -> Return<T>
        ): From<T>
        public fun selects(): Selects
    }

    public interface SelectCaseWhenExistsFirst<T : Any> : SqlClientQuery.SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SqlClientQuery.SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): FirstSelect<U>
    }

    public interface Whereable<T : Any> : SqlClientQuery.Whereable<Where<T>> {
        public fun wheres(): Wheres<T>
    }

    public interface Froms<T : Any> : SqlClientQuery.Fromable, Whereable<T>,
        GroupableBy<T>, LimitOffset<T>, Return<T> {
        override fun <U : Any> from(table: Table<U>): FromsTable<T, U>
        override fun <U : Any> from(dsl: Scope.() -> Return<U>): FromsPart2<T>
        override fun from(tsquery: Tsquery): FromsPart2<T>
    }

    public interface FromsPart2<T : Any> : Froms<T>, SqlClientQuery.From {
        override fun `as`(alias: String): Froms<T>
    }

    public interface FromsTable<T : Any, U : Any> : Froms<T>, SqlClientQuery.FromTableSelect<U>, SqlClientQuery.From {
        override fun <V : Any> innerJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromsTable<T, V>>
        override fun <V : Any> leftJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromsTable<T, V>>
        override fun <V : Any> rightJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromsTable<T, V>>
        override fun <V : Any> fullJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromsTable<T, V>>
        override fun `as`(alias: String): Froms<T>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable {
        override fun <U : Any> from(table: Table<U>): FromTable<T, U>
        override fun <U : Any> from(dsl: Scope.() -> Return<U>): From<T>
        override fun from(tsquery: Tsquery): From<T>
        public fun froms(): Froms<T>
    }

    public interface SelectFromable<T : Any> : SqlClientQuery.Select, Fromable<T> {
        override fun `as`(alias: String): Fromable<T>
    }

    public interface FirstSelect<T : Any> : SelectFromable<T>, SqlClientQuery.SelectAndable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): SecondSelect<T?, Long>
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SecondSelect<T?, Float>
        override fun <U : Any> and(dsl: Scope.() -> Return<U>)
                : SecondSelect<T?, U?>

        override fun <U : Any> andCaseWhenExists(
            dsl: SingleScope.() -> Return<U>
        ): AndCaseWhenExistsSecond<T, U>

        override fun `as`(alias: String): FirstSelect<T>
    }

    public interface AndCaseWhenExistsSecond<T : Any, U : Any> : SqlClientQuery.AndCaseWhenExists {
        override fun <V : Any> then(value: V): AndCaseWhenExistsSecondPart2<T, U, V>
    }

    public interface AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any> :
        SqlClientQuery.AndCaseWhenExistsPart2<V> {
        override fun `else`(value: V): SecondSelect<T?, V>
    }

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, SqlClientQuery.Select, SqlClientQuery.SelectAndable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ThirdSelect<T, U, BigDecimal>
        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): ThirdSelect<T, U, Long>
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): ThirdSelect<T, U, Float>
        override fun <V : Any> and(dsl: Scope.() -> Return<V>)
                : ThirdSelect<T, U, V?>

        override fun <V : Any> andCaseWhenExists(dsl: SingleScope.() -> Return<V>)
                : AndCaseWhenExistsThird<T, U, V>

        override fun `as`(alias: String): SecondSelect<T, U>
    }

    public interface AndCaseWhenExistsThird<T, U, V : Any> : SqlClientQuery.AndCaseWhenExists {
        override fun <W : Any> then(value: W): AndCaseWhenExistsThirdPart2<T, U, V, W>
    }

    public interface AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any> : SqlClientQuery.AndCaseWhenExistsPart2<W> {
        override fun `else`(value: W): ThirdSelect<T, U, W>
    }

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, SqlClientQuery.Select,
        SqlClientQuery.SelectAndable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): Select
        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): Select
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select
        override fun <W : Any> and(dsl: Scope.() -> Return<W>): Select
        override fun <W : Any> andCaseWhenExists(
            dsl: SingleScope.() -> Return<W>
        ): AndCaseWhenExistsLast<W>

        override fun `as`(alias: String): ThirdSelect<T, U, V>
    }

    public interface AndCaseWhenExistsLast<T : Any> : SqlClientQuery.AndCaseWhenExists {
        override fun <U : Any> then(value: U): AndCaseWhenExistsLastPart2<T, U>
    }

    public interface AndCaseWhenExistsLastPart2<T : Any, U : Any> : SqlClientQuery.AndCaseWhenExistsPart2<U> {
        override fun `else`(value: U): Select
    }

    public interface Select : Fromable<List<Any?>>, SqlClientQuery.Select, SqlClientQuery.SelectAndable {
        override fun <T : Any> and(column: Column<*, T>): Select
        override fun <T : Any> and(table: Table<T>): Select
        override fun <T : Any> andCount(column: Column<*, T>): Select
        override fun <T : Any> andDistinct(column: Column<*, T>): Select
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): Select
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select
        override fun <T : Any> and(dsl: Scope.() -> Return<T>): Select
        override fun <T : Any> andCaseWhenExists(
            dsl: SingleScope.() -> Return<T>
        ): AndCaseWhenExistsLast<T>

        override fun `as`(alias: String): Select
    }

    public interface Selects : Fromable<List<Any?>>, SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): SelectsPart2
        override fun <T : Any> select(table: Table<T>): SelectsPart2
        override fun <T : Any> selectCount(column: Column<*, T>?): SelectsPart2
        override fun <T : Any> selectDistinct(column: Column<*, T>): SelectsPart2
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SelectsPart2
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SelectsPart2
        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SelectsPart2
        override fun <T : Any> select(dsl: Scope.() -> Return<T>): SelectsPart2
        override fun <T : Any> selectCaseWhenExists(dsl: SingleScope.() -> Return<T>): SelectsCaseWhenExists<T>
    }

    public interface SelectsCaseWhenExists<T : Any> : SqlClientQuery.SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectsCaseWhenExistsPart2<T, U>
    }

    public interface SelectsCaseWhenExistsPart2<T : Any, U : Any> : SqlClientQuery.SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): SelectsPart2
    }

    public interface SelectsPart2 : Selects, SqlClientQuery.Select {
        override fun `as`(alias: String): Selects
    }

    public interface From<T : Any> : SqlClientQuery.From, SqlClientQuery.FromAndable,
        Whereable<T>, GroupableBy<T>, LimitOffset<T>, Return<T> {
        override fun <U : Any> and(table: Table<U>): FromTable<T, U>
        override fun <U : Any> and(dsl: Scope.() -> Return<U>): From<T>
        override fun and(tsquery: Tsquery): From<T>
    }

    public interface FromTable<T : Any, U : Any> : SqlClientQuery.FromTableSelect<U>, From<T>,
        Whereable<T>, GroupableBy<T>, LimitOffset<T>, Return<T> {
        override fun <V : Any> innerJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromTable<T, V>>
        override fun <V : Any> leftJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromTable<T, V>>
        override fun <V : Any> rightJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromTable<T, V>>
        override fun <V : Any> fullJoin(table: Table<V>): SqlClientQuery.Joinable<V, FromTable<T, V>>
    }

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, SqlClientQuery.Andable<Where<T>>,
        SqlClientQuery.Orable<Where<T>>, GroupableBy<T>, LimitOffset<T>, Return<T>

    public interface Wheres<T : Any> : SqlClientQuery.Whereable<Wheres<T>>, SqlClientQuery.Where<Wheres<T>>,
        SqlClientQuery.Orable<Wheres<T>>, GroupableBy<T>, LimitOffset<T>, Return<T>

    public interface GroupableBy<T : Any> : SqlClientQuery.GroupableBy<GroupByAndable<T>> {
        public fun groupsBy() : GroupsBy<T>
    }

    public interface GroupByAndable<T : Any> : SqlClientQuery.GroupByAndable<GroupByAndable<T>>, LimitOffset<T>,
        Return<T>

    public interface GroupsBy<T : Any> : SqlClientQuery.GroupBy<GroupsBy<T>>, SqlClientQuery.GroupableBy<GroupsBy<T>>,
        LimitOffset<T>, Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        public fun sql(parentProperties: DefaultSqlClientCommon.Properties): String
    }
}
