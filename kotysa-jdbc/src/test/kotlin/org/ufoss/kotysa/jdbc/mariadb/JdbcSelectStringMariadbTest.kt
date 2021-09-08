/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.MARIADB_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe
import java.sql.Connection


class JdbcSelectStringMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectString>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectString(connection)

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .isEqualTo(userJdoe)
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
        assertThat(repository.selectByAlias(userBboss.alias))
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectByAlias(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(userJdoe.firstname))
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val seq = sequenceOf(userJdoe.firstname, userBboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(userBboss.alias))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh"))
                .hasSize(1)
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userJdoe)
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
                .containsExactlyInAnyOrder(userBboss)
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
                .containsExactlyInAnyOrder(userBboss)
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
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo"))
                .hasSize(0)
    }
}


class UserRepositoryJdbcMariadbSelectString(connection: Connection) : AbstractUserRepositoryJdbcMariadb(connection) {

    fun selectFirstByFirstnameNotNullable(firstname: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname eq firstname
                    ).fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname notEq firstname
                    ).fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname `in` firstnames
                    ).fetchAll()

    fun selectByAlias(alias: String?) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.alias eq alias
                    ).fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.alias notEq alias
                    ).fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname contains firstnameContains
                    ).fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname startsWith firstnameStartsWith
                    ).fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.firstname endsWith firstnameEndsWith
                    ).fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.alias contains aliasContains
                    ).fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.alias startsWith aliasStartsWith
                    ).fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.alias endsWith aliasEndsWith
                    ).fetchAll()
}