/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.ufoss.kotysa.tables
import java.time.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

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

val mysqlTables =
        tables().mysql {
            table<MysqlRole> {
                name = "roles"
                column { it[MysqlRole::id].integer() }
                        .primaryKey()
                column { it[MysqlRole::label].varchar {
                    size = 255
                } }
            }
            table<MysqlUser> {
                name = "users"
                column { it[MysqlUser::id].integer() }
                        .primaryKey("PK_users")
                column { it[MysqlUser::firstname].varchar {
                    name = "fname"
                    size = 255
                } }
                column { it[MysqlUser::lastname].varchar() {
                    name = "lname"
                    size = 255
                } }
                column { it[MysqlUser::isAdmin].boolean() }
                column { it[MysqlUser::roleId].integer() }
                        .foreignKey<MysqlRole>("FK_users_roles")
                column { it[MysqlUser::alias].varchar {
                    size = 255
                } }
            }
            table<MysqlAllTypesNotNull> {
                name = "all_types"
                column { it[MysqlAllTypesNotNull::id].integer() }
                        .primaryKey()
                column { it[MysqlAllTypesNotNull::string].varchar {
                    size = 255
                } }
                column { it[MysqlAllTypesNotNull::boolean].boolean() }
                column { it[MysqlAllTypesNotNull::localDate].date() }
                column { it[MysqlAllTypesNotNull::kotlinxLocalDate].date() }
                column { it[MysqlAllTypesNotNull::localTim].time() }
                column { it[MysqlAllTypesNotNull::localDateTime].dateTime() }
                column { it[MysqlAllTypesNotNull::kotlinxLocalDateTime].dateTime() }
                column { it[MysqlAllTypesNotNull::int].integer {
                    name = "mysql_integer"
                } }
            }
            table<MysqlAllTypesNullable> {
                name = "all_types_nullable"
                column { it[MysqlAllTypesNullable::id].integer() }
                        .primaryKey()
                column { it[MysqlAllTypesNullable::string].varchar {
                    size = 255
                } }
                column { it[MysqlAllTypesNullable::localDate].date() }
                column { it[MysqlAllTypesNullable::kotlinxLocalDate].date() }
                column { it[MysqlAllTypesNullable::localTim].time {
                    fractionalSecondsPart = 0
                } }
                column { it[MysqlAllTypesNullable::localDateTime].dateTime() }
                column { it[MysqlAllTypesNullable::kotlinxLocalDateTime].dateTime() }
                column { it[MysqlAllTypesNullable::int].integer {
                    name = "mysql_integer"
                } }
            }
            table<MysqlAllTypesNullableDefaultValue> {
                column { it[MysqlAllTypesNullableDefaultValue::id].integer() }
                        .primaryKey()
                column { it[MysqlAllTypesNullableDefaultValue::string].varchar {
                    defaultValue = "default"
                    size = 255
                } }
                column { it[MysqlAllTypesNullableDefaultValue::localDate].date {
                    defaultValue = LocalDate.of(2019, 11, 4)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::kotlinxLocalDate].date {
                    defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::localTim].time {
                    defaultValue = LocalTime.of(11, 25, 55)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::localDateTime].dateTime() {
                    defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::kotlinxLocalDateTime].dateTime {
                    defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::int].integer {
                    name = "mysql_integer"
                    defaultValue = 42
                } }
            }
            table<MysqlUuid> {
                column { it[MysqlUuid::id].uuid() }
                        .primaryKey()
                column { it[MysqlUuid::uuidNotNull].uuid() }
                column { it[MysqlUuid::uuidNullable].uuid() }
            }
            table<MysqlLocalDate> {
                column { it[MysqlLocalDate::id].integer() }
                        .primaryKey()
                column { it[MysqlLocalDate::localDateNotNull].date() }
                column { it[MysqlLocalDate::localDateNullable].date() }
            }
            table<MysqlKotlinxLocalDate> {
                column { it[MysqlKotlinxLocalDate::id].integer() }
                        .primaryKey()
                column { it[MysqlKotlinxLocalDate::localDateNotNull].date() }
                column { it[MysqlKotlinxLocalDate::localDateNullable].date() }
            }
            table<MysqlLocalDateTime> {
                column { it[MysqlLocalDateTime::id].integer() }
                        .primaryKey()
                column { it[MysqlLocalDateTime::localDateTimeNotNull].dateTime() }
                column { it[MysqlLocalDateTime::localDateTimeNullable].dateTime() }
            }
            table<MysqlKotlinxLocalDateTime> {
                column { it[MysqlKotlinxLocalDateTime::id].integer() }
                        .primaryKey()
                column { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull].dateTime() }
                column { it[MysqlKotlinxLocalDateTime::localDateTimeNullable].dateTime() }
            }
            table<MysqlOffsetDateTime> {
                column { it[MysqlOffsetDateTime::id].integer() }
                        .primaryKey()
                column { it[MysqlOffsetDateTime::offsetDateTimeNotNull].dateTime() }
                column { it[MysqlOffsetDateTime::offsetDateTimeNullable].dateTime() }
            }
            table<MysqlLocalTime> {
                column { it[MysqlLocalTime::id].integer() }
                        .primaryKey()
                column { it[MysqlLocalTime::localTimeNotNull].time() }
                column { it[MysqlLocalTime::localTimeNullable].time() }
            }
            table<MysqlInt> {
                column { it[MysqlInt::id].autoIncrementInteger() }
                        .primaryKey()
                column { it[MysqlInt::intNotNull].integer() }
                column { it[MysqlInt::intNullable].integer() }
            }
        }


