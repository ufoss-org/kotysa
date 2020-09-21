/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*


class SpringJdbcAllTypesH2Test : AbstractSpringJdbcH2Test<AllTypesRepositoryH2>() {
    override val context = startContext<AllTypesRepositoryH2>()

    override val repository = getContextRepository<AllTypesRepositoryH2>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

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
                .containsExactly(H2AllTypesNullableDefaultValue(
                        "default",
                        LocalDate.MAX,
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                        LocalTime.MAX,
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        LocalDateTime.of(2019, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0),
                        UUID.fromString(defaultUuid),
                        42,
                        h2AllTypesNullableDefaultValue.id
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
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newKotlinxLocalDate,
                    newOffsetDateTime, newLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                    newKotlinxLocalDateTime, newUuid, newInt)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            H2AllTypesNotNull(h2AllTypesNotNull.id, "new", false, newLocalDate, newKotlinxLocalDate,
                                    newOffsetDateTime, newLocalTime, newLocalDateTime, newLocalDateTime, newKotlinxLocalDateTime,
                                    newKotlinxLocalDateTime, newUuid, newInt))
        }
    }
}


class AllTypesRepositoryH2(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        deleteAllFromAllTypesNotNull()
        deleteAllFromAllTypesNullable()
    }

    private fun createTables() {
        sqlClient.createTable<H2AllTypesNotNull>()
        sqlClient.createTable<H2AllTypesNullable>()
        sqlClient.createTable<H2AllTypesNullableDefaultValue>()
    }

    private fun insertAllTypes() = sqlClient.insert(h2AllTypesNotNull, h2AllTypesNullable, h2AllTypesNullableDefaultValue)

    private fun deleteAllFromAllTypesNotNull() = sqlClient.deleteAllFromTable<H2AllTypesNotNull>()

    private fun deleteAllFromAllTypesNullable() = sqlClient.deleteAllFromTable<H2AllTypesNullable>()

    fun selectAllAllTypesNotNull() = sqlClient.selectAll<H2AllTypesNotNull>()

    fun selectAllAllTypesNullable() = sqlClient.selectAll<H2AllTypesNullable>()

    fun selectAllAllTypesNullableDefaultValue() = sqlClient.selectAll<H2AllTypesNullableDefaultValue>()

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate, newKotlinxLocalDate: kotlinx.datetime.LocalDate,
                              newOffsetDateTime: OffsetDateTime, newLocalTim: LocalTime, newLocalDateTime1: LocalDateTime,
                              newLocalDateTime2: LocalDateTime, newKotlinxLocalDateTime1: kotlinx.datetime.LocalDateTime,
                              newKotlinxLocalDateTime2: kotlinx.datetime.LocalDateTime, newUuid: UUID, newInt: Int) =
            sqlClient.updateTable<H2AllTypesNotNull>()
                    .set { it[H2AllTypesNotNull::string] = newString }
                    .set { it[H2AllTypesNotNull::boolean] = newBoolean }
                    .set { it[H2AllTypesNotNull::localDate] = newLocalDate }
                    .set { it[H2AllTypesNotNull::kotlinxLocalDate] = newKotlinxLocalDate }
                    .set { it[H2AllTypesNotNull::offsetDateTime] = newOffsetDateTime }
                    .set { it[H2AllTypesNotNull::localTim] = newLocalTim }
                    .set { it[H2AllTypesNotNull::localDateTime1] = newLocalDateTime1 }
                    .set { it[H2AllTypesNotNull::localDateTime2] = newLocalDateTime2 }
                    .set { it[H2AllTypesNotNull::kotlinxLocalDateTime1] = newKotlinxLocalDateTime1 }
                    .set { it[H2AllTypesNotNull::kotlinxLocalDateTime2] = newKotlinxLocalDateTime2 }
                    .set { it[H2AllTypesNotNull::uuid] = newUuid }
                    .set { it[H2AllTypesNotNull::int] = newInt }
                    .where { it[H2AllTypesNotNull::id] eq h2AllTypesNotNull.id }
                    .execute()
}
