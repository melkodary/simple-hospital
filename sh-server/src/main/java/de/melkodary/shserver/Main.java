package de.melkodary.shserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"de.melkodary.shserver"})
@EntityScan(basePackages = {"de.melkodary.shserver"})
@EnableJpaRepositories(basePackages = {"de.melkodary.shserver"})
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}