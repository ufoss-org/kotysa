/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMysqlTest : AbstractVertxSqlClientMysqlTest<JavaUserMysqlRepository>(),
    VertxJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: MutinySqlClient) :
    VertxJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
