package com.sample

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.tables

private val h2Tables = tables().h2(Roles, Users)

@Configuration
class DataConfig : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory =
        ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1;FILE_LOCK=NO")

    @Bean
    fun sqlClient(dbClient: DatabaseClient) = dbClient.sqlClient(h2Tables)

    @Bean
    fun operator(op: TransactionalOperator) = op.transactionalOp()
}
