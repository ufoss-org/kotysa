/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientInheritancePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<InheritancePostgresqlRepository>() {
    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = InheritancePostgresqlRepository(sqlClient)

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(PostgresqlInheriteds, "id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(PostgresqlInheriteds, "name").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        val all = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(PostgresqlInheriteds, "id")
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAll() }
        }.await().indefinitely()
        assertThat(all).isEmpty()
    }
}


class InheritancePostgresqlRepository(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTable()
            .chain { -> insert() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTable() = sqlClient createTable PostgresqlInheriteds

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlInheriteds

    fun selectAll() = sqlClient selectAllFrom PostgresqlInheriteds

    fun selectInheritedById(id: String) =
        (sqlClient selectFrom PostgresqlInheriteds
                where PostgresqlInheriteds.id eq id
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
