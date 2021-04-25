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

object SQLITE_ROLE : SqLiteTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
            .primaryKey()
    val label = text(RoleEntity::label)
}

object SQLITE_USER : SqLiteTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
            .primaryKey("PK_users")
    val firstname = text(UserEntity::firstname, "fname")
    val lastname = text(UserEntity::lastname, "lname")
    val isAdmin = integer(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
            .foreignKey(SQLITE_ROLE.id, "FK_users_roles")
    val alias = text(UserEntity::alias)
}

object SQLITE_USER_ROLE : SqLiteTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
            .foreignKey(SQLITE_USER.id)
    val roleId = integer(UserRoleEntity::roleId)
            .foreignKey(SQLITE_ROLE.id)
    val pk = primaryKey(userId, roleId)
}

object SQLITE_ALL_TYPES_NOT_NULL : SqLiteTable<AllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
            .primaryKey()
    val string = text(AllTypesNotNullEntity::string)
    val boolean = integer(AllTypesNotNullEntity::boolean)
    val localDate = text(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTime = text(AllTypesNotNullEntity::localTime)
    val localDateTime1 = text(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
}

object SQLITE_ALL_TYPES_NULLABLE : SqLiteTable<AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
            .primaryKey()
    val string = text(AllTypesNullableEntity::string)
    val localDate = text(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNullableEntity::kotlinxLocalDate)
    val localTime = text(AllTypesNullableEntity::localTime)
    val localDateTime1 = text(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
}

object SQLITE_ALL_TYPES_NULLABLE_DEFAULT_VALUE :
        SqLiteTable<AllTypesNullableDefaultValueEntity>("all_types_nullable_default_value") {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
            .primaryKey()
    val string = text(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = text(AllTypesNullableDefaultValueEntity::localDate,
            defaultValue = LocalDate.of(2019, 11, 4))
    val kotlinxLocalDate = text(AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6))
    val localTime = text(AllTypesNullableDefaultValueEntity::localTime,
            defaultValue = LocalTime.of(11, 25, 55))
    val localDateTime1 = text(AllTypesNullableDefaultValueEntity::localDateTime1,
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0))
    val localDateTime2 = text(AllTypesNullableDefaultValueEntity::localDateTime2,
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0))
    val kotlinxLocalDateTime1 = text(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
    val kotlinxLocalDateTime2 = text(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0))
    val int = integer(AllTypesNullableDefaultValueEntity::int, "sqlite_integer", 42)
}

object SQLITE_LOCAL_DATE : SqLiteTable<LocalDateEntity>("local_date") {
    val id = integer(LocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = text(LocalDateEntity::localDateNotNull)
    val localDateNullable = text(LocalDateEntity::localDateNullable)
}

object SQLITE_KOTLINX_LOCAL_DATE : SqLiteTable<KotlinxLocalDateEntity>("kotlinx_local_date") {
    val id = integer(KotlinxLocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = text(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = text(KotlinxLocalDateEntity::localDateNullable)
}

object SQLITE_LOCAL_DATE_TIME : SqLiteTable<LocalDateTimeEntity>("local_date_time") {
    val id = integer(LocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = text(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = text(LocalDateTimeEntity::localDateTimeNullable)
}

object SQLITE_KOTLINX_LOCAL_DATE_TIME : SqLiteTable<KotlinxLocalDateTimeEntity>("kotlinx_local_date_time") {
    val id = integer(KotlinxLocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = text(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = text(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object SQLITE_OFFSET_DATE_TIME : SqLiteTable<OffsetDateTimeEntity>("offset_date_time") {
    val id = integer(OffsetDateTimeEntity::id)
            .primaryKey()
    val offsetDateTimeNotNull = text(OffsetDateTimeEntity::offsetDateTimeNotNull)
    val offsetDateTimeNullable = text(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object SQLITE_LOCAL_TIME : SqLiteTable<LocalTimeEntity>("local_time") {
    val id = integer(LocalTimeEntity::id)
            .primaryKey()
    val localTimeNotNull = text(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = text(LocalTimeEntity::localTimeNullable)
}

object SQLITE_INT : SqLiteTable<IntEntity>("ints") {
    val id = autoIncrementInteger(IntEntity::id)
            .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object SQLITE_CUSTOMER : SqLiteTable<CustomerEntity>("customer") {
    val id = integer(CustomerEntity::id)
            .primaryKey()
    val name = text(CustomerEntity::name)
    val country = text(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

val sqLiteTables = tables().sqlite(
        SQLITE_ROLE,
        SQLITE_USER,
        SQLITE_USER_ROLE,
        SQLITE_ALL_TYPES_NOT_NULL,
        SQLITE_ALL_TYPES_NULLABLE,
        SQLITE_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        SQLITE_LOCAL_DATE,
        SQLITE_KOTLINX_LOCAL_DATE,
        SQLITE_LOCAL_DATE_TIME,
        SQLITE_KOTLINX_LOCAL_DATE_TIME,
        SQLITE_OFFSET_DATE_TIME,
        SQLITE_LOCAL_TIME,
        SQLITE_INT,
        SQLITE_CUSTOMER,
)
