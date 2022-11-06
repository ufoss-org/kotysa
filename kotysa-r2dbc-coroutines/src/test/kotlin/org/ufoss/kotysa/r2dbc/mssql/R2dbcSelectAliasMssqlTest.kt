/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.R2dbcBadGrammarException
import io.r2dbc.spi.R2dbcNonTransientException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectAliasMssqlTest : AbstractR2dbcMssqlTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { runTest { 
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcSQLSyntaxErrorException`() {
        assertThatThrownBy { runTest {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname)
        } }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcSQLSyntaxErrorException`() {
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
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcSQLSyntaxErrorException`() {
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
        } }.isInstanceOf(R2dbcNonTransientException::class.java)
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
        } }.isInstanceOf(R2dbcNonTransientException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        } }.isInstanceOf(R2dbcNonTransientException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        } }.isInstanceOf(R2dbcNonTransientException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy { runTest {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        } }.isInstanceOf(R2dbcNonTransientException::class.java)
    }
}

class UserRepositorySelectAlias(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcMssql(sqlClient) {

    suspend fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select MssqlUsers.firstname `as` "fna"
                from MssqlUsers
                where MssqlUsers.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select MssqlUsers.firstname `as` "fna"
                from MssqlUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct MssqlRoles.label
                andCaseWhenExists {
            (this select MssqlUsers.id
                    from MssqlUsers
                    where MssqlUsers.roleId eq MssqlRoles.id
                    and MssqlUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from MssqlRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select MssqlUsers.firstname `as` "fn"
                from MssqlUsers
                orderByAsc MssqlUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select MssqlUsers.firstname `as` "fna"
                from MssqlUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount MssqlUsers.id and MssqlUsers.roleId `as` "roleId"
                from MssqlUsers
                groupBy MssqlUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount MssqlUsers.id and MssqlUsers.roleId `as` "roleId"
                from MssqlUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select MssqlRoles.label and MssqlRoles.id `as` "roleId"
                from MssqlRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select MssqlUsers.roleId
                            from MssqlUsers
                            where MssqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MssqlUsers.firstname `as` "fna"
                    from MssqlUsers)
        } `as` "derivedTable" where MssqlUsers.firstname["derivedTable.fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MssqlUsers.firstname `as` "fna"
                    from MssqlUsers)
        } `as` "derivedTable" where MssqlUsers.firstname eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select MssqlUsers.firstname `as` "fna"
                    from MssqlUsers)
        } `as` "derivedTable" where QueryAlias<String>("derivedTable.fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct MssqlRoles.label
                    andCaseWhenExists {
                (this select MssqlUsers.id
                        from MssqlUsers
                        where MssqlUsers.roleId eq MssqlRoles.id
                        and MssqlUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from MssqlRoles)
        } `as` "derivedTable" where QueryAlias<Boolean>("derivedTable.roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select MssqlUsers.firstname `as` "fna"
                    from MssqlUsers)
        } `as` "derivedTable" orderByAsc QueryAlias<String>("derivedTable.fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select MssqlRoles.label and MssqlRoles.id `as` "roleId"
                    from MssqlRoles)
        } `as` "derivedTable" where QueryAlias<Int>("derivedTable.roleId") `in`
                {
                    (this select MssqlUsers.roleId
                            from MssqlUsers
                            where MssqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select MssqlUsers["u"].firstname
                from MssqlUsers `as` "u"
                where MssqlUsers["u"].firstname eq firstname
                ).fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select MssqlRoles["r"].label and MssqlRoles["r"].id
                from MssqlRoles `as` "r" innerJoin MssqlUsers `as` "u" on MssqlRoles["r"].id eq MssqlUsers["u"].roleId
                where MssqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select MssqlRoles["r"].label and MssqlRoles.id
                from MssqlRoles `as` "r" innerJoin MssqlUsers `as` "u" on MssqlRoles["r"].id eq MssqlUsers["u"].roleId
                where MssqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select MssqlRoles["r"].label and MssqlRoles["r"].id
                from MssqlRoles `as` "r" innerJoin MssqlUsers `as` "u" on MssqlRoles.id eq MssqlUsers["u"].roleId
                where MssqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select MssqlRoles["r"].label and MssqlRoles["r"].id
                from MssqlRoles `as` "r" innerJoin MssqlUsers `as` "u" on MssqlRoles["r"].id eq MssqlUsers.roleId
                where MssqlUsers["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select MssqlRoles["r"].label and MssqlRoles["r"].id
                from MssqlRoles `as` "r" innerJoin MssqlUsers `as` "u" on MssqlRoles["r"].id eq MssqlUsers["u"].roleId
                where MssqlUsers.id eq userId)
            .fetchOne()
}
