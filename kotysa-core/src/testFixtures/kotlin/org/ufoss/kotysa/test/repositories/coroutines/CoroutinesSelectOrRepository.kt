/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesSelectOrRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: CoroutinesSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractCoroutinesUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom tableRoles
                where tableRoles.label eq label1
                or tableRoles.label eq label2
                ).fetchAll()
}
