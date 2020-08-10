/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

/**
 * Reactive (using Reactor Mono and Flux) Sql Client, to be used with R2dbc
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public abstract class ReactorSqlClient {

    public abstract fun <T : Any> insert(row: T): Mono<Void>

    public abstract fun insert(vararg rows: Any): Mono<Void>

    public inline fun <reified T : Any> select(noinline dsl: SelectDslApi.(ValueProvider) -> T)
            : ReactorSqlClientSelect.Select<T> = select(T::class, dsl)

    public inline fun <reified T : Any> select(): ReactorSqlClientSelect.Select<T> = select(T::class, null)

    public inline fun <reified T : Any> selectAll(): Flux<T> = select(T::class, null).fetchAll()

    public inline fun <reified T : Any> countAll(): Mono<Long> = select(Long::class) { count<T>() }.fetchOne()

    @PublishedApi
    internal abstract fun <T : Any> select(resultClass: KClass<T>, dsl: (SelectDslApi.(ValueProvider) -> T)?): ReactorSqlClientSelect.Select<T>

    public inline fun <reified T : Any> createTable(): Mono<Void> = createTable(T::class)

    @PublishedApi
    internal abstract fun <T : Any> createTable(tableClass: KClass<T>): Mono<Void>

    public inline fun <reified T : Any> deleteFromTable(): ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            deleteFromTable(T::class)

    public inline fun <reified T : Any> deleteAllFromTable(): Mono<Int> = deleteFromTable(T::class).execute()

    @PublishedApi
    internal abstract fun <T : Any> deleteFromTable(tableClass: KClass<T>): ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>

    public inline fun <reified T : Any> updateTable(): ReactorSqlClientDeleteOrUpdate.Update<T> = updateTable(T::class)

    @PublishedApi
    internal abstract fun <T : Any> updateTable(tableClass: KClass<T>): ReactorSqlClientDeleteOrUpdate.Update<T>
}



public class ReactorSqlClientSelect private constructor() {

    public abstract class Select<T : Any> : Whereable<T>, Return<T> {
        public inline fun <reified U : Any> innerJoin(alias: String? = null): Joinable<T> =
                join(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): Joinable<T>
    }

    public interface Joinable<T : Any> {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): Join<T>
    }

    public interface Join<T : Any> : Whereable<T>, Return<T>

    public interface Whereable<T : Any> {
        public fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where<T>
    }

    public interface Where<T : Any> : Return<T> {
        public fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where<T>
        public fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where<T>
    }

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

    public abstract class DeleteOrUpdate<T : Any> : Return {
        public inline fun <reified U : Any> innerJoin(alias: String? = null): Joinable =
                join(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): Joinable

        public abstract fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): TypedWhere<T>
    }

    public abstract class Update<T : Any> : DeleteOrUpdate<T>() {
        public abstract fun set(dsl: (FieldSetter<T>) -> Unit): Update<T>
    }

    public interface Joinable {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): Join
    }

    public interface Join : Return {
        public fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
    }

    public interface Where : Return {
        public fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
        public fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
    }

    public interface TypedWhere<T : Any> : Return {
        public fun and(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): TypedWhere<T>
        public fun or(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): TypedWhere<T>
    }

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Mono<Int>
    }
}
