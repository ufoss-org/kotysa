/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.MysqlUser
import org.ufoss.kotysa.test.mysqlBboss
import org.ufoss.kotysa.test.mysqlJdoe


class SpringJdbcSelectStringMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectString>() {
    override val context = startContext<UserRepositorySpringJdbcMysqlSelectString>()

    override val repository = getContextRepository<UserRepositorySpringJdbcMysqlSelectString>()

    @Test
    fun `Verify selectFirstByFirstame finds John`() {
        assertThat(repository.selectFirstByFirstame(mysqlJdoe.firstname))
                .isEqualTo(mysqlJdoe)
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
        assertThat(repository.selectAllByAlias(mysqlBboss.alias))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectAllByAlias(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlJdoe)
    }

    @Test
    fun `Verify selectAllByFirstameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstameNotEq(mysqlJdoe.firstname))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstameNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlJdoe, mysqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(mysqlBboss.alias))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlBboss)
    }

    @Test
    fun `Verify selectAllByFirstameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstameContains("oh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlJdoe)
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
                .containsExactlyInAnyOrder(mysqlJdoe)
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
                .containsExactlyInAnyOrder(mysqlJdoe)
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
                .containsExactlyInAnyOrder(mysqlBboss)
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
                .containsExactlyInAnyOrder(mysqlBboss)
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
                .containsExactlyInAnyOrder(mysqlBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo"))
                .hasSize(0)
    }
}


class UserRepositorySpringJdbcMysqlSelectString(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectFirstByFirstameNotNullable(firstname: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] eq firstname }
            .fetchFirst()

    fun selectAllByFirstameNotEq(firstname: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] notEq firstname }
            .fetchAll()

    fun selectAllByAlias(alias: String?) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] eq alias }
            .fetchAll()

    fun selectAllByAliasNotEq(alias: String?) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] notEq alias }
            .fetchAll()

    fun selectAllByFirstameContains(firstnameContains: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] contains firstnameContains }
            .fetchAll()

    fun selectAllByFirstameStartsWith(firstnameStartsWith: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] startsWith firstnameStartsWith }
            .fetchAll()

    fun selectAllByFirstameEndsWith(firstnameEndsWith: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] endsWith firstnameEndsWith }
            .fetchAll()

    fun selectAllByAliasContains(aliasContains: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] contains aliasContains }
            .fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] startsWith aliasStartsWith }
            .fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] endsWith aliasEndsWith }
            .fetchAll()
}
