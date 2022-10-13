/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*

abstract class SelectBooleanRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: SqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectAllByIsAdminEq(value: Boolean) =
        (sqlClient selectFrom tableUsers
                where tableUsers.isAdmin eq value
                ).fetchAll()
}
