/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object SQLITE_ROLE : SqLiteTable<RoleEntity>() {
    override var name = "roles"
    val id = column { it[RoleEntity::id].integer() }
            .primaryKey()
    val label = column { it[RoleEntity::label].text() }
}

object SQLITE_USER : SqLiteTable<UserEntity>() {
    override var name = "users"
    val id = column { it[UserEntity::id].integer() }
            .primaryKey("PK_users")
    val firstname = column {
        it[UserEntity::firstname].text {
            name = "fname"
        }
    }
    val lastname = column {
        it[UserEntity::lastname].text {
            name = "lname"
        }
    }
    val isAdmin = column { it[UserEntity::isAdmin].integer() }
    val roleId = column { it[UserEntity::roleId].integer() }
            .foreignKey(H2_ROLE, "FK_users_roles")
    val alias = column { it[UserEntity::alias].text() }
}

object SQLITE_ALL_TYPES_NOT_NULL : SqLiteTable<AllTypesNotNullEntity>() {
    override var name = "all_types"
    val id = column { it[AllTypesNotNullEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNotNullEntity::string].text() }
    val boolean = column { it[AllTypesNotNullEntity::boolean].integer() }
    val localDate = column { it[AllTypesNotNullEntity::localDate].text() }
    val kotlinxLocalDate = column { it[AllTypesNotNullEntity::kotlinxLocalDate].text() }
    val localTime = column { it[AllTypesNotNullEntity::localTim].text() }
    val localDateTime1 = column { it[AllTypesNotNullEntity::localDateTime1].text() }
    val localDateTime2 = column { it[AllTypesNotNullEntity::localDateTime2].text() }
    val kotlinxLocalDateTime1 = column { it[AllTypesNotNullEntity::kotlinxLocalDateTime1].text() }
    val kotlinxLocalDateTime2 = column { it[AllTypesNotNullEntity::kotlinxLocalDateTime2].text() }
    val int = column { it[AllTypesNotNullEntity::int].integer() }
}

object SQLITE_ALL_TYPES_NULLABLE : SqLiteTable<AllTypesNullableEntity>() {
    override var name = "all_types_nullable"
    val id = column { it[AllTypesNullableEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNullableEntity::string].text() }
    val localDate = column { it[AllTypesNullableEntity::localDate].text() }
    val kotlinxLocalDate = column { it[AllTypesNullableEntity::kotlinxLocalDate].text() }
    val localTime = column { it[AllTypesNullableEntity::localTim].text() }
    val localDateTime1 = column { it[AllTypesNullableEntity::localDateTime1].text() }
    val localDateTime2 = column { it[AllTypesNullableEntity::localDateTime2].text() }
    val kotlinxLocalDateTime1 = column { it[AllTypesNullableEntity::kotlinxLocalDateTime1].text() }
    val kotlinxLocalDateTime2 = column { it[AllTypesNullableEntity::kotlinxLocalDateTime2].text() }
    val int = column { it[AllTypesNullableEntity::int].integer() }
}

object SQLITE_ALL_TYPES_NULLABLE_DEFAULT_VALUE : SqLiteTable<AllTypesNullableDefaultValueEntity>() {
    val id = column { it[AllTypesNullableDefaultValueEntity::id].integer() }
            .primaryKey()
    val string = column {
        it[AllTypesNullableDefaultValueEntity::string].text {
            defaultValue = "default"
        }
    }
    val localDate = column {
        it[AllTypesNullableDefaultValueEntity::localDate].text {
            defaultValue = LocalDate.of(2019, 11, 4)
        }
    }
    val kotlinxLocalDate = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDate].text {
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
        }
    }
    val localTime = column {
        it[AllTypesNullableDefaultValueEntity::localTim].text {
            defaultValue = LocalTime.of(11, 25, 55)
        }
    }
    val localDateTime1 = column {
        it[AllTypesNullableDefaultValueEntity::localDateTime1].text {
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
        }
    }
    val localDateTime2 = column {
        it[AllTypesNullableDefaultValueEntity::localDateTime2].text {
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
        }
    }
    val kotlinxLocalDateTime1 = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1].text {
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
        }
    }
    val kotlinxLocalDateTime2 = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2].text {
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
        }
    }
    val int = column {
        it[AllTypesNullableDefaultValueEntity::int].integer {
            name = "mysql_integer"
            defaultValue = 42
        }
    }
}

