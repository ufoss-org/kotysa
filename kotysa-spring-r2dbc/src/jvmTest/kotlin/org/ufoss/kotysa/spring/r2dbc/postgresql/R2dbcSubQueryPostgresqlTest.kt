/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSubQueryPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSubQuery>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = UserRepositoryJdbcPostgresqlSubQuery(sqlClient)

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
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toIterable())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
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


class UserRepositoryJdbcPostgresqlSubQuery(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select PostgresqlUsers.firstname and {
            (this select PostgresqlRoles.label
                    from PostgresqlRoles
                    where PostgresqlRoles.id eq PostgresqlUsers.roleId
                    and PostgresqlRoles.label eq roleAdmin.label)
        }
                from PostgresqlUsers
                where PostgresqlUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select PostgresqlRoles.label
                from PostgresqlRoles
                whereExists
                {
                    (this select PostgresqlUsers.id
                            from PostgresqlUsers
                            where PostgresqlUsers.roleId eq PostgresqlRoles.id
                            and PostgresqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select PostgresqlRoles.label and PostgresqlRoles.id
                from PostgresqlRoles
                where PostgresqlRoles.id eq
                {
                    (this select PostgresqlUsers.roleId
                            from PostgresqlUsers
                            where PostgresqlUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select PostgresqlRoles.label and PostgresqlRoles.id
                from PostgresqlRoles
                where PostgresqlRoles.id notEq 0
                and PostgresqlRoles.id eq
                {
                    (this select PostgresqlUsers.roleId
                            from PostgresqlUsers
                            where PostgresqlUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select PostgresqlRoles.label and PostgresqlRoles.id
                from PostgresqlRoles
                where PostgresqlRoles.id `in`
                {
                    (this select PostgresqlUsers.roleId
                            from PostgresqlUsers
                            where PostgresqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct PostgresqlRoles.label
                andCaseWhenExists {
            (this select PostgresqlUsers.id
                    from PostgresqlUsers
                    where PostgresqlUsers.roleId eq PostgresqlRoles.id
                    and PostgresqlUsers.id `in` userIds)
        } then true `else` false
                from PostgresqlRoles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select PostgresqlRoles.label
                from PostgresqlRoles
                orderByDescCaseWhenExists {
            (this select PostgresqlUsers.id
                    from PostgresqlUsers
                    where PostgresqlUsers.roleId eq PostgresqlRoles.id
                    and PostgresqlUsers.id `in` userIds)
        } then true `else` false
                andAsc PostgresqlRoles.label)
            .fetchAll()
}
