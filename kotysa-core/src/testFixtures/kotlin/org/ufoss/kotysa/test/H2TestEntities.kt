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
import java.util.*

const val defaultUuid = "67d4306e-d99d-4e54-8b1d-5b1e92691a4e"

val h2Tables =
        tables().h2 {
            table<H2Role> {
                name = "roles"
                column { it[H2Role::id].uuid() }
                        .primaryKey()
                column { it[H2Role::label].varchar() }
            }
            table<H2User> {
                name = "users"
                column { it[H2User::id].uuid() }
                        .primaryKey("PK_users")
                column { it[H2User::firstname].varchar().name("fname") }
                column { it[H2User::lastname].varchar().name("lname") }
                column { it[H2User::isAdmin].boolean() }
                column { it[H2User::roleId].uuid() }
                        .foreignKey<H2Role>("FK_users_roles")
                column { it[H2User::alias].varchar() }
            }
            table<H2AllTypesNotNull> {
                name = "all_types"
                column { it[H2AllTypesNotNull::id].uuid() }
                        .primaryKey()
                column { it[H2AllTypesNotNull::string].varchar() }
                column { it[H2AllTypesNotNull::boolean].boolean() }
                column { it[H2AllTypesNotNull::localDate].date() }
                column { it[H2AllTypesNotNull::offsetDateTime].timestampWithTimeZone() }
                column { it[H2AllTypesNotNull::localTim].time9() }
                column { it[H2AllTypesNotNull::localDateTime1].dateTime() }
                column { it[H2AllTypesNotNull::localDateTime2].timestamp() }
                column { it[H2AllTypesNotNull::uuid].uuid() }
                column { it[H2AllTypesNotNull::int].integer() }
            }
            table<H2AllTypesNullable> {
                name = "all_types_nullable"
                column { it[H2AllTypesNullable::id].uuid() }
                        .primaryKey()
                column { it[H2AllTypesNullable::string].varchar() }
                column { it[H2AllTypesNullable::localDate].date() }
                column { it[H2AllTypesNullable::offsetDateTime].timestampWithTimeZone() }
                column { it[H2AllTypesNullable::localTim].time9() }
                column { it[H2AllTypesNullable::localDateTime1].dateTime() }
                column { it[H2AllTypesNullable::localDateTime2].timestamp() }
                column { it[H2AllTypesNullable::uuid].uuid() }
                column { it[H2AllTypesNullable::int].integer() }
            }
            table<H2AllTypesNullableDefaultValue> {
                column { it[H2AllTypesNullableDefaultValue::id].uuid() }
                        .primaryKey()
                column { it[H2AllTypesNullableDefaultValue::string].varchar().defaultValue("default") }
                column { it[H2AllTypesNullableDefaultValue::localDate].date().defaultValue(LocalDate.MAX) }
                column { it[H2AllTypesNullableDefaultValue::offsetDateTime].timestampWithTimeZone().defaultValue(OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)) }
                column { it[H2AllTypesNullableDefaultValue::localTim].time9().defaultValue(LocalTime.MAX) }
                column { it[H2AllTypesNullableDefaultValue::localDateTime1].dateTime().defaultValue(LocalDateTime.of(2018, 11, 4, 0, 0)) }
                column { it[H2AllTypesNullableDefaultValue::localDateTime2].timestamp().defaultValue(LocalDateTime.of(2019, 11, 4, 0, 0)) }
                column { it[H2AllTypesNullableDefaultValue::uuid].uuid().defaultValue(UUID.fromString(defaultUuid)) }
                column { it[H2AllTypesNullableDefaultValue::int].integer().defaultValue(42) }
            }
            table<H2Uuid> {
                column { it[H2Uuid::id].uuid() }
                        .primaryKey()
                column { it[H2Uuid::roleIdNotNull].uuid() }
                        .foreignKey<H2Role>()
                column { it[H2Uuid::roleIdNullable].uuid() }
                        .foreignKey<H2Role>()
            }
            table<H2LocalDate> {
                column { it[H2LocalDate::id].uuid() }
                        .primaryKey()
                column { it[H2LocalDate::localDateNotNull].date() }
                column { it[H2LocalDate::localDateNullable].date() }
            }
            table<H2LocalDateTime> {
                column { it[H2LocalDateTime::id].uuid() }
                        .primaryKey()
                column { it[H2LocalDateTime::localDateTimeNotNull].dateTime() }
                column { it[H2LocalDateTime::localDateTimeNullable].dateTime() }
                column { it[H2LocalDateTime::localDateTimeAsTimestampNotNull].timestamp() }
                column { it[H2LocalDateTime::localDateTimeAsTimestampNullable].timestamp() }
            }
            table<H2OffsetDateTime> {
                column { it[H2OffsetDateTime::id].uuid() }
                        .primaryKey()
                column { it[H2OffsetDateTime::offsetDateTimeNotNull].timestampWithTimeZone() }
                column { it[H2OffsetDateTime::offsetDateTimeNullable].timestampWithTimeZone() }
            }
            table<H2LocalTime> {
                column { it[H2LocalTime::id].uuid() }
                        .primaryKey()
                column { it[H2LocalTime::localTimeNotNull].time9() }
                column { it[H2LocalTime::localTimeNullable].time9() }
            }
            table<H2Int> {
                column { it[H2Int::id].integer().autoIncrement() }
                        .primaryKey()
                column { it[H2Int::intNotNull].integer() }
                column { it[H2Int::intNullable].integer() }
            }
        }


