/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectIntMysqlTest : AbstractR2dbcMysqlTest<IntRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<IntRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    private val mysqlIntWithNullable = MysqlInt(
            org.ufoss.kotysa.test.mysqlIntWithNullable.intNotNull,
            org.ufoss.kotysa.test.mysqlIntWithNullable.intNullable,
            1)

    private val mysqlIntWithoutNullable = MysqlInt(
            org.ufoss.kotysa.test.mysqlIntWithoutNullable.intNotNull,
            org.ufoss.kotysa.test.mysqlIntWithoutNullable.intNullable,
            2)

    @Test
    fun `Verify selectAllByIntNotNull finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNull(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds mysqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullNotEq(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInf(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullInf(10).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds mysqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds mysqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSup(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullSup(12).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds mysqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds mysqlIntWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullable(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds mysqlIntWithoutNullable`() {
        assertThat(repository.selectAllByIntNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntNullableNotEq(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInf(7).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableInf(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(7).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds mysqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableSup(5).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableSup(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds mysqlIntWithNullable`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(5).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds mysqlIntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlIntWithNullable)
    }
}


class IntRepositoryMysqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mysqlTables)

    override fun init() {
        createTables()
                .then(insertInts())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlInt>()

    private fun insertInts() = sqlClient.insert(mysqlIntWithNullable, mysqlIntWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlInt>()

    fun selectAllByIntNotNull(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] eq int }
            .fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] notEq int }
            .fetchAll()

    fun selectAllByIntNotNullInf(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] inf int }
            .fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] infOrEq int }
            .fetchAll()

    fun selectAllByIntNotNullSup(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] sup int }
            .fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNotNull] supOrEq int }
            .fetchAll()

    fun selectAllByIntNullable(int: Int?) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] eq int }
            .fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] notEq int }
            .fetchAll()

    fun selectAllByIntNullableInf(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] inf int }
            .fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] infOrEq int }
            .fetchAll()

    fun selectAllByIntNullableSup(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] sup int }
            .fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) = sqlClient.select<MysqlInt>()
            .where { it[MysqlInt::intNullable] supOrEq int }
            .fetchAll()
}
*/