/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users
import org.ufoss.kotysa.test.roleAdmin

abstract class SubQueryRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: SqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select tableUsers.firstname and {
            (this select tableRoles.label
                    from tableRoles
                    where tableRoles.id eq tableUsers.roleId
                    and tableRoles.label eq roleAdmin.label)
        }
                from tableUsers
                where tableUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select tableRoles.label
                from tableRoles
                whereExists
                {
                    (this select tableUsers.id
                            from tableUsers
                            where tableUsers.roleId eq tableRoles.id
                            and tableUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectRoleLabelWhereEqUserSubQuery(userId: Int) =
        (sqlClient select tableRoles.label and tableRoles.id
                from tableRoles
                where tableRoles.id eq
                {
                    (this select tableUsers.roleId
                            from tableUsers
                            where tableUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelAndEqUserSubQuery(userId: Int) =
        (sqlClient select tableRoles.label and tableRoles.id
                from tableRoles
                where tableRoles.id notEq 0
                and tableRoles.id eq
                {
                    (this select tableUsers.roleId
                            from tableUsers
                            where tableUsers.id eq userId)
                })
            .fetchOne()

    fun selectRoleLabelWhereInUserSubQuery(userIds: List<Int>) =
        (sqlClient select tableRoles.label and tableRoles.id
                from tableRoles
                where tableRoles.id `in`
                {
                    (this select tableUsers.roleId
                            from tableUsers
                            where tableUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectDistinct tableRoles.label
                andCaseWhenExists {
            (this select tableUsers.id
                    from tableUsers
                    where tableUsers.roleId eq tableRoles.id
                    and tableUsers.id `in` userIds)
        } then true `else` false
                from tableRoles)
            .fetchAll()

    fun selectOrderByCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient select tableRoles.label
                from tableRoles
                orderByDescCaseWhenExists {
            (this select tableUsers.id
                    from tableUsers
                    where tableUsers.roleId eq tableRoles.id
                    and tableUsers.id `in` userIds)
        } then true `else` false
                andAsc tableRoles.label)
            .fetchAll()
}
