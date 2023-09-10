/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal
import java.time.*
import java.util.*

class VertxCoroutinesAllTypesPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<AllTypesRepositoryPostgresql>() {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = AllTypesRepositoryPostgresql(sqlClient)

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
                    kotlinx.datetime.LocalTime(11, 25, 55, 123456789),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L,
                    42.42f,
                    84.84,
                    BigDecimal("4.2"),
                    BigDecimal("4.3"),
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
    fun `Verify updateAllTypesNotNull works`() = runTest {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
        val newKotlinxLocalTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).time
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newFloat = 2.2f
        val newDouble = 2.2
        val newByteArray = byteArrayOf(0x2B)
        val newBigDecimal = BigDecimal("3.3")
        val newOffsetDateTime = OffsetDateTime.now()
        val newUuid = UUID.randomUUID()
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal, newOffsetDateTime, newUuid
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    PostgresqlAllTypesNotNullEntity(
                        postgresqlAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalTime, newKotlinxLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                        newBigDecimal, newOffsetDateTime, newUuid
                    )
                )
        }
    }

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlAllTypesNotNull)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValues())
                .isEqualTo(
                    PostgresqlAllTypesNullableDefaultValueEntity(
                        postgresqlAllTypesNullableDefaultValueToInsert.id,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalTime.of(11, 25, 55, 123456789),
                        kotlinx.datetime.LocalTime(11, 25, 55, 123456789),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        42.42f,
                        84.84,
                        BigDecimal("4.2"),
                        BigDecimal("4.3"),
                        OffsetDateTime.of(
                            2019, 11, 4, 0, 0, 0, 0,
                            ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                        ),
                        UUID.fromString(defaultUuid),
                    )
                )
        }
    }
}


class AllTypesRepositoryPostgresql(private val sqlClient: CoroutinesVertxSqlClient) : Repository {

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
        newString: String,
        newBoolean: Boolean,
        newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalTime: LocalTime,
        newKotlinxLocalTime: kotlinx.datetime.LocalTime,
        newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        newInt: Int,
        newLong: Long,
        newByteArray: ByteArray,
        newFloat: Float,
        newDouble: Double,
        newBigDecimal: BigDecimal,
        newOffsetDateTime: OffsetDateTime,
        newUuid: UUID,
    ) =
        (sqlClient update PostgresqlAllTypesNotNulls
                set PostgresqlAllTypesNotNulls.string eq newString
                set PostgresqlAllTypesNotNulls.boolean eq newBoolean
                set PostgresqlAllTypesNotNulls.localDate eq newLocalDate
                set PostgresqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set PostgresqlAllTypesNotNulls.localTim eq newLocalTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalTim eq newKotlinxLocalTime
                set PostgresqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set PostgresqlAllTypesNotNulls.inte eq newInt
                set PostgresqlAllTypesNotNulls.longe eq newLong
                set PostgresqlAllTypesNotNulls.byteArray eq newByteArray
                set PostgresqlAllTypesNotNulls.float eq newFloat
                set PostgresqlAllTypesNotNulls.double eq newDouble
                set PostgresqlAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set PostgresqlAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                set PostgresqlAllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set PostgresqlAllTypesNotNulls.uuid eq newUuid
                where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun updateAllTypesNotNullColumn() =
        (sqlClient update PostgresqlAllTypesNotNulls
                set PostgresqlAllTypesNotNulls.string eq PostgresqlAllTypesNotNulls.string
                set PostgresqlAllTypesNotNulls.boolean eq PostgresqlAllTypesNotNulls.boolean
                set PostgresqlAllTypesNotNulls.localDate eq PostgresqlAllTypesNotNulls.localDate
                set PostgresqlAllTypesNotNulls.kotlinxLocalDate eq PostgresqlAllTypesNotNulls.kotlinxLocalDate
                set PostgresqlAllTypesNotNulls.localTim eq PostgresqlAllTypesNotNulls.localTim
                set PostgresqlAllTypesNotNulls.kotlinxLocalTim eq PostgresqlAllTypesNotNulls.kotlinxLocalTim
                set PostgresqlAllTypesNotNulls.localDateTime1 eq PostgresqlAllTypesNotNulls.localDateTime1
                set PostgresqlAllTypesNotNulls.localDateTime2 eq PostgresqlAllTypesNotNulls.localDateTime2
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1 eq PostgresqlAllTypesNotNulls.kotlinxLocalDateTime1
                set PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2 eq PostgresqlAllTypesNotNulls.kotlinxLocalDateTime2
                set PostgresqlAllTypesNotNulls.inte eq PostgresqlAllTypesNotNulls.inte
                set PostgresqlAllTypesNotNulls.longe eq PostgresqlAllTypesNotNulls.longe
                set PostgresqlAllTypesNotNulls.byteArray eq PostgresqlAllTypesNotNulls.byteArray
                set PostgresqlAllTypesNotNulls.float eq PostgresqlAllTypesNotNulls.float
                set PostgresqlAllTypesNotNulls.double eq PostgresqlAllTypesNotNulls.double
                set PostgresqlAllTypesNotNulls.bigDecimal1 eq PostgresqlAllTypesNotNulls.bigDecimal1
                set PostgresqlAllTypesNotNulls.bigDecimal2 eq PostgresqlAllTypesNotNulls.bigDecimal2
                set PostgresqlAllTypesNotNulls.offsetDateTime eq PostgresqlAllTypesNotNulls.offsetDateTime
                set PostgresqlAllTypesNotNulls.uuid eq PostgresqlAllTypesNotNulls.uuid
                where PostgresqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun insertAndReturnAllTypesDefaultValues() =
        sqlClient insertAndReturn postgresqlAllTypesNullableDefaultValueToInsert
}
