/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.sqlite.text
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object SqliteRoles : SqLiteTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
        .primaryKey()
    val label = text(RoleEntity::label)
        .unique()
}

object SqliteUsers : SqLiteTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    val firstname = text(UserEntity::firstname, "fname")
    val lastname = text(UserEntity::lastname, "lname")
    val isAdmin = integer(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
        .foreignKey(SqliteRoles.id, "FK_users_roles")
    val alias = text(UserEntity::alias)
}

object SqliteUserRoles : SqLiteTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
        .foreignKey(SqliteUsers.id)
    val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(SqliteRoles.id)
    val pk = primaryKey(userId, roleId)
}

object SqliteAllTypesNotNullWithTimes : SqLiteTable<AllTypesNotNullWithTimeEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = text(AllTypesNotNullEntity::string)
    val boolean = integer(AllTypesNotNullEntity::boolean)
    val localDate = text(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTime = text(AllTypesNotNullWithTimeEntity::localTime)
    val localDateTime1 = text(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
    val long = integer(AllTypesNotNullEntity::long)
    val byteArray = blob(AllTypesNotNullEntity::byteArray)
}

object SqliteAllTypesNullableWithTimes : SqLiteTable<AllTypesNullableWithTimeEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = text(AllTypesNullableEntity::string)
    val localDate = text(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNullableEntity::kotlinxLocalDate)
    val localTime = text(AllTypesNullableWithTimeEntity::localTime)
    val localDateTime1 = text(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
    val long = integer(AllTypesNullableEntity::long)
    val byteArray = blob(AllTypesNullableEntity::byteArray)
}

object SqliteAllTypesNullableDefaultValueWithTimes :
    SqLiteTable<AllTypesNullableDefaultValueWithTimeEntity>("all_types_nullable_default_value") {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
        .primaryKey()
    val string = text(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = text(
        AllTypesNullableDefaultValueEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = text(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
        defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
    )
    val localTime = text(
        AllTypesNullableDefaultValueWithTimeEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55)
    )
    val localDateTime1 = text(
        AllTypesNullableDefaultValueEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = text(
        AllTypesNullableDefaultValueEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = text(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
        defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = text(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
        defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
    )
    val int = integer(AllTypesNullableDefaultValueEntity::int, "sqlite_integer", 42)
    val long = integer(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
}

object SqliteLocalDates : SqLiteTable<LocalDateEntity>("local_date") {
    val id = integer(LocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = text(LocalDateEntity::localDateNotNull)
    val localDateNullable = text(LocalDateEntity::localDateNullable)
}

object SqliteKotlinxLocalDates : SqLiteTable<KotlinxLocalDateEntity>("kotlinx_local_date") {
    val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = text(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = text(KotlinxLocalDateEntity::localDateNullable)
}

object SqliteLocalDateTimes : SqLiteTable<LocalDateTimeEntity>("local_date_time") {
    val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    val localDateTimeNotNull = text(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = text(LocalDateTimeEntity::localDateTimeNullable)
}

object SqliteKotlinxLocalDateTimes : SqLiteTable<KotlinxLocalDateTimeEntity>("kotlinx_local_date_time") {
    val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    val localDateTimeNotNull = text(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = text(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object SqliteOffsetDateTimes : SqLiteTable<OffsetDateTimeEntity>("offset_date_time") {
    val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    val offsetDateTimeNotNull = text(OffsetDateTimeEntity::offsetDateTimeNotNull)
    val offsetDateTimeNullable = text(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object SqliteLocalTimes : SqLiteTable<LocalTimeEntity>("local_time") {
    val id = integer(LocalTimeEntity::id)
        .primaryKey()
    val localTimeNotNull = text(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = text(LocalTimeEntity::localTimeNullable)
}

object SqliteInts : SqLiteTable<IntEntity>("ints") {
    val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object SqliteLongs : SqLiteTable<LongEntity>("longs") {
    val id = autoIncrementInteger(LongEntity::id)
        .primaryKey()
    val longNotNull = integer(LongEntity::longNotNull)
    val longNullable = integer(LongEntity::longNullable)
}

object SqliteCustomers : SqLiteTable<CustomerEntity>("customer") {
    val id = integer(CustomerEntity::id)
        .primaryKey()
    val name = text(CustomerEntity::name)
        .unique()
    val country = text(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

object SqliteByteArrays : SqLiteTable<ByteArrayEntity>() {
    val id = integer(ByteArrayEntity::id)
        .primaryKey()
    val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

val sqLiteTables = tables().sqlite(
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles,
    SqliteAllTypesNotNullWithTimes,
    SqliteAllTypesNullableWithTimes,
    SqliteAllTypesNullableDefaultValueWithTimes,
    SqliteLocalDates,
    SqliteKotlinxLocalDates,
    SqliteLocalDateTimes,
    SqliteKotlinxLocalDateTimes,
    SqliteOffsetDateTimes,
    SqliteLocalTimes,
    SqliteInts,
    SqliteLongs,
    SqliteCustomers,
    SqliteByteArrays,
)
