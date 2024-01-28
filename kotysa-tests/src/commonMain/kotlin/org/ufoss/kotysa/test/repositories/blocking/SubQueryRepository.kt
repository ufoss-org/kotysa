/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.SqlClientSubQuery
import org.ufoss.kotysa.test.*

abstract class SubQueryRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: SqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

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

    fun selectRoleLabelAndEqUserSubQueryWithAnd(userId: Int) =
        (sqlClient select tableRoles.label and tableRoles.id
                from tableRoles
                where tableRoles.id eq
                {
                    (this select tableUsers.roleId
                            from tableUsers
                            where tableUsers.id eq userId)
                } and tableRoles.id notEq 0)
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

    fun selectStarConditionalSyntax(params: Int = 0) =
        (sqlClient selectStarFrom { selectStarConditionalSyntax(params, this) } `as` "derivedTable"
                ).fetchAll()

    private fun selectStarConditionalSyntax(
        params: Int,
        subQueryScope: SqlClientSubQuery.Scope
    ): SqlClientSubQuery.Return<List<Any?>> {
        val selects = subQueryScope.selects()
        selects.select(tableUsers.firstname)
        if (params > 0) {
            selects.select(tableUsers.lastname)
        }
        if (params > 1) {
            selects.select(tableRoles.label)
        }

        val froms = selects.froms()
        froms.from(tableUsers)
        if (params > 0) {
            froms.from(tableUserRoles)
        }
        if (params > 1) {
            froms.from(tableRoles)
        }

        val wheres = froms.wheres()
        wheres.where(tableUsers.id).sup(0)
        if (params > 0) {
            wheres.where(tableUserRoles.userId).eq(tableUsers.id)
        }
        if (params > 1) {
            wheres.where(tableRoles.id).eq(tableUserRoles.roleId)
        }

        val groupsBy = wheres.groupsBy()
        groupsBy.groupBy(tableUsers.firstname)
        if (params > 0) {
            groupsBy.groupBy(tableUsers.lastname)
        }
        if (params > 1) {
            groupsBy.groupBy(tableRoles.label)
        }
        return groupsBy
    }
}
