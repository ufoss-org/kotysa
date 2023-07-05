/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import io.vertx.mysqlclient.MySQLException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectTest

class VertxSqlClientSelectMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryMysqlSelect>(),
    MutinySelectTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryMysqlSelect> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryMysqlSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(MySQLException::class.java)
    }
}

class UserRepositoryMysqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
