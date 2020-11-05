/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.PostgreSQLContainer

class MySqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent("mySqlContainer", { MySqlContainerResource() })
    }
}

class KMySQLContainer : MySQLContainer<KMySQLContainer>("mysql:8.0.22")

class MySqlContainerResource : TestContainersCloseableResource {
    companion object {

        const val ID = "MySqlContainerResource"

        @JvmStatic
        private val dbContainer = createDbContainer()

        @JvmStatic
        private fun createDbContainer(): KMySQLContainer {
            val mysqlContainer = KMySQLContainer()
                    .withDatabaseName("db")
                    .withUsername("mysql")
                    .withPassword("test")
            mysqlContainer.start()
            println("KMySQLContainer started")

            return mysqlContainer
        }
    }

    override fun close() {
        dbContainer.stop()
        println("KPostgreSqlContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
