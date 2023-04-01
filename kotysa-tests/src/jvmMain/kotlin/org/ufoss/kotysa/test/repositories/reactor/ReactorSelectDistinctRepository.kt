/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class ReactorSelectDistinctRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: ReactorSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractReactorUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct tableRoles.label
                from tableRoles
                ).fetchAll()
}
