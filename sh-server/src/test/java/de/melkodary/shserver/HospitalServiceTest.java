package de.melkodary.shserver;

import de.melkodary.shserver.model.Hospital;
import de.melkodary.shserver.service.HospitalService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Sql(scripts = "/data.sql")
public class HospitalServiceTest {

    @Autowired
    private HospitalService hospitalService;

    @Test
    public void getAllHospitalsTest() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        assertFalse(hospitals.isEmpty());
    }

    @Test
    public void getHospitalByIdTest() {
        Hospital hospital = hospitalService.getHospital(1L);
        assertEquals("h1", hospital.getName());
    }
}
