/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringTest

class VertxSqlClientSelectStringOracleTest :
    AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectString>(),
    MutinySelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryVertxSqlClientOracleSelectString> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientOracleSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectString(sqlClient: MutinyVertxSqlClient) :
    MutinySelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
