/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.datetime.*
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
                    LocalTime.of(11, 25, 55),
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
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(
                MariadbAllTypesNotNull(
                    mariadbAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                    newLong, newByteArray, newLocalTime
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
            .expectNext(mariadbAllTypesNotNull)
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
        (sqlClient deleteAllFrom MariadbAllTypesNotNulls)
            .then(sqlClient deleteAllFrom MariadbAllTypesNullableWithTimes)
            .then(sqlClient deleteAllFrom MariadbAllTypesNullableDefaultValueWithTimes)
            .block()
    }

    private fun createTables() =
        (sqlClient createTable MariadbAllTypesNotNulls)
            .then(sqlClient createTable MariadbAllTypesNullableWithTimes)
            .then(sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValueWithTimes)

    private fun insertAllTypes() =
        sqlClient.insert(mariadbAllTypesNotNull, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MariadbAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MariadbAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MariadbAllTypesNullableDefaultValueWithTimes

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update MariadbAllTypesNotNulls
                set MariadbAllTypesNotNulls.string eq newString
                set MariadbAllTypesNotNulls.boolean eq newBoolean
                set MariadbAllTypesNotNulls.localDate eq newLocalDate
                set MariadbAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MariadbAllTypesNotNulls.localTim eq newLocalTime
                set MariadbAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MariadbAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.inte eq newInt
                set MariadbAllTypesNotNulls.longe eq newLong
                set MariadbAllTypesNotNulls.byteArray eq newByteArray
                where MariadbAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MariadbAllTypesNotNulls
                set MariadbAllTypesNotNulls.string eq MariadbAllTypesNotNulls.string
                set MariadbAllTypesNotNulls.boolean eq MariadbAllTypesNotNulls.boolean
                set MariadbAllTypesNotNulls.localDate eq MariadbAllTypesNotNulls.localDate
                set MariadbAllTypesNotNulls.kotlinxLocalDate eq MariadbAllTypesNotNulls.kotlinxLocalDate
                set MariadbAllTypesNotNulls.localTim eq MariadbAllTypesNotNulls.localTim
                set MariadbAllTypesNotNulls.localDateTime1 eq MariadbAllTypesNotNulls.localDateTime1
                set MariadbAllTypesNotNulls.localDateTime2 eq MariadbAllTypesNotNulls.localDateTime2
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime1 eq MariadbAllTypesNotNulls.kotlinxLocalDateTime1
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime2 eq MariadbAllTypesNotNulls.kotlinxLocalDateTime2
                set MariadbAllTypesNotNulls.inte eq MariadbAllTypesNotNulls.inte
                set MariadbAllTypesNotNulls.longe eq MariadbAllTypesNotNulls.longe
                set MariadbAllTypesNotNulls.byteArray eq MariadbAllTypesNotNulls.byteArray
                where MariadbAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
