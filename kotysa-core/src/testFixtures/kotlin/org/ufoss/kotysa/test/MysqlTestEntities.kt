/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.toJavaLocalDateTime
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit


object MYSQL_ROLE : MysqlTable<RoleEntity>("roles") {
    val id = integer(RoleEntity::id)
            .primaryKey()
    val label = varchar(RoleEntity::label)
}

object MYSQL_USER : MysqlTable<UserEntity>("users") {
    val id = integer(UserEntity::id, "PK_users")
            .primaryKey()
    val firstname = varchar(UserEntity::firstname, "fname")
    val lastname = varchar(UserEntity::lastname, "lname")
    val isAdmin = boolean(UserEntity::isAdmin)
    val roleId = integer(UserEntity::roleId)
            .foreignKey(MYSQL_ROLE.id, "FK_users_roles")
    val alias = varchar(UserEntity::alias)
}

object MYSQL_ALL_TYPES_NOT_NULL : MysqlTable<AllTypesNotNullEntity>("all_types") {
    val id = integer(AllTypesNotNullEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNotNullEntity::string)
    val boolean = boolean(AllTypesNotNullEntity::boolean)
    val localDate = date(AllTypesNotNullEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNotNullEntity::kotlinxLocalDate)
    val localTime = time(AllTypesNotNullEntity::localTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNotNullEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNotNullEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNotNullEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNotNullEntity::int)
}

object MYSQL_ALL_TYPES_NULLABLE : MysqlTable<AllTypesNullableEntity>("all_types_nullable") {
    val id = integer(AllTypesNullableEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableEntity::string)
    val localDate = date(AllTypesNullableEntity::localDate)
    val kotlinxLocalDate = date(AllTypesNullableEntity::kotlinxLocalDate)
    val localTime = time(AllTypesNullableEntity::localTime) // todo test fractionalSecondsPart later
    val localDateTime1 = dateTime(AllTypesNullableEntity::localDateTime1)
    val localDateTime2 = dateTime(AllTypesNullableEntity::localDateTime2)
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime1)
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableEntity::kotlinxLocalDateTime2)
    val int = integer(AllTypesNullableEntity::int)
}

object MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE : MysqlTable<AllTypesNullableDefaultValueEntity>() {
    val id = integer(AllTypesNullableDefaultValueEntity::id)
            .primaryKey()
    val string = varchar(AllTypesNullableDefaultValueEntity::string, defaultValue = "default")
    val localDate = date(AllTypesNullableDefaultValueEntity::localDate,
            defaultValue = LocalDate.of(2019, 11, 4))
    val kotlinxLocalDate = date(AllTypesNullableDefaultValueEntity::kotlinxLocalDate,
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6))
    val localTim = time(AllTypesNullableDefaultValueEntity::localTime, precision = 9,
            defaultValue = LocalTime.of(11, 25, 55, 123456789))
    val localDateTime1 = dateTime(AllTypesNullableDefaultValueEntity::localDateTime1,
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0))
    val localDateTime2 = dateTime(AllTypesNullableDefaultValueEntity::localDateTime2,
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0))
    val kotlinxLocalDateTime1 = dateTime(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1,
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
    val kotlinxLocalDateTime2 = dateTime(AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2,
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0))
    val int = integer(AllTypesNullableDefaultValueEntity::int, defaultValue = 42)
}

