/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import org.ufoss.kotysa.mariadb.MariadbTable
import org.ufoss.kotysa.mariadb.date
import org.ufoss.kotysa.mariadb.dateTime
import org.ufoss.kotysa.mariadb.time
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object MariadbRoles : MariadbTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = varchar(RoleEntity::label)
        .unique()
}

object MariadbUsers : MariadbTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id, "PK_users")
        .primaryKey()
    override val firstname = varchar(UserEntity::firstname, "fname")
    override val lastname = varchar(UserEntity::lastname, "lname")
    override val isAdmin = boolean(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(MariadbRoles.id, "FK_users_roles")
    override val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object MariadbUserRoles : MariadbTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(MariadbUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(MariadbRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class MariadbAllTypesNotNull(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localDateTime1: LocalDateTime,
    override val localDateTime2: LocalDateTime,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    override val int: Int,
    override val long: Long,
    override val byteArray: ByteArray,
) : AllTypesNotNullEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MariadbAllTypesNotNull

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1.truncatedTo(ChronoUnit.SECONDS)
            != other.localDateTime1.truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (localDateTime2.truncatedTo(ChronoUnit.SECONDS)
            != other.localDateTime2.truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
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
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + id
        return result
    }
}

val mariadbAllTypesNotNull = MariadbAllTypesNotNull(
    1, "",
    true, LocalDate.now(), Clock.System.todayIn(TimeZone.UTC), LocalDateTime.now(), LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC), Clock.System.now().toLocalDateTime(TimeZone.UTC), 1,
    1L, byteArrayOf(0x2A)
)

object MariadbAllTypesNotNulls : MariadbTable<MariadbAllTypesNotNull>("all_types") {
    val id = integer(MariadbAllTypesNotNull::id)
        .primaryKey()
    val string = varchar(MariadbAllTypesNotNull::string)
    val boolean = boolean(MariadbAllTypesNotNull::boolean)
    val localDate = date(MariadbAllTypesNotNull::localDate)
    val kotlinxLocalDate = date(MariadbAllTypesNotNull::kotlinxLocalDate)
    val localDateTime1 = dateTime(MariadbAllTypesNotNull::localDateTime1)
    val localDateTime2 = dateTime(MariadbAllTypesNotNull::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(MariadbAllTypesNotNull::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(MariadbAllTypesNotNull::kotlinxLocalDateTime2)
    val inte = integer(MariadbAllTypesNotNull::int)
    val longe = bigInt(MariadbAllTypesNotNull::long)
    val byteArray = binary(MariadbAllTypesNotNull::byteArray)
}

data class MariadbAllTypesNotNullWithTime(
    override val id: Int,
    override val string: String,
    override val boolean: Boolean,
    override val localDate: LocalDate,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
    override val localDateTime1: LocalDateTime,
    override val localDateTime2: LocalDateTime,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
    override val int: Int,
    override val long: Long,
    override val byteArray: ByteArray,
    override val localTime: LocalTime,
    override val kotlinxLocalTime: kotlinx.datetime.LocalTime,
) : AllTypesNotNullWithTimeEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, localTime, kotlinxLocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MariadbAllTypesNotNullWithTime

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime.truncatedTo(ChronoUnit.SECONDS)) return false
        if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
            != kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)) return false
        if (localDateTime1.truncatedTo(ChronoUnit.SECONDS)
            != other.localDateTime1.truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (localDateTime2.truncatedTo(ChronoUnit.SECONDS)
            != other.localDateTime2.truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
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
        result = 31 * result + localTime.hashCode()
        result = 31 * result + kotlinxLocalTime.hashCode()
        result = 31 * result + localDateTime1.hashCode()
        result = 31 * result + localDateTime2.hashCode()
        result = 31 * result + kotlinxLocalDateTime1.hashCode()
        result = 31 * result + kotlinxLocalDateTime2.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        result = 31 * result + id
        return result
    }
}

