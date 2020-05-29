### Describe Database Model with Type-Safe DSL

```tables``` functional DSL is used to define all mapped tables' structure, by linking every table to a class (aka Entity).

```kotlin
data class Role(
        val label: String,
        val id: UUID = UUID.randomUUID()
)

data class User(
        val firstname: String,
        val lastname: String,
        val isAdmin: Boolean,
        val roleId: UUID,
        val alias: String? = null,
        val id: UUID = UUID.randomUUID()
)

val tables =
        tables().h2 { // choose database type
            table<Role> {
                name = "roles"
                column { it[Role::id].uuid().primaryKey }
                column { it[Role::label].varchar() }
            }
            table<User> {
                name = "users"
                column { it[User::id].uuid().primaryKey }
                column { it[User::firstname].varchar().name("fname") }
                column { it[User::lastname].varchar().name("lname") }                
                column { it[User::isAdmin].boolean() }
                column { it[User::roleId].uuid().foreignKey<Role>() }
                column { it[User::alias].varchar() }
            }
        }
```

## Data types

Supported data types will be updated as new types are added. Java 8+ ```java.time.*``` date types are used.

### H2

<table>
    <tr>
        <th>Kotlin type</th>
        <th>Description
        <th>H2 SQL type</th>
    </tr>
    <tr>
        <td>String</td>
        <td>Represents a variable-length character string, maximum length fixed</td>
        <td>VARCHAR</td>
    </tr>
    <tr>
        <td>java.time.LocalDate</td>
        <td>Represents a date without time part and without timezone</td>
        <td>DATE</td>
    </tr>
    <tr>
        <td rowspan="2">java.time.LocalDateTime</td>
        <td rowspan="2">Represents a date+time without timezone</td>
        <td>TIMESTAMP</td>
    </tr>
    <tr>
        <td>DATETIME</td>
    </tr>
    <tr>
        <td>java.time.OffsetDateTime</td>
        <td>Represents a date+time with timezone</td>
        <td>TIMESTAMP WITH TIME ZONE</td>
    </tr>
    <tr>
        <td>java.time.LocalTime</td>
        <td>Represents a time without a date part and without timezone</td>
        <td>TIME(9)</td>
    </tr>
    <tr>
        <td>Boolean</td>
        <td>Represents a boolean state. Nullable Boolean is not allowed !</td>
        <td>BOOLEAN</td>
    </tr>
    <tr>
        <td>java.util.UUID</td>
        <td>Universally unique identifier (128 bit value)</td>
        <td>UUID</td>
    </tr>
    <tr>
        <td>Int</td>
        <td>Represents an integer</td>
        <td>INTEGER</td>
    </tr>
</table>

### PostgreSQL

<table>
    <tr>
        <th>Kotlin type</th>
        <th>Description
        <th>PostgreSQL type</th>
    </tr>
    <tr>
        <td>String</td>
        <td>Represents a variable-length character string, maximum length fixed</td>
        <td>VARCHAR</td>
    </tr>
    <tr>
        <td>java.time.LocalDate</td>
        <td>Represents a date without time part and without timezone</td>
        <td>DATE</td>
    </tr>
    <tr>
        <td>java.time.LocalDateTime</td>
        <td>Represents a date+time without timezone</td>
        <td>TIMESTAMP</td>
    </tr>
    <tr>
        <td>java.time.OffsetDateTime</td>
        <td>Represents a date+time with timezone. PostgreSQL uses UTC timezone to store timestamp with time zone, so you may have to override equals to use Instant based "isEqual" method on java.time.OffsetDateTime fields</td>
        <td>TIMESTAMP WITH TIME ZONE</td>
    </tr>
    <tr>
        <td>java.time.LocalTime</td>
        <td>Represents a time without a date part and without timezone</td>
        <td>TIME</td>
    </tr>
    <tr>
        <td>Boolean</td>
        <td>Represents a boolean state. Nullable Boolean is not allowed !</td>
        <td>BOOLEAN</td>
    </tr>
    <tr>
        <td>java.util.UUID</td>
        <td>Universally unique identifier (128 bit value)</td>
        <td>UUID</td>
    </tr>
    <tr>
        <td rowspan="2">Int</td>
        <td>Represents an integer</td>
        <td>INTEGER</td>
    </tr>
    <tr>
        <td>Represents an auto-incremented integer</td>
        <td>SERIAL</td>
    </tr>
</table>

### SqLite

<table>
    <tr>
        <th>Kotlin type</th>
        <th>Description
        <th>SqLite SQL type</th>
    </tr>
    <tr>
        <td>String</td>
        <td>Represents a variable-length character string, maximum length fixed</td>
        <td>TEXT</td>
    </tr>
    <tr>
        <td>java.time.LocalDate</td>
        <td>Represents a date without time part and without timezone</td>
        <td>TEXT</td>
    </tr>
    <tr>
        <td>java.time.LocalDateTime</td>
        <td>Represents a date+time without timezone</td>
        <td>TEXT</td>
    </tr>
    <tr>
        <td>java.time.OffsetDateTime</td>
        <td>Represents a date+time with timezone</td>
        <td>TEXT</td>
    </tr>
    <tr>
        <td>java.time.LocalTime</td>
        <td>Represents a time without a date part and without timezone</td>
        <td>TEXT</td>
    </tr>
    <tr>
        <td>Boolean</td>
        <td>Represents a boolean state. Nullable Boolean is not allowed !</td>
        <td>INTEGER</td>
    </tr>
    <tr>
        <td>Int</td>
        <td>Represents an integer</td>
        <td>INTEGER</td>
    </tr>
</table>
