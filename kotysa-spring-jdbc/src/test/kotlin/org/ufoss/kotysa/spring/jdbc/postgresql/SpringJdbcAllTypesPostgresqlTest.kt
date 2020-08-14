/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.*
import java.util.*


class SpringJdbcAllTypesPostgresqlTest : AbstractSpringJdbcPostgresqlTest<AllTypesRepositoryPostgresql>() {
    override val context = startContext<AllTypesRepositoryPostgresql>()

    override val repository = getContextRepository<AllTypesRepositoryPostgresql>()

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
        assertThat(repository.selectAllAllTypesNullable())
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
        repository.updateAllTypesNotNull("new", false, newLocalDate, newOffsetDateTime, newLocalTime,
                newLocalDateTime, newUuid, newInt)
        assertThat(repository.selectAllAllTypesNotNull())
                .hasSize(1)
                .containsExactlyInAnyOrder(
                        PostgresqlAllTypesNotNull(postgresqlAllTypesNotNull.id, "new", false, newLocalDate, newOffsetDateTime,
                                newLocalTime, newLocalDateTime, newUuid, newInt))
        repository.updateAllTypesNotNull(postgresqlAllTypesNotNull.string, postgresqlAllTypesNotNull.boolean, postgresqlAllTypesNotNull.localDate,
                postgresqlAllTypesNotNull.offsetDateTime, postgresqlAllTypesNotNull.localTim, postgresqlAllTypesNotNull.localDateTime,
                postgresqlAllTypesNotNull.uuid, postgresqlAllTypesNotNull.int)
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
