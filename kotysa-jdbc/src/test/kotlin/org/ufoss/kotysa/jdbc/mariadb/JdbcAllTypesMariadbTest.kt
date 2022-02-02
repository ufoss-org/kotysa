/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class JdbcAllTypesMariadbTest : AbstractJdbcMariadbTest<AllTypesRepositoryMariadb>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = AllTypesRepositoryMariadb(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(mariadbAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(AllTypesNullableDefaultValueWithTimeEntity(
                        allTypesNullableDefaultValueWithTime.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        LocalTime.of(11, 25, 55),
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(allTypesNullableWithTime)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            MariadbAllTypesNotNull(mariadbAllTypesNotNull.id, "new", false, newLocalDate,
                                    newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                                    newKotlinxLocalDateTime, newInt, newLong, newLocalTime))
        }
    }
}


class AllTypesRepositoryMariadb(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MARIADB_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom MARIADB_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom MARIADB_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun createTables() {
        sqlClient createTable MARIADB_ALL_TYPES_NOT_NULL
        sqlClient createTable MARIADB_ALL_TYPES_NULLABLE
        sqlClient createTableIfNotExists MARIADB_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun insertAllTypes() {
        sqlClient.insert(mariadbAllTypesNotNull)
        sqlClient.insert(allTypesNullableWithTime)
        sqlClient.insert(allTypesNullableDefaultValueWithTime)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MARIADB_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MARIADB_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MARIADB_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newKotlinxLocalDate: kotlinx.datetime.LocalDate,
                              newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
                              newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long) =
            (sqlClient update MARIADB_ALL_TYPES_NOT_NULL
                    set MARIADB_ALL_TYPES_NOT_NULL.string eq newString
                    set MARIADB_ALL_TYPES_NOT_NULL.boolean eq newBoolean
                    set MARIADB_ALL_TYPES_NOT_NULL.localDate eq newLocalDate
                    set MARIADB_ALL_TYPES_NOT_NULL.kotlinxLocalDate eq newKotlinxLocalDate
                    set MARIADB_ALL_TYPES_NOT_NULL.localTim eq newLocalTime
                    set MARIADB_ALL_TYPES_NOT_NULL.localDateTime1 eq newLocalDateTime
                    set MARIADB_ALL_TYPES_NOT_NULL.localDateTime2 eq newLocalDateTime
                    set MARIADB_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                    set MARIADB_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                    set MARIADB_ALL_TYPES_NOT_NULL.inte eq newInt
                    set MARIADB_ALL_TYPES_NOT_NULL.longe eq newLong
                    where MARIADB_ALL_TYPES_NOT_NULL.id eq allTypesNotNullWithTime.id
                    ).execute()
}
