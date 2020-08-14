/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


class SpringJdbcSelectIntPostgresqlTest : AbstractSpringJdbcPostgresqlTest<IntRepositoryPostgresqlSelect>() {
    override val context = startContext<IntRepositoryPostgresqlSelect>()

    override val repository = getContextRepository<IntRepositoryPostgresqlSelect>()

    private val postgresqlIntWithNullable = PostgresqlInt(
            org.ufoss.kotysa.test.postgresqlIntWithNullable.intNotNull,
            org.ufoss.kotysa.test.postgresqlIntWithNullable.intNullable,
            1)

    private val postgresqlIntWithoutNullable = PostgresqlInt(
            org.ufoss.kotysa.test.postgresqlIntWithoutNullable.intNotNull,
            org.ufoss.kotysa.test.postgresqlIntWithoutNullable.intNullable,
            2)

    @Test
    fun `Verify selectAllByIntNotNull finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNull(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds postgresqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullNotEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInf(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullInf(10))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds postgresqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds postgresqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSup(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullSup(12))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds postgresqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds postgresqlIntWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullable(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds postgresqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntNullableNotEq(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInf(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableInf(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds postgresqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableSup(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableSup(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds postgresqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds postgresqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlIntWithNullable)
    }
}


class IntRepositoryPostgresqlSelect(client: JdbcTemplate) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertInts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<PostgresqlInt>()

    private fun insertInts() = sqlClient.insert(postgresqlIntWithNullable, postgresqlIntWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<PostgresqlInt>()

    fun selectAllByIntNotNull(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] eq int }
            .fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] notEq int }
            .fetchAll()

    fun selectAllByIntNotNullInf(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] inf int }
            .fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] infOrEq int }
            .fetchAll()

    fun selectAllByIntNotNullSup(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] sup int }
            .fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNotNull] supOrEq int }
            .fetchAll()

    fun selectAllByIntNullable(int: Int?) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] eq int }
            .fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] notEq int }
            .fetchAll()

    fun selectAllByIntNullableInf(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] inf int }
            .fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] infOrEq int }
            .fetchAll()

    fun selectAllByIntNullableSup(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] sup int }
            .fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) = sqlClient.select<PostgresqlInt>()
            .where { it[PostgresqlInt::intNullable] supOrEq int }
            .fetchAll()
}
