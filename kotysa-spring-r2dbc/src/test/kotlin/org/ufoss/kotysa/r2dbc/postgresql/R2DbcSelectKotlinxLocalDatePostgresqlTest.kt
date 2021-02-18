/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql
/*
import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectKotlinxLocalDatePostgresqlTest : AbstractR2dbcPostgresqlTest<KotlinxLocalDateRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateRepositoryPostgresqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNull finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds postgresqlKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds postgresqlKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds postgresqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds postgresqlKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds postgresqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds postgresqlKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlKotlinxLocalDateWithNullable)
    }
}


class KotlinxLocalDateRepositoryPostgresqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
                .then(insertLocalDates())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlKotlinxLocalDate>()

    private fun insertLocalDates() =
            sqlClient.insert(postgresqlKotlinxLocalDateWithNullable, postgresqlKotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<PostgresqlKotlinxLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNotNull] afterOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            sqlClient.select<PostgresqlKotlinxLocalDate>()
                    .where { it[PostgresqlKotlinxLocalDate::localDateNullable] afterOrEq localDate }
                    .fetchAll()
}
*/