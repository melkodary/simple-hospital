package de.melkodary.shserver.service;

import de.melkodary.shserver.exception.EntityExistsException;
import de.melkodary.shserver.exception.EntityNotFoundException;
import de.melkodary.shserver.model.Patient;
import de.melkodary.shserver.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

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

    public void deletePatient(Long patientId) {
        Patient patient = getPatient(patientId);
        patientRepository.delete(patient);
    }

    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }
}
