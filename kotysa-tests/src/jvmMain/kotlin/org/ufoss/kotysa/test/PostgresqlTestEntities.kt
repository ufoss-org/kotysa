/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.ufoss.kotysa.postgresql.*
import org.ufoss.kotysa.tables
import java.math.BigDecimal
import java.time.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

object PostgresqlCompanies : PostgresqlTable<CompanyEntity>("companies"), Companies {
    override val id = integer(CompanyEntity::id)
        .primaryKey()
    override val name = varchar(CompanyEntity::name)
        .unique()
}

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
    override val companyId = integer(UserEntity::companyId)
        .foreignKey(PostgresqlCompanies.id, "FK_users_companies")
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
    override val float: Float,
    override val double: Double,
    override val bigDecimal1: BigDecimal,
    override val bigDecimal2: BigDecimal,
    val offsetDateTime: OffsetDateTime,
    val uuid: UUID,
) : AllTypesNotNullWithTimeEntity(
    id, string, boolean, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNotNullEntity

        if (id != other.id) return false
        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime.truncatedTo(ChronoUnit.SECONDS) != other.localTime.truncatedTo(ChronoUnit.SECONDS)) return false
        if (kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
            != other.kotlinxLocalTime.toJavaLocalTime().truncatedTo(ChronoUnit.SECONDS)
        ) return false
        if (localDateTime1.truncatedTo(ChronoUnit.MILLIS) != other.localDateTime1.truncatedTo(ChronoUnit.MILLIS)) return false
        if (localDateTime2.truncatedTo(ChronoUnit.MILLIS) != other.localDateTime2.truncatedTo(ChronoUnit.MILLIS)) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
            != other.kotlinxLocalDateTime1.toJavaLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
        ) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
            != other.kotlinxLocalDateTime2.toJavaLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
        ) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (!byteArray.contentEquals(other.byteArray)) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (bigDecimal1 != other.bigDecimal1) return false
        if (bigDecimal2 != other.bigDecimal2) return false
        if (!offsetDateTime.truncatedTo(ChronoUnit.MILLIS)
                .isEqual(other.offsetDateTime.truncatedTo(ChronoUnit.MILLIS))
        ) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + offsetDateTime.hashCode()
        result = 31 * result + uuid.hashCode()
        return result
    }
}

val postgresqlAllTypesNotNull = PostgresqlAllTypesNotNullEntity(
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
    Int.MAX_VALUE,
    Long.MAX_VALUE,
    byteArrayOf(0x2A),
    Float.MAX_VALUE,
    Double.MAX_VALUE,
    BigDecimal("1.1"),
    BigDecimal("2.2"),
    OffsetDateTime.of(
        2018, 11, 4, 0, 0, 0, 0,
        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
    ),
    UUID.randomUUID(),
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
    val float = real(AllTypesNotNullEntity::float)
    val double = doublePrecision(AllTypesNotNullEntity::double)
    val bigDecimal1 = numeric(AllTypesNotNullEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNotNullEntity::bigDecimal2, 3, 1)
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
    override val float: Float?,
    override val double: Double?,
    override val bigDecimal1: BigDecimal?,
    override val bigDecimal2: BigDecimal?,
    val offsetDateTime: OffsetDateTime?,
    val uuid: UUID?,
) : AllTypesNullableWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, byteArray, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as PostgresqlAllTypesNullableEntity

        if (offsetDateTime != other.offsetDateTime) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        return result
    }
}

