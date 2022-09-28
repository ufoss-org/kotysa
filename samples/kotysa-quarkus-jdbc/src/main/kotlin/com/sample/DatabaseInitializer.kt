package com.sample

import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    fun onStart(@Observes event: StartupEvent) {
        roleRepository.createTable()
        userRepository.createTable()

        userRepository.deleteAll()
        roleRepository.deleteAll()

        roleRepository.insert()
        userRepository.insert()
    }
}
