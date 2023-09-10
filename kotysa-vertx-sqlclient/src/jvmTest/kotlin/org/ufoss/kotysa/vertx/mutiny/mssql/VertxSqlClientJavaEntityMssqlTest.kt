/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientJavaEntityMssqlTest : AbstractVertxSqlClientMssqlTest<JavaUserMssqlRepository>(),
    MutinyJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
