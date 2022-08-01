/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

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

class R2DbcAllTypesMysqlTest : AbstractR2dbcMysqlTest<AllTypesRepositoryMysql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMysql>(resource)
    }

    override val repository: AllTypesRepositoryMysql by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNull)
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
                    kotlinx.datetime.LocalDate(2019, 11, 6),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
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
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
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
                MysqlAllTypesNotNull(
                    mysqlAllTypesNotNull.id, "new", false, newLocalDate,
                    newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newInt, newLong, newByteArray, newLocalTime
                )
            )
            .verifyComplete()
    }
}


class AllTypesRepositoryMysql(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mysqlTables)

    override fun init() {
        createTables()
            .then(insertAllTypes().then())
            .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MysqlAllTypesNotNulls)
            .then(sqlClient deleteAllFrom MysqlAllTypesNullableWithTimes)
            .then(sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValueWithTimes)
            .block()
    }

    private fun createTables() =
        (sqlClient createTable MysqlAllTypesNotNulls)
            .then(sqlClient createTable MysqlAllTypesNullableWithTimes)
            .then(sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValueWithTimes)

    private fun insertAllTypes() =
        sqlClient.insert(mysqlAllTypesNotNull, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MysqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MysqlAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MysqlAllTypesNullableDefaultValueWithTimes

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update MysqlAllTypesNotNulls
                set MysqlAllTypesNotNulls.string eq newString
                set MysqlAllTypesNotNulls.boolean eq newBoolean
                set MysqlAllTypesNotNulls.localDate eq newLocalDate
                set MysqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MysqlAllTypesNotNulls.localTim eq newLocalTime
                set MysqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MysqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.inte eq newInt
                set MysqlAllTypesNotNulls.longe eq newLong
                set MysqlAllTypesNotNulls.byteArray eq newByteArray
                where MysqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
