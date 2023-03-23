/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritanceMysqlTest : AbstractR2dbcMysqlTest<InheritanceMysqlRepository>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        InheritanceMysqlRepository(sqlClient)

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(MysqlInheriteds, "id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(MysqlInheriteds, "name").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(MysqlInheriteds, "id")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAll())
        }.test()
            .verifyComplete()
    }
}


class InheritanceMysqlRepository(private val sqlClient: MysqlReactorSqlClient) : Repository {

    override fun init() {
        createTable()
            .then(insert())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTable() = sqlClient createTable MysqlInheriteds

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom MysqlInheriteds

    fun selectAll() = sqlClient selectAllFrom MysqlInheriteds

    fun selectInheritedById(id: String) =
        (sqlClient selectFrom MysqlInheriteds
                where MysqlInheriteds.id eq id
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
