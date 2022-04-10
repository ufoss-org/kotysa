/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal

public class SqlClientSubQuery private constructor() {

    public interface SingleScope : SqlClientQuery.SelectableSingle {
        override fun <T : Any> select(column: Column<*, T>): Fromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): Fromable<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): Fromable<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Fromable<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Fromable<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): Fromable<BigDecimal>
        override fun selectSum(column: IntColumn<*>): Fromable<Long>
    }

    public interface Scope : SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): FirstSelect<BigDecimal>
        override fun selectSum(column: IntColumn<*>): FirstSelect<Long>
        override fun <T : Any> selectCaseWhenExists(
            dsl: SingleScope.() -> Return<T>
        ): SelectCaseWhenExistsFirst<T>
        override fun <T : Any> selectStarFromSubQuery(
            dsl: Scope.() -> Return<T>
        ): From<T>
    }

    public interface SelectCaseWhenExistsFirst<T : Any> : SqlClientQuery.SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SqlClientQuery.SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): FirstSelect<U>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Select, SqlClientQuery.Fromable {
        override fun <U : Any> from(table: Table<U>): FromTable<T, U>
        override fun <U : Any> from(dsl: SingleScope.() -> Return<U>): From<T>

        override fun `as`(alias: String): Fromable<T>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, SqlClientQuery.Select, SqlClientQuery.Andable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        override fun andSum(column: IntColumn<*>): SecondSelect<T?, Long>
        override fun <U : Any> and(dsl: SingleScope.() -> Return<U>)
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

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, SqlClientQuery.Andable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ThirdSelect<T, U, BigDecimal>
        override fun andSum(column: IntColumn<*>): ThirdSelect<T, U, Long>
        override fun <V : Any> and(dsl: SingleScope.() -> Return<V>)
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

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, SqlClientQuery.Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): Select
        override fun andSum(column: IntColumn<*>): Select
        override fun <W : Any> and(dsl: SingleScope.() -> Return<W>): Select
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

    public interface Select : Fromable<List<Any?>>, SqlClientQuery.Andable {
        override fun <T : Any> and(column: Column<*, T>): Select
        override fun <T : Any> and(table: Table<T>): Select
        override fun <T : Any> andCount(column: Column<*, T>): Select
        override fun <T : Any> andDistinct(column: Column<*, T>): Select
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        override fun andSum(column: IntColumn<*>): Select
        override fun <T : Any> and(dsl: SingleScope.() -> Return<T>): Select
        override fun <T : Any> andCaseWhenExists(
            dsl: SingleScope.() -> Return<T>
        ): AndCaseWhenExistsLast<T>

        override fun `as`(alias: String): Select
    }

    public interface From<T : Any> : SqlClientQuery.From<From<T>>, SqlClientQuery.Whereable<Where<T>>, GroupBy<T>,
        LimitOffset<T>, Return<T> {
        override fun <U : Any> and(table: Table<U>): FromTable<T, U>
        override fun <U : Any> and(dsl: SingleScope.() -> Return<U>): From<T>
    }

    public interface FromTable<T : Any, U : Any> : SqlClientQuery.FromTable<U, FromTable<T, U>>,
        SqlClientQuery.From<From<T>>, From<T>, SqlClientQuery.Whereable<Where<T>>, GroupBy<T>, LimitOffset<T>, Return<T>

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, GroupBy<T>, LimitOffset<T>, Return<T>

    public interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupByPart2<T>>

    public interface GroupByPart2<T : Any> : SqlClientQuery.GroupByPart2<GroupByPart2<T>>, LimitOffset<T>, Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        public fun sql(): String
    }
}
