package de.melkodary.shserver.service;

import de.melkodary.shserver.exception.EntityExistsException;
import de.melkodary.shserver.exception.EntityNotFoundException;
import de.melkodary.shserver.model.Hospital;
import de.melkodary.shserver.model.Patient;
import de.melkodary.shserver.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospital(Long hospitalId) {
        return hospitalRepository
                .findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException(Hospital.class, hospitalId));

    }

    public Hospital createHospital(Hospital hospital) {
        try {
            return hospitalRepository.save(hospital);
        } catch (DuplicateKeyException ex) {
            throw new EntityExistsException(Hospital.class);
        }
    }

    public Hospital createHospital(String address, String name) {
        Hospital hospital = new Hospital();
        hospital.setAddress(address);
        hospital.setName(name);

        return createHospital(hospital);
    }

    public void updateHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
    }

    public void deleteHospital(Long hospitalId) {
        Hospital hospital = getHospital(hospitalId);
        hospitalRepository.delete(hospital);
    }

    public void addPatient(Hospital hospital, Patient patient) {
        Set<Patient> patients = hospital.getPatients();
        patients.add(patient);
        hospital.setPatients(patients);

        hospitalRepository.save(hospital);
    }

    public List<Hospital> getHospitals(Patient patient) {
        return patient.getHospitals().stream().toList();
    }
}
