/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritanceH2Test : AbstractR2dbcH2Test<InheritanceH2Repository>() {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        InheritanceH2Repository(sqlClient)

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
        assertThat(repository.selectById(H2Inheriteds, "id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(H2Inheriteds, "name").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(H2Inheriteds, "id")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAll())
        }.test()
            .verifyComplete()
    }
}


class InheritanceH2Repository(private val sqlClient: H2ReactorSqlClient) : Repository {

    override fun init() {
        createTable()
            .then(insert())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTable() = sqlClient createTable H2Inheriteds

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom H2Inheriteds

    fun selectAll() = sqlClient selectAllFrom H2Inheriteds

    fun selectInheritedById(id: String) =
        (sqlClient selectFrom H2Inheriteds
                where H2Inheriteds.id eq id
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
