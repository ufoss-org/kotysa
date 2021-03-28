/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Reactive (using Reactor Mono and Flux) Sql Client, to be used with R2DBC
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public interface ReactorSqlClient {

    public infix fun <T : Any> insert(row: T): Mono<Void>

    public fun <T : Any> insert(vararg rows: T): Mono<Void>

    public infix fun <T : Any> createTable(table: Table<T>): Mono<Void>

    public infix fun <T : Any> deleteFrom(table: Table<T>): ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Mono<Int> = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): ReactorSqlClientDeleteOrUpdate.Update<T>

    public infix fun <T : Any, U : Any> select(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U>
    public infix fun <T : Any> select(table: Table<T>): ReactorSqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> select(dsl: (ValueProvider) -> T): ReactorSqlClientSelect.Fromable<T>
    public fun selectCount(): ReactorSqlClientSelect.Fromable<Long>
    public infix fun <T : Any> selectCount(column: Column<*, T>): ReactorSqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): ReactorSqlClientSelect.FirstSelect<U>

    public infix fun <T : Any> selectFrom(table: Table<T>): ReactorSqlClientSelect.From<T, T> =
            select(table).from(table)
    public infix fun <T : Any> selectCountFrom(table: Table<T>): ReactorSqlClientSelect.From<Long, T> =
            selectCount().from(table)

    public infix fun <T : Any> selectAllFrom(table: Table<T>): Flux<T> = selectFrom(table).fetchAll()
    public infix fun <T : Any> selectCountAllFrom(table: Table<T>): Mono<Long> = selectCountFrom(table).fetchOne()
}


public class ReactorSqlClientSelect private constructor() : SqlClientQuery() {

    public interface Selectable : SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> select(dsl: (ValueProvider) -> T): Fromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable {
        override fun <U : Any> from(table: Table<U>): From<T, U>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, Andable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        public override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
    }

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, Andable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
    }

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
    }

    public interface Select : Fromable<List<Any?>>, Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
    }

    public interface From<T : Any, U : Any> : SqlClientQuery.From<U, From<T, U>>, Whereable<Any, Where<T>>, LimitOffset<T>

    public interface Where<T : Any> : SqlClientQuery.Where<Any, Where<T>>, LimitOffset<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        /**
         * This Query return one result as [Mono], or an empty [Mono] if no result
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): Mono<T>

        /**
         * This Query return one result as [Mono], or an empty [Mono] if no result
         */
        public fun fetchFirst(): Mono<T>

        /**
         * This Query can return several results as [Flux], or an empty [Flux] if no result
         */
        public fun fetchAll(): Flux<T>
    }
}


public class ReactorSqlClientDeleteOrUpdate private constructor() {

    public interface FirstDeleteOrUpdate<T : Any> : SqlClientQuery.From<T, DeleteOrUpdate<T>>, SqlClientQuery.Whereable<T, Where<T>>, Return

    public interface DeleteOrUpdate<T : Any> : SqlClientQuery.From<T, DeleteOrUpdate<T>>, SqlClientQuery.Whereable<Any, Where<Any>>, Return

    public interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>>

    public interface Where<T : Any> : SqlClientQuery.Where<T, Where<T>>, Return

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Mono<Int>
    }
}
