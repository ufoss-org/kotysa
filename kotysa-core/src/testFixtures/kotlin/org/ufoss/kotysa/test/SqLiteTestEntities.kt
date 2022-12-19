/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.sqlite.text
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object SqliteRoles : SqLiteTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = text(RoleEntity::label)
        .unique()
}

object SqliteUsers : SqLiteTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = text(UserEntity::firstname, "fname")
    override val lastname = text(UserEntity::lastname, "lname")
    override val isAdmin = integer(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(SqliteRoles.id, "FK_users_roles")
    override val alias = text(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object SqliteUserRoles : SqLiteTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(SqliteUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(SqliteRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

class SqliteAllTypesNotNullWithTimeEntity(
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
    val localTime: LocalTime,
    val kotlinxLocalTime: kotlinx.datetime.LocalTime,
) : AllTypesNotNullBaseEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double
) {
    // equals and hashcode (this class is not a data class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SqliteAllTypesNotNullWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + localTime.hashCode()
        result = 31 * result + kotlinxLocalTime.hashCode()
        return result
    }
}


val sqliteAllTypesNotNullWithTime = SqliteAllTypesNotNullWithTimeEntity(
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
    LocalTime.now(),
    Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
)

object SqliteAllTypesNotNullWithTimes : SqLiteTable<SqliteAllTypesNotNullWithTimeEntity>("all_types") {
    val id = integer(SqliteAllTypesNotNullWithTimeEntity::id)
        .primaryKey()
    val string = text(SqliteAllTypesNotNullWithTimeEntity::string)
    val boolean = integer(SqliteAllTypesNotNullWithTimeEntity::boolean)
    val localDate = text(SqliteAllTypesNotNullWithTimeEntity::localDate)
    val kotlinxLocalDate = text(SqliteAllTypesNotNullWithTimeEntity::kotlinxLocalDate)
    val localTim = text(SqliteAllTypesNotNullWithTimeEntity::localTime)
    val kotlinxLocalTim = text(SqliteAllTypesNotNullWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = text(SqliteAllTypesNotNullWithTimeEntity::localDateTime1)
    val localDateTime2 = text(SqliteAllTypesNotNullWithTimeEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(SqliteAllTypesNotNullWithTimeEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(SqliteAllTypesNotNullWithTimeEntity::kotlinxLocalDateTime2)
    val inte = integer(SqliteAllTypesNotNullWithTimeEntity::int)
    val longe = integer(SqliteAllTypesNotNullWithTimeEntity::long)
    val byteArray = blob(SqliteAllTypesNotNullWithTimeEntity::byteArray)
    val float = real(SqliteAllTypesNotNullWithTimeEntity::float)
    val double = real(SqliteAllTypesNotNullWithTimeEntity::double)
}

class SqliteAllTypesNullableWithTimeEntity(
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
    val localTime: LocalTime?,
    val kotlinxLocalTime: kotlinx.datetime.LocalTime?,
) : AllTypesNullableBaseEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SqliteAllTypesNullableWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
        return result
    }
}

val sqliteAllTypesNullableWithTime = SqliteAllTypesNullableWithTimeEntity(
    1, null, null, null, null, null,
    null, null, null, null, null,
    null, null, null, null
)

object SqliteAllTypesNullableWithTimes : SqLiteTable<SqliteAllTypesNullableWithTimeEntity>("all_types_nullable") {
    val id = integer(SqliteAllTypesNullableWithTimeEntity::id)
        .primaryKey()
    val string = text(SqliteAllTypesNullableWithTimeEntity::string)
    val localDate = text(SqliteAllTypesNullableWithTimeEntity::localDate)
    val kotlinxLocalDate = text(SqliteAllTypesNullableWithTimeEntity::kotlinxLocalDate)
    val localTim = text(SqliteAllTypesNullableWithTimeEntity::localTime)
    val kotlinxLocalTim = text(SqliteAllTypesNullableWithTimeEntity::kotlinxLocalTime)
    val localDateTime1 = text(SqliteAllTypesNullableWithTimeEntity::localDateTime1)
    val localDateTime2 = text(SqliteAllTypesNullableWithTimeEntity::localDateTime2)
    val kotlinxLocalDateTime1 = text(SqliteAllTypesNullableWithTimeEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = text(SqliteAllTypesNullableWithTimeEntity::kotlinxLocalDateTime2)
    val inte = integer(SqliteAllTypesNullableWithTimeEntity::int)
    val longe = integer(SqliteAllTypesNullableWithTimeEntity::long)
    val byteArray = blob(SqliteAllTypesNullableWithTimeEntity::byteArray)
    val float = real(SqliteAllTypesNullableWithTimeEntity::float)
    val double = real(SqliteAllTypesNullableWithTimeEntity::double)
}

class SqliteAllTypesNullableDefaultValueWithTimeEntity(
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
    val localTime: LocalTime? = null,
    val kotlinxLocalTime: kotlinx.datetime.LocalTime? = null,
) : AllTypesNullableDefaultValueBaseEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SqliteAllTypesNullableDefaultValueWithTimeEntity

        if (localTime != other.localTime) return false
        if (kotlinxLocalTime != other.kotlinxLocalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (localTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalTime?.hashCode() ?: 0)
        return result
    }
}

val sqliteAllTypesNullableDefaultValueWithTime = SqliteAllTypesNullableDefaultValueWithTimeEntity(1)
val sqliteAllTypesNullableDefaultValueWithTimeToInsert = SqliteAllTypesNullableDefaultValueWithTimeEntity(2)

object SqliteAllTypesNullableDefaultValueWithTimes :
    SqLiteTable<SqliteAllTypesNullableDefaultValueWithTimeEntity>("all_types_nullable_default_value") {
    val id = integer(SqliteAllTypesNullableDefaultValueWithTimeEntity::id)
        .primaryKey()
    val string = text(SqliteAllTypesNullableDefaultValueWithTimeEntity::string, defaultValue = "default")
    val localDate = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::localDate,
        defaultValue = LocalDate.of(2019, 11, 4)
    )
    val kotlinxLocalDate = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDate,
        defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
    )
    val localTim = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::localTime,
        defaultValue = LocalTime.of(11, 25, 55)
    )
    val kotlinxLocalTim = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalTime,
        defaultValue = kotlinx.datetime.LocalTime(11, 25, 55)
    )
    val localDateTime1 = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::localDateTime1,
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    )
    val localDateTime2 = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::localDateTime2,
        defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime1 = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDateTime1,
        defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
    )
    val kotlinxLocalDateTime2 = text(
        SqliteAllTypesNullableDefaultValueWithTimeEntity::kotlinxLocalDateTime2,
        defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
    )
    val inte = integer(SqliteAllTypesNullableDefaultValueWithTimeEntity::int, "sqlite_integer", 42)
    val longe = integer(SqliteAllTypesNullableDefaultValueWithTimeEntity::long, defaultValue = 84L)
    val float = real(SqliteAllTypesNullableDefaultValueWithTimeEntity::float, defaultValue = 42.42f)
    val double = real(SqliteAllTypesNullableDefaultValueWithTimeEntity::double, defaultValue = 84.84)
}

