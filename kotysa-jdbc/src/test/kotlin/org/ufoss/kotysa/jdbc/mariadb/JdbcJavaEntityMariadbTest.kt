/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository

class JdbcJavaEntityMariadbTest :
    AbstractJdbcMariadbTest<JavaUserMariadbRepository>(),
    JavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: JdbcSqlClient) :
    JavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
