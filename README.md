[![License: Unlicense](https://img.shields.io/github/license/ufoss-org/kotysa)](http://unlicense.org/)
[![Download](https://api.bintray.com/packages/ufoss/ufoss/kotysa/images/download.svg) ](https://bintray.com/ufoss/ufoss/kotysa/_latestVersion)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.10-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotysa

The idiomatic way to write **Ko**tlin **ty**pe-**sa**fe SQL.

```kotlin
val admins = sqlClient.select<User>()
        .innerJoin<Role>().on { it[User::roleId] }
        .where { it[Role::label] eq "admin" }
        .fetchAll() // returns all admin users
```

<p align="center">
<a href="https://ufoss.org/kotysa/kotysa.html">Read Documentation</a>
</p>

## Contributors

Contributions are very welcome.

* To compile Kotysa use a JDK 11.
* You need a local docker, like docker-ce. Some integration tests use testcontainers to start real databases like PostgreSQL

1. Clone this repo

```bash
git clone git@github.com:ufoss-org/kotysa.git
```

2. Build project

```bash
./gradlew clean buildNeeded
```
