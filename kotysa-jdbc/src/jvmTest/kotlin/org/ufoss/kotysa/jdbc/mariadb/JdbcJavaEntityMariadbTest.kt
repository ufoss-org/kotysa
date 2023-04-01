/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository

class JdbcJavaEntityMariadbTest :
    AbstractJdbcMariadbTest<JavaUserMariadbRepository>(),
    JavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: JdbcSqlClient) :
    JavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
