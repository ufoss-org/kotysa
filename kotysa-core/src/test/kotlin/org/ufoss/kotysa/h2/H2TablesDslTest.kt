/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.ufoss.kotysa.DbTypeChoice
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*


class H2TablesDslTest {

    @Test
    fun `Test all supported column types for not null properties`() {
        val tables = DbTypeChoice.h2(H2AllTypesNotNulls)
        assertThat(tables.allColumns.values)
            .extracting("name", "sqlType", "nullable", "autoIncrement")
            .containsExactlyInAnyOrder(
                tuple("id", SqlType.INT, false, false),
                tuple("string", SqlType.VARCHAR, false, false),
                tuple("boolean", SqlType.BOOLEAN, false, false),
                tuple("localDate", SqlType.DATE, false, false),
                tuple("kotlinxLocalDate", SqlType.DATE, false, false),
                tuple("localTim", SqlType.TIME, false, false),
                tuple("localDateTime1", SqlType.DATE_TIME, false, false),
                tuple("localDateTime2", SqlType.TIMESTAMP, false, false),
                tuple("kotlinxLocalDateTime1", SqlType.DATE_TIME, false, false),
                tuple("kotlinxLocalDateTime2", SqlType.TIMESTAMP, false, false),
                tuple("int", SqlType.INT, false, false),
                tuple("long", SqlType.BIGINT, false, false),
                tuple("byteArray", SqlType.BINARY, false, false),
                tuple("offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, false, false),
                tuple("uuid", SqlType.UUID, false, false),
            )
    }

    @Test
    fun `Test all supported column types for nullable properties`() {
        val tables = DbTypeChoice.h2(H2AllTypesNullables)
        assertThat(tables.allColumns.values)
            .extracting("name", "sqlType", "nullable", "autoIncrement")
            .containsExactlyInAnyOrder(
                tuple("id", SqlType.INT, false, false),
                tuple("string", SqlType.VARCHAR, true, false),
                tuple("localDate", SqlType.DATE, true, false),
                tuple("kotlinxLocalDate", SqlType.DATE, true, false),
                tuple("localTim", SqlType.TIME, true, false),
                tuple("localDateTime1", SqlType.DATE_TIME, true, false),
                tuple("localDateTime2", SqlType.TIMESTAMP, true, false),
                tuple("kotlinxLocalDateTime1", SqlType.DATE_TIME, true, false),
                tuple("kotlinxLocalDateTime2", SqlType.TIMESTAMP, true, false),
                tuple("int", SqlType.INT, true, false),
                tuple("long", SqlType.BIGINT, true, false),
                tuple("byteArray", SqlType.BINARY, true, false),
                tuple("offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, true, false),
                tuple("uuid", SqlType.UUID, true, false),
            )
    }

    @Test
    fun `Test all supported column types for nullable properties with default values`() {
        val tables = DbTypeChoice.h2(H2AllTypesNullableDefaultValues)
        assertThat(tables.allColumns.values)
            .extracting("name", "sqlType", "nullable", "defaultValue")
            .containsExactly(
                tuple("id", SqlType.INT, false, null),
                tuple("string", SqlType.VARCHAR, false, "default"),
                tuple("localDate", SqlType.DATE, false, LocalDate.of(2019, 11, 4)),
                tuple("kotlinxLocalDate", SqlType.DATE, false, kotlinx.datetime.LocalDate(2019, 11, 6)),
                tuple("localTim", SqlType.TIME, false, LocalTime.of(11, 25, 55, 123456789)),
                tuple("localDateTime1", SqlType.DATE_TIME, false, LocalDateTime.of(2018, 11, 4, 0, 0)),
                tuple("localDateTime2", SqlType.TIMESTAMP, false, LocalDateTime.of(2019, 11, 4, 0, 0)),
                tuple(
                    "kotlinxLocalDateTime1",
                    SqlType.DATE_TIME,
                    false,
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
                ),
                tuple(
                    "kotlinxLocalDateTime2",
                    SqlType.TIMESTAMP,
                    false,
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
                ),
                tuple("int", SqlType.INT, false, 42),
                tuple("long", SqlType.BIGINT, false, 84L),
                tuple(
                    "offsetDateTime", SqlType.TIMESTAMP_WITH_TIME_ZONE, false, OffsetDateTime.of(
                        2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                    )
                ),
                tuple("uuid", SqlType.UUID, false, UUID.fromString(defaultUuid)),
            )
    }

    @Test
    fun `Test primary and foreign key`() {
        val tables = tables().h2(
            H2Roles,
            H2Users
        )
        val roleTable = tables.allTables[H2Roles] ?: fail { "require mapped RoleEntity" }
        assertThat(roleTable.columns)
            .extracting("name", "sqlType", "nullable")
            .containsExactly(
                tuple("id", SqlType.INT, false),
                tuple("label", SqlType.VARCHAR, false)
            )
        assertThat(roleTable.primaryKey.name).isNull()
        assertThat(roleTable.primaryKey.columns)
            .containsExactly(H2Roles.id)
        val userTable = tables.allTables[H2Users] ?: fail { "require mapped UserEntity" }
        assertThat(userTable.columns)
            .extracting("name", "sqlType", "nullable")
            .containsExactlyInAnyOrder(
                tuple("id", SqlType.INT, false),
                tuple("fname", SqlType.VARCHAR, false),
                tuple("lname", SqlType.VARCHAR, false),
                tuple("isAdmin", SqlType.BOOLEAN, false),
                tuple("alias", SqlType.VARCHAR, true),
                tuple("roleId", SqlType.INT, false)
            )
        val userTablePk = userTable.primaryKey
        assertThat(userTablePk.columns[0].entityGetter).isEqualTo(UserEntity::id)
        assertThat(userTablePk.name).isEqualTo("PK_users")
        val userTableFk = userTable.foreignKeys.iterator().next()
        assertThat(userTableFk.name).isEqualTo("FK_users_roles")
        assertThat(userTableFk.references.values)
            .hasSize(1)
            .containsExactly(H2Roles.id)
    }
}
