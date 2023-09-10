/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalTest

class VertxSqlClientSelectBigDecimalOracleTest :
    AbstractVertxSqlClientOracleTest<SelectBigDecimalRepositoryOracleSelect>(),
    MutinySelectBigDecimalTest<OracleBigDecimals, SelectBigDecimalRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectBigDecimalRepositoryOracleSelect(sqlClient)
}

class SelectBigDecimalRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
