package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class DemoApplication {

    @Bean
    fun commandLineRunner(jdbcTemplate: JdbcTemplate) = CommandLineRunner {
        jdbcTemplate.execute(
                """
                    create table if not exists task (
                    id bigint primary key auto_increment,
                    content varchar(100) not null,
                    done boolean not null default false
                    )
                    """
        )
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
