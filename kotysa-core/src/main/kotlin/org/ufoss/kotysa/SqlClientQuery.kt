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

    public interface Update<T : Any, U : Update<T, U>> {
        public operator fun set(column: StringColumnNotNull<T>, value: String): U
        public operator fun set(column: StringColumnNullable<T>, value: String?): U
        public operator fun set(column: LocalDateTimeColumnNotNull<T>, value: LocalDateTime): U
        public operator fun set(column: LocalDateTimeColumnNullable<T>, value: LocalDateTime?): U
        public operator fun set(column: KotlinxLocalDateTimeColumnNotNull<T>, value: kotlinx.datetime.LocalDateTime): U
        public operator fun set(column: KotlinxLocalDateTimeColumnNullable<T>, value: kotlinx.datetime.LocalDateTime?): U
        public operator fun set(column: LocalDateColumnNotNull<T>, value: LocalDate): U
        public operator fun set(column: LocalDateColumnNullable<T>, value: LocalDate?): U
        public operator fun set(column: KotlinxLocalDateColumnNotNull<T>, value: kotlinx.datetime.LocalDate): U
        public operator fun set(column: KotlinxLocalDateColumnNullable<T>, value: kotlinx.datetime.LocalDate?): U
        public operator fun set(column: OffsetDateTimeColumnNotNull<T>, value: OffsetDateTime): U
        public operator fun set(column: OffsetDateTimeColumnNullable<T>, value: OffsetDateTime?): U
        public operator fun set(column: LocalTimeColumnNotNull<T>, value: LocalTime): U
        public operator fun set(column: LocalTimeColumnNullable<T>, value: LocalTime?): U
        public operator fun set(column: BooleanColumnNotNull<T>, value: Boolean): U
        public operator fun set(column: UuidColumnNotNull<T>, value: UUID): U
        public operator fun set(column: UuidColumnNullable<T>, value: UUID?): U
        public operator fun set(column: IntColumnNotNull<T>, value: Int): U
        public operator fun set(column: IntColumnNullable<T>, value: Int?): U
    }

    public interface TypedWhereable<T : Any, U : TypedWhere<T>> {
        public infix fun where(stringColumnNotNull: StringColumnNotNull<T>): TypedWhereOpStringColumnNotNull<T, U>
        public infix fun where(stringColumnNullable: StringColumnNullable<T>): TypedWhereOpStringColumnNullable<T, U>
        public infix fun where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalDateTime>
        public infix fun where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDateTime>
        public infix fun where(localDateColumnNotNull: LocalDateColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalDate>
        public infix fun where(localDateColumnNullable: LocalDateColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalDate>
        public infix fun where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, kotlinx.datetime.LocalDate>
        public infix fun where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, kotlinx.datetime.LocalDate>
        public infix fun where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, OffsetDateTime>
        public infix fun where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, OffsetDateTime>
        public infix fun where(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): TypedWhereOpDateColumnNotNull<T, U, LocalTime>
        public infix fun where(localTimeColumnNullable: LocalTimeColumnNullable<T>): TypedWhereOpDateColumnNullable<T, U, LocalTime>
        public infix fun where(booleanColumnNotNull: BooleanColumnNotNull<T>): TypedWhereOpBooleanColumnNotNull<T, U>
        public infix fun where(intColumnNotNull: IntColumnNotNull<T>): TypedWhereOpIntColumnNotNull<T, U>
        public infix fun where(intColumnNullable: IntColumnNullable<T>): TypedWhereOpIntColumnNullable<T, U>
        public infix fun where(uuidColumnNotNull: UuidColumnNotNull<T>): TypedWhereOpUuidColumnNotNull<T, U>
        public infix fun where(uuidColumnNullable: UuidColumnNullable<T>): TypedWhereOpUuidColumnNullable<T, U>
    }

    public interface TypedWhereInOpColumn<T : Any, U : TypedWhere<T>, V: Any> {
        public infix fun `in`(values: Collection<V>): U
        public infix fun `in`(values: Sequence<V>): U =
                this.`in`(values.toSet())
    }

    public interface TypedWhereOpColumnNotNull<T : Any, U : TypedWhere<T>, V: Any> {
        public infix fun eq(value: V): U
        public infix fun notEq(value: V): U
    }

    public interface TypedWhereOpColumnNullable<T : Any, U : TypedWhere<T>, V: Any> {
        public infix fun eq(value: V?): U
        public infix fun notEq(value: V?): U
    }

    public interface TypedWhereOpStringColumn<T : Any, U : TypedWhere<T>> : TypedWhereInOpColumn<T, U, String> {
        public infix fun contains(value: String): U
        public infix fun startsWith(value: String): U
        public infix fun endsWith(value: String): U
    }

    public interface TypedWhereOpStringColumnNotNull<T : Any, U : TypedWhere<T>> :
            TypedWhereOpStringColumn<T, U>, TypedWhereOpColumnNotNull<T, U, String>

    public interface TypedWhereOpStringColumnNullable<T : Any, U : TypedWhere<T>> :
            TypedWhereOpStringColumn<T, U>, TypedWhereOpColumnNullable<T, U, String>

    public interface TypedWhereOpDateColumn<T : Any, U : TypedWhere<T>, V : Any> : TypedWhereInOpColumn<T, U, V> {
        public infix fun before(value: V): U
        public infix fun after(value: V): U
        public infix fun beforeOrEq(value: V): U
        public infix fun afterOrEq(value: V): U
    }

    public interface TypedWhereOpDateColumnNotNull<T : Any, U : TypedWhere<T>, V : Any> :
            TypedWhereOpDateColumn<T, U, V>, TypedWhereOpColumnNotNull<T, U, V>

    public interface TypedWhereOpDateColumnNullable<T : Any, U : TypedWhere<T>, V : Any> :
            TypedWhereOpDateColumn<T, U, V>, TypedWhereOpColumnNullable<T, U, V>

    public interface TypedWhereOpBooleanColumnNotNull<T : Any, U : TypedWhere<T>> {
        public infix fun eq(value: Boolean): U
    }

    public interface TypedWhereOpIntColumn<T : Any, U : TypedWhere<T>> : TypedWhereInOpColumn<T, U, Int> {
        public infix fun inf(value: Int): U
        public infix fun sup(value: Int): U
        public infix fun infOrEq(value: Int): U
        public infix fun supOrEq(value: Int): U
    }

    public interface TypedWhereOpIntColumnNotNull<T : Any, U : TypedWhere<T>> :
            TypedWhereOpIntColumn<T, U>, TypedWhereOpColumnNotNull<T, U, Int>

    public interface TypedWhereOpIntColumnNullable<T : Any, U : TypedWhere<T>> :
            TypedWhereOpIntColumn<T, U>, TypedWhereOpColumnNullable<T, U, Int>

    public interface TypedWhereOpUuidColumnNotNull<T : Any, U : TypedWhere<T>> :
            TypedWhereOpColumnNotNull<T, U, UUID>, TypedWhereInOpColumn<T, U, UUID>

    public interface TypedWhereOpUuidColumnNullable<T : Any, U : TypedWhere<T>> :
            TypedWhereOpColumnNullable<T, U, UUID>, TypedWhereInOpColumn<T, U, UUID>

    public interface TypedWhere<T : Any>
}
