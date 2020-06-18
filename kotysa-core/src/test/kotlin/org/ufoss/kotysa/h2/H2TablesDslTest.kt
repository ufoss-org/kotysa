/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.ufoss.kotysa.DbTypeChoice
import org.ufoss.kotysa.SinglePrimaryKey
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.test.H2AllTypesNotNull
import org.ufoss.kotysa.test.H2AllTypesNullable
import org.ufoss.kotysa.test.H2Role
import org.ufoss.kotysa.test.H2User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


class H2TablesDslTest {

    @Test
    fun `Test all supported column types for not null properties`() {
        val tables = DbTypeChoice.h2 {
            table<H2AllTypesNotNull> {
                name = "all_types"
                column { it[H2AllTypesNotNull::id].uuid().primaryKey() }
                column { it[H2AllTypesNotNull::string].varchar() }
                column { it[H2AllTypesNotNull::boolean].boolean() }
                column { it[H2AllTypesNotNull::localDate].date() }
                column { it[H2AllTypesNotNull::offsetDateTime].timestampWithTimeZone() }
                column { it[H2AllTypesNotNull::localTim].time9() }
                column { it[H2AllTypesNotNull::localDateTime1].dateTime() }
                column { it[H2AllTypesNotNull::localDateTime2].timestamp() }
                column { it[H2AllTypesNotNull::uuid].uuid() }
                column { it[H2AllTypesNotNull::int].integer().autoIncrement() }
            }
        }
        assertThat(tables.allColumns.values)
                .extracting("name", "sqlType", "isNullable", "isAutoIncrement")
                .containsExactly(
                        tuple("id", SqlType.UUID, false, false),
                        tuple("string", SqlType.VARCHAR, false, false),
                        tuple("boolean", SqlType.BOOLEAN, false, false),
                        tuple("localDate", SqlType.DATE, false, false),
                        tuple("offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, false, false),
                        tuple("localTim", SqlType.TIME9, false, false),
                        tuple("localDateTime1", SqlType.DATE_TIME, false, false),
                        tuple("localDateTime2", SqlType.TIMESTAMP, false, false),
                        tuple("uuid", SqlType.UUID, false, false),
                        tuple("int", SqlType.INTEGER, false, true))
    }

    @Test
    fun `Test all supported column types for nullable properties`() {
        val tables = DbTypeChoice.h2 {
            table<H2AllTypesNullable> {
                name = "all_types_nullable"
                column { it[H2AllTypesNullable::id].uuid().primaryKey() }
                column { it[H2AllTypesNullable::string].varchar() }
                column { it[H2AllTypesNullable::localDate].date() }
                column { it[H2AllTypesNullable::offsetDateTime].timestampWithTimeZone() }
                column { it[H2AllTypesNullable::localTim].time9() }
                column { it[H2AllTypesNullable::localDateTime1].dateTime() }
                column { it[H2AllTypesNullable::localDateTime2].timestamp() }
                column { it[H2AllTypesNullable::uuid].uuid() }
                column { it[H2AllTypesNullable::int].integer() }
            }
        }
        assertThat(tables.allColumns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("string", SqlType.VARCHAR, true),
                        tuple("localDate", SqlType.DATE, true),
                        tuple("offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, true),
                        tuple("localTim", SqlType.TIME9, true),
                        tuple("localDateTime1", SqlType.DATE_TIME, true),
                        tuple("localDateTime2", SqlType.TIMESTAMP, true),
                        tuple("uuid", SqlType.UUID, true),
                        tuple("int", SqlType.INTEGER, true))
    }

    @Test
    fun `Test all supported column types for nullable properties with default values`() {
        val defaultUuid = UUID.randomUUID()
        val tables = DbTypeChoice.h2 {
            table<H2AllTypesNullable> {
                name = "all_types_nullable"
                column { it[H2AllTypesNullable::id].uuid().primaryKey() }
                column { it[H2AllTypesNullable::string].varchar().defaultValue("default") }
                column { it[H2AllTypesNullable::localDate].date().defaultValue(LocalDate.MAX) }
                column { it[H2AllTypesNullable::offsetDateTime].timestampWithTimeZone().defaultValue(OffsetDateTime.MAX) }
                column { it[H2AllTypesNullable::localTim].time9().defaultValue(LocalTime.MAX) }
                column { it[H2AllTypesNullable::localDateTime1].dateTime().defaultValue(LocalDateTime.MAX) }
                column { it[H2AllTypesNullable::localDateTime2].timestamp().defaultValue(LocalDateTime.MAX) }
                column { it[H2AllTypesNullable::uuid].uuid().defaultValue(defaultUuid) }
                column { it[H2AllTypesNullable::int].integer().defaultValue(42) }
            }
        }
        assertThat(tables.allColumns.values)
                .extracting("name", "sqlType", "isNullable", "defaultValue")
                .containsExactly(
                        tuple("id", SqlType.UUID, false, null),
                        tuple("string", SqlType.VARCHAR, false, "default"),
                        tuple("localDate", SqlType.DATE, false, LocalDate.MAX),
                        tuple("offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, false, OffsetDateTime.MAX),
                        tuple("localTim", SqlType.TIME9, false, LocalTime.MAX),
                        tuple("localDateTime1", SqlType.DATE_TIME, false, LocalDateTime.MAX),
                        tuple("localDateTime2", SqlType.TIMESTAMP, false, LocalDateTime.MAX),
                        tuple("uuid", SqlType.UUID, false, defaultUuid),
                        tuple("int", SqlType.INTEGER, false, 42))
    }

    @Test
    fun `Test unnamed primary and foreign key`() {
        val tables = DbTypeChoice.h2 {
            table<H2Role> {
                name = "roles"
                column { it[H2Role::id].uuid().primaryKey() }
                column { it[H2Role::label].varchar() }
            }
            table<H2User> {
                name = "users"
                column { it[H2User::id].uuid().primaryKey() }
                column { it[H2User::firstname].varchar() }
                column { it[H2User::alias].varchar() }
                column { it[H2User::roleId].uuid() }
                foreignKey<H2Role> { columns(it[H2User::roleId]) }
            }
        }
        val roleTable = tables.allTables[H2Role::class] ?: fail { "require mapped H2Role" }
        assertThat(roleTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("label", SqlType.VARCHAR, false))
        val userTable = tables.allTables[H2User::class] ?: fail { "require mapped H2User" }
        assertThat(userTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("firstname", SqlType.VARCHAR, false),
                        tuple("alias", SqlType.VARCHAR, true),
                        tuple("roleId", SqlType.UUID, false))
        assertThat(userTable.foreignKeys)
                .extracting("referencedClass", "name")
                .containsExactly(tuple(H2Role::class, null))
        val userTablePk = userTable.primaryKey as SinglePrimaryKey<*, *>
        assertThat(userTablePk.column.entityGetter).isEqualTo(H2User::id)
        assertThat(userTablePk.name).isNull()
        val userTableFk = userTable.foreignKeys.iterator().next()
        assertThat(userTableFk.columns)
                .hasSize(1)
                .extracting("entityGetter")
                .containsExactly(H2User::roleId)
        assertThat(userTableFk.referencedColumns)
                .hasSize(1)
                .extracting("entityGetter")
                .containsExactly(H2Role::id)
        assertThat(userTableFk.name).isNull()
    }

    @Test
    fun `Test named primary and foreign key`() {
        val tables = DbTypeChoice.h2 {
            table<H2Role> {
                name = "roles"
                column { it[H2Role::id].uuid().primaryKey() }
                column { it[H2Role::label].varchar() }
            }
            table<H2User> {
                name = "users"
                column { it[H2User::id].uuid().primaryKey("users_pk") }
                column { it[H2User::firstname].varchar() }
                column { it[H2User::alias].varchar() }
                column { it[H2User::roleId].uuid() }
                foreignKey<H2Role> { columns(it[H2User::roleId]).name("users_fk") }
            }
        }
        val roleTable = tables.allTables[H2Role::class] ?: fail { "require mapped H2Role" }
        assertThat(roleTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("label", SqlType.VARCHAR, false))
        val userTable = tables.allTables[H2User::class] ?: fail { "require mapped H2User" }
        assertThat(userTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("firstname", SqlType.VARCHAR, false),
                        tuple("alias", SqlType.VARCHAR, true),
                        tuple("roleId", SqlType.UUID, false))
        assertThat(userTable.foreignKeys)
                .extracting("referencedClass", "name")
                .containsExactly(tuple(H2Role::class, "users_fk"))
        val userTablePk = userTable.primaryKey as SinglePrimaryKey<*, *>
        assertThat(userTablePk.column.entityGetter).isEqualTo(H2User::id)
        assertThat(userTablePk.name).isEqualTo("users_pk")
        val userTableFk = userTable.foreignKeys.iterator().next()
        assertThat(userTableFk.columns).hasSize(1).extracting("entityGetter").containsExactly(H2User::roleId)
        assertThat(userTableFk.referencedColumns).hasSize(1).extracting("entityGetter").containsExactly(H2Role::id)
        assertThat(userTableFk.name).isEqualTo("users_fk")
    }

    @Test
    fun `Test named primary and foreign key - column syntax`() {
        val tables = DbTypeChoice.h2 {
            table<H2Role> {
                name = "roles"
                column { it[H2Role::id].uuid().primaryKey() }
                column { it[H2Role::label].varchar() }
            }
            table<H2User> {
                name = "users"
                column { it[H2User::id].uuid().primaryKey("users_pk") }
                column { it[H2User::firstname].varchar() }
                column { it[H2User::alias].varchar() }
                column { it[H2User::roleId].uuid() }
                        .foreignKey<H2Role>("users_fk")
            }
        }
        val roleTable = tables.allTables[H2Role::class] ?: fail { "require mapped H2Role" }
        assertThat(roleTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("label", SqlType.VARCHAR, false))
        val userTable = tables.allTables[H2User::class] ?: fail { "require mapped H2User" }
        assertThat(userTable.columns.values)
                .extracting("name", "sqlType", "isNullable")
                .containsExactly(
                        tuple("id", SqlType.UUID, false),
                        tuple("firstname", SqlType.VARCHAR, false),
                        tuple("alias", SqlType.VARCHAR, true),
                        tuple("roleId", SqlType.UUID, false))
        assertThat(userTable.foreignKeys)
                .extracting("referencedClass", "name")
                .containsExactly(tuple(H2Role::class, "users_fk"))
        val userTablePk = userTable.primaryKey as SinglePrimaryKey<*, *>
        assertThat(userTablePk.column.entityGetter).isEqualTo(H2User::id)
        assertThat(userTablePk.name).isEqualTo("users_pk")
        val userTableFk = userTable.foreignKeys.iterator().next()
        assertThat(userTableFk.columns).hasSize(1).extracting("entityGetter").containsExactly(H2User::roleId)
        assertThat(userTableFk.referencedColumns).hasSize(1).extracting("entityGetter").containsExactly(H2Role::id)
        assertThat(userTableFk.name).isEqualTo("users_fk")
    }
}
