package de.melkodary.shserver;

import de.melkodary.shserver.grpc.HospitalGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"de.melkodary.shserver"})
@EntityScan(basePackages = {"de.melkodary.shserver"})
@EnableJpaRepositories(basePackages = {"de.melkodary.shserver"})
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new HospitalGrpcImpl()).build();

        server.start();
        server.awaitTermination();
    }
}