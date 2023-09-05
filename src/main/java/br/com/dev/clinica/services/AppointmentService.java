package br.com.dev.clinica.services;

import br.com.dev.clinica.domain.appointment.Appointment;
import br.com.dev.clinica.domain.appointment.AppointmentRequestDTO;
import br.com.dev.clinica.domain.appointment.AppointmentResponseDTO;
import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;
import br.com.dev.clinica.repositories.AppointmentRepository;
import br.com.dev.clinica.repositories.DoctorRepository;
import br.com.dev.clinica.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO data) throws Exception {
        Appointment appointment = new Appointment(data);

        Optional<Patient> patient = patientRepository.findById(data.patient().getId());

        if(!patient.isPresent()) throw new Exception("Patient not found");

        Optional<Doctor> doctor = doctorRepository.findById(data.doctor().getId());

        if(!doctor.isPresent()) throw new Exception("Doctor not found");

        appointment.setPatient(patient.get());
        appointment.setDoctor(doctor.get());

        appointmentRepository.save(appointment);

        return buildAppointmentInfo(appointment);
    }

    public List<AppointmentResponseDTO> findAll() {
        return appointmentRepository
                .findAll()
                .stream()
                .map(appointment -> buildAppointmentInfo(appointment))
                .collect(Collectors.toList());
    }

    private AppointmentResponseDTO buildAppointmentInfo(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDescription(),
                appointment.getAppointmentDatetime(),
                appointment.getAppointmentInitialDatetime(),
                appointment.getAppointmentFinishDatetime(),
                appointment.getConsultType(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                appointment.getRating(),
                appointment.getReview()
        );
    }

    public AppointmentResponseDTO findById(UUID id) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if(!appointment.isPresent()) throw new Exception("Appointment not found");

        return buildAppointmentInfo(appointment.get());
    }

    @Transactional
    public AppointmentResponseDTO update(UUID id, AppointmentRequestDTO data) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if(!appointment.isPresent()) throw new Exception("Appointment not found");

        Optional<Patient> patient = patientRepository.findById(data.patient().getId());

        if(!patient.isPresent()) throw new Exception("Patient not found");

        Optional<Doctor> doctor = doctorRepository.findById(data.doctor().getId());

        if(!doctor.isPresent()) throw new Exception("Doctor not found");

        Appointment appointmentToUpdate = appointment.get();

        appointmentToUpdate.setDescription(data.description());
        appointmentToUpdate.setAppointmentDatetime(data.appointmentDatetime());
        appointmentToUpdate.setAppointmentInitialDatetime(data.appointmentInitialDatetime());
        appointmentToUpdate.setAppointmentFinishDatetime(data.appointmentFinishDatetime());
        appointmentToUpdate.setConsultType(data.consultType());
        appointmentToUpdate.setPatient(data.patient());
        appointmentToUpdate.setDoctor(data.doctor());
        appointmentToUpdate.setRating(data.rating());
        appointmentToUpdate.setReview(data.review());

        appointmentRepository.save(appointmentToUpdate);

        return buildAppointmentInfo(appointmentToUpdate);
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if(!appointment.isPresent()) throw new Exception("Appointment not found");

        Appointment appointmentToDelete = appointment.get();

        appointmentRepository.delete(appointmentToDelete);
    }
}
