/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class R2DbcAllTypesMariadbTest : AbstractR2dbcMariadbTest<AllTypesRepositoryMariadb>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMariadb>(resource)
    }

    override val repository: AllTypesRepositoryMariadb by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
                .hasSize(1)
                .containsExactly(mariadbAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
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
        assertThat(repository.selectAllAllTypesNullable().toIterable())
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
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
                .expectNext(MariadbAllTypesNotNull(mariadbAllTypesNotNull.id, "new", false, newLocalDate,
                    newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newLocalTime))
                .verifyComplete()
    }
}


class AllTypesRepositoryMariadb(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mariadbTables)

    override fun init() {
        createTables()
                .then(insertAllTypes().then())
                .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MARIADB_ALL_TYPES_NOT_NULL)
                .then(sqlClient deleteAllFrom MARIADB_ALL_TYPES_NULLABLE)
                .then(sqlClient deleteAllFrom MARIADB_ALL_TYPES_NULLABLE_DEFAULT_VALUE)
                .block()
    }

    private fun createTables() =
            (sqlClient createTable MARIADB_ALL_TYPES_NOT_NULL)
                    .then(sqlClient createTable MARIADB_ALL_TYPES_NULLABLE)
                    .then(sqlClient createTableIfNotExists MARIADB_ALL_TYPES_NULLABLE_DEFAULT_VALUE)

    private fun insertAllTypes() =
            sqlClient.insert(mariadbAllTypesNotNull, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)

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
