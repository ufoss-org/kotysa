/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository

class R2dbcJavaEntityMariadbTest :
    AbstractR2dbcMariadbTest<JavaUserMariadbRepository>(),
    CoroutinesJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
