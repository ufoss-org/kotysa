/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsByteaPostgresqlTest :
    AbstractJdbcPostgresqlTest<ByteArrayAsByteaRepositoryPostgresqlSelect>(),
    SelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayAsByteaRepositoryPostgresqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient)
}

class ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(sqlClient, PostgresqlByteArrayAsByteas)