val postgresqlAllTypesNullable = PostgresqlAllTypesNullableEntity(
    1, null, null, null, null, null, null,
    null, null, null, null, null, null,
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
    val float = real(AllTypesNullableEntity::float)
    val double = doublePrecision(AllTypesNullableEntity::double)
    val bigDecimal1 = numeric(AllTypesNullableEntity::bigDecimal1, 3, 1)
    val bigDecimal2 = decimal(AllTypesNullableEntity::bigDecimal2, 3, 1)
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
    override val float: Float? = null,
    override val double: Double? = null,
    override val bigDecimal1: BigDecimal? = null,
    override val bigDecimal2: BigDecimal? = null,
    val offsetDateTime: OffsetDateTime? = null,
    val uuid: UUID? = null,
) : AllTypesNullableDefaultValueWithTimeEntity(
    id, string, localDate, kotlinxLocalDate, localDateTime1, localDateTime2, kotlinxLocalDateTime1,
    kotlinxLocalDateTime2, int, long, float, double, bigDecimal1, bigDecimal2, localTime, kotlinxLocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostgresqlAllTypesNullableDefaultValueEntity

        if (id != other.id) return false
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
        if (float != other.float) return false
        if (double != other.double) return false
        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
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
    val float = real(AllTypesNullableDefaultValueEntity::float, defaultValue = 42.42f)
    val double = doublePrecision(AllTypesNullableDefaultValueEntity::double, defaultValue = 84.84)
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

object PostgresqlIntNonNullIds : PostgresqlTable<IntNonNullIdEntity>(), IntNonNullIds {
    override val id = serial(IntNonNullIdEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntNonNullIdEntity::intNotNull)
    override val intNullable = integer(IntNonNullIdEntity::intNullable)
}

object PostgresqlIntAsIdentities : PostgresqlTable<IntEntityAsIdentity>(), IntAsIdentities {
    override val id = integer(IntEntityAsIdentity::id)
        .identity()
        .primaryKey()
    override val intNotNull = integer(IntEntityAsIdentity::intNotNull)
    override val intNullable = integer(IntEntityAsIdentity::intNullable)
}

object PostgresqlLongs : PostgresqlTable<LongEntity>(), Longs {
    override val id = bigSerial(LongEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongEntity::longNotNull)
    override val longNullable = bigInt(LongEntity::longNullable)
}

object PostgresqlLongNonNullIds : PostgresqlTable<LongNonNullIdEntity>(), LongNonNullIds {
    override val id = bigSerial(LongNonNullIdEntity::id)
        .primaryKey()
    override val longNotNull = bigInt(LongNonNullIdEntity::longNotNull)
    override val longNullable = bigInt(LongNonNullIdEntity::longNullable)
}

object PostgresqlFloats : PostgresqlTable<FloatEntity>(), Floats {
    override val id = integer(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = real(FloatEntity::floatNotNull)
    override val floatNullable = real(FloatEntity::floatNullable)
}

object PostgresqlDoubles : PostgresqlTable<DoubleEntity>(), Doubles {
    override val id = integer(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = doublePrecision(DoubleEntity::doubleNotNull)
    override val doubleNullable = doublePrecision(DoubleEntity::doubleNullable)
}

object PostgresqlBigDecimals : PostgresqlTable<BigDecimalEntity>(), BigDecimals {
    override val id = integer(BigDecimalEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = decimal(BigDecimalEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = decimal(BigDecimalEntity::bigDecimalNullable, 3, 1)
}

object PostgresqlBigDecimalAsNumerics : PostgresqlTable<BigDecimalAsNumericEntity>(), BigDecimalAsNumerics {
    override val id = integer(BigDecimalAsNumericEntity::id)
        .primaryKey()
    override val bigDecimalNotNull = numeric(BigDecimalAsNumericEntity::bigDecimalNotNull, 3, 1)
    override val bigDecimalNullable = numeric(BigDecimalAsNumericEntity::bigDecimalNullable, 3, 1)
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
    override val id = bigInt(CustomerEntity::id)
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
    GenericAllTypesNotNulls,
    GenericAllTypesNullables,
    GenericAllTypesNullableDefaultValues,
    PostgresqlCompanies,
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
    PostgresqlIntAsIdentities,
    PostgresqlInts,
    PostgresqlIntNonNullIds,
    PostgresqlLongs,
    PostgresqlLongNonNullIds,
    PostgresqlUuids,
    PostgresqlInheriteds,
    PostgresqlJavaUsers,
    PostgresqlCustomers,
    PostgresqlTexts,
    PostgresqlByteArrayAsByteas,
    PostgresqlTsvectors,
    PostgresqlFloats,
    PostgresqlDoubles,
    PostgresqlBigDecimals,
    PostgresqlBigDecimalAsNumerics,
)
