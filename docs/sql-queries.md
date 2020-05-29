### Write Type-Safe Queries with SqlClient

**SqlClient** supports :
* ```select<T>``` that returns one (```fetchOne()``` and ```fetchFirst()```) or several (```fetchAll()```) results
* ```createTable<T>``` for table creation
* ```insert``` for single or multiple rows insertion
* ```deleteFromTable<T>``` that returns number of deleted rows
* ```updateTable<T>``` to update fields, returns number of updated rows

```kotlin
fun createTable() = sqlClient.createTable<User>()

fun insert() = sqlClient.insert(jdoe, bboss)

fun deleteAll() = sqlClient.deleteAllFromTable<User>()

fun deleteById(id: UUID) = sqlClient.deleteFromTable<User>()
        .where { it[User::id] eq id }
        .execute()

fun selectAll() = sqlClient.selectAll<User>()

fun countAll() = sqlClient.countAll<User>()

fun countWithAlias() = sqlClient.select { count { it[User::alias] } }.fetchOne()

fun selectAllMappedToDto() =
        sqlClient.select {
            UserDto("${it[User::firstname]} ${it[User::lastname]}",
                    it[User::alias])
        }.fetchAll()
        
fun selectFirstByFirstname(firstname: String) = sqlClient.select<User>()
        .where { it[User::firstname] eq firstname }
        // null String forbidden        ^^^^^^^^^
        .fetchFirst()

fun selectAllByAlias(alias1: String?, alias2: String?) = sqlClient.select<User>()
        .where { it[User::alias] eq alias1 }
        // null String accepted     ^^^^^ , if alias1==null, gives "WHERE user.alias IS NULL"
        .or { it[User::alias] eq alias2 }
        .fetchAll()
        
fun updateFirstname(newFirstname: String) = sqlClient.updateTable<User>()
        .set { it[User::firstname] = newFirstname }
        .execute()

val jdoe = User("John", "Doe", false)
val bboss = User("Big", "Boss", true, "TheBoss")

data class UserDto(
		val name: String,
		val alias: String?
)
```
