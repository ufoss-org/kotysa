/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*
import java.math.BigDecimal
import java.time.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

const val defaultUuid = "67d4306e-d99d-4e54-8b1d-5b1e92691a4e"

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

open class AllTypesNotNullEntity(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localDateTime1: LocalDateTime,
    override val localDateTime2: LocalDateTime,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    override val int: Int,
    override val long: Long,
    override val byteArray: ByteArray,
    override val float: Float,
    override val double: Double,
    open val bigDecimal1: BigDecimal,
    open val bigDecimal2: BigDecimal,
) : AllTypesNotNullBaseEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNotNullEntity

        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + bigDecimal1.hashCode()
        result = 31 * result + bigDecimal2.hashCode()
        return result
    }
}

val allTypesNotNull = AllTypesNotNullEntity(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    Float.MAX_VALUE,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
)

open class AllTypesNotNullWithTimeEntity(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localDateTime1: LocalDateTime,
    override val localDateTime2: LocalDateTime,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    override val int: Int,
    override val long: Long,
    override val byteArray: ByteArray,
    override val float: Float,
    override val double: Double,
    override val bigDecimal1: BigDecimal,
    override val bigDecimal2: BigDecimal,
    open val localTime: LocalTime,
    open val kotlinxLocalTime: kotlinx.datetime.LocalTime,
) : AllTypesNotNullEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2
) {
    // equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNotNullWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + localTime.hashCode()
        result = 31 * result + kotlinxLocalTime.hashCode()
        return result
    }
}


val allTypesNotNullWithTime = AllTypesNotNullWithTimeEntity(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    Float.MAX_VALUE,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
    LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
)

data class GenericAllTypesNotNullEntity(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localTime: LocalTime,
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime,
    override val localDateTime1: LocalDateTime,
    override val localDateTime2: LocalDateTime,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    override val int: Int,
    override val long: Long,
    override val byteArray: ByteArray,
    override val float: Float,
    override val double: Double,
    override val bigDecimal1: BigDecimal,
    override val bigDecimal2: BigDecimal,
    val offsetDateTime: OffsetDateTime,
    val uuid: UUID,
) : AllTypesNotNullWithTimeEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {

    // Must override equals for various Date truncation, we must ensure the most restricted one = MSSQL
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenericAllTypesNotNullEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime.truncatedTo(ChronoUnit.SECONDS)) return false
        if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (localDateTime1.truncatedTo(ChronoUnit.SECONDS) != other.localDateTime1.truncatedTo(ChronoUnit.SECONDS)) return false
        if (localDateTime2.truncatedTo(ChronoUnit.SECONDS) != other.localDateTime2.truncatedTo(ChronoUnit.SECONDS)) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false
        if (
            !offsetDateTime.truncatedTo(ChronoUnit.MILLIS).isEqual(other.offsetDateTime.truncatedTo(ChronoUnit.MILLIS))
        ) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + offsetDateTime.hashCode()
        result = 31 * result + uuid.hashCode()
        return result
    }
}

val genericAllTypesNotNull = GenericAllTypesNotNullEntity(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    Float.MAX_VALUE,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutes(1, 2)
    ),
    UUID.randomUUID(),
)

