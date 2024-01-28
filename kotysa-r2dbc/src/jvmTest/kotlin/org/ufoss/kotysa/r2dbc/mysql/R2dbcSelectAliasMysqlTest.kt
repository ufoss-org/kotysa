/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.R2dbcException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.AbstractCoroutinesUserRepository

class R2dbcSelectAliasMysqlTest : AbstractR2dbcMysqlTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws R2dbcException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcR2dbcException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcR2dbcException`() {
        assertThatThrownBy { runTest {
            repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(R2dbcException::class.java)
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
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcR2dbcException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
        } }
            .isInstanceOf(R2dbcException::class.java)
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
        } }.isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() = runTest {
        assertThat(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Tag("asyncer")
    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery throws IllegalArgumentException`() {
        assertThatThrownBy { runTest {
            repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).toList()
        } }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Tag("jasync")
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
        } }.isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        } }.isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        } }.isInstanceOf(R2dbcException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        } }.isInstanceOf(R2dbcException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: R2dbcSqlClient) :
    AbstractCoroutinesUserRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    ) {

    suspend fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select MysqlUsers.firstname `as` "fna"
                from MysqlUsers
                where MysqlUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select MysqlUsers.firstname `as` "fna"
                from MysqlUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct MysqlRoles.label
                andCaseWhenExists {
            (this select MysqlUsers.id
                    from MysqlUsers
                    where MysqlUsers.roleId eq MysqlRoles.id
                    and MysqlUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from MysqlRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select MysqlUsers.firstname `as` "fn"
                from MysqlUsers
                orderByAsc MysqlUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select MysqlUsers.firstname `as` "fna"
                from MysqlUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount MysqlUsers.id and MysqlUsers.roleId `as` "roleId"
                from MysqlUsers
                groupBy MysqlUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount MysqlUsers.id and MysqlUsers.roleId `as` "roleId"
                from MysqlUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select MysqlRoles.label and MysqlRoles.id `as` "roleId"
                from MysqlRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MysqlUsers.firstname `as` "fna"
                    from MysqlUsers)
        } `as` "dummy" where MysqlUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MysqlUsers.firstname `as` "fna"
                    from MysqlUsers)
        } `as` "dummy" where MysqlUsers.firstname eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MysqlUsers.firstname `as` "fna"
                    from MysqlUsers)
        } `as` "dummy" where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct MysqlRoles.label
                    andCaseWhenExists {
                (this select MysqlUsers.id
                        from MysqlUsers
                        where MysqlUsers.roleId eq MysqlRoles.id
                        and MysqlUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from MysqlRoles)
        } `as` "dummy" where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select MysqlUsers.firstname `as` "fna"
                    from MysqlUsers)
        } `as` "dummy" orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select MysqlRoles.label and MysqlRoles.id `as` "roleId"
                    from MysqlRoles)
        } `as` "dummy" where QueryAlias<Int>("roleId") `in`
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select MysqlUsers["u"].firstname
                from MysqlUsers `as` "u"
                where MysqlUsers["u"].firstname eq firstname
                ).fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select MysqlRoles["r"].label and MysqlRoles["r"].id
                from MysqlRoles `as` "r" innerJoin MysqlUsers `as` "u" on MysqlRoles["r"].id eq MysqlUsers["u"].roleId
                where MysqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select MysqlRoles["r"].label and MysqlRoles.id
                from MysqlRoles `as` "r" innerJoin MysqlUsers `as` "u" on MysqlRoles["r"].id eq MysqlUsers["u"].roleId
                where MysqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select MysqlRoles["r"].label and MysqlRoles["r"].id
                from MysqlRoles `as` "r" innerJoin MysqlUsers `as` "u" on MysqlRoles.id eq MysqlUsers["u"].roleId
                where MysqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select MysqlRoles["r"].label and MysqlRoles["r"].id
                from MysqlRoles `as` "r" innerJoin MysqlUsers `as` "u" on MysqlRoles["r"].id eq MysqlUsers.roleId
                where MysqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select MysqlRoles["r"].label and MysqlRoles["r"].id
                from MysqlRoles `as` "r" innerJoin MysqlUsers `as` "u" on MysqlRoles["r"].id eq MysqlUsers["u"].roleId
                where MysqlUsers.id eq userId)
            .fetchOne()
}
