/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class SpringJdbcAllTypesMysqlTest : AbstractSpringJdbcMysqlTest<AllTypesRepositoryMysql>() {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = AllTypesRepositoryMysql(jdbcOperations)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
            .hasSize(1)
            .containsExactly(mysqlAllTypesNotNullWithTime)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
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
        assertThat(repository.selectAllAllTypesNullable())
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
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble,
                newBigDecimal
            )
            assertThat(repository.selectAllAllTypesNotNull())
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

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlAllTypesNotNullWithTime)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.insertAndReturnAllTypesDefaultValues())
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


class AllTypesRepositoryMysql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MysqlAllTypesNotNullWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableWithTimes
        sqlClient deleteAllFrom MysqlAllTypesNullableDefaultValueWithTimes
    }

    private fun createTables() {
        sqlClient createTable MysqlAllTypesNotNullWithTimes
        sqlClient createTable MysqlAllTypesNullableWithTimes
        sqlClient createTableIfNotExists MysqlAllTypesNullableDefaultValueWithTimes
    }

    private fun insertAllTypes() {
        sqlClient.insert(mysqlAllTypesNotNullWithTime, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MysqlAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MysqlAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MysqlAllTypesNullableDefaultValueWithTimes

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

    fun updateAllTypesNotNullColumn() =
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

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTimeToInsert
}
