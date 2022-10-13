/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository
import java.sql.SQLSyntaxErrorException

class JdbcSelectAliasMariadbTest : AbstractJdbcMariadbTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname) }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname) }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)) }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
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
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)) }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
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
        }.isInstanceOf(SQLSyntaxErrorException::class.java)
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
        }.isInstanceOf(SQLSyntaxErrorException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        }.isInstanceOf(SQLSyntaxErrorException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        }.isInstanceOf(SQLSyntaxErrorException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        }.isInstanceOf(SQLSyntaxErrorException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: JdbcSqlClient) :
    AbstractUserRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    ) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select MariadbUsers.firstname `as` "fna"
                from MariadbUsers
                where MariadbUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select MariadbUsers.firstname `as` "fna"
                from MariadbUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct MariadbRoles.label
                andCaseWhenExists {
            (this select MariadbUsers.id
                    from MariadbUsers
                    where MariadbUsers.roleId eq MariadbRoles.id
                    and MariadbUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from MariadbRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select MariadbUsers.firstname `as` "fn"
                from MariadbUsers
                orderByAsc MariadbUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select MariadbUsers.firstname `as` "fna"
                from MariadbUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount MariadbUsers.id and MariadbUsers.roleId `as` "roleId"
                from MariadbUsers
                groupBy MariadbUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount MariadbUsers.id and MariadbUsers.roleId `as` "roleId"
                from MariadbUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select MariadbRoles.label and MariadbRoles.id `as` "roleId"
                from MariadbRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select MariadbUsers.roleId
                            from MariadbUsers
                            where MariadbUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } `as` "dummy" where MariadbUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } where MariadbUsers.firstname eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } `as` "dummy" where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct MariadbRoles.label
                    andCaseWhenExists {
                (this select MariadbUsers.id
                        from MariadbUsers
                        where MariadbUsers.roleId eq MariadbRoles.id
                        and MariadbUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from MariadbRoles)
        } `as` "dummy" where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } `as` "dummy" orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select MariadbRoles.label and MariadbRoles.id `as` "roleId"
                    from MariadbRoles)
        } `as` "dummy" where QueryAlias<Int>("roleId") `in`
                {
                    (this select MariadbUsers.roleId
                            from MariadbUsers
                            where MariadbUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select MariadbUsers["u"].firstname
                from MariadbUsers `as` "u"
                where MariadbUsers["u"].firstname eq firstname
                ).fetchOne()

    fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles.id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles.id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers.roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers.id eq userId)
            .fetchOne()
}
