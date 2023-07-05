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
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class R2dbcAllTypesMysqlTest : AbstractR2dbcMysqlTest<AllTypesRepositoryMysql>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = AllTypesRepositoryMysql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNull().toList())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() = runTest {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toList())
            .hasSize(1)
            .containsExactly(
                AllTypesNullableDefaultValueEntity(
                    allTypesNullableDefaultValue.id,
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
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() = runTest {
        assertThat(repository.selectAllAllTypesNullable().toList())
            .hasSize(1)
            .containsExactly(allTypesNullable)
    }

    @Test
    fun `Verify updateAllTypesNotNull works`() = runTest {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newFloat = 2.2f
        val newDouble = 2.2
        val newByteArray = byteArrayOf(0x2B)
        val newBigDecimal = BigDecimal("3.3")
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble, newBigDecimal
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MysqlAllTypesNotNull(
                        mysqlAllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                        newLong, newByteArray, newFloat, newDouble, newBigDecimal, newBigDecimal
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
                .containsExactlyInAnyOrder(mysqlAllTypesNotNull)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValues())
                .isEqualTo(
                    AllTypesNullableDefaultValueEntity(
                        allTypesNullableDefaultValueToInsert.id,
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
                    )
                )
        }
    }

    @Tag("asyncer")
    @Test
    fun `Verify selectAllAllTypesNotNullWithTime returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNullWithTime().toList())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNullWithTime)
    }

    @Tag("asyncer")
    @Test
    fun `Verify selectAllAllTypesNullableDefaultValueWithTime returns all AllTypesNullableDefaultValueWithTime`() =
        runTest {
            assertThat(repository.selectAllAllTypesNullableDefaultValueWithTime().toList())
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
    fun `Verify selectAllAllTypesNullableWithTime returns all AllTypesNullableWithTime`() = runTest {
        assertThat(repository.selectAllAllTypesNullableWithTime().toList())
            .hasSize(1)
            .containsExactly(allTypesNullableWithTime)
    }

    @Tag("asyncer")
    @Test
    fun `Verify updateAllTypesNotNullWithTime works`() = runTest {
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
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullWithTime(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal
            )
            assertThat(repository.selectAllAllTypesNotNullWithTime().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MysqlAllTypesNotNullWithTime(
                        mysqlAllTypesNotNullWithTime.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                        newLong, newByteArray, newLocalTime, newKotlinxLocalTime, newFloat, newDouble, newBigDecimal,
                        newBigDecimal
                    )
                )
        }
    }

    @Tag("asyncer")
    @Test
    fun `Verify updateAllTypesNotNullColumnWithTime works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumnWithTime()
            assertThat(repository.selectAllAllTypesNotNullWithTime().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlAllTypesNotNullWithTime)
        }
    }

    @Tag("asyncer")
    @Test
    fun `Verify insertAndReturnAllTypesDefaultValuesWithTime works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValuesWithTime())
                .isEqualTo(
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
        }
    }
}


class AllTypesRepositoryMysql(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertAllTypes()
    }

    override fun delete() = runBlocking<Unit> {
        sqlClient deleteAllFrom MysqlAllTypesNotNulls
        sqlClient deleteAllFrom MysqlAllTypesNullables
        sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValues
        sqlClient deleteAllFrom MysqlAllTypesNotNullWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun createTables() {
        sqlClient createTable MysqlAllTypesNotNulls
        sqlClient createTable MysqlAllTypesNullables
        sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValues
        sqlClient createTable MysqlAllTypesNotNullWithTimes
        sqlClient createTable MysqlAllTypesNullableWithTimes
        sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun insertAllTypes() {
        sqlClient insert mysqlAllTypesNotNull
        sqlClient insert allTypesNullable
        sqlClient insert allTypesNullableDefaultValue
        sqlClient insert mysqlAllTypesNotNullWithTime
        sqlClient insert allTypesNullableWithTime
        sqlClient insert allTypesNullableDefaultValueWithTime
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MysqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MysqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MysqlAllTypesNullableDefaultValues

    suspend fun updateAllTypesNotNull(
        newString: String,
        newBoolean: Boolean,
        newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        newInt: Int,
        newLong: Long,
        newByteArray: ByteArray,
        newFloat: Float,
        newDouble: Double,
        newBigDecimal: BigDecimal,
    ) =
        (sqlClient update MysqlAllTypesNotNulls
                set MysqlAllTypesNotNulls.string eq newString
                set MysqlAllTypesNotNulls.boolean eq newBoolean
                set MysqlAllTypesNotNulls.localDate eq newLocalDate
                set MysqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MysqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MysqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MysqlAllTypesNotNulls.inte eq newInt
                set MysqlAllTypesNotNulls.longe eq newLong
                set MysqlAllTypesNotNulls.byteArray eq newByteArray
                set MysqlAllTypesNotNulls.floate eq newFloat
                set MysqlAllTypesNotNulls.doublee eq newDouble
                set MysqlAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set MysqlAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                where MysqlAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()

    suspend fun updateAllTypesNotNullColumn() =
        (sqlClient update MysqlAllTypesNotNulls
                set MysqlAllTypesNotNulls.string eq MysqlAllTypesNotNulls.string
                set MysqlAllTypesNotNulls.boolean eq MysqlAllTypesNotNulls.boolean
                set MysqlAllTypesNotNulls.localDate eq MysqlAllTypesNotNulls.localDate
                set MysqlAllTypesNotNulls.kotlinxLocalDate eq MysqlAllTypesNotNulls.kotlinxLocalDate
                set MysqlAllTypesNotNulls.localDateTime1 eq MysqlAllTypesNotNulls.localDateTime1
                set MysqlAllTypesNotNulls.localDateTime2 eq MysqlAllTypesNotNulls.localDateTime2
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime1 eq MysqlAllTypesNotNulls.kotlinxLocalDateTime1
                set MysqlAllTypesNotNulls.kotlinxLocalDateTime2 eq MysqlAllTypesNotNulls.kotlinxLocalDateTime2
                set MysqlAllTypesNotNulls.inte eq MysqlAllTypesNotNulls.inte
                set MysqlAllTypesNotNulls.longe eq MysqlAllTypesNotNulls.longe
                set MysqlAllTypesNotNulls.byteArray eq MysqlAllTypesNotNulls.byteArray
                set MysqlAllTypesNotNulls.floate eq MysqlAllTypesNotNulls.floate
                set MysqlAllTypesNotNulls.doublee eq MysqlAllTypesNotNulls.doublee
                set MysqlAllTypesNotNulls.bigDecimal1 eq MysqlAllTypesNotNulls.bigDecimal1
                set MysqlAllTypesNotNulls.bigDecimal2 eq MysqlAllTypesNotNulls.bigDecimal2
                where MysqlAllTypesNotNulls.id eq allTypesNotNull.id
                ).execute()

    suspend fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueToInsert

    fun selectAllAllTypesNotNullWithTime() = sqlClient selectAllFrom MysqlAllTypesNotNullWithTimes

    fun selectAllAllTypesNullableWithTime() = sqlClient selectAllFrom MysqlAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValueWithTime() =
        sqlClient selectAllFrom MysqlAllTypesNullableDefaultValueWithTimes

    suspend fun updateAllTypesNotNullWithTime(
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
                set MysqlAllTypesNotNullWithTimes.floate eq newFloat
                set MysqlAllTypesNotNullWithTimes.doublee eq newDouble
                set MysqlAllTypesNotNullWithTimes.bigDecimal1 eq newBigDecimal
                set MysqlAllTypesNotNullWithTimes.bigDecimal2 eq newBigDecimal
                where MysqlAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun updateAllTypesNotNullColumnWithTime() =
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
                set MysqlAllTypesNotNullWithTimes.floate eq MysqlAllTypesNotNullWithTimes.floate
                set MysqlAllTypesNotNullWithTimes.doublee eq MysqlAllTypesNotNullWithTimes.doublee
                set MysqlAllTypesNotNullWithTimes.bigDecimal1 eq MysqlAllTypesNotNullWithTimes.bigDecimal1
                set MysqlAllTypesNotNullWithTimes.bigDecimal2 eq MysqlAllTypesNotNullWithTimes.bigDecimal2
                where MysqlAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun insertAndReturnAllTypesDefaultValuesWithTime() = sqlClient insertAndReturn
            allTypesNullableDefaultValueWithTimeToInsert
}
