/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class R2dbcJavaEntityMysqlTest :
    AbstractR2dbcMysqlTest<JavaUserMysqlRepository>(),
    CoroutinesJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
