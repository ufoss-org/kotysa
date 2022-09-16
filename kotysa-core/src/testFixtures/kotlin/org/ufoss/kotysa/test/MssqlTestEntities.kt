/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.mssql.date
import org.ufoss.kotysa.mssql.dateTime
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


object MssqlRoles : MssqlTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
        .primaryKey()
    val label = varchar(RoleEntity::label)
        .unique()
}

object MssqlUsers : MssqlTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = bit(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
        .foreignKey(MssqlRoles.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)
}

object MssqlUserRoles : MssqlTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
        .foreignKey(MssqlUsers.id)
    val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(MssqlRoles.id)
    val pk = primaryKey(userId, roleId)
}

data class MssqlAllTypesNotNull(
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
) : AllTypesNotNullEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MssqlAllTypesNotNull

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
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
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + localDate.hashCode()
        result = 31 * result + kotlinxLocalDate.hashCode()
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + id
        return result
    }
}

val mssqlAllTypesNotNull = MssqlAllTypesNotNull(
    1, "", true, LocalDate.now(), Clock.System.todayIn(TimeZone.UTC), LocalDateTime.now(),
    LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC), 1, 1L, byteArrayOf(0x2A)
)

object MssqlAllTypesNotNulls : MssqlTable<MssqlAllTypesNotNull>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = bit(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNotNullEntity::int)
    val longe = bigInt(AllTypesNotNullEntity::long)
    val byteArray = binary(AllTypesNotNullEntity::byteArray)
}

object MssqlAllTypesNullables : MssqlTable<AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableEntity::int)
    val longe = bigInt(AllTypesNullableEntity::long)
    val byteArray = binary(AllTypesNullableEntity::byteArray)
}

object MssqlAllTypesNullableDefaultValues : MssqlTable<AllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(
        AllTypesNullableDefaultValueEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
        defaultValue = LocalDate(2019, 11, 6)
    )
    val localDateTime1 = dateTime(
        AllTypesNullableDefaultValueEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = dateTime(
        AllTypesNullableDefaultValueEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = dateTime(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
        defaultValue = LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = dateTime(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
        defaultValue = LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
}

object MssqlLocalDates : MssqlTable<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MssqlKotlinxLocalDates : MssqlTable<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MssqlLocalDateTimes : MssqlTable<LocalDateTimeEntity>() {
    val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MssqlKotlinxLocalDateTimes : MssqlTable<KotlinxLocalDateTimeEntity>() {
    val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object MssqlInts : MssqlTable<IntEntity>() {
    val id = identityInteger(IntEntity::id)
        .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object MssqlLongs : MssqlTable<LongEntity>() {
    val id = identityBigInt(LongEntity::id)
        .primaryKey()
    val longNotNull = bigInt(LongEntity::longNotNull)
    val longNullable = bigInt(LongEntity::longNullable)
}

object MssqlInheriteds : MssqlTable<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object MssqlJavaUsers : MssqlTable<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = bit(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object MssqlCustomers : MssqlTable<CustomerEntity>() {
    val id = integer(CustomerEntity::id)
        .primaryKey()
    val name = varchar(CustomerEntity::name)
        .unique()
    val country = varchar(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

object MssqlByteArrayAsBinarys : MssqlTable<ByteArrayAsBinaryEntity>() {
    val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    val byteArrayNotNull = binary(ByteArrayAsBinaryEntity::byteArrayNotNull)
    val byteArrayNullable = binary(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val mssqlTables = tables().mssql(
    MssqlRoles,
    MssqlUsers,
    MssqlUserRoles,
    MssqlAllTypesNotNulls,
    MssqlAllTypesNullables,
    MssqlAllTypesNullableDefaultValues,
    MssqlLocalDates,
    MssqlKotlinxLocalDates,
    MssqlLocalDateTimes,
    MssqlKotlinxLocalDateTimes,
    MssqlInts,
    MssqlLongs,
    MssqlInheriteds,
    MssqlJavaUsers,
    MssqlCustomers,
    MssqlByteArrayAsBinarys,
)
