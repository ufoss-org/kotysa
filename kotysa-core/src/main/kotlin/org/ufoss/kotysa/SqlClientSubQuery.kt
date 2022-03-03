/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal

public class SqlClientSubQuery private constructor() {

    public interface Scope : SqlClientQuery.Selectable {
        public override infix fun <T : Any> select(column: Column<*, T>): Fromable<T>
        public override infix fun <T : Any> selectCount(column: Column<*, T>?): Fromable<Long>
        public override infix fun <T : Any> selectDistinct(column: Column<*, T>): Fromable<T>
        public override infix fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Fromable<T>
        public override infix fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Fromable<T>
        public override infix fun <T : Any> selectAvg(column: NumericColumn<*, T>): Fromable<BigDecimal>
        public override infix fun selectSum(column: IntColumn<*>): Fromable<Long>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Select, SqlClientQuery.Fromable {
        public override infix fun <U : Any> from(table: Table<U>): From<T, U>
    }

    public interface From<T : Any, U : Any> :
        SqlClientQuery.From<U, From<T, U>>, SqlClientQuery.Whereable<Any, Where<T>>, GroupBy<T>, LimitOffset<T>,
        Return<T> {
        public infix fun <V : Any> and(table: Table<V>): From<T, V>
    }

    public interface Where<T : Any> : SqlClientQuery.Where<Any, Where<T>>, GroupBy<T>, LimitOffset<T>, Return<T>

    public interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupByPart2<T>>

    public interface GroupByPart2<T : Any> : SqlClientQuery.GroupByPart2<GroupByPart2<T>>, LimitOffset<T>, Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        public fun sql(): String
    }
}
