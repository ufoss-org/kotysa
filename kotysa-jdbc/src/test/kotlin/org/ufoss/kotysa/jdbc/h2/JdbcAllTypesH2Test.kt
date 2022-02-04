/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*

class JdbcAllTypesH2Test : AbstractJdbcH2Test<AllTypesRepositoryH2>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = AllTypesRepositoryH2(sqlClient)

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(h2AllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(H2AllTypesNullableDefaultValueEntity(
                        1,
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        LocalTime.of(11, 25, 55, 123456789),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        42,
                        84L,
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0,
                                ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)),
                        UUID.fromString(defaultUuid),
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(h2AllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newKotlinxLocalDate = Clock.System.todayAt(TimeZone.UTC)
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newKotlinxLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val newUuid = UUID.randomUUID()
        val newInt = 2
        val newLong = 2L
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newInt, newLong, newOffsetDateTime,
                    newUuid)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            H2AllTypesNotNullEntity(h2AllTypesNotNull.id, "new", false, newLocalDate,
                                    newKotlinxLocalDate, newLocalTime, newLocalDateTime, newLocalDateTime,
                                    newKotlinxLocalDateTime, newKotlinxLocalDateTime, newInt, newLong,
                                    newOffsetDateTime, newUuid))
        }
    }
}


class AllTypesRepositoryH2(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        sqlClient deleteAllFrom H2AllTypesNotNulls
        sqlClient deleteAllFrom H2AllTypesNullables
        sqlClient deleteAllFrom H2AllTypesNullableDefaultValues
    }

    private fun createTables() {
        sqlClient createTable H2AllTypesNotNulls
        sqlClient createTable H2AllTypesNullables
        sqlClient createTableIfNotExists H2AllTypesNullableDefaultValues
    }

    private fun insertAllTypes() {
        sqlClient.insert(h2AllTypesNotNull)
        sqlClient.insert(h2AllTypesNullable)
        sqlClient.insert(h2AllTypesNullableDefaultValue)
    }

    fun selectAllAllTypesNotNull() = sqlClient selectAllFrom H2AllTypesNotNulls

    fun selectAllAllTypesNullable() = sqlClient selectAllFrom H2AllTypesNullables

    fun selectAllAllTypesNullableDefaultValue() = sqlClient selectAllFrom H2AllTypesNullableDefaultValues

    fun updateAllTypesNotNull(
            newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
            newKotlinxLocalDate: kotlinx.datetime.LocalDate, newLocalTime: LocalTime,
            newLocalDateTime: LocalDateTime, newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newInt: Int,
            newLong: Long, newOffsetDateTime: OffsetDateTime, newUuid: UUID
    ) =
            (sqlClient update H2AllTypesNotNulls
                    set H2AllTypesNotNulls.string eq newString
                    set H2AllTypesNotNulls.boolean eq newBoolean
                    set H2AllTypesNotNulls.localDate eq newLocalDate
                    set H2AllTypesNotNulls.kotlinxLocalDate eq newKotlinxLocalDate
                    set H2AllTypesNotNulls.localTim eq newLocalTime
                    set H2AllTypesNotNulls.localDateTime1 eq newLocalDateTime
                    set H2AllTypesNotNulls.localDateTime2 eq newLocalDateTime
                    set H2AllTypesNotNulls.kotlinxLocalDateTime1 eq newKotlinxLocalDateTime
                    set H2AllTypesNotNulls.kotlinxLocalDateTime2 eq newKotlinxLocalDateTime
                    set H2AllTypesNotNulls.int eq newInt
                    set H2AllTypesNotNulls.long eq newLong
                    set H2AllTypesNotNulls.offsetDateTime eq newOffsetDateTime
                    set H2AllTypesNotNulls.uuid eq newUuid
                    where H2AllTypesNotNulls.id eq allTypesNotNullWithTime.id
                    ).execute()
}
