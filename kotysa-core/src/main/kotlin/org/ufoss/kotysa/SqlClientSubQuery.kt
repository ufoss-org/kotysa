/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

public class SqlClientSubQuery private constructor() {

    public interface Scope {
        public infix fun <T : Any> select(column: Column<*, T>): Fromable
        public infix fun <T : Any> select(table: Table<T>): Fromable
        public infix fun <T : Any> select(dsl: (ValueProvider) -> T): Fromable
        public infix fun <T : Any> selectCount(column: Column<*, T>?): Fromable
        public infix fun <T : Any> selectDistinct(column: Column<*, T>): Fromable
        public infix fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Fromable
        public infix fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Fromable
        public infix fun <T : Any> selectAvg(column: NumericColumn<*, T>): Fromable
        public infix fun selectSum(column: IntColumn<*>): Fromable
    }

    public interface Fromable {
        public infix fun <T : Any> from(table: Table<T>): From<T>
    }

    public interface From<T : Any> {
        public infix fun <U : Any> innerJoin(table: Table<U>): Joinable<T, U>
        public infix fun <U : Any> and(table: Table<U>): From<U>
    }

    public interface Joinable<T : Any, U : Any> {
        public infix fun on(column: Column<T, *>): Join<T, U>
    }

    public interface Join<T : Any, U : Any> {
        public infix fun eq(column: Column<U, *>): From<T>
    }

