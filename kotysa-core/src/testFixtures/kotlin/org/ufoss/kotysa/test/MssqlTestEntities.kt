/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.mssql.date
import org.ufoss.kotysa.mssql.dateTime
import org.ufoss.kotysa.tables
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object MssqlRoles : MssqlTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = varchar(RoleEntity::label)
        .unique()
}

object MssqlUsers : MssqlTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = varchar(UserEntity::firstname, "fname")
    override val lastname = varchar(UserEntity::lastname, "lname")
    override val isAdmin = bit(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(MssqlRoles.id, "FK_users_roles")
    override val alias = varchar(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object MssqlUserRoles : MssqlTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(MssqlUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(MssqlRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

data class MssqlAllTypesNotNull(
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

    // Must override equals for LocalDateTime truncation
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MssqlAllTypesNotNull

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

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

val mssqlAllTypesNotNull = MssqlAllTypesNotNull(
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
    Float.MAX_VALUE,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
)

object MssqlAllTypesNotNulls : MssqlTable<MssqlAllTypesNotNull>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
        .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = bit(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val inte = integer(AllTypesNotNullEntity::int)
    val longe = bigInt(AllTypesNotNullEntity::long)
    val byteArray = binary(AllTypesNotNullEntity::byteArray)
    val float = real(AllTypesNotNullEntity::float)
    val doublee = float(AllTypesNotNullEntity::double)
    val bigDecimal1 = numeric(AllTypesNotNullEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNotNullEntity::bigDecimal2, 3, 1)
}

object MssqlAllTypesNullables : MssqlTable<AllTypesNullableEntity>("all_types_nullable") {
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
    val float = real(AllTypesNullableEntity::float)
    val doublee = float(AllTypesNullableEntity::double)
    val bigDecimal1 = numeric(AllTypesNullableEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNullableEntity::bigDecimal2, 3, 1)
}

object MssqlAllTypesNullableDefaultValues : MssqlTable<AllTypesNullableDefaultValueEntity>() {
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
    val float = real(AllTypesNullableDefaultValueEntity::float, defaultValue = 42.42f)
    val doublee = float(AllTypesNullableDefaultValueEntity::double, defaultValue = 84.84)
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

object MssqlLocalDates : MssqlTable<LocalDateEntity>(), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    override val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MssqlKotlinxLocalDates : MssqlTable<KotlinxLocalDateEntity>(), KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MssqlLocalDateTimes : MssqlTable<LocalDateTimeEntity>(), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MssqlKotlinxLocalDateTimes : MssqlTable<KotlinxLocalDateTimeEntity>(), KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object MssqlInts : MssqlTable<IntEntity>(), Ints {
    override val id = identityInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object MssqlLongs : MssqlTable<LongEntity>(), Longs {
    override val id = identityBigInt(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object MssqlFloats : MssqlTable<FloatEntity>(), Floats {
    override val id = integer(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = real(FloatEntity::floatNotNull)
    override val floatNullable = real(FloatEntity::floatNullable)
}

object MssqlDoubles : MssqlTable<DoubleEntity>(), Doubles {
    override val id = integer(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = float(DoubleEntity::doubleNotNull)
    override val doubleNullable = float(DoubleEntity::doubleNullable)
}

object MssqlBigDecimals : MssqlTable<BigDecimalEntity>(), BigDecimals {
    override val id = integer(BigDecimalEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = decimal(BigDecimalEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = decimal(BigDecimalEntity::bigDecimalNullable, 3, 1)
}

object MssqlBigDecimalAsNumerics : MssqlTable<BigDecimalAsNumericEntity>(), BigDecimalAsNumerics {
    override val id = integer(BigDecimalAsNumericEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = numeric(BigDecimalAsNumericEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = numeric(BigDecimalAsNumericEntity::bigDecimalNullable, 3, 1)
}

object MssqlInheriteds : MssqlTable<Inherited>(), Entities<Inherited>, Nameables<Inherited>, Inheriteds {
    override val id = varchar(Inherited::getId)
        .primaryKey()
    override val name = varchar(Inherited::name)
    override val firstname = varchar(Inherited::firstname)
}

object MssqlJavaUsers : MssqlTable<JavaUser>("java_users"), JavaUsers {
    override val login = varchar(JavaUser::getLogin)
        .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = bit(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

object MssqlCustomers : MssqlTable<CustomerEntity>(), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = varchar(CustomerEntity::name)
        .unique()
    override val country = varchar(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object MssqlByteArrayAsBinaries : MssqlTable<ByteArrayAsBinaryEntity>(), ByteArrayAsBinaries {
    override val id = integer(ByteArrayAsBinaryEntity::id)
        .primaryKey()
    override val byteArrayNotNull = binary(ByteArrayAsBinaryEntity::byteArrayNotNull)
    override val byteArrayNullable = binary(ByteArrayAsBinaryEntity::byteArrayNullable)
}

val mssqlTables = tables().mssql(
    MssqlRoles,
    MssqlUsers,
    MssqlUserRoles,
    MssqlAllTypesNotNulls,
    MssqlAllTypesNullables,
    MssqlAllTypesNullableDefaultValues,
    MssqlLocalDates,
    MssqlKotlinxLocalDates,
    MssqlLocalDateTimes,
    MssqlKotlinxLocalDateTimes,
    MssqlInts,
    MssqlLongs,
    MssqlInheriteds,
    MssqlJavaUsers,
    MssqlCustomers,
    MssqlByteArrayAsBinaries,
    MssqlFloats,
    MssqlDoubles,
    MssqlBigDecimals,
    MssqlBigDecimalAsNumerics,
)
