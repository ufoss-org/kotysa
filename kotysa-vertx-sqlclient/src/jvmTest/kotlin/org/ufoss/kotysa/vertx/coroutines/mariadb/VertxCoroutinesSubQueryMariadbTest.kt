/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSubQueryMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryVertxCoroutinesMariadbSubQuery>(),
    CoroutinesSubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryVertxCoroutinesMariadbSubQuery, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryVertxCoroutinesMariadbSubQuery(sqlClient)
}

class UserRepositoryVertxCoroutinesMariadbSubQuery(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
