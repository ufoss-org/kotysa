/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.*


class SpringJdbcInheritancePostgresqlTest : AbstractSpringJdbcPostgresqlTest<InheritancePostgresqlRepository>() {
    override val context = startContext<InheritancePostgresqlRepository>()

    override val repository = getContextRepository<InheritancePostgresqlRepository>()

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
        assertThat(repository.deleteById<Inherited>("id"))
                .isEqualTo(1)
        assertThat(repository.selectAll())
                .isEmpty()
        // re-insert
        repository.insert()
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


class InheritancePostgresqlRepository(client: JdbcTemplate) : Repository {

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

inline fun <reified T : Entity<String>> InheritancePostgresqlRepository.selectById(id: String) =
        sqlClient.select<T>().where { it[Entity<String>::getId] eq id }.fetchOne()

inline fun <reified T : Nameable> InheritancePostgresqlRepository.selectFirstByName(name: String) =
        sqlClient.select<T>().where { it[Nameable::name] eq name }.fetchFirst()

inline fun <reified T : Entity<String>> InheritancePostgresqlRepository.deleteById(id: String) =
        sqlClient.deleteFromTable<T>().where { it[Entity<String>::getId] eq id }.execute()