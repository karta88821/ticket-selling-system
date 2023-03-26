package com.daniel_liao.ticketsellingsystem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan("com.daniel_liao.ticketsellingsystem.configuration")
@EnableJpaRepositories(basePackages = {"com.daniel_liao.ticketsellingsystem.section", "com.daniel_liao.ticketsellingsystem.event", "com.daniel_liao.ticketsellingsystem.ticket", "com.daniel_liao.ticketsellingsystem.security"})
@EntityScan(basePackages = {"com.daniel_liao.ticketsellingsystem.section", "com.daniel_liao.ticketsellingsystem.event", "com.daniel_liao.ticketsellingsystem.ticket", "com.daniel_liao.ticketsellingsystem.security"})
public class SharedConfiguration {
    
}
