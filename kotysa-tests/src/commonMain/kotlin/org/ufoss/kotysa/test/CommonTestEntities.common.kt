/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.*

data class CompanyEntity(
    val id: Int,
    val name: String,
)

val companyBigPharma = CompanyEntity(1, "Big Pharma")
val companyBigBrother = CompanyEntity(2, "Big Brother")

interface Companies : Table<CompanyEntity> {
    val id: IntColumnNotNull<CompanyEntity>
    val name: StringColumnNotNull<CompanyEntity>
}

data class RoleEntity(
    val id: Int,
    val label: String,
)

val roleUser = RoleEntity(1, "user")
val roleAdmin = RoleEntity(2, "admin")
val roleGod = RoleEntity(3, "god")

interface Roles : Table<RoleEntity> {
    val id: IntColumnNotNull<RoleEntity>
    val label: StringColumnNotNull<RoleEntity>
}

data class UserEntity(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val isAdmin: Boolean,
    val roleId: Int,
    val companyId: Int,
    val alias: String? = null,
)

val userJdoe = UserEntity(1, "John", "Doe", false, roleUser.id, companyBigPharma.id)
val userBboss = UserEntity(2, "Big", "Boss", true, roleAdmin.id, companyBigPharma.id, "TheBoss")

interface Users : Table<UserEntity> {
    val id: IntColumnNotNull<UserEntity>
    val firstname: StringColumnNotNull<UserEntity>
    val lastname: StringColumnNotNull<UserEntity>
    val isAdmin: BooleanColumnNotNull<UserEntity>
    val roleId: IntColumnNotNull<UserEntity>
    val companyId: IntColumnNotNull<UserEntity>
    val alias: StringColumnNullable<UserEntity>
}

data class UserRoleEntity(
    val userId: Int,
    val roleId: Int,
)

val userRoleBboss = UserRoleEntity(userBboss.id, roleAdmin.id)

interface UserRoles : Table<UserRoleEntity> {
    val userId: IntColumnNotNull<UserRoleEntity>
    val roleId: IntColumnNotNull<UserRoleEntity>
}

expect open class AllTypesNotNullBaseEntity
expect open class AllTypesNullableBaseEntity
expect open class AllTypesNullableDefaultValueBaseEntity

data class KotlinxLocalDateEntity(
    val id: Int,
    val localDateNotNull: kotlinx.datetime.LocalDate,
    val localDateNullable: kotlinx.datetime.LocalDate? = null
)

val kotlinxLocalDateWithNullable = KotlinxLocalDateEntity(
    1, kotlinx.datetime.LocalDate(2019, 11, 4),
    kotlinx.datetime.LocalDate(2018, 11, 4)
)
val kotlinxLocalDateWithoutNullable = KotlinxLocalDateEntity(2, kotlinx.datetime.LocalDate(2019, 11, 6))

interface KotlinxLocalDates : Table<KotlinxLocalDateEntity> {
    val id: IntColumnNotNull<KotlinxLocalDateEntity>
    val localDateNotNull: KotlinxLocalDateColumnNotNull<KotlinxLocalDateEntity>
    val localDateNullable: KotlinxLocalDateColumnNullable<KotlinxLocalDateEntity>
}

data class KotlinxLocalDateTimeEntity(
    val id: Int,
    val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
    val localDateTimeNullable: kotlinx.datetime.LocalDateTime? = null
)

val kotlinxLocalDateTimeWithNullable = KotlinxLocalDateTimeEntity(
    1,
    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
)
val kotlinxLocalDateTimeWithoutNullable = KotlinxLocalDateTimeEntity(
    2,
    kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0)
)

interface KotlinxLocalDateTimes : Table<KotlinxLocalDateTimeEntity> {
    val id: IntColumnNotNull<KotlinxLocalDateTimeEntity>
    val localDateTimeNotNull: KotlinxLocalDateTimeColumnNotNull<KotlinxLocalDateTimeEntity>
    val localDateTimeNullable: KotlinxLocalDateTimeColumnNullable<KotlinxLocalDateTimeEntity>
}

data class KotlinxLocalDateTimeAsTimestampEntity(
    val id: Int,
    val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
    val localDateTimeNullable: kotlinx.datetime.LocalDateTime? = null
)

val kotlinxLocalDateTimeAsTimestampWithNullable = KotlinxLocalDateTimeAsTimestampEntity(
    1,
    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
)
val kotlinxLocalDateTimeAsTimestampWithoutNullable = KotlinxLocalDateTimeAsTimestampEntity(
    2,
    kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0)
)

interface KotlinxLocalDateTimeAsTimestamps : Table<KotlinxLocalDateTimeAsTimestampEntity> {
    val id: IntColumnNotNull<KotlinxLocalDateTimeAsTimestampEntity>
    val localDateTimeNotNull: KotlinxLocalDateTimeColumnNotNull<KotlinxLocalDateTimeAsTimestampEntity>
    val localDateTimeNullable: KotlinxLocalDateTimeColumnNullable<KotlinxLocalDateTimeAsTimestampEntity>
}

data class KotlinxLocalTimeEntity(
    val id: Int,
    val localTimeNotNull: kotlinx.datetime.LocalTime,
    val localTimeNullable: kotlinx.datetime.LocalTime? = null
)

val kotlinxLocalTimeWithNullable =
    KotlinxLocalTimeEntity(1, kotlinx.datetime.LocalTime(12, 4), kotlinx.datetime.LocalTime(11, 4))
