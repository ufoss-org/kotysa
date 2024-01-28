/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal

/**
 * Reactive (using Mutiny Uni and Multi) Sql Client, to be used with a Vertx driver
 *
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxMutiny
 */
public sealed interface MutinySqlClient {
    public infix fun <T : Any> insert(row: T): Uni<Void>
    public fun <T : Any> insert(vararg rows: T): Uni<Void>

    public infix fun <T : Any> insertAndReturn(row: T): Uni<T>
    public fun <T : Any> insertAndReturn(vararg rows: T): Multi<T>

    public infix fun <T : Any> createTable(table: Table<T>): Uni<Void>
    public infix fun <T : Any> createTableIfNotExists(table: Table<T>): Uni<Void>

    public infix fun <T : Any> deleteFrom(table: Table<T>): MutinySqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>
    public infix fun <T : Any> deleteAllFrom(table: Table<T>): Uni<Int> = deleteFrom(table).execute()

    public infix fun <T : Any> update(table: Table<T>): MutinySqlClientDeleteOrUpdate.Update<T>

    public infix fun <T : Any, U : Any> select(column: Column<T, U>): MutinySqlClientSelect.FirstSelect<U>
    public infix fun <T : Any> select(table: Table<T>): MutinySqlClientSelect.FirstSelect<T>
    public infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): MutinySqlClientSelect.Fromable<T>
    public fun selectCount(): MutinySqlClientSelect.Fromable<Long>
    public infix fun <T : Any> selectCount(column: Column<*, T>): MutinySqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): MutinySqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): MutinySqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): MutinySqlClientSelect.FirstSelect<U>
    public infix fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): MutinySqlClientSelect.FirstSelect<BigDecimal>
    public infix fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>): MutinySqlClientSelect.FirstSelect<Long>
    public infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>)
            : MutinySqlClientSelect.FirstSelect<T>

    public infix fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): MutinySqlClientSelect.SelectCaseWhenExistsFirst<T>

    // Postgresql specific
    public fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery)
            : MutinySqlClientSelect.FirstSelect<Float> =
        throw UnsupportedOperationException("Only PostgreSQL supports selectTsRankCd")

    public infix fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): MutinySqlClientSelect.From<T>

    public infix fun <T : Any> selectFrom(table: Table<T>): MutinySqlClientSelect.FromTable<T, T> =
        select(table).from(table)

    public infix fun <T : Any> selectCountFrom(table: Table<T>): MutinySqlClientSelect.FromTable<Long, T> =
        selectCount().from(table)

    public infix fun <T : Any> selectAllFrom(table: Table<T>): Uni<List<T>> = selectFrom(table).fetchAll()
    public infix fun <T : Any> selectCountAllFrom(table: Table<T>): Uni<Long> = selectCountFrom(table).fetchOne()

    public fun selects(): MutinySqlClientSelect.Selects
}

public interface MysqlMutinySqlClient : MutinySqlClient
public interface PostgresqlMutinySqlClient : MutinySqlClient, SqlClientQuery.ToTsquery
public interface MssqlMutinySqlClient : MutinySqlClient
public interface MariadbMutinySqlClient : MutinySqlClient
public interface OracleMutinySqlClient : MutinySqlClient


public class MutinySqlClientSelect private constructor() : SqlClientQuery() {