data class H2Role(
        val label: String,
        val id: UUID = UUID.randomUUID()
)

val h2User = H2Role("user")
val h2Admin = H2Role("admin")
val h2God = H2Role("god")


data class H2User(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: UUID,
        val alias: String? = null,
        val id: UUID = UUID.randomUUID()
)

val h2Jdoe = H2User("John", "Doe", false, h2User.id)
val h2Bboss = H2User("Big", "Boss", true, h2Admin.id, "TheBoss")

data class H2UserRole(
        val userId: UUID,
        val roleId: UUID
)

data class H2AllTypesNotNull(
        val id: UUID,
        val string: String,
        val boolean: Boolean,
        val localDate: LocalDate,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate,
        val offsetDateTime: OffsetDateTime,
        val localTim: LocalTime,
        val localDateTime1: LocalDateTime,
        val localDateTime2: LocalDateTime,
        val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
        val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime,
        val uuid: UUID,
        val int: Int
)

val h2AllTypesNotNull = H2AllTypesNotNull(UUID.fromString("79e9eb45-2835-49c8-ad3b-c951b591bc7f"), "",
        true, LocalDate.now(), Clock.System.todayAt(TimeZone.UTC), OffsetDateTime.now(), LocalTime.now(),
        LocalDateTime.now(), LocalDateTime.now(), Clock.System.now().toLocalDateTime(TimeZone.UTC),
        Clock.System.now().toLocalDateTime(TimeZone.UTC), UUID.randomUUID(), 1)


data class H2AllTypesNullable(
        val id: UUID,
        val string: String?,
        val localDate: LocalDate?,
        val kotlinxLocalDate: kotlinx.datetime.LocalDate?,
        val offsetDateTime: OffsetDateTime?,
        val localTim: LocalTime?,
        val localDateTime1: LocalDateTime?,
        val localDateTime2: LocalDateTime?,
        val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime?,
        val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime?,
        val uuid: UUID?,
        val int: Int?
)

val h2AllTypesNullable = H2AllTypesNullable(UUID.fromString("67d4306e-d99d-4e54-8b1d-5b1e92691a4e"), null, null,
        null, null, null, null, null, null, null, null, null)


