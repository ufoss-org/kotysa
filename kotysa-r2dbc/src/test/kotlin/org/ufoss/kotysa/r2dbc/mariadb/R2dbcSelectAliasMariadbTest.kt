/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.R2dbcBadGrammarException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectAliasMariadbTest : AbstractR2dbcMariadbTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcR2dbcBadGrammarException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcR2dbcBadGrammarException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcR2dbcBadGrammarException`() {
        assertThatThrownBy { runTest {
            repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameGet().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAlias().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() = runTest {
        assertThat(repository.selectCountUserGroupByCountryGet().toList())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() = runTest {
        assertThat(repository.selectCountUserGroupByCountryAlias().toList())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcR2dbcBadGrammarException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() = runTest {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname)
        } }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() = runTest {
        assertThat(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery returns results`() = runTest {
        assertThat(repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAliasSubQuery returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAliasSubQuery().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAliasSubQuery returns User and Admin roles`() = runTest {
        assertThat(repository.selectRoleLabelWhereInUserSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectFirstnameByFirstnameTableAlias returns TheBoss firstname`() = runTest {
        assertThat(repository.selectFirstnameByFirstnameTableAlias(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdTableAlias returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelAndIdFromUserIdTableAlias(userBboss.id))
            .isEqualTo(Pair(roleAdmin.label, roleAdmin.id))
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias(userBboss.id)
        } }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        } }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        } }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        } }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }
}

class UserRepositorySelectAlias(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    suspend fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select MariadbUsers.firstname `as` "fna"
                from MariadbUsers
                where MariadbUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
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

    suspend fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } `as` "dummy" where MariadbUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MariadbUsers.firstname `as` "fna"
                    from MariadbUsers)
        } where MariadbUsers.firstname eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
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

    suspend fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select MariadbUsers["u"].firstname
                from MariadbUsers `as` "u"
                where MariadbUsers["u"].firstname eq firstname
                ).fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles.id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles.id eq MariadbUsers["u"].roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers.roleId
                where MariadbUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select MariadbRoles["r"].label and MariadbRoles["r"].id
                from MariadbRoles `as` "r" innerJoin MariadbUsers `as` "u" on MariadbRoles["r"].id eq MariadbUsers["u"].roleId
                where MariadbUsers.id eq userId)
            .fetchOne()
}
