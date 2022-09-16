/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*

class SpringJdbcSubQueryH2Test : AbstractSpringJdbcH2Test<UserRepositoryJdbcH2SubQuery>() {
    override val context = startContext<UserRepositoryJdbcH2SubQuery>()
    override val repository = getContextRepository<UserRepositoryJdbcH2SubQuery>()

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelWhereEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role`() {
        assertThat(repository.selectRoleLabelAndEqUserSubQuery(userJdoe.id))
            .isEqualTo(Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereInUserSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectOrderByCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(3)
            .containsExactly(
                roleAdmin.label,
                roleUser.label,
                roleGod.label,
            )
    }
}


class UserRepositoryJdbcH2SubQuery(dbClient: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(dbClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select H2Users.firstname and {
            (this select H2Roles.label
                    from H2Roles
                    where H2Roles.id eq H2Users.roleId
                    and H2Roles.label eq roleAdmin.label)
        }
                from H2Users
                where H2Users.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select H2Roles.label
                from H2Roles
                whereExists
                {
                    (this select H2Users.id
                            from H2Users
                            where H2Users.roleId eq H2Roles.id
                            and H2Users.id `in` userIds)
                })
            .fetchAll()

    fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select H2Roles.label and H2Roles.id
                from H2Roles
                where H2Roles.id eq
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select H2Roles.label and H2Roles.id
                from H2Roles
                where H2Roles.id notEq 0
                and H2Roles.id eq
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select H2Roles.label and H2Roles.id
                from H2Roles
                where H2Roles.id `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct H2Roles.label
                andCaseWhenExists {
            (this select H2Users.id
                    from H2Users
                    where H2Users.roleId eq H2Roles.id
                    and H2Users.id `in` userIds)
        } then true `else` false
                from H2Roles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select H2Roles.label
                from H2Roles
                orderByDescCaseWhenExists {
            (this select H2Users.id
                    from H2Users
                    where H2Users.roleId eq H2Roles.id
                    and H2Users.id `in` userIds)
        } then true `else` false
                andAsc H2Roles.label)
            .fetchAll()
}
