/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.ufoss.kotysa.tables
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.*

private fun LocalTime.roundToSecond(): LocalTime {
    var time = this
    if (nano >= 500_000_000) {
        time = plusSeconds(1)
    }
    return time.truncatedTo(ChronoUnit.SECONDS)
}

val mysqlTables =
        tables().mysql {
            table<MysqlRole> {
                name = "roles"
                column { it[MysqlRole::id].uuid() }
                        .primaryKey()
                column { it[MysqlRole::label].varchar {
                    size = 255
                } }
            }
            table<MysqlUser> {
                name = "users"
                column { it[MysqlUser::id].uuid() }
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
                column { it[MysqlUser::roleId].uuid() }
                        .foreignKey<MysqlRole>("FK_users_roles")
                column { it[MysqlUser::alias].varchar {
                    size = 255
                } }
            }
            table<MysqlAllTypesNotNull> {
                name = "all_types"
                column { it[MysqlAllTypesNotNull::id].uuid() }
                        .primaryKey()
                column { it[MysqlAllTypesNotNull::string].varchar {
                    size = 255
                } }
                column { it[MysqlAllTypesNotNull::boolean].boolean() }
                column { it[MysqlAllTypesNotNull::localDate].date() }
                column { it[MysqlAllTypesNotNull::kotlinxLocalDate].date() }
                column { it[MysqlAllTypesNotNull::offsetDateTime].timestamp() }
                column { it[MysqlAllTypesNotNull::localTim].time() }
                column { it[MysqlAllTypesNotNull::localDateTime].timestamp() }
                column { it[MysqlAllTypesNotNull::kotlinxLocalDateTime].timestamp() }
                column { it[MysqlAllTypesNotNull::uuid].uuid() }
                column { it[MysqlAllTypesNotNull::int].integer() }
            }
            table<MysqlAllTypesNullable> {
                name = "all_types_nullable"
                column { it[MysqlAllTypesNullable::id].uuid() }
                        .primaryKey()
                column { it[MysqlAllTypesNullable::string].varchar {
                    size = 255
                } }
                column { it[MysqlAllTypesNullable::localDate].date() }
                column { it[MysqlAllTypesNullable::kotlinxLocalDate].date() }
                column { it[MysqlAllTypesNullable::offsetDateTime].timestamp() }
                column { it[MysqlAllTypesNullable::localTim].time {
                    fractionalSecondsPart = 0
                } }
                column { it[MysqlAllTypesNullable::localDateTime].timestamp() }
                column { it[MysqlAllTypesNullable::kotlinxLocalDateTime].timestamp() }
                column { it[MysqlAllTypesNullable::uuid].uuid() }
                column { it[MysqlAllTypesNullable::int].integer() }
            }
            table<MysqlAllTypesNullableDefaultValue> {
                column { it[MysqlAllTypesNullableDefaultValue::id].uuid() }
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
                column { it[MysqlAllTypesNullableDefaultValue::offsetDateTime].timestamp {
                    defaultValue = OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::localTim].time {
                    defaultValue = LocalTime.of(11, 25, 55)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::localDateTime].timestamp {
                    defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::kotlinxLocalDateTime].timestamp {
                    defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::uuid].uuid {
                    defaultValue = UUID.fromString(defaultUuid)
                } }
                column { it[MysqlAllTypesNullableDefaultValue::int].integer {
                    defaultValue = 42
                } }
            }
            table<MysqlUuid> {
                column { it[MysqlUuid::id].uuid() }
                        .primaryKey()
                column { it[MysqlUuid::roleIdNotNull].uuid() }
                        .foreignKey<MysqlRole>()
                column { it[MysqlUuid::roleIdNullable].uuid() }
                        .foreignKey<MysqlRole>()
            }
            table<MysqlLocalDate> {
                column { it[MysqlLocalDate::id].uuid() }
                        .primaryKey()
                column { it[MysqlLocalDate::localDateNotNull].date() }
                column { it[MysqlLocalDate::localDateNullable].date() }
            }
            table<MysqlKotlinxLocalDate> {
                column { it[MysqlKotlinxLocalDate::id].uuid() }
                        .primaryKey()
                column { it[MysqlKotlinxLocalDate::localDateNotNull].date() }
                column { it[MysqlKotlinxLocalDate::localDateNullable].date() }
            }
            table<MysqlLocalDateTime> {
                column { it[MysqlLocalDateTime::id].uuid() }
                        .primaryKey()
                column { it[MysqlLocalDateTime::localDateTimeAsTimestampNotNull].timestamp() }
                column { it[MysqlLocalDateTime::localDateTimeAsTimestampNullable].timestamp() }
            }
            table<MysqlKotlinxLocalDateTime> {
                column { it[MysqlKotlinxLocalDateTime::id].uuid() }
                        .primaryKey()
                column { it[MysqlKotlinxLocalDateTime::localDateTimeAsTimestampNotNull].timestamp() }
                column { it[MysqlKotlinxLocalDateTime::localDateTimeAsTimestampNullable].timestamp() }
            }
            table<MysqlOffsetDateTime> {
                column { it[MysqlOffsetDateTime::id].uuid() }
                        .primaryKey()
                column { it[MysqlOffsetDateTime::offsetDateTimeNotNull].timestamp() }
                column { it[MysqlOffsetDateTime::offsetDateTimeNullable].timestamp() }
            }
            table<MysqlLocalTime> {
                column { it[MysqlLocalTime::id].uuid() }
                        .primaryKey()
                column { it[MysqlLocalTime::localTimeNotNull].time() }
                column { it[MysqlLocalTime::localTimeNullable].time() }
            }
            table<MysqlInt> {
                column { it[MysqlInt::id].serial() }
                        .primaryKey()
                column { it[MysqlInt::intNotNull].integer() }
                column { it[MysqlInt::intNullable].integer() }
            }
        }


