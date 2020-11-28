/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * Sql Client, to be used with a blocking driver
 */
public interface SqlClient {

    public infix fun <T : Any> insert(row: T)

    public fun <T : Any> insert(vararg rows: T)

    public infix fun <T : Any> createTable(table: Table<T>)

    public infix fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.DeleteOrUpdate<T>

    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Int = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T>

    /*public fun <T : Any> select(tableOrColumn: TableOrColumn<T>): SqlClientSelect.Select<T>

    public inline fun <reified T : Any> select(): SqlClientSelect.Select<T> = selectInternal(T::class, null)

    public inline fun <reified T : Any> selectAll(): List<T> = selectInternal(T::class, null).fetchAll()

    public inline fun <reified T : Any> countAll(): Long = selectInternal(Long::class) { count<T>() }.fetchOne()

    protected abstract fun <T : Any> select(
            resultClass: KClass<T>, dsl: (SelectDslApi.() -> T)?): SqlClientSelect.Select<T>
     */
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
}*/


public class SqlClientDeleteOrUpdate private constructor(): SqlClientQuery() {
    public interface DeleteOrUpdate<T : Any> : TypedWhereable<T, TypedWhere<T>>, Return {

        /*public fun <U : Any> innerJoin(joinedTable: Table<U>, alias: String? = null): Joinable =
                join(joinedTable, alias, JoinType.INNER)

        protected abstract fun <U : Any> join(joinedTable: Table<U>, alias: String?, type: JoinType): Joinable*/
    }

    public interface Update<T : Any> : DeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>>


    /*public interface Joinable {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): Join
    }

    public interface Join : Return {
        public fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
    }

    public interface Where : Return {
        public fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
        public fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): Where
    }*/

    public interface TypedWhere<T : Any> : SqlClientQuery.TypedWhere<T>, Return {
        /*public fun and(whereClause: WhereClause<T>): TypedWhere<T>
        public fun or(whereClause: WhereClause<T>): TypedWhere<T>*/
    }

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Int
    }
}
