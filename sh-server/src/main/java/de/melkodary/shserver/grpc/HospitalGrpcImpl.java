package de.melkodary.shserver.grpc;

import de.melkodary.sh.grpc.lib.*;
import de.melkodary.shserver.model.Hospital;
import de.melkodary.shserver.model.Patient;
import de.melkodary.shserver.service.HospitalService;
import de.melkodary.shserver.service.PatientService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HospitalGrpcImpl extends HospitalServiceGrpc.HospitalServiceImplBase {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private PatientService patientService;

    @Override
    public void createHospital(HospitalRequest request, StreamObserver<HospitalResponse> responseObserver) {
        Hospital hospital = hospitalService.createHospital(request.getAddress(), request.getName());
        HospitalResponse response = HospitalResponse
                .newBuilder()
                .setId(hospital.getId())
                .setName(hospital.getName())
                .setAddress(hospital.getAddress())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateHospital(HospitalRequest request, StreamObserver<HospitalResponse> responseObserver) {
        Hospital hospital = hospitalService.getHospital(request.getId());
        hospital.setAddress(request.getAddress().isEmpty() ? hospital.getAddress() : request.getAddress());
        hospital.setName(request.getName().isEmpty() ? hospital.getName() : request.getName());

        hospitalService.updateHospital(hospital);
        HospitalResponse response = HospitalResponse
                .newBuilder()
                .setId(hospital.getId())
                .setName(hospital.getName())
                .setAddress(hospital.getAddress())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteHospital(HospitalRequest request, StreamObserver<EmptyResponse> responseObserver) {
        hospitalService.deleteHospital(request.getId());
        EmptyResponse response = EmptyResponse
                .newBuilder()
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createPatient(PatientRequest request, StreamObserver<PatientResponse> responseObserver) {
        Patient patient = patientService.createPatient(Patient.SEX.valueOf(request.getSex()), request.getName());
        PatientResponse response = PatientResponse
                .newBuilder()
                .setId(patient.getId())
                .setName(patient.getName())
                .setSex(patient.getSex().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updatePatient(PatientRequest request, StreamObserver<PatientResponse> responseObserver) {
        Patient patient = patientService.getPatient(request.getId());
        patient.setName(request.getName().isEmpty() ? patient.getName() : request.getName());
        patient.setSex(request.getSex().isEmpty() ? patient.getSex() : Patient.SEX.valueOf(request.getSex()));
        patientService.updatePatient(patient);

        PatientResponse response = PatientResponse
                .newBuilder()
                .setId(patient.getId())
                .setName(patient.getName())
                .setSex(patient.getSex().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePatient(PatientRequest request, StreamObserver<EmptyResponse> responseObserver) {
        patientService.deletePatient(request.getId());
        EmptyResponse response = EmptyResponse
                .newBuilder()
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void registerPatientInHospital(PatientHospitalRegistrationRequest request, StreamObserver<EmptyResponse> responseObserver) {
        Patient patient = patientService.getPatient(request.getPatientId());
        Hospital hospital = hospitalService.getHospital(request.getHospitalId());

        hospitalService.addPatient(hospital, patient);

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void listAllHospitalsOfPatient(PatientRequest request, StreamObserver<HospitalListResponse> responseObserver) {
        Patient patient = patientService.getPatient(request.getId());

        List<Hospital> hospitals = patient.getHospitals().stream().toList();
        HospitalListResponse response = HospitalListResponse
                .newBuilder()
                .addAllHospitals(
                        hospitals
                                .stream()
                                .map(hospital ->
                                        HospitalResponse
                                                .newBuilder()
                                                .setId(hospital.getId())
                                                .setName(hospital.getName())
                                                .setAddress(hospital.getAddress())
                                                .build()
                                ).toList()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void listAllPatientsOfHospital(HospitalRequest request, StreamObserver<PatientListResponse> responseObserver) {
        Hospital hospital = hospitalService.getHospital(request.getId());

        List<Patient> patients = hospital.getPatients().stream().toList();
        PatientListResponse response = PatientListResponse
                .newBuilder()
                .addAllPatients(
                        patients
                                .stream()
                                .map(patient ->
                                        PatientResponse
                                                .newBuilder()
                                                .setId(patient.getId())
                                                .setName(patient.getName())
                                                .setSex(patient.getSex().toString())
                                                .build()
                                ).toList()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