data class MysqlRole(
        val id: Int,
        val label: String
)

val mysqlUser = MysqlRole(1, "user")
val mysqlAdmin = MysqlRole(2,"admin")
val mysqlGod = MysqlRole(3, "god")


data class MysqlUser(
        val id: Int,
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: Int,
        val alias: String? = null
)

val mysqlJdoe = MysqlUser(1, "John", "Doe", false, mysqlUser.id)
val mysqlBboss = MysqlUser(2, "Big", "Boss", true, mysqlAdmin.id, "TheBoss")


data class MysqlAllTypesNotNull(
        val id: Int,
        val string: String,
        val boolean: Boolean,
        val localDate: LocalDate,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate,
        val localTim: LocalTime,
        val localDateTime: LocalDateTime,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        val int: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNotNull

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTim.roundToSecond() != other.localTim.roundToSecond()) return false
        if (localDateTime.roundToSecond() != other.localDateTime.roundToSecond()) return false
        if (kotlinxLocalDateTime.toJavaLocalDateTime().roundToSecond()
                != other.kotlinxLocalDateTime.toJavaLocalDateTime().roundToSecond()) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (localDate.hashCode())
        result = 31 * result + (kotlinxLocalDate.hashCode())
        result = 31 * result + (localTim.hashCode())
        result = 31 * result + (localDateTime.hashCode())
        result = 31 * result + (kotlinxLocalDateTime.hashCode())
        result = 31 * result + (int)
        result = 31 * result + (id)
        return result
    }
}

val mysqlAllTypesNotNull = MysqlAllTypesNotNull(1, "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), LocalTime.now(),
        LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC), 1)


data class MysqlAllTypesNullable(
        val id: Int,
        val string: String?,
        val localDate: LocalDate?,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
        val localTim: LocalTime?,
        val localDateTime: LocalDateTime?,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime?,
        val int: Int?
)

val mysqlAllTypesNullable = MysqlAllTypesNullable(1, null, null, null,
        null, null, null, null)


data class MysqlAllTypesNullableDefaultValue(
        val id: Int,
        val string: String? = null,
        val localDate: LocalDate? = null,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
        val localTim: LocalTime? = null,
        val localDateTime: LocalDateTime? = null,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime? = null,
        val int: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNullableDefaultValue

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTim != other.localTim) return false
        if (localDateTime != other.localDateTime) return false
        if (kotlinxLocalDateTime != other.kotlinxLocalDateTime) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string?.hashCode() ?: 0
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (localTim?.hashCode() ?: 0)
        result = 31 * result + (localDateTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + (id)
        return result
    }
}

