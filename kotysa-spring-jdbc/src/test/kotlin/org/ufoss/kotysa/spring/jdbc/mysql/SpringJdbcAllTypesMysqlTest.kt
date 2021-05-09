/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class SpringJdbcAllTypesMysqlTest : AbstractSpringJdbcMysqlTest<AllTypesRepositoryMysql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMysql>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(mysqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(AllTypesNullableDefaultValueEntity(
                        allTypesNullableDefaultValue.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalTime.of(11, 25, 55),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(allTypesNullable)
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
                            MysqlAllTypesNotNull(mysqlAllTypesNotNull.id, "new", false,
                                    newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong))
        }
    }
}


class AllTypesRepositoryMysql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MYSQL_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom MYSQL_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun createTables() {
        sqlClient createTable MYSQL_ALL_TYPES_NOT_NULL
        sqlClient createTable MYSQL_ALL_TYPES_NULLABLE
        sqlClient createTable MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun insertAllTypes() {
        sqlClient.insert(mysqlAllTypesNotNull, allTypesNullable, allTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MYSQL_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MYSQL_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MYSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newKotlinxLocalDate: kotlinx.datetime.LocalDate,
                              newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
                              newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long) =
            (sqlClient update MYSQL_ALL_TYPES_NOT_NULL
                    set MYSQL_ALL_TYPES_NOT_NULL.string eq newString
                    set MYSQL_ALL_TYPES_NOT_NULL.boolean eq newBoolean
                    set MYSQL_ALL_TYPES_NOT_NULL.localDate eq newLocalDate
                    set MYSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDate eq newKotlinxLocalDate
                    set MYSQL_ALL_TYPES_NOT_NULL.localTim eq newLocalTime
                    set MYSQL_ALL_TYPES_NOT_NULL.localDateTime1 eq newLocalDateTime
                    set MYSQL_ALL_TYPES_NOT_NULL.localDateTime2 eq newLocalDateTime
                    set MYSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                    set MYSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                    set MYSQL_ALL_TYPES_NOT_NULL.inte eq newInt
                    set MYSQL_ALL_TYPES_NOT_NULL.longe eq newLong
                    where MYSQL_ALL_TYPES_NOT_NULL.id eq allTypesNotNull.id
                    ).execute()
}
