/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritancePostgresqlTest : AbstractR2dbcPostgresqlTest<InheritancePostgresqlRepository>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = InheritancePostgresqlRepository(sqlClient)

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(PostgresqlInheriteds, "id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(PostgresqlInheriteds, "name").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(PostgresqlInheriteds, "id")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAll())
        }.test()
            .verifyComplete()
    }
}


class InheritancePostgresqlRepository(private val sqlClient: PostgresqlReactorSqlClient) : Repository {

    override fun init() {
        createTable()
            .then(insert())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
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
