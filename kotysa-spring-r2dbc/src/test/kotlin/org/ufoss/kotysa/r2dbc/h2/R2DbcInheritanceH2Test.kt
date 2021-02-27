/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritanceH2Test : AbstractR2dbcH2Test<InheritanceH2Repository>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<InheritanceH2Repository>()
        repository = getContextRepository()
    }

    @Test
    fun `Verify extension function selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(H2_INHERITED, "id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(H2_INHERITED, "name").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(H2_INHERITED, "id")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAll())
        }.test()
                .verifyComplete()
    }
}


class InheritanceH2Repository(dbClient: DatabaseClient) : Repository {

    val sqlClient = dbClient.sqlClient(h2Tables)

    override fun init() {
        createTable()
                .then(insert())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTable() = sqlClient createTable H2_INHERITED

    fun insert() = sqlClient insert inherited

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