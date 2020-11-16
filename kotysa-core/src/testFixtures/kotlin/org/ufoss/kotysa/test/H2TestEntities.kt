/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.tables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

const val defaultUuid = "67d4306e-d99d-4e54-8b1d-5b1e92691a4e"

object H2_ROLE : H2Table<RoleEntity>("roles") {
    val id = column { it[RoleEntity::id].integer() }
            .primaryKey()
    val label = column { it[RoleEntity::label].varchar() }
}

object H2_USER : H2Table<UserEntity>("users") {
    val id = column { it[UserEntity::id].integer() }
            .primaryKey("PK_users")
    val firstname = column {
        it[UserEntity::firstname].varchar {
            name = "fname"
        }
    }
    val lastname = column {
        it[UserEntity::lastname].varchar {
            name = "lname"
        }
    }
    val isAdmin = column { it[UserEntity::isAdmin].boolean() }
    val roleId = column { it[UserEntity::roleId].integer() }
            .foreignKey(H2_ROLE, "FK_users_roles")
    val alias = column { it[UserEntity::alias].varchar() }
}

object H2_ALL_TYPES_NOT_NULL : H2Table<AllTypesNotNullEntity>("all_types") {
    val id = column { it[AllTypesNotNullEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNotNullEntity::string].varchar() }
    val boolean = column { it[AllTypesNotNullEntity::boolean].boolean() }
    val localDate = column { it[AllTypesNotNullEntity::localDate].date() }
    val kotlinxLocalDate = column { it[AllTypesNotNullEntity::kotlinxLocalDate].date() }
    val localTime = column { it[AllTypesNotNullEntity::localTim].time() } // todo test fractionalSecondsPart later
    val localDateTime1 = column { it[AllTypesNotNullEntity::localDateTime1].dateTime() }
    val localDateTime2 = column { it[AllTypesNotNullEntity::localDateTime2].timestamp() }
    val kotlinxLocalDateTime1 = column { it[AllTypesNotNullEntity::kotlinxLocalDateTime1].dateTime() }
    val kotlinxLocalDateTime2 = column { it[AllTypesNotNullEntity::kotlinxLocalDateTime2].timestamp() }
    val int = column { it[AllTypesNotNullEntity::int].integer() }
}

object H2_ALL_TYPES_NULLABLE : H2Table<AllTypesNullableEntity>("all_types_nullable") {
    val id = column { it[AllTypesNullableEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNullableEntity::string].varchar() }
    val localDate = column { it[AllTypesNullableEntity::localDate].date() }
    val kotlinxLocalDate = column { it[AllTypesNullableEntity::kotlinxLocalDate].date() }
    val localTime = column { it[AllTypesNullableEntity::localTim].time() } // todo test fractionalSecondsPart later
    val localDateTime1 = column { it[AllTypesNullableEntity::localDateTime1].dateTime() }
    val localDateTime2 = column { it[AllTypesNullableEntity::localDateTime2].timestamp() }
    val kotlinxLocalDateTime1 = column { it[AllTypesNullableEntity::kotlinxLocalDateTime1].dateTime() }
    val kotlinxLocalDateTime2 = column { it[AllTypesNullableEntity::kotlinxLocalDateTime2].timestamp() }
    val int = column { it[AllTypesNullableEntity::int].integer() }
}

object H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE : H2Table<AllTypesNullableDefaultValueEntity>() {
    val id = column { it[AllTypesNullableDefaultValueEntity::id].integer() }
            .primaryKey()
    val string = column {
        it[AllTypesNullableDefaultValueEntity::string].varchar {
            defaultValue = "default"
        }
    }
    val localDate = column {
        it[AllTypesNullableDefaultValueEntity::localDate].date {
            defaultValue = LocalDate.of(2019, 11, 4)
        }
    }
    val kotlinxLocalDate = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDate].date {
            defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
        }
    }
    val localTime = column {
        it[AllTypesNullableDefaultValueEntity::localTim].time {
            defaultValue = LocalTime.of(11, 25, 55, 123456789)
        }
    }
    val localDateTime1 = column {
        it[AllTypesNullableDefaultValueEntity::localDateTime1].dateTime {
            defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
        }
    }
    val localDateTime2 = column {
        it[AllTypesNullableDefaultValueEntity::localDateTime2].timestamp {
            defaultValue = LocalDateTime.of(2019, 11, 4, 0, 0)
        }
    }
    val kotlinxLocalDateTime1 = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime1].dateTime {
            defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
        }
    }
    val kotlinxLocalDateTime2 = column {
        it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime2].timestamp {
            defaultValue = kotlinx.datetime.LocalDateTime(2019, 11, 4, 0, 0)
        }
    }
    val int = column {
        it[AllTypesNullableDefaultValueEntity::int].integer {
            defaultValue = 42
        }
    }
}

