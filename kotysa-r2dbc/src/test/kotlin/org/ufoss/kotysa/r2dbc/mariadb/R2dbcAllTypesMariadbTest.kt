/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

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
import java.time.LocalTime


class R2dbcAllTypesMariadbTest : AbstractR2dbcMariadbTest<AllTypesRepositoryMariadb>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = AllTypesRepositoryMariadb(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() = runTest {
        assertThat(repository.selectAllAllTypesNotNull().toList())
            .hasSize(1)
            .containsExactly(mariadbAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() = runTest {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toList())
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
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() = runTest {
        assertThat(repository.selectAllAllTypesNullable().toList())
            .hasSize(1)
            .containsExactly(allTypesNullableWithTime)
    }

    @Test
    fun `Verify updateAll works`() = runTest {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate,
                newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong
            )
            assertThat(repository.selectAllAllTypesNotNull().toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MariadbAllTypesNotNull(
                        mariadbAllTypesNotNull.id, "new", false, newLocalDate,
                        newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                        newKotlinxLocalDateTime, newInt, newLong, newLocalTime
                    )
                )
        }
    }
}


class AllTypesRepositoryMariadb(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertAllTypes()
    }

    override fun delete() = runBlocking<Unit> {
        sqlClient deleteAllFrom MariadbAllTypesNotNulls
        sqlClient deleteAllFrom MariadbAllTypesNullableWithTimes
        sqlClient deleteAllFrom MariadbAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun createTables() {
        sqlClient createTable MariadbAllTypesNotNulls
        sqlClient createTable MariadbAllTypesNullableWithTimes
        sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValueWithTimes
    }

    private suspend fun insertAllTypes() {
        sqlClient insert mariadbAllTypesNotNull
        sqlClient insert allTypesNullableWithTime
        sqlClient insert allTypesNullableDefaultValueWithTime
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MariadbAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MariadbAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MariadbAllTypesNullableDefaultValueWithTimes

    suspend fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate,
        newLocalTime: LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long
    ) =
        (sqlClient update MariadbAllTypesNotNulls
                set MariadbAllTypesNotNulls.string eq newString
                set MariadbAllTypesNotNulls.boolean eq newBoolean
                set MariadbAllTypesNotNulls.localDate eq newLocalDate
                set MariadbAllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                set MariadbAllTypesNotNulls.localTim eq newLocalTime
                set MariadbAllTypesNotNulls.localDateTime1 eq newLocalDateTime
                set MariadbAllTypesNotNulls.localDateTime2 eq newLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNulls.inte eq newInt
                set MariadbAllTypesNotNulls.longe eq newLong
                where MariadbAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
