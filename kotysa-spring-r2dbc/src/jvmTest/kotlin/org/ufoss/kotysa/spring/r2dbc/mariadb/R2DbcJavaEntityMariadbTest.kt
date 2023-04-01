/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityMysqlTest :
    AbstractR2dbcMariadbTest<JavaUserMariadbRepository>(),
    ReactorJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: MariadbReactorSqlClient) :
    ReactorJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