val mariadbAllTypesNotNullWithTime = MariadbAllTypesNotNullWithTime(
    1, "",
    true, LocalDate.now(), Clock.System.todayIn(TimeZone.UTC), LocalDateTime.now(), LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC), Clock.System.now().toLocalDateTime(TimeZone.UTC), 1,
    1L, byteArrayOf(0x2A), LocalTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC).time
)

object MariadbAllTypesNotNullWithTimes : MariadbTable<MariadbAllTypesNotNullWithTime>("all_types_with_times") {
    val id = integer(MariadbAllTypesNotNullWithTime::id)
        .primaryKey()
    val string = varchar(MariadbAllTypesNotNullWithTime::string)
    val boolean = boolean(MariadbAllTypesNotNullWithTime::boolean)
    val localDate = date(MariadbAllTypesNotNullWithTime::localDate)
    val kotlinxLocalDate = date(MariadbAllTypesNotNullWithTime::kotlinxLocalDate)
    val localTim = time(MariadbAllTypesNotNullWithTime::localTime) // todo test fractionalSecondsPart later
    val kotlinxLocalTim = time(MariadbAllTypesNotNullWithTime::kotlinxLocalTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(MariadbAllTypesNotNullWithTime::localDateTime1)
    val localDateTime2 = dateTime(MariadbAllTypesNotNullWithTime::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(MariadbAllTypesNotNullWithTime::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(MariadbAllTypesNotNullWithTime::kotlinxLocalDateTime2)
    val inte = integer(MariadbAllTypesNotNullWithTime::int)
    val longe = bigInt(MariadbAllTypesNotNullWithTime::long)
    val byteArray = binary(MariadbAllTypesNotNullWithTime::byteArray)
}

object MariadbAllTypesNullables : MariadbTable<AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableEntity::int)
    val longe = bigInt(AllTypesNullableEntity::long)
    val byteArray = binary(AllTypesNullableEntity::byteArray)
}

object MariadbAllTypesNullableWithTimes :
    MariadbTable<AllTypesNullableWithTimeEntity>("all_types_nullable_with_times") {
    val id = integer(AllTypesNullableWithTimeEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableWithTimeEntity::string)
    val localDate = date(AllTypesNullableWithTimeEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableWithTimeEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableWithTimeEntity::localTime) // todo test fractionalSecondsPart later
    val kotlinxLocalTim = time(AllTypesNullableWithTimeEntity::kotlinxLocalTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableWithTimeEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNullableWithTimeEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableWithTimeEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableWithTimeEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableWithTimeEntity::int)
    val longe = bigInt(AllTypesNullableWithTimeEntity::long)
    val byteArray = binary(AllTypesNullableWithTimeEntity::byteArray)
}

object MariadbAllTypesNullableDefaultValues : MariadbTable<AllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(
        AllTypesNullableDefaultValueEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
        defaultValue = LocalDate(2019, 11, 6)
    )
    val localDateTime1 = dateTime(
        AllTypesNullableDefaultValueEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = dateTime(
        AllTypesNullableDefaultValueEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = dateTime(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
        defaultValue = LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = dateTime(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
        defaultValue = LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = bigInt(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
}

object MariadbAllTypesNullableDefaultValueWithTimes : MariadbTable<AllTypesNullableDefaultValueWithTimeEntity>() {
    val id = integer(AllTypesNullableDefaultValueWithTimeEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueWithTimeEntity::string, defaultValue = "default")
    val localDate = date(
        AllTypesNullableDefaultValueWithTimeEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDate,
        defaultValue = LocalDate(2019, 11, 6)
    )
    val localTim = time(
        AllTypesNullableDefaultValueWithTimeEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55, 123456789)
    )
    val kotlinxLocalTim = time(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalTime,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55, 123456789)
    )
    val localDateTime1 = dateTime(
        AllTypesNullableDefaultValueWithTimeEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = dateTime(
        AllTypesNullableDefaultValueWithTimeEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = dateTime(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDateTime1,
        defaultValue = LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = dateTime(
        AllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDateTime2,
        defaultValue = LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = integer(AllTypesNullableDefaultValueWithTimeEntity::int, defaultValue = 42)
    val longe = bigInt(AllTypesNullableDefaultValueWithTimeEntity::long, defaultValue = 84L)
}

object MariadbLocalDates : MariadbTable<LocalDateEntity>(), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MariadbKotlinxLocalDates : MariadbTable<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MariadbLocalDateTimes : MariadbTable<LocalDateTimeEntity>(), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MariadbKotlinxLocalDateTimes : MariadbTable<KotlinxLocalDateTimeEntity>(), KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object MariadbLocalTimes : MariadbTable<LocalTimeEntity>(), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object MariadbKotlinxLocalTimes : MariadbTable<KotlinxLocalTimeEntity>(), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(KotlinxLocalTimeEntity::localTimeNullable)
}

object MariadbInts : MariadbTable<IntEntity>(), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object MariadbLongs : MariadbTable<LongEntity>(), Longs {
    override val id = autoIncrementBigInt(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object MariadbInheriteds : MariadbTable<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    override val firstname = varchar(Inherited::firstname)
}

object MariadbJavaUsers : MariadbTable<JavaUser>("java_users"), JavaUsers {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object MariadbCustomers : MariadbTable<CustomerEntity>(), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = varchar(CustomerEntity::name)
        .unique()
    override val country = varchar(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object MariadbTinyTexts : MariadbTable<StringAsTinyTextEntity>(), TinyTexts {
    override val id = integer(StringAsTinyTextEntity::id)
        .primaryKey()
    override val stringNotNull = tinyText(StringAsTinyTextEntity::stringNotNull)
    override val stringNullable = tinyText(StringAsTinyTextEntity::stringNullable)
}

object MariadbTexts : MariadbTable<StringAsTextEntity>(), Texts {
    override val id = integer(StringAsTextEntity::id)
        .primaryKey()
    override val stringNotNull = text(StringAsTextEntity::stringNotNull)
    override val stringNullable = text(StringAsTextEntity::stringNullable)
}

object MariadbMediumTexts : MariadbTable<StringAsMediumTextEntity>(), MediumTexts {
    override val id = integer(StringAsMediumTextEntity::id)
        .primaryKey()
    override val stringNotNull = mediumText(StringAsMediumTextEntity::stringNotNull)
    override val stringNullable = mediumText(StringAsMediumTextEntity::stringNullable)
}

object MariadbLongTexts : MariadbTable<StringAsLongTextEntity>(), LongTexts {
    override val id = integer(StringAsLongTextEntity::id)
        .primaryKey()
    override val stringNotNull = longText(StringAsLongTextEntity::stringNotNull)
    override val stringNullable = longText(StringAsLongTextEntity::stringNullable)
}

object MariadbByteArrays : MariadbTable<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

object MariadbByteArrayAsBinaries : MariadbTable<ByteArrayAsBinaryEntity>(), ByteArrayAsBinaries {
    override val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    override val byteArrayNotNull = binary(ByteArrayAsBinaryEntity::byteArrayNotNull)
    override val byteArrayNullable = binary(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val mariadbTables = tables().mariadb(
    MariadbRoles,
    MariadbUsers,
    MariadbUserRoles,
    MariadbAllTypesNotNulls,
    MariadbAllTypesNullables,
    MariadbAllTypesNullableDefaultValues,
    MariadbAllTypesNotNullWithTimes,
    MariadbAllTypesNullableWithTimes,
    MariadbAllTypesNullableDefaultValueWithTimes,
    MariadbLocalDates,
    MariadbKotlinxLocalDates,
    MariadbLocalDateTimes,
    MariadbKotlinxLocalDateTimes,
    MariadbLocalTimes,
    MariadbKotlinxLocalTimes,
    MariadbInts,
    MariadbLongs,
    MariadbInheriteds,
    MariadbJavaUsers,
    MariadbCustomers,
    MariadbTinyTexts,
    MariadbTexts,
    MariadbMediumTexts,
    MariadbLongTexts,
    MariadbByteArrays,
    MariadbByteArrayAsBinaries,
)
