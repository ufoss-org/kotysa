/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.transactional
import org.ufoss.kotysa.spring.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.time.*
import java.util.*


class R2DbcAllTypesH2Test : AbstractR2dbcH2Test<AllTypesRepositoryH2>() {
    override val context = startContext<AllTypesRepositoryH2>()
    override val repository = getContextRepository<AllTypesRepositoryH2>()

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
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
            .hasSize(1)
            .containsExactly(h2AllTypesNullable)
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
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newOffsetDateTime, newUuid
            )
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(
                H2AllTypesNotNullEntity(
                    h2AllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime,
                    newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                    newLong, newByteArray, newOffsetDateTime, newUuid
                )
            )
            .verifyComplete()
    }
}


class AllTypesRepositoryH2(
    private val sqlClient: ReactorSqlClient,
    transactionalOperator: TransactionalOperator
) : Repository {

    private val operator = transactionalOperator.transactionalOp()

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
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray,
        newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
        (sqlClient update H2AllTypesNotNulls
                set H2AllTypesNotNulls.string eq newString
                set H2AllTypesNotNulls.boolean eq newBoolean
                set H2AllTypesNotNulls.localDate eq newLocalDate
                set H2AllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set H2AllTypesNotNulls.localTim eq newLocalTime
                set H2AllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set H2AllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set H2AllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set H2AllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set H2AllTypesNotNulls.int eq newInt
                set H2AllTypesNotNulls.long eq newLong
                set H2AllTypesNotNulls.byteArray eq newByteArray
                set H2AllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                set H2AllTypesNotNulls.uuid eq newUuid
                where H2AllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
