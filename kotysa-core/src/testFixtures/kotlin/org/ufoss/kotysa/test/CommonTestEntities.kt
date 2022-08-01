/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.ufoss.kotysa.BooleanColumnNotNull
import org.ufoss.kotysa.StringColumnNotNull
import org.ufoss.kotysa.StringColumnNullable
import org.ufoss.kotysa.Table
import java.time.*
import java.util.*

const val defaultUuid = "67d4306e-d99d-4e54-8b1d-5b1e92691a4e"

data class RoleEntity(
        val id: Int,
        val label: String,
)

val roleUser = RoleEntity(1, "user")
val roleAdmin = RoleEntity(2, "admin")
val roleGod = RoleEntity(3, "god")
val roleGodBis = RoleEntity(4, "god")


data class UserEntity(
        val id: Int,
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: Int,
        val alias: String? = null,
)

val userJdoe = UserEntity(1, "John", "Doe", false, roleUser.id)
val userBboss = UserEntity(2, "Big", "Boss", true, roleAdmin.id, "TheBoss")

data class UserRoleEntity(
        val userId: Int,
        val roleId: Int,
)

val userRoleBboss = UserRoleEntity(2, 2)

open class AllTypesNotNullEntity(
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
) {
    // equals, hashcode and toString (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNotNullEntity

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
        return result
    }

    override fun toString(): String {
        return "AllTypesNotNullEntity(id=$id, string='$string', boolean=$boolean, localDate=$localDate, " +
                "kotlinxLocalDate=$kotlinxLocalDate, localDateTime1=$localDateTime1, localDateTime2=$localDateTime2, " +
                "kotlinxLocalDateTime1=$kotlinxLocalDateTime1, kotlinxLocalDateTime2=$kotlinxLocalDateTime2, " +
                "int=$int, long=$long, byteArray=${byteArray.contentToString()})"
    }
}

val allTypesNotNull = AllTypesNotNullEntity(1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalDateTime.now(), LocalDateTime.now(),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Int.MAX_VALUE, Long.MAX_VALUE, byteArrayOf(0x2A))

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
        open val localTime: LocalTime,
) : AllTypesNotNullEntity(
        id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
        kotlinxLocalDateTime2, int, long, byteArray
) {
    // equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNotNullWithTimeEntity

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
        if (localTime != other.localTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
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
        result = 31 * result + localTime.hashCode()
        return result
    }
}


val allTypesNotNullWithTime = AllTypesNotNullWithTimeEntity(1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalDateTime.now(), LocalDateTime.now(),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Int.MAX_VALUE, Long.MAX_VALUE, byteArrayOf(0x2A), LocalTime.now())


open class AllTypesNullableEntity(
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
) {
    // just base equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNullableEntity

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
        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false

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
        return result
    }
}

