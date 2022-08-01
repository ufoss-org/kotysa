/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test
import java.time.LocalDate
import java.time.LocalDateTime


class R2dbcAllTypesMssqlTest : AbstractR2dbcMssqlTest<AllTypesRepositoryMssql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMssql>(resource)
    }

    override val repository: AllTypesRepositoryMssql by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
            .hasSize(1)
            .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
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
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
            .hasSize(1)
            .containsExactly(allTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newInt = 2
        val newLong = 2L
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
            .expectNext(
                MssqlAllTypesNotNull(
                    allTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate, newLocalDateTime,
                    newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray
                )
            )
            .verifyComplete()
    }
}


class AllTypesRepositoryMssql(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mssqlTables)

    override fun init() {
        createTables()
            .then(insertAllTypes().then())
            .block()
    }

    override fun delete() {
        (sqlClient deleteAllFrom MssqlAllTypesNotNulls)
            .then(sqlClient deleteAllFrom MssqlAllTypesNullables)
            .then(sqlClient deleteAllFrom MssqlAllTypesNullableDefaultValues)
            .block()
    }

    private fun createTables() =
        (sqlClient createTable MssqlAllTypesNotNulls)
            .then(sqlClient createTable MssqlAllTypesNullables)
            .then(sqlClient createTableIfNotExists MssqlAllTypesNullableDefaultValues)

    private fun insertAllTypes() =
        sqlClient.insert(mssqlAllTypesNotNull, allTypesNullable, allTypesNullableDefaultValue)

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MssqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MssqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MssqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
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
                where MssqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                ).execute()
}