val kotlinxLocalTimeWithoutNullable = KotlinxLocalTimeEntity(2, kotlinx.datetime.LocalTime(12, 6))

interface KotlinxLocalTimes : Table<KotlinxLocalTimeEntity> {
    val id: IntColumnNotNull<KotlinxLocalTimeEntity>
    val localTimeNotNull: KotlinxLocalTimeColumnNotNull<KotlinxLocalTimeEntity>
    val localTimeNullable: KotlinxLocalTimeColumnNullable<KotlinxLocalTimeEntity>
}

data class IntEntity(
    val intNotNull: Int,
    val intNullable: Int? = null,
    val id: Int? = null
)

val intWithNullable = IntEntity(10, 6)
val intWithoutNullable = IntEntity(12)

interface Ints : Table<IntEntity> {
    val id: IntColumnNotNull<IntEntity>
    val intNotNull: IntColumnNotNull<IntEntity>
    val intNullable: IntColumnNullable<IntEntity>
}

data class IntEntityAsIdentity(
    val intNotNull: Int,
    val intNullable: Int? = null,
    val id: Int? = null
)

val intAsIdentityWithNullable = IntEntityAsIdentity(10, 6)
val intAsIdentityWithoutNullable = IntEntityAsIdentity(12)

interface IntAsIdentities : Table<IntEntityAsIdentity> {
    val id: IntColumnNotNull<IntEntityAsIdentity>
    val intNotNull: IntColumnNotNull<IntEntityAsIdentity>
    val intNullable: IntColumnNullable<IntEntityAsIdentity>
}

data class LongEntity(
    val longNotNull: Long,
    val longNullable: Long? = null,
    val id: Long? = null
)

val longWithNullable = LongEntity(10L, 6L)
val longWithoutNullable = LongEntity(12L)

interface Longs : Table<LongEntity> {
    val id: LongColumnNotNull<LongEntity>
    val longNotNull: LongColumnNotNull<LongEntity>
    val longNullable: LongColumnNullable<LongEntity>
}

data class FloatEntity(
    val id: Int,
    val floatNotNull: Float,
    val floatNullable: Float? = null,
)

val floatWithNullable = FloatEntity(1, 1.1f, 2.2f)
val floatWithoutNullable = FloatEntity(2, 1.3f)

interface Floats : Table<FloatEntity> {
    val id: IntColumnNotNull<FloatEntity>
    val floatNotNull: FloatColumnNotNull<FloatEntity>
    val floatNullable: FloatColumnNullable<FloatEntity>
}

data class DoubleEntity(
    val id: Int,
    val doubleNotNull: Double,
    val doubleNullable: Double? = null,
)

val doubleWithNullable = DoubleEntity(1, 1.1, 2.2)
val doubleWithoutNullable = DoubleEntity(2, 1.3)

interface Doubles : Table<DoubleEntity> {
    val id: IntColumnNotNull<DoubleEntity>
    val doubleNotNull: DoubleColumnNotNull<DoubleEntity>
    val doubleNullable: DoubleColumnNullable<DoubleEntity>
}

data class CustomerEntity(
    val id: Long,
    val name: String,
    val country: String,
    val age: Int,
)

val customerFrance = CustomerEntity(2147483648L, "Jean", "France", 19)
val customerUSA1 = CustomerEntity(2147483649L, "John", "USA", 21)
val customerUSA2 = CustomerEntity(2147483650L, "Big Boss", "USA", 20)
val customerJapan1 = CustomerEntity(2147483651L, "Seya", "USA", 20)
val customerJapan2 = CustomerEntity(2147483652L, "Shun", "USA", 20)
val customerFranceDup = CustomerEntity(2147483653L, "Jean", "France", 56)

interface Customers : Table<CustomerEntity> {
    val id: LongColumnNotNull<CustomerEntity>
    val name: StringColumnNotNull<CustomerEntity>
    val country: StringColumnNotNull<CustomerEntity>
    val age: IntColumnNotNull<CustomerEntity>
}

data class UserDto(
    val name: String,
    val isAdmin: Boolean,
    val alias: String?
)

data class CompleteUserDto(
    val lastname: String,
    val role: String,
    val company: String
)

interface Nameable {
    val name: String
}

interface Nameables<T : Nameable> : Table<T> {
    val name: StringColumnNotNull<T>
}

interface DummyIntermediary : Nameable

data class StringAsTextEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val stringAsTextNotNull = StringAsTextEntity(1, "abc", "def")
val stringAsTextNullable = StringAsTextEntity(2, "ghi")

interface Texts : Table<StringAsTextEntity> {
    val id: IntDbIntColumnNotNull<StringAsTextEntity>
    val stringNotNull: StringDbTextColumnNotNull<StringAsTextEntity>
    val stringNullable: StringDbTextColumnNullable<StringAsTextEntity>
}

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

interface ByteArrays : Table<ByteArrayEntity> {
    val id: IntColumnNotNull<ByteArrayEntity>
    val byteArrayNotNull: ByteArrayColumnNotNull<ByteArrayEntity>
    val byteArrayNullable: ByteArrayColumnNullable<ByteArrayEntity>
}

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

interface ByteArrayAsBinaries : Table<ByteArrayAsBinaryEntity> {
    val id: IntDbIntColumnNotNull<ByteArrayAsBinaryEntity>
    val byteArrayNotNull: ByteArrayColumnNotNull<ByteArrayAsBinaryEntity>
    val byteArrayNullable: ByteArrayColumnNullable<ByteArrayAsBinaryEntity>
}
