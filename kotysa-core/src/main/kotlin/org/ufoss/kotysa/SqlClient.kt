/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.util.stream.Stream

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

    public infix fun <T : Any, U : Any> select(column: ColumnNotNull<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> select(column: ColumnNullable<T, U>): SqlClientSelect.FirstSelect<U?>
    public infix fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T>

    public infix fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.From<T>

    public infix fun <T : Any> selectAllFrom(table: Table<T>): List<T> = selectFrom(table).fetchAll()

    /*protected abstract fun <T : Any> select(
            resultClass: KClass<T>, dsl: (SelectDslApi.() -> T)?): SqlClientSelect.Select<T>
     */
}


public class SqlClientSelect private constructor() : SqlClientQuery() {
    public interface Selectable : SqlClientQuery.Selectable {
        override fun <T : Any> select(column: ColumnNotNull<*, T>): FirstSelect<T>
        override fun <T : Any> select(column: ColumnNullable<*, T>): FirstSelect<T?>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
    }

    public interface FirstSelect<T> : Fromable<From<T>>, Andable {
        override fun <U : Any> and(column: ColumnNotNull<*, U>): SecondSelect<T, U>
        override fun <U : Any> and(column: ColumnNullable<*, U>): SecondSelect<T, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
    }

    public interface SecondSelect<T, U> : Fromable<From<Pair<T, U>>>, Andable {
        override fun <V : Any> and(column: ColumnNotNull<*, V>): ThirdSelect<T, U, V>
        override fun <V : Any> and(column: ColumnNullable<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
    }

    public interface ThirdSelect<T, U, V> : Fromable<From<Triple<T, U, V>>>, Andable {
        override fun <W : Any> and(column: ColumnNotNull<*, W>): Select
        override fun <W : Any> and(column: ColumnNullable<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
            }

    public interface Select : Fromable<From<List<Any?>>>, Andable

    public interface From<T> : SqlClientQuery.From<From<T>>, Whereable<Any, Where<T>>, Return<T> {

        /*public inline fun <reified U : Any> innerJoin(alias: String? = null): Joinable<T> =
                joinInternal(U::class, alias, JoinType.INNER)

        @PublishedApi
        internal fun <U : Any> joinInternal(joinClass: KClass<U>, alias: String?, type: JoinType) =
                join(joinClass, alias, type)

        protected abstract fun <U : Any> join(joinClass: KClass<U>, alias: String?, type: JoinType): Joinable<T>*/
    }

    /*public interface Joinable<T : Any> {
        public fun on(dsl: (FieldProvider) -> ColumnField<*, *>): Join<T>
    }

    public interface Join<T : Any> : Whereable<T>, Return<T>*/

    public interface Where<T> : SqlClientQuery.Where<Any, Where<T>>, Return<T> {
    }

    public interface Return<T> {
        /**
         * This query return one result
         *
         * @throws NoResultException if no results
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): T

        /**
         * This query return one result, or null if no results
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOneOrNull(): T?

        /**
         * This query return the first result
         *
         * @throws NoResultException if no results
         */
        public fun fetchFirst(): T

        /**
         * This query return the first result, or null if no results
         */
        public fun fetchFirstOrNull(): T?

        /**
         * This query can return several results as [List], can be empty if no results
         */
        public fun fetchAll(): List<T>

        /**
         * This query can return several results as [Stream] (for lazy result iteration), can be empty if no results
         */
        public fun fetchAllStream(): Stream<T> = fetchAll().stream()
    }
}


public class SqlClientDeleteOrUpdate private constructor() : SqlClientQuery() {
    public interface DeleteOrUpdate<T : Any> : From<DeleteOrUpdate<T>>, Whereable<T, Where<T>>, Return {

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

    public interface Where<T : Any> : SqlClientQuery.Where<T, Where<T>>, Return {
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
