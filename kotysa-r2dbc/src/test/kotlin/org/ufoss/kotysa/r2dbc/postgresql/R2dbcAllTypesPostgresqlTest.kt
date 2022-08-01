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
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newOffsetDateTime, newUuid
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    PostgresqlAllTypesNotNullEntity(
                        postgresqlAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong, newByteArray, newOffsetDateTime, newUuid
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
        sqlClient deleteAllFrom PostgresqlAllTypesNotNulls
        sqlClient deleteAllFrom PostgresqlAllTypesNullables
        sqlClient deleteAllFrom PostgresqlAllTypesNullableDefaultValues
    }

    private suspend fun createTables() {
        sqlClient createTable PostgresqlAllTypesNotNulls
        sqlClient createTable PostgresqlAllTypesNullables
        sqlClient createTableIfNotExists PostgresqlAllTypesNullableDefaultValues
    }

    private suspend fun insertAllTypes() {
        sqlClient insert postgresqlAllTypesNotNull
        sqlClient insert postgresqlAllTypesNullable
        sqlClient insert postgresqlAllTypesNullableDefaultValue
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom PostgresqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom PostgresqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom PostgresqlAllTypesNullableDefaultValues

    suspend fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray,
        newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
        (sqlClient update PostgresqlAllTypesNotNulls
                set PostgresqlAllTypesNotNulls.string eq newString
                set PostgresqlAllTypesNotNulls.boolean eq newBoolean
                set PostgresqlAllTypesNotNulls.localDate eq newLocalDate
                set PostgresqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set PostgresqlAllTypesNotNulls.localTim eq newLocalTime
                set PostgresqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.int eq newInt
                set PostgresqlAllTypesNotNulls.long eq newLong
                set PostgresqlAllTypesNotNulls.byteArray eq newByteArray
                set PostgresqlAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set PostgresqlAllTypesNotNulls.uuid eq newUuid
                where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
