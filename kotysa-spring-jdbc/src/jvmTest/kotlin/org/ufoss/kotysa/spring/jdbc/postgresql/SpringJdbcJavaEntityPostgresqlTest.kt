/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    JavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = JavaUserPostgresqlRepository(jdbcOperations)
}


class JavaUserPostgresqlRepository(client: JdbcOperations) :
    JavaUserRepository<PostgresqlJavaUsers>(client.sqlClient(postgresqlTables), PostgresqlJavaUsers)
