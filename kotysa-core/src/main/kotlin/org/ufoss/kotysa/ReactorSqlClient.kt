/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

/**
 * Reactive (using Reactor Mono and Flux) Sql Client, to be used with a R2DBC driver
 *
 * @sample org.ufoss.kotysa.spring.r2dbc.sample.UserRepositorySpringR2dbc
 */
public sealed interface ReactorSqlClient {

    public infix fun <T : Any> insert(row: T): Mono<Void>
    public fun <T : Any> insert(vararg rows: T): Mono<Void>

    public infix fun <T : Any> insertAndReturn(row: T): Mono<T>
    public fun <T : Any> insertAndReturn(vararg rows: T): Flux<T>

    public infix fun <T : Any> createTable(table: Table<T>): Mono<Void>
    public infix fun <T : Any> createTableIfNotExists(table: Table<T>): Mono<Void>

    public infix fun <T : Any> deleteFrom(table: Table<T>): ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Mono<Int> = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): ReactorSqlClientDeleteOrUpdate.Update<T>

    public infix fun <T : Any, U : Any> select(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U>
    public infix fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T>
    public fun selectCount(): ReactorSqlClientSelect.Fromable<Long>
    public infix fun <T : Any> selectCount(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): ReactorSqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): ReactorSqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): ReactorSqlClientSelect.FirstSelect<BigDecimal>
    public infix fun <T : Any> selectSum(column: IntColumn<T>): ReactorSqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>)
            : ReactorSqlClientSelect.FirstSelect<T>

    public infix fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.SelectCaseWhenExistsFirst<T>

    public infix fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): ReactorSqlClientSelect.From<T>

    public infix fun <T : Any> selectFrom(table: Table<T>): ReactorSqlClientSelect.FromTable<T, T> =
            select(table).from(table)
    public infix fun <T : Any> selectCountFrom(table: Table<T>): ReactorSqlClientSelect.FromTable<Long, T> =
            selectCount().from(table)

    public infix fun <T : Any> selectAllFrom(table: Table<T>): Flux<T> = selectFrom(table).fetchAll()
    public infix fun <T : Any> selectCountAllFrom(table: Table<T>): Mono<Long> = selectCountFrom(table).fetchOne()
}

public interface H2ReactorSqlClient : ReactorSqlClient
public interface MysqlReactorSqlClient : ReactorSqlClient
public interface PostgresqlReactorSqlClient : ReactorSqlClient
public interface MssqlReactorSqlClient : ReactorSqlClient
public interface MariadbReactorSqlClient : ReactorSqlClient


public class ReactorSqlClientSelect private constructor() : SqlClientQuery() {

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
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExistsFirst<T>
        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): From<T>
    }

    public interface SelectCaseWhenExistsFirst<T : Any> : SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): FirstSelect<U>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable, SqlClientQuery.Select {
        override fun <U : Any> from(table: Table<U>): FromTable<T, U>
        override fun <U : Any> from(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, SqlClientQuery.Select, Andable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        public override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        override fun andSum(column: IntColumn<*>): SecondSelect<T?, Long>
        override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>)
                : SecondSelect<T?, U?>

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): AndCaseWhenExistsSecond<T, U>

        override fun `as`(alias: String): FirstSelect<T>
    }

    public interface AndCaseWhenExistsSecond<T : Any, U : Any> : AndCaseWhenExists {
        override fun <V : Any> then(value: V): AndCaseWhenExistsSecondPart2<T, U, V>
    }

    public interface AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any> : AndCaseWhenExistsPart2<V> {
        override fun `else`(value: V): SecondSelect<T?, V>
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

        override fun <V : Any> andCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>)
                : AndCaseWhenExistsThird<T, U, V>

        override fun `as`(alias: String): SecondSelect<T, U>
    }

    public interface AndCaseWhenExistsThird<T, U, V : Any> : AndCaseWhenExists {
        override fun <W : Any> then(value: W): AndCaseWhenExistsThirdPart2<T, U, V, W>
    }

    public interface AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any> : AndCaseWhenExistsPart2<W> {
        override fun `else`(value: W): ThirdSelect<T, U, W>
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
        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): AndCaseWhenExistsLast<W>

        override fun `as`(alias: String): ThirdSelect<T, U, V>
    }

    public interface AndCaseWhenExistsLast<T : Any> : AndCaseWhenExists {
        override fun <U : Any> then(value: U): AndCaseWhenExistsLastPart2<T, U>
    }

    public interface AndCaseWhenExistsLastPart2<T : Any, U : Any> : AndCaseWhenExistsPart2<U> {
        override fun `else`(value: U): Select
    }

    public interface Select : Fromable<List<Any?>>, Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        override fun andSum(column: IntColumn<*>): Select
        override fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): AndCaseWhenExistsLast<T>

        override fun `as`(alias: String): Select
    }

    public interface From<T : Any> : SqlClientQuery.From<From<T>>, Whereable<Where<T>>, GroupBy<T>, OrderBy<T>,
        LimitOffset<T>, Return<T> {
        override fun <U : Any> and(table: Table<U>): FromTable<T, U>
        override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>
    }

    public interface FromTable<T : Any, U : Any> : SqlClientQuery.FromTable<U, FromTable<T, U>>,
        SqlClientQuery.From<From<T>>, From<T>, Whereable<Where<T>>, GroupBy<T>, OrderBy<T>, LimitOffset<T>, Return<T> {
        override fun `as`(alias: String): FromTable<T, U>
    }

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, OrderBy<T>, GroupBy<T>, LimitOffset<T>, Return<T>

    public interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupByPart2<T>>

    public interface GroupByPart2<T : Any> : SqlClientQuery.GroupByPart2<GroupByPart2<T>>, OrderBy<T>, LimitOffset<T>,
        Return<T>

    public interface OrderBy<T : Any> : SqlClientQuery.OrderBy<OrderByPart2<T>>

    public interface OrderByPart2<T : Any> : SqlClientQuery.OrderByPart2<OrderByPart2<T>>, GroupBy<T>, LimitOffset<T>,
        Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        /**
         * This query returns one result as [Mono], or an empty [Mono] if no result
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): Mono<T>

        /**
         * This query returns the first result as [Mono], or an empty [Mono] if no result
         */
        public fun fetchFirst(): Mono<T>

        /**
         * This query returns several results as [Flux], or an empty [Flux] if no result
         */
        public fun fetchAll(): Flux<T>
    }
}


public class ReactorSqlClientDeleteOrUpdate private constructor() {

    public interface FirstDeleteOrUpdate<T : Any> : SqlClientQuery.FromTable<T, DeleteOrUpdate<T>>, SqlClientQuery.Whereable<Where<T>>,
        Return

    public interface DeleteOrUpdate<T : Any> : SqlClientQuery.FromTable<T, DeleteOrUpdate<T>>, SqlClientQuery.Whereable<Where<Any>>,
        Return

    public interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>, UpdateInt<T>>

    public interface UpdateInt<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.UpdateInt<T, Update<T>, UpdateInt<T>>

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, Return

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Mono<Int>
    }
}
