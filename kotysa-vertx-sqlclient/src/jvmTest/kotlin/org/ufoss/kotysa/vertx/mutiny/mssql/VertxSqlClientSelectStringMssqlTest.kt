/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringTest


class VertxSqlClientSelectStringMssqlTest :
    AbstractVertxSqlClientMssqlTest<UserRepositoryVertxSqlClientMssqlSelectString>(),
    MutinySelectStringTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies,
            UserRepositoryVertxSqlClientMssqlSelectString> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientMssqlSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientMssqlSelectString(sqlClient: MutinyVertxSqlClient) :
    MutinySelectStringRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
