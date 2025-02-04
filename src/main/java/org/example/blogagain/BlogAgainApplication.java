package org.example.blogagain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("org.example.blogagain.entity")
@EnableJpaRepositories("org.example.blogagain.repository")
public class BlogAgainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAgainApplication.class, args);
    }
}

