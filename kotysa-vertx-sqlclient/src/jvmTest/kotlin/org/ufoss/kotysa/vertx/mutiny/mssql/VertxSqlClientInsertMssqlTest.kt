/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import io.vertx.mssqlclient.MSSQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertTest

@Order(3)
class VertxSqlClientInsertMssqlTest : AbstractVertxSqlClientMssqlTest<RepositoryMssqlInsert>(),
    MutinyInsertTest<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds,
            RepositoryMssqlInsert> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = RepositoryMssqlInsert(sqlClient)
    override val exceptionClass = MSSQLException::class.java
}


class RepositoryMssqlInsert(sqlClient: MutinyVertxSqlClient) :
    MutinyInsertRepository<MssqlInts, MssqlLongs, MssqlCustomers, MssqlIntNonNullIds, MssqlLongNonNullIds>(
        sqlClient,
        MssqlInts,
        MssqlLongs,
        MssqlCustomers,
        MssqlIntNonNullIds,
        MssqlLongNonNullIds
    )
