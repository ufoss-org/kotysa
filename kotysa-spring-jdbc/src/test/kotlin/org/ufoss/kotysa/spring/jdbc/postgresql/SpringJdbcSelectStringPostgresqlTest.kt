/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.PostgresqlUser
import org.ufoss.kotysa.test.postgresqlBboss
import org.ufoss.kotysa.test.postgresqlJdoe


class SpringJdbcSelectStringPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectString>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlSelectString>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlSelectString>()

    @Test
    fun `Verify selectFirstByFirstame finds John`() {
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
                .isEqualTo(postgresqlJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstame finds no Unknown`() {
        assertThat(repository.selectFirstByFirstame("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstameNotNullable finds no Unknown, throws NoResultException`() {
        Assertions.assertThatThrownBy { repository.selectFirstByFirstameNotNullable("Unknown") }
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
    fun `Verify selectAllByFirstameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstameNotEq(postgresqlJdoe.firstname))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstameNotEq("Unknown"))
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
    fun `Verify selectAllByFirstameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstameContains("oh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstameContains get nothing by searching boz`() {
        assertThat(repository.selectAllByFirstameContains("boz"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstameStartsWith get John by searching Joh`() {
        assertThat(repository.selectAllByFirstameStartsWith("Joh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstameStartsWith get nothing by searching oh`() {
        assertThat(repository.selectAllByFirstameStartsWith("oh"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstameEndsWith get John by searching ohn`() {
        assertThat(repository.selectAllByFirstameEndsWith("ohn"))
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstameEndsWith get nothing by searching joh`() {
        assertThat(repository.selectAllByFirstameEndsWith("joh"))
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


class UserRepositorySpringJdbcPostgresqlSelectString(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectFirstByFirstameNotNullable(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirst()

    fun selectAllByFirstameNotEq(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] notEq firstname }
            .fetchAll()

    fun selectAllByAlias(alias: String?) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] eq alias }
            .fetchAll()

    fun selectAllByAliasNotEq(alias: String?) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] notEq alias }
            .fetchAll()

    fun selectAllByFirstameContains(firstnameContains: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] contains firstnameContains }
            .fetchAll()

    fun selectAllByFirstameStartsWith(firstnameStartsWith: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] startsWith firstnameStartsWith }
            .fetchAll()

    fun selectAllByFirstameEndsWith(firstnameEndsWith: String) = sqlClient.select<PostgresqlUser>()
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
