package com.capgemini.model.service;

import com.capgemini.model.dto.AppointmentDTO;
import com.capgemini.model.entity.Appointment;
import com.capgemini.model.entity.Doctor;
import com.capgemini.model.exception.ResourceNotFoundException;
import com.capgemini.model.repository.AppointmentRepository;
import com.capgemini.model.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public AppointmentDTO createAppointment(int doctorId, AppointmentDTO dto) {

		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorId + " not found"));

		if (dto.getScheduledTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Scheduled time must be in future");
		}

		Appointment appointment = new Appointment();
		appointment.setPatientName(dto.getPatientName());
		appointment.setScheduledTime(dto.getScheduledTime());
		appointment.setStatus(dto.getStatus());
		appointment.setDoctor(doctor);

		Appointment saved = appointmentRepository.save(appointment);

		dto.setAppointmentId(saved.getAppointmentId());
		return dto;
	}

	public Page<Appointment> getAppointments(int doctorId, int page, int size) {

		doctorRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorId + " not found"));

		Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledTime").ascending());

		return appointmentRepository.findAll(pageable);
	}

	public Appointment getAppointmentById(int doctorId, int apptId) {

		return appointmentRepository.findById(apptId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + apptId + " not found"));
	}

	public Appointment updateAppointment(int doctorId, int apptId, AppointmentDTO dto) {

		Appointment appointment = appointmentRepository.findById(apptId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + apptId + " not found"));

		appointment.setPatientName(dto.getPatientName());
		appointment.setScheduledTime(dto.getScheduledTime());
		appointment.setStatus(dto.getStatus());

		return appointmentRepository.save(appointment);
	}

	public void deleteAppointment(int doctorId, int apptId) {

		Appointment appointment = appointmentRepository.findById(apptId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + apptId + " not found"));

		appointmentRepository.delete(appointment);
	}

	public java.util.List<Appointment> getPendingAppointments() {
		return appointmentRepository.findByStatusOrderByScheduledTimeAsc("PENDING");
	}
}