package de.melkodary.shserver;

import de.melkodary.sh.grpc.lib.HospitalRequest;
import de.melkodary.sh.grpc.lib.HospitalResponse;
import de.melkodary.sh.grpc.lib.HospitalServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HospitalGrpcTest {

    @GrpcClient("hospitalService")
    private HospitalServiceGrpc.HospitalServiceBlockingStub hospitalGrpc;

    @Test
    void test() {
        HospitalRequest hospitalRequest = HospitalRequest
                .newBuilder()
                .setAddress("New Address")
                .setName("New Name")
                .build();
        HospitalResponse hospitalResponse = hospitalGrpc.createHospital(hospitalRequest);
        assertEquals(hospitalRequest.getName(), hospitalResponse.getName());
    }
}
