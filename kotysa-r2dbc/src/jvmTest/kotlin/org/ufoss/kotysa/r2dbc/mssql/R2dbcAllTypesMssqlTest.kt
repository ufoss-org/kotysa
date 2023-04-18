/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class R2dbcAllTypesMssqlTest : AbstractR2dbcMssqlTest<AllTypesRepositoryMssql>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = AllTypesRepositoryMssql(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNull().toList())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() = runTest {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toList())
            .hasSize(1)
            .containsExactly(
                AllTypesNullableDefaultValueEntity(
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
                    MssqlAllTypesNotNull(
                        allTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
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
                .containsExactlyInAnyOrder(mssqlAllTypesNotNull)
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
}


class AllTypesRepositoryMssql(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertAllTypes()
    }

    override fun delete() = runBlocking<Unit> {
        sqlClient deleteAllFrom MssqlAllTypesNotNulls
        sqlClient deleteAllFrom MssqlAllTypesNullables
        sqlClient deleteAllFrom MssqlAllTypesNullableDefaultValues
    }

    private suspend fun createTables() {
        sqlClient createTable MssqlAllTypesNotNulls
        sqlClient createTable MssqlAllTypesNullables
        sqlClient createTableIfNotExists MssqlAllTypesNullableDefaultValues
    }

    private suspend fun insertAllTypes() {
        sqlClient insert mssqlAllTypesNotNull
        sqlClient insert allTypesNullable
        sqlClient insert allTypesNullableDefaultValue
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MssqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MssqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MssqlAllTypesNullableDefaultValues

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
        (sqlClient update MssqlAllTypesNotNulls
                set MssqlAllTypesNotNulls.string eq newString
                set MssqlAllTypesNotNulls.boolean eq newBoolean
                set MssqlAllTypesNotNulls.localDate eq newLocalDate
                set MssqlAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MssqlAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MssqlAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MssqlAllTypesNotNulls.inte eq newInt
                set MssqlAllTypesNotNulls.longe eq newLong
                set MssqlAllTypesNotNulls.byteArray eq newByteArray
                set MssqlAllTypesNotNulls.float eq newFloat
                set MssqlAllTypesNotNulls.doublee eq newDouble
                set MssqlAllTypesNotNulls.bigDecimal1 eq newBigDecimal
                set MssqlAllTypesNotNulls.bigDecimal2 eq newBigDecimal
                where MssqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun updateAllTypesNotNullColumn() =
        (sqlClient update MssqlAllTypesNotNulls
                set MssqlAllTypesNotNulls.string eq MssqlAllTypesNotNulls.string
                set MssqlAllTypesNotNulls.boolean eq MssqlAllTypesNotNulls.boolean
                set MssqlAllTypesNotNulls.localDate eq MssqlAllTypesNotNulls.localDate
                set MssqlAllTypesNotNulls.kotlinxLocalDate eq MssqlAllTypesNotNulls.kotlinxLocalDate
                set MssqlAllTypesNotNulls.localDateTime1 eq MssqlAllTypesNotNulls.localDateTime1
                set MssqlAllTypesNotNulls.localDateTime2 eq MssqlAllTypesNotNulls.localDateTime2
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime1 eq MssqlAllTypesNotNulls.kotlinxLocalDateTime1
                set MssqlAllTypesNotNulls.kotlinxLocalDateTime2 eq MssqlAllTypesNotNulls.kotlinxLocalDateTime2
                set MssqlAllTypesNotNulls.inte eq MssqlAllTypesNotNulls.inte
                set MssqlAllTypesNotNulls.longe eq MssqlAllTypesNotNulls.longe
                set MssqlAllTypesNotNulls.byteArray eq MssqlAllTypesNotNulls.byteArray
                set MssqlAllTypesNotNulls.float eq MssqlAllTypesNotNulls.float
                set MssqlAllTypesNotNulls.doublee eq MssqlAllTypesNotNulls.doublee
                set MssqlAllTypesNotNulls.bigDecimal1 eq MssqlAllTypesNotNulls.bigDecimal1
                set MssqlAllTypesNotNulls.bigDecimal2 eq MssqlAllTypesNotNulls.bigDecimal2
                where MssqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()

    suspend fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueToInsert
}