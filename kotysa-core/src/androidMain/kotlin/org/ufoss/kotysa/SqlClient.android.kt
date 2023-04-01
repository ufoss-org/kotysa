/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.math.BigDecimal
import java.util.stream.Stream

/**
 * Sql Client, to be used with a blocking driver
 */
public actual sealed interface SqlClient {

    public actual infix fun <T : Any> insert(row: T)
    public actual fun <T : Any> insert(vararg rows: T)

    public actual infix fun <T : Any> insertAndReturn(row: T): T
    public actual fun <T : Any> insertAndReturn(vararg rows: T): List<T>

    public actual infix fun <T : Any> createTable(table: Table<T>)
    public actual infix fun <T : Any> createTableIfNotExists(table: Table<T>)

    public actual infix fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public actual infix fun <T : Any> deleteAllFrom(table: Table<T>): Int = deleteFrom(table).execute()

    public actual infix fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T>

    public actual infix fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U>
    public actual infix fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T>
    public actual infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T>
    public actual fun selectCount(): SqlClientSelect.Fromable<Long>
    public actual infix fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long>
    public actual infix fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U>
    public actual infix fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U>
    public actual infix fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal>
    public actual infix fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long>
    public actual infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>)
            : SqlClientSelect.FirstSelect<T>

    public actual infix fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T>

    public actual infix fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.From<T>

    public actual infix fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.FromTable<T, T> =
        select(table).from(table)

    public actual infix fun <T : Any> selectCountFrom(table: Table<T>): SqlClientSelect.FromTable<Long, T> =
        selectCount().from(table)

    public actual infix fun <T : Any> selectAllFrom(table: Table<T>): List<T> = selectFrom(table).fetchAll()
    public actual infix fun <T : Any> selectCountAllFrom(table: Table<T>): Long = selectCountFrom(table).fetchOne()!!
}

