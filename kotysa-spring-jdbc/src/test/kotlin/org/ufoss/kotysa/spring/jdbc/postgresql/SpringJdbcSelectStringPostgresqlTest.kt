/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.PostgresqlUser
import org.ufoss.kotysa.test.postgresqlBboss
import org.ufoss.kotysa.test.postgresqlJdoe


class SpringJdbcSelectStringPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectString>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlSelectString>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlSelectString>()

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(postgresqlJdoe.firstname))
                .isEqualTo(postgresqlJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        Assertions.assertThatThrownBy { repository.selectFirstByFirstnameNotNullable("Unknown") }
                .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        assertThat(repository.selectAllByAlias(postgresqlBboss.alias))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectAllByAlias(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(postgresqlJdoe.firstname))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlJdoe, postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(postgresqlBboss.alias))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() {
        assertThat(repository.selectAllByFirstnameContains("boz"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("Joh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("oh"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() {
        assertThat(repository.selectAllByFirstnameEndsWith("ohn"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() {
        assertThat(repository.selectAllByFirstnameEndsWith("joh"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() {
        assertThat(repository.selectAllByAliasContains("heBos"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() {
        assertThat(repository.selectAllByAliasContains("heBoz"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() {
        assertThat(repository.selectAllByAliasStartsWith("TheBo"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() {
        assertThat(repository.selectAllByAliasStartsWith("heBo"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() {
        assertThat(repository.selectAllByAliasEndsWith("Boss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo"))
                .hasSize(0)
    }
}


class UserRepositorySpringJdbcPostgresqlSelectString(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectFirstByFirstnameNotNullable(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] notEq firstname }
            .fetchAll()

    fun selectAllByAlias(alias: String?) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] eq alias }
            .fetchAll()

    fun selectAllByAliasNotEq(alias: String?) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] notEq alias }
            .fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] contains firstnameContains }
            .fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] startsWith firstnameStartsWith }
            .fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] endsWith firstnameEndsWith }
            .fetchAll()

    fun selectAllByAliasContains(aliasContains: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] contains aliasContains }
            .fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] startsWith aliasStartsWith }
            .fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] endsWith aliasEndsWith }
            .fetchAll()
}
