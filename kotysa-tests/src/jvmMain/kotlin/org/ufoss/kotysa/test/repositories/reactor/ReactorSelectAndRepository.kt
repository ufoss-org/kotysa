/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Companies
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class ReactorSelectAndRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: ReactorSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractReactorUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom tableRoles
                where tableRoles.label contains label1
                and tableRoles.label contains label2
                ).fetchAll()
}
