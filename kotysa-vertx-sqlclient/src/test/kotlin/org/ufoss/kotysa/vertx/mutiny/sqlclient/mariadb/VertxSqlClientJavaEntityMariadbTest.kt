/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMariadbTest : AbstractVertxSqlClientMariadbTest<JavaUserMariadbRepository>(),
    VertxJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: MutinySqlClient) :
    VertxJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
