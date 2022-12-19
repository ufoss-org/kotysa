/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalPostgresqlTest : AbstractJdbcPostgresqlTest<SelectBigDecimalRepositoryPostgresqlSelect>(),
    SelectBigDecimalTest<PostgresqlBigDecimals, SelectBigDecimalRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectBigDecimalRepositoryPostgresqlSelect(sqlClient)
}

class SelectBigDecimalRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)
