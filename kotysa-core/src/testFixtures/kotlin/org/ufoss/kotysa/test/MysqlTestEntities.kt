/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

/*
object H2_ROLE : H2Table<RoleEntity>() {
    override var name = "roles"
    val id = column { it[RoleEntity::id].integer() }
            .primaryKey()
    val lable = column { it[RoleEntity::label].varchar {
        size = 255
    } }
}

object H2_USER : H2Table<UserEntity>() {
    override var name = "users"
    val id = column { it[UserEntity::id].integer() }
            .primaryKey("PK_users")
    val firstname = column { it[UserEntity::firstname].varchar {
        name = "fname"
        size = 255
    } }
    val lastname = column { it[UserEntity::lastname].varchar {
        name = "lname"
        size = 255
    } }
    val isAdmin = column { it[UserEntity::isAdmin].boolean() }
    val roleId = column { it[UserEntity::roleId].integer() }
            .foreignKey(H2_ROLE, "FK_users_roles")
    val alias = column { it[UserEntity::alias].varchar {
        size = 255
    } }
}

object H2_ALL_TYPES : H2Table<AllTypesNotNullEntity>() {
    override var name = "all_types"
    val id = column { it[AllTypesNotNullEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNotNullEntity::string].varchar {
        size = 255
    } }
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

object H2_ALL_TYPES_NULLABLE : H2Table<AllTypesNullableEntity>() {
    override var name = "all_types_nullable"
    val id = column { it[AllTypesNullableEntity::id].integer() }
            .primaryKey()
    val string = column { it[AllTypesNullableEntity::string].varchar {
        size = 255
    } }
    val localDate = column { it[AllTypesNullableEntity::localDate].date() }
    val kotlinxLocalDate = column { it[AllTypesNullableEntity::kotlinxLocalDate].date() }
    val localTime = column { it[AllTypesNullableEntity::localTim].time() } // todo test fractionalSecondsPart later
    val localDateTime = column { it[AllTypesNullableEntity::localDateTime].dateTime() }
    val kotlinxLocalDateTime = column { it[AllTypesNullableEntity::kotlinxLocalDateTime].dateTime() }
    val int = column { it[AllTypesNullableEntity::int].integer() }
}

object H2_ALL_TYPES_NULLABLE_DEFAULT_VALUE : H2Table<AllTypesNullableDefaultValueEntity>() {
    val id = column { it[AllTypesNullableDefaultValueEntity::id].integer() }
    .primaryKey()
    val string = column { it[AllTypesNullableDefaultValueEntity::string].varchar {
        defaultValue = "default"
        size = 255
    } }
    val localDate = column { it[AllTypesNullableDefaultValueEntity::localDate].date {
        defaultValue = LocalDate.of(2019, 11, 4)
    } }
    val kotlinxLocalDate = column { it[AllTypesNullableDefaultValueEntity::kotlinxLocalDate].date {
        defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
    } }
    val localTime = column { it[AllTypesNullableDefaultValueEntity::localTim].time {
        defaultValue = LocalTime.of(11, 25, 55)
    } }
    val localDateTime = column { it[AllTypesNullableDefaultValueEntity::localDateTime].dateTime() {
        defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
    } }
    val kotlinxLocalDateTime = column { it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime].dateTime {
        defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
    } }
    val int = column { it[AllTypesNullableDefaultValueEntity::int].integer {
        name = "mysql_integer"
        defaultValue = 42
    } }
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
    val offsetDateTimeNotNull = column { it[OffsetDateTimeEntity::offsetDateTimeNotNull].timestamp() }
    val offsetDateTimeNullable = column { it[OffsetDateTimeEntity::offsetDateTimeNullable].timestamp() }
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

val mysqlTables =
        tables().mysql {
            table<RoleEntity> {
                name = "roles"
                column { it[RoleEntity::id].integer() }
                        .primaryKey()
                column { it[RoleEntity::label].varchar {
                    size = 255
                } }
            }
            table<UserEntity> {
                name = "users"
                column { it[UserEntity::id].integer() }
                        .primaryKey("PK_users")
                column { it[UserEntity::firstname].varchar {
                    name = "fname"
                    size = 255
                } }
                column { it[UserEntity::lastname].varchar() {
                    name = "lname"
                    size = 255
                } }
                column { it[UserEntity::isAdmin].boolean() }
                column { it[UserEntity::roleId].integer() }
                        .foreignKey<RoleEntity>("FK_users_roles")
                column { it[UserEntity::alias].varchar {
                    size = 255
                } }
            }
            table<AllTypesNotNullEntity> {
                name = "all_types"
                column { it[AllTypesNotNullEntity::id].integer() }
                        .primaryKey()
                column { it[AllTypesNotNullEntity::string].varchar {
                    size = 255
                } }
                column { it[AllTypesNotNullEntity::boolean].boolean() }
                column { it[AllTypesNotNullEntity::localDate].date() }
                column { it[AllTypesNotNullEntity::kotlinxLocalDate].date() }
                column { it[AllTypesNotNullEntity::localTim].time() }
                column { it[AllTypesNotNullEntity::localDateTime].dateTime() }
                column { it[AllTypesNotNullEntity::kotlinxLocalDateTime].dateTime() }
                column { it[AllTypesNotNullEntity::int].integer {
                    name = "mysql_integer"
                } }
            }
            table<AllTypesNullableEntity> {
                name = "all_types_nullable"
                column { it[AllTypesNullableEntity::id].integer() }
                        .primaryKey()
                column { it[AllTypesNullableEntity::string].varchar {
                    size = 255
                } }
                column { it[AllTypesNullableEntity::localDate].date() }
                column { it[AllTypesNullableEntity::kotlinxLocalDate].date() }
                column { it[AllTypesNullableEntity::localTim].time {
                    fractionalSecondsPart = 0
                } }
                column { it[AllTypesNullableEntity::localDateTime].dateTime() }
                column { it[AllTypesNullableEntity::kotlinxLocalDateTime].dateTime() }
                column { it[AllTypesNullableEntity::int].integer {
                    name = "mysql_integer"
                } }
            }
            table<AllTypesNullableDefaultValueEntity> {
                column { it[AllTypesNullableDefaultValueEntity::id].integer() }
                        .primaryKey()
                column { it[AllTypesNullableDefaultValueEntity::string].varchar {
                    defaultValue = "default"
                    size = 255
                } }
                column { it[AllTypesNullableDefaultValueEntity::localDate].date {
                    defaultValue = LocalDate.of(2019, 11, 4)
                } }
                column { it[AllTypesNullableDefaultValueEntity::kotlinxLocalDate].date {
                    defaultValue = kotlinx.datetime.LocalDate(2019, 11, 6)
                } }
                column { it[AllTypesNullableDefaultValueEntity::localTim].time {
                    defaultValue = LocalTime.of(11, 25, 55)
                } }
                column { it[AllTypesNullableDefaultValueEntity::localDateTime].dateTime() {
                    defaultValue = LocalDateTime.of(2018, 11, 4, 0, 0)
                } }
                column { it[AllTypesNullableDefaultValueEntity::kotlinxLocalDateTime].dateTime {
                    defaultValue = kotlinx.datetime.LocalDateTime(2018, 11, 4, 0, 0)
                } }
                column { it[AllTypesNullableDefaultValueEntity::int].integer {
                    name = "mysql_integer"
                    defaultValue = 42
                } }
            }
            table<LocalDateEntity> {
                column { it[LocalDateEntity::id].integer() }
                        .primaryKey()
                column { it[LocalDateEntity::localDateNotNull].date() }
                column { it[LocalDateEntity::localDateNullable].date() }
            }
            table<KotlinxLocalDateEntity> {
                column { it[KotlinxLocalDateEntity::id].integer() }
                        .primaryKey()
                column { it[KotlinxLocalDateEntity::localDateNotNull].date() }
                column { it[KotlinxLocalDateEntity::localDateNullable].date() }
            }
            table<LocalDateTimeEntity> {
                column { it[LocalDateTimeEntity::id].integer() }
                        .primaryKey()
                column { it[LocalDateTimeEntity::localDateTimeNotNull].dateTime() }
                column { it[LocalDateTimeEntity::localDateTimeNullable].dateTime() }
            }
            table<KotlinxLocalDateTimeEntity> {
                column { it[KotlinxLocalDateTimeEntity::id].integer() }
                        .primaryKey()
                column { it[KotlinxLocalDateTimeEntity::localDateTimeNotNull].dateTime() }
                column { it[KotlinxLocalDateTimeEntity::localDateTimeNullable].dateTime() }
            }
            table<OffsetDateTimeEntity> {
                column { it[OffsetDateTimeEntity::id].integer() }
                        .primaryKey()
                column { it[OffsetDateTimeEntity::offsetDateTimeNotNull].timestamp() }
                column { it[OffsetDateTimeEntity::offsetDateTimeNullable].timestamp() }
            }
            table<LocalTimeEntity> {
                column { it[LocalTimeEntity::id].integer() }
                        .primaryKey()
                column { it[LocalTimeEntity::localTimeNotNull].time() }
                column { it[LocalTimeEntity::localTimeNullable].time() }
            }
            table<IntEntity> {
                column { it[IntEntity::id].autoIncrementInteger() }
                        .primaryKey()
                column { it[IntEntity::intNotNull].integer() }
                column { it[IntEntity::intNullable].integer() }
            }
        }

private fun LocalTime.roundToSecond(): LocalTime {
    var time = this
    if (nano >= 500_000_000) {
        time = plusSeconds(1)
    }
    return time.truncatedTo(ChronoUnit.SECONDS)
}

private fun LocalDateTime.roundToSecond(): LocalDateTime {
    var localDateTime = this
    if (nano >= 500_000_000) {
        localDateTime = plusSeconds(1)
    }
    return localDateTime.truncatedTo(ChronoUnit.SECONDS)
}

data class MysqlAllTypesNotNull(
        override val id: Int,
        override val string: String,
        override val boolean: Boolean,
        override val localDate: LocalDate,
        override val kotlinxLocalDate: kotlinx.datetime.LocalDate,
        override val localTim: LocalTime,
        override val localDateTime: LocalDateTime,
        override val kotlinxLocalDateTime: kotlinx.datetime.LocalDateTime,
        override val int: Int
) : AllTypesNotNullEntity(
        id, string, boolean, localDate, kotlinxLocalDate, localTim, localDateTime, kotlinxLocalDateTime, int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllTypesNotNullEntity

        if (string != other.string) return false
        if (localDate != other.localDate) return false
        if (kotlinxLocalDate != other.kotlinxLocalDate) return false
        if (localTim.roundToSecond() != other.localTim.roundToSecond()) return false
        if (localDateTime.roundToSecond() != other.localDateTime.roundToSecond()) return false
        if (kotlinxLocalDateTime.toJavaLocalDateTime().roundToSecond()
                != other.kotlinxLocalDateTime.toJavaLocalDateTime().roundToSecond()) return false
        if (int != other.int) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (localDate.hashCode())
        result = 31 * result + (kotlinxLocalDate.hashCode())
        result = 31 * result + (localTim.hashCode())
        result = 31 * result + (localDateTime.hashCode())
        result = 31 * result + (kotlinxLocalDateTime.hashCode())
        result = 31 * result + (int)
        result = 31 * result + (id)
        return result
    }
}*/