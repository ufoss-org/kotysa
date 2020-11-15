/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.util.stream.Stream
import kotlin.reflect.KClass

/**
 * Sql Client, to be used with a JDBC driver
 */
public interface SqlClient {

    public fun <T : Any> insert(row: T)

    public fun <T : Any> insert(vararg rows: T)

    public fun <T : Any> createTable(table: Table<T>)

    /*public fun <T : Any> select(tableOrColumn: TableOrColumn<T>): SqlClientSelect.Select<T>

    public inline fun <reified T : Any> select(): SqlClientSelect.Select<T> = selectInternal(T::class, null)

    public inline fun <reified T : Any> selectAll(): List<T> = selectInternal(T::class, null).fetchAll()

    public inline fun <reified T : Any> countAll(): Long = selectInternal(Long::class) { count<T>() }.fetchOne()

    protected abstract fun <T : Any> select(
            resultClass: KClass<T>, dsl: (SelectDslApi.() -> T)?): SqlClientSelect.Select<T>

    public inline fun <reified T : Any> deleteFromTable(): SqlClientDeleteOrUpdate.DeleteOrUpdate<T> = deleteFromTableInternal(T::class)

    public inline fun <reified T : Any> deleteAllFromTable(): Int = deleteFromTableInternal(T::class).execute()

    @PublishedApi
    internal fun <T : Any> deleteFromTableInternal(tableClass: KClass<T>) =
            deleteFromTable(tableClass)

    protected abstract fun <T : Any> deleteFromTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.DeleteOrUpdate<T>

    public inline fun <reified T : Any> updateTable(): SqlClientDeleteOrUpdate.Update<T> = updateTableInternal(T::class)

    @PublishedApi
    internal fun <T : Any> updateTableInternal(tableClass: KClass<T>) =
            updateTable(tableClass)

    protected abstract fun <T : Any> updateTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.Update<T>*/
}

/*
public class SqlClientSelect private constructor() {
    public abstract class Select<T : Any> : Whereable<T>, Return<T> {

        public inline fun <reified U : Any> innerJoin(alias: String? = null): Joinable<T> =
                joinInternal(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal fun <U : Any> joinInternal(joinClass: KClass<U>, alias: String?, type: JoinType) =
                join(joinClass, alias, type)

        protected abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): Joinable<T>
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

        /**
         * This Query can return several results as [Stream] (for lazy result iteration), can be empty if no results
         */
        public fun fetchAllStream(): Stream<T> = fetchAll().stream()
    }
}


public class SqlClientDeleteOrUpdate private constructor() {
    public abstract class DeleteOrUpdate<T : Any> : Return {

        public inline fun <reified U : Any> innerJoin(alias: String? = null): Joinable =
                joinInternal(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal fun <U : Any> joinInternal(joinClass: KClass<U>, alias: String?, type: JoinType) =
                join(joinClass, alias, type)

        protected abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): Joinable

        public abstract fun where(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause): TypedWhere<T>
    }

    public abstract class Update<T : Any> : SqlClientDeleteOrUpdate.DeleteOrUpdate<T>() {
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
        public fun execute(): Int
    }
}*/
