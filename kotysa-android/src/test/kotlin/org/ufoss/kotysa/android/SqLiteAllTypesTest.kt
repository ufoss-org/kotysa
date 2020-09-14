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
import java.time.OffsetDateTime

class SqLiteAllTypesTest : AbstractSqLiteTest<AllTypesRepository>() {

    override fun getRepository(sqLiteTables: Tables) = AllTypesRepository(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
            .hasSize(1)
            .containsExactly(sqLiteAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
            .hasSize(1)
            .containsExactly(
                SqLiteAllTypesNullableDefaultValue(
                    sqLiteAllTypesNullableDefaultValue.id,
                    "default",
                    LocalDate.MAX,
                    kotlinx.datetime.LocalDate(2019, 11, 6),
                    OffsetDateTime.MAX,
                    LocalDateTime.MAX,
                    kotlinx.datetime.LocalDateTime(2019, 11, 6, 0, 0),
                    LocalTime.MAX,
                    42
                )
            )
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
            .hasSize(1)
            .containsExactly(sqLiteAllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val operator = client.transactionalOp()
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate,
                newOffsetDateTime, newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt
            )
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    SqLiteAllTypesNotNull(
                        sqLiteAllTypesNotNull.id, "new", false, newLocalDate,
                        newKotlinxLocalDate, newOffsetDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newLocalTime, newInt
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
        deleteAll()
    }

    private fun createTables() {
        sqlClient.createTable<SqLiteAllTypesNotNull>()
        sqlClient.createTable<SqLiteAllTypesNullable>()
        sqlClient.createTable<SqLiteAllTypesNullableDefaultValue>()
    }

    private fun insertAllTypes() {
        sqlClient.insert(sqLiteAllTypesNotNull, sqLiteAllTypesNullable, sqLiteAllTypesNullableDefaultValue)
    }

    private fun deleteAll() {
        sqlClient.deleteAllFromTable<SqLiteAllTypesNotNull>()
        sqlClient.deleteAllFromTable<SqLiteAllTypesNullable>()
        sqlClient.deleteAllFromTable<SqLiteAllTypesNullableDefaultValue>()
    }

    fun selectAllAllTypesNotNull() = sqlClient.selectAll<SqLiteAllTypesNotNull>()

    fun selectAllAllTypesNullable() = sqlClient.selectAll<SqLiteAllTypesNullable>()

    fun selectAllAllTypesNullableDefaultValue() = sqlClient.selectAll<SqLiteAllTypesNullableDefaultValue>()

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newOffsetDateTime: OffsetDateTime, newLocalTime: LocalTime,
        newLocalDateTime: LocalDateTime, newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int
    ) =
        sqlClient.updateTable<SqLiteAllTypesNotNull>()
            .set { it[SqLiteAllTypesNotNull::string] = newString }
            .set { it[SqLiteAllTypesNotNull::boolean] = newBoolean }
            .set { it[SqLiteAllTypesNotNull::localDate] = newLocalDate }
            .set { it[SqLiteAllTypesNotNull::kotlinxLocalDate] = newKotlinxLocalDate }
            .set { it[SqLiteAllTypesNotNull::offsetDateTime] = newOffsetDateTime }
            .set { it[SqLiteAllTypesNotNull::localTime] = newLocalTime }
            .set { it[SqLiteAllTypesNotNull::localDateTime] = newLocalDateTime }
            .set { it[SqLiteAllTypesNotNull::kotlinxLocalDateTime] = newKotlinxLocalDateTime }
            .set { it[SqLiteAllTypesNotNull::int] = newInt }
            .where { it[SqLiteAllTypesNotNull::id] eq sqLiteAllTypesNotNull.id }
            .execute()
}
