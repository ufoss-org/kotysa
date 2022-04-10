/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectAliasH2Test : AbstractJdbcH2Test<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameGet())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAlias())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryGet())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryAlias())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }
}

class UserRepositorySelectAlias(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcH2(sqlClient) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                where H2Users.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct H2Roles.label
                andCaseWhenExists {
            (this select H2Users.id
                    from H2Users
                    where H2Users.roleId eq H2Roles.id
                    and H2Users.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from H2Roles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select H2Users.firstname `as` "fn"
                from H2Users
                orderByAsc H2Users.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount H2Users.id and H2Users.roleId `as` "roleId"
                from H2Users
                groupBy H2Users.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount H2Users.id and H2Users.roleId `as` "roleId"
                from H2Users
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select H2Roles.label and H2Roles.id `as` "freddee"
                from H2Roles
                where QueryAlias<Int>("freddee") `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where H2Users.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectFrom {
            (this selectDistinct H2Roles.label
                    andCaseWhenExists {
                (this select H2Users.id
                        from H2Users
                        where H2Users.roleId eq H2Roles.id
                        and H2Users.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from H2Roles
                    where QueryAlias<Boolean>("roleUsedByUser") eq true)
        })
            .fetchAll()
}
