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
        public infix fun set(uuidColumnNotNull: UuidColumnNotNull<T>): UpdateOpColumn<T, U, UUID>
        public infix fun set(uuidColumnNullable: UuidColumnNullable<T>): UpdateOpColumn<T, U, UUID?>
    }

    public interface UpdateOpColumn<T : Any, U : Update<T, U>, V> {
        public infix fun eq(value: V): U
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
        public infix fun `in`(values: Sequence<V>): U = this.`in`(values.toSet())
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
