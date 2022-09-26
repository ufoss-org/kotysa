/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMssqlTest : AbstractVertxSqlClientMssqlTest<JavaUserMssqlRepository>(),
    VertxJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: MutinySqlClient) :
    VertxJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
