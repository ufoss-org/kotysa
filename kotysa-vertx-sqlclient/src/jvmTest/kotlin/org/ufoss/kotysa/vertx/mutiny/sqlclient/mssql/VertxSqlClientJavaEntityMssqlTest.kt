/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMssqlTest : AbstractVertxSqlClientMssqlTest<JavaUserMssqlRepository>(),
    MutinyJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
