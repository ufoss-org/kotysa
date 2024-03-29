/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalMariadbTest : AbstractR2dbcMariadbTest<BigDecimalRepositoryMariadbSelect>(),
    CoroutinesSelectBigDecimalTest<MariadbBigDecimals, BigDecimalRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalRepositoryMariadbSelect(sqlClient)
}

class BigDecimalRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)
