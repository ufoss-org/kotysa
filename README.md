[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-blue.svg)](http://unlicense.org/)
[![Download](https://api.bintray.com/packages/ufoss/ufoss/kotysa/images/download.svg) ](https://bintray.com/ufoss/ufoss/kotysa/_latestVersion)

# Kotysa

The idiomatic way to write **Ko**tlin **ty**pe-**sa**fe SQL.

## Easy to use : 3 steps only
### step 1 -> Create Kotlin entities

data classes are great for that !

```kotlin
data class Role(
        val label: String,
        val id: UUID = UUID.randomUUID()
)

data class User(
        val firstname: String,
        val roleId: UUID,
        val alias: String? = null,
        val id: UUID = UUID.randomUUID()
)
```

### step 2 -> Describe database model with [type-safe DSL](docs/table-modelling.md), based on these entities

```kotlin
val tables =
        tables().postgresql { // choose database type
            table<Role> {
                name = "roles"
                column { it[Role::id].uuid() }.primaryKey()
                column { it[Role::label].varchar() }
            }
            table<User> {
                name = "users"
                column { it[User::id].uuid() }.primaryKey()
                column { it[User::firstname].varchar().name("first-name") }
                column { it[User::roleId].uuid() }.foreignKey<Role>()
                column { it[User::alias].varchar() }
            }
        }
```

### step 3 -> Write SQL queries with [type-safe SqlClient DSL](docs/sql-queries.md)

Kotysa will generate SQL for you !

```kotlin
// return all admin users
val admins = sqlClient.select<User>()
        .innerJoin<Role>().on { it[User::roleId] }
        .where { it[Role::label] eq "admin" }
        .fetchAll()
```

**No annotations, no code generation, just regular Kotlin code ! No JPA, just pure SQL !**

Kotysa provides [Kotlin Coroutines first class support with R2DBC](kotysa-spring-r2dbc/README.md#coroutines-first-class-support)

Kotysa is **not production ready yet**, some key features are still missing. Regular early releases will provide new features (see [next milestones](https://github.com/ufoss-org/kotysa/milestones)).

## Getting started

Kotysa is agnostic from Sql Engine (SqLite on Android and R2DBC. JDBC in future) :
* use Kotysa with [Spring data R2DBC](kotysa-spring-r2dbc/README.md)
* use Kotysa with [SqLite on Android](kotysa-android/README.md)

### Samples

See sample projects [here](samples).

[Real world sample project with R2DBC](https://github.com/pull-vert/demo-kotlin) is a Spring Boot Reactive web application with a R2DBC backend accessed via Kotysa, HTTP2, JWT based Security, Bean validation, RestDoc...

## Build from sources

* Clone Kotysa repository
* Use a JDK 1.8
* You need a local docker, like docker-ce. Some integration tests use testcontainers to start real databases like PostgreSQL
* Kotysa can be easily built with the gradle wrapper

```bash
 $ ./gradlew clean buildNeeded
```
