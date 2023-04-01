/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class MutinySelectDistinctRepository<T : Roles, U : Users, V : UserRoles>(
    sqlClient: MutinySqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
) : AbstractMutinyUserRepository<T, U, V>(sqlClient, tableRoles, tableUsers, tableUserRoles) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct tableRoles.label
                from tableRoles
                ).fetchAll()
}
