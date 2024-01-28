/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import reactor.core.publisher.Flux

abstract class ReactorSelectRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: ReactorSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractReactorUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectOneNonUnique() =
        (sqlClient selectFrom tableUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto(
                "${it[tableUsers.firstname]} ${it[tableUsers.lastname]}", it[tableUsers.isAdmin]!!,
                it[tableUsers.alias]
            )
        }
                from tableUsers
                ).fetchAll()

    fun selectWithCascadeInnerJoin() =
        (sqlClient selectAndBuild {
            CompleteUserDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!, it[tableCompanies.name]!!)
        }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                innerJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                innerJoin tableCompanies on tableUsers.companyId eq tableCompanies.id
                ).fetchAll()

    fun selectWithCascadeLeftJoin() =
        (sqlClient selectAndBuild {
            CompleteUserDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!, it[tableCompanies.name]!!)
        }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                leftJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                innerJoin tableCompanies on tableUsers.companyId eq tableCompanies.id
                ).fetchAll()

    fun selectWithCascadeRightJoin() =
        (sqlClient selectAndBuild {
            CompleteUserDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!, it[tableCompanies.name]!!)
        }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                rightJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                innerJoin tableCompanies on tableUsers.companyId eq tableCompanies.id
                ).fetchAll()

    fun selectWithCascadeFullJoin() =
        (sqlClient selectAndBuild {
            CompleteUserDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!, it[tableCompanies.name]!!)
        }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                fullJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                innerJoin tableCompanies on tableUsers.companyId eq tableCompanies.id
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild {
            CompleteUserDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!, it[tableCompanies.name]!!)
        }
                from tableUsers and tableRoles and tableCompanies
                where tableUsers.roleId eq tableRoles.id
                and tableUsers.companyId eq tableCompanies.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias `in` aliases
                ).fetchAll()

    fun selectOneById(id: Int) =
        (sqlClient select tableUsers
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectFirstnameById(id: Int) =
        (sqlClient select tableUsers.firstname
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectAliasById(id: Int) =
        (sqlClient select tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select tableUsers.firstname and tableUsers.alias
                from tableUsers
                ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.lastname and tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.lastname and tableUsers.alias and tableUsers.isAdmin
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun countAllUsersAndAliases() =
        (sqlClient selectCount tableUsers.id
                andCount tableUsers.alias
                from tableUsers
                ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select tableRoles.label
                from tableRoles innerJoin tableUsers on tableRoles.id eq tableUsers.roleId
                where tableUsers.id eq userId)
            .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom tableUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select tableRoles.label
                from tableUserRoles innerJoin tableRoles on tableUserRoles.roleId eq tableRoles.id
                where tableUserRoles.userId eq userId)
            .fetchAll()

    fun selectConditionalSyntax(params: Int = 0): Flux<List<Any?>> {
        val selects = sqlClient.selects()
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

        val ordersBy = groupsBy.ordersBy()
        if (params > 0) {
            ordersBy.orderByDesc(tableUsers.lastname)
        }
        if (params > 1) {
            ordersBy.orderByAsc(tableRoles.label)
        }
        ordersBy.orderByAsc(tableUsers.firstname)
        return ordersBy.fetchAll()
    }
}