data class H2AllTypesNullableDefaultValue(
        val string: String? = null,
        val localDate: LocalDate? = null,
        val LocalDate: kotlinx.datetime.LocalDate? = null,
        val offsetDateTime: OffsetDateTime? = null,
        val localTim: LocalTime? = null,
        val localDateTime1: LocalDateTime? = null,
        val localDateTime2: LocalDateTime? = null,
        val kotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime? = null,
        val kotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime? = null,
        val uuid: UUID? = null,
        val int: Int? = null,
        val id: UUID = UUID.randomUUID()
)

val h2AllTypesNullableDefaultValue = H2AllTypesNullableDefaultValue()


data class H2Uuid(
        val roleIdNotNull: UUID,
        val roleIdNullable: UUID? = null,
        val id: UUID = UUID.randomUUID()
)

val h2UuidWithNullable = H2Uuid(h2User.id, h2Admin.id)
val h2UuidWithoutNullable = H2Uuid(h2User.id)


data class H2LocalDate(
        val localDateNotNull: LocalDate,
        val localDateNullable: LocalDate? = null,
        val id: UUID = UUID.randomUUID()
)

val h2LocalDateWithNullable = H2LocalDate(LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val h2LocalDateWithoutNullable = H2LocalDate(LocalDate.of(2019, 11, 6))

data class H2KotlinxLocalDate(
        val localDateNotNull: kotlinx.datetime.LocalDate,
        val localDateNullable: kotlinx.datetime.LocalDate? = null,
        val id: UUID = UUID.randomUUID()
)

val h2KotlinxLocalDateWithNullable = H2KotlinxLocalDate(kotlinx.datetime.LocalDate(2019, 11, 4),
        kotlinx.datetime.LocalDate(2018, 11, 4))
val h2KotlinxLocalDateWithoutNullable = H2KotlinxLocalDate(kotlinx.datetime.LocalDate(2019, 11, 6))


data class H2LocalDateTime(
        val localDateTimeNotNull: LocalDateTime,
        val localDateTimeNullable: LocalDateTime?,
        val localDateTimeAsTimestampNotNull: LocalDateTime,
        val localDateTimeAsTimestampNullable: LocalDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val h2LocalDateTimeWithNullable = H2LocalDateTime(LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0),
        LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val h2LocalDateTimeWithoutNullable = H2LocalDateTime(LocalDateTime.of(2019, 11, 6, 0, 0), null,
        LocalDateTime.of(2019, 11, 6, 0, 0))


data class H2KotlinxLocalDateTime(
        val localDateTimeNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeNullable: kotlinx.datetime.LocalDateTime?,
        val localDateTimeAsTimestampNotNull: kotlinx.datetime.LocalDateTime,
        val localDateTimeAsTimestampNullable: kotlinx.datetime.LocalDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val h2KotlinxLocalDateTimeWithNullable = H2KotlinxLocalDateTime(
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0))
val h2KotlinxLocalDateTimeWithoutNullable = H2KotlinxLocalDateTime(
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0), null,
        kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0))


data class H2OffsetDateTime(
        val offsetDateTimeNotNull: OffsetDateTime,
        val offsetDateTimeNullable: OffsetDateTime? = null,
        val id: UUID = UUID.randomUUID()
)

val h2OffsetDateTimeWithNullable = H2OffsetDateTime(
        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC))
val h2OffsetDateTimeWithoutNullable = H2OffsetDateTime(
        OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC))


data class H2LocalTime(
        val localTimeNotNull: LocalTime,
        val localTimeNullable: LocalTime? = null,
        val id: UUID = UUID.randomUUID()
)

val h2LocalTimeWithNullable = H2LocalTime(LocalTime.of(12, 4), LocalTime.of(11, 4))
val h2LocalTimeWithoutNullable = H2LocalTime(LocalTime.of(12, 6))


data class H2Int(
        val intNotNull: Int,
        val intNullable: Int? = null,
        val id: Int? = null
)

val h2IntWithNullable = H2Int(10, 6)
val h2IntWithoutNullable = H2Int(12)
