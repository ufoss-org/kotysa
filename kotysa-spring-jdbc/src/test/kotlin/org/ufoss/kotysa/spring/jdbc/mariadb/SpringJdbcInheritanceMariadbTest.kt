/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcInheritanceMariadbTest : AbstractSpringJdbcMariadbTest<InheritanceMariadbRepository>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceMariadbRepository>(resource)
    }

    override val repository: InheritanceMariadbRepository by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(MariadbInheriteds, "id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(MariadbInheriteds, "name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(MariadbInheriteds, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritanceMariadbRepository(client: JdbcOperations) : Repository {

    val sqlClient = client.sqlClient(mariadbTables)

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() {
        sqlClient createTable MariadbInheriteds
    }

    fun insert() {
        sqlClient insert inherited
    }

    private fun deleteAll() = sqlClient deleteAllFrom MariadbInheriteds

    fun selectAll() = sqlClient selectAllFrom MariadbInheriteds

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom MariadbInheriteds
                    where MariadbInheriteds.id eq id
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
