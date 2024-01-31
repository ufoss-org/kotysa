/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.mysql.date
import org.ufoss.kotysa.mysql.dateTime
import org.ufoss.kotysa.mysql.time
import org.ufoss.kotysa.tables
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MysqlCompanies : MysqlTable<CompanyEntity>("companies"), Companies {
    override val id = integer(CompanyEntity::id)
        .primaryKey()
    override val name = varchar(CompanyEntity::name)
        .unique()
}

object MysqlRoles : MysqlTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = varchar(RoleEntity::label)
        .unique()
}

object MysqlUsers : MysqlTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id, "PK_users")
        .primaryKey()
    override val firstname = varchar(UserEntity::firstname, "fname")
    override val lastname = varchar(UserEntity::lastname, "lname")
    override val isAdmin = boolean(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(MysqlRoles.id, "FK_users_roles")
    override val companyId = integer(UserEntity::companyId)
        .foreignKey(MysqlCompanies.id, "FK_users_companies")
    override val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object MysqlUserRoles : MysqlTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(MysqlUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(MysqlRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class MysqlAllTypesNotNull(
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
    override val float: Float,
    override val double: Double,
    override val bigDecimal1: BigDecimal,
    override val bigDecimal2: BigDecimal,
) : AllTypesNotNullEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNotNull

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1.roundToSecond() != other.localDateTime1.roundToSecond()) return false
        if (localDateTime2.roundToSecond() != other.localDateTime2.roundToSecond()) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()
        ) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

val mysqlAllTypesNotNull = MysqlAllTypesNotNull(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    6666.75f,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
)

object MysqlAllTypesNotNulls : MysqlTable<MysqlAllTypesNotNull>("all_types") {
    val id = integer(MysqlAllTypesNotNull::id)
        .primaryKey()
    val string = varchar(MysqlAllTypesNotNull::string)
    val boolean = boolean(MysqlAllTypesNotNull::boolean)
    val localDate = date(MysqlAllTypesNotNull::localDate)
    val kotlinxLocalDate = date(MysqlAllTypesNotNull::kotlinxLocalDate)
    val localDateTime1 = dateTime(MysqlAllTypesNotNull::localDateTime1)
    val localDateTime2 = dateTime(MysqlAllTypesNotNull::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(MysqlAllTypesNotNull::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(MysqlAllTypesNotNull::kotlinxLocalDateTime2)
    val inte = integer(MysqlAllTypesNotNull::int)
    val longe = bigInt(MysqlAllTypesNotNull::long)
    val byteArray = binary(MysqlAllTypesNotNull::byteArray)
    val floate = float(MysqlAllTypesNotNull::float)
    val doublee = doublePrecision(MysqlAllTypesNotNull::double)
    val bigDecimal1 = numeric(MysqlAllTypesNotNull::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(MysqlAllTypesNotNull::bigDecimal2, 3, 1)
}

data class MysqlAllTypesNotNullWithTime(
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
    override val float: Float,
    override val double: Double,
    override val bigDecimal1: BigDecimal,
    override val bigDecimal2: BigDecimal,
) : AllTypesNotNullWithTimeEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNotNullWithTime

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime.roundToSecond() != other.localTime.roundToSecond()) return false
        if (kotlinxLocalTime.toJavaLocalTime().roundToSecond()
            != kotlinxLocalTime.toJavaLocalTime().roundToSecond()
        ) return false
        if (localDateTime1.roundToSecond() != other.localDateTime1.roundToSecond()) return false
        if (localDateTime2.roundToSecond() != other.localDateTime2.roundToSecond()) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()
        ) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

val mysqlAllTypesNotNullWithTime = MysqlAllTypesNotNullWithTime(
    1,
    "",
    true,
    LocalDate.now(),
    Clock.System.todayIn(TimeZone.UTC),
    LocalDateTime.now(),
    LocalDateTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Clock.System.now().toLocalDateTime(TimeZone.UTC),
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
    6666.75f,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
)

object MysqlAllTypesNotNullWithTimes : MysqlTable<MysqlAllTypesNotNullWithTime>("all_types_with_times") {
    val id = integer(MysqlAllTypesNotNullWithTime::id)
        .primaryKey()
    val string = varchar(MysqlAllTypesNotNullWithTime::string)
    val boolean = boolean(MysqlAllTypesNotNullWithTime::boolean)
    val localDate = date(MysqlAllTypesNotNullWithTime::localDate)
    val kotlinxLocalDate = date(MysqlAllTypesNotNullWithTime::kotlinxLocalDate)
    val localTim = time(MysqlAllTypesNotNullWithTime::localTime) // todo test fractionalSecondsPart later
    val kotlinxLocalTim = time(MysqlAllTypesNotNullWithTime::kotlinxLocalTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(MysqlAllTypesNotNullWithTime::localDateTime1)
    val localDateTime2 = dateTime(MysqlAllTypesNotNullWithTime::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(MysqlAllTypesNotNullWithTime::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(MysqlAllTypesNotNullWithTime::kotlinxLocalDateTime2)
    val inte = integer(MysqlAllTypesNotNullWithTime::int)
    val longe = bigInt(MysqlAllTypesNotNullWithTime::long)
    val byteArray = binary(MysqlAllTypesNotNullWithTime::byteArray)
    val floate = float(MysqlAllTypesNotNullWithTime::float)
    val doublee = doublePrecision(MysqlAllTypesNotNullWithTime::double)
    val bigDecimal1 = numeric(MysqlAllTypesNotNullWithTime::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(MysqlAllTypesNotNullWithTime::bigDecimal2, 3, 1)
}

object MysqlAllTypesNullables : MysqlTable<AllTypesNullableEntity>("all_types_nullable") {
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
    val floate = float(AllTypesNullableEntity::float)
    val doublee = doublePrecision(AllTypesNullableEntity::double)
    val bigDecimal1 = numeric(AllTypesNullableEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNullableEntity::bigDecimal2, 3, 1)
}

object MysqlAllTypesNullableWithTimes :
    MysqlTable<AllTypesNullableWithTimeEntity>("all_types_nullable_with_types") {
    val id = integer(AllTypesNullableWithTimeEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNullableWithTimeEntity::string)
    val localDate = date(AllTypesNullableWithTimeEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableWithTimeEntity::kotlinxLocalDate)
    val localTim = time(AllTypesNullableWithTimeEntity::localTime) // todo test fractionalSecondsPart later
    val kotlinxLocalTim =
        time(AllTypesNullableWithTimeEntity::kotlinxLocalTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableWithTimeEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNullableWithTimeEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableWithTimeEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableWithTimeEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNullableWithTimeEntity::int)
    val longe = bigInt(AllTypesNullableWithTimeEntity::long)
    val byteArray = binary(AllTypesNullableWithTimeEntity::byteArray)
    val floate = float(AllTypesNullableWithTimeEntity::float)
    val doublee = doublePrecision(AllTypesNullableWithTimeEntity::double)
    val bigDecimal1 = numeric(AllTypesNullableWithTimeEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNullableWithTimeEntity::bigDecimal2, 3, 1)
}

object MysqlAllTypesNullableDefaultValues : MysqlTable<AllTypesNullableDefaultValueEntity>() {
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
    val floate = float(AllTypesNullableDefaultValueEntity::float, defaultValue = 42.42f)
    val doublee = doublePrecision(AllTypesNullableDefaultValueEntity::double, defaultValue = 84.84)
    val bigDecimal1 = numeric(
        AllTypesNullableDefaultValueEntity::bigDecimal1,
        3,
        1,
        defaultValue = BigDecimal("4.2")
    )
    val bigDecimal2 = decimal(
        AllTypesNullableDefaultValueEntity::bigDecimal2,
        3,
        1,
        defaultValue = BigDecimal("4.3")
    )
}

object MysqlAllTypesNullableDefaultValueWithTimes : MysqlTable<AllTypesNullableDefaultValueWithTimeEntity>() {
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
        defaultValue = LocalTime(11, 25, 55, 123456789)
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
    val floate = float(AllTypesNullableDefaultValueWithTimeEntity::float, defaultValue = 42.42f)
    val doublee = doublePrecision(AllTypesNullableDefaultValueWithTimeEntity::double, defaultValue = 84.84)
    val bigDecimal1 = numeric(
        AllTypesNullableDefaultValueWithTimeEntity::bigDecimal1,
        3,
        1,
        defaultValue = BigDecimal("4.2")
    )
    val bigDecimal2 = decimal(
        AllTypesNullableDefaultValueWithTimeEntity::bigDecimal2,
        3,
        1,
        defaultValue = BigDecimal("4.3")
    )
}

object MysqlLocalDates : MysqlTable<LocalDateEntity>(), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MysqlKotlinxLocalDates : MysqlTable<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MysqlLocalDateTimes : MysqlTable<LocalDateTimeEntity>(), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MysqlKotlinxLocalDateTimes : MysqlTable<KotlinxLocalDateTimeEntity>(), KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object MysqlLocalTimes : MysqlTable<LocalTimeEntity>(), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object MysqlKotlinxLocalTimes : MysqlTable<KotlinxLocalTimeEntity>(), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = time(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = time(KotlinxLocalTimeEntity::localTimeNullable)
}

object MysqlInts : MysqlTable<IntEntity>(), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object MysqlIntNonNullIds : MysqlTable<IntNonNullIdEntity>(), IntNonNullIds {
    override val id = autoIncrementInteger(IntNonNullIdEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntNonNullIdEntity::intNotNull)
    override val intNullable = integer(IntNonNullIdEntity::intNullable)
}

object MysqlLongs : MysqlTable<LongEntity>(), Longs {
    override val id = autoIncrementBigInt(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object MysqlLongNonNullIds : MysqlTable<LongNonNullIdEntity>(), LongNonNullIds {
    override val id = autoIncrementBigInt(LongNonNullIdEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongNonNullIdEntity::longNotNull)
    override val longNullable = bigInt(LongNonNullIdEntity::longNullable)
}

object MysqlFloats : MysqlTable<FloatEntity>(), Floats {
    override val id = integer(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = float(FloatEntity::floatNotNull, precision = 3, scale = 1)
    override val floatNullable = float(FloatEntity::floatNullable, precision = 3, scale = 1)
}

object MysqlDoubles : MysqlTable<DoubleEntity>(), Doubles {
    override val id = integer(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = doublePrecision(DoubleEntity::doubleNotNull)
    override val doubleNullable = doublePrecision(DoubleEntity::doubleNullable)
}

object MysqlBigDecimals : MysqlTable<BigDecimalEntity>(), BigDecimals {
    override val id = integer(BigDecimalEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = decimal(BigDecimalEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = decimal(BigDecimalEntity::bigDecimalNullable, 3, 1)
}

object MysqlBigDecimalAsNumerics : MysqlTable<BigDecimalAsNumericEntity>(), BigDecimalAsNumerics {
    override val id = integer(BigDecimalAsNumericEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = numeric(BigDecimalAsNumericEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = numeric(BigDecimalAsNumericEntity::bigDecimalNullable, 3, 1)
}

object MysqlInheriteds : MysqlTable<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    override val firstname = varchar(Inherited::firstname)
}

object MysqlJavaUsers : MysqlTable<JavaUser>("java_users"), JavaUsers {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object MysqlCustomers : MysqlTable<CustomerEntity>(), Customers {
    override val id = bigInt(CustomerEntity::id)
        .primaryKey()
    override val name = varchar(CustomerEntity::name)
        .unique()
    override val country = varchar(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object MysqlTinyTexts : MysqlTable<StringAsTinyTextEntity>(), TinyTexts {
    override val id = integer(StringAsTinyTextEntity::id)
        .primaryKey()
    override val stringNotNull = tinyText(StringAsTinyTextEntity::stringNotNull)
    override val stringNullable = tinyText(StringAsTinyTextEntity::stringNullable)
}

object MysqlTexts : MysqlTable<StringAsTextEntity>(), Texts {
    override val id = integer(StringAsTextEntity::id)
        .primaryKey()
    override val stringNotNull = text(StringAsTextEntity::stringNotNull)
    override val stringNullable = text(StringAsTextEntity::stringNullable)
}

object MysqlMediumTexts : MysqlTable<StringAsMediumTextEntity>(), MediumTexts {
    override val id = integer(StringAsMediumTextEntity::id)
        .primaryKey()
    override val stringNotNull = mediumText(StringAsMediumTextEntity::stringNotNull)
    override val stringNullable = mediumText(StringAsMediumTextEntity::stringNullable)
}

object MysqlLongTexts : MysqlTable<StringAsLongTextEntity>(), LongTexts {
    override val id = integer(StringAsLongTextEntity::id)
        .primaryKey()
    override val stringNotNull = longText(StringAsLongTextEntity::stringNotNull)
    override val stringNullable = longText(StringAsLongTextEntity::stringNullable)
}

object MysqlByteArrays : MysqlTable<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

object MysqlByteArrayAsBinaries : MysqlTable<ByteArrayAsBinaryEntity>(), ByteArrayAsBinaries {
    override val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    override val byteArrayNotNull = binary(ByteArrayAsBinaryEntity::byteArrayNotNull)
    override val byteArrayNullable = binary(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val mysqlTables = tables().mysql(
    MysqlCompanies,
    MysqlRoles,
    MysqlUsers,
    MysqlUserRoles,
    MysqlAllTypesNotNulls,
    MysqlAllTypesNullables,
    MysqlAllTypesNullableDefaultValues,
    MysqlAllTypesNotNullWithTimes,
    MysqlAllTypesNullableWithTimes,
    MysqlAllTypesNullableDefaultValueWithTimes,
    MysqlLocalDates,
    MysqlKotlinxLocalDates,
    MysqlLocalDateTimes,
    MysqlKotlinxLocalDateTimes,
    MysqlLocalTimes,
    MysqlKotlinxLocalTimes,
    MysqlInts,
    MysqlIntNonNullIds,
    MysqlLongs,
    MysqlLongNonNullIds,
    MysqlInheriteds,
    MysqlJavaUsers,
    MysqlCustomers,
    MysqlTinyTexts,
    MysqlTexts,
    MysqlMediumTexts,
    MysqlLongTexts,
    MysqlByteArrays,
    MysqlByteArrayAsBinaries,
    MysqlFloats,
    MysqlDoubles,
    MysqlBigDecimals,
    MysqlBigDecimalAsNumerics,
)
