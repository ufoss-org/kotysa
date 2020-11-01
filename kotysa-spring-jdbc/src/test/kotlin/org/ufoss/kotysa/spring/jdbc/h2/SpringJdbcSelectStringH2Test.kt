/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.H2User
import org.ufoss.kotysa.test.h2Bboss
import org.ufoss.kotysa.test.h2Jdoe


class SpringJdbcSelectStringH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectString>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectString>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectString>()

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                .isEqualTo(h2Jdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        assertThatThrownBy { repository.selectFirstByFirstnameNotNullable("Unknown") }
                .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        assertThat(repository.selectAllByAlias(h2Bboss.alias))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Bboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectAllByAlias(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Jdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(h2Jdoe.firstname))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Bboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Jdoe, h2Bboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val col = mutableListOf<String>()
        col.add(h2Jdoe.firstname)
        col.add(h2Bboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(col))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Jdoe, h2Bboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(h2Bboss.alias))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Bboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Jdoe)
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
                .containsExactlyInAnyOrder(h2Jdoe)
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
                .containsExactlyInAnyOrder(h2Jdoe)
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
                .containsExactlyInAnyOrder(h2Bboss)
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
                .containsExactlyInAnyOrder(h2Bboss)
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
                .containsExactlyInAnyOrder(h2Bboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo"))
                .hasSize(0)
    }
}


class UserRepositorySpringJdbcH2SelectString(client: NamedParameterJdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectFirstByFirstnameNotNullable(firstname: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] eq firstname }
            .fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] notEq firstname }
            .fetchAll()

    fun selectAllByFirstnameIn(firstnames: Collection<String>) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] `in` firstnames }
            .fetchAll()

    fun selectAllByAlias(alias: String?) = sqlClient.select<H2User>()
            .where { it[H2User::alias] eq alias }
            .fetchAll()

    fun selectAllByAliasNotEq(alias: String?) = sqlClient.select<H2User>()
            .where { it[H2User::alias] notEq alias }
            .fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] contains firstnameContains }
            .fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] startsWith firstnameStartsWith }
            .fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] endsWith firstnameEndsWith }
            .fetchAll()

    fun selectAllByAliasContains(aliasContains: String) = sqlClient.select<H2User>()
            .where { it[H2User::alias] contains aliasContains }
            .fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) = sqlClient.select<H2User>()
            .where { it[H2User::alias] startsWith aliasStartsWith }
            .fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) = sqlClient.select<H2User>()
            .where { it[H2User::alias] endsWith aliasEndsWith }
            .fetchAll()
}
