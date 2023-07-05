/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: CoroutinesSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractCoroutinesUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom tableUsers
                ).fetchOne()

    fun selectAllMappedToDto() =
        (sqlClient selectAndBuild {
            UserDto("${it[tableUsers.firstname]} ${it[tableUsers.lastname]}", it[tableUsers.isAdmin]!!,
                it[tableUsers.alias])
        }
                from tableUsers
                ).fetchAll()

    fun selectWithCascadeInnerJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!) }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                innerJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                ).fetchAll()

    fun selectWithCascadeLeftJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!) }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                leftJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                ).fetchAll()

    fun selectWithCascadeRightJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!) }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                rightJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                ).fetchAll()

    fun selectWithCascadeFullJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!) }
                from tableUsers innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                fullJoin tableUserRoles on tableRoles.id eq tableUserRoles.roleId
                ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[tableUsers.lastname]!!, it[tableRoles.label]!!) }
                from tableUsers and tableRoles
                where tableUsers.roleId eq tableRoles.id
                ).fetchAll()

    fun selectAllIn(aliases: Collection<String>) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias `in` aliases
                ).fetchAll()

    suspend fun selectOneById(id: Int) =
        (sqlClient select tableUsers
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameById(id: Int) =
        (sqlClient select tableUsers.firstname
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    suspend fun selectAliasById(id: Int) =
        (sqlClient select tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOneOrNull()

    suspend fun selectFirstnameAndAliasById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    fun selectAllFirstnameAndAlias() =
        (sqlClient select tableUsers.firstname and tableUsers.alias
                from tableUsers
                ).fetchAll()

    suspend fun selectFirstnameAndLastnameAndAliasById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.lastname and tableUsers.alias
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    suspend fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
        (sqlClient select tableUsers.firstname and tableUsers.lastname and tableUsers.alias and tableUsers.isAdmin
                from tableUsers
                where tableUsers.id eq id
                ).fetchOne()

    suspend fun countAllUsersAndAliases() =
        (sqlClient selectCount tableUsers.id
                andCount tableUsers.alias
                from tableUsers
                ).fetchOne()

    suspend fun selectRoleLabelFromUserId(userId: Int) =
        (sqlClient select tableRoles.label
                from tableRoles innerJoin tableUsers on tableRoles.id eq tableUsers.roleId
                where tableUsers.id eq userId)
            .fetchOne()

    suspend fun countAllUsers() = sqlClient selectCountAllFrom tableUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
        (sqlClient select tableRoles.label
                from tableUserRoles innerJoin tableRoles on tableUserRoles.roleId eq tableRoles.id
                where tableUserRoles.userId eq userId)
            .fetchAll()
}
