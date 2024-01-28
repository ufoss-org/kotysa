/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectBooleanRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: CoroutinesSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractCoroutinesUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectAllByIsAdminEq(value: Boolean) =
        (sqlClient selectFrom tableUsers
                where tableUsers.isAdmin eq value
                ).fetchAll()
}
