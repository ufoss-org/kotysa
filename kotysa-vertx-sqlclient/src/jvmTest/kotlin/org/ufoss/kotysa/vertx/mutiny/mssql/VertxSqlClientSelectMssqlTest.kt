/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTest

class VertxSqlClientSelectMssqlTest : AbstractVertxSqlClientMssqlTest<UserRepositoryMssqlSelect>(),
    MutinySelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryMssqlSelect> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMssqlSelect(sqlClient)
}

class UserRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