object SqliteLocalDates : SqLiteTable<LocalDateEntity>("local_date"), LocalDates {
    override val id = integer(LocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = text(LocalDateEntity::localDateNotNull)
    override val localDateNullable = text(LocalDateEntity::localDateNullable)
}

object SqliteKotlinxLocalDates : SqLiteTable<KotlinxLocalDateEntity>("kotlinx_local_date"),
    KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = text(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = text(KotlinxLocalDateEntity::localDateNullable)
}

object SqliteLocalDateTimes : SqLiteTable<LocalDateTimeEntity>("local_date_time"), LocalDateTimes {
    override val id = integer(LocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = text(LocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = text(LocalDateTimeEntity::localDateTimeNullable)
}

object SqliteKotlinxLocalDateTimes : SqLiteTable<KotlinxLocalDateTimeEntity>("kotlinx_local_date_time"),
    KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = text(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = text(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object SqliteOffsetDateTimes : SqLiteTable<OffsetDateTimeEntity>("offset_date_time"), OffsetDateTimes {
    override val id = integer(OffsetDateTimeEntity::id)
        .primaryKey()
    override val offsetDateTimeNotNull = text(OffsetDateTimeEntity::offsetDateTimeNotNull)
    override val offsetDateTimeNullable = text(OffsetDateTimeEntity::offsetDateTimeNullable)
}

object SqliteLocalTimes : SqLiteTable<LocalTimeEntity>("local_time"), LocalTimes {
    override val id = integer(LocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = text(LocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = text(LocalTimeEntity::localTimeNullable)
}

object SqliteKotlinxLocalTimes : SqLiteTable<KotlinxLocalTimeEntity>("local_time"), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = text(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = text(KotlinxLocalTimeEntity::localTimeNullable)
}

object SqliteInts : SqLiteTable<IntEntity>("ints"), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object SqliteLongs : SqLiteTable<LongEntity>("longs"), Longs {
    override val id = autoIncrementInteger(LongEntity::id)
        .primaryKey()
    override val longNotNull = integer(LongEntity::longNotNull)
    override val longNullable = integer(LongEntity::longNullable)
}

object SqliteFloats : SqLiteTable<FloatEntity>(), Floats {
    override val id = integer(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = real(FloatEntity::floatNotNull)
    override val floatNullable = real(FloatEntity::floatNullable)
}

object SqliteDoubles : SqLiteTable<DoubleEntity>(), Doubles {
    override val id = integer(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = real(DoubleEntity::doubleNotNull)
    override val doubleNullable = real(DoubleEntity::doubleNullable)
}

object SqliteCustomers : SqLiteTable<CustomerEntity>("customer"), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = text(CustomerEntity::name)
        .unique()
    override val country = text(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object SqliteByteArrays : SqLiteTable<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

val sqLiteTables = tables().sqlite(
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles,
    SqliteAllTypesNotNullWithTimes,
    SqliteAllTypesNullableWithTimes,
    SqliteAllTypesNullableDefaultValueWithTimes,
    SqliteLocalDates,
    SqliteKotlinxLocalDates,
    SqliteLocalDateTimes,
    SqliteKotlinxLocalDateTimes,
    SqliteOffsetDateTimes,
    SqliteLocalTimes,
    SqliteKotlinxLocalTimes,
    SqliteInts,
    SqliteLongs,
    SqliteCustomers,
    SqliteByteArrays,
    SqliteFloats,
    SqliteDoubles,
)
