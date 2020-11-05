/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectStringPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectString>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryPostgresqlSelectString>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(postgresqlJdoe.firstname).block())
                .isEqualTo(postgresqlJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown").block())
                .isNull()
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        assertThat(repository.selectAllByAlias(postgresqlBboss.alias).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectAllByAlias(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(postgresqlJdoe.firstname).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown").toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlJdoe, postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(postgresqlBboss.alias).toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val seq = sequenceOf(postgresqlJdoe.firstname, postgresqlBboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlJdoe, postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() {
        assertThat(repository.selectAllByFirstnameContains("boz").toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("Joh").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("oh").toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() {
        assertThat(repository.selectAllByFirstnameEndsWith("ohn").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() {
        assertThat(repository.selectAllByFirstnameEndsWith("joh").toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() {
        assertThat(repository.selectAllByAliasContains("heBos").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() {
        assertThat(repository.selectAllByAliasContains("heBoz").toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() {
        assertThat(repository.selectAllByAliasStartsWith("TheBo").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() {
        assertThat(repository.selectAllByAliasStartsWith("heBo").toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() {
        assertThat(repository.selectAllByAliasEndsWith("Boss").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo").toIterable())
                .hasSize(0)
    }
}


class UserRepositoryPostgresqlSelectString(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectAllByFirstnameNotEq(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] notEq firstname }
            .fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] `in` firstnames }
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