    public interface Selectable : SelectableFull {
        override fun <T : Any> select(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> select(table: Table<T>): FirstSelect<T>
        override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): Fromable<T>
        override fun <T : Any> selectCount(column: Column<*, T>?): FirstSelect<Long>
        override fun <T : Any> selectDistinct(column: Column<*, T>): FirstSelect<T>
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): FirstSelect<T>
        override fun <T : Any> selectAvg(column: NumericColumn<*, T>): FirstSelect<BigDecimal>
        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): FirstSelect<Long>
        override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): FirstSelect<T>
        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExistsFirst<T>

        override fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): From<T>

        override fun selects(): Selects

        // Postgresql specific
        override fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): FirstSelect<Float>
    }

    public interface SelectCaseWhenExistsFirst<T : Any> : SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectCaseWhenExistsFirstPart2<T, U>
    }

    public interface SelectCaseWhenExistsFirstPart2<T : Any, U : Any> : SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): FirstSelect<U>
    }

    public interface Fromable<T : Any> : SqlClientQuery.Fromable {
        override fun <U : Any> from(table: Table<U>): FromTable<T, U>
        override fun <U : Any> from(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>
        public fun froms(): Froms<T>

        // Postgresql specific
        override fun from(tsquery: Tsquery): From<T>
    }

    public interface Whereable<T : Any> : SqlClientQuery.Whereable<Where<T>> {
        public fun wheres(): Wheres<T>
    }

    public interface Froms<T : Any> : SqlClientQuery.Fromable, Whereable<T>, GroupableBy<T>, OrderableBy<T>,
        LimitOffset<T>, Return<T> {
        override fun <U : Any> from(table: Table<U>): FromsTable<T, U>
        override fun <U : Any> from(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): FromsPart2<T>
        override fun from(tsquery: Tsquery): FromsPart2<T>
    }

    public interface FromsPart2<T : Any> : Froms<T>, SqlClientQuery.From {
        override fun `as`(alias: String): Froms<T>
    }

    public interface FromsTable<T : Any, U : Any> : FromsPart2<T>, FromTableSelect<U> {
        override fun <V : Any> innerJoin(table: Table<V>): Joinable<V, FromsTable<T, V>>
        override fun <V : Any> leftJoin(table: Table<V>): Joinable<V, FromsTable<T, V>>
        override fun <V : Any> rightJoin(table: Table<V>): Joinable<V, FromsTable<T, V>>
        override fun <V : Any> fullJoin(table: Table<V>): Joinable<V, FromsTable<T, V>>
    }

    public interface FirstSelect<T : Any> : Fromable<T>, SqlClientQuery.Select, SelectAndable {
        override fun <U : Any> and(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> and(table: Table<U>): SecondSelect<T, U>
        override fun <U : Any> andCount(column: Column<*, U>): SecondSelect<T, Long>
        override fun <U : Any> andDistinct(column: Column<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMin(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andMax(column: MinMaxColumn<*, U>): SecondSelect<T?, U?>
        override fun <U : Any> andAvg(column: NumericColumn<*, U>): SecondSelect<T?, BigDecimal>
        override fun <U : Any> andSum(column: WholeNumberColumn<*, U>): SecondSelect<T?, Long>
        override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>)
                : SecondSelect<T?, U?>

        override fun <U : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): AndCaseWhenExistsSecond<T, U>

        // Postgresql specific
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): SecondSelect<T?, Float>

        override fun `as`(alias: String): FirstSelect<T>
    }

    public interface AndCaseWhenExistsSecond<T : Any, U : Any> : AndCaseWhenExists {
        override fun <V : Any> then(value: V): AndCaseWhenExistsSecondPart2<T, U, V>
    }

    public interface AndCaseWhenExistsSecondPart2<T : Any, U : Any, V : Any> : AndCaseWhenExistsPart2<V> {
        override fun `else`(value: V): SecondSelect<T?, V>
    }

    public interface SecondSelect<T, U> : Fromable<Pair<T, U>>, SqlClientQuery.Select, SelectAndable {
        override fun <V : Any> and(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> and(table: Table<V>): ThirdSelect<T, U, V>
        override fun <V : Any> andCount(column: Column<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> andDistinct(column: Column<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMin(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andMax(column: MinMaxColumn<*, V>): ThirdSelect<T, U, V?>
        override fun <V : Any> andAvg(column: NumericColumn<*, V>): ThirdSelect<T, U, BigDecimal>
        override fun <V : Any> andSum(column: WholeNumberColumn<*, V>): ThirdSelect<T, U, Long>
        override fun <V : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<V>)
                : ThirdSelect<T, U, V?>

        override fun <V : Any> andCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>)
                : AndCaseWhenExistsThird<T, U, V>

        // Postgresql specific
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): ThirdSelect<T, U, Float>

        override fun `as`(alias: String): SecondSelect<T, U>
    }

    public interface AndCaseWhenExistsThird<T, U, V : Any> : AndCaseWhenExists {
        override fun <W : Any> then(value: W): AndCaseWhenExistsThirdPart2<T, U, V, W>
    }

    public interface AndCaseWhenExistsThirdPart2<T, U, V : Any, W : Any> : AndCaseWhenExistsPart2<W> {
        override fun `else`(value: W): ThirdSelect<T, U, W>
    }

    public interface ThirdSelect<T, U, V> : Fromable<Triple<T, U, V>>, SqlClientQuery.Select, SelectAndable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <W : Any> andMin(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andMax(column: MinMaxColumn<*, W>): Select
        override fun <W : Any> andAvg(column: NumericColumn<*, W>): Select
        override fun <W : Any> andSum(column: WholeNumberColumn<*, W>): Select
        override fun <W : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<W>): Select
        override fun <W : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<W>
        ): AndCaseWhenExistsLast<W>

        // Postgresql specific
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select

        override fun `as`(alias: String): ThirdSelect<T, U, V>
    }

    public interface AndCaseWhenExistsLast<T : Any> : AndCaseWhenExists {
        override fun <U : Any> then(value: U): AndCaseWhenExistsLastPart2<T, U>
    }

    public interface AndCaseWhenExistsLastPart2<T : Any, U : Any> : AndCaseWhenExistsPart2<U> {
        override fun `else`(value: U): Select
    }

    public interface Select : Fromable<List<Any?>>, SqlClientQuery.Select, SelectAndable {
        override fun <W : Any> and(column: Column<*, W>): Select
        override fun <W : Any> and(table: Table<W>): Select
        override fun <W : Any> andCount(column: Column<*, W>): Select
        override fun <W : Any> andDistinct(column: Column<*, W>): Select
        override fun <T : Any> andMin(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andMax(column: MinMaxColumn<*, T>): Select
        override fun <T : Any> andAvg(column: NumericColumn<*, T>): Select
        override fun <T : Any> andSum(column: WholeNumberColumn<*, T>): Select
        override fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        override fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): AndCaseWhenExistsLast<T>

        // Postgresql specific
        override fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select

        override fun `as`(alias: String): Select
    }

    public interface Selects : Fromable<List<Any?>>, SqlClientQuery.Selectable {
        override fun <T : Any> select(column: Column<*, T>): SelectsPart2
        override fun <T : Any> select(table: Table<T>): SelectsPart2
        override fun <T : Any> selectCount(column: Column<*, T>?): SelectsPart2
        override fun <T : Any> selectDistinct(column: Column<*, T>): SelectsPart2
        override fun <T : Any> selectMin(column: MinMaxColumn<*, T>): SelectsPart2
        override fun <T : Any> selectMax(column: MinMaxColumn<*, T>): SelectsPart2
        override fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): SelectsPart2
        override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): SelectsPart2
        override fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SelectsCaseWhenExists<T>
    }

    public interface SelectsCaseWhenExists<T : Any> : SelectCaseWhenExists {
        override fun <U : Any> then(value: U): SelectsCaseWhenExistsPart2<T, U>
    }

    public interface SelectsCaseWhenExistsPart2<T : Any, U : Any> : SelectCaseWhenExistsPart2<U> {
        override fun `else`(value: U): SelectsPart2
    }

    public interface SelectsPart2 : Selects, SqlClientQuery.Select {
        override fun `as`(alias: String): Selects
    }

    public interface From<T : Any> : SqlClientQuery.From, FromAndable, Whereable<T>, GroupableBy<T>,
        OrderableBy<T>, LimitOffset<T>, Return<T> {
        override fun `as`(alias: String): From<T>
        override fun <U : Any> and(table: Table<U>): FromTable<T, U>
        override fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): From<T>

        // Postgresql specific
        override fun and(tsquery: Tsquery): From<T>
    }

    public interface FromTable<T : Any, U : Any> : FromTableSelect<U>, From<T>, Whereable<T>, GroupableBy<T>,
        OrderableBy<T>, LimitOffset<T>, Return<T> {
        override fun `as`(alias: String): FromTable<T, U>
        override fun <V : Any> innerJoin(table: Table<V>): Joinable<V, FromTable<T, V>>
        override fun <V : Any> leftJoin(table: Table<V>): Joinable<V, FromTable<T, V>>
        override fun <V : Any> rightJoin(table: Table<V>): Joinable<V, FromTable<T, V>>
        override fun <V : Any> fullJoin(table: Table<V>): Joinable<V, FromTable<T, V>>
    }

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, Andable<Where<T>>, Orable<Where<T>>,
        OrderableBy<T>, GroupableBy<T>, LimitOffset<T>, Return<T>

    public interface Wheres<T : Any> : SqlClientQuery.Whereable<Wheres<T>>, SqlClientQuery.Where<Wheres<T>>,
        Orable<Wheres<T>>, OrderableBy<T>, GroupableBy<T>, LimitOffset<T>,
        Return<T>

    public interface GroupableBy<T : Any> : SqlClientQuery.GroupableBy<GroupBy<T>> {
        public fun groupsBy(): GroupsBy<T>
    }

    public interface GroupBy<T : Any> : SqlClientQuery.GroupBy<GroupBy<T>>, GroupByAndable<GroupBy<T>>,
        OrderableBy<T>, LimitOffset<T>, Return<T>

    public interface GroupsBy<T : Any> : SqlClientQuery.GroupBy<GroupsBy<T>>,
        SqlClientQuery.GroupableBy<GroupsBy<T>>, OrderableBy<T>, LimitOffset<T>,
        Return<T>

    public interface OrderableBy<T : Any> : SqlClientQuery.OrderableBy<OrderBy<T>> {
        public fun ordersBy(): OrdersBy<T>
    }

    public interface OrderBy<T : Any> : SqlClientQuery.OrderBy<OrderBy<T>>, OrderByAndable<OrderBy<T>>, GroupableBy<T>,
        LimitOffset<T>, Return<T>

    public interface OrdersBy<T : Any> : SqlClientQuery.OrderBy<OrdersBy<T>>, SqlClientQuery.OrderableBy<OrdersBy<T>>,
        GroupableBy<T>, LimitOffset<T>, Return<T>

    public interface LimitOffset<T : Any> : SqlClientQuery.LimitOffset<LimitOffset<T>>, Return<T>

    public interface Return<T : Any> {
        /**
         * This query returns one result as [Uni], or an empty [Uni] if no result
         *
         * @throws NonUniqueResultException if more than one result
         */
        public fun fetchOne(): Uni<T>

        /**
         * This query returns the first result as [Uni], or an empty [Uni] if no result
         */
        public fun fetchFirst(): Uni<T>

        /**
         * This query returns several results as Uni<List<T>>
         */
        public fun fetchAll(): Uni<List<T>>
    }
}


