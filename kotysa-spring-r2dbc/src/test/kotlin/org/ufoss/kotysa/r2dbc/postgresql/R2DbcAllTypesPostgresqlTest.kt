/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test
import java.time.*
import java.util.*


class R2DbcAllTypesPostgresqlTest : AbstractR2dbcPostgresqlTest<AllTypesRepositoryPostgresql>() {
    override val context = startContext<AllTypesRepositoryPostgresql>()

    override val repository = getContextRepository<AllTypesRepositoryPostgresql>()
    private val operator = context.getBean<TransactionalOperator>().transactionalOp()

    @Test
    fun `Verify selectAllAllTypesNotNull returns all AllTypesNotNull`() {
        assertThat(repository.selectAllAllTypesNotNull().toIterable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNotNull)
    }

    @Test
    fun `Verify selectAllAllTypesNullableDefaultValue returns all AllTypesNullableDefaultValue`() {
        assertThat(repository.selectAllAllTypesNullableDefaultValue().toIterable())
                .hasSize(1)
                .containsExactly(PostgresqlAllTypesNullableDefaultValue(
                        "default",
                        LocalDate.of(2019, 11, 4),
                        OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                        LocalTime.of(11, 25, 55),
                        LocalDateTime.of(2018, 11, 4, 0, 0),
                        UUID.fromString(defaultUuid),
                        42,
                        postgresqlAllTypesNullableDefaultValue.id
                ))
    }

    @Test
    fun `Verify selectAllAllTypesNullable returns all AllTypesNullable`() {
        assertThat(repository.selectAllAllTypesNullable().toIterable())
                .hasSize(1)
                .containsExactly(postgresqlAllTypesNullable)
    }

    @Test
    fun `Verify updateAll works`() {
        val newLocalDate = LocalDate.now()
        val newOffsetDateTime = OffsetDateTime.now()
        val newLocalTime = LocalTime.now()
        val newLocalDateTime = LocalDateTime.now()
        val newUuid = UUID.randomUUID()
        val newInt = 2
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.updateAllTypesNotNull("new", false, newLocalDate, newOffsetDateTime, newLocalTime,
                    newLocalDateTime, newUuid, newInt)
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAllAllTypesNotNull())
        }.test()
                .expectNext(PostgresqlAllTypesNotNull(postgresqlAllTypesNotNull.id, "new", false, newLocalDate, newOffsetDateTime,
                        newLocalTime, newLocalDateTime, newUuid, newInt))
                .verifyComplete()
    }
}


class AllTypesRepositoryPostgresql(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
                .then(insertAllTypes())
                .block()
    }

    override fun delete() {
        deleteAllFromAllTypesNotNull()
                .then(deleteAllFromAllTypesNullable())
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlAllTypesNotNull>()
                    .then(sqlClient.createTable<PostgresqlAllTypesNullable>())
                    .then(sqlClient.createTable<PostgresqlAllTypesNullableDefaultValue>())

    private fun insertAllTypes() = sqlClient.insert(postgresqlAllTypesNotNull, postgresqlAllTypesNullable, postgresqlAllTypesNullableDefaultValue)

    private fun deleteAllFromAllTypesNotNull() = sqlClient.deleteAllFromTable<PostgresqlAllTypesNotNull>()

    private fun deleteAllFromAllTypesNullable() = sqlClient.deleteAllFromTable<PostgresqlAllTypesNullable>()

    fun selectAllAllTypesNotNull() = sqlClient.selectAll<PostgresqlAllTypesNotNull>()

    fun selectAllAllTypesNullable() = sqlClient.selectAll<PostgresqlAllTypesNullable>()

    fun selectAllAllTypesNullableDefaultValue() = sqlClient.selectAll<PostgresqlAllTypesNullableDefaultValue>()

    fun updateAllTypesNotNull(newString: String, newBoolean: Boolean, newLocalDate: LocalDate,
                              newOffsetDateTime: OffsetDateTime, newLocalTim: LocalTime, newLocalDateTime: LocalDateTime,
                              newUuid: UUID, newInt: Int) =
            sqlClient.updateTable<PostgresqlAllTypesNotNull>()
                    .set { it[PostgresqlAllTypesNotNull::string] = newString }
                    .set { it[PostgresqlAllTypesNotNull::boolean] = newBoolean }
                    .set { it[PostgresqlAllTypesNotNull::localDate] = newLocalDate }
                    .set { it[PostgresqlAllTypesNotNull::offsetDateTime] = newOffsetDateTime }
                    .set { it[PostgresqlAllTypesNotNull::localTim] = newLocalTim }
                    .set { it[PostgresqlAllTypesNotNull::localDateTime] = newLocalDateTime }
                    .set { it[PostgresqlAllTypesNotNull::uuid] = newUuid }
                    .set { it[PostgresqlAllTypesNotNull::int] = newInt }
                    .where { it[PostgresqlAllTypesNotNull::id] eq postgresqlAllTypesNotNull.id }
                    .execute()
}
