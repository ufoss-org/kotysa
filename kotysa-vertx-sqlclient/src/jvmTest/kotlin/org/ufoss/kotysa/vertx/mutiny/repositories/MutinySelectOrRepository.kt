/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.test.Companies
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class MutinySelectOrRepository<T : Roles, U : Users, V : UserRoles, W: Companies>(
    sqlClient: MutinySqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractMutinyUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom tableRoles
                where tableRoles.label eq label1
                or tableRoles.label eq label2
                ).fetchAll()
}
