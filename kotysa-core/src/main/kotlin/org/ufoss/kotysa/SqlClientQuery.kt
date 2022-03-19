/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

public abstract class SqlClientQuery protected constructor() {

    public interface Selectable {
        public infix fun <T : Any> select(column: Column<*, T>): Select
        public infix fun <T : Any> selectCount(column: Column<*, T>?): Select
        public infix fun <T : Any> selectDistinct(column: Column<*, T>): Select
        public infix fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Select
        public infix fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Select
        public infix fun <T : Any> selectAvg(column: NumericColumn<*, T>): Select
        public infix fun selectSum(column: IntColumn<*>): Select
    }

    public interface SelectableFull : Selectable {
        public infix fun <T : Any> select(table: Table<T>): Select
        public infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): Select

        /**
         * sub-query
         */
        public infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        public infix fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExists
    }

    public interface SelectCaseWhenExists {
        public infix fun <T : Any> then(value: T): SelectCaseWhenExistsPart2<T>
    }

    public interface SelectCaseWhenExistsPart2<T : Any> {
        public infix fun `else`(value: T): Select
    }

    public interface Select {
        public infix fun `as`(alias: String): Select
    }

    public interface Fromable {
        public infix fun <T : Any> from(table: Table<T>): From<T, *>
    }

    public interface AndCaseWhenExists {
        public infix fun <T : Any> then(value: T): AndCaseWhenExistsPart2<T>
    }

    public interface AndCaseWhenExistsPart2<T : Any> {
        public infix fun `else`(value: T): Andable
    }

    public interface Andable {
        public infix fun <T : Any> and(table: Table<T>): Andable
        public infix fun <T : Any> and(column: Column<*, T>): Andable
        public infix fun <T : Any> andCount(column: Column<*, T>): Andable
        public infix fun <T : Any> andDistinct(column: Column<*, T>): Andable
        public infix fun <T : Any> andMin(column: MinMaxColumn<*, T>): Andable
        public infix fun <T : Any> andMax(column: MinMaxColumn<*, T>): Andable
        public infix fun <T : Any> andAvg(column: NumericColumn<*, T>): Andable
        public infix fun andSum(column: IntColumn<*>): Andable

        /**
         * sub-query
         */
        public infix fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Andable
        public infix fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): AndCaseWhenExists
    }

    public interface From<T : Any, U : From<T, U>> {
        public infix fun <V : Any> innerJoin(table: Table<V>): Joinable<T, U, V>
    }

    public interface Joinable<T : Any, U : From<T, U>, V : Any> {
        public infix fun on(column: Column<T, *>): Join<T, U, V>
    }

    public interface Join<T : Any, U : From<T, U>, V : Any> {
        public infix fun eq(column: Column<V, *>): U
    }

    public interface Update<T : Any, U : Update<T, U>> {
        public infix fun set(stringColumnNotNull: StringColumnNotNull<T>): UpdateOpColumn<T, U, String>
        public infix fun set(stringColumnNullable: StringColumnNullable<T>): UpdateOpColumn<T, U, String?>
        public infix fun set(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, U, LocalDateTime>
        public infix fun set(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): UpdateOpColumn<T, U, LocalDateTime?>
        public infix fun set(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun set(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): UpdateOpColumn<T, U, kotlinx.datetime.LocalDateTime?>
        public infix fun set(localDateColumnNotNull: LocalDateColumnNotNull<T>): UpdateOpColumn<T, U, LocalDate>
        public infix fun set(localDateColumnNullable: LocalDateColumnNullable<T>): UpdateOpColumn<T, U, LocalDate?>
        public infix fun set(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): UpdateOpColumn<T, U, kotlinx.datetime.LocalDate>
        public infix fun set(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): UpdateOpColumn<T, U, kotlinx.datetime.LocalDate?>
        public infix fun set(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): UpdateOpColumn<T, U, OffsetDateTime>
        public infix fun set(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): UpdateOpColumn<T, U, OffsetDateTime?>
        public infix fun set(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): UpdateOpColumn<T, U, LocalTime>
        public infix fun set(localTimeColumnNullable: LocalTimeColumnNullable<T>): UpdateOpColumn<T, U, LocalTime?>
        public infix fun set(booleanColumnNotNull: BooleanColumnNotNull<T>): UpdateOpColumn<T, U, Boolean>
        public infix fun set(intColumnNotNull: IntColumnNotNull<T>): UpdateOpColumn<T, U, Int>
        public infix fun set(intColumnNullable: IntColumnNullable<T>): UpdateOpColumn<T, U, Int?>
        public infix fun set(bigIntColumnNotNull: LongColumnNotNull<T>): UpdateOpColumn<T, U, Long>
        public infix fun set(bigIntColumnNullable: LongColumnNullable<T>): UpdateOpColumn<T, U, Long?>
        public infix fun set(uuidColumnNotNull: UuidColumnNotNull<T>): UpdateOpColumn<T, U, UUID>
        public infix fun set(uuidColumnNullable: UuidColumnNullable<T>): UpdateOpColumn<T, U, UUID?>
    }

    public interface UpdateOpColumn<T : Any, U : Update<T, U>, V> {
        public infix fun eq(value: V): U
    }

    public interface Whereable<T : Where<T>> {
        public infix fun <U : Any> where(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T>
        public infix fun <U : Any> where(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T>
        public infix fun <U : Any> where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime>
        public infix fun <U : Any> where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime>
        public infix fun <U : Any> where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> where(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate>
        public infix fun <U : Any> where(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate>
        public infix fun <U : Any> where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime>
        public infix fun <U : Any> where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime>
        public infix fun <U : Any> where(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime>
        public infix fun <U : Any> where(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime>
        public infix fun <U : Any> where(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T>
        public infix fun <U : Any> where(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T>
        public infix fun <U : Any> where(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T>
        public infix fun <U : Any> where(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T>
        public infix fun <U : Any> where(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T>
        public infix fun <U : Any> where(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T>
        public infix fun <U : Any> where(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T>
        public infix fun <U : Any> whereExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T
    }

    public interface WhereInOpColumn<T : Any, U : Where<U>, V : Any> {
        public infix fun `in`(values: Collection<V>): U
        public infix fun `in`(values: Sequence<V>): U = this.`in`(values.toSet())
    }

    public interface WhereOpColumnNotNull<T : Any, U : Where<U>, V : Any> {
        public infix fun eq(value: V): U
        public infix fun notEq(value: V): U
    }

    public interface WhereOpColumnNullable<T : Any, U : Where<U>, V : Any> {
        public infix fun eq(value: V?): U
        public infix fun notEq(value: V?): U
    }

    public interface WhereOpStringColumn<T : Any, U : Where<U>> : WhereInOpColumn<T, U, String> {
        public infix fun contains(value: String): U
        public infix fun startsWith(value: String): U
        public infix fun endsWith(value: String): U
        public infix fun eq(otherStringColumn: StringColumn<*>): U
        public infix fun notEq(otherStringColumn: StringColumn<*>): U
        public infix fun contains(otherStringColumn: StringColumn<*>): U
        public infix fun startsWith(otherStringColumn: StringColumn<*>): U
        public infix fun endsWith(otherStringColumn: StringColumn<*>): U
    }

    public interface WhereOpStringColumnNotNull<T : Any, U : Where<U>> :
        WhereOpStringColumn<T, U>, WhereOpColumnNotNull<T, U, String>

    public interface WhereOpStringColumnNullable<T : Any, U : Where<U>> :
        WhereOpStringColumn<T, U>, WhereOpColumnNullable<T, U, String>

    public interface WhereOpDateColumn<T : Any, U : Where<U>, V : Any> : WhereInOpColumn<T, U, V> {
        public infix fun before(value: V): U
        public infix fun after(value: V): U
        public infix fun beforeOrEq(value: V): U
        public infix fun afterOrEq(value: V): U
        public infix fun eq(otherDateColumn: Column<*, V>): U
        public infix fun notEq(otherDateColumn: Column<*, V>): U
        public infix fun before(otherDateColumn: Column<*, V>): U
        public infix fun after(otherDateColumn: Column<*, V>): U
        public infix fun beforeOrEq(otherDateColumn: Column<*, V>): U
        public infix fun afterOrEq(otherDateColumn: Column<*, V>): U
    }

    public interface WhereOpDateColumnNotNull<T : Any, U : Where<U>, V : Any> :
        WhereOpDateColumn<T, U, V>, WhereOpColumnNotNull<T, U, V>

    public interface WhereOpDateColumnNullable<T : Any, U : Where<U>, V : Any> :
        WhereOpDateColumn<T, U, V>, WhereOpColumnNullable<T, U, V>

    public interface WhereOpBooleanColumnNotNull<T : Any, U : Where<U>> {
        public infix fun eq(value: Boolean): U
        public infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): U
    }

    public interface WhereOpIntColumn<T : Any, U : Where<U>> : WhereInOpColumn<T, U, Int> {
        public infix fun inf(value: Int): U
        public infix fun sup(value: Int): U
        public infix fun infOrEq(value: Int): U
        public infix fun supOrEq(value: Int): U
        public infix fun eq(otherIntColumn: IntColumn<*>): U
        public infix fun notEq(otherIntColumn: IntColumn<*>): U
        public infix fun inf(otherIntColumn: IntColumn<*>): U
        public infix fun sup(otherIntColumn: IntColumn<*>): U
        public infix fun infOrEq(otherIntColumn: IntColumn<*>): U
        public infix fun supOrEq(otherIntColumn: IntColumn<*>): U
    }

    public interface WhereOpIntColumnNotNull<T : Any, U : Where<U>> :
        WhereOpIntColumn<T, U>, WhereOpColumnNotNull<T, U, Int>

    public interface WhereOpIntColumnNullable<T : Any, U : Where<U>> :
        WhereOpIntColumn<T, U>, WhereOpColumnNullable<T, U, Int>

    public interface WhereOpLongColumn<T : Any, U : Where<U>> : WhereInOpColumn<T, U, Long> {
        public infix fun inf(value: Long): U
        public infix fun sup(value: Long): U
        public infix fun infOrEq(value: Long): U
        public infix fun supOrEq(value: Long): U
        public infix fun eq(otherLongColumn: LongColumn<*>): U
        public infix fun notEq(otherLongColumn: LongColumn<*>): U
        public infix fun inf(otherLongColumn: LongColumn<*>): U
        public infix fun sup(otherLongColumn: LongColumn<*>): U
        public infix fun infOrEq(otherLongColumn: LongColumn<*>): U
        public infix fun supOrEq(otherLongColumn: LongColumn<*>): U
    }

    public interface WhereOpLongColumnNotNull<T : Any, U : Where<U>> :
        WhereOpLongColumn<T, U>, WhereOpColumnNotNull<T, U, Long>

    public interface WhereOpLongColumnNullable<T : Any, U : Where<U>> :
        WhereOpLongColumn<T, U>, WhereOpColumnNullable<T, U, Long>

    public interface WhereOpUuidColumn<T : Any, U : Where<U>> : WhereInOpColumn<T, U, UUID> {
        public infix fun eq(otherUuidColumn: UuidColumn<*>): U
        public infix fun notEq(otherUuidColumn: UuidColumn<*>): U
    }

    public interface WhereOpUuidColumnNotNull<T : Any, U : Where<U>> :
        WhereOpUuidColumn<T, U>, WhereOpColumnNotNull<T, U, UUID>

    public interface WhereOpUuidColumnNullable<T : Any, U : Where<U>> :
        WhereOpUuidColumn<T, U>, WhereOpColumnNullable<T, U, UUID>

    public interface Where<T : Where<T>> {
        public infix fun <U : Any> and(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T>
        public infix fun <U : Any> and(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T>
        public infix fun <U : Any> and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime>
        public infix fun <U : Any> and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime>
        public infix fun <U : Any> and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> and(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate>
        public infix fun <U : Any> and(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate>
        public infix fun <U : Any> and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime>
        public infix fun <U : Any> and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime>
        public infix fun <U : Any> and(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime>
        public infix fun <U : Any> and(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime>
        public infix fun <U : Any> and(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T>
        public infix fun <U : Any> and(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T>
        public infix fun <U : Any> and(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T>
        public infix fun <U : Any> and(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T>
        public infix fun <U : Any> and(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T>
        public infix fun <U : Any> and(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T>
        public infix fun <U : Any> and(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T>
        public infix fun <U : Any> andExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T

        public infix fun <U : Any> or(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringColumnNotNull<U, T>
        public infix fun <U : Any> or(stringColumnNullable: StringColumnNullable<U>): WhereOpStringColumnNullable<U, T>
        public infix fun <U : Any> or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDateTime>
        public infix fun <U : Any> or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDateTime>
        public infix fun <U : Any> or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> or(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalDate>
        public infix fun <U : Any> or(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalDate>
        public infix fun <U : Any> or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateColumnNullable<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, OffsetDateTime>
        public infix fun <U : Any> or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, OffsetDateTime>
        public infix fun <U : Any> or(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateColumnNotNull<U, T, LocalTime>
        public infix fun <U : Any> or(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateColumnNullable<U, T, LocalTime>
        public infix fun <U : Any> or(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanColumnNotNull<U, T>
        public infix fun <U : Any> or(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntColumnNotNull<U, T>
        public infix fun <U : Any> or(intColumnNullable: IntColumnNullable<U>): WhereOpIntColumnNullable<U, T>
        public infix fun <U : Any> or(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongColumnNotNull<U, T>
        public infix fun <U : Any> or(longColumnNullable: LongColumnNullable<U>): WhereOpLongColumnNullable<U, T>
        public infix fun <U : Any> or(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidColumnNotNull<U, T>
        public infix fun <U : Any> or(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidColumnNullable<U, T>
        public infix fun <U : Any> orExists(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): T
    }

    public interface LimitOffset<T : LimitOffset<T>> {
        public infix fun limit(limit: Long): T
        public infix fun offset(offset: Long): T
    }

    public interface GroupBy<T : GroupByPart2<T>> {
        public infix fun groupBy(column: Column<*, *>): T
    }

    public interface GroupByPart2<T : GroupByPart2<T>> {
        public infix fun and(column: Column<*, *>): T
        // todo HAVING https://www.dofactory.com/sql/having
    }

    public interface OrderBy<T : OrderByPart2<T>> {
        public infix fun orderByAsc(column: Column<*, *>): T
        public infix fun orderByDesc(column: Column<*, *>): T
    }

    public interface OrderByPart2<T : OrderByPart2<T>> {
        public infix fun andAsc(column: Column<*, *>): T
        public infix fun andDesc(column: Column<*, *>): T
    }
}
