/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class ReactorSelectBooleanRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: ReactorSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractReactorUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectAllByIsAdminEq(value: Boolean) =
        (sqlClient selectFrom tableUsers
                where tableUsers.isAdmin eq value
                ).fetchAll()
}
