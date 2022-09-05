/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSubQueryMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSubQuery>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcMariadbSubQuery(sqlClient)

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
            .hasSize(4)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
                roleGod.label,
            )
    }
}


class UserRepositoryR2dbcMariadbSubQuery(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    suspend fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MariadbUsers.firstname and {
            (this select MariadbRoles.label
                    from MariadbRoles
                    where MariadbRoles.id eq MariadbUsers.roleId
                    and MariadbRoles.label eq roleAdmin.label)
        }
                from MariadbUsers
                where MariadbUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select MariadbRoles.label
                from MariadbRoles
                whereExists
                {
                    (this select MariadbUsers.id
                            from MariadbUsers
                            where MariadbUsers.roleId eq MariadbRoles.id
                            and MariadbUsers.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select MariadbRoles.label and MariadbRoles.id
                from MariadbRoles
                where MariadbRoles.id eq
                {
                    (this select MariadbUsers.roleId
                            from MariadbUsers
                            where MariadbUsers.id eq userId)
                })
            .fetchOne()

    suspend fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select MariadbRoles.label and MariadbRoles.id
                from MariadbRoles
                where MariadbRoles.id notEq 0
                and MariadbRoles.id eq
                {
                    (this select MariadbUsers.roleId
                            from MariadbUsers
                            where MariadbUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select MariadbRoles.label and MariadbRoles.id
                from MariadbRoles
                where MariadbRoles.id `in`
                {
                    (this select MariadbUsers.roleId
                            from MariadbUsers
                            where MariadbUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct MariadbRoles.label
                andCaseWhenExists {
            (this select MariadbUsers.id
                    from MariadbUsers
                    where MariadbUsers.roleId eq MariadbRoles.id
                    and MariadbUsers.id `in` userIds)
        } then true `else` false
                from MariadbRoles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select MariadbRoles.label
                from MariadbRoles
                orderByDescCaseWhenExists {
            (this select MariadbUsers.id
                    from MariadbUsers
                    where MariadbUsers.roleId eq MariadbRoles.id
                    and MariadbUsers.id `in` userIds)
        } then true `else` false
                andAsc MariadbRoles.label)
            .fetchAll()
}
