package com.daniel_liao.ticketsellingsystem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan("com.daniel_liao.ticketsellingsystem.configuration")
@EnableJpaRepositories(basePackages = {"com.daniel_liao.ticketsellingsystem.repository", "com.daniel_liao.ticketsellingsystem.section"})
@EntityScan(basePackages = {"com.daniel_liao.ticketsellingsystem.entity", "com.daniel_liao.ticketsellingsystem.section"})
public class SharedConfiguration {
    
}
