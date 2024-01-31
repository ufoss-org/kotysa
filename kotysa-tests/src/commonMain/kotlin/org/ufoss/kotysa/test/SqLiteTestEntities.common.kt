/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.sqlite.SqLiteTable
import org.ufoss.kotysa.sqlite.text

object SqliteCompanies : SqLiteTable<CompanyEntity>("companies"), Companies {
    override val id = integer(CompanyEntity::id)
        .primaryKey()
    override val name = text(CompanyEntity::name)
        .unique()
}

object SqliteRoles : SqLiteTable<RoleEntity>("roles"), Roles {
    override val id = integer(RoleEntity::id)
        .primaryKey()
    override val label = text(RoleEntity::label)
        .unique()
}

object SqliteUsers : SqLiteTable<UserEntity>("users"), Users {
    override val id = integer(UserEntity::id)
        .primaryKey("PK_users")
    override val firstname = text(UserEntity::firstname, "fname")
    override val lastname = text(UserEntity::lastname, "lname")
    override val isAdmin = integer(UserEntity::isAdmin)
    override val roleId = integer(UserEntity::roleId)
        .foreignKey(SqliteRoles.id, "FK_users_roles")
    override val companyId = integer(UserEntity::companyId)
        .foreignKey(SqliteCompanies.id, "FK_users_companies")
    override val alias = text(UserEntity::alias)

    init {
        index(setOf(firstname, lastname), indexName = "full_name_index")
    }
}

object SqliteUserRoles : SqLiteTable<UserRoleEntity>("userRoles"), UserRoles {
    override val userId = integer(UserRoleEntity::userId)
        .foreignKey(SqliteUsers.id)
    override val roleId = integer(UserRoleEntity::roleId)
        .foreignKey(SqliteRoles.id)

    init {
        primaryKey(userId, roleId)
    }
}

expect class SqliteAllTypesNotNullWithTimeEntity
expect object SqliteAllTypesNotNullWithTimes : SqLiteTable<SqliteAllTypesNotNullWithTimeEntity>

expect class SqliteAllTypesNullableWithTimeEntity
expect object SqliteAllTypesNullableWithTimes : SqLiteTable<SqliteAllTypesNullableWithTimeEntity>

expect class SqliteAllTypesNullableDefaultValueWithTimeEntity
expect object SqliteAllTypesNullableDefaultValueWithTimes :
    SqLiteTable<SqliteAllTypesNullableDefaultValueWithTimeEntity>

object SqliteKotlinxLocalDates : SqLiteTable<KotlinxLocalDateEntity>("kotlinx_local_date"),
    KotlinxLocalDates {
    override val id = integer(KotlinxLocalDateEntity::id)
        .primaryKey()
    override val localDateNotNull = text(KotlinxLocalDateEntity::localDateNotNull)
    override val localDateNullable = text(KotlinxLocalDateEntity::localDateNullable)
}

object SqliteKotlinxLocalDateTimes : SqLiteTable<KotlinxLocalDateTimeEntity>("kotlinx_local_date_time"),
    KotlinxLocalDateTimes {
    override val id = integer(KotlinxLocalDateTimeEntity::id)
        .primaryKey()
    override val localDateTimeNotNull = text(KotlinxLocalDateTimeEntity::localDateTimeNotNull)
    override val localDateTimeNullable = text(KotlinxLocalDateTimeEntity::localDateTimeNullable)
}

object SqliteKotlinxLocalTimes : SqLiteTable<KotlinxLocalTimeEntity>("local_time"), KotlinxLocalTimes {
    override val id = integer(KotlinxLocalTimeEntity::id)
        .primaryKey()
    override val localTimeNotNull = text(KotlinxLocalTimeEntity::localTimeNotNull)
    override val localTimeNullable = text(KotlinxLocalTimeEntity::localTimeNullable)
}

object SqliteInts : SqLiteTable<IntEntity>("ints"), Ints {
    override val id = autoIncrementInteger(IntEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntEntity::intNotNull)
    override val intNullable = integer(IntEntity::intNullable)
}

object SqliteIntNonNullIds : SqLiteTable<IntNonNullIdEntity>(), IntNonNullIds {
    override val id = autoIncrementInteger(IntNonNullIdEntity::id)
        .primaryKey()
    override val intNotNull = integer(IntNonNullIdEntity::intNotNull)
    override val intNullable = integer(IntNonNullIdEntity::intNullable)
}

object SqliteLongs : SqLiteTable<LongEntity>("longs"), Longs {
    override val id = autoIncrementInteger(LongEntity::id)
        .primaryKey()
    override val longNotNull = integer(LongEntity::longNotNull)
    override val longNullable = integer(LongEntity::longNullable)
}

object SqliteLongNonNullIds : SqLiteTable<LongNonNullIdEntity>(), LongNonNullIds {
    override val id = autoIncrementInteger(LongNonNullIdEntity::id)
        .primaryKey()
    override val longNotNull = integer(LongNonNullIdEntity::longNotNull)
    override val longNullable = integer(LongNonNullIdEntity::longNullable)
}

object SqliteFloats : SqLiteTable<FloatEntity>(), Floats {
    override val id = integer(FloatEntity::id)
        .primaryKey()
    override val floatNotNull = real(FloatEntity::floatNotNull)
    override val floatNullable = real(FloatEntity::floatNullable)
}

object SqliteDoubles : SqLiteTable<DoubleEntity>(), Doubles {
    override val id = integer(DoubleEntity::id)
        .primaryKey()
    override val doubleNotNull = real(DoubleEntity::doubleNotNull)
    override val doubleNullable = real(DoubleEntity::doubleNullable)
}

object SqliteCustomers : SqLiteTable<CustomerEntity>("customer"), Customers {
    override val id = integer(CustomerEntity::id)
        .primaryKey()
    override val name = text(CustomerEntity::name)
        .unique()
    override val country = text(CustomerEntity::country)
    override val age = integer(CustomerEntity::age)
}

object SqliteByteArrays : SqLiteTable<ByteArrayEntity>(), ByteArrays {
    override val id = integer(ByteArrayEntity::id)
        .primaryKey()
    override val byteArrayNotNull = blob(ByteArrayEntity::byteArrayNotNull)
    override val byteArrayNullable = blob(ByteArrayEntity::byteArrayNullable)
}

expect val sqLiteTables: SqLiteTables