object SQLITE_LOCAL_DATE : SqLiteTable<LocalDateEntity>() {
    val id = column { it[LocalDateEntity::id].integer() }
            .primaryKey()
    val localDateNotNull = column { it[LocalDateEntity::localDateNotNull].text() }
    val localDateNullable = column { it[LocalDateEntity::localDateNullable].text() }
}

object SQLITE_KOTLINX_LOCAL_DATE : SqLiteTable<KotlinxLocalDateEntity>() {
    val id = column { it[KotlinxLocalDateEntity::id].integer() }
            .primaryKey()
    val localDateNotNull = column { it[KotlinxLocalDateEntity::localDateNotNull].text() }
    val localDateNullable = column { it[KotlinxLocalDateEntity::localDateNullable].text() }
}

object SQLITE_LOCAL_DATE_TIME : SqLiteTable<LocalDateTimeEntity>() {
    val id = column { it[LocalDateTimeEntity::id].integer() }
            .primaryKey()
    val localDateTimeNotNull = column { it[LocalDateTimeEntity::localDateTimeNotNull].text() }
    val localDateTimeNullable = column { it[LocalDateTimeEntity::localDateTimeNullable].text() }
}

object SQLITE_KOTLINX_LOCAL_DATE_TIME : SqLiteTable<KotlinxLocalDateTimeEntity>() {
    val id = column { it[KotlinxLocalDateTimeEntity::id].integer() }
            .primaryKey()
    val localDateTimeNotNull = column { it[KotlinxLocalDateTimeEntity::localDateTimeNotNull].text() }
    val localDateTimeNullable = column { it[KotlinxLocalDateTimeEntity::localDateTimeNullable].text() }
}

object SQLITE_OFFSET_LOCAL_DATE_TIME : SqLiteTable<OffsetDateTimeEntity>() {
    val id = column { it[OffsetDateTimeEntity::id].integer() }
            .primaryKey()
    val offsetDateTimeNotNull = column { it[OffsetDateTimeEntity::offsetDateTimeNotNull].text() }
    val offsetDateTimeNullable = column { it[OffsetDateTimeEntity::offsetDateTimeNullable].text() }
}

object SQLITE_LOCAL_TIME : SqLiteTable<LocalTimeEntity>() {
    val id = column { it[LocalTimeEntity::id].integer() }
            .primaryKey()
    val localTimeNotNull = column { it[LocalTimeEntity::localTimeNotNull].text() }
    val localTimeNullable = column { it[LocalTimeEntity::localTimeNullable].text() }
}

object SQLITE_INT : SqLiteTable<IntEntity>() {
    val id = column { it[IntEntity::id].autoIncrementInteger() }
            .primaryKey()
    val intNotNull = column { it[IntEntity::intNotNull].integer() }
    val intNullable = column { it[IntEntity::intNullable].integer() }
}

val sqLiteTables = tables().sqlite(
        SQLITE_ROLE,
        SQLITE_USER,
        SQLITE_ALL_TYPES_NOT_NULL,
        SQLITE_ALL_TYPES_NULLABLE,
        SQLITE_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        SQLITE_LOCAL_DATE,
        SQLITE_KOTLINX_LOCAL_DATE,
        SQLITE_LOCAL_DATE_TIME,
        SQLITE_KOTLINX_LOCAL_DATE_TIME,
        SQLITE_OFFSET_LOCAL_DATE_TIME,
        SQLITE_LOCAL_TIME,
        SQLITE_INT,
)
