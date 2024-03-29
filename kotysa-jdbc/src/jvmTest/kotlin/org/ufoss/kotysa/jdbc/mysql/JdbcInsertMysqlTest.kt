/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest
import java.sql.SQLIntegrityConstraintViolationException

@Order(3)
class JdbcInsertMysqlTest : AbstractJdbcMysqlTest<RepositoryMysqlInsert>(),
    InsertTest<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds,
            RepositoryMysqlInsert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryMysqlInsert(sqlClient)
    override val exceptionClass = SQLIntegrityConstraintViolationException::class
}

class RepositoryMysqlInsert(sqlClient: JdbcSqlClient) :
    InsertRepository<MysqlInts, MysqlLongs, MysqlCustomers, MysqlIntNonNullIds, MysqlLongNonNullIds>(
        sqlClient,
        MysqlInts,
        MysqlLongs,
        MysqlCustomers,
        MysqlIntNonNullIds,
        MysqlLongNonNullIds
    )
