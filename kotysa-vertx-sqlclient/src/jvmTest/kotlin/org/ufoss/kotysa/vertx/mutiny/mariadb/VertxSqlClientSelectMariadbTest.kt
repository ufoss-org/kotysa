/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import io.vertx.mysqlclient.MySQLException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTest

class VertxSqlClientSelectMariadbTest : AbstractVertxSqlClientMariadbTest<UserRepositoryMariadbSelect>(),
    MutinySelectTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryMariadbSelect> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMariadbSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(MySQLException::class.java)
    }
}

class UserRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
