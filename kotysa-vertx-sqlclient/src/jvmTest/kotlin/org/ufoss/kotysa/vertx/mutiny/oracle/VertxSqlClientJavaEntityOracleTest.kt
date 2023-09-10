/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaUserRepository

class VertxSqlClientJavaEntityOracleTest : AbstractVertxSqlClientOracleTest<JavaUserOracleRepository>(),
    MutinyJavaEntityTest<OracleJavaUsers, JavaUserOracleRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = JavaUserOracleRepository(sqlClient)
}

class JavaUserOracleRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<OracleJavaUsers>(sqlClient, OracleJavaUsers)
