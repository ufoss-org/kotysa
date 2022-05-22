[![License: Unlicense](https://img.shields.io/github/license/ufoss-org/kotysa)](http://unlicense.org/)
[![Maven Central](https://img.shields.io/maven-central/v/org.ufoss.kotysa/kotysa-core)](https://search.maven.org/artifact/org.ufoss.kotysa/kotysa-core)
[![Kotlin](https://img.shields.io/badge/kotlin-1.6.21-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotysa

The idiomatic way to write **Ko**tlin **ty**pe-**sa**fe SQL for JVM and Android

## Getting started

<p align="center">
<a href="https://ufoss.org/kotysa/kotysa.html">Full documentation</a>
</p>

## Basic example

Kotysa is easy to use : 3 steps only

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

### step 2 -> Describe database model

Use our type-safe Tables DSL to map your entities with the database tables,
this is the ORM (object-relational mapping) step

```kotlin
object Roles : H2Table<Role>("roles") {
    val id = uuid(Role::id)
        .primaryKey()
    val label = varchar(Role::label)
}

object Users : H2Table<User>("users") {
    val id = uuid(User::id)
        .primaryKey("PK_users")
    val firstname = varchar(User::firstname, "f_name")
    val roleId = uuid(User::roleId)
        .foreignKey(Roles.id, "FK_users_roles")
    val alias = varchar(User::alias)
}

// List all your mapped tables
private val tables = tables().h2(Roles, Users)
```

### step 3 -> Write SQL queries

Use our type-safe SqlClient DSL, Kotysa executes SQL query for you !

```kotlin
val admins = (sqlClient selectFrom Users
        innerJoin Roles on Users.roleId eq Roles.id
        where Roles.label eq "admin"
        ).fetchAll() // returns all admin users
```

**No annotations, no code generation, no proxy, just regular Kotlin code ! No JPA, just pure SQL !**

## Contributors

Contributions are welcome.

* To compile Kotysa use a JDK 8.
* You need a local docker, like docker-ce : some tests use testcontainers to start real databases like PostgreSQL, MySQL...

1. Clone this repo

```bash
git clone git@github.com:ufoss-org/kotysa.git
```

2. Build project

```bash
./gradlew clean build
```
