/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcInheritancePostgresqlTest : AbstractJdbcPostgresqlTest<InheritancePostgresqlRepository>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritancePostgresqlRepository(sqlClient)

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
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(POSTGRESQL_INHERITED, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritancePostgresqlRepository(private val sqlClient: JdbcSqlClient) : Repository {

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
