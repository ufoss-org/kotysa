/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringTest


class VertxSqlClientSelectStringMysqlTest :
    AbstractVertxSqlClientMysqlTest<UserRepositoryVertxSqlClientMysqlSelectString>(),
    MutinySelectStringTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies,
            UserRepositoryVertxSqlClientMysqlSelectString> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientMysqlSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientMysqlSelectString(sqlClient: MutinyVertxSqlClient) :
    MutinySelectStringRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
