/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*
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
                    kotlinx.datetime.LocalDate(2019, 11, 6),
                    LocalDateTime.of(2018, 11, 4, 0, 0),
                    LocalDateTime.of(2019, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                    kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                    42,
                    84L
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
    fun `Verify updateAll works`() = runTest {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MssqlAllTypesNotNull(
                        allTypesNotNull.id, "new", false, newLocalDate,
                        newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong
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
        sqlClient deleteAllFrom MSSQL_ALL_TYPES_NOT_NULL
        sqlClient deleteAllFrom MSSQL_ALL_TYPES_NULLABLE
        sqlClient deleteAllFrom MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private suspend fun createTables() {
        sqlClient createTable MSSQL_ALL_TYPES_NOT_NULL
        sqlClient createTable MSSQL_ALL_TYPES_NULLABLE
        sqlClient createTableIfNotExists MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE
    }

    private suspend fun insertAllTypes() {
        sqlClient insert mssqlAllTypesNotNull
        sqlClient insert allTypesNullable
        sqlClient insert allTypesNullableDefaultValue
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MSSQL_ALL_TYPES_NOT_NULL

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MSSQL_ALL_TYPES_NULLABLE

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MSSQL_ALL_TYPES_NULLABLE_DEFAULT_VALUE

    suspend fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long
    ) =
        (sqlClient update MSSQL_ALL_TYPES_NOT_NULL
                set MSSQL_ALL_TYPES_NOT_NULL.string eq newString
                set MSSQL_ALL_TYPES_NOT_NULL.boolean eq newBoolean
                set MSSQL_ALL_TYPES_NOT_NULL.localDate eq newLocalDate
                set MSSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDate eq newKotlinxLocalDate
                set MSSQL_ALL_TYPES_NOT_NULL.localDateTime1 eq newLocalDateTime
                set MSSQL_ALL_TYPES_NOT_NULL.localDateTime2 eq newLocalDateTime
                set MSSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MSSQL_ALL_TYPES_NOT_NULL.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MSSQL_ALL_TYPES_NOT_NULL.inte eq newInt
                set MSSQL_ALL_TYPES_NOT_NULL.longe eq newLong
                where MSSQL_ALL_TYPES_NOT_NULL.id eq allTypesNotNullWithTime.id
                ).execute()
}
