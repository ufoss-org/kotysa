/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import org.ufoss.kotysa.oracle.OracleTable
import org.ufoss.kotysa.oracle.date
import org.ufoss.kotysa.oracle.timestamp
import org.ufoss.kotysa.tables
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

object OracleRoles : OracleTable<RoleEntity>("roles"), Roles {
    override val id = number(RoleEntity::id)
        .primaryKey()
    override val label = varchar2(RoleEntity::label)
        .unique()
}

object OracleUsers : OracleTable<UserEntity>("users"), Users {
    override val id = number(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = varchar2(UserEntity::firstname, "fname")
    override val lastname = varchar2(UserEntity::lastname, "lname")
    override val isAdmin = number(UserEntity::isAdmin)
    override val roleId = number(UserEntity::roleId)
        .foreignKey(OracleRoles.id, "FK_users_roles")
    override val alias = varchar2(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object OracleUserRoles : OracleTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = number(UserRoleEntity::userId)
        .foreignKey(OracleUsers.id)
    override val roleId = number(UserRoleEntity::roleId)
        .foreignKey(OracleRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class OracleAllTypesNotNullEntity(
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
    val offsetDateTime: OffsetDateTime,
) : AllTypesNotNullEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2
) {

    // Must override equals for LocalDateTime truncation
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OracleAllTypesNotNullEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localDateTime1.truncatedTo(ChronoUnit.SECONDS) != other.localDateTime1.truncatedTo(ChronoUnit.SECONDS)) return false
        if (localDateTime2.truncatedTo(ChronoUnit.SECONDS) != other.localDateTime2.truncatedTo(ChronoUnit.SECONDS)) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (!offsetDateTime.truncatedTo(ChronoUnit.MILLIS).isEqual(other.offsetDateTime.truncatedTo(ChronoUnit.MILLIS))) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + offsetDateTime.hashCode()
        return result
    }
}

val oracleAllTypesNotNull = OracleAllTypesNotNullEntity(
    1,
    "a string",
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
    Float.MAX_VALUE,
    6666.75,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
    ),
)

