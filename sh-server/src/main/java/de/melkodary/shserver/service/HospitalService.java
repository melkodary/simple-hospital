package de.melkodary.shserver.service;

import de.melkodary.shserver.exception.EntityExistsException;
import de.melkodary.shserver.exception.EntityNotFoundException;
import de.melkodary.shserver.model.Hospital;
import de.melkodary.shserver.model.Patient;
import de.melkodary.shserver.repository.HospitalRepository;
import de.melkodary.shserver.repository.PatientRepository;
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

    public Set<Patient> getPatients(Long hospitalId) {
        return getHospital(hospitalId).getPatients();
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

    public Hospital deleteHospital(Long hospitalId) {
        Hospital hospital = getHospital(hospitalId);
        hospitalRepository.delete(hospital);

        return hospital;
    }

    public Hospital addPatient(Hospital hospital, Patient patient) {
        Set<Patient> patients = hospital.getPatients();
        patients.add(patient);
        hospital.setPatients(patients);

        hospitalRepository.save(hospital);

        return hospital;
    }

    public List<Hospital> getHospitals(Patient patient) {
        return patient.getHospitals().stream().toList();
    }

    private final PatientRepository patientRepository;

    public Patient getPatient(Long id) {
        return patientRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Patient.class, id));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient createPatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch (DuplicateKeyException ex) {
            throw new EntityExistsException(Patient.class);
        }
    }

    public Patient createPatient(Patient.SEX sex, String name) {
        Patient patient = new Patient();
        patient.setSex(sex);
        patient.setName(name);

        return createPatient(patient);
    }

    public Patient deletePatient(Long patientId) {
        Patient patient = getPatient(patientId);
        patientRepository.delete(patient);

        return patient;
    }
}
