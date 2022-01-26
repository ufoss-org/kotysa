/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository


class R2dbcJavaEntityPostgresqlTest : AbstractR2dbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    CoroutinesJavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserPostgresqlRepository(connection)
}


class JavaUserPostgresqlRepository(connection: Connection) :
    CoroutinesJavaUserRepository<POSTGRESQL_JAVA_USER>(connection.sqlClient(postgresqlTables), POSTGRESQL_JAVA_USER)
