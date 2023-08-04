/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMysqlTest : AbstractR2dbcMysqlTest<ByteArrayRepositoryMysqlSelect>(),
    ReactorSelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ByteArrayRepositoryMysqlSelect(sqlClient)
}

class ByteArrayRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(sqlClient, MysqlByteArrayAsBinaries)