object GenericAllTypesNotNulls : GenericTable<GenericAllTypesNotNullEntity>("generic_all_types") {
    val id = integer(GenericAllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar(GenericAllTypesNotNullEntity::string)
    val boolean = boolean(GenericAllTypesNotNullEntity::boolean)
    val localDate = date(GenericAllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(GenericAllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = time(GenericAllTypesNotNullEntity::localTime, precision = 6)
    val kotlinxLocalTim = time(GenericAllTypesNotNullEntity::kotlinxLocalTime, precision = 6)
    val localDateTime1 = timestamp(GenericAllTypesNotNullEntity::localDateTime1, precision = 6)
    val localDateTime2 = timestamp(GenericAllTypesNotNullEntity::localDateTime2, precision = 6)
    val kotlinxLocalDateTime1 = timestamp(GenericAllTypesNotNullEntity::kotlinxLocalDateTime1, precision = 6)
    val kotlinxLocalDateTime2 = timestamp(GenericAllTypesNotNullEntity::kotlinxLocalDateTime2, precision = 6)
    val inte = integer(GenericAllTypesNotNullEntity::int)
    val longe = bigInt(GenericAllTypesNotNullEntity::long)
    val byteArray = binary(GenericAllTypesNotNullEntity::byteArray)
    val float = real(GenericAllTypesNotNullEntity::float)
    val doublee = doublePrecision(GenericAllTypesNotNullEntity::double)
    val bigDecimal1 = numeric(GenericAllTypesNotNullEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(GenericAllTypesNotNullEntity::bigDecimal2, 3, 1)
    val offsetDateTime = timestampWithTimeZone(GenericAllTypesNotNullEntity::offsetDateTime)
    val uuid = uuid(GenericAllTypesNotNullEntity::uuid)
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

open class AllTypesNullableEntity(
    override val id: Int,
    override val string: String?,
    override val localDate: LocalDate?,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
    override val localDateTime1: LocalDateTime?,
    override val localDateTime2: LocalDateTime?,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
    override val int: Int?,
    override val long: Long?,
    override val byteArray: ByteArray?,
    override val float: Float?,
    override val double: Double?,
    open val bigDecimal1: BigDecimal?,
    open val bigDecimal2: BigDecimal?,
) : AllTypesNullableBaseEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableEntity

        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (bigDecimal1?.hashCode() ?: 0)
        result = 31 * result + (bigDecimal2?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullable = AllTypesNullableEntity(
    1, null, null, null, null, null, null,
    null, null, null, null, null, null, null, null
)

open class AllTypesNullableWithTimeEntity(
    override val id: Int,
    override val string: String?,
    override val localDate: LocalDate?,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
    override val localDateTime1: LocalDateTime?,
    override val localDateTime2: LocalDateTime?,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
    override val int: Int?,
    override val long: Long?,
    override val byteArray: ByteArray?,
    override val float: Float?,
    override val double: Double?,
    override val bigDecimal1: BigDecimal?,
    override val bigDecimal2: BigDecimal?,
    open val localTime: LocalTime?,
    open val kotlinxLocalTime: kotlinx.datetime.LocalTime?,
) : AllTypesNullableEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullableWithTime = AllTypesNullableWithTimeEntity(
    1, null, null, null, null, null,
    null, null, null, null, null,
    null, null, null, null, null, null
)

data class GenericAllTypesNullableEntity(
    override val id: Int,
    override val string: String?,
    override val localDate: LocalDate?,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
    override val localTime: LocalTime?,
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime?,
    override val localDateTime1: LocalDateTime?,
    override val localDateTime2: LocalDateTime?,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
    override val int: Int?,
    override val long: Long?,
    override val byteArray: ByteArray?,
    override val float: Float?,
    override val double: Double?,
    override val bigDecimal1: BigDecimal?,
    override val bigDecimal2: BigDecimal?,
    val offsetDateTime: OffsetDateTime?,
    val uuid: UUID?,
) : AllTypesNullableWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as GenericAllTypesNullableEntity

        if (offsetDateTime != other.offsetDateTime) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        return result
    }
}

val genericAllTypesNullable = GenericAllTypesNullableEntity(
    1, null, null, null, null, null, null,
    null, null, null, null, null, null,
    null, null, null, null, null, null
)

object GenericAllTypesNullables : GenericTable<GenericAllTypesNullableEntity>("generic_all_types_nullable") {
    val id = integer(GenericAllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(GenericAllTypesNullableEntity::string)
    val localDate = date(GenericAllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(GenericAllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(GenericAllTypesNullableEntity::localTime)
    val kotlinxLocalTim = time(GenericAllTypesNullableEntity::kotlinxLocalTime)
    val localDateTime1 = timestamp(GenericAllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(GenericAllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(GenericAllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(GenericAllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(GenericAllTypesNullableEntity::int)
    val longe = bigInt(GenericAllTypesNullableEntity::long)
    val byteArray = binary(GenericAllTypesNullableEntity::byteArray)
    val float = real(GenericAllTypesNullableEntity::float)
    val doublee = doublePrecision(GenericAllTypesNullableEntity::double)
    val bigDecimal1 = numeric(GenericAllTypesNullableEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(GenericAllTypesNullableEntity::bigDecimal2, 3, 1)
    val offsetDateTime = timestampWithTimeZone(GenericAllTypesNullableEntity::offsetDateTime)
    val uuid = uuid(GenericAllTypesNullableEntity::uuid)
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

open class AllTypesNullableDefaultValueEntity(
    override val id: Int,
    override val string: String? = null,
    override val localDate: LocalDate? = null,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
    override val localDateTime1: LocalDateTime? = null,
    override val localDateTime2: LocalDateTime? = null,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
    override val int: Int? = null,
    override val long: Long? = null,
    override val float: Float? = null,
    override val double: Double? = null,
    open val bigDecimal1: BigDecimal? = null,
    open val bigDecimal2: BigDecimal? = null,
) : AllTypesNullableDefaultValueBaseEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableDefaultValueEntity

        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (bigDecimal1?.hashCode() ?: 0)
        result = 31 * result + (bigDecimal2?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullableDefaultValue = AllTypesNullableDefaultValueEntity(1)
val allTypesNullableDefaultValueToInsert = AllTypesNullableDefaultValueEntity(2)

open class AllTypesNullableDefaultValueWithTimeEntity(
    override val id: Int,
    override val string: String? = null,
    override val localDate: LocalDate? = null,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
    override val localDateTime1: LocalDateTime? = null,
    override val localDateTime2: LocalDateTime? = null,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
    override val int: Int? = null,
    override val long: Long? = null,
    override val float: Float? = null,
    override val double: Double? = null,
    override val bigDecimal1: BigDecimal? = null,
    override val bigDecimal2: BigDecimal? = null,
    open val localTime: LocalTime? = null,
    open val kotlinxLocalTime: kotlinx.datetime.LocalTime? = null,
) : AllTypesNullableDefaultValueEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double, bigDecimal1, bigDecimal2
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableDefaultValueWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullableDefaultValueWithTime = AllTypesNullableDefaultValueWithTimeEntity(1)
val allTypesNullableDefaultValueWithTimeToInsert = AllTypesNullableDefaultValueWithTimeEntity(2)

data class GenericAllTypesNullableDefaultValueEntity(
    override val id: Int,
    override val string: String? = null,
    override val localDate: LocalDate? = null,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
    override val localTime: LocalTime? = null,
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime? = null,
    override val localDateTime1: LocalDateTime? = null,
    override val localDateTime2: LocalDateTime? = null,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
    override val int: Int? = null,
    override val long: Long? = null,
    override val float: Float? = null,
    override val double: Double? = null,
    override val bigDecimal1: BigDecimal? = null,
    override val bigDecimal2: BigDecimal? = null,
    val offsetDateTime: OffsetDateTime? = null,
    val uuid: UUID? = null,
) : AllTypesNullableDefaultValueWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {

    // Must override equals for various Date truncation, we must ensure the most restricted one = MSSQL
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenericAllTypesNullableDefaultValueEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime != null) {
            if (localTime.truncatedTo(ChronoUnit.SECONDS)
                != other.localTime?.truncatedTo(ChronoUnit.SECONDS)
            ) return false
        } else if (other.localTime != null) {
            return false
        }
        if (kotlinxLocalTime != null) {
            if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
                != other.kotlinxLocalTime?.toJavaLocalTime()?.truncatedTo(ChronoUnit.SECONDS)
            ) return false
        } else if (other.kotlinxLocalTime != null) {
            return false
        }
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        return result
    }
}

val genericAllTypesNullableDefaultValue = GenericAllTypesNullableDefaultValueEntity(1)
val genericAllTypesNullableDefaultValueToInsert = GenericAllTypesNullableDefaultValueEntity(2)

object GenericAllTypesNullableDefaultValues : GenericTable<GenericAllTypesNullableDefaultValueEntity>() {
    val id = integer(GenericAllTypesNullableDefaultValueEntity::id)
        .primaryKey()
    val string = varchar(GenericAllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(
        GenericAllTypesNullableDefaultValueEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
        GenericAllTypesNullableDefaultValueEntity::kotlinxLocalDate,
        defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
    )
    val localTim = time(
        GenericAllTypesNullableDefaultValueEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55, 123456789)
    )
    val kotlinxLocalTim = time(
        GenericAllTypesNullableDefaultValueEntity::kotlinxLocalTime,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55, 123456789)
    )
    val localDateTime1 = timestamp(
        GenericAllTypesNullableDefaultValueEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = timestamp(
        GenericAllTypesNullableDefaultValueEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = timestamp(
        GenericAllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
        defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = timestamp(
        GenericAllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
        defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = integer(GenericAllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = bigInt(GenericAllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val float = real(GenericAllTypesNullableDefaultValueEntity::float, defaultValue = 42.42f)
    val doublee = doublePrecision(GenericAllTypesNullableDefaultValueEntity::double, defaultValue = 84.84)
    val bigDecimal1 = numeric(
        GenericAllTypesNullableDefaultValueEntity::bigDecimal1,
        3,
        1,
        defaultValue = BigDecimal("4.2")
    )
    val bigDecimal2 = decimal(
        GenericAllTypesNullableDefaultValueEntity::bigDecimal2,
        3,
        1,
        defaultValue = BigDecimal("4.3")
    )
    val offsetDateTime = timestampWithTimeZone(
        GenericAllTypesNullableDefaultValueEntity::offsetDateTime,
        defaultValue = OffsetDateTime.of(
            2019, 11, 4, 0, 0, 0, 0,
            ZoneOffset.ofHoursMinutes(1, 2)
        )
    )
    val uuid = uuid(
        GenericAllTypesNullableDefaultValueEntity::uuid,
        defaultValue = UUID.fromString(defaultUuid)
    )
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

data class LocalDateTimeAsTimestampEntity(
    val id: Int,
    val localDateTimeNotNull: LocalDateTime,
    val localDateTimeNullable: LocalDateTime? = null
)

val localDateTimeAsTimestampWithNullable =
    LocalDateTimeAsTimestampEntity(1, LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val localDateTimeAsTimestampWithoutNullable = LocalDateTimeAsTimestampEntity(2, LocalDateTime.of(2019, 11, 6, 0, 0))

interface LocalDateTimeAsTimestamps : Table<LocalDateTimeAsTimestampEntity> {
    val id: IntColumnNotNull<LocalDateTimeAsTimestampEntity>
    val localDateTimeNotNull: LocalDateTimeColumnNotNull<LocalDateTimeAsTimestampEntity>
    val localDateTimeNullable: LocalDateTimeColumnNullable<LocalDateTimeAsTimestampEntity>
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

data class UuidEntity(
    val uuidNotNull: UUID,
    val uuidNullable: UUID? = null,
    val id: UUID = UUID.randomUUID()
)

val uuidWithNullable = UuidEntity(UUID.randomUUID(), UUID.randomUUID())
val uuidWithoutNullable = UuidEntity(UUID.randomUUID())

interface Uuids : Table<UuidEntity> {
    val id: UuidColumnNotNull<UuidEntity>
    val uuidNotNull: UuidColumnNotNull<UuidEntity>
    val uuidNullable: UuidColumnNullable<UuidEntity>
}

data class BigDecimalEntity(
    val id: Int,
    val bigDecimalNotNull: BigDecimal,
    val bigDecimalNullable: BigDecimal? = null,
)

val bigDecimalWithNullable = BigDecimalEntity(1, BigDecimal("1.1"), BigDecimal("2.2"))
val bigDecimalWithoutNullable = BigDecimalEntity(2, BigDecimal("1.3"))

interface BigDecimals : Table<BigDecimalEntity> {
    val id: IntColumnNotNull<BigDecimalEntity>
    val bigDecimalNotNull: BigDecimalColumnNotNull<BigDecimalEntity>
    val bigDecimalNullable: BigDecimalColumnNullable<BigDecimalEntity>
}

data class BigDecimalAsNumericEntity(
    val id: Int,
    val bigDecimalNotNull: BigDecimal,
    val bigDecimalNullable: BigDecimal? = null,
)

val bigDecimalAsNumericWithNullable = BigDecimalAsNumericEntity(1, BigDecimal("1.1"), BigDecimal("2.2"))
val bigDecimalAsNumericWithoutNullable = BigDecimalAsNumericEntity(2, BigDecimal("1.3"))

interface BigDecimalAsNumerics : Table<BigDecimalAsNumericEntity> {
    val id: IntColumnNotNull<BigDecimalAsNumericEntity>
    val bigDecimalNotNull: BigDecimalColumnNotNull<BigDecimalAsNumericEntity>
    val bigDecimalNullable: BigDecimalColumnNullable<BigDecimalAsNumericEntity>
}

open class Inherited(
    private val id: String,
    override val name: String,
    val firstname: String?
) : DummyIntermediary, Entity<String> {

    override fun getId() = id

    // try to bring ambiguity for reflection on name val
    protected fun name() = ""
    internal fun getName() = ""

    @Suppress("UNUSED_PARAMETER")
    fun getName(dummyParam: Boolean) = ""

    // not a data class so needs hashCode & equals functions

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Inherited

        if (name != other.name) return false
        if (firstname != other.firstname) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (firstname?.hashCode() ?: 0)
        return result
    }
}

interface Entities<T : Entity<String>> : Table<T> {
    val id: StringColumnNotNull<T>
}

// test inheritance
val inherited = Inherited("id", "name", "firstname")

interface Inheriteds : Entities<Inherited>, Nameables<Inherited>, Table<Inherited> {
    override val id: StringColumnNotNull<Inherited>
    override val name: StringColumnNotNull<Inherited>
    val firstname: StringColumnNullable<Inherited>
}

val javaJdoe = JavaUser().apply {
    login = "jdoe"
    firstname = "John"
    lastname = "Doe"
    isAdmin = false
}

val javaBboss = JavaUser().apply {
    login = "bboss"
    firstname = "Big"
    lastname = "Boss"
    isAdmin = true
    alias1 = "TheBoss"
    alias2 = "TheBoss"
    alias3 = "TheBoss"
}

interface JavaUsers : Table<JavaUser> {
    val login: StringColumnNotNull<JavaUser>
    val firstname: StringColumnNotNull<JavaUser>
    val lastname: StringColumnNotNull<JavaUser>
    val isAdmin: BooleanColumnNotNull<JavaUser>
    val alias1: StringColumnNullable<JavaUser>
    val alias2: StringColumnNullable<JavaUser>
    val alias3: StringColumnNullable<JavaUser>
}

data class StringAsTinyTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsTinyTextNotNull = StringAsTinyTextEntity(1, "abc", "def")
val stringAsTinyTextNullable = StringAsTinyTextEntity(2, "ghi")

interface TinyTexts : Table<StringAsTinyTextEntity> {
    val id: IntDbIntColumnNotNull<StringAsTinyTextEntity>
    val stringNotNull: StringDbTinyTextColumnNotNull<StringAsTinyTextEntity>
    val stringNullable: StringDbTinyTextColumnNullable<StringAsTinyTextEntity>
}

data class StringAsMediumTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsMediumTextNotNull = StringAsMediumTextEntity(1, "abc", "def")
val stringAsMediumTextNullable = StringAsMediumTextEntity(2, "ghi")

interface MediumTexts : Table<StringAsMediumTextEntity> {
    val id: IntDbIntColumnNotNull<StringAsMediumTextEntity>
    val stringNotNull: StringDbMediumTextColumnNotNull<StringAsMediumTextEntity>
    val stringNullable: StringDbMediumTextColumnNullable<StringAsMediumTextEntity>
}

data class StringAsLongTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsLongTextNotNull = StringAsLongTextEntity(1, "abc", "def")
val stringAsLongTextNullable = StringAsLongTextEntity(2, "ghi")

interface LongTexts : Table<StringAsLongTextEntity> {
    val id: IntDbIntColumnNotNull<StringAsLongTextEntity>
    val stringNotNull: StringDbLongTextColumnNotNull<StringAsLongTextEntity>
    val stringNullable: StringDbLongTextColumnNullable<StringAsLongTextEntity>
}
