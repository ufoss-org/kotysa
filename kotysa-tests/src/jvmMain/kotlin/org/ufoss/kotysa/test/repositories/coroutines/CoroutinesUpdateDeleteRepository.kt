/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesUpdateDeleteRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: CoroutinesSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractCoroutinesUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    suspend fun deleteUserById(id: Int) =
        (sqlClient deleteFrom tableUsers
                where tableUsers.id eq id
                and tableUsers.id eq id
                ).execute()

    suspend fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom tableUsers
                where tableUsers.id `in` ids
                ).execute()

    suspend fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom tableUsers
                innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                where tableRoles.label eq roleLabel
                ).execute()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                where tableUsers.id eq userJdoe.id
                or tableUsers.id eq userJdoe.id
                ).execute()

    suspend fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                where tableUsers.id `in` ids
                ).execute()

    suspend fun updateAlias(newAlias: String?) =
        (sqlClient update tableUsers
                set tableUsers.alias eq newAlias
                where tableUsers.id eq userBboss.id
                ).execute()

    suspend fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                where tableRoles.label eq roleLabel
                ).execute()

    suspend fun updateAndIncrementRoleId() =
        (sqlClient update tableUsers
                set tableUsers.roleId eq tableUsers.roleId plus 2
                where tableUsers.id eq userJdoe.id
                ).execute()

    suspend fun updateAndDecrementRoleId() =
        (sqlClient update tableUsers
                set tableUsers.roleId eq tableUsers.roleId minus 1
                where tableUsers.id eq userBboss.id
                ).execute()
}
