package com.sample

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application

class UserRepositoryTests {

    private val dataApp = application {
        enable(dataConfig)
    }

    private lateinit var context: ConfigurableApplicationContext
    private lateinit var repository: UserRepository

    @BeforeAll
    fun beforeAll() {
        context = dataApp.run(profiles = "test")
        repository = context.getBean()
    }

    @Test
    fun count() = runBlocking<Unit> {
        assertThat(repository.count())
                .isEqualTo(2)
    }

    @Test
    fun selectWithJoin() = runBlocking<Unit> {
        assertThat(repository.selectWithJoin().toList())
                .hasSize(2)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
