package com.sample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.tables


private val h2Tables = tables().h2(Roles, Users)

@Configuration
class DataConfig : AbstractJdbcConfiguration() {

    @Bean
    fun sqlClient(dbClient: JdbcOperations) = dbClient.sqlClient(h2Tables)

    @Bean
    fun operator(op: TransactionTemplate) = op.transactionalOp()
}
