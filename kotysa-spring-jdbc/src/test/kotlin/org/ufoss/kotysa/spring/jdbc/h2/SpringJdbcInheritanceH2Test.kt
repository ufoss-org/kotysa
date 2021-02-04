/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*


class SpringJdbcInheritanceH2Test : AbstractSpringJdbcH2Test<InheritanceH2Repository>() {
    override val context = startContext<InheritanceH2Repository>()

    override val repository = getContextRepository<InheritanceH2Repository>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(H2_INHERITED, "id"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(H2_INHERITED, "name"))
                .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(H2_INHERITED, "id"))
                    .isEqualTo(1)
            assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}


class InheritanceH2Repository(client: JdbcOperations) : Repository {

    val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() = sqlClient createTable H2_INHERITED

    fun insert() = sqlClient insert inherited

    private fun deleteAll() = sqlClient deleteAllFrom H2_INHERITED

    fun selectAll() = sqlClient selectAllFrom H2_INHERITED

    fun selectInheritedById(id: String) =
            (sqlClient selectFrom H2_INHERITED
                    where H2_INHERITED.id eq id
                    ).fetchOne()

    fun <T : H2_ENTITY<U>, U : Entity<String>> selectById(table: T, id: String) =
            (sqlClient selectFrom table
                    where table.id eq id
                    ).fetchOne()

    fun <T : H2_NAMEABLE<U>, U : Nameable> selectFirstByName(table: T, name: String) =
            (sqlClient selectFrom table
                    where table.name eq name
                    ).fetchFirst()

    fun <T : H2_ENTITY<U>, U : Entity<String>> deleteById(table: T, id: String) =
            (sqlClient deleteFrom table
                    where table.id eq id
                    ).execute()
}
