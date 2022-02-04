/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SqLiteAllTypesTest : AbstractSqLiteTest<AllTypesRepository>() {

    override fun getRepository(sqLiteTables: Tables) = AllTypesRepository(dbHelper, sqLiteTables)

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
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val operator = client.transactionalOp()
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                    "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime,
                    newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong
            )
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            AllTypesNotNullWithTimeEntity(
                                    allTypesNotNullWithTime.id, "new", false, newLocalDate,
                                    newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                                    newKotlinxLocalDateTime, newInt, newLong, newLocalTime
                            )
                    )
        }
    }
}

class AllTypesRepository(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : Repository {

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

    fun updateAllTypesNotNull(
            newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
            newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
            newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long
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
                    where SqliteAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                    ).execute()
}
