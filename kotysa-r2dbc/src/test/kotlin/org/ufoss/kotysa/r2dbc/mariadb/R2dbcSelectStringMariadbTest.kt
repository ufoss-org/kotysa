/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class R2dbcSelectStringMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectString>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectString(sqlClient)

    @Test
    fun `Verify selectFirstByFirstname finds John`() = runTest {
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
            .isEqualTo(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() = runTest {
        assertThat(repository.selectFirstByFirstname("Unknown"))
            .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() = runTest {
        Assertions.assertThatThrownBy {
            runBlocking {
                repository.selectFirstByFirstnameNotNullable("Unknown")
            }
        }.isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() = runTest {
        assertThat(repository.selectByAlias(userBboss.alias).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runTest {
        assertThat(repository.selectByAlias(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() = runTest {
        assertThat(repository.selectAllByFirstnameNotEq(userJdoe.firstname).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() = runTest {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown").toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() = runTest {
        val seq = sequenceOf(userJdoe.firstname, userBboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() = runTest {
        assertThat(repository.selectAllByAliasNotEq(userBboss.alias).toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() = runTest {
        assertThat(repository.selectAllByAliasNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() = runTest {
        assertThat(repository.selectAllByFirstnameContains("oh").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() = runTest {
        assertThat(repository.selectAllByFirstnameContains("boz").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() = runTest {
        assertThat(repository.selectAllByFirstnameStartsWith("Joh").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() = runTest {
        assertThat(repository.selectAllByFirstnameStartsWith("oh").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() = runTest {
        assertThat(repository.selectAllByFirstnameEndsWith("ohn").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() = runTest {
        assertThat(repository.selectAllByFirstnameEndsWith("joh").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() = runTest {
        assertThat(repository.selectAllByAliasContains("heBos").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() = runTest {
        assertThat(repository.selectAllByAliasContains("heBoz").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() = runTest {
        assertThat(repository.selectAllByAliasStartsWith("TheBo").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() = runTest {
        assertThat(repository.selectAllByAliasStartsWith("heBo").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() = runTest {
        assertThat(repository.selectAllByAliasEndsWith("Boss").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() = runTest {
        assertThat(repository.selectAllByAliasEndsWith("TheBo").toList())
            .hasSize(0)
    }
}


class UserRepositoryJdbcMariadbSelectString(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname eq firstname
                ).fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname notEq firstname
                ).fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname `in` firstnames
                ).fetchAll()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias eq alias
                ).fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias notEq alias
                ).fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname contains firstnameContains
                ).fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname startsWith firstnameStartsWith
                ).fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname endsWith firstnameEndsWith
                ).fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias contains aliasContains
                ).fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias startsWith aliasStartsWith
                ).fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias endsWith aliasEndsWith
                ).fetchAll()
}
