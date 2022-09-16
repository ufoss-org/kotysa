/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.ufoss.kotysa.postgresql.PostgresqlTable
import org.ufoss.kotysa.postgresql.date
import org.ufoss.kotysa.postgresql.timestamp
import org.ufoss.kotysa.tables
import java.time.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

object PostgresqlRoles : PostgresqlTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
        .primaryKey()
    val label = varchar(RoleEntity::label)
        .unique()
}

object PostgresqlUsers : PostgresqlTable<UserEntity>("users") {
    val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = boolean(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
        .foreignKey(PostgresqlRoles.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object PostgresqlUserRoles : PostgresqlTable<UserRoleEntity>("userRoles") {
    val userId = integer(UserRoleEntity::userId)
        .foreignKey(PostgresqlUsers.id)
    val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(PostgresqlRoles.id)
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
    override val long: Long,
    override val byteArray: ByteArray,
    val offsetDateTime: OffsetDateTime,
    val uuid: UUID,
) : AllTypesNotNullWithTimeEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2,
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, byteArray, localTime
) {

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
        if (!byteArray.contentEquals(other.byteArray)) return false
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
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNotNull = PostgresqlAllTypesNotNullEntity(
    1, "",
    true, LocalDate.now(), Clock.System.todayIn(TimeZone.UTC), LocalTime.now(), LocalDateTime.now(),
    LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC), Int.MAX_VALUE, Long.MAX_VALUE, byteArrayOf(0x2A),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
    ), UUID.randomUUID()
)

object PostgresqlAllTypesNotNulls : PostgresqlTable<PostgresqlAllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNotNullWithTimeEntity::localTime)
    val localDateTime1 = timestamp(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
    val long = bigInt(AllTypesNotNullEntity::long)
    val byteArray = bytea(AllTypesNotNullEntity::byteArray)
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
    override val byteArray: ByteArray?,
    val offsetDateTime: OffsetDateTime?,
    val uuid: UUID?,
) : AllTypesNullableWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2,
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, byteArray, localTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as PostgresqlAllTypesNullableEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime != other.localTime) return false
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
        if (offsetDateTime != other.offsetDateTime) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
        result = 31 * result + (string?.hashCode() ?: 0)
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        return result
    }
}

val postgresqlAllTypesNullable = PostgresqlAllTypesNullableEntity(
    1, null, null, null,
    null, null, null, null, null,
    null, null, null, null, null
)

object PostgresqlAllTypesNullables : PostgresqlTable<PostgresqlAllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableWithTimeEntity::localTime)
    val localDateTime1 = timestamp(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
    val long = bigInt(AllTypesNullableEntity::long)
    val byteArray = bytea(AllTypesNullableEntity::byteArray)
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
) : AllTypesNullableDefaultValueWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2,
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, localTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNullableDefaultValueEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime != null) {
            if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime?.truncatedTo(ChronoUnit.SECONDS)) return false
        } else if (other.localTime != null) {
            return false
        }
        if (localDateTime1 != other.localDateTime1) return false
        if (localDateTime2 != other.localDateTime2) return false
        if (kotlinxLocalDateTime1 != other.kotlinxLocalDateTime1) return false
        if (kotlinxLocalDateTime2 != other.kotlinxLocalDateTime2) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }
        if (uuid != other.uuid) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string?.hashCode() ?: 0
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (localDateTime1?.hashCode() ?: 0)
        result = 31 * result + (localDateTime2?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime1?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime2?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (long?.hashCode() ?: 0)
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}

val postgresqlAllTypesNullableDefaultValue = PostgresqlAllTypesNullableDefaultValueEntity(1)

object PostgresqlAllTypesNullableDefaultValues : PostgresqlTable<PostgresqlAllTypesNullableDefaultValueEntity>() {
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
        AllTypesNullableDefaultValueWithTimeEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55, 123456789)
    )
    val localDateTime1 = timestamp(
        AllTypesNullableDefaultValueEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = timestamp(
        AllTypesNullableDefaultValueEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = timestamp(
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
        PostgresqlAllTypesNullableDefaultValueEntity::offsetDateTime,
        defaultValue = OffsetDateTime.of(
            2019, 11, 4, 0, 0, 0, 0,
            ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
        )
    )
    val uuid = uuid(PostgresqlAllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
}

object PostgresqlLocalDates : PostgresqlTable<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object PostgresqlKotlinxLocalDates : PostgresqlTable<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object PostgresqlLocalDateTimeAsTimestamps : PostgresqlTable<LocalDateTimeAsTimestampEntity>() {
    val id = integer(LocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object PostgresqlKotlinxLocalDateTimeAsTimestamps : PostgresqlTable<KotlinxLocalDateTimeAsTimestampEntity>() {
    val id = integer(KotlinxLocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object PostgresqlOffsetDateTimes : PostgresqlTable<OffsetDateTimeEntity>() {
    val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object PostgresqlLocalTimes : PostgresqlTable<LocalTimeEntity>() {
    val id = integer(LocalTimeEntity::id)
        .primaryKey()
    val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object PostgresqlInts : PostgresqlTable<IntEntity>() {
    val id = serial(IntEntity::id)
        .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

object PostgresqlLongs : PostgresqlTable<LongEntity>() {
    val id = bigSerial(LongEntity::id)
        .primaryKey()
    val longNotNull = bigInt(LongEntity::longNotNull)
    val longNullable = bigInt(LongEntity::longNullable)
}

object PostgresqlUuids : PostgresqlTable<UuidEntity>() {
    val id = uuid(UuidEntity::id)
        .primaryKey()
    val uuidNotNull = uuid(UuidEntity::uuidNotNull)
    val uuidNullable = uuid(UuidEntity::uuidNullable)
}

object PostgresqlInheriteds : PostgresqlTable<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object PostgresqlJavaUsers : PostgresqlTable<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object PostgresqlCustomers : PostgresqlTable<CustomerEntity>() {
    val id = integer(CustomerEntity::id)
        .primaryKey()
    val name = varchar(CustomerEntity::name)
        .unique()
    val country = varchar(CustomerEntity::country)
    val age = integer(CustomerEntity::age)
}

object PostgresqlTexts : PostgresqlTable<StringAsTextEntity>() {
    val id = integer(StringAsTextEntity::id)
        .primaryKey()
    val stringNotNull = text(StringAsTextEntity::stringNotNull)
    val stringNullable = text(StringAsTextEntity::stringNullable)
}

object PostgresqlByteArrayAsByteas : PostgresqlTable<ByteArrayAsBinaryEntity>() {
    val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    val byteArrayNotNull = bytea(ByteArrayAsBinaryEntity::byteArrayNotNull)
    val byteArrayNullable = bytea(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val postgresqlTables = tables().postgresql(
    PostgresqlRoles,
    PostgresqlUsers,
    PostgresqlUserRoles,
    PostgresqlAllTypesNotNulls,
    PostgresqlAllTypesNullables,
    PostgresqlAllTypesNullableDefaultValues,
    PostgresqlLocalDates,
    PostgresqlKotlinxLocalDates,
    PostgresqlLocalDateTimeAsTimestamps,
    PostgresqlKotlinxLocalDateTimeAsTimestamps,
    PostgresqlOffsetDateTimes,
    PostgresqlLocalTimes,
    PostgresqlInts,
    PostgresqlLongs,
    PostgresqlUuids,
    PostgresqlInheriteds,
    PostgresqlJavaUsers,
    PostgresqlCustomers,
    PostgresqlTexts,
    PostgresqlByteArrayAsByteas,
)
