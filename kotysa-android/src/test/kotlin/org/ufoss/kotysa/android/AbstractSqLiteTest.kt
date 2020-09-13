/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.sqLiteTables

/**
 * Android SDK 5.0 (API = 21) is the minimal that works
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [21])
abstract class AbstractSqLiteTest<T : Repository> {

    protected lateinit var dbHelper: DbHelper
    protected lateinit var repository: T

    @Suppress("DEPRECATION")
    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application)
        repository = getRepository(sqLiteTables)
        repository.init()
    }

    @After
    fun afterAll() {
        repository.delete()
    }

    protected abstract fun getRepository(sqLiteTables: Tables): T

    protected val client: SQLiteDatabase get() = dbHelper.writableDatabase
}
