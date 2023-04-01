/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMariadbTest : AbstractR2dbcMariadbTest<BigDecimalAsNumericMariadbRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericMariadbRepository,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        BigDecimalAsNumericMariadbRepository(sqlClient)
}

class BigDecimalAsNumericMariadbRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorSelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)
