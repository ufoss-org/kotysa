/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcReactorTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.transactional
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.math.BigDecimal
import java.time.*
import java.util.*


class R2DbcAllTypesH2Test : AbstractR2dbcH2Test<AllTypesRepositoryH2>() {

    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        AllTypesRepositoryH2(sqlClient, operator)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
            .hasSize(1)
            .containsExactly(h2AllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
            .hasSize(1)
            .containsExactly(
                H2AllTypesNullableDefaultValueEntity(
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
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
            .hasSize(1)
            .containsExactly(h2AllTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() {
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
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal, newOffsetDateTime, newUuid
            )
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(
                H2AllTypesNotNullEntity(
                    h2AllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime,
                    newKotlinxLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal,
                    newBigDecimal, newOffsetDateTime, newUuid
                )
            )
            .verifyComplete()
    }

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(h2AllTypesNotNull)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.test()
            .expectNext(
                H2AllTypesNullableDefaultValueEntity(
                    h2AllTypesNullableDefaultValueToInsert.id,
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
            .verifyComplete()
    }
}


class AllTypesRepositoryH2(
    private val sqlClient: ReactorSqlClient,
    private val operator: SpringR2dbcReactorTransactionalOp
) : Repository {

    override fun init() {
        createTables()
            .then(insertAllTypes().then())
            // another option would be to plug in SingleConnectionFactory somehow
            // because in memory (serverless) h2 databases don't seem to be shared between connections
            .transactional(operator)
            .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom H2AllTypesNotNulls)
            .then(sqlClient deleteAllFrom H2AllTypesNullables)
            .then(sqlClient deleteAllFrom H2AllTypesNullableDefaultValues)
            .block()
    }

    private fun createTables() =
        (sqlClient createTable H2AllTypesNotNulls)
            .then(sqlClient createTable H2AllTypesNullables)
            .then(sqlClient createTableIfNotExists H2AllTypesNullableDefaultValues)

    private fun insertAllTypes() =
        sqlClient.insert(h2AllTypesNotNull, h2AllTypesNullable, h2AllTypesNullableDefaultValue)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom H2AllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom H2AllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom H2AllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
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
        (sqlClient update H2AllTypesNotNulls
                set H2AllTypesNotNulls.string eq newString
                set H2AllTypesNotNulls.boolean eq newBoolean
                set H2AllTypesNotNulls.localDate eq newLocalDate
                set H2AllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set H2AllTypesNotNulls.localTim eq newLocalTime
                set H2AllTypesNotNulls.kotlinxLocalTim eq newKotlinxLocalTime
                set H2AllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set H2AllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set H2AllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set H2AllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set H2AllTypesNotNulls.inte eq newInt
                set H2AllTypesNotNulls.longe eq newLong
                set H2AllTypesNotNulls.byteArray eq newByteArray
                set H2AllTypesNotNulls.float eq newFloat
                set H2AllTypesNotNulls.double eq newDouble
                set H2AllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set H2AllTypesNotNulls.bigDecimal2 eq newBigDecimal
                set H2AllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set H2AllTypesNotNulls.uuid eq newUuid
                where H2AllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update H2AllTypesNotNulls
                set H2AllTypesNotNulls.string eq H2AllTypesNotNulls.string
                set H2AllTypesNotNulls.boolean eq H2AllTypesNotNulls.boolean
                set H2AllTypesNotNulls.localDate eq H2AllTypesNotNulls.localDate
                set H2AllTypesNotNulls.kotlinxLocalDate eq H2AllTypesNotNulls.kotlinxLocalDate
                set H2AllTypesNotNulls.localTim eq H2AllTypesNotNulls.localTim
                set H2AllTypesNotNulls.kotlinxLocalTim eq H2AllTypesNotNulls.kotlinxLocalTim
                set H2AllTypesNotNulls.localDateTime1 eq H2AllTypesNotNulls.localDateTime1
                set H2AllTypesNotNulls.localDateTime2 eq H2AllTypesNotNulls.localDateTime2
                set H2AllTypesNotNulls.kotlinxLocalDateTime1 eq H2AllTypesNotNulls.kotlinxLocalDateTime1
                set H2AllTypesNotNulls.kotlinxLocalDateTime2 eq H2AllTypesNotNulls.kotlinxLocalDateTime2
                set H2AllTypesNotNulls.inte eq H2AllTypesNotNulls.inte
                set H2AllTypesNotNulls.longe eq H2AllTypesNotNulls.longe
                set H2AllTypesNotNulls.byteArray eq H2AllTypesNotNulls.byteArray
                set H2AllTypesNotNulls.float eq H2AllTypesNotNulls.float
                set H2AllTypesNotNulls.double eq H2AllTypesNotNulls.double
                set H2AllTypesNotNulls.bigDecimal1 eq H2AllTypesNotNulls.bigDecimal1
                set H2AllTypesNotNulls.bigDecimal2 eq H2AllTypesNotNulls.bigDecimal2
                set H2AllTypesNotNulls.offsetDateTime eq H2AllTypesNotNulls.offsetDateTime
                set H2AllTypesNotNulls.uuid eq H2AllTypesNotNulls.uuid
                where H2AllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn h2AllTypesNullableDefaultValueToInsert
}
