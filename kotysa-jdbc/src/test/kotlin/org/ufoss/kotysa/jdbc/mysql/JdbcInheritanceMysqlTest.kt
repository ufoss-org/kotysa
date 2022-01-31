/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcInheritanceMysqlTest : AbstractJdbcMysqlTest<InheritanceMysqlRepository>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceMysqlRepository(sqlClient)

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(MYSQL_INHERITED, "id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(MYSQL_INHERITED, "name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(MYSQL_INHERITED, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritanceMysqlRepository(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() {
        sqlClient createTable MYSQL_INHERITED
    }

    fun insert() {
        sqlClient insert inherited
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_INHERITED

    fun selectAll() = sqlClient selectAllFrom MYSQL_INHERITED

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom MYSQL_INHERITED
                    where MYSQL_INHERITED.id eq id
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
