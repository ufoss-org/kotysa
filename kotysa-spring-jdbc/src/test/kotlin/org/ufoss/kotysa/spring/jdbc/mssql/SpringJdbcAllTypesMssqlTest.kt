/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.LocalDate
import java.time.LocalDateTime


class SpringJdbcAllTypesMssqlTest : AbstractSpringJdbcMssqlTest<AllTypesRepositoryMssql>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMssql>(resource)
    }

    override val repository: AllTypesRepositoryMssql by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(mssqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(AllTypesNullableDefaultValueEntity(
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
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
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
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            MssqlAllTypesNotNull(allTypesNotNull.id, "new", false, newLocalDate,
                                    newKotlinxLocalDate, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                                    newKotlinxLocalDateTime, newInt, newLong))
        }
    }
}


class AllTypesRepositoryMssql(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mssqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MssqlAllTypesNotNulls
        sqlClient deleteAllFrom MssqlAllTypesNullables
        sqlClient deleteAllFrom MssqlAllTypesNullableDefaultValues
    }

    private fun createTables() {
        sqlClient createTable MssqlAllTypesNotNulls
        sqlClient createTable MssqlAllTypesNullables
        sqlClient createTableIfNotExists MssqlAllTypesNullableDefaultValues
    }

    private fun insertAllTypes() {
        sqlClient.insert(mssqlAllTypesNotNull, allTypesNullable, allTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MssqlAllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MssqlAllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MssqlAllTypesNullableDefaultValues

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalDateTime: LocalDateTime,
                              newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long) =
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
                    where MssqlAllTypesNotNulls.id eq allTypesNotNullWithTime.id
                    ).execute()
}
