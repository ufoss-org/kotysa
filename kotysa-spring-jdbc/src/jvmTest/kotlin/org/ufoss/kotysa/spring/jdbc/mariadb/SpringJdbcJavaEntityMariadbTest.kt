/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityMariadbTest :
    AbstractSpringJdbcMariadbTest<JavaUserMariadbRepository>(),
    JavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = JavaUserMariadbRepository(jdbcOperations)
}


class JavaUserMariadbRepository(client: JdbcOperations) :
    JavaUserRepository<MariadbJavaUsers>(client.sqlClient(mariadbTables), MariadbJavaUsers)
