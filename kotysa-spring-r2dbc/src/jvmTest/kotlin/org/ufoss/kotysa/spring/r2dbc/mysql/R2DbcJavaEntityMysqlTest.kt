/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityMysqlTest :
    AbstractR2dbcMysqlTest<JavaUserMysqlRepository>(),
    ReactorJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
