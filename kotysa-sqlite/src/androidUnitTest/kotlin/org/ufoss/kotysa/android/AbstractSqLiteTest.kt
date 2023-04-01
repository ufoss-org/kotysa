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
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.repositories.blocking.RepositoryTest
import org.ufoss.kotysa.test.sqLiteTables
import org.ufoss.kotysa.transaction.TransactionalOp

/**
 * Android SDK 5.0 (API = 21) is the minimal that works
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [21])
abstract class AbstractSqLiteTest<T : Repository> : RepositoryTest<T, AndroidTransaction> {

    protected lateinit var dbHelper: DbHelper
    private lateinit var _repository: T

    @Suppress("DEPRECATION")
    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application)
        _repository = getRepository(sqLiteTables)
        repository.init()
    }

    @After
    fun after() {
        repository.delete()
        dbHelper.close()
    }

    override val repository: T by lazy {
        _repository
    }

    override val operator: TransactionalOp<AndroidTransaction>
        get() = client.transactionalOp()

    protected abstract fun getRepository(sqLiteTables: SqLiteTables): T

    protected val client: SQLiteDatabase get() = dbHelper.writableDatabase
}
