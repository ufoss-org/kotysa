/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.*
import reactor.kotlin.test.test


class R2DbcInheritanceH2Test : AbstractR2dbcH2Test<InheritanceH2Repository>() {
    override val context = startContext<InheritanceH2Repository>()

    override val repository = getContextRepository<InheritanceH2Repository>()
    private val operator = context.getBean<TransactionalOperator>().transactionalOp()

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
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById<Inherited>("id")
                    .doOnNext { n -> assertThat(n).isEqualTo(1) }
                    .thenMany(repository.selectAll())
        }.test()
                .verifyComplete()
    }
}

private val tables =
        tables().h2Old {
            table<Inherited> {
                name = "inherited"
                column { it[Inherited::getId].varchar() }
                        .primaryKey()
                column { it[Inherited::name].varchar() }
                column { it[Inherited::firstname].varchar() }
            }
        }


class InheritanceH2Repository(dbClient: DatabaseClient) : Repository {

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

inline fun <reified T : Entity<String>> InheritanceH2Repository.selectById(id: String) =
        sqlClient.select<T>().where { it[Entity<String>::getId] eq id }.fetchOne()

inline fun <reified T : Nameable> InheritanceH2Repository.selectFirstByName(name: String) =
        sqlClient.select<T>().where { it[Nameable::name] eq name }.fetchFirst()

inline fun <reified T : Entity<String>> InheritanceH2Repository.deleteById(id: String) =
        sqlClient.deleteFromTable<T>().where { it[Entity<String>::getId] eq id }.execute()
*/