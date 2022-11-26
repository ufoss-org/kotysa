/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.ufoss.kotysa.postgresql.*
import org.ufoss.kotysa.tables
import java.time.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

object PostgresqlRoles : PostgresqlTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = varchar(RoleEntity::label)
        .unique()
}

object PostgresqlUsers : PostgresqlTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = varchar(UserEntity::firstname, "fname")
    override val lastname = varchar(UserEntity::lastname, "lname")
    override val isAdmin = boolean(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(PostgresqlRoles.id, "FK_users_roles")
    override val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object PostgresqlUserRoles : PostgresqlTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(PostgresqlUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(PostgresqlRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class PostgresqlAllTypesNotNullEntity(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localTime: LocalTime,
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime,
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
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, byteArray, localTime, kotlinxLocalTime
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
        if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
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
        result = 31 * result + kotlinxLocalTime.hashCode()
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
    true, LocalDate.now(), Clock.System.todayIn(TimeZone.UTC), LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time, LocalDateTime.now(), LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC), Clock.System.now().toLocalDateTime(TimeZone.UTC), Int.MAX_VALUE,
    Long.MAX_VALUE, byteArrayOf(0x2A), OffsetDateTime.of(
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
    val kotlinxLocalTim = time(AllTypesNotNullWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = timestamp(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNotNullEntity::int)
    val longe = bigInt(AllTypesNotNullEntity::long)
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
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime?,
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
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, byteArray, localTime, kotlinxLocalTime
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
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false
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
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
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
    null, null, null, null, null, null
)

object PostgresqlAllTypesNullables : PostgresqlTable<PostgresqlAllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableWithTimeEntity::localTime)
    val kotlinxLocalTim = time(AllTypesNullableWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = timestamp(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableEntity::int)
    val longe = bigInt(AllTypesNullableEntity::long)
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
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime? = null,
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
    kotlinxLocalDateTime1, kotlinxLocalDateTime2, int, long, localTime, kotlinxLocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNullableDefaultValueEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime != null) {
            if (localTime.truncatedTo(ChronoUnit.SECONDS)
                != other.localTime?.truncatedTo(ChronoUnit.SECONDS)
            ) return false
        } else if (other.localTime != null) {
            return false
        }
        if (kotlinxLocalTime != null) {
            if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
                != other.kotlinxLocalTime?.toJavaLocalTime()?.truncatedTo(ChronoUnit.SECONDS)
            ) return false
        } else if (other.kotlinxLocalTime != null) {
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
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
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
val postgresqlAllTypesNullableDefaultValueToInsert = PostgresqlAllTypesNullableDefaultValueEntity(2)

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
    val kotlinxLocalTim = time(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalTime,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55, 123456789)
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
    val inte = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val offsetDateTime = timestampWithTimeZone(
        PostgresqlAllTypesNullableDefaultValueEntity::offsetDateTime,
        defaultValue = OffsetDateTime.of(
            2019, 11, 4, 0, 0, 0, 0,
            ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
        )
    )
    val uuid = uuid(PostgresqlAllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
}

object PostgresqlLocalDates : PostgresqlTable<LocalDateEntity>(), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object PostgresqlKotlinxLocalDates : PostgresqlTable<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object PostgresqlLocalDateTimeAsTimestamps : PostgresqlTable<LocalDateTimeAsTimestampEntity>(),
    LocalDateTimeAsTimestamps {
    override val id = integer(LocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object PostgresqlKotlinxLocalDateTimeAsTimestamps : PostgresqlTable<KotlinxLocalDateTimeAsTimestampEntity>(),
    KotlinxLocalDateTimeAsTimestamps {
    override val id = integer(KotlinxLocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object PostgresqlOffsetDateTimes : PostgresqlTable<OffsetDateTimeEntity>(), OffsetDateTimes {
    override val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    override val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    override val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object PostgresqlLocalTimes : PostgresqlTable<LocalTimeEntity>(), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object PostgresqlKotlinxLocalTimes : PostgresqlTable<KotlinxLocalTimeEntity>(), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(KotlinxLocalTimeEntity::localTimeNullable)
}

object PostgresqlInts : PostgresqlTable<IntEntity>(), Ints {
    override val id = serial(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object PostgresqlLongs : PostgresqlTable<LongEntity>(), Longs {
    override val id = bigSerial(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object PostgresqlUuids : PostgresqlTable<UuidEntity>(), Uuids {
    override val id = uuid(UuidEntity::id)
        .primaryKey()
    override val uuidNotNull = uuid(UuidEntity::uuidNotNull)
    override val uuidNullable = uuid(UuidEntity::uuidNullable)
}

object PostgresqlInheriteds : PostgresqlTable<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    override val firstname = varchar(Inherited::firstname)
}

object PostgresqlJavaUsers : PostgresqlTable<JavaUser>("java_users"), JavaUsers {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object PostgresqlCustomers : PostgresqlTable<CustomerEntity>(), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = varchar(CustomerEntity::name)
        .unique()
    override val country = varchar(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object PostgresqlTexts : PostgresqlTable<StringAsTextEntity>(), Texts {
    override val id = integer(StringAsTextEntity::id)
        .primaryKey()
    override val stringNotNull = text(StringAsTextEntity::stringNotNull)
    override val stringNullable = text(StringAsTextEntity::stringNullable)
}

object PostgresqlByteArrayAsByteas : PostgresqlTable<ByteArrayAsBinaryEntity>(), ByteArrayAsBinaries {
    override val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    override val byteArrayNotNull = bytea(ByteArrayAsBinaryEntity::byteArrayNotNull)
    override val byteArrayNullable = bytea(ByteArrayAsBinaryEntity::byteArrayNullable)
}

data class TsvectorEntity(
    val id: Int,
    val stringNotNull: String,
    val stringNullable: String? = null
)

val tsVectorNotNull = TsvectorEntity(1, "Creating several", "tables")
val tsVectorNullable = TsvectorEntity(2, "Tables are powerful")

object PostgresqlTsvectors : PostgresqlTable<TsvectorEntity>() {
    val id = integer(TsvectorEntity::id)
        .primaryKey()
    val stringNotNull = text(TsvectorEntity::stringNotNull)
    val stringNullable = text(TsvectorEntity::stringNullable)

    val textSearchable = tsvector(TsvectorType.english, "text_searchable", stringNotNull)
        .withGinIndex()
    val textSearchableBoth = tsvector(TsvectorType.english, stringNotNull, stringNullable)
        .withGistIndex()
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
    PostgresqlKotlinxLocalTimes,
    PostgresqlInts,
    PostgresqlLongs,
    PostgresqlUuids,
    PostgresqlInheriteds,
    PostgresqlJavaUsers,
    PostgresqlCustomers,
    PostgresqlTexts,
    PostgresqlByteArrayAsByteas,
    PostgresqlTsvectors,
)
