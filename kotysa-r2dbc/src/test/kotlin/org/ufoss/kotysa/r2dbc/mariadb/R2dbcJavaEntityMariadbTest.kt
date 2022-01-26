/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MARIADB_JAVA_USER
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository

class R2dbcJavaEntityMariadbTest :
    AbstractR2dbcMariadbTest<JavaUserMariadbRepository>(),
    CoroutinesJavaEntityTest<MARIADB_JAVA_USER, JavaUserMariadbRepository, R2dbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserMariadbRepository(connection)
}


class JavaUserMariadbRepository(connection: Connection) :
    CoroutinesJavaUserRepository<MARIADB_JAVA_USER>(connection.sqlClient(mariadbTables), MARIADB_JAVA_USER)
