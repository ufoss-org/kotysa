/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcInheritanceH2Test : AbstractJdbcH2Test<InheritanceH2Repository>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceH2Repository(sqlClient)

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(H2_INHERITED, "id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(H2_INHERITED, "name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(H2_INHERITED, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritanceH2Repository(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() {
        sqlClient createTable H2_INHERITED
    }

    fun insert() {
        sqlClient insert inherited
    }

    private fun deleteAll() = sqlClient deleteAllFrom H2_INHERITED

    fun selectAll() = sqlClient selectAllFrom H2_INHERITED

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom H2_INHERITED
                    where H2_INHERITED.id eq id
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
