/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSubQueryMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryJdbcMssqlSubQuery>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMssqlSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() = runTest {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() = runTest {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() = runTest {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() = runTest {
        assertThat(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }
}


class UserRepositoryJdbcMssqlSubQuery(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcMssql(sqlClient) {

    suspend fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MssqlUsers.firstname and {
            (this select MssqlRoles.label
                    from MssqlRoles
                    where MssqlRoles.id eq MssqlUsers.roleId
                    and MssqlRoles.label eq roleAdmin.label)
        }
                from MssqlUsers
                where MssqlUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select MssqlRoles.label
                from MssqlRoles
                whereExists
                {
                    (this select MssqlUsers.id
                            from MssqlUsers
                            where MssqlUsers.roleId eq MssqlRoles.id
                            and MssqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select MssqlRoles.label and MssqlRoles.id
                from MssqlRoles
                where MssqlRoles.id eq
                {
                    (this select MssqlUsers.roleId
                            from MssqlUsers
                            where MssqlUsers.id eq userId)
                })
            .fetchOne()

    suspend fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select MssqlRoles.label and MssqlRoles.id
                from MssqlRoles
                where MssqlRoles.id notEq 0
                and MssqlRoles.id eq
                {
                    (this select MssqlUsers.roleId
                            from MssqlUsers
                            where MssqlUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select MssqlRoles.label and MssqlRoles.id
                from MssqlRoles
                where MssqlRoles.id `in`
                {
                    (this select MssqlUsers.roleId
                            from MssqlUsers
                            where MssqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct MssqlRoles.label
                andCaseWhenExists {
            (this select MssqlUsers.id
                    from MssqlUsers
                    where MssqlUsers.roleId eq MssqlRoles.id
                    and MssqlUsers.id `in` userIds)
        } then true `else` false
                from MssqlRoles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select MssqlRoles.label
                from MssqlRoles
                orderByDescCaseWhenExists {
            (this select MssqlUsers.id
                    from MssqlUsers
                    where MssqlUsers.roleId eq MssqlRoles.id
                    and MssqlUsers.id `in` userIds)
        } then true `else` false
                andAsc MssqlRoles.label)
            .fetchAll()
}
