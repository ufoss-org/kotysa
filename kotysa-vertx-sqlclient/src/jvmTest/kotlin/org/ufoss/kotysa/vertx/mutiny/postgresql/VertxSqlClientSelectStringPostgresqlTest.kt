/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectStringPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelectString>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = UserRepositoryPostgresqlSelectString(sqlClient)

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname).await().indefinitely())
            .isEqualTo(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown").await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectByAlias finds BigBoss`() {
        assertThat(repository.selectByAlias(userBboss.alias).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectByAlias(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown`() {
        assertThat(repository.selectFirstByFirstnameNotNullable("Unknown").await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(userJdoe.firstname).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown").await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(userBboss.alias).await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss`() {
        val seq = sequenceOf(userJdoe.firstname, userBboss.firstname)
        assertThat(repository.selectAllByFirstnameIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() {
        assertThat(repository.selectAllByFirstnameContains("boz").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("Joh").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("oh").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() {
        assertThat(repository.selectAllByFirstnameEndsWith("ohn").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() {
        assertThat(repository.selectAllByFirstnameEndsWith("joh").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() {
        assertThat(repository.selectAllByAliasContains("heBos").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() {
        assertThat(repository.selectAllByAliasContains("heBoz").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() {
        assertThat(repository.selectAllByAliasStartsWith("TheBo").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() {
        assertThat(repository.selectAllByAliasStartsWith("heBo").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() {
        assertThat(repository.selectAllByAliasEndsWith("Boss").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo").await().indefinitely())
            .hasSize(0)
    }
}


class UserRepositoryPostgresqlSelectString(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname eq firstname
                ).fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname notEq firstname
                ).fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname `in` firstnames
                ).fetchAll()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias eq alias
                ).fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias notEq alias
                ).fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname contains firstnameContains
                ).fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname startsWith firstnameStartsWith
                ).fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname endsWith firstnameEndsWith
                ).fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias contains aliasContains
                ).fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias startsWith aliasStartsWith
                ).fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias endsWith aliasEndsWith
                ).fetchAll()
}