object MYSQL_LOCAL_DATE : MysqlTable<LocalDateEntity>() {
    val id = integer(LocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(LocalDateEntity::localDateNotNull)
    val localDateNullable = date(LocalDateEntity::localDateNullable)
}

object MYSQL_KOTLINX_LOCAL_DATE : MysqlTable<KotlinxLocalDateEntity>() {
    val id = integer(KotlinxLocalDateEntity::id)
            .primaryKey()
    val localDateNotNull = date(KotlinxLocalDateEntity::localDateNotNull)
    val localDateNullable = date(KotlinxLocalDateEntity::localDateNullable)
}

object MYSQL_LOCAL_DATE_TIME : MysqlTable<LocalDateTimeEntity>() {
    val id = integer(LocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(LocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(LocalDateTimeEntity::localDateTimeNullable)
}

object MYSQL_KOTLINX_LOCAL_DATE_TIME : MysqlTable<KotlinxLocalDateTimeEntity>() {
    val id = integer(KotlinxLocalDateTimeEntity::id)
            .primaryKey()
    val localDateTimeNotNull = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    val localDateTimeNullable = dateTime(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

// object MYSQL_OFFSET_DATE_TIME : MysqlTable<OffsetDateTimeEntity>() {
//     val id = integer(OffsetDateTimeEntity::id)
//             .primaryKey()
//     val offsetDateTimeNotNull = timestamp(OffsetDateTimeEntity::offsetDateTimeNotNull)
//     val offsetDateTimeNullable = timestamp(OffsetDateTimeEntity::offsetDateTimeNullable)
// }

object MYSQL_LOCAL_TIME : MysqlTable<LocalTimeEntity>() {
    val id = integer(LocalTimeEntity::id)
            .primaryKey()
    val localTimeNotNull = time(LocalTimeEntity::localTimeNotNull)
    val localTimeNullable = time(LocalTimeEntity::localTimeNullable)
}

object MYSQL_INT : MysqlTable<IntEntity>() {
    val id = autoIncrementInteger(IntEntity::id)
            .primaryKey()
    val intNotNull = integer(IntEntity::intNotNull)
    val intNullable = integer(IntEntity::intNullable)
}

private fun LocalTime.roundToSecond(): LocalTime {
    var time = this
    if (nano >= 500_000_000) {
        time = plusSeconds(1)
    }
    return time.truncatedTo(ChronoUnit.SECONDS)
}

private fun LocalDateTime.roundToSecond(): LocalDateTime {
    var localDateTime = this
    if (nano >= 500_000_000) {
        localDateTime = plusSeconds(1)
    }
    return localDateTime.truncatedTo(ChronoUnit.SECONDS)
}

data class MysqlAllTypesNotNull(
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
        override val int: Int
) : AllTypesNotNullEntity(
        id, string, boolean, localDate, kotlinxLocalDate, localTime, localDateTime1, localDateTime2,
        kotlinxLocalDateTime1, kotlinxLocalDateTime2, int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNotNullEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTime.roundToSecond() != other.localTime.roundToSecond()) return false
        if (localDateTime1.roundToSecond() != other.localDateTime1.roundToSecond()) return false
        if (localDateTime2.roundToSecond() != other.localDateTime2.roundToSecond()) return false
        if (kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()
                != other.kotlinxLocalDateTime1.toJavaLocalDateTime().roundToSecond()) return false
        if (kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()
                != other.kotlinxLocalDateTime2.toJavaLocalDateTime().roundToSecond()) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (localDate.hashCode())
        result = 31 * result + (kotlinxLocalDate.hashCode())
        result = 31 * result + (localTime.hashCode())
        result = 31 * result + (localDateTime1.hashCode())
        result = 31 * result + (localDateTime2.hashCode())
        result = 31 * result + (kotlinxLocalDateTime1.hashCode())
        result = 31 * result + (kotlinxLocalDateTime2.hashCode())
        result = 31 * result + (int)
        result = 31 * result + (id)
        return result
    }
}

object MYSQL_INHERITED : MysqlTable<Inherited>(), ENTITY<Inherited>, NAMEABLE<Inherited> {
    override val id = varchar(Inherited::getId)
            .primaryKey()
    override val name = varchar(Inherited::name)
    val firstname = varchar(Inherited::firstname)
}

object MYSQL_JAVA_USER : MysqlTable<JavaUser>("java_users"), JAVA_USER {
    override val login = varchar(JavaUser::getLogin)
            .primaryKey()
    override val firstname = varchar(JavaUser::getFirstname, "fname")
    override val lastname = varchar(JavaUser::getLastname, "lname")
    override val isAdmin = boolean(JavaUser::isAdmin)
    override val alias1 = varchar(JavaUser::getAlias1)
    override val alias2 = varchar(JavaUser::getAlias2)
    override val alias3 = varchar(JavaUser::getAlias3 as (JavaUser) -> String?)
}

val mysqlTables =
        tables().mysql(
                MYSQL_ROLE,
                MYSQL_USER,
                MYSQL_ALL_TYPES_NOT_NULL,
                MYSQL_ALL_TYPES_NULLABLE,
                MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
                MYSQL_LOCAL_DATE,
                MYSQL_KOTLINX_LOCAL_DATE,
                MYSQL_LOCAL_DATE_TIME,
                MYSQL_KOTLINX_LOCAL_DATE_TIME,
//                 MYSQL_OFFSET_DATE_TIME,
                MYSQL_LOCAL_TIME,
                MYSQL_INT,
                MYSQL_INHERITED,
                MYSQL_JAVA_USER,
        )