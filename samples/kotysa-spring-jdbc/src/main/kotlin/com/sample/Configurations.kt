package com.sample

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.jdbc.jdbc
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.tables

val dataConfig = configuration {
    beans {
        val tables = if (profiles.contains("test")) h2Tables else postgresqlTables
        bean { ref<JdbcOperations>().sqlClient(tables) }
        bean<RoleRepository>()
        bean<UserRepository>()
    }
    if (profiles.any { profile -> "dev" == profile || "test" == profile }) {
        listener<ApplicationReadyEvent> {
            ref<RoleRepository>().init()
            ref<UserRepository>().init()
        }
    }
    if (profiles.contains("test")) {
        jdbc {
            url = "jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
        }
    } else {
        jdbc {
            url = "jdbc:tc:postgresql:13.0-alpine///db"
            username = "test"
            password = "test"
        }
    }
}

internal val h2Tables =
        tables().h2 {
            table<Role> {
                name = "roles"
                column { it[Role::id].uuid() }.primaryKey()
                column { it[Role::label].varchar() }
            }
            table<User> {
                name = "users"
                column { it[User::id].autoIncrementInteger() }.primaryKey("PK_users")
                column {
                    it[User::firstname].varchar {
                        name = "fname"
                    }
                }
                column {
                    it[User::lastname].varchar {
                        name = "lname"
                    }
                }
                column {
                    it[User::isAdmin].boolean {
                        name = "is_admin"
                    }
                }
                column {
                    it[User::roleId].uuid {
                        name = "role_id"
                    }
                }.foreignKey<Role>("FK_users_roles")
                column {
                    it[User::creationTime].dateTime {
                        name = "creation_time"
                    }
                }
                column { it[User::alias].varchar() }
            }
        }

internal val postgresqlTables =
        tables().postgresql {
            table<Role> {
                name = "roles"
                column { it[Role::id].uuid() }.primaryKey()
                column { it[Role::label].varchar() }
            }
            table<User> {
                name = "users"
                column { it[User::id].serial() }.primaryKey("PK_users")
                column {
                    it[User::firstname].varchar {
                        name = "fname"
                    }
                }
                column {
                    it[User::lastname].varchar {
                        name = "lname"
                    }
                }
                column {
                    it[User::isAdmin].boolean {
                        name = "is_admin"
                    }
                }
                column {
                    it[User::roleId].uuid {
                        name = "role_id"
                    }
                }.foreignKey<Role>("FK_users_roles")
                column {
                    it[User::creationTime].timestamp {
                        name = "creation_time"
                    }
                }
                column { it[User::alias].varchar() }
            }
        }

val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean(::routes)
    }
    webMvc {
        port = if (profiles.any { profile -> "dev" == profile || "test" == profile }) 8181 else 8080
        converters {
            jackson()
        }
    }
}
