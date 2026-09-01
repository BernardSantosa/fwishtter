package com.fwishtter.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.fwishtter")
@EntityScan("com.fwishtter")
@EnableJpaRepositories("com.fwishtter")
@EnableFeignClients
@EnableJpaAuditing
@EnableKafka
public class TweetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetServiceApplication.class, args);
    }

}