/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class R2DbcSelectStringH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectString>() {
    override val context = startContext<UserRepositoryH2SelectString>()
    override val repository = getContextRepository<UserRepositoryH2SelectString>()

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname).block())
                .isEqualTo(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown").block())
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown`() {
        assertThat(repository.selectFirstByFirstnameNotNullable("Unknown").block())
                .isNull()
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        assertThat(repository.selectByAlias(userBboss.alias).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectByAlias(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(userJdoe.firstname).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown").toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(userBboss.alias).toIterable())
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val seq = sequenceOf(userJdoe.firstname, userBboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh").toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userBboss)
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
                .containsExactlyInAnyOrder(userBboss)
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
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo").toIterable())
                .hasSize(0)
    }
}


class UserRepositoryH2SelectString(
    sqlClient: ReactorSqlClient,
) : org.ufoss.kotysa.spring.r2dbc.h2.AbstractUserRepositoryH2(sqlClient) {

    fun selectFirstByFirstnameNotNullable(firstname: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname eq firstname
                    ).fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname notEq firstname
                    ).fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname `in` firstnames
                    ).fetchAll()

    fun selectByAlias(alias: String?) =
            (sqlClient selectFrom H2Users
                    where H2Users.alias eq alias
                    ).fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
            (sqlClient selectFrom H2Users
                    where H2Users.alias notEq alias
                    ).fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname contains firstnameContains
                    ).fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname startsWith firstnameStartsWith
                    ).fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.firstname endsWith firstnameEndsWith
                    ).fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.alias contains aliasContains
                    ).fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.alias startsWith aliasStartsWith
                    ).fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
            (sqlClient selectFrom H2Users
                    where H2Users.alias endsWith aliasEndsWith
                    ).fetchAll()
}
