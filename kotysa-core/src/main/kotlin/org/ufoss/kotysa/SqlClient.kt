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

    public infix fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Int = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T>

    public infix fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U>
    public infix fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T>
    public fun selectCount(): SqlClientSelect.Fromable<Long>
    public infix fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long>

    public infix fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.From<T, T> =
            select(table).from(table)
    public infix fun <T : Any> selectCountFrom(table: Table<T>): SqlClientSelect.From<Long, T> =
            selectCount().from(table)

    public infix fun <T : Any> selectAllFrom(table: Table<T>): List<T> = selectFrom(table).fetchAll()
    public infix fun <T : Any> selectCountAllFrom(table: Table<T>): Long = selectCountFrom(table).fetchOne()!!
}


public class SqlClientSelect private constructor() : SqlClientQuery() {

    public interface Selectable : SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> select(dsl: (ValueProvider) -> T): Fromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable {
        override fun <U : Any> from(table: Table<U>): From<T, U>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, Andable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
    }

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, Andable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
    }

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, Andable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
    }

    public interface Select : Fromable<List<Any?>>, Andable

    public interface From<T : Any, U : Any> : SqlClientQuery.From<U, From<T, U>>, Whereable<Any, Where<T>>, LimitOffset<T>

    public interface Where<T : Any> : SqlClientQuery.Where<Any, Where<T>>, LimitOffset<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        /**
         * This query return one result
         *
         * @throws NoResultException if no results
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): T?

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
        public fun fetchFirst(): T?

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
    public interface FirstDeleteOrUpdate<T : Any> : From<T, DeleteOrUpdate<T>>, Whereable<T, Where<T>>, Return

    public interface DeleteOrUpdate<T : Any> : From<T, DeleteOrUpdate<T>>, Whereable<Any, Where<Any>>, Return

    public interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>>

    public interface Where<T : Any> : SqlClientQuery.Where<T, Where<T>>, Return

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Int
    }
}
