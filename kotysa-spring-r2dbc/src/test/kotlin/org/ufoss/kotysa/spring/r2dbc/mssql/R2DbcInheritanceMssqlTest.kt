/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcInheritanceMssqlTest : AbstractR2dbcMssqlTest<InheritanceMssqlRepository>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceMssqlRepository>(resource)
    }

    override val repository: InheritanceMssqlRepository by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(MssqlInheriteds, "id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(MssqlInheriteds, "name").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(MssqlInheriteds, "id")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAll())
        }.test()
                .verifyComplete()
    }
}


class InheritanceMssqlRepository(dbClient: DatabaseClient) : Repository {

    val sqlClient = dbClient.sqlClient(mssqlTables)

    override fun init() {
        createTable()
                .then(insert())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTable() = sqlClient createTable MssqlInheriteds

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom MssqlInheriteds

    fun selectAll() = sqlClient selectAllFrom MssqlInheriteds

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom MssqlInheriteds
                    where MssqlInheriteds.id eq id
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
