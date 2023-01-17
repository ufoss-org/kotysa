/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.*

abstract class MutinyUpdateDeleteRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: MutinySqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractMutinyUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom tableUsers
                where tableUsers.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom tableUsers
                where tableUsers.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom tableUsers
                innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                where tableRoles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                where tableUsers.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                where tableUsers.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update tableUsers
                set tableUsers.alias eq newAlias
                where tableUsers.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update tableUsers
                set tableUsers.lastname eq newLastname
                innerJoin tableRoles on tableUsers.roleId eq tableRoles.id
                where tableRoles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update tableUsers
                set tableUsers.roleId eq tableUsers.roleId plus 2
                where tableUsers.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update tableUsers
                set tableUsers.roleId eq tableUsers.roleId minus 1
                where tableUsers.id eq userBboss.id
                ).execute()
}