    public interface Whereable<T : Any> {
        public infix fun where(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T>
        public infix fun where(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T>
        public infix fun where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDateTime>
        public infix fun where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDateTime>
        public infix fun where(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDate>
        public infix fun where(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDate>
        public infix fun where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDate>
        public infix fun where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDate>
        public infix fun where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, OffsetDateTime>
        public infix fun where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, OffsetDateTime>
        public infix fun where(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalTime>
        public infix fun where(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalTime>
        public infix fun where(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T>
        public infix fun where(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T>
        public infix fun where(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T>
        public infix fun where(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T>
        public infix fun where(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T>
        public infix fun where(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T>
        public infix fun where(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T>
    }

    public interface WhereInOpColumn<T : Any, U : Any> {
        public infix fun `in`(values: Collection<U>): Where<T>
        public infix fun `in`(values: Sequence<U>): Where<T> = this.`in`(values.toSet())
    }

    public interface WhereOpColumnNotNull<T : Any, U : Any> {
        public infix fun eq(value: U): Where<T>
        public infix fun notEq(value: U): Where<T>
    }

    public interface WhereOpColumnNullable<T : Any, U : Any> {
        public infix fun eq(value: U?): Where<T>
        public infix fun notEq(value: U?): Where<T>
    }

    public interface WhereOpStringColumn<T : Any> : WhereInOpColumn<T, String> {
        public infix fun contains(value: String): Where<T>
        public infix fun startsWith(value: String): Where<T>
        public infix fun endsWith(value: String): Where<T>
        public infix fun eq(otherStringColumn: StringColumn<*>): Where<T>
        public infix fun notEq(otherStringColumn: StringColumn<*>): Where<T>
        public infix fun contains(otherStringColumn: StringColumn<*>): Where<T>
        public infix fun startsWith(otherStringColumn: StringColumn<*>): Where<T>
        public infix fun endsWith(otherStringColumn: StringColumn<*>): Where<T>
    }

    public interface WhereOpStringColumnNotNull<T : Any> : WhereOpStringColumn<T>, WhereOpColumnNotNull<T, String>

    public interface WhereOpStringColumnNullable<T : Any> : WhereOpStringColumn<T>, WhereOpColumnNullable<T, String>

    public interface WhereOpDateColumn<T : Any, U : Any> : WhereInOpColumn<T, U> {
        public infix fun before(value: U): Where<T>
        public infix fun after(value: U): Where<T>
        public infix fun beforeOrEq(value: U): Where<T>
        public infix fun afterOrEq(value: U): Where<T>
        public infix fun eq(otherDateColumn: Column<*, U>): Where<T>
        public infix fun notEq(otherDateColumn: Column<*, U>): Where<T>
        public infix fun before(otherDateColumn: Column<*, U>): Where<T>
        public infix fun after(otherDateColumn: Column<*, U>): Where<T>
        public infix fun beforeOrEq(otherDateColumn: Column<*, U>): Where<T>
        public infix fun afterOrEq(otherDateColumn: Column<*, U>): Where<T>
    }

    public interface WhereOpDateColumnNotNull<T : Any, U : Any> : WhereOpDateColumn<T, U>, WhereOpColumnNotNull<T, U>

    public interface WhereOpDateColumnNullable<T : Any, U : Any> : WhereOpDateColumn<T, U>, WhereOpColumnNullable<T, U>

    public interface WhereOpBooleanColumnNotNull<T : Any> {
        public infix fun eq(value: Boolean): Where<T>
        public infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): Where<T>
    }

    public interface WhereOpIntColumn<T : Any> : WhereInOpColumn<T, Int> {
        public infix fun inf(value: Int): Where<T>
        public infix fun sup(value: Int): Where<T>
        public infix fun infOrEq(value: Int): Where<T>
        public infix fun supOrEq(value: Int): Where<T>
        public infix fun eq(otherIntColumn: IntColumn<*>): Where<T>
        public infix fun notEq(otherIntColumn: IntColumn<*>): Where<T>
        public infix fun inf(otherIntColumn: IntColumn<*>): Where<T>
        public infix fun sup(otherIntColumn: IntColumn<*>): Where<T>
        public infix fun infOrEq(otherIntColumn: IntColumn<*>): Where<T>
        public infix fun supOrEq(otherIntColumn: IntColumn<*>): Where<T>
    }

    public interface WhereOpIntColumnNotNull<T : Any> : WhereOpIntColumn<T>, WhereOpColumnNotNull<T, Int>

    public interface WhereOpIntColumnNullable<T : Any> : WhereOpIntColumn<T>, WhereOpColumnNullable<T, Int>

    public interface WhereOpLongColumn<T : Any> : WhereInOpColumn<T, Long> {
        public infix fun inf(value: Long): Where<T>
        public infix fun sup(value: Long): Where<T>
        public infix fun infOrEq(value: Long): Where<T>
        public infix fun supOrEq(value: Long): Where<T>
        public infix fun eq(otherLongColumn: LongColumn<*>): Where<T>
        public infix fun notEq(otherLongColumn: LongColumn<*>): Where<T>
        public infix fun inf(otherLongColumn: LongColumn<*>): Where<T>
        public infix fun sup(otherLongColumn: LongColumn<*>): Where<T>
        public infix fun infOrEq(otherLongColumn: LongColumn<*>): Where<T>
        public infix fun supOrEq(otherLongColumn: LongColumn<*>): Where<T>
    }

    public interface WhereOpLongColumnNotNull<T : Any, U : Where<T, U>> :
        WhereOpLongColumn<T, U>, WhereOpColumnNotNull<T, U, Long>

    public interface WhereOpLongColumnNullable<T : Any, U : Where<T, U>> :
        WhereOpLongColumn<T, U>, WhereOpColumnNullable<T, U, Long>

    public interface WhereOpUuidColumn<T : Any> : WhereInOpColumn<T, UUID> {
        public infix fun eq(otherUuidColumn: UuidColumn<*>): Where<T>
        public infix fun notEq(otherUuidColumn: UuidColumn<*>): Where<T>
    }

    public interface WhereOpUuidColumnNotNull<T : Any, U : Where<T, U>> :
        WhereOpUuidColumn<T, U>, WhereOpColumnNotNull<T, U, UUID>

    public interface WhereOpUuidColumnNullable<T : Any> :
        WhereOpUuidColumn<T>, WhereOpColumnNullable<T, UUID>

    public interface Where<T : Any> {
        public infix fun and(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T>
        public infix fun and(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T>
        public infix fun and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDateTime>
        public infix fun and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDateTime>
        public infix fun and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDateTime>
        public infix fun and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDateTime>
        public infix fun and(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDate>
        public infix fun and(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDate>
        public infix fun and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDate>
        public infix fun and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDate>
        public infix fun and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, OffsetDateTime>
        public infix fun and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, OffsetDateTime>
        public infix fun and(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalTime>
        public infix fun and(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalTime>
        public infix fun and(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T>
        public infix fun and(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T>
        public infix fun and(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T>
        public infix fun and(longColumnNotNull: LongColumnNotNull<T>): WhereOpLongColumnNotNull<T>
        public infix fun and(longColumnNullable: LongColumnNullable<T>): WhereOpLongColumnNullable<T>
        public infix fun and(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T>
        public infix fun and(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T>

        public infix fun or(stringColumnNotNull: StringColumnNotNull<T>): WhereOpStringColumnNotNull<T>
        public infix fun or(stringColumnNullable: StringColumnNullable<T>): WhereOpStringColumnNullable<T>
        public infix fun or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDateTime>
        public infix fun or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDateTime>
        public infix fun or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDateTime>
        public infix fun or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDateTime>
        public infix fun or(localDateColumnNotNull: LocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalDate>
        public infix fun or(localDateColumnNullable: LocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, LocalDate>
        public infix fun or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): WhereOpDateColumnNotNull<T, kotlinx.datetime.LocalDate>
        public infix fun or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): WhereOpDateColumnNullable<T, kotlinx.datetime.LocalDate>
        public infix fun or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, OffsetDateTime>
        public infix fun or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): WhereOpDateColumnNullable<T, OffsetDateTime>
        public infix fun or(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): WhereOpDateColumnNotNull<T, LocalTime>
        public infix fun or(localTimeColumnNullable: LocalTimeColumnNullable<T>): WhereOpDateColumnNullable<T, LocalTime>
        public infix fun or(booleanColumnNotNull: BooleanColumnNotNull<T>): WhereOpBooleanColumnNotNull<T>
        public infix fun or(intColumnNotNull: IntColumnNotNull<T>): WhereOpIntColumnNotNull<T>
        public infix fun or(intColumnNullable: IntColumnNullable<T>): WhereOpIntColumnNullable<T>
        public infix fun or(uuidColumnNotNull: UuidColumnNotNull<T>): WhereOpUuidColumnNotNull<T>
        public infix fun or(uuidColumnNullable: UuidColumnNullable<T>): WhereOpUuidColumnNullable<T>
    }
}
