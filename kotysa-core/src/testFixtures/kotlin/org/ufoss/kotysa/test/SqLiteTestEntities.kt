/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.tables
import java.time.*

val sqLiteTables =
        tables().sqlite {
            table<SqLiteRole> {
                name = "roles"
                column { it[SqLiteRole::id].text().primaryKey() }
                column { it[SqLiteRole::label].text() }
            }
            table<SqLiteUser> {
                name = "users"
                column { it[SqLiteUser::id].text().primaryKey() }
                column { it[SqLiteUser::firstname].text().name("fname") }
                column { it[SqLiteUser::lastname].text().name("lname") }
                column { it[SqLiteUser::isAdmin].integer() }
                column { it[SqLiteUser::roleId].text() }
                column { it[SqLiteUser::alias].text() }
                foreignKey<SqLiteRole> { column(it[SqLiteUser::roleId]).name("FK_users_roles") }
            }
            table<SqLiteAllTypesNotNull> {
                name = "all_types"
                column { it[SqLiteAllTypesNotNull::id].text().primaryKey() }
                column { it[SqLiteAllTypesNotNull::string].text() }
                column { it[SqLiteAllTypesNotNull::boolean].integer() }
                column { it[SqLiteAllTypesNotNull::localDate].text() }
                column { it[SqLiteAllTypesNotNull::offsetDateTime].text() }
                column { it[SqLiteAllTypesNotNull::localDateTime].text() }
                column { it[SqLiteAllTypesNotNull::localTime].text() }
                column { it[SqLiteAllTypesNotNull::int].integer() }
            }
            table<SqLiteAllTypesNullable> {
                name = "all_types_nullable"
                column { it[SqLiteAllTypesNullable::id].text().primaryKey() }
                column { it[SqLiteAllTypesNullable::string].text() }
                column { it[SqLiteAllTypesNullable::localDate].text() }
                column { it[SqLiteAllTypesNullable::offsetDateTime].text() }
                column { it[SqLiteAllTypesNullable::localDateTime].text() }
                column { it[SqLiteAllTypesNullable::localTime].text() }
                column { it[SqLiteAllTypesNullable::int].integer() }
            }
            table<SqLiteAllTypesNullableDefaultValue> {
                column { it[SqLiteAllTypesNullableDefaultValue::id].text().primaryKey() }
                column { it[SqLiteAllTypesNullableDefaultValue::string].text().defaultValue("default") }
                column { it[SqLiteAllTypesNullableDefaultValue::localDate].text().defaultValue(LocalDate.MAX) }
                column { it[SqLiteAllTypesNullableDefaultValue::offsetDateTime].text().defaultValue(OffsetDateTime.MAX) }
                column { it[SqLiteAllTypesNullableDefaultValue::localDateTime].text().defaultValue(LocalDateTime.MAX) }
                column { it[SqLiteAllTypesNullableDefaultValue::localTime].text().defaultValue(LocalTime.MAX) }
                column { it[SqLiteAllTypesNullableDefaultValue::int].integer().defaultValue(42) }
            }
            table<SqLiteLocalDate> {
                column { it[SqLiteLocalDate::id].text().primaryKey() }
                column { it[SqLiteLocalDate::localDateNotNull].text() }
                column { it[SqLiteLocalDate::localDateNullable].text() }
            }
            table<SqLiteLocalDateTime> {
                column { it[SqLiteLocalDateTime::id].text().primaryKey() }
                column { it[SqLiteLocalDateTime::localDateTimeNotNull].text() }
                column { it[SqLiteLocalDateTime::localDateTimeNullable].text() }
            }
            table<SqLiteOffsetDateTime> {
                column { it[SqLiteOffsetDateTime::id].text().primaryKey() }
                column { it[SqLiteOffsetDateTime::offsetDateTimeNotNull].text() }
                column { it[SqLiteOffsetDateTime::offsetDateTimeNullable].text() }
            }
            table<SqLiteLocalTime> {
                column { it[SqLiteLocalTime::id].text().primaryKey() }
                column { it[SqLiteLocalTime::localTimeNotNull].text() }
                column { it[SqLiteLocalTime::localTimeNullable].text() }
            }
            table<SqLiteInteger> {
                column { it[SqLiteInteger::id].integer().autoIncrement().primaryKey() }
                column { it[SqLiteInteger::integerNotNull].integer() }
                column { it[SqLiteInteger::integerNullable].integer() }
            }
        }


