/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsBinaryMariadbTest : AbstractJdbcMariadbTest<ByteArrayAsBinaryRepositoryMariadbSelect>(),
    SelectByteArrayAsBinaryTest<MariadbByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayAsBinaryRepository<MariadbByteArrayAsBinaries>(sqlClient, MariadbByteArrayAsBinaries)
