/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*


class R2dbcAllTypesPostgresqlTest : AbstractR2dbcPostgresqlTest<AllTypesRepositoryPostgresql>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = AllTypesRepositoryPostgresql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNull().toList())
            .hasSize(1)
            .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() = runTest {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toList())
            .hasSize(1)
            .containsExactly(
                PostgresqlAllTypesNullableDefaultValueEntity(
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
                    OffsetDateTime.of(
                        2019, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                    ),
                    UUID.fromString(defaultUuid),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() = runTest {
        assertThat(repository.selectAllAllTypesNullable().toList())
            .hasSize(1)
            .containsExactly(postgresqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() = runTest {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newUuid = UUID.randomUUID()
        val newInt = 2
        val newLong = 2L
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate,
                newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newOffsetDateTime,
                newUuid
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    PostgresqlAllTypesNotNullEntity(
                        postgresqlAllTypesNotNull.id, "new", false,
                        newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                        newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong,
                        newOffsetDateTime, newUuid
                    )
                )
        }
    }
}


class AllTypesRepositoryPostgresql(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertAllTypes()
    }

    override fun delete() = runBlocking<Unit> {
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private suspend fun createTables() {
        sqlClient createTable POSTGRESQL_ALL_TYPES_NOT_NULL
        sqlClient createTable POSTGRESQL_ALL_TYPES_NULLABLE
        sqlClient createTableIfNotExists POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private suspend fun insertAllTypes() {
        sqlClient insert postgresqlAllTypesNotNull
        sqlClient insert postgresqlAllTypesNullable
        sqlClient insert postgresqlAllTypesNullableDefaultValue
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom POSTGRESQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    suspend fun updateAllTypesNotNull(
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