val mysqlAllTypesNullableDefaultValue = MysqlAllTypesNullableDefaultValue(1)


/**
 * Just for JDBC tests, UUID is not natively supported by MySQL but some drivers may fake this support by making some
 * operations for the user
 */
data class MysqlUuid(
        val uuidNotNull: UUID,
        val uuidNullable: UUID? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlUuidWithNullable = MysqlUuid(UUID.fromString(defaultUuid), UUID.randomUUID())
val mysqlUuidWithoutNullable = MysqlUuid(UUID.fromString(defaultUuid))


data class MysqlLocalDate(
        val id: Int,
        val localDateNotNull: LocalDate,
        val localDateNullable: LocalDate? = null
)

val mysqlLocalDateWithNullable = MysqlLocalDate(1, LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val mysqlLocalDateWithoutNullable = MysqlLocalDate(2, LocalDate.of(2019, 11, 6))

data class MysqlKotlinxLocalDate(
        val id: Int,
        val localDateNotNull: kotlinx.datetime.LocalDate,
        val localDateNullable: kotlinx.datetime.LocalDate? = null
)

val mysqlKotlinxLocalDateWithNullable = MysqlKotlinxLocalDate(1, kotlinx.datetime.LocalDate(2019, 11, 4),
        kotlinx.datetime.LocalDate(2018, 11, 4))
val mysqlKotlinxLocalDateWithoutNullable = MysqlKotlinxLocalDate(2, kotlinx.datetime.LocalDate(2019, 11, 6))


data class MysqlLocalDateTime(
        val id: Int,
        val localDateTimeNotNull: LocalDateTime,
        val localDateTimeNullable: LocalDateTime? = null
)

val mysqlLocalDateTimeWithNullable = MysqlLocalDateTime(1, LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val mysqlLocalDateTimeWithoutNullable = MysqlLocalDateTime(2, LocalDateTime.of(2019, 11, 6, 0, 0))


data class MysqlKotlinxLocalDateTime(
        val id: Int,
        val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeNullable: kotlinx.datetime.LocalDateTime? = null
)

val mysqlKotlinxLocalDateTimeWithNullable = MysqlKotlinxLocalDateTime(1,
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
val mysqlKotlinxLocalDateTimeWithoutNullable = MysqlKotlinxLocalDateTime(2,
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0))

data class MysqlOffsetDateTime(
        val id: Int,
        val offsetDateTimeNotNull: OffsetDateTime,
        val offsetDateTimeNullable: OffsetDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlOffsetDateTime

        if (id != other.id) return false
        if (!offsetDateTimeNotNull.isEqual(other.offsetDateTimeNotNull)) return false
        if (offsetDateTimeNullable != null) {
            if (!offsetDateTimeNullable.isEqual(other.offsetDateTimeNullable)) return false
        } else if (other.offsetDateTimeNullable != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + offsetDateTimeNotNull.hashCode()
        result = 31 * result + (offsetDateTimeNullable?.hashCode() ?: 0)
        return result
    }
}

val mysqlOffsetDateTimeWithNullable = MysqlOffsetDateTime(1,
        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC))
val mysqlOffsetDateTimeWithoutNullable = MysqlOffsetDateTime(2,
        OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC))


data class MysqlLocalTime(
        val id: Int,
        val localTimeNotNull: LocalTime,
        val localTimeNullable: LocalTime? = null
)

val mysqlLocalTimeWithNullable = MysqlLocalTime(1, LocalTime.of(12, 4), LocalTime.of(11, 4))
val mysqlLocalTimeWithoutNullable = MysqlLocalTime(2, LocalTime.of(12, 6))


data class MysqlInt(
        val intNotNull: Int,
        val intNullable: Int? = null,
        val id: Int? = null
)

val mysqlIntWithNullable = MysqlInt(10, 6)
val mysqlIntWithoutNullable = MysqlInt(12)
