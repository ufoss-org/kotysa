/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectByteArrayRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectByteArrayTest

class VertxSqlClientSelectByteArrayOracleTest : AbstractVertxSqlClientOracleTest<ByteArrayRepositoryOracleSelect>(),
    MutinySelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = ByteArrayRepositoryOracleSelect(sqlClient)
}

class ByteArrayRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
