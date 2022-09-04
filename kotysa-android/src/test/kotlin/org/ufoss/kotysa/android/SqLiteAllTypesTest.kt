/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SqLiteAllTypesTest : AbstractSqLiteTest<AllTypesRepository>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = AllTypesRepository(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
            .hasSize(1)
            .containsExactly(allTypesNotNullWithTime)
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
            .hasSize(1)
            .containsExactly(allTypesNullableWithTime)
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
                    LocalTime.of(11, 25, 55),
                )
            )
    }

    @Test
    fun `Verify updateAllTypesNotNullValue works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayIn(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newByteArray = byteArrayOf(0x2B)
        val operator = client.transactionalOp()
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullValue(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    AllTypesNotNullWithTimeEntity(
                        allTypesNotNullWithTime.id, "new", false, newLocalDate,
                        newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong, newByteArray, newLocalTime
                    )
                )
        }
    }

    @Test
    fun `Verify updateAllTypesNotNullColumn works`() {
        val operator = client.transactionalOp()
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullColumn()
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(allTypesNotNullWithTime)
        }
    }
}

class AllTypesRepository(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) : Repository {

    private val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom SqliteAllTypesNotNullWithTimes
        sqlClient deleteAllFrom SqliteAllTypesNullableWithTimes
        sqlClient deleteAllFrom SqliteAllTypesNullableDefaultValueWithTimes
    }

    private fun createTables() {
        sqlClient createTable SqliteAllTypesNotNullWithTimes
        sqlClient createTable SqliteAllTypesNullableWithTimes
        sqlClient createTableIfNotExists SqliteAllTypesNullableDefaultValueWithTimes
    }

    private fun insertAllTypes() {
        sqlClient.insert(allTypesNotNullWithTime)
        sqlClient.insert(allTypesNullableWithTime)
        sqlClient.insert(allTypesNullableDefaultValueWithTime)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom SqliteAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom SqliteAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom SqliteAllTypesNullableDefaultValueWithTimes

    fun updateAllTypesNotNullValue(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update SqliteAllTypesNotNullWithTimes
                set SqliteAllTypesNotNullWithTimes.string eq newString
                set SqliteAllTypesNotNullWithTimes.boolean eq newBoolean
                set SqliteAllTypesNotNullWithTimes.localDate eq newLocalDate
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDate eq newKotlinxLocalDate
                set SqliteAllTypesNotNullWithTimes.localTime eq newLocalTime
                set SqliteAllTypesNotNullWithTimes.localDateTime1 eq newLocalDateTime
                set SqliteAllTypesNotNullWithTimes.localDateTime2 eq newLocalDateTime
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set SqliteAllTypesNotNullWithTimes.int eq newInt
                set SqliteAllTypesNotNullWithTimes.long eq newLong
                set SqliteAllTypesNotNullWithTimes.byteArray eq newByteArray
                where SqliteAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update SqliteAllTypesNotNullWithTimes
                set SqliteAllTypesNotNullWithTimes.string eq SqliteAllTypesNotNullWithTimes.string
                set SqliteAllTypesNotNullWithTimes.boolean eq SqliteAllTypesNotNullWithTimes.boolean
                set SqliteAllTypesNotNullWithTimes.localDate eq SqliteAllTypesNotNullWithTimes.localDate
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDate eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDate
                set SqliteAllTypesNotNullWithTimes.localTime eq SqliteAllTypesNotNullWithTimes.localTime
                set SqliteAllTypesNotNullWithTimes.localDateTime1 eq SqliteAllTypesNotNullWithTimes.localDateTime1
                set SqliteAllTypesNotNullWithTimes.localDateTime2 eq SqliteAllTypesNotNullWithTimes.localDateTime2
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2
                set SqliteAllTypesNotNullWithTimes.int eq SqliteAllTypesNotNullWithTimes.int
                set SqliteAllTypesNotNullWithTimes.long eq SqliteAllTypesNotNullWithTimes.long
                set SqliteAllTypesNotNullWithTimes.byteArray eq SqliteAllTypesNotNullWithTimes.byteArray
                where SqliteAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()
}
