/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectByteArrayRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectByteArrayTest

class VertxSqlClientSelectByteArrayOracleTest : AbstractVertxSqlClientOracleTest<ByteArrayRepositoryOracleSelect>(),
    MutinySelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = ByteArrayRepositoryOracleSelect(sqlClient)
}

class ByteArrayRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
