/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SpringJdbcSelectAliasH2Test : AbstractSpringJdbcH2Test<UserRepositorySelectAlias>() {
    override val context = startContext<UserRepositorySelectAlias>()
    override val repository = getContextRepository<UserRepositorySelectAlias>()

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcInvalidDataAccessApiUsageException`() {
        assertThatThrownBy { repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname) }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcInvalidDataAccessApiUsageException`() {
        assertThatThrownBy { repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname) }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcInvalidDataAccessApiUsageException`() {
        assertThatThrownBy { repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)) }
            .isInstanceOf(BadSqlGrammarException::class.java)
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
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcInvalidDataAccessApiUsageException`() {
        assertThatThrownBy { repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)) }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname)
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAliasSubQuery returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAliasSubQuery())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAliasSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereInUserSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectFirstnameByFirstnameTableAlias returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameByFirstnameTableAlias(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdTableAlias returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelAndIdFromUserIdTableAlias(userBboss.id))
            .isEqualTo(Pair(roleAdmin.label, roleAdmin.id))
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias(userBboss.id)
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositorySelectAlias(client: JdbcOperations) :
    AbstractUserRepository<H2Roles, H2Users, H2UserRoles>(client.sqlClient(h2Tables), H2Roles, H2Users, H2UserRoles) {

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
        (sqlClient select H2Roles.label and H2Roles.id `as` "roleId"
                from H2Roles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where H2Users.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where H2Users.firstname eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct H2Roles.label
                    andCaseWhenExists {
                (this select H2Users.id
                        from H2Users
                        where H2Users.roleId eq H2Roles.id
                        and H2Users.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from H2Roles)
        } where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select H2Roles.label and H2Roles.id `as` "roleId"
                    from H2Roles)
        } where QueryAlias<Int>("roleId") `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select H2Users["u"].firstname
                from H2Users `as` "u"
                where H2Users["u"].firstname eq firstname
                ).fetchOne()

    fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles.id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles.id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users.roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users.id eq userId)
            .fetchOne()
}
