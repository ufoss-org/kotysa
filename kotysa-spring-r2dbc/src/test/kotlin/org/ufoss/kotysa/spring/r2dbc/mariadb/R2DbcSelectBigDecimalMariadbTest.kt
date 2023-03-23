/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMariadbTest : AbstractR2dbcMariadbTest<BigDecimalMariadbRepository>(),
    ReactorSelectBigDecimalTest<MariadbBigDecimals, BigDecimalMariadbRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        BigDecimalMariadbRepository(sqlClient)
}

class BigDecimalMariadbRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorSelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)