data class MysqlRole(
        val label: String,
        val id: UUID = UUID.randomUUID()
)

val mysqlUser = MysqlRole("user")
val mysqlAdmin = MysqlRole("admin")
val mysqlGod = MysqlRole("god")


data class MysqlUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: UUID,
        val alias: String? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlJdoe = MysqlUser("John", "Doe", false, mysqlUser.id)
val mysqlBboss = MysqlUser("Big", "Boss", true, mysqlAdmin.id, "TheBoss")


data class MysqlAllTypesNotNull(
        val id: UUID,
        val string: String,
        val boolean: Boolean,
        val localDate: LocalDate,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate,
        val offsetDateTime: OffsetDateTime,
        val localTim: LocalTime,
        val localDateTime: LocalDateTime,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        val uuid: UUID,
        val int: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNotNull

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (offsetDateTime != other.offsetDateTime) return false
        if (localTim.roundToSecond() != other.localTim.roundToSecond()) return false
        if (localDateTime != other.localDateTime) return false
        if (kotlinxLocalDateTime != other.kotlinxLocalDateTime) return false
        if (uuid != other.uuid) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (localDate.hashCode())
        result = 31 * result + (kotlinxLocalDate.hashCode())
        result = 31 * result + (offsetDateTime.hashCode())
        result = 31 * result + (localTim.hashCode())
        result = 31 * result + (localDateTime.hashCode())
        result = 31 * result + (kotlinxLocalDateTime.hashCode())
        result = 31 * result + (uuid.hashCode())
        result = 31 * result + (int)
        result = 31 * result + id.hashCode()
        return result
    }
}

val mysqlAllTypesNotNull = MysqlAllTypesNotNull(UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f"), "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), OffsetDateTime.now(), LocalTime.now(),
        LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC), UUID.randomUUID(), 1)


data class MysqlAllTypesNullable(
        val id: UUID,
        val string: String?,
        val localDate: LocalDate?,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
        val offsetDateTime: OffsetDateTime?,
        val localTim: LocalTime?,
        val localDateTime: LocalDateTime?,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime?,
        val uuid: UUID?,
        val int: Int?
)

val mysqlAllTypesNullable = MysqlAllTypesNullable(UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e"),
        null, null, null, null, null, null,
        null, null, null)


