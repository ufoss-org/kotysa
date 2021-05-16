/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.mssql.date
import org.ufoss.kotysa.mssql.dateTime
import org.ufoss.kotysa.tables


object MSSQL_ROLE : MssqlTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
            .primaryKey()
    val label = varchar(RoleEntity::label)
}

object MSSQL_USER : MssqlTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
            .primaryKey("PK_users")
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = bit(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
            .foreignKey(MSSQL_ROLE.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)
}

object MSSQL_USER_ROLE : MssqlTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
            .foreignKey(MSSQL_USER.id)
    val roleId = integer(UserRoleEntity::roleId)
            .foreignKey(MSSQL_ROLE.id)
    val pk = primaryKey(userId, roleId)
}

/*data class PostgresqlAllTypesNotNullEntity(
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
) : AllTypesNotNullEntity(id, string, boolean, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long) {

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
        if (long != other.long) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + localDate.hashCode()
        result = 31 * result + kotlinxLocalDate.hashCode()
        result = 31 * result + offsetDateTime.hashCode()
        result = 31 * result + localTime.hashCode()
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNotNull = PostgresqlAllTypesNotNullEntity(1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalTime.now(), LocalDateTime.now(),
        LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), Int.MAX_VALUE, Long.MAX_VALUE,
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)), UUID.randomUUID())

object MSSQL_ALL_TYPES_NOT_NULL : MssqlTable<PostgresqlAllTypesNotNullEntity>("all_types") {
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
    val long = bigInt(AllTypesNotNullEntity::long)
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
        override val long: Long?,
        val offsetDateTime: OffsetDateTime?,
        val uuid: UUID?,
) : AllTypesNullableEntity(id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long)

val postgresqlAllTypesNullable = PostgresqlAllTypesNullableEntity(1, null, null, null,
        null, null, null, null, null,
        null, null, null, null)

object MSSQL_ALL_TYPES_NULLABLE : MssqlTable<PostgresqlAllTypesNullableEntity>("all_types_nullable") {
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
    val long = bigInt(AllTypesNullableEntity::long)
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
        override val long: Long? = null,
        val offsetDateTime: OffsetDateTime? = null,
        val uuid: UUID? = null,
) : AllTypesNullableDefaultValueEntity(id, string, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long) {

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
        if (long != other.long) return false
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
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNullableDefaultValue = PostgresqlAllTypesNullableDefaultValueEntity(1)

object MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE : MssqlTable<PostgresqlAllTypesNullableDefaultValueEntity>() {
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
    val long = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val offsetDateTime = timestampWithTimeZone(PostgresqlAllTypesNullableDefaultValueEntity::offsetDateTime,
            defaultValue = OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)))
    val uuid = uuid(PostgresqlAllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
}
*/
object MSSQL_LOCAL_DATE : MssqlTable<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MSSQL_KOTLINX_LOCAL_DATE : MssqlTable<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MSSQL_LOCAL_DATE_TIME : MssqlTable<LocalDateTimeEntity>() {
    val id = integer(LocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MSSQL_KOTLINX_LOCAL_DATE_TIME : MssqlTable<KotlinxLocalDateTimeEntity>() {
    val id = integer(KotlinxLocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object MSSQL_INT : MssqlTable<IntEntity>() {
    val id = identityInteger(IntEntity::id)
            .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object MSSQL_LONG : MssqlTable<LongEntity>() {
    val id = identityBigInt(LongEntity::id)
            .primaryKey()
    val longNotNull = bigInt(LongEntity::longNotNull)
    val longNullable = bigInt(LongEntity::longNullable)
}

object MSSQL_INHERITED : MssqlTable<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
            .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object MSSQL_JAVA_USER : MssqlTable<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
            .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = bit(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object MSSQL_CUSTOMER : MssqlTable<CustomerEntity>() {
    val id = integer(CustomerEntity::id)
            .primaryKey()
    val name = varchar(CustomerEntity::name)
    val country = varchar(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

val mssqlTables = tables().mssql(
        MSSQL_ROLE,
        MSSQL_USER,
        MSSQL_USER_ROLE,
        /*MSSQL_ALL_TYPES_NOT_NULL,
        MSSQL_ALL_TYPES_NULLABLE,
        MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE,*/
        MSSQL_LOCAL_DATE,
        MSSQL_KOTLINX_LOCAL_DATE,
        MSSQL_LOCAL_DATE_TIME,
        MSSQL_KOTLINX_LOCAL_DATE_TIME,
        MSSQL_INT,
        MSSQL_LONG,
        MSSQL_INHERITED,
        MSSQL_JAVA_USER,
        MSSQL_CUSTOMER,
)
