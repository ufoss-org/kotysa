/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class R2dbcAllTypesMysqlTest : AbstractR2dbcMysqlTest<AllTypesRepositoryMysql>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = AllTypesRepositoryMysql(sqlClient)

    @Tag("miku")
    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNull().toList())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNullWithTime)
    }

    @Tag("miku")
    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() = runTest {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toList())
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
                    LocalTime(11, 25, 55),
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() = runTest {
        assertThat(repository.selectAllAllTypesNullable().toList())
            .hasSize(1)
            .containsExactly(allTypesNullableWithTime)
    }

    @Tag("miku")
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
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MysqlAllTypesNotNullWithTime(
                        mysqlAllTypesNotNullWithTime.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                        newLong, newByteArray, newLocalTime, newKotlinxLocalTime
                    )
                )
        }
    }

    @Tag("miku")
    @Test
    fun `Verify updateAllTypesNotNullColumn works`() = runTest {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlAllTypesNotNullWithTime)
        }
    }
}


class AllTypesRepositoryMysql(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertAllTypes()
    }

    override fun delete() = runBlocking<Unit> {
        sqlClient deleteAllFrom MysqlAllTypesNotNullWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun createTables() {
        sqlClient createTable MysqlAllTypesNotNullWithTimes
        sqlClient createTable MysqlAllTypesNullableWithTimes
        sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun insertAllTypes() {
        sqlClient insert mysqlAllTypesNotNullWithTime
        sqlClient insert allTypesNullableWithTime
        sqlClient insert allTypesNullableDefaultValueWithTime
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MysqlAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MysqlAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MysqlAllTypesNullableDefaultValueWithTimes

    suspend fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
        newKotlinxLocalTime: kotlinx.datetime.LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update MysqlAllTypesNotNullWithTimes
                set MysqlAllTypesNotNullWithTimes.string eq newString
                set MysqlAllTypesNotNullWithTimes.boolean eq newBoolean
                set MysqlAllTypesNotNullWithTimes.localDate eq newLocalDate
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDate eq newKotlinxLocalDate
                set MysqlAllTypesNotNullWithTimes.localTim eq newLocalTime
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalTim eq newKotlinxLocalTime
                set MysqlAllTypesNotNullWithTimes.localDateTime1 eq newLocalDateTime
                set MysqlAllTypesNotNullWithTimes.localDateTime2 eq newLocalDateTime
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNullWithTimes.inte eq newInt
                set MysqlAllTypesNotNullWithTimes.longe eq newLong
                set MysqlAllTypesNotNullWithTimes.byteArray eq newByteArray
                where MysqlAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun updateAllTypesNotNullColumn() =
        (sqlClient update MysqlAllTypesNotNullWithTimes
                set MysqlAllTypesNotNullWithTimes.string eq MysqlAllTypesNotNullWithTimes.string
                set MysqlAllTypesNotNullWithTimes.boolean eq MysqlAllTypesNotNullWithTimes.boolean
                set MysqlAllTypesNotNullWithTimes.localDate eq MysqlAllTypesNotNullWithTimes.localDate
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDate eq MysqlAllTypesNotNullWithTimes.kotlinxLocalDate
                set MysqlAllTypesNotNullWithTimes.localTim eq MysqlAllTypesNotNullWithTimes.localTim
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalTim eq MysqlAllTypesNotNullWithTimes.kotlinxLocalTim
                set MysqlAllTypesNotNullWithTimes.localDateTime1 eq MysqlAllTypesNotNullWithTimes.localDateTime1
                set MysqlAllTypesNotNullWithTimes.localDateTime2 eq MysqlAllTypesNotNullWithTimes.localDateTime2
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime1
                set MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq MysqlAllTypesNotNullWithTimes.kotlinxLocalDateTime2
                set MysqlAllTypesNotNullWithTimes.inte eq MysqlAllTypesNotNullWithTimes.inte
                set MysqlAllTypesNotNullWithTimes.longe eq MysqlAllTypesNotNullWithTimes.longe
                set MysqlAllTypesNotNullWithTimes.byteArray eq MysqlAllTypesNotNullWithTimes.byteArray
                where MysqlAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()
}
