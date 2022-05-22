/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSubQueryMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSubQuery>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcMysqlSubQuery(sqlClient)

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
    fun `Verify selectCaseWhenExistsSubQuery throws IllegalArgumentException`() {
        Assertions.assertThatThrownBy {
            runTest {
                repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList()
            }
        }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(4)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
                roleGod.label,
            )
    }
}


class UserRepositoryR2dbcMysqlSubQuery(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMysql(sqlClient) {

    suspend fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MysqlUsers.firstname and {
            (this select MysqlRoles.label
                    from MysqlRoles
                    where MysqlRoles.id eq MysqlUsers.roleId
                    and MysqlRoles.label eq roleAdmin.label)
        }
                from MysqlUsers
                where MysqlUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select MysqlRoles.label
                from MysqlRoles
                whereExists
                {
                    (this select MysqlUsers.id
                            from MysqlUsers
                            where MysqlUsers.roleId eq MysqlRoles.id
                            and MysqlUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select MysqlRoles.label and MysqlRoles.id
                from MysqlRoles
                where MysqlRoles.id eq
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id eq userId)
                })
            .fetchOne()

    suspend fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select MysqlRoles.label and MysqlRoles.id
                from MysqlRoles
                where MysqlRoles.id notEq 0
                and MysqlRoles.id eq
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select MysqlRoles.label and MysqlRoles.id
                from MysqlRoles
                where MysqlRoles.id `in`
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct MysqlRoles.label
                andCaseWhenExists {
            (this select MysqlUsers.id
                    from MysqlUsers
                    where MysqlUsers.roleId eq MysqlRoles.id
                    and MysqlUsers.id `in` userIds)
        } then true `else` false
                from MysqlRoles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select MysqlRoles.label
                from MysqlRoles
                orderByDescCaseWhenExists {
            (this select MysqlUsers.id
                    from MysqlUsers
                    where MysqlUsers.roleId eq MysqlRoles.id
                    and MysqlUsers.id `in` userIds)
        } then true `else` false
                andAsc MysqlRoles.label)
            .fetchAll()
}
