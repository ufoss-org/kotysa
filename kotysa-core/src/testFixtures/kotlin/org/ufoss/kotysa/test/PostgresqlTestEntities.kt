/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.postgresql.PostgresqlTable
import org.ufoss.kotysa.postgresql.date
import org.ufoss.kotysa.postgresql.timestamp
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.MYSQL_CUSTOMER.primaryKey
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.*


object POSTGRESQL_ROLE : PostgresqlTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
            .primaryKey()
    val label = varchar(RoleEntity::label)
}

object POSTGRESQL_USER : PostgresqlTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
            .primaryKey("PK_users")
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = boolean(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
            .foreignKey(POSTGRESQL_ROLE.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)
}

object POSTGRESQL_USER_ROLE : PostgresqlTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
            .foreignKey(POSTGRESQL_USER.id)
    val roleId = integer(UserRoleEntity::roleId)
            .foreignKey(POSTGRESQL_ROLE.id)
    val pk = primaryKey(userId, roleId)
}

data class PostgresqlAllTypesNotNullEntity(
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
        val offsetDateTime: OffsetDateTime,
        val uuid: UUID,
) : AllTypesNotNullEntity(id, string, boolean, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNotNullEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime.truncatedTo(ChronoUnit.SECONDS)) return false
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (uuid != other.uuid) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (localDate.hashCode())
        result = 31 * result + (kotlinxLocalDate.hashCode())
        result = 31 * result + (offsetDateTime.hashCode())
        result = 31 * result + (localTime.hashCode())
        result = 31 * result + (localDateTime1.hashCode())
        result = 31 * result + (localDateTime2.hashCode())
        result = 31 * result + (kotlinxLocalDateTime1.hashCode())
        result = 31 * result + (kotlinxLocalDateTime2.hashCode())
        result = 31 * result + (uuid.hashCode())
        result = 31 * result + (int)
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNotNull = PostgresqlAllTypesNotNullEntity(1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalTime.now(), LocalDateTime.now(),
        LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), 1, OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)), UUID.randomUUID())

object POSTGRESQL_ALL_TYPES_NOT_NULL : PostgresqlTable<PostgresqlAllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNotNullEntity::localTime)
    val localDateTime1 = timestamp(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
    val offsetDateTime = timestampWithTimeZone(PostgresqlAllTypesNotNullEntity::offsetDateTime)
    val uuid = uuid(PostgresqlAllTypesNotNullEntity::uuid)
}

data class PostgresqlAllTypesNullableEntity(
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
        val offsetDateTime: OffsetDateTime?,
        val uuid: UUID?,
) : AllTypesNullableEntity(id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int)

val postgresqlAllTypesNullable = PostgresqlAllTypesNullableEntity(1, null, null, null, null,
        null, null, null, null, null, null, null)

object POSTGRESQL_ALL_TYPES_NULLABLE : PostgresqlTable<PostgresqlAllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableEntity::localTime)
    val localDateTime1 = timestamp(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
    val offsetDateTime = timestampWithTimeZone(PostgresqlAllTypesNullableEntity::offsetDateTime)
    val uuid = uuid(PostgresqlAllTypesNullableEntity::uuid)
}

data class PostgresqlAllTypesNullableDefaultValueEntity(
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
        val offsetDateTime: OffsetDateTime? = null,
        val uuid: UUID? = null,
) : AllTypesNullableDefaultValueEntity(id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNullableDefaultValueEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }
        if (localTime != null) {
            if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime?.truncatedTo(ChronoUnit.SECONDS)) return false
        } else if (other.localTime != null) {
            return false
        }
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (uuid != other.uuid) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string?.hashCode() ?: 0
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNullableDefaultValue = PostgresqlAllTypesNullableDefaultValueEntity(1)

object POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE : PostgresqlTable<PostgresqlAllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(AllTypesNullableDefaultValueEntity::localDate,
            defaultValue = LocalDate.of(2019, 11, 4))
    val kotlinxLocalDate = date(AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6))
    val localTim = time(AllTypesNullableDefaultValueEntity::localTime,
            defaultValue = LocalTime.of(11, 25, 55, 123456789))
    val localDateTime1 = timestamp(AllTypesNullableDefaultValueEntity::localDateTime1,
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0))
    val localDateTime2 = timestamp(AllTypesNullableDefaultValueEntity::localDateTime2,
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0))
    val kotlinxLocalDateTime1 = timestamp(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0))
    val int = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val offsetDateTime = timestampWithTimeZone(PostgresqlAllTypesNullableDefaultValueEntity::offsetDateTime,
            defaultValue = OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)))
    val uuid = uuid(PostgresqlAllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
}

object POSTGRESQL_LOCAL_DATE : PostgresqlTable<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object POSTGRESQL_KOTLINX_LOCAL_DATE : PostgresqlTable<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object POSTGRESQL_LOCAL_DATE_TIME : PostgresqlTable<LocalDateTimeAsTimestampEntity>() {
    val id = integer(LocalDateTimeAsTimestampEntity::id)
            .primaryKey()
    val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object POSTGRESQL_KOTLINX_LOCAL_DATE_TIME : PostgresqlTable<KotlinxLocalDateTimeAsTimestampEntity>() {
    val id = integer(KotlinxLocalDateTimeAsTimestampEntity::id)
            .primaryKey()
    val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object POSTGRESQL_OFFSET_DATE_TIME : PostgresqlTable<OffsetDateTimeEntity>() {
    val id = integer(OffsetDateTimeEntity::id)
            .primaryKey()
    val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object POSTGRESQL_LOCAL_TIME : PostgresqlTable<LocalTimeEntity>() {
    val id = integer(LocalTimeEntity::id)
            .primaryKey()
    val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object POSTGRESQL_INT : PostgresqlTable<IntEntity>() {
    val id = serial(IntEntity::id)
            .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object POSTGRESQL_UUID : PostgresqlTable<UuidEntity>() {
    val id = uuid(UuidEntity::id)
            .primaryKey()
    val uuidNotNull = uuid(UuidEntity::uuidNotNull)
    val uuidNullable = uuid(UuidEntity::uuidNullable)
}

object POSTGRESQL_INHERITED : PostgresqlTable<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
            .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object POSTGRESQL_JAVA_USER : PostgresqlTable<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
            .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object POSTGRESQL_CUSTOMER : PostgresqlTable<CustomerEntity>() {
    val id = integer(CustomerEntity::id)
            .primaryKey()
    val name = varchar(CustomerEntity::name)
    val country = varchar(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

val postgresqlTables = tables().postgresql(
        POSTGRESQL_ROLE,
        POSTGRESQL_USER,
        POSTGRESQL_USER_ROLE,
        POSTGRESQL_ALL_TYPES_NOT_NULL,
        POSTGRESQL_ALL_TYPES_NULLABLE,
        POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        POSTGRESQL_LOCAL_DATE,
        POSTGRESQL_KOTLINX_LOCAL_DATE,
        POSTGRESQL_LOCAL_DATE_TIME,
        POSTGRESQL_KOTLINX_LOCAL_DATE_TIME,
        POSTGRESQL_OFFSET_DATE_TIME,
        POSTGRESQL_LOCAL_TIME,
        POSTGRESQL_INT,
        POSTGRESQL_UUID,
        POSTGRESQL_INHERITED,
        POSTGRESQL_JAVA_USER,
        POSTGRESQL_CUSTOMER,
)