object H2_LOCAL_DATE : H2Table<LocalDateEntity>() {
    val id = column { it[LocalDateEntity::id].integer() }
            .primaryKey()
    val localDateNotNull = column { it[LocalDateEntity::localDateNotNull].date() }
    val localDateNullable = column { it[LocalDateEntity::localDateNullable].date() }
}

object H2_KOTLINX_LOCAL_DATE : H2Table<KotlinxLocalDateEntity>() {
    val id = column { it[KotlinxLocalDateEntity::id].integer() }
            .primaryKey()
    val localDateNotNull = column { it[KotlinxLocalDateEntity::localDateNotNull].date() }
    val localDateNullable = column { it[KotlinxLocalDateEntity::localDateNullable].date() }
}

object H2_LOCAL_DATE_TIME : H2Table<LocalDateTimeEntity>() {
    val id = column { it[LocalDateTimeEntity::id].integer() }
            .primaryKey()
    val localDateTimeNotNull = column { it[LocalDateTimeEntity::localDateTimeNotNull].dateTime() }
    val localDateTimeNullable = column { it[LocalDateTimeEntity::localDateTimeNullable].dateTime() }
}

object H2_KOTLINX_LOCAL_DATE_TIME : H2Table<KotlinxLocalDateTimeEntity>() {
    val id = column { it[KotlinxLocalDateTimeEntity::id].integer() }
            .primaryKey()
    val localDateTimeNotNull = column { it[KotlinxLocalDateTimeEntity::localDateTimeNotNull].dateTime() }
    val localDateTimeNullable = column { it[KotlinxLocalDateTimeEntity::localDateTimeNullable].dateTime() }
}

object H2_OFFSET_LOCAL_DATE_TIME : H2Table<OffsetDateTimeEntity>() {
    val id = column { it[OffsetDateTimeEntity::id].integer() }
            .primaryKey()
    val offsetDateTimeNotNull = column { it[OffsetDateTimeEntity::offsetDateTimeNotNull].timestampWithTimeZone() }
    val offsetDateTimeNullable = column { it[OffsetDateTimeEntity::offsetDateTimeNullable].timestampWithTimeZone() }
}

object H2_LOCAL_TIME : H2Table<LocalTimeEntity>() {
    val id = column { it[LocalTimeEntity::id].integer() }
            .primaryKey()
    val localTimeNotNull = column { it[LocalTimeEntity::localTimeNotNull].time() }
    val localTimeNullable = column { it[LocalTimeEntity::localTimeNullable].time() }
}

object H2_INT : H2Table<IntEntity>() {
    val id = column { it[IntEntity::id].autoIncrementInteger() }
            .primaryKey()
    val intNotNull = column { it[IntEntity::intNotNull].integer() }
    val intNullable = column { it[IntEntity::intNullable].integer() }
}

object H2_UUID : H2Table<UuidEntity>() {
    val id = column { it[UuidEntity::id].uuid() }
            .primaryKey()
    val uuidNotNull = column { it[UuidEntity::uuidNotNull].uuid() }
    val uuidNullable = column { it[UuidEntity::uuidNullable].uuid() }
}

val h2Tables = tables().h2(
        H2_ROLE,
        H2_USER,
        H2_ALL_TYPES_NOT_NULL,
        H2_ALL_TYPES_NULLABLE,
        H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE,
        H2_LOCAL_DATE,
        H2_KOTLINX_LOCAL_DATE,
        H2_LOCAL_DATE_TIME,
        H2_KOTLINX_LOCAL_DATE_TIME,
        H2_OFFSET_LOCAL_DATE_TIME,
        H2_LOCAL_TIME,
        H2_INT,
        H2_UUID
)
