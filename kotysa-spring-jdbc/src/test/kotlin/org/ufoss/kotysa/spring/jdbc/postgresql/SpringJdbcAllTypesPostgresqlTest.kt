/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

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
import java.time.*
import java.util.*


class SpringJdbcAllTypesPostgresqlTest : AbstractSpringJdbcPostgresqlTest<AllTypesRepositoryPostgresql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryPostgresql>(resource)
    }

    override val repository: AllTypesRepositoryPostgresql by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(PostgresqlAllTypesNullableDefaultValueEntity(
                        1,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalTime.of(11, 25, 55, 123456789),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)),
                        UUID.fromString(defaultUuid),
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newUuid = UUID.randomUUID()
        val newInt = 2
        val newLong = 2L
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newOffsetDateTime,
                    newUuid)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            PostgresqlAllTypesNotNullEntity(postgresqlAllTypesNotNull.id, "new", false,
                                    newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong,
                                    newOffsetDateTime, newUuid))
        }
    }
}


class AllTypesRepositoryPostgresql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun createTables() {
        sqlClient createTable POSTGRESQL_ALL_TYPES_NOT_NULL
        sqlClient createTable POSTGRESQL_ALL_TYPES_NULLABLE
        sqlClient createTable POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private fun insertAllTypes() {
        sqlClient.insert(postgresqlAllTypesNotNull, postgresqlAllTypesNullable, postgresqlAllTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    fun updateAllTypesNotNull(
            newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
            newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
            newLocalDateTime: LocalDateTime, newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int,
            newLong: Long, newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
            (sqlClient update POSTGRESQL_ALL_TYPES_NOT_NULL
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.string eq newString
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.boolean eq newBoolean
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.localDate eq newLocalDate
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.kotlinxLocalDate eq newKotlinxLocalDate
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.localTim eq newLocalTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.localDateTime1 eq newLocalDateTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.localDateTime2 eq newLocalDateTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.int eq newInt
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.long eq newLong
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.offsetDateTime eq newOffsetDateTime
                    set POSTGRESQL_ALL_TYPES_NOT_NULL.uuid eq newUuid
                    where POSTGRESQL_ALL_TYPES_NOT_NULL.id eq allTypesNotNullWithTime.id
                    ).execute()
}
