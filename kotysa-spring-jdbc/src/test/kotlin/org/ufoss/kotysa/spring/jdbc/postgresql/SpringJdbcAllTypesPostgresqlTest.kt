/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*


class SpringJdbcAllTypesPostgresqlTest : AbstractSpringJdbcPostgresqlTest<AllTypesRepositoryPostgresql>() {
    override val context = startContext<AllTypesRepositoryPostgresql>()

    override val repository = getContextRepository<AllTypesRepositoryPostgresql>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue())
                .hasSize(1)
                .containsExactly(PostgresqlAllTypesNullableDefaultValue(
                        "default",
                        LocalDate.of(2019, 11, 4),
                        kotlinx.datetime.LocalDate(2019, 11, 6),
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                        LocalTime.of(11, 25, 55),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0),
                        UUID.fromString(defaultUuid),
                        42,
                        postgresqlAllTypesNullableDefaultValue.id
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNullable)
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
                    newOffsetDateTime, newLocalTime, newLocalDateTime, newKotlinxLocalDateTime, newUuid, newInt)
            assertThat(repository.selectAllAllTypesNotNull())
                    .hasSize(1)
                    .containsExactlyInAnyOrder(
                            PostgresqlAllTypesNotNull(postgresqlAllTypesNotNull.id, "new", false,
                                    newLocalDate, newKotlinxLocalDate, newOffsetDateTime, newLocalTime,
                                    newLocalDateTime, newKotlinxLocalDateTime, newUuid, newInt))
        }
    }
}


class AllTypesRepositoryPostgresql(client: JdbcTemplate) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertAllTypes()
    }

    override fun delete() {
        deleteAllFromAllTypesNotNull()
        deleteAllFromAllTypesNullable()
    }

    private fun createTables() {
        sqlClient.createTable<PostgresqlAllTypesNotNull>()
        sqlClient.createTable<PostgresqlAllTypesNullable>()
        sqlClient.createTable<PostgresqlAllTypesNullableDefaultValue>()
    }

    private fun insertAllTypes() = sqlClient.insert(postgresqlAllTypesNotNull, postgresqlAllTypesNullable, postgresqlAllTypesNullableDefaultValue)

    private fun deleteAllFromAllTypesNotNull() = sqlClient.deleteAllFromTable<PostgresqlAllTypesNotNull>()

    private fun deleteAllFromAllTypesNullable() = sqlClient.deleteAllFromTable<PostgresqlAllTypesNullable>()

    fun selectAllAllTypesNotNull() = sqlClient.selectAll<PostgresqlAllTypesNotNull>()

    fun selectAllAllTypesNullable() = sqlClient.selectAll<PostgresqlAllTypesNullable>()

    fun selectAllAllTypesNullableDefaultValue() = sqlClient.selectAll<PostgresqlAllTypesNullableDefaultValue>()

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newKotlinxLocalDate: kotlinx.datetime.LocalDate, newOffsetDateTime: OffsetDateTime,
                              newLocalTim: LocalTime, newLocalDateTime: LocalDateTime,
                              newKotlinxLocalDateTime: kotlinx.datetime.LocalDateTime, newUuid: UUID, newInt: Int) =
            sqlClient.updateTable<PostgresqlAllTypesNotNull>()
                    .set { it[PostgresqlAllTypesNotNull::string] = newString }
                    .set { it[PostgresqlAllTypesNotNull::boolean] = newBoolean }
                    .set { it[PostgresqlAllTypesNotNull::localDate] = newLocalDate }
                    .set { it[PostgresqlAllTypesNotNull::kotlinxLocalDate] = newKotlinxLocalDate }
                    .set { it[PostgresqlAllTypesNotNull::localDate] = newLocalDate }
                    .set { it[PostgresqlAllTypesNotNull::offsetDateTime] = newOffsetDateTime }
                    .set { it[PostgresqlAllTypesNotNull::localTim] = newLocalTim }
                    .set { it[PostgresqlAllTypesNotNull::localDateTime] = newLocalDateTime }
                    .set { it[PostgresqlAllTypesNotNull::kotlinxLocalDateTime] = newKotlinxLocalDateTime }
                    .set { it[PostgresqlAllTypesNotNull::uuid] = newUuid }
                    .set { it[PostgresqlAllTypesNotNull::int] = newInt }
                    .where { it[PostgresqlAllTypesNotNull::id] eq postgresqlAllTypesNotNull.id }
                    .execute()
}
