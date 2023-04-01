/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import ch.tutteli.atrium.api.fluent.en_GB.toContain
import ch.tutteli.atrium.api.fluent.en_GB.toContainExactly
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.fluent.en_GB.toHaveSize
import ch.tutteli.atrium.api.verbs.expect
import kotlinx.datetime.*
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
        expect(repository.selectAllAllTypesNotNull())
            .toHaveSize(1)
            .toContainExactly(sqliteAllTypesNotNullWithTime)
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        expect(repository.selectAllAllTypesNullable())
            .toHaveSize(1)
            .toContainExactly(sqliteAllTypesNullableWithTime)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        expect(repository.selectAllAllTypesNullableDefaultValue())
            .toHaveSize(1)
            .toContainExactly(
                SqliteAllTypesNullableDefaultValueWithTimeEntity(
                    sqliteAllTypesNullableDefaultValueWithTime.id,
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
                    LocalTime.of(11, 25, 55),
                    LocalTime(11, 25, 55),
                )
            )
    }

    @Test
    fun `Verify updateAllTypesNotNullValue works`() {
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
        val operator = client.transactionalOp()
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNullValue(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray, newFloat, newDouble
            )
            expect(repository.selectAllAllTypesNotNull())
                .toHaveSize(1)
                .toContain(
                    SqliteAllTypesNotNullWithTimeEntity(
                        sqliteAllTypesNotNullWithTime.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                        newLong, newByteArray, newFloat, newDouble, newLocalTime, newKotlinxLocalTime
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
            expect(repository.selectAllAllTypesNotNull())
                .toHaveSize(1)
                .toContain(sqliteAllTypesNotNullWithTime)
        }
    }

    @Test
    fun `Verify insertAndReturnAllTypesDefaultValues works correctly`() {
        val operator = client.transactionalOp()
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            expect(repository.insertAndReturnAllTypesDefaultValues())
                .toEqual(
                    SqliteAllTypesNullableDefaultValueWithTimeEntity(
                        sqliteAllTypesNullableDefaultValueWithTimeToInsert.id,
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
                        LocalTime.of(11, 25, 55),
                        LocalTime(11, 25, 55),
                    )
                )
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
        sqlClient.insert(sqliteAllTypesNotNullWithTime)
        sqlClient.insert(sqliteAllTypesNullableWithTime)
        sqlClient.insert(sqliteAllTypesNullableDefaultValueWithTime)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom SqliteAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom SqliteAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom SqliteAllTypesNullableDefaultValueWithTimes

    fun updateAllTypesNotNullValue(
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
    ) =
        (sqlClient update SqliteAllTypesNotNullWithTimes
                set SqliteAllTypesNotNullWithTimes.string eq newString
                set SqliteAllTypesNotNullWithTimes.boolean eq newBoolean
                set SqliteAllTypesNotNullWithTimes.localDate eq newLocalDate
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDate eq newKotlinxLocalDate
                set SqliteAllTypesNotNullWithTimes.localTim eq newLocalTime
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalTim eq newKotlinxLocalTime
                set SqliteAllTypesNotNullWithTimes.localDateTime1 eq newLocalDateTime
                set SqliteAllTypesNotNullWithTimes.localDateTime2 eq newLocalDateTime
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set SqliteAllTypesNotNullWithTimes.inte eq newInt
                set SqliteAllTypesNotNullWithTimes.longe eq newLong
                set SqliteAllTypesNotNullWithTimes.byteArray eq newByteArray
                set SqliteAllTypesNotNullWithTimes.float eq newFloat
                set SqliteAllTypesNotNullWithTimes.double eq newDouble
                where SqliteAllTypesNotNullWithTimes.id eq sqliteAllTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update SqliteAllTypesNotNullWithTimes
                set SqliteAllTypesNotNullWithTimes.string eq SqliteAllTypesNotNullWithTimes.string
                set SqliteAllTypesNotNullWithTimes.boolean eq SqliteAllTypesNotNullWithTimes.boolean
                set SqliteAllTypesNotNullWithTimes.localDate eq SqliteAllTypesNotNullWithTimes.localDate
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDate eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDate
                set SqliteAllTypesNotNullWithTimes.localTim eq SqliteAllTypesNotNullWithTimes.localTim
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalTim eq SqliteAllTypesNotNullWithTimes.kotlinxLocalTim
                set SqliteAllTypesNotNullWithTimes.localDateTime1 eq SqliteAllTypesNotNullWithTimes.localDateTime1
                set SqliteAllTypesNotNullWithTimes.localDateTime2 eq SqliteAllTypesNotNullWithTimes.localDateTime2
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime1
                set SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq SqliteAllTypesNotNullWithTimes.kotlinxLocalDateTime2
                set SqliteAllTypesNotNullWithTimes.inte eq SqliteAllTypesNotNullWithTimes.inte
                set SqliteAllTypesNotNullWithTimes.longe eq SqliteAllTypesNotNullWithTimes.longe
                set SqliteAllTypesNotNullWithTimes.byteArray eq SqliteAllTypesNotNullWithTimes.byteArray
                set SqliteAllTypesNotNullWithTimes.float eq SqliteAllTypesNotNullWithTimes.float
                set SqliteAllTypesNotNullWithTimes.double eq SqliteAllTypesNotNullWithTimes.double
                where SqliteAllTypesNotNullWithTimes.id eq sqliteAllTypesNotNullWithTime.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn sqliteAllTypesNullableDefaultValueWithTimeToInsert
}
