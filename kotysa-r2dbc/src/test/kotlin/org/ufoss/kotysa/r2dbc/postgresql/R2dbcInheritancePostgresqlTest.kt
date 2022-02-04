/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcInheritancePostgresqlTest : AbstractR2dbcPostgresqlTest<InheritancePostgresqlRepository>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritancePostgresqlRepository(sqlClient)

    @Test
    fun `Verify extension function selectById finds inherited`() = runTest {
        assertThat(repository.selectById(PostgresqlInheriteds, "id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() = runTest {
        assertThat(repository.selectInheritedById("id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() = runTest {
        assertThat(repository.selectFirstByName(PostgresqlInheriteds, "name"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() = runTest {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(PostgresqlInheriteds, "id"))
                .isEqualTo(1)
            assertThat(repository.selectAll().toList())
                .isEmpty()
        }
    }
}


class InheritancePostgresqlRepository(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTable PostgresqlInheriteds
    }

    suspend fun insert() {
        sqlClient insert inherited
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom PostgresqlInheriteds

    fun selectAll() = sqlClient selectAllFrom PostgresqlInheriteds

    suspend fun selectInheritedById(id: String) =
        (sqlClient selectFrom PostgresqlInheriteds
                where PostgresqlInheriteds.id eq id
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
