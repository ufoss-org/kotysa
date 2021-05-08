/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.h2.date
import org.ufoss.kotysa.h2.dateTime
import org.ufoss.kotysa.h2.timestamp
import org.ufoss.kotysa.tables
import java.time.*
import java.util.*

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

object H2_USER_ROLE : H2Table<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
            .foreignKey(H2_USER.id)
    val roleId = integer(UserRoleEntity::roleId)
            .foreignKey(H2_ROLE.id)
    val pk = primaryKey(userId, roleId)
}

data class H2AllTypesNotNullEntity(
        override val id: Int,
        override val string: String,
        override val boolean: Boolean,
        override val localDate: LocalDate,
        override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
        override val localTime: LocalTime,
        override val localDateTime1: LocalDateTime,
        override val localDateTime2: LocalDateTime,
        override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
        override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
        override val int: Int,
        override val long: Long,
        val offsetDateTime: OffsetDateTime,
        val uuid: UUID,
) : AllTypesNotNullEntity(
        id, string, boolean, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long
)

val h2AllTypesNotNull = H2AllTypesNotNullEntity(
        1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalTime.now(), LocalDateTime.now(),
        LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), 1, 1L, OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
), UUID.randomUUID()
)

object H2_ALL_TYPES_NOT_NULL : H2Table<H2AllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNotNullEntity::localTime, precision = 9)
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
    val long = bigInt(AllTypesNotNullEntity::long)
    val offsetDateTime = timestampWithTimeZone(H2AllTypesNotNullEntity::offsetDateTime)
    val uuid = uuid(H2AllTypesNotNullEntity::uuid)
}

data class H2AllTypesNullableEntity(
        override val id: Int,
        override val string: String?,
        override val localDate: LocalDate?,
        override val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
        override val localTime: LocalTime?,
        override val localDateTime1: LocalDateTime?,
        override val localDateTime2: LocalDateTime?,
        override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
        override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
        override val int: Int?,
        override val long: Long?,
        val offsetDateTime: OffsetDateTime?,
        val uuid: UUID?,
) : AllTypesNullableEntity(
        id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long
)

val h2AllTypesNullable = H2AllTypesNullableEntity(
        1, null, null, null, null, null, null,
        null, null, null, null, null, null
)

object H2_ALL_TYPES_NULLABLE : H2Table<H2AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableEntity::localTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
    val long = bigInt(AllTypesNullableEntity::long)
    val offsetDateTime = timestampWithTimeZone(H2AllTypesNullableEntity::offsetDateTime)
    val uuid = uuid(H2AllTypesNullableEntity::uuid)
}

data class H2AllTypesNullableDefaultValueEntity(
        override val id: Int,
        override val string: String? = null,
        override val localDate: LocalDate? = null,
        override val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
        override val localTime: LocalTime? = null,
        override val localDateTime1: LocalDateTime? = null,
        override val localDateTime2: LocalDateTime? = null,
        override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
        override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
        override val int: Int? = null,
        override val long: Long? = null,
        val offsetDateTime: OffsetDateTime? = null,
        val uuid: UUID? = null,
) : AllTypesNullableDefaultValueEntity(
        id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long
)

val h2AllTypesNullableDefaultValue = H2AllTypesNullableDefaultValueEntity(1)

object H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE : H2Table<H2AllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(
            AllTypesNullableDefaultValueEntity::localDate,
            defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
            AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
    )
    val localTim = time(
            AllTypesNullableDefaultValueEntity::localTime, precision = 9,
            defaultValue = LocalTime.of(11, 25, 55, 123456789)
    )
    val localDateTime1 = dateTime(
            AllTypesNullableDefaultValueEntity::localDateTime1,
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = timestamp(
            AllTypesNullableDefaultValueEntity::localDateTime2,
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = dateTime(
            AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = timestamp(
            AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
    )
    val int = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val long = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val offsetDateTime = timestampWithTimeZone(
            H2AllTypesNullableDefaultValueEntity::offsetDateTime,
            defaultValue = OffsetDateTime.of(
                    2019, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
            )
    )
    val uuid = uuid(H2AllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
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

object H2_LOCAL_DATE_TIME_AS_TIMESTAMP : H2Table<LocalDateTimeAsTimestampEntity>() {
    val id = integer(LocalDateTimeAsTimestampEntity::id)
            .primaryKey()
    val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object H2_KOTLINX_LOCAL_DATE_TIME : H2Table<KotlinxLocalDateTimeEntity>() {
    val id = integer(KotlinxLocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP : H2Table<KotlinxLocalDateTimeAsTimestampEntity>() {
    val id = integer(KotlinxLocalDateTimeAsTimestampEntity::id)
            .primaryKey()
    val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object H2_OFFSET_DATE_TIME : H2Table<OffsetDateTimeEntity>() {
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

object H2_INHERITED : H2Table<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
            .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object H2_JAVA_USER : H2Table<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
            .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object H2_CUSTOMER : H2Table<CustomerEntity>() {
    val id = integer(CustomerEntity::id)
            .primaryKey()
    val name = varchar(CustomerEntity::name)
    val country = varchar(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

val h2Tables = tables().h2(
        H2_ROLE,
        H2_USER,
        H2_USER_ROLE,
        H2_ALL_TYPES_NOT_NULL,
        H2_ALL_TYPES_NULLABLE,
        H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        H2_LOCAL_DATE,
        H2_KOTLINX_LOCAL_DATE,
        H2_LOCAL_DATE_TIME,
        H2_LOCAL_DATE_TIME_AS_TIMESTAMP,
        H2_KOTLINX_LOCAL_DATE_TIME,
        H2_KOTLINX_LOCAL_DATE_TIME_AS_TIMESTAMP,
        H2_OFFSET_DATE_TIME,
        H2_LOCAL_TIME,
        H2_INT,
        H2_UUID,
        H2_INHERITED,
        H2_JAVA_USER,
        H2_CUSTOMER,
)
