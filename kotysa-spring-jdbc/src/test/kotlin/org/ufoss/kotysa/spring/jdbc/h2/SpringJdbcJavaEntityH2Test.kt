/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityH2Test :
        AbstractSpringJdbcH2Test<JavaUserH2Repository>(), JdbcJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository> {
    override val context = startContext<JavaUserH2Repository>()
    override val repository = getContextRepository<JavaUserH2Repository>()
}


class JavaUserH2Repository(client: JdbcOperations)
    : JavaUserRepository<H2_JAVA_USER>(client.sqlClient(h2Tables), H2_JAVA_USER)
