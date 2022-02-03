package com.sample

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTests {

    @Autowired
    private lateinit var repository: UserRepository

    @Test
    fun count() = runBlockingTest {
        assertThat(repository.count())
                .isEqualTo(2)
    }

    @Test
    fun selectWithJoin() = runBlockingTest {
        assertThat(repository.selectWithJoin().toList())
                .hasSize(2)
    }
}