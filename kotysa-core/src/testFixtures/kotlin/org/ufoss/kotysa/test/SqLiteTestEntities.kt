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

object SqliteRoles : SqLiteTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = text(RoleEntity::label)
        .unique()
}

object SqliteUsers : SqLiteTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = text(UserEntity::firstname, "fname")
    override val lastname = text(UserEntity::lastname, "lname")
    override val isAdmin = integer(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(SqliteRoles.id, "FK_users_roles")
    override val alias = text(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object SqliteUserRoles : SqLiteTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(SqliteUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(SqliteRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

object SqliteAllTypesNotNullWithTimes : SqLiteTable<AllTypesNotNullWithTimeEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = text(AllTypesNotNullEntity::string)
    val boolean = integer(AllTypesNotNullEntity::boolean)
    val localDate = text(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = text(AllTypesNotNullWithTimeEntity::localTime)
    val kotlinxLocalTim = text(AllTypesNotNullWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = text(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNotNullEntity::int)
    val longe = integer(AllTypesNotNullEntity::long)
    val byteArray = blob(AllTypesNotNullEntity::byteArray)
}

object SqliteAllTypesNullableWithTimes : SqLiteTable<AllTypesNullableWithTimeEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = text(AllTypesNullableEntity::string)
    val localDate = text(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = text(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = text(AllTypesNullableWithTimeEntity::localTime)
    val kotlinxLocalTim = text(AllTypesNullableWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = text(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = text(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableEntity::int)
    val longe = integer(AllTypesNullableEntity::long)
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
    val localTim = text(
        AllTypesNullableDefaultValueWithTimeEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55)
    )
    val kotlinxLocalTim = text(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalTime,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55)
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
    val inte = integer(AllTypesNullableDefaultValueEntity::int, "sqlite_integer", 42)
    val longe = integer(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
}

object SqliteLocalDates : SqLiteTable<LocalDateEntity>("local_date"), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = text(LocalDateEntity::localDateNotNull)
    override val localDateNullable = text(LocalDateEntity::localDateNullable)
}

object SqliteKotlinxLocalDates : SqLiteTable<KotlinxLocalDateEntity>("kotlinx_local_date"),
    KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = text(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = text(KotlinxLocalDateEntity::localDateNullable)
}

object SqliteLocalDateTimes : SqLiteTable<LocalDateTimeEntity>("local_date_time"), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = text(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = text(LocalDateTimeEntity::localDateTimeNullable)
}

object SqliteKotlinxLocalDateTimes : SqLiteTable<KotlinxLocalDateTimeEntity>("kotlinx_local_date_time"),
    KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = text(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = text(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object SqliteOffsetDateTimes : SqLiteTable<OffsetDateTimeEntity>("offset_date_time"), OffsetDateTimes {
    override val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    override val offsetDateTimeNotNull = text(OffsetDateTimeEntity::offsetDateTimeNotNull)
    override val offsetDateTimeNullable = text(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object SqliteLocalTimes : SqLiteTable<LocalTimeEntity>("local_time"), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = text(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = text(LocalTimeEntity::localTimeNullable)
}

object SqliteKotlinxLocalTimes : SqLiteTable<KotlinxLocalTimeEntity>("local_time"), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = text(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = text(KotlinxLocalTimeEntity::localTimeNullable)
}

object SqliteInts : SqLiteTable<IntEntity>("ints"), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object SqliteLongs : SqLiteTable<LongEntity>("longs"), Longs {
    override val id = autoIncrementInteger(LongEntity::id)
        .primaryKey()
    override val longNotNull = integer(LongEntity::longNotNull)
    override val longNullable = integer(LongEntity::longNullable)
}

object SqliteCustomers : SqLiteTable<CustomerEntity>("customer"), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = text(CustomerEntity::name)
        .unique()
    override val country = text(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object SqliteByteArrays : SqLiteTable<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
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
    SqliteKotlinxLocalTimes,
    SqliteInts,
    SqliteLongs,
    SqliteCustomers,
    SqliteByteArrays,
)
