/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbByteArrays
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class JdbcSelectByteArrayAsBlobMariadbTest : AbstractJdbcMariadbTest<ByteArrayRepositoryMariadbSelect>(),
    SelectByteArrayTest<MariadbByteArrays, ByteArrayRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayRepositoryMariadbSelect(sqlClient)
}

class ByteArrayRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayRepository<MariadbByteArrays>(sqlClient, MariadbByteArrays)
