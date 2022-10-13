/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientInheritanceMariadbTest :
    AbstractVertxSqlClientMariadbTest<InheritanceMariadbRepository>() {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = InheritanceMariadbRepository(sqlClient)

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(MariadbInheriteds, "id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(MariadbInheriteds, "name").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        val all = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(MariadbInheriteds, "id")
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAll() }
        }.await().indefinitely()
        assertThat(all).isEmpty()
    }
}


class InheritanceMariadbRepository(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTable()
            .chain { -> insert() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTable() = sqlClient createTable MariadbInheriteds

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom MariadbInheriteds

    fun selectAll() = sqlClient selectAllFrom MariadbInheriteds

    fun selectInheritedById(id: String) =
        (sqlClient selectFrom MariadbInheriteds
                where MariadbInheriteds.id eq id
                ).fetchOne()

    fun <T : Entities<U>, U : Entity<String>> selectById(table: T, id: String) =
        (sqlClient selectFrom table
                where table.id eq id
                ).fetchOne()

    fun <T : Nameables<U>, U : Nameable> selectFirstByName(table: T, name: String) =
        (sqlClient selectFrom table
                where table.name eq name
                ).fetchFirst()

    fun <T : Entities<U>, U : Entity<String>> deleteById(table: T, id: String) =
        (sqlClient deleteFrom table
                where table.id eq id
                ).execute()
}