val allTypesNullable = AllTypesNullableEntity(1, null, null, null,
        null, null, null, null, null,
        null, null)

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
        open val localTime: LocalTime?,
) : AllTypesNullableEntity(
        id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
        kotlinxLocalDateTime2, int, long, byteArray
) {
    // equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableWithTimeEntity

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
        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (localTime != other.localTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
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
        result = 31 * result + (localTime?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullableWithTime = AllTypesNullableWithTimeEntity(1, null, null, null,
        null, null, null, null, null,
        null, null, null)


open class AllTypesNullableDefaultValueEntity(
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
) {
    // just base equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNullableDefaultValueEntity

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
        return result
    }
}

val allTypesNullableDefaultValue = AllTypesNullableDefaultValueEntity(1)

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
        open val localTime: LocalTime? = null,
) : AllTypesNullableDefaultValueEntity(
        id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long
) {
    // just base equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AllTypesNullableDefaultValueWithTimeEntity

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
        if (localTime != other.localTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
        result = 31 * result + (string?.hashCode() ?: 0)
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + (localTime?.hashCode() ?: 0)
        return result
    }
}

val allTypesNullableDefaultValueWithTime = AllTypesNullableDefaultValueWithTimeEntity(1)


data class LocalDateEntity(
        val id: Int,
        val localDateNotNull: LocalDate,
        val localDateNullable: LocalDate? = null
)

val localDateWithNullable = LocalDateEntity(1, LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val localDateWithoutNullable = LocalDateEntity(2, LocalDate.of(2019, 11, 6))

data class KotlinxLocalDateEntity(
        val id: Int,
        val localDateNotNull: kotlinx.datetime.LocalDate,
        val localDateNullable: kotlinx.datetime.LocalDate? = null
)

val kotlinxLocalDateWithNullable = KotlinxLocalDateEntity(1, kotlinx.datetime.LocalDate(2019, 11, 4),
        kotlinx.datetime.LocalDate(2018, 11, 4))
val kotlinxLocalDateWithoutNullable = KotlinxLocalDateEntity(2, kotlinx.datetime.LocalDate(2019, 11, 6))


data class LocalDateTimeEntity(
        val id: Int,
        val localDateTimeNotNull: LocalDateTime,
        val localDateTimeNullable: LocalDateTime? = null
)

val localDateTimeWithNullable = LocalDateTimeEntity(1, LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val localDateTimeWithoutNullable = LocalDateTimeEntity(2, LocalDateTime.of(2019, 11, 6, 0, 0))


data class LocalDateTimeAsTimestampEntity(
        val id: Int,
        val localDateTimeNotNull: LocalDateTime,
        val localDateTimeNullable: LocalDateTime? = null
)

val localDateTimeAsTimestampWithNullable = LocalDateTimeAsTimestampEntity(1, LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val localDateTimeAsTimestampWithoutNullable = LocalDateTimeAsTimestampEntity(2, LocalDateTime.of(2019, 11, 6, 0, 0))

data class KotlinxLocalDateTimeEntity(
        val id: Int,
        val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeNullable: kotlinx.datetime.LocalDateTime? = null
)

val kotlinxLocalDateTimeWithNullable = KotlinxLocalDateTimeEntity(1,
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
val kotlinxLocalDateTimeWithoutNullable = KotlinxLocalDateTimeEntity(2,
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0))

data class KotlinxLocalDateTimeAsTimestampEntity(
        val id: Int,
        val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeNullable: kotlinx.datetime.LocalDateTime? = null
)

val kotlinxLocalDateTimeAsTimestampWithNullable = KotlinxLocalDateTimeAsTimestampEntity(1,
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
val kotlinxLocalDateTimeAsTimestampWithoutNullable = KotlinxLocalDateTimeAsTimestampEntity(2,
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0))


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

val offsetDateTimeWithNullable = OffsetDateTimeEntity(1,
        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)))
val offsetDateTimeWithoutNullable = OffsetDateTimeEntity(2,
        OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC))

data class LocalTimeEntity(
        val id: Int,
        val localTimeNotNull: LocalTime,
        val localTimeNullable: LocalTime? = null
)

val localTimeWithNullable = LocalTimeEntity(1, LocalTime.of(12, 4), LocalTime.of(11, 4))
val localTimeWithoutNullable = LocalTimeEntity(2, LocalTime.of(12, 6))


data class IntEntity(
        val intNotNull: Int,
        val intNullable: Int? = null,
        val id: Int? = null
)

val intWithNullable = IntEntity(10, 6)
val intWithoutNullable = IntEntity(12)


data class LongEntity(
        val longNotNull: Long,
        val longNullable: Long? = null,
        val id: Long? = null
)

val longWithNullable = LongEntity(10L, 6L)
val longWithoutNullable = LongEntity(12L)


data class UuidEntity(
        val uuidNotNull: UUID,
        val uuidNullable: UUID? = null,
        val id: UUID = UUID.randomUUID()
)

val uuidWithNullable = UuidEntity(UUID.randomUUID(), UUID.randomUUID())
val uuidWithoutNullable = UuidEntity(UUID.randomUUID())

data class CustomerEntity(
        val id: Int,
        val name: String,
        val country: String,
        val age: Int,
)

val customerFrance = CustomerEntity(1, "Jean", "France", 19)
val customerUSA1 = CustomerEntity(2, "John", "USA", 21)
val customerUSA2 = CustomerEntity(3, "Big Boss", "USA", 20)
val customerJapan1 = CustomerEntity(4, "Seya", "USA", 20)
val customerJapan2 = CustomerEntity(5, "Shun", "USA", 20)


data class UserDto(
        val name: String,
        val alias: String?
)


data class UserWithRoleDto(
        val lastname: String,
        val role: String
)

// test inheritance
val inherited = Inherited("id", "name", "firstname")


interface Nameable {
    val name: String
}


interface DummyIntermediary : Nameable


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

interface ENTITY<T : Entity<String>> : Table<T> {
    val id: StringColumnNotNull<T>
}

interface NAMEABLE<T : Nameable> : Table<T> {
    val name: StringColumnNotNull<T>
}

interface JAVA_USER : Table<JavaUser> {
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

data class StringAsTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsTextNotNull = StringAsTextEntity(1, "abc", "def")
val stringAsTextNullable = StringAsTextEntity(2, "ghi")

data class StringAsMediumTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsMediumTextNotNull = StringAsMediumTextEntity(1, "abc", "def")
val stringAsMediumTextNullable = StringAsMediumTextEntity(2, "ghi")

data class StringAsLongTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsLongTextNotNull = StringAsLongTextEntity(1, "abc", "def")
val stringAsLongTextNullable = StringAsLongTextEntity(2, "ghi")

data class ByteArrayEntity(
    val id: Int,
    val byteArrayNotNull: ByteArray,
    val byteArrayNullable: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ByteArrayEntity

        if (id != other.id) return false
        if (!byteArrayNotNull.contentEquals(other.byteArrayNotNull)) return false
        if (byteArrayNullable != null) {
            if (other.byteArrayNullable == null) return false
            if (!byteArrayNullable.contentEquals(other.byteArrayNullable)) return false
        } else if (other.byteArrayNullable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + byteArrayNotNull.contentHashCode()
        result = 31 * result + (byteArrayNullable?.contentHashCode() ?: 0)
        return result
    }
}

val byteArrayWithNullable = ByteArrayEntity(1, byteArrayOf(0x2A), byteArrayOf(0x2B))
val byteArrayWithoutNullable = ByteArrayEntity(2, byteArrayOf(0x2C))

data class ByteArrayAsBinaryEntity(
    val id: Int,
    val byteArrayNotNull: ByteArray,
    val byteArrayNullable: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ByteArrayAsBinaryEntity

        if (id != other.id) return false
        if (!byteArrayNotNull.contentEquals(other.byteArrayNotNull)) return false
        if (byteArrayNullable != null) {
            if (other.byteArrayNullable == null) return false
            if (!byteArrayNullable.contentEquals(other.byteArrayNullable)) return false
        } else if (other.byteArrayNullable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + byteArrayNotNull.contentHashCode()
        result = 31 * result + (byteArrayNullable?.contentHashCode() ?: 0)
        return result
    }
}

val byteArrayBinaryWithNullable = ByteArrayAsBinaryEntity(1, byteArrayOf(0x2A), byteArrayOf(0x2B))
val byteArrayBinaryWithoutNullable = ByteArrayAsBinaryEntity(2, byteArrayOf(0x2C))
