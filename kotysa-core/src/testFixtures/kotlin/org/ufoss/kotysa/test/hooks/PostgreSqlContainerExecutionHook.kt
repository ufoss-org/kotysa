/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent("postgreSqlContainer", { PostgreSqlContainerResource() })
    }
}

private class KPostgreSqlContainer : PostgreSQLContainer<KPostgreSqlContainer>("postgres:13.0-alpine")

class PostgreSqlContainerResource : TestContainersCloseableResource {
    companion object {

        const val ID = "PostgreSqlContainerResource"

        @JvmStatic
        private val dbContainer = createDbContainer()

        @JvmStatic
        private fun createDbContainer(): KPostgreSqlContainer {
            val postgreSqlContainer = KPostgreSqlContainer()
            postgreSqlContainer
                    .withDatabaseName("db")
                    .withUsername("postgres")
                    .withPassword("test")
            postgreSqlContainer.start()
            println("KPostgreSqlContainer started")

            return postgreSqlContainer
        }
    }

    override fun close() {
        dbContainer.stop()
        println("KPostgreSqlContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
