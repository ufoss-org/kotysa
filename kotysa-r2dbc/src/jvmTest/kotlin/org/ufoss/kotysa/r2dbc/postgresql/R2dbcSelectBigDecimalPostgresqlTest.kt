/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalPostgresqlTest :
    AbstractR2dbcPostgresqlTest<BigDecimalRepositoryPostgresqlSelect>(),
    CoroutinesSelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        BigDecimalRepositoryPostgresqlSelect(sqlClient)
}

class BigDecimalRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)
