package com.sample

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import kotlinx.coroutines.runBlocking

@ApplicationScoped
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    fun onStart(@Observes event: StartupEvent) = runBlocking {
        roleRepository.createTable()
        userRepository.createTable()
        userRepository.deleteAll()
        roleRepository.deleteAll()

        roleRepository.insert()
        userRepository.insert()
    }
}
