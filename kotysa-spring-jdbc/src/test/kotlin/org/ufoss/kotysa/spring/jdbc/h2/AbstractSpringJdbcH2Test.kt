/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.ufoss.kotysa.test.Repository
import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.jdbc.jdbc


abstract class AbstractSpringJdbcH2Test<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext() =
            application {
                beans {
                    bean<U>()
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                jdbc {
                    url = "jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
                }
            }.run()

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.close()
    }
}
