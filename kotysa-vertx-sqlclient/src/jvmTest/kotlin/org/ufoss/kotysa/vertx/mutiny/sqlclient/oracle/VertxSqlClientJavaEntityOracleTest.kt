/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaUserRepository

class VertxSqlClientJavaEntityOracleTest : AbstractVertxSqlClientOracleTest<JavaUserOracleRepository>(),
    MutinyJavaEntityTest<OracleJavaUsers, JavaUserOracleRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserOracleRepository(sqlClient)
}

class JavaUserOracleRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<OracleJavaUsers>(sqlClient, OracleJavaUsers)
