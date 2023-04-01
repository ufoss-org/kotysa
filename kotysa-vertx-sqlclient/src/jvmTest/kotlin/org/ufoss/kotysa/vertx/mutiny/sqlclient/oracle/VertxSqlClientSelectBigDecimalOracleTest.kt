/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalTest

class VertxSqlClientSelectBigDecimalOracleTest :
    AbstractVertxSqlClientOracleTest<SelectBigDecimalRepositoryOracleSelect>(),
    MutinySelectBigDecimalTest<OracleBigDecimals, SelectBigDecimalRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectBigDecimalRepositoryOracleSelect(sqlClient)
}

class SelectBigDecimalRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