data class SqLiteRole(
        val label: String,
        val id: String
)

val sqLiteUser = SqLiteRole("user", "ghi")
val sqLiteAdmin = SqLiteRole("admin", "jkl")
val sqLiteGod = SqLiteRole("god", "mno")


data class SqLiteUser(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: String,
        val alias: String? = null,
        val id: String
)

val sqLiteJdoe = SqLiteUser("John", "Doe", false, sqLiteUser.id, id = "abc")
val sqLiteBboss = SqLiteUser("Big", "Boss", true, sqLiteAdmin.id, "TheBoss", "def")


data class SqLiteAllTypesNotNull(
        val id: String,
        val string: String,
        val boolean: Boolean,
        val localDate: LocalDate,
        val offsetDateTime: OffsetDateTime,
        val localDateTime: LocalDateTime,
        val localTime: LocalTime,
        val int: Int
)

val sqLiteAllTypesNotNull = SqLiteAllTypesNotNull("abc", "", true, LocalDate.now(),
        OffsetDateTime.now(), LocalDateTime.now(), LocalTime.now(), 1)


data class SqLiteAllTypesNullable(
        val id: String,
        val string: String?,
        val localDate: LocalDate?,
        val offsetDateTime: OffsetDateTime?,
        val localDateTime: LocalDateTime?,
        val localTime: LocalTime?,
        val int: Int?
)

val sqLiteAllTypesNullable = SqLiteAllTypesNullable("def", null, null, null,
        null, null, null)


data class SqLiteAllTypesNullableDefaultValue(
        val id: String,
        val string: String? = null,
        val localDate: LocalDate? = null,
        val offsetDateTime: OffsetDateTime? = null,
        val localDateTime: LocalDateTime? = null,
        val localTime: LocalTime? = null,
        val int: Int? = null
)

val sqLiteAllTypesNullableDefaultValue = SqLiteAllTypesNullableDefaultValue("abc")


data class SqLiteLocalDate(
        val id: String,
        val localDateNotNull: LocalDate,
        val localDateNullable: LocalDate? = null
)

val sqLiteLocalDateWithNullable = SqLiteLocalDate("abc", LocalDate.of(2019, 11, 4), LocalDate.of(2018, 11, 4))
val sqLiteLocalDateWithoutNullable = SqLiteLocalDate("def", LocalDate.of(2019, 11, 6))


data class SqLiteLocalDateTime(
        val id: String,
        val localDateTimeNotNull: LocalDateTime,
        val localDateTimeNullable: LocalDateTime? = null
)

val sqLiteLocalDateTimeWithNullable = SqLiteLocalDateTime("abc", LocalDateTime.of(2019, 11, 4, 0, 0), LocalDateTime.of(2018, 11, 4, 0, 0))
val sqLiteLocalDateTimeWithoutNullable = SqLiteLocalDateTime("def", LocalDateTime.of(2019, 11, 6, 0, 0))


data class SqLiteOffsetDateTime(
        val id: String,
        val offsetDateTimeNotNull: OffsetDateTime,
        val offsetDateTimeNullable: OffsetDateTime? = null
)

val sqLiteOffsetDateTimeWithNullable = SqLiteOffsetDateTime("abc",
        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
        OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC))
val sqLiteOffsetDateTimeWithoutNullable = SqLiteOffsetDateTime("def",
        OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC))


data class SqLiteLocalTime(
        val id: String,
        val localTimeNotNull: LocalTime,
        val localTimeNullable: LocalTime? = null
)

val sqLiteLocalTimeWithNullable = SqLiteLocalTime("abc", LocalTime.of(12, 4), LocalTime.of(11, 4))
val sqLiteLocalTimeWithoutNullable = SqLiteLocalTime("def", LocalTime.of(12, 6))


data class SqLiteInteger(
        val integerNotNull: Int,
        val integerNullable: Int? = null,
        val id: Int? = null
)

val sqLiteIntegerWithNullable = SqLiteInteger(10, 6)
val sqLiteIntegerWithoutNullable = SqLiteInteger(12)
