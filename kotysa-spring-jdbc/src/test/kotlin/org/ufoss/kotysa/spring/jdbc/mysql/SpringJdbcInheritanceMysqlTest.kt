/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.*


class SpringJdbcInheritanceMysqlTest : AbstractSpringJdbcMysqlTest<InheritanceMysqlRepository>() {
    override val context = startContext<InheritanceMysqlRepository>()

    override val repository = getContextRepository<InheritanceMysqlRepository>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById<Inherited>("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName<Inherited>("name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById<Inherited>("id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}

private val tables =
        tables().mysql {
            table<Inherited> {
                name = "inherited"
                column { it[Inherited::getId].varchar() }
                        .primaryKey()
                column { it[Inherited::name].varchar() }
                column { it[Inherited::firstname].varchar() }
            }
        }


class InheritanceMysqlRepository(client: JdbcOperations) : Repository {

    val sqlClient = client.sqlClient(tables)

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() = sqlClient.createTable<Inherited>()

    fun insert() = sqlClient.insert(inherited)

    private fun deleteAll() = sqlClient.deleteAllFromTable<Inherited>()

    fun selectAll() = sqlClient.selectAll<Inherited>()

    fun selectInheritedById(id: String) =
            sqlClient.select<Inherited>().where { it[Inherited::getId] eq id }.fetchOne()
}

inline fun <reified T : Entity<String>> InheritanceMysqlRepository.selectById(id: String) =
        sqlClient.select<T>().where { it[Entity<String>::getId] eq id }.fetchOne()

inline fun <reified T : Nameable> InheritanceMysqlRepository.selectFirstByName(name: String) =
        sqlClient.select<T>().where { it[Nameable::name] eq name }.fetchFirst()

inline fun <reified T : Entity<String>> InheritanceMysqlRepository.deleteById(id: String) =
        sqlClient.deleteFromTable<T>().where { it[Entity<String>::getId] eq id }.execute()
