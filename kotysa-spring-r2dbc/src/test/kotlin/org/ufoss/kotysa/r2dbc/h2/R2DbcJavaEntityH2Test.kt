/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables


class R2DbcJavaEntityH2Test :
        AbstractR2dbcH2Test<JavaUserH2Repository>(), R2dbcJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository> {

    @BeforeAll
    fun beforeAll() {
        context = startContext<JavaUserH2Repository>()
        repository = getContextRepository()
    }
}


class JavaUserH2Repository(client: DatabaseClient)
    : R2dbcJavaUserRepository<H2_JAVA_USER>(client.sqlClient(h2Tables), H2_JAVA_USER)
