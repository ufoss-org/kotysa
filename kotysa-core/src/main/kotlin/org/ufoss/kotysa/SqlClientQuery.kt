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
        public infix fun <T : Any> select(column: Column<*, T>): Andable
        public infix fun <T : Any> select(table: Table<T>): Andable
        public infix fun <T : Any> select(dsl: (ValueProvider) -> T): Fromable
        public infix fun <T : Any> selectCount(column: Column<*, T>?): Andable
        public infix fun <T : Any> selectDistinct(column: Column<*, T>): Andable
        public infix fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Andable
        public infix fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Andable
        public infix fun <T : Any> selectAvg(column: NumericColumn<*, T>): Andable
        public infix fun selectSum(column: IntColumn<*>): Andable
    }

    public interface Fromable {
        public infix fun <T : Any> from(table: Table<T>): From<T, *>
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

    public interface Whereable<T : Any, U : Where<T, U>> {
        public infix fun where(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U>
        public infix fun where(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U>
        public infix fun where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime>
        public infix fun where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun where(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate>
        public infix fun where(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate>
        public infix fun where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate>
        public infix fun where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate>
        public infix fun where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime>
        public infix fun where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime>
        public infix fun where(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime>
        public infix fun where(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime>
        public infix fun where(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U>
        public infix fun where(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U>
        public infix fun where(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U>
        public infix fun where(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T, U>
        public infix fun where(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T, U>
        public infix fun where(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U>
        public infix fun where(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U>
    }

    public interface WhereInOpColumn<T : Any, U : Where<T, U>, V : Any> {
        public infix fun `in`(values: Collection<V>): U
        public infix fun `in`(values: Sequence<V>): U = this.`in`(values.toSet())
    }

    public interface WhereOpColumnNotNull<T : Any, U : Where<T, U>, V : Any> {
        public infix fun eq(value: V): U
        public infix fun notEq(value: V): U
    }

    public interface WhereOpColumnNullable<T : Any, U : Where<T, U>, V : Any> {
        public infix fun eq(value: V?): U
        public infix fun notEq(value: V?): U
    }

    public interface WhereOpStringColumn<T : Any, U : Where<T, U>> : WhereInOpColumn<T, U, String> {
        public infix fun contains(value: String): U
        public infix fun startsWith(value: String): U
        public infix fun endsWith(value: String): U
    }

    public interface WhereOpStringColumnNotNull<T : Any, U : Where<T, U>> :
            WhereOpStringColumn<T, U>, WhereOpColumnNotNull<T, U, String>

    public interface WhereOpStringColumnNullable<T : Any, U : Where<T, U>> :
            WhereOpStringColumn<T, U>, WhereOpColumnNullable<T, U, String>

    public interface WhereOpDateColumn<T : Any, U : Where<T, U>, V : Any> : WhereInOpColumn<T, U, V> {
        public infix fun before(value: V): U
        public infix fun after(value: V): U
        public infix fun beforeOrEq(value: V): U
        public infix fun afterOrEq(value: V): U
    }

    public interface WhereOpDateColumnNotNull<T : Any, U : Where<T, U>, V : Any> :
            WhereOpDateColumn<T, U, V>, WhereOpColumnNotNull<T, U, V>

    public interface WhereOpDateColumnNullable<T : Any, U : Where<T, U>, V : Any> :
            WhereOpDateColumn<T, U, V>, WhereOpColumnNullable<T, U, V>

    public interface WhereOpBooleanColumnNotNull<T : Any, U : Where<T, U>> {
        public infix fun eq(value: Boolean): U
    }

    public interface WhereOpIntColumn<T : Any, U : Where<T, U>> : WhereInOpColumn<T, U, Int> {
        public infix fun inf(value: Int): U
        public infix fun sup(value: Int): U
        public infix fun infOrEq(value: Int): U
        public infix fun supOrEq(value: Int): U
    }

    public interface WhereOpIntColumnNotNull<T : Any, U : Where<T, U>> :
            WhereOpIntColumn<T, U>, WhereOpColumnNotNull<T, U, Int>

    public interface WhereOpIntColumnNullable<T : Any, U : Where<T, U>> :
            WhereOpIntColumn<T, U>, WhereOpColumnNullable<T, U, Int>

    public interface WhereOpLongColumn<T : Any, U : Where<T, U>> : WhereInOpColumn<T, U, Long> {
        public infix fun inf(value: Long): U
        public infix fun sup(value: Long): U
        public infix fun infOrEq(value: Long): U
        public infix fun supOrEq(value: Long): U
    }

    public interface WhereOpLongColumnNotNull<T : Any, U : Where<T, U>> :
            WhereOpLongColumn<T, U>, WhereOpColumnNotNull<T, U, Long>

    public interface WhereOpLongColumnNullable<T : Any, U : Where<T, U>> :
            WhereOpLongColumn<T, U>, WhereOpColumnNullable<T, U, Long>

    public interface WhereOpUuidColumnNotNull<T : Any, U : Where<T, U>> :
            WhereOpColumnNotNull<T, U, UUID>, WhereInOpColumn<T, U, UUID>

    public interface WhereOpUuidColumnNullable<T : Any, U : Where<T, U>> :
            WhereOpColumnNullable<T, U, UUID>, WhereInOpColumn<T, U, UUID>

    public interface Where<T : Any, U : Where<T, U>> {
        public infix fun and(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U>
        public infix fun and(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U>
        public infix fun and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime>
        public infix fun and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime>
        public infix fun and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun and(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate>
        public infix fun and(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate>
        public infix fun and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate>
        public infix fun and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate>
        public infix fun and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime>
        public infix fun and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime>
        public infix fun and(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime>
        public infix fun and(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime>
        public infix fun and(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U>
        public infix fun and(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U>
        public infix fun and(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U>
        public infix fun and(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T, U>
        public infix fun and(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T, U>
        public infix fun and(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U>
        public infix fun and(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U>

        public infix fun or(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T, U>
        public infix fun or(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T, U>
        public infix fun or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDateTime>
        public infix fun or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDateTime>
        public infix fun or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun or(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalDate>
        public infix fun or(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalDate>
        public infix fun or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate>
        public infix fun or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate>
        public infix fun or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, OffsetDateTime>
        public infix fun or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, OffsetDateTime>
        public infix fun or(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, U, LocalTime>
        public infix fun or(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, U, LocalTime>
        public infix fun or(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T, U>
        public infix fun or(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T, U>
        public infix fun or(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T, U>
        public infix fun or(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T, U>
        public infix fun or(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T, U>
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
