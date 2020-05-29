/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

/**
 * Blocking Sql Client, to be used with any blocking JDBC driver
 */
public abstract class BlockingSqlClient {

    public abstract fun <T : Any> insert(row: T)

    public abstract fun insert(vararg rows: Any)

    public inline fun <reified T : Any> select(noinline dsl: SelectDslApi.(ValueProvider) -> T): BlockingSqlClientSelect.Select<T> = selectInternal(T::class, dsl)

    public inline fun <reified T : Any> select(): BlockingSqlClientSelect.Select<T> = selectInternal(T::class, null)

    public inline fun <reified T : Any> selectAll(): List<T> = selectInternal(T::class, null).fetchAll()

    public inline fun <reified T : Any> countAll(): Long = selectInternal(Long::class) { count<T>() }.fetchOne()

    @PublishedApi
    internal fun <T : Any> selectInternal(resultClass: KClass<T>, dsl: (SelectDslApi.(ValueProvider) -> T)?) =
            select(resultClass, dsl)

    protected abstract fun <T : Any> select(
            resultClass: KClass<T>, dsl: (SelectDslApi.(ValueProvider) -> T)?): BlockingSqlClientSelect.Select<T>

    public inline fun <reified T : Any> createTable(): Unit = createTableInternal(T::class)

    @PublishedApi
    internal fun <T : Any> createTableInternal(tableClass: KClass<T>) = createTable(tableClass)

    protected abstract fun <T : Any> createTable(tableClass: KClass<T>)

    public inline fun <reified T : Any> deleteFromTable(): BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T> = deleteFromTableInternal(T::class)

    public inline fun <reified T : Any> deleteAllFromTable(): Int = deleteFromTableInternal(T::class).execute()

    @PublishedApi
    internal fun <T : Any> deleteFromTableInternal(tableClass: KClass<T>) =
            deleteFromTable(tableClass)

    protected abstract fun <T : Any> deleteFromTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T>

    public inline fun <reified T : Any> updateTable(): BlockingSqlClientDeleteOrUpdate.Update<T> = updateTableInternal(T::class)

    @PublishedApi
    internal fun <T : Any> updateTableInternal(tableClass: KClass<T>) =
            updateTable(tableClass)

    protected abstract fun <T : Any> updateTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.Update<T>
}


public class BlockingSqlClientSelect private constructor() {
    public abstract class Select<T : Any> : BlockingSqlClientSelect.Whereable<T>, BlockingSqlClientSelect.Return<T> {

        public inline fun <reified U : Any> innerJoin(alias: String? = null): BlockingSqlClientSelect.Joinable<T> =
                joinInternal(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal fun <U : Any> joinInternal(joinClass: KClass<U>, alias: String?, type: JoinType) =
                join(joinClass, alias, type)

        protected abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): BlockingSqlClientSelect.Joinable<T>
    }

    public interface Joinable<T : Any> {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): BlockingSqlClientSelect.Join<T>
    }

    public interface Join<T : Any> : BlockingSqlClientSelect.Whereable<T>, BlockingSqlClientSelect.Return<T>

    public interface Whereable<T : Any> {
        public fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T>
    }

    public interface Where<T : Any> : BlockingSqlClientSelect.Return<T> {
        public fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T>
        public fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T>
    }

    public interface Return<T : Any> {
        /**
         * This Query return one result
         *
         * @throws NoResultException if no results
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): T

        /**
         * This Query return one result, or null if no results
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOneOrNull(): T?

        /**
         * This Query return the first result
         *
         * @throws NoResultException if no results
         */
        public fun fetchFirst(): T

        /**
         * This Query return the first result, or null if no results
         */
        public fun fetchFirstOrNull(): T?

        /**
         * This Query can return several results as [List], can be empty if no results
         */
        public fun fetchAll(): List<T>
    }
}


public class BlockingSqlClientDeleteOrUpdate private constructor() {
    public abstract class DeleteOrUpdate<T : Any> : BlockingSqlClientDeleteOrUpdate.Return {

        public inline fun <reified U : Any> innerJoin(alias: String? = null): BlockingSqlClientDeleteOrUpdate.Joinable =
                joinInternal(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal fun <U : Any> joinInternal(joinClass: KClass<U>, alias: String?, type: JoinType) =
                join(joinClass, alias, type)

        protected abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): BlockingSqlClientDeleteOrUpdate.Joinable

        public abstract fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T>
    }

    public abstract class Update<T : Any> : BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T>() {
        public abstract fun set(dsl: (FieldSetter<T>) -> Unit): BlockingSqlClientDeleteOrUpdate.Update<T>
    }

    public interface Joinable {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): BlockingSqlClientDeleteOrUpdate.Join
    }

    public interface Join : BlockingSqlClientDeleteOrUpdate.Return {
        public fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where
    }

    public interface Where : BlockingSqlClientDeleteOrUpdate.Return {
        public fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where
        public fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientDeleteOrUpdate.Where
    }

    public interface TypedWhere<T : Any> : BlockingSqlClientDeleteOrUpdate.Return {
        public fun and(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T>
        public fun or(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): BlockingSqlClientDeleteOrUpdate.TypedWhere<T>
    }

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Int
    }
}
