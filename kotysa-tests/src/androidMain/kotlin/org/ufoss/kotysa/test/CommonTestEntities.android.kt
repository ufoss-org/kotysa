/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.*
import java.time.*

actual open class AllTypesNotNullBaseEntity(
    open val id: Int,
    open val string: String,
    open val boolean: Boolean,
    open val localDate: LocalDate,
    open val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    open val localDateTime1: LocalDateTime,
    open val localDateTime2: LocalDateTime,
    open val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    open val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    open val int: Int,
    open val long: Long,
    open val byteArray: ByteArray,
    open val float: Float,
    open val double: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNotNullBaseEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (boolean != other.boolean) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + string.hashCode()
        result = 31 * result + boolean.hashCode()
        result = 31 * result + localDate.hashCode()
        result = 31 * result + kotlinxLocalDate.hashCode()
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + float.hashCode()
        result = 31 * result + double.hashCode()
        return result
    }
}

actual open class AllTypesNullableBaseEntity(
    open val id: Int,
    open val string: String?,
    open val localDate: LocalDate?,
    open val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
    open val localDateTime1: LocalDateTime?,
    open val localDateTime2: LocalDateTime?,
    open val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
    open val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
    open val int: Int?,
    open val long: Long?,
    open val byteArray: ByteArray?,
    open val float: Float?,
    open val double: Double?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNullableBaseEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (string?.hashCode() ?: 0)
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        result = 31 * result + (float?.hashCode() ?: 0)
        result = 31 * result + (double?.hashCode() ?: 0)
        return result
    }
}

actual open class AllTypesNullableDefaultValueBaseEntity(
    open val id: Int,
    open val string: String? = null,
    open val localDate: LocalDate? = null,
    open val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
    open val localDateTime1: LocalDateTime? = null,
    open val localDateTime2: LocalDateTime? = null,
    open val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
    open val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
    open val int: Int? = null,
    open val long: Long? = null,
    open val float: Float? = null,
    open val double: Double? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNullableDefaultValueBaseEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (float != other.float) return false
        if (double != other.double) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (string?.hashCode() ?: 0)
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + (float?.hashCode() ?: 0)
        result = 31 * result + (double?.hashCode() ?: 0)
        return result
    }
}

data class LocalDateEntity(
    val id: Int,
    val localDateNotNull: LocalDate,
    val localDateNullable: LocalDate? = null
)

val localDateWithNullable = LocalDateEntity(1, LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val localDateWithoutNullable = LocalDateEntity(2, LocalDate.of(2019, 11, 6))

interface LocalDates : Table<LocalDateEntity> {
    val id: IntColumnNotNull<LocalDateEntity>
    val localDateNotNull: LocalDateColumnNotNull<LocalDateEntity>
    val localDateNullable: LocalDateColumnNullable<LocalDateEntity>
}

data class LocalDateTimeEntity(
    val id: Int,
    val localDateTimeNotNull: LocalDateTime,
    val localDateTimeNullable: LocalDateTime? = null
)

val localDateTimeWithNullable =
    LocalDateTimeEntity(1, LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val localDateTimeWithoutNullable = LocalDateTimeEntity(2, LocalDateTime.of(2019, 11, 6, 0, 0))

interface LocalDateTimes : Table<LocalDateTimeEntity> {
    val id: IntColumnNotNull<LocalDateTimeEntity>
    val localDateTimeNotNull: LocalDateTimeColumnNotNull<LocalDateTimeEntity>
    val localDateTimeNullable: LocalDateTimeColumnNullable<LocalDateTimeEntity>
}

data class OffsetDateTimeEntity(
    val id: Int,
    val offsetDateTimeNotNull: OffsetDateTime,
    val offsetDateTimeNullable: OffsetDateTime? = null
) {
    /**
     * Overrides equals to use [OffsetDateTime.isEqual] that only compares the instant of the date-time
     * **Note :** For H2 and SqLite this is not required
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OffsetDateTimeEntity

        if (id != other.id) return false
        if (!offsetDateTimeNotNull.isEqual(other.offsetDateTimeNotNull)) return false
        if (offsetDateTimeNullable != null) {
            if (!offsetDateTimeNullable.isEqual(other.offsetDateTimeNullable)) return false
        } else if (other.offsetDateTimeNullable != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + offsetDateTimeNotNull.hashCode()
        result = 31 * result + (offsetDateTimeNullable?.hashCode() ?: 0)
        return result
    }
}

val offsetDateTimeWithNullable = OffsetDateTimeEntity(
    1,
    OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutes(1, 2)
    )
)
val offsetDateTimeWithoutNullable = OffsetDateTimeEntity(
    2,
    OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
)

interface OffsetDateTimes : Table<OffsetDateTimeEntity> {
    val id: IntColumnNotNull<OffsetDateTimeEntity>
    val offsetDateTimeNotNull: OffsetDateTimeColumnNotNull<OffsetDateTimeEntity>
    val offsetDateTimeNullable: OffsetDateTimeColumnNullable<OffsetDateTimeEntity>
}

data class LocalTimeEntity(
    val id: Int,
    val localTimeNotNull: LocalTime,
    val localTimeNullable: LocalTime? = null
)

val localTimeWithNullable = LocalTimeEntity(1, LocalTime.of(12, 4), LocalTime.of(11, 4))
val localTimeWithoutNullable = LocalTimeEntity(2, LocalTime.of(12, 6))

interface LocalTimes : Table<LocalTimeEntity> {
    val id: IntColumnNotNull<LocalTimeEntity>
    val localTimeNotNull: LocalTimeColumnNotNull<LocalTimeEntity>
    val localTimeNullable: LocalTimeColumnNullable<LocalTimeEntity>
}
