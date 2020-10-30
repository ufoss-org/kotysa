package com.sample

import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.tables

val dataConfig = configuration {
    beans {
        val tables = if (profiles.contains("test")) h2Tables else postgresqlTables
        bean { ref<DatabaseClient>().coSqlClient(tables) }
        bean<RoleRepository>()
        bean<UserRepository>()
    }
    if (profiles.any { profile -> "dev" == profile || "test" == profile }) {
        listener<ApplicationReadyEvent> {
            runBlocking {
                ref<RoleRepository>().init()
                ref<UserRepository>().init()
            }
        }
    }
    if (profiles.contains("test")) {
        r2dbc {
            url = "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
        }
    } else {
        r2dbc {
            url = "r2dbc:tc:postgresql:///db?TC_IMAGE_TAG=13.0-alpine"
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
                column { it[User::id].autoIncrementInteger() }
                        .primaryKey("PK_users")
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
    webFlux {
        port = if (profiles.any { profile -> "dev" == profile || "test" == profile }) 8181 else 8080
        codecs {
            jackson()
        }
    }
}
