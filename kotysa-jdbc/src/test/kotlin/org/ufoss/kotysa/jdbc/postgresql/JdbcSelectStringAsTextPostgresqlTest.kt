/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class JdbcSelectStringAsTextPostgresqlTest : AbstractJdbcPostgresqlTest<StringAsTextRepositoryPostgresqlSelect>(),
    SelectStringAsTextTest<PostgresqlTexts, StringAsTextRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        StringAsTextRepositoryPostgresqlSelect(sqlClient)
}

class StringAsTextRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsTextRepository<PostgresqlTexts>(sqlClient, PostgresqlTexts)
