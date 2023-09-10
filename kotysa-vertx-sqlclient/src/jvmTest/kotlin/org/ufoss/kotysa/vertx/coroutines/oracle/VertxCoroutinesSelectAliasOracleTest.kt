/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import io.vertx.oracleclient.OracleException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.AbstractCoroutinesUserRepository

class VertxCoroutinesSelectAliasOracleTest : AbstractVertxCoroutinesOracleTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws VertxCoroutinesException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname)
        } }
            .isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcVertxCoroutinesException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname)
        } }
            .isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcVertxCoroutinesException`() {
        assertThatThrownBy { runTest {
            repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(OracleException::class.java)
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
            .containsExactlyInAnyOrder(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() = runTest {
        assertThat(repository.selectCountUserGroupByCountryAlias().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcVertxCoroutinesException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(OracleException::class.java)
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
        } }.isInstanceOf(OracleException::class.java)
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
        } }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        } }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        } }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        } }.isInstanceOf(OracleException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: CoroutinesVertxSqlClient) :
    AbstractCoroutinesUserRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    ) {

    suspend fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                where OracleUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct OracleRoles.label
                andCaseWhenExists {
            (this select OracleUsers.id
                    from OracleUsers
                    where OracleUsers.roleId eq OracleRoles.id
                    and OracleUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from OracleRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select OracleUsers.firstname `as` "fn"
                from OracleUsers
                orderByAsc OracleUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount OracleUsers.id and OracleUsers.roleId `as` "roleId"
                from OracleUsers
                groupBy OracleUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount OracleUsers.id and OracleUsers.roleId `as` "roleId"
                from OracleUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select OracleRoles.label and OracleRoles.id `as` "roleId"
                from OracleRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select OracleUsers.roleId
                            from OracleUsers
                            where OracleUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where OracleUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where OracleUsers.firstname eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct OracleRoles.label
                    andCaseWhenExists {
                (this select OracleUsers.id
                        from OracleUsers
                        where OracleUsers.roleId eq OracleRoles.id
                        and OracleUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from OracleRoles)
        } `as` "dummy" where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select OracleRoles.label and OracleRoles.id `as` "roleId"
                    from OracleRoles)
        } `as` "dummy" where QueryAlias<Int>("roleId") `in`
                {
                    (this select OracleUsers.roleId
                            from OracleUsers
                            where OracleUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select OracleUsers["u"].firstname
                from OracleUsers `as` "u"
                where OracleUsers["u"].firstname eq firstname
                ).fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles.id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles.id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers.roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers.id eq userId)
            .fetchOne()
}
