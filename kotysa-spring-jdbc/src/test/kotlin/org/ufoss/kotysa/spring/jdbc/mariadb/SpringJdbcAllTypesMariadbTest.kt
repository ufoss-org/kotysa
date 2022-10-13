/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import kotlinx.datetime.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SpringJdbcAllTypesMariadbTest : AbstractSpringJdbcMariadbTest<AllTypesRepositoryMariadb>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<AllTypesRepositoryMariadb>(resource)
    }

    override val repository: AllTypesRepositoryMariadb by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
            .hasSize(1)
            .containsExactly(mariadbAllTypesNotNullWithTime)
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
        val newByteArray = byteArrayOf(0x2B)
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull(
                "new", false, newLocalDate, newKotlinxLocalDate, newLocalTime, newKotlinxLocalTime,
                newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newByteArray
            )
            assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                    MariadbAllTypesNotNullWithTime(
                        mariadbAllTypesNotNullWithTime.id, "new", false, newLocalDate, newKotlinxLocalDate,
                        newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt,
                        newLong, newByteArray, newLocalTime, newKotlinxLocalTime
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
                .containsExactlyInAnyOrder(mariadbAllTypesNotNullWithTime)
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
                        LocalTime.of(11, 25, 55),
                        LocalTime(11, 25, 55),
                    )
                )
        }
    }
}


class AllTypesRepositoryMariadb(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mariadbTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom MariadbAllTypesNotNullWithTimes
        sqlClient deleteAllFrom MariadbAllTypesNullableWithTimes
        sqlClient deleteAllFrom MariadbAllTypesNullableDefaultValueWithTimes
    }

    private fun createTables() {
        sqlClient createTable MariadbAllTypesNotNullWithTimes
        sqlClient createTable MariadbAllTypesNullableWithTimes
        sqlClient createTableIfNotExists MariadbAllTypesNullableDefaultValueWithTimes
    }

    private fun insertAllTypes() {
        sqlClient.insert(mariadbAllTypesNotNullWithTime, allTypesNullableWithTime, allTypesNullableDefaultValueWithTime)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom MariadbAllTypesNotNullWithTimes

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom MariadbAllTypesNullableWithTimes

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom MariadbAllTypesNullableDefaultValueWithTimes

    fun updateAllTypesNotNull(
        newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
        newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
        newKotlinxLocalTime: kotlinx.datetime.LocalTime, newLocalDateTime: LocalDateTime,
        newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int, newLong: Long, newByteArray: ByteArray
    ) =
        (sqlClient update MariadbAllTypesNotNullWithTimes
                set MariadbAllTypesNotNullWithTimes.string eq newString
                set MariadbAllTypesNotNullWithTimes.boolean eq newBoolean
                set MariadbAllTypesNotNullWithTimes.localDate eq newLocalDate
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDate eq newKotlinxLocalDate
                set MariadbAllTypesNotNullWithTimes.localTim eq newLocalTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalTim eq newKotlinxLocalTime
                set MariadbAllTypesNotNullWithTimes.localDateTime1 eq newLocalDateTime
                set MariadbAllTypesNotNullWithTimes.localDateTime2 eq newLocalDateTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                set MariadbAllTypesNotNullWithTimes.inte eq newInt
                set MariadbAllTypesNotNullWithTimes.longe eq newLong
                set MariadbAllTypesNotNullWithTimes.byteArray eq newByteArray
                where MariadbAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    fun updateAllTypesNotNullColumn() =
        (sqlClient update MariadbAllTypesNotNullWithTimes
                set MariadbAllTypesNotNullWithTimes.string eq MariadbAllTypesNotNullWithTimes.string
                set MariadbAllTypesNotNullWithTimes.boolean eq MariadbAllTypesNotNullWithTimes.boolean
                set MariadbAllTypesNotNullWithTimes.localDate eq MariadbAllTypesNotNullWithTimes.localDate
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDate eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDate
                set MariadbAllTypesNotNullWithTimes.localTim eq MariadbAllTypesNotNullWithTimes.localTim
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalTim eq MariadbAllTypesNotNullWithTimes.kotlinxLocalTim
                set MariadbAllTypesNotNullWithTimes.localDateTime1 eq MariadbAllTypesNotNullWithTimes.localDateTime1
                set MariadbAllTypesNotNullWithTimes.localDateTime2 eq MariadbAllTypesNotNullWithTimes.localDateTime2
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1 eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime1
                set MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2 eq MariadbAllTypesNotNullWithTimes.kotlinxLocalDateTime2
                set MariadbAllTypesNotNullWithTimes.inte eq MariadbAllTypesNotNullWithTimes.inte
                set MariadbAllTypesNotNullWithTimes.longe eq MariadbAllTypesNotNullWithTimes.longe
                set MariadbAllTypesNotNullWithTimes.byteArray eq MariadbAllTypesNotNullWithTimes.byteArray
                where MariadbAllTypesNotNullWithTimes.id eq allTypesNotNullWithTime.id
                ).execute()

    fun insertAndReturnAllTypesDefaultValues() = sqlClient insertAndReturn allTypesNullableDefaultValueWithTimeToInsert
}
