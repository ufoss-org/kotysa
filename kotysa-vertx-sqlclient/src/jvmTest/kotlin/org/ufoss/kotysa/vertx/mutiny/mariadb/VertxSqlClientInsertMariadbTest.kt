/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import io.vertx.mysqlclient.MySQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertTest

@Order(3)
class VertxSqlClientInsertMariadbTest : AbstractVertxSqlClientMariadbTest<RepositoryMariadbInsert>(),
    MutinyInsertTest<MariadbInts, MariadbLongs, MariadbCustomers, MariadbIntNonNullIds, MariadbLongNonNullIds,
            RepositoryMariadbInsert> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = RepositoryMariadbInsert(sqlClient)
    override val exceptionClass = MySQLException::class.java
}


class RepositoryMariadbInsert(sqlClient: MutinyVertxSqlClient) :
    MutinyInsertRepository<MariadbInts, MariadbLongs, MariadbCustomers, MariadbIntNonNullIds, MariadbLongNonNullIds>(
        sqlClient,
        MariadbInts,
        MariadbLongs,
        MariadbCustomers,
        MariadbIntNonNullIds,
        MariadbLongNonNullIds
    )
