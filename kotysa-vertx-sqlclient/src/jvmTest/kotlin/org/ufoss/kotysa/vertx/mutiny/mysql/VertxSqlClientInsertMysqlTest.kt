/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import io.vertx.mysqlclient.MySQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertTest

@Order(3)
class VertxSqlClientInsertMysqlTest : AbstractVertxSqlClientMysqlTest<RepositoryMysqlInsert>(),
    MutinyInsertTest<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds,
            RepositoryMysqlInsert> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = RepositoryMysqlInsert(sqlClient)
    override val exceptionClass = MySQLException::class.java
}

class RepositoryMysqlInsert(sqlClient: MutinyVertxSqlClient) :
    MutinyInsertRepository<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds>(
        sqlClient,
        MysqlInts,
        MysqlLongs,
        MysqlCustomers,
        MysqlIntNonNullIds,
        MysqlLongNonNullIds
    )
