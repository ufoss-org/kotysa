/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcInheritanceH2Test : AbstractR2dbcH2Test<InheritanceH2Repository>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceH2Repository(sqlClient)

    @Test
    fun `Verify selectInheritedById finds inherited`() = runTest {
        assertThat(repository.selectInheritedById("id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() = runTest {
        assertThat(repository.selectById(H2Inheriteds, "id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() = runTest {
        assertThat(repository.selectFirstByName(H2Inheriteds, "name"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(H2Inheriteds, "id"))
                .isEqualTo(1)
            assertThat(repository.selectAll().toList())
                .isEmpty()
        }
    }
}

class InheritanceH2Repository(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTable H2Inheriteds
    }

    private suspend fun insert() {
        sqlClient insert inherited
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2Inheriteds

    fun selectAll() = sqlClient selectAllFrom H2Inheriteds

    suspend fun selectInheritedById(id: String) =
        (sqlClient selectFrom H2Inheriteds
                where H2Inheriteds.id eq id
                ).fetchOne()

    suspend fun <T : Entities<U>, U : Entity<String>> selectById(table: T, id: String) =
        (sqlClient selectFrom table
                where table.id eq id
                ).fetchOne()

    suspend fun <T : Nameables<U>, U : Nameable> selectFirstByName(table: T, name: String) =
        (sqlClient selectFrom table
                where table.name eq name
                ).fetchFirst()

    suspend fun <T : Entities<U>, U : Entity<String>> deleteById(table: T, id: String) =
        (sqlClient deleteFrom table
                where table.id eq id
                ).execute()
}
