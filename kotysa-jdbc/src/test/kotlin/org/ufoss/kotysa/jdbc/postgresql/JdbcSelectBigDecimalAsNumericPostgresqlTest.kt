/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class JdbcSelectBigDecimalAsNumericPostgresqlTest : AbstractJdbcPostgresqlTest<SelectBigDecimalAsNumericRepositoryPostgresqlSelect>(),
    SelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, SelectBigDecimalAsNumericRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectBigDecimalAsNumericRepositoryPostgresqlSelect(sqlClient)
}

class SelectBigDecimalAsNumericRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(sqlClient, PostgresqlBigDecimalAsNumerics)
