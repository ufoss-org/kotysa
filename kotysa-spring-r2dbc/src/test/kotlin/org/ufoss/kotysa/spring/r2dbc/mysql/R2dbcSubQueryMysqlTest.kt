/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSubQueryMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSubQuery>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryR2dbcMysqlSubQuery>(resource)
    }

    override val repository: UserRepositoryR2dbcMysqlSubQuery by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id).block())
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id).block())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id).block())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery throws IllegalArgumentException`() {
        Assertions.assertThatThrownBy {
            repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).blockLast()
        }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toIterable())
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }
}


class UserRepositoryR2dbcMysqlSubQuery(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
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

    fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select MysqlRoles.label and MysqlRoles.id
                from MysqlRoles
                where MysqlRoles.id eq
                {
                    (this select MysqlUsers.roleId
                            from MysqlUsers
                            where MysqlUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
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