object OracleAllTypesNotNulls : OracleTable<OracleAllTypesNotNullEntity>("all_types") {
    val id = number(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar2(AllTypesNotNullEntity::string)
    val boolean = number(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localDateTime1 = timestamp(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = number(AllTypesNotNullEntity::int)
    val longe = number(AllTypesNotNullEntity::long)
    val byteArray = raw(AllTypesNotNullEntity::byteArray)
    val floate = binaryFloat(AllTypesNotNullEntity::float)
    val doublee = binaryDouble(AllTypesNotNullEntity::double)
    val bigDecimal1 = number(AllTypesNotNullEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = number(AllTypesNotNullEntity::bigDecimal2, 3, 1)
    val offsetDateTime = timestampWithTimeZone(OracleAllTypesNotNullEntity::offsetDateTime)
}

data class OracleAllTypesNullableEntity(
    override val id: Int,
    override val string: String?,
    override val localDate: LocalDate?,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
    override val localDateTime1: LocalDateTime?,
    override val localDateTime2: LocalDateTime?,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
    override val int: Int?,
    override val long: Long?,
    override val byteArray: ByteArray?,
    override val float: Float?,
    override val double: Double?,
    override val bigDecimal1: BigDecimal?,
    override val bigDecimal2: BigDecimal?,
    val offsetDateTime: OffsetDateTime?,
) : AllTypesNullableEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as OracleAllTypesNullableEntity

        if (offsetDateTime != other.offsetDateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        return result
    }
}

val oracleAllTypesNullable = OracleAllTypesNullableEntity(
    1, null, null, null, null, null, null,
    null, null, null, null, null, null,
    null, null, null,
)

object OracleAllTypesNullables : OracleTable<OracleAllTypesNullableEntity>("all_types_nullable") {
    val id = number(AllTypesNullableEntity::id)
        .primaryKey()
    val string = varchar2(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localDateTime1 = timestamp(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = timestamp(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = timestamp(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val inte = number(AllTypesNullableEntity::int)
    val longe = number(AllTypesNullableEntity::long)
    val byteArray = raw(AllTypesNullableEntity::byteArray)
    val floate = binaryFloat(AllTypesNullableEntity::float)
    val doublee = binaryDouble(AllTypesNullableEntity::double)
    val bigDecimal1 = number(AllTypesNullableEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = number(AllTypesNullableEntity::bigDecimal2, 3, 1)
    val offsetDateTime = timestampWithTimeZone(OracleAllTypesNullableEntity::offsetDateTime)
}

data class OracleAllTypesNullableDefaultValueEntity(
    override val id: Int,
    override val string: String? = null,
    override val localDate: LocalDate? = null,
    override val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
    override val localDateTime1: LocalDateTime? = null,
    override val localDateTime2: LocalDateTime? = null,
    override val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
    override val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
    override val int: Int? = null,
    override val long: Long? = null,
    override val float: Float? = null,
    override val double: Double? = null,
    override val bigDecimal1: BigDecimal? = null,
    override val bigDecimal2: BigDecimal? = null,
    val offsetDateTime: OffsetDateTime? = null,
) : AllTypesNullableDefaultValueEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double, bigDecimal1, bigDecimal2
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as OracleAllTypesNullableDefaultValueEntity

        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        return result
    }
}

val oracleAllTypesNullableDefaultValue = OracleAllTypesNullableDefaultValueEntity(1)
val oracleAllTypesNullableDefaultValueToInsert = OracleAllTypesNullableDefaultValueEntity(2)

object OracleAllTypesNullableDefaultValues : OracleTable<OracleAllTypesNullableDefaultValueEntity>() {
    val id = number(AllTypesNullableDefaultValueEntity::id)
        .primaryKey()
    val string = varchar2(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(
        AllTypesNullableDefaultValueEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = date(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
        defaultValue = LocalDate(2019, 11, 6)
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
        defaultValue = LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = timestamp(
        AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
        defaultValue = LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = number(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
    val longe = number(AllTypesNullableDefaultValueEntity::long, defaultValue = 84L)
    val floate = binaryFloat(AllTypesNullableDefaultValueEntity::float, defaultValue = 42.42f)
    val doublee = binaryDouble(AllTypesNullableDefaultValueEntity::double, defaultValue = 84.84)
    val bigDecimal1 = number(
        AllTypesNullableDefaultValueEntity::bigDecimal1,
        3,
        1,
        defaultValue = BigDecimal("4.2")
    )
    val bigDecimal2 = number(
        AllTypesNullableDefaultValueEntity::bigDecimal2,
        3,
        1,
        defaultValue = BigDecimal("4.3")
    )
    val offsetDateTime = timestampWithTimeZone(
        OracleAllTypesNullableDefaultValueEntity::offsetDateTime,
        defaultValue = OffsetDateTime.of(
            2019, 11, 4, 0, 0, 0, 0,
            ZoneOffset.ofHoursMinutes(1, 2)
        )
    )
}

object OracleLocalDates : OracleTable<LocalDateEntity>(), LocalDates {
    override val id = number(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object OracleKotlinxLocalDates : OracleTable<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = number(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object OracleLocalDateTimeAsTimestamps : OracleTable<LocalDateTimeAsTimestampEntity>(),
    LocalDateTimeAsTimestamps {
    override val id = number(LocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(LocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object OracleKotlinxLocalDateTimeAsTimestamps : OracleTable<KotlinxLocalDateTimeAsTimestampEntity>(),
    KotlinxLocalDateTimeAsTimestamps {
    override val id = number(KotlinxLocalDateTimeAsTimestampEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNotNull)
    override val localDateTimeNullable = timestamp(KotlinxLocalDateTimeAsTimestampEntity::localDateTimeNullable)
}

object OracleOffsetDateTimes : OracleTable<OffsetDateTimeEntity>(), OffsetDateTimes {
    override val id = number(OffsetDateTimeEntity::id)
        .primaryKey()
    override val offsetDateTimeNotNull = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNotNull)
    override val offsetDateTimeNullable = timestampWithTimeZone(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object OracleInts : OracleTable<IntEntity>(), Ints {
    override val id = number(IntEntity::id)
        .identity()
        .primaryKey()
    override val intNotNull = number(IntEntity::intNotNull)
    override val intNullable = number(IntEntity::intNullable)
}

object OracleLongs : OracleTable<LongEntity>(), Longs {
    override val id = number(LongEntity::id)
        .identity()
        .primaryKey()
    override val longNotNull = number(LongEntity::longNotNull)
    override val longNullable = number(LongEntity::longNullable)
}

object OracleFloats : OracleTable<FloatEntity>(), Floats {
    override val id = number(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = binaryFloat(FloatEntity::floatNotNull)
    override val floatNullable = binaryFloat(FloatEntity::floatNullable)
}

object OracleDoubles : OracleTable<DoubleEntity>(), Doubles {
    override val id = number(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = binaryDouble(DoubleEntity::doubleNotNull)
    override val doubleNullable = binaryDouble(DoubleEntity::doubleNullable)
}

object OracleBigDecimals : OracleTable<BigDecimalEntity>(), BigDecimals {
    override val id = number(BigDecimalEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = number(BigDecimalEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = number(BigDecimalEntity::bigDecimalNullable, 3, 1)
}

object OracleInheriteds : OracleTable<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar2(Inherited::getId)
        .primaryKey()
    override val name = varchar2(Inherited::name)
    override val firstname = varchar2(Inherited::firstname)
}

object OracleJavaUsers : OracleTable<JavaUser>("java_users"), JavaUsers {
    override val login = varchar2(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar2(JavaUser::getFirstname, "fname")
    override val lastname = varchar2(JavaUser::getLastname, "lname")
    override val isAdmin = number(JavaUser::isAdmin)
    override val alias1 = varchar2(JavaUser::getAlias1)
    override val alias2 = varchar2(JavaUser::getAlias2)
    override val alias3 = varchar2(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object OracleCustomers : OracleTable<CustomerEntity>(), Customers {
    override val id = number(CustomerEntity::id)
        .primaryKey()
    override val name = varchar2(CustomerEntity::name)
        .unique()
    override val country = varchar2(CustomerEntity::country)
    override val age = number(CustomerEntity::age)
}

object OracleByteArrays : OracleTable<ByteArrayEntity>(), ByteArrays {
    override val id = number(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = raw(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = raw(ByteArrayEntity::byteArrayNullable)
}

val oracleTables = tables().oracle(
    OracleRoles,
    OracleUsers,
    OracleUserRoles,
    OracleAllTypesNotNulls,
    OracleAllTypesNullables,
    OracleAllTypesNullableDefaultValues,
    OracleLocalDates,
    OracleKotlinxLocalDates,
    OracleLocalDateTimeAsTimestamps,
    OracleKotlinxLocalDateTimeAsTimestamps,
    OracleOffsetDateTimes,
    OracleInts,
    OracleLongs,
    OracleInheriteds,
    OracleJavaUsers,
    OracleCustomers,
    OracleByteArrays,
    OracleFloats,
    OracleDoubles,
    OracleBigDecimals,
)