data class MysqlAllTypesNullableDefaultValue(
        val string: String? = null,
        val localDate: LocalDate? = null,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate? = null,
        val offsetDateTime: OffsetDateTime? = null,
        val localTim: LocalTime? = null,
        val localDateTime: LocalDateTime? = null,
        val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime? = null,
        val uuid: UUID? = null,
        val int: Int? = null,
        val id: UUID = UUID.randomUUID()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MysqlAllTypesNullableDefaultValue

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (offsetDateTime != null) {
            if (!offsetDateTime.isEqual(other.offsetDateTime)) return false
        } else if (other.offsetDateTime != null) {
            return false
        }
        if (localTim != other.localTim) return false
        if (localDateTime != other.localDateTime) return false
        if (kotlinxLocalDateTime != other.kotlinxLocalDateTime) return false
        if (uuid != other.uuid) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string?.hashCode() ?: 0
        result = 31 * result + (localDate?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDate?.hashCode() ?: 0)
        result = 31 * result + (offsetDateTime?.hashCode() ?: 0)
        result = 31 * result + (localTim?.hashCode() ?: 0)
        result = 31 * result + (localDateTime?.hashCode() ?: 0)
        result = 31 * result + (kotlinxLocalDateTime?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        result = 31 * result + (int ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}

val mysqlAllTypesNullableDefaultValue = MysqlAllTypesNullableDefaultValue()


data class MysqlUuid(
        val roleIdNotNull: UUID,
        val roleIdNullable: UUID? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlUuidWithNullable = MysqlUuid(mysqlUser.id, mysqlAdmin.id)
val mysqlUuidWithoutNullable = MysqlUuid(mysqlUser.id)


data class MysqlLocalDate(
        val localDateNotNull: LocalDate,
        val localDateNullable: LocalDate? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlLocalDateWithNullable = MysqlLocalDate(LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val mysqlLocalDateWithoutNullable = MysqlLocalDate(LocalDate.of(2019, 11, 6))

data class MysqlKotlinxLocalDate(
        val localDateNotNull: kotlinx.datetime.LocalDate,
        val localDateNullable: kotlinx.datetime.LocalDate? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlKotlinxLocalDateWithNullable = MysqlKotlinxLocalDate(kotlinx.datetime.LocalDate(2019, 11, 4),
        kotlinx.datetime.LocalDate(2018, 11, 4))
val mysqlKotlinxLocalDateWithoutNullable = MysqlKotlinxLocalDate(kotlinx.datetime.LocalDate(2019, 11, 6))


data class MysqlLocalDateTime(
        val localDateTimeAsTimestampNotNull: LocalDateTime,
        val localDateTimeAsTimestampNullable: LocalDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlLocalDateTimeWithNullable = MysqlLocalDateTime(LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val mysqlLocalDateTimeWithoutNullable = MysqlLocalDateTime(LocalDateTime.of(2019, 11, 6, 0, 0))


data class MysqlKotlinxLocalDateTime(
        val localDateTimeAsTimestampNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeAsTimestampNullable: kotlinx.datetime.LocalDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlKotlinxLocalDateTimeWithNullable = MysqlKotlinxLocalDateTime(
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
val mysqlKotlinxLocalDateTimeWithoutNullable = MysqlKotlinxLocalDateTime(
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0))

data class MysqlOffsetDateTime(
        val offsetDateTimeNotNull: OffsetDateTime,
        val offsetDateTimeNullable: OffsetDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlOffsetDateTimeWithNullable = MysqlOffsetDateTime(
        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC))
val mysqlOffsetDateTimeWithoutNullable = MysqlOffsetDateTime(
        OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC))


data class MysqlLocalTime(
        val localTimeNotNull: LocalTime,
        val localTimeNullable: LocalTime? = null,
        val id: UUID = UUID.randomUUID()
)

val mysqlLocalTimeWithNullable = MysqlLocalTime(LocalTime.of(12, 4), LocalTime.of(11, 4))
val mysqlLocalTimeWithoutNullable = MysqlLocalTime(LocalTime.of(12, 6))


data class MysqlInt(
        val intNotNull: Int,
        val intNullable: Int? = null,
        val id: Int? = null
)

val mysqlIntWithNullable = MysqlInt(10, 6)
val mysqlIntWithoutNullable = MysqlInt(12)
