/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.ContainerState

interface TestContainersCloseableResource : ExtensionContext.Store.CloseableResource, ContainerState
