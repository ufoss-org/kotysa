/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*

abstract class SelectAndRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: SqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom tableRoles
                where tableRoles.label contains label1
                and tableRoles.label contains label2
                ).fetchAll()
}
