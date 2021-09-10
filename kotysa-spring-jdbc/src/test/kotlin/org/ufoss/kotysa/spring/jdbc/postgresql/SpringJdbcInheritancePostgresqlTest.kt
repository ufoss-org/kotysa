/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcInheritancePostgresqlTest : AbstractSpringJdbcPostgresqlTest<InheritancePostgresqlRepository>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritancePostgresqlRepository>(resource)
    }

    override val repository: InheritancePostgresqlRepository by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(POSTGRESQL_INHERITED, "id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(POSTGRESQL_INHERITED, "name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(POSTGRESQL_INHERITED, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritancePostgresqlRepository(client: JdbcOperations) : Repository {

    val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() {
        sqlClient createTable POSTGRESQL_INHERITED
    }

    fun insert() {
        sqlClient insert inherited
    }

    private fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_INHERITED

    fun selectAll() = sqlClient selectAllFrom POSTGRESQL_INHERITED

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom POSTGRESQL_INHERITED
                    where POSTGRESQL_INHERITED.id eq id
                    ).fetchOne()

    fun <T : ENTITY<U>, U : Entity<String>> selectById(table: T, id: String) =
            (sqlClient selectFrom table
                    where table.id eq id
                    ).fetchOne()

    fun <T : NAMEABLE<U>, U : Nameable> selectFirstByName(table: T, name: String) =
            (sqlClient selectFrom table
                    where table.name eq name
                    ).fetchFirst()

    fun <T : ENTITY<U>, U : Entity<String>> deleteById(table: T, id: String) =
            (sqlClient deleteFrom table
                    where table.id eq id
                    ).execute()
}
