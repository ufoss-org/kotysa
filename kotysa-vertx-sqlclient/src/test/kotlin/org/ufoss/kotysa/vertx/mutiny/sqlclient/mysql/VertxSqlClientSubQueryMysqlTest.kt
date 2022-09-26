/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSubQueryMysqlTest :
    AbstractVertxSqlClientMysqlTest<UserRepositoryJdbcMysqlSubQuery>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcMysqlSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id).await().indefinitely())
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        assertThat(
            repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        )
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id).await().indefinitely())
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() {
        assertThat(
            repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        )
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() {
        assertThat(
            repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        )
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }
}


class UserRepositoryJdbcMysqlSubQuery(sqlClient: VertxSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

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
