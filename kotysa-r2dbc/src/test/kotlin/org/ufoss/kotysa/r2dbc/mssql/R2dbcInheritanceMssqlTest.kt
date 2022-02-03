/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcInheritanceMssqlTest : AbstractR2dbcMssqlTest<InheritanceMssqlRepository>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceMssqlRepository(sqlClient)

    @Test
    fun `Verify selectInheritedById finds inherited`() = runTest {
        assertThat(repository.selectInheritedById("id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() = runTest {
        assertThat(repository.selectById(MSSQL_INHERITED, "id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() = runTest {
        assertThat(repository.selectFirstByName(MSSQL_INHERITED, "name"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() = runTest {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(MSSQL_INHERITED, "id"))
                .isEqualTo(1)
            assertThat(repository.selectAll().toList())
                .isEmpty()
        }
    }
}


class InheritanceMssqlRepository(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTable MSSQL_INHERITED
    }

    suspend fun insert() {
        sqlClient insert inherited
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MSSQL_INHERITED

    fun selectAll() = sqlClient selectAllFrom MSSQL_INHERITED

    suspend fun selectInheritedById(id: String) =
        (sqlClient selectFrom MSSQL_INHERITED
                where MSSQL_INHERITED.id eq id
                ).fetchOne()

    suspend fun <T : ENTITY<U>, U : Entity<String>> selectById(table: T, id: String) =
        (sqlClient selectFrom table
                where table.id eq id
                ).fetchOne()

    suspend fun <T : NAMEABLE<U>, U : Nameable> selectFirstByName(table: T, name: String) =
        (sqlClient selectFrom table
                where table.name eq name
                ).fetchFirst()

    suspend fun <T : ENTITY<U>, U : Entity<String>> deleteById(table: T, id: String) =
        (sqlClient deleteFrom table
                where table.id eq id
                ).execute()
}
