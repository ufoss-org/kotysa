/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class R2dbcSelectIntMysqlTest : AbstractR2dbcMysqlTest<SelectIntRepositoryMysqlSelect>(),
    CoroutinesSelectIntTest<MysqlInts, SelectIntRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntRepositoryMysqlSelect(sqlClient)
}

class SelectIntRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntRepository<MysqlInts>(sqlClient, MysqlInts)
