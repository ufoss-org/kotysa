/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.ReactorTransactionalOp
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritancePostgresqlTest : AbstractR2dbcPostgresqlTest<InheritancePostgresqlRepository>() {
    override val context = startContext<InheritancePostgresqlRepository>()

    override val repository = getContextRepository<InheritancePostgresqlRepository>()
    private val transactionalOp = context.getBean<ReactorTransactionalOp>()

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById<Inherited>("id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName<Inherited>("name").block())
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        transactionalOp.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById<Inherited>("id")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAll())
        }.test()
                .verifyComplete()
    }
}

private val tables =
        tables().postgresql {
            table<Inherited> {
                name = "inherited"
                column { it[Inherited::getId].varchar() }
                        .primaryKey()
                column { it[Inherited::name].varchar() }
                column { it[Inherited::firstname].varchar() }
            }
        }


class InheritancePostgresqlRepository(dbClient: DatabaseClient) : Repository {

    val sqlClient = dbClient.sqlClient(tables)

    override fun init() {
        createTable()
                .then(insert())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTable() = sqlClient.createTable<Inherited>()

    fun insert() = sqlClient.insert(inherited)

    private fun deleteAll() = sqlClient.deleteAllFromTable<Inherited>()

    fun selectAll() = sqlClient.selectAll<Inherited>()

    fun selectInheritedById(id: String) =
            sqlClient.select<Inherited>().where { it[Inherited::getId] eq id }.fetchOne()
}

inline fun <reified T : Entity<String>> InheritancePostgresqlRepository.selectById(id: String) =
        sqlClient.select<T>().where { it[Entity<String>::getId] eq id }.fetchOne()

inline fun <reified T : Nameable> InheritancePostgresqlRepository.selectFirstByName(name: String) =
        sqlClient.select<T>().where { it[Nameable::name] eq name }.fetchFirst()

inline fun <reified T : Entity<String>> InheritancePostgresqlRepository.deleteById(id: String) =
        sqlClient.deleteFromTable<T>().where { it[Entity<String>::getId] eq id }.execute()