public class MutinySqlClientDeleteOrUpdate private constructor() {

    public interface FirstDeleteOrUpdate<T : Any> : SqlClientQuery.FromTable<T>, SqlClientQuery.Whereable<Where<T>>,
        Return {
        override fun <U : Any> innerJoin(table: Table<U>): SqlClientQuery.Joinable<U, DeleteOrUpdate<U>>
    }

    public interface DeleteOrUpdate<T : Any> : SqlClientQuery.FromTable<T>, SqlClientQuery.Whereable<Where<Any>>,
        Return {
        override fun <U : Any> innerJoin(table: Table<U>): SqlClientQuery.Joinable<U, DeleteOrUpdate<U>>
    }

    public interface Update<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.Update<T, Update<T>, UpdateInt<T>>

    public interface UpdateInt<T : Any> : FirstDeleteOrUpdate<T>, SqlClientQuery.UpdateInt<T, Update<T>, UpdateInt<T>>

    public interface Where<T : Any> : SqlClientQuery.Where<Where<T>>, SqlClientQuery.Andable<Where<T>>,
        SqlClientQuery.Orable<Where<T>>, Return

    public interface Return {
        /**
         * Execute delete or update and return the number of updated or deleted rows
         */
        public fun execute(): Uni<Int>
    }
}
