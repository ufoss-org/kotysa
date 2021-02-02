/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object H2_ROLE : H2Table<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
            .primaryKey()
    val label = varchar(RoleEntity::label)
}

object H2_USER : H2Table<UserEntity>("users") {
    val id = integer(UserEntity::id)
            .primaryKey("PK_users")
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = boolean(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
            .foreignKey(H2_ROLE.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)
}

object H2_ALL_TYPES_NOT_NULL : H2Table<AllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
            .primaryKey()
    val string_named = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTime = time(AllTypesNotNullEntity::localTim) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
}

object H2_ALL_TYPES_NULLABLE : H2Table<AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTime = time(AllTypesNullableEntity::localTim) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
}

object H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE : H2Table<AllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(AllTypesNullableDefaultValueEntity::localDate,
            defaultValue = LocalDate.of(2019, 11, 4))
    val kotlinxLocalDate = date(AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6))
    val localTime = time(AllTypesNullableDefaultValueEntity::localTim,
            defaultValue = LocalTime.of(11, 25, 55, 123456789))
    val localDateTime1 = dateTime(AllTypesNullableDefaultValueEntity::localDateTime1,
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0))
    val localDateTime2 = timestamp(AllTypesNullableDefaultValueEntity::localDateTime2,
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0))
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0))
    val int = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
}

object H2_LOCAL_DATE : H2Table<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object H2_KOTLINX_LOCAL_DATE : H2Table<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object H2_LOCAL_DATE_TIME : H2Table<LocalDateTimeEntity>() {
    val id = integer(LocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object H2_KOTLINX_LOCAL_DATE_TIME : H2Table<KotlinxLocalDateTimeEntity>() {
    val id = integer(KotlinxLocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object H2_OFFSET_LOCAL_DATE_TIME : H2Table<OffsetDateTimeEntity>() {
    val id = integer(OffsetDateTimeEntity::id)
            .primaryKey()
    val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object H2_LOCAL_TIME : H2Table<LocalTimeEntity>() {
    val id = integer(LocalTimeEntity::id)
            .primaryKey()
    val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object H2_INT : H2Table<IntEntity>() {
    val id = autoIncrementInteger(IntEntity::id)
            .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object H2_UUID : H2Table<UuidEntity>() {
    val id = uuid(UuidEntity::id)
            .primaryKey()
    val uuidNotNull = uuid(UuidEntity::uuidNotNull)
    val uuidNullable = uuid(UuidEntity::uuidNullable)
}

val h2Tables = tables().h2(
        H2_ROLE,
        H2_USER,
        H2_ALL_TYPES_NOT_NULL,
        H2_ALL_TYPES_NULLABLE,
        H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        H2_LOCAL_DATE,
        H2_KOTLINX_LOCAL_DATE,
        H2_LOCAL_DATE_TIME,
        H2_KOTLINX_LOCAL_DATE_TIME,
        H2_OFFSET_LOCAL_DATE_TIME,
        H2_LOCAL_TIME,
        H2_INT,
        H2_UUID
)
