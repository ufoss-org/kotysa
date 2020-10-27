/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.JavaUserRepository
import org.ufoss.kotysa.spring.jdbc.SpringJdbcJavaEntityTest
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.JavaUser


class SpringJdbcJavaEntityMysqlTest :
        AbstractSpringJdbcMysqlTest<JavaUserMysqlRepository>(), SpringJdbcJavaEntityTest<JavaUserMysqlRepository> {
    override val context = startContext<JavaUserMysqlRepository>()
    override val repository = getContextRepository<JavaUserMysqlRepository>()
}

private val tables =
        tables().mysql {
            table<JavaUser> {
                name = "java_users"
                column { it[JavaUser::getLogin].varchar {
                    size = 255
                } }
                        .primaryKey()
                column {
                    it[JavaUser::getFirstname].varchar {
                        name = "fname"
                        size = 255
                    }
                }
                column {
                    it[JavaUser::getLastname].varchar {
                        name = "lname"
                        size = 255
                    }
                }
                column { it[JavaUser::isAdmin].boolean() }
                column { it[JavaUser::getAlias1].varchar {
                    size = 255
                } }
                column { it[JavaUser::getAlias2].varchar {
                    size = 255
                } }
                column { it[JavaUser::getAlias3].varchar {
                    size = 255
                } }
            }
        }


class JavaUserMysqlRepository(client: JdbcOperations) : JavaUserRepository(client, tables)
