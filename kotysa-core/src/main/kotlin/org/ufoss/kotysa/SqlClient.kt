/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal
import java.util.stream.Stream

/**
 * Sql Client, to be used with a blocking driver
 */
public interface SqlClient {

    public infix fun <T : Any> insert(row: T)
    public fun <T : Any> insert(vararg rows: T)

    public infix fun <T : Any> insertAndReturn(row: T): T
    public fun <T : Any> insertAndReturn(vararg rows: T): List<T>

    public infix fun <T : Any> createTable(table: Table<T>)
    public infix fun <T : Any> createTableIfNotExists(table: Table<T>)

    public infix fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Int = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T>

    public infix fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T>
    public fun selectCount(): SqlClientSelect.Fromable<Long>
    public infix fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal>
    public infix fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>)
            : SqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T>

    public infix fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.From<T, T> =
            select(table).from(table)
    public infix fun <T : Any> selectCountFrom(table: Table<T>): SqlClientSelect.From<Long, T> =
            selectCount().from(table)

    public infix fun <T : Any> selectAllFrom(table: Table<T>): List<T> = selectFrom(table).fetchAll()
    public infix fun <T : Any> selectCountAllFrom(table: Table<T>): Long = selectCountFrom(table).fetchOne()!!
}


public class SqlClientSelect private constructor() : SqlClientQuery() {

    public interface Selectable : SelectableFull {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): Fromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): FirstSelect<BigDecimal>
        override fun selectSum(column: IntColumn<*>): FirstSelect<Long>
        override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): FirstSelect<T>
        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExistsFirst<T>
    }

    public interface SelectCaseWhenExistsFirst<T : Any>: SelectCaseWhenExists {
        public override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SelectCaseWhenExistsPart2<U> {
        public override fun `else`(value: U): FirstSelect<U>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable, SqlClientQuery.Select {
        override fun <U : Any> from(table: Table<U>): From<T, U>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, SqlClientQuery.Select, Andable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        override fun andSum(column: IntColumn<*>): SecondSelect<T?, Long>
        override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>)
                : SecondSelect<T?, U?>

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>
        ): AndCaseWhenExistsSecond<T, U>
        
        override fun `as`(alias: String): FirstSelect<T>
    }

    public interface AndCaseWhenExistsSecond<T : Any, U : Any>: AndCaseWhenExists {
        public override fun <V : Any> then(value: V): AndCaseWhenExistsSecondPart2<T, U, V>
    }

    public interface AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any> : AndCaseWhenExistsPart2<V> {
        public override fun `else`(value: V): SecondSelect<T?, V?>
    }

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, Andable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ThirdSelect<T, U, BigDecimal>
        override fun andSum(column: IntColumn<*>): ThirdSelect<T, U, Long>
        override fun <V : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>)
                : ThirdSelect<T, U, V?>
        override fun `as`(alias: String): SecondSelect<T, U>
    }

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): Select
        override fun andSum(column: IntColumn<*>): Select
        override fun <W : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>): Select
        override fun `as`(alias: String): ThirdSelect<T, U, V>
    }

    public interface Select : Fromable<List<Any?>>, Andable {
        override fun <T : Any> and(column: Column<*, T>): Select
        override fun <T : Any> and(table: Table<T>): Select
        override fun <T : Any> andCount(column: Column<*, T>): Select
        override fun <T : Any> andDistinct(column: Column<*, T>): Select
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        override fun andSum(column: IntColumn<*>): Select
        override fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        override fun `as`(alias: String): Select
    }

    public interface From<T : Any, U : Any> : SqlClientQuery.From<U, From<T, U>>, Whereable<Where<T>>, GroupBy<T>,
        OrderBy<T>, LimitOffset<T>, Return<T> {
        public infix fun <V : Any> and(table: Table<V>): From<T, V>
    }

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, OrderBy<T>, GroupBy<T>, LimitOffset<T>,
            Return<T>

    public interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupByPart2<T>>

    public interface GroupByPart2<T : Any> : SqlClientQuery.GroupByPart2<GroupByPart2<T>>, OrderBy<T>, LimitOffset<T>,
            Return<T>

    public interface OrderBy<T : Any> : SqlClientQuery.OrderBy<OrderByPart2<T>>

    public interface OrderByPart2<T : Any> : SqlClientQuery.OrderByPart2<OrderByPart2<T>>, GroupBy<T>, LimitOffset<T>,
            Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        /**
         * This query returns one result
         *
         * @throws NoResultException if no results
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): T?

        /**
         * This query returns one result, or null if no results
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOneOrNull(): T?

        /**
         * This query returns the first result
         *
         * @throws NoResultException if no results
         */
        public fun fetchFirst(): T?

        /**
         * This query returns the first result, or null if no results
         */
        public fun fetchFirstOrNull(): T?

        /**
         * This query returns several results as [List], can be empty if no results
         */
        public fun fetchAll(): List<T>

        /**
         * This query returns several results as [Stream], can be empty if no results
         */
        public fun fetchAllStream(): Stream<T> = fetchAll().stream()
    }
}


public class SqlClientDeleteOrUpdate private constructor() : SqlClientQuery() {
    public interface FirstDeleteOrUpdate<T : Any> : From<T, DeleteOrUpdate<T>>, Whereable<Where<T>>, Return

    public interface DeleteOrUpdate<T : Any> : From<T, DeleteOrUpdate<T>>, Whereable<Where<Any>>, Return

    public interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>>

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, Return

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Int
    }
}
