/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class R2DbcAllTypesMariadbTest : AbstractR2dbcMariadbTest<AllTypesRepositoryMariadb>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        AllTypesRepositoryMariadb(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
            .hasSize(1)
            .containsExactly(mariadbAllTypesNotNullWithTime)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
            .hasSize(1)
            .containsExactly(
                AllTypesNullableDefaultValueWithTimeEntity(
                    allTypesNullableDefaultValueWithTime.id,
                    "default",
                    LocalDate.of(2019, 11, 4),
                    LocalDate(2019, 11, 6),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    LocalDateTime(2018, 11, 4, 0, 0),
                    LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L,
                    42.42f,
                    84.84,
                    BigDecimal("4.2"),
                    BigDecimal("4.3"),
                    LocalTime.of(11, 25, 55),
                    LocalTime(11, 25, 55),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
            .hasSize(1)
            .containsExactly(allTypesNullableWithTime)
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
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal
            )
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(
                MariadbAllTypesNotNullWithTime(
                    mariadbAllTypesNotNullWithTime.id, "new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                    newLong, newByteArray, newLocalTime, newKotlinxLocalTime, newFloat, newDouble, newBigDecimal,
                    newBigDecimal
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
            .expectNext(mariadbAllTypesNotNullWithTime)
            .verifyComplete()
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.insertAndReturnAllTypesDefaultValues()
        }.test()
            .expectNext(
                AllTypesNullableDefaultValueWithTimeEntity(
                    allTypesNullableDefaultValueWithTimeToInsert.id,
                    "default",
                    LocalDate.of(2019, 11, 4),
                    LocalDate(2019, 11, 6),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    LocalDateTime(2018, 11, 4, 0, 0),
                    LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L,
                    42.42f,
                    84.84,
                    BigDecimal("4.2"),
                    BigDecimal("4.3"),
                    LocalTime.of(11, 25, 55),
                    LocalTime(11, 25, 55),
                )
            )
            .verifyComplete()
    }
}


class AllTypesRepositoryMariadb(private val sqlClient: MariadbReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertAllTypes().then())
            .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MariadbAllTypesNotNullWithTimes)
            .then(sqlClient deleteAllFrom MariadbAllTypesNullableWithTimes)
            .then(sqlClient deleteAllFrom MariadbAllTypesNullableDefaultValueWithTimes)
            .block()
    }

    private fun createTables() =
        (sqlClient createTable MariadbAllTypesNotNullWithTimes)
            .then(sqlClient createTable MariadbAllTypesNullableWithTimes)
            .then(sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValueWithTimes)

    private fun insertAllTypes() =
        sqlClient.insert(mariadbAllTypesNotNullWithTime, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MariadbAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MariadbAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MariadbAllTypesNullableDefaultValueWithTimes

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
    ) =
        (sqlClient update MariadbAllTypesNotNullWithTimes
                set MariadbAllTypesNotNullWithTimes.string eq newString
                set MariadbAllTypesNotNullWithTimes.boolean eq newBoolean
                set MariadbAllTypesNotNullWithTimes.localDate eq newLocalDate
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDate eq newKotlinxLocalDate
                set MariadbAllTypesNotNullWithTimes.localTim eq newLocalTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalTim eq newKotlinxLocalTime
                set MariadbAllTypesNotNullWithTimes.localDateTime1 eq newLocalDateTime
                set MariadbAllTypesNotNullWithTimes.localDateTime2 eq newLocalDateTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNullWithTimes.inte eq newInt
                set MariadbAllTypesNotNullWithTimes.longe eq newLong
                set MariadbAllTypesNotNullWithTimes.byteArray eq newByteArray
                set MariadbAllTypesNotNullWithTimes.floate eq newFloat
                set MariadbAllTypesNotNullWithTimes.doublee eq newDouble
                set MariadbAllTypesNotNullWithTimes.bigDecimal1 eq newBigDecimal
                set MariadbAllTypesNotNullWithTimes.bigDecimal2 eq newBigDecimal
                where MariadbAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MariadbAllTypesNotNullWithTimes
                set MariadbAllTypesNotNullWithTimes.string eq MariadbAllTypesNotNullWithTimes.string
                set MariadbAllTypesNotNullWithTimes.boolean eq MariadbAllTypesNotNullWithTimes.boolean
                set MariadbAllTypesNotNullWithTimes.localDate eq MariadbAllTypesNotNullWithTimes.localDate
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDate eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDate
                set MariadbAllTypesNotNullWithTimes.localTim eq MariadbAllTypesNotNullWithTimes.localTim
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalTim eq MariadbAllTypesNotNullWithTimes.kotlinxLocalTim
                set MariadbAllTypesNotNullWithTimes.localDateTime1 eq MariadbAllTypesNotNullWithTimes.localDateTime1
                set MariadbAllTypesNotNullWithTimes.localDateTime2 eq MariadbAllTypesNotNullWithTimes.localDateTime2
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2
                set MariadbAllTypesNotNullWithTimes.inte eq MariadbAllTypesNotNullWithTimes.inte
                set MariadbAllTypesNotNullWithTimes.longe eq MariadbAllTypesNotNullWithTimes.longe
                set MariadbAllTypesNotNullWithTimes.byteArray eq MariadbAllTypesNotNullWithTimes.byteArray
                set MariadbAllTypesNotNullWithTimes.floate eq MariadbAllTypesNotNullWithTimes.floate
                set MariadbAllTypesNotNullWithTimes.doublee eq MariadbAllTypesNotNullWithTimes.doublee
                set MariadbAllTypesNotNullWithTimes.bigDecimal1 eq MariadbAllTypesNotNullWithTimes.bigDecimal1
                set MariadbAllTypesNotNullWithTimes.bigDecimal2 eq MariadbAllTypesNotNullWithTimes.bigDecimal2
                where MariadbAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTimeToInsert
}