public actual class SqlClientSelect private actual constructor() : SqlClientQuery() {

    public actual interface Selectable : SelectableFull {
        actual override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        actual override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        actual override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): Fromable<T>
        actual override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        actual override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
        actual override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): FirstSelect<T>
        actual override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): FirstSelect<BigDecimal>
        actual override fun selectSum(column: IntColumn<*>): FirstSelect<Long>
        actual override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): FirstSelect<T>
        actual override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExistsFirst<T>

        actual override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): From<T>
    }

    public actual interface SelectCaseWhenExistsFirst<T : Any> : SelectCaseWhenExists {
        actual override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public actual interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SelectCaseWhenExistsPart2<U> {
        actual override fun `else`(value: U): FirstSelect<U>
    }

    public actual interface Fromable<T : Any> : SqlClientQuery.Fromable, SqlClientQuery.Select {
        actual override fun <U : Any> from(table: Table<U>): FromTable<T, U>
        actual override fun <U : Any> from(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>
    }

    public actual interface FirstSelect<T : Any> : Fromable<T>, SqlClientQuery.Select, Andable {
        actual override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        actual override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        actual override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        actual override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        actual override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        actual override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        actual override fun andSum(column: IntColumn<*>): SecondSelect<T?, Long>
        actual override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>)
                : SecondSelect<T?, U?>

        actual override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): AndCaseWhenExistsSecond<T, U>

        actual override fun `as`(alias: String): FirstSelect<T>
    }

    public actual interface AndCaseWhenExistsSecond<T : Any, U : Any> : AndCaseWhenExists {
        actual override fun <V : Any> then(value: V): AndCaseWhenExistsSecondPart2<T, U, V>
    }

    public actual interface AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any> : AndCaseWhenExistsPart2<V> {
        actual override fun `else`(value: V): SecondSelect<T?, V>
    }

    public actual interface SecondSelect<T, U> : Fromable<Pair<T, U>>, Andable {
        actual override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        actual override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        actual override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        actual override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
        actual override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        actual override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ThirdSelect<T, U, BigDecimal>
        actual override fun andSum(column: IntColumn<*>): ThirdSelect<T, U, Long>
        actual override fun <V : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>)
                : ThirdSelect<T, U, V?>

        actual override fun <V : Any> andCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>)
                : AndCaseWhenExistsThird<T, U, V>

        actual override fun `as`(alias: String): SecondSelect<T, U>
    }

    public actual interface AndCaseWhenExistsThird<T, U, V : Any> : AndCaseWhenExists {
        actual override fun <W : Any> then(value: W): AndCaseWhenExistsThirdPart2<T, U, V, W>
    }

    public actual interface AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any> : AndCaseWhenExistsPart2<W> {
        actual override fun `else`(value: W): ThirdSelect<T, U, W>
    }

    public actual interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, Andable {
        actual override fun <W : Any> and(column: Column<*, W>): Select
        actual override fun <W : Any> and(table: Table<W>): Select
        actual override fun <W : Any> andCount(column: Column<*, W>): Select
        actual override fun <W : Any> andDistinct(column: Column<*, W>): Select
        actual override fun <W : Any> andMin(column: MinMaxColumn<*, W>): Select
        actual override fun <W : Any> andMax(column: MinMaxColumn<*, W>): Select
        actual override fun <W : Any> andAvg(column: NumericColumn<*, W>): Select
        actual override fun andSum(column: IntColumn<*>): Select
        actual override fun <W : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>): Select
        actual override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): AndCaseWhenExistsLast<W>

        actual override fun `as`(alias: String): ThirdSelect<T, U, V>
    }

    public actual interface AndCaseWhenExistsLast<T : Any> : AndCaseWhenExists {
        actual override fun <U : Any> then(value: U): AndCaseWhenExistsLastPart2<T, U>
    }

    public actual interface AndCaseWhenExistsLastPart2<T : Any, U : Any> : AndCaseWhenExistsPart2<U> {
        actual override fun `else`(value: U): Select
    }

    public actual interface Select : Fromable<List<Any?>>, Andable {
        actual override fun <T : Any> and(column: Column<*, T>): Select
        actual override fun <T : Any> and(table: Table<T>): Select
        actual override fun <T : Any> andCount(column: Column<*, T>): Select
        actual override fun <T : Any> andDistinct(column: Column<*, T>): Select
        actual override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        actual override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        actual override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        actual override fun andSum(column: IntColumn<*>): Select
        actual override fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        actual override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): AndCaseWhenExistsLast<T>

        actual override fun `as`(alias: String): Select
    }

    public actual interface From<T : Any> : SqlClientQuery.From<From<T>>, Whereable<Where<T>>, GroupBy<T>, OrderBy<T>,
        LimitOffset<T>, Return<T> {
        actual override fun <U : Any> and(table: Table<U>): FromTable<T, U>
        actual override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>
    }

    public actual interface FromTable<T : Any, U : Any> : SqlClientQuery.FromTable<U, FromTable<T, U>>,
        SqlClientQuery.From<From<T>>, From<T>, Whereable<Where<T>>, GroupBy<T>, OrderBy<T>, LimitOffset<T>, Return<T> {
        actual override fun `as`(alias: String): FromTable<T, U>
    }

    public actual interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, OrderBy<T>, GroupBy<T>, LimitOffset<T>,
        Return<T>

    public actual interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupByPart2<T>>

    public actual interface GroupByPart2<T : Any> : SqlClientQuery.GroupByPart2<GroupByPart2<T>>, OrderBy<T>,
        LimitOffset<T>, Return<T>

    public actual interface OrderBy<T : Any> : SqlClientQuery.OrderBy<OrderByPart2<T>>

    public actual interface OrderByPart2<T : Any> : SqlClientQuery.OrderByPart2<OrderByPart2<T>>, GroupBy<T>,
        LimitOffset<T>, Return<T>

    public actual interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public actual interface Return<T : Any> {
        /**
         * This query returns one result
         *
         * @throws NoResultException if no results
         * @throws NonUniqueResultException if more than one result
         */
        public actual fun fetchOne(): T?

        /**
         * This query returns one result, or null if no results
         *
         * @throws NonUniqueResultException if more than one result
         */
        public actual fun fetchOneOrNull(): T?

        /**
         * This query returns the first result
         *
         * @throws NoResultException if no results
         */
        public actual fun fetchFirst(): T?

        /**
         * This query returns the first result, or null if no results
         */
        public actual fun fetchFirstOrNull(): T?

        /**
         * This query returns several results as [List], can be empty if no results
         */
        public actual fun fetchAll(): List<T>

        /**
         * This query returns several results as [Stream], can be empty if no results
         */
        public fun fetchAllStream(): Stream<T> = fetchAll().stream()
    }
}


public actual class SqlClientDeleteOrUpdate private actual constructor() : SqlClientQuery() {
    public actual interface FirstDeleteOrUpdate<T : Any> : FromTable<T, DeleteOrUpdate<T>>, Whereable<Where<T>>, Return

    public actual interface DeleteOrUpdate<T : Any> : FromTable<T, DeleteOrUpdate<T>>, Whereable<Where<Any>>, Return

    public actual interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>, UpdateInt<T>>

    public actual interface UpdateInt<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.UpdateInt<T, Update<T>, UpdateInt<T>>

    public actual interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, Return

    public actual interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public actual fun execute(): Int
    }
}
