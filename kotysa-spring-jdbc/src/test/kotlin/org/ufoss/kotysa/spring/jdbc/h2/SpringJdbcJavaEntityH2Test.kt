/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.JavaUser
import org.ufoss.kotysa.test.UserDto
import org.ufoss.kotysa.test.javaBboss
import org.ufoss.kotysa.test.javaJdoe


class SpringJdbcJavaEntityH2Test : AbstractSpringJdbcH2Test<JavaUserH2Repository>() {
    override val context = startContext<JavaUserH2Repository>()

    override val repository = getContextRepository<JavaUserH2Repository>()

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAll())
                .hasSize(2)
                .containsExactlyInAnyOrder(javaJdoe, javaBboss)
    }

    @Test
    fun `Verify selectFirstByFirstame finds John`() {
        assertThat(repository.selectFirstByFirstame("John"))
                .isEqualTo(javaJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstame finds no Unknown`() {
        assertThat(repository.selectFirstByFirstame("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectByAlias1 finds TheBoss`() {
        assertThat(repository.selectByAlias1("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias2 finds TheBoss`() {
        assertThat(repository.selectByAlias2("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias3 finds TheBoss`() {
        assertThat(repository.selectByAlias3("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias1 with null alias finds John`() {
        assertThat(repository.selectByAlias1(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllByAlias2 with null alias finds John`() {
        assertThat(repository.selectByAlias2(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectByAlias3 with null alias finds John`() {
        assertThat(repository.selectByAlias3(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss"))
    }

    @Test
    fun `Verify deleteAll works correctly`() {
        assertThat(repository.deleteAll())
                .isEqualTo(2)
        assertThat(repository.selectAll())
                .isEmpty()
        // re-insert users
        repository.insert()
    }
}

private val tables =
        tables().h2 {
            table<JavaUser> {
                name = "java_users"
                column { it[JavaUser::getLogin].varchar() }
                        .primaryKey()
                column { it[JavaUser::getFirstname].varchar().name("fname") }
                column { it[JavaUser::getLastname].varchar().name("lname") }
                column { it[JavaUser::isAdmin].boolean() }
                column { it[JavaUser::getAlias1].varchar() }
                column { it[JavaUser::getAlias2].varchar() }
                column { it[JavaUser::getAlias3].varchar() }
            }
        }


class JavaUserH2Repository(client: JdbcTemplate) : Repository {

    private val sqlClient = client.sqlClient(tables)

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() = sqlClient.createTable<JavaUser>()

    fun insert() = sqlClient.insert(javaJdoe, javaBboss)

    fun deleteAll() = sqlClient.deleteAllFromTable<JavaUser>()

    fun selectAll() = sqlClient.selectAll<JavaUser>()

    fun selectFirstByFirstame(firstname: String) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getFirstname] eq firstname }
            .fetchFirstOrNull()

    fun selectByAlias1(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias1] eq alias }
            .fetchAll()

    fun selectByAlias2(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias2] eq alias }
            .fetchAll()

    fun selectByAlias3(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias3] eq alias }
            .fetchAll()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[JavaUser::getFirstname]} ${it[JavaUser::getLastname]}",
                        it[JavaUser::getAlias1])
            }.fetchAll()
}
