/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectTest

class VertxSqlClientSelectMssqlTest : AbstractVertxSqlClientMssqlTest<UserRepositoryMssqlSelect>(),
    MutinySelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryMssqlSelect> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryMssqlSelect(sqlClient)
}

class UserRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
