/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.ufoss.kotysa.h2.*
import org.ufoss.kotysa.tables
import java.time.*
import java.util.*

object H2Roles : H2Table<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = varchar(RoleEntity::label)
        .unique()
}

object H2Users : H2Table<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = varchar(UserEntity::firstname, "fname")
    override val lastname = varchar(UserEntity::lastname, "lname")
    override val isAdmin = boolean(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(H2Roles.id, "FK_users_roles")
    override val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object H2UserRoles : H2Table<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(H2Users.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(H2Roles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class H2AllTypesNotNullEntity(
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
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, localTime, kotlinxLocalTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as H2AllTypesNotNullEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (boolean != other.boolean) return false
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
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (offsetDateTime != other.offsetDateTime) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
        result = 31 * result + string.hashCode()
        result = 31 * result + boolean.hashCode()
        result = 31 * result + localDate.hashCode()
        result = 31 * result + kotlinxLocalDate.hashCode()
        result = 31 * result + localTime.hashCode()
        result = 31 * result + kotlinxLocalTime.hashCode()
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + offsetDateTime.hashCode()
        result = 31 * result + uuid.hashCode()
        return result
    }
}

val h2AllTypesNotNull = H2AllTypesNotNullEntity(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    1,
    1L,
    byteArrayOf(0x2A),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
    ),
    UUID.randomUUID()
)

object H2AllTypesNotNulls : H2Table<H2AllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNotNullWithTimeEntity::localTime, precision = 9)
    val kotlinxLocalTim = time(AllTypesNotNullWithTimeEntity::kotlinxLocalTime, precision = 9)
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNotNullEntity::int)
    val longe = bigInt(AllTypesNotNullEntity::long)
    val byteArray = binary(AllTypesNotNullEntity::byteArray)
    val offsetDateTime = timestampWithTimeZone(H2AllTypesNotNullEntity::offsetDateTime)
    val uuid = uuid(H2AllTypesNotNullEntity::uuid)
}

data class H2AllTypesNullableEntity(
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

        other as H2AllTypesNullableEntity

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

val h2AllTypesNullable = H2AllTypesNullableEntity(
    1, null, null, null, null, null, null, null,
    null, null, null, null, null, null, null
)

object H2AllTypesNullables : H2Table<H2AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableWithTimeEntity::localTime) // todo test fractionalSecondsPart later
    val kotlinxLocalTim = time(AllTypesNullableWithTimeEntity::kotlinxLocalTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableEntity::int)
    val longe = bigInt(AllTypesNullableEntity::long)
    val byteArray = binary(AllTypesNullableEntity::byteArray)
    val offsetDateTime = timestampWithTimeZone(H2AllTypesNullableEntity::offsetDateTime)
    val uuid = uuid(H2AllTypesNullableEntity::uuid)
}

data class H2AllTypesNullableDefaultValueEntity(
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
)

val h2AllTypesNullableDefaultValue = H2AllTypesNullableDefaultValueEntity(1)
val h2AllTypesNullableDefaultValueToInsert = H2AllTypesNullableDefaultValueEntity(2)

object H2AllTypesNullableDefaultValues : H2Table<H2AllTypesNullableDefaultValueEntity>() {
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
        AllTypesNullableDefaultValueWithTimeEntity::localTime, precision = 9,
        defaultValue = LocalTime.of(11, 25, 55, 123456789)
    )
    val kotlinxLocalTim = time(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalTime, precision = 9,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55, 123456789)
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
    val inte = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val offsetDateTime = timestampWithTimeZone(
        H2AllTypesNullableDefaultValueEntity::offsetDateTime,
        defaultValue = OffsetDateTime.of(
            2019, 11, 4, 0, 0, 0, 0,
            ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
        )
    )
    val uuid = uuid(H2AllTypesNullableDefaultValueEntity::uuid, defaultValue = UUID.fromString(defaultUuid))
}

object H2LocalDates : H2Table<LocalDateEntity>(), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object H2KotlinxLocalDates : H2Table<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object H2LocalDateTimes : H2Table<LocalDateTimeEntity>(), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object H2LocalDateTimeAsTimestamps : H2Table<LocalDateTimeAsTimestampEntity>(), LocalDateTimeAsTimestamps {
    override val id = integer(LocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object H2KotlinxLocalDateTimes : H2Table<KotlinxLocalDateTimeEntity>(), KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object H2KotlinxLocalDateTimeAsTimestamps : H2Table<KotlinxLocalDateTimeAsTimestampEntity>(),
    KotlinxLocalDateTimeAsTimestamps {
    override val id = integer(KotlinxLocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object H2OffsetDateTimes : H2Table<OffsetDateTimeEntity>(), OffsetDateTimes {
    override val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    override val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    override val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object H2LocalTimes : H2Table<LocalTimeEntity>(), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object H2KotlinxLocalTimes : H2Table<KotlinxLocalTimeEntity>(), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(KotlinxLocalTimeEntity::localTimeNullable)
}

object H2Ints : H2Table<IntEntity>(), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object H2Longs : H2Table<LongEntity>(), Longs {
    override val id = autoIncrementBigInt(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object H2Uuids : H2Table<UuidEntity>(), Uuids {
    override val id = uuid(UuidEntity::id)
        .primaryKey()
    override val uuidNotNull = uuid(UuidEntity::uuidNotNull)
    override val uuidNullable = uuid(UuidEntity::uuidNullable)
}

object H2Inheriteds : H2Table<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    override val firstname = varchar(Inherited::firstname)
}

object H2JavaUsers : H2Table<JavaUser>("java_users"), JavaUsers {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object H2Customers : H2Table<CustomerEntity>(), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = varchar(CustomerEntity::name)
        .unique()
    override val country = varchar(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object H2ByteArrays : H2Table<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

object H2ByteArrayAsBinaries : H2Table<ByteArrayAsBinaryEntity>(), ByteArrayAsBinaries {
    override val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    override val byteArrayNotNull = binary(ByteArrayAsBinaryEntity::byteArrayNotNull)
    override val byteArrayNullable = binary(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val h2Tables = tables().h2(
    H2Roles,
    H2Users,
    H2UserRoles,
    H2AllTypesNotNulls,
    H2AllTypesNullables,
    H2AllTypesNullableDefaultValues,
    H2LocalDates,
    H2KotlinxLocalDates,
    H2LocalDateTimes,
    H2LocalDateTimeAsTimestamps,
    H2KotlinxLocalDateTimes,
    H2KotlinxLocalDateTimeAsTimestamps,
    H2OffsetDateTimes,
    H2LocalTimes,
    H2KotlinxLocalTimes,
    H2Ints,
    H2Longs,
    H2Uuids,
    H2Inheriteds,
    H2JavaUsers,
    H2Customers,
    H2ByteArrays,
    H2ByteArrayAsBinaries
)
