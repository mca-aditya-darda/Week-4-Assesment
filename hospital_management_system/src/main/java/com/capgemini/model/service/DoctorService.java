package com.capgemini.model.service;

import com.capgemini.model.dto.DoctorDTO;
import com.capgemini.model.entity.Doctor;
import com.capgemini.model.exception.DuplicateResourceException;
import com.capgemini.model.exception.ResourceNotFoundException;
import com.capgemini.model.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	public DoctorDTO createDoctor(Doctor doctor) {

		if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
			throw new DuplicateResourceException("Email already registered");
		}

		Doctor saved = doctorRepository.save(doctor);

		DoctorDTO dto = new DoctorDTO();
		dto.setDoctorId(saved.getDoctorId());
		dto.setName(saved.getName());
		dto.setSpecialization(saved.getSpecialization());
		dto.setEmail(saved.getEmail());
		dto.setPhone(saved.getPhone());
		dto.setTotalAppointments(0);

		return dto;
	}

	public List<DoctorDTO> getAllDoctors() {
		return doctorRepository.findAll().stream().map(d -> {
			DoctorDTO dto = new DoctorDTO();
			dto.setDoctorId(d.getDoctorId());
			dto.setName(d.getName());
			dto.setSpecialization(d.getSpecialization());
			dto.setEmail(d.getEmail());
			dto.setPhone(d.getPhone());
			dto.setTotalAppointments(d.getAppointments() == null ? 0 : d.getAppointments().size());
			return dto;
		}).collect(Collectors.toList());
	}

	public DoctorDTO getDoctorById(int id) {

		Doctor d = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

		DoctorDTO dto = new DoctorDTO();
		dto.setDoctorId(d.getDoctorId());
		dto.setName(d.getName());
		dto.setSpecialization(d.getSpecialization());
		dto.setEmail(d.getEmail());
		dto.setPhone(d.getPhone());
		dto.setTotalAppointments(d.getAppointments() == null ? 0 : d.getAppointments().size());

		return dto;
	}

	public DoctorDTO updateDoctor(int id, Doctor updated) {

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

		doctor.setName(updated.getName());
		doctor.setSpecialization(updated.getSpecialization());
		doctor.setEmail(updated.getEmail());
		doctor.setPhone(updated.getPhone());

		Doctor saved = doctorRepository.save(doctor);

		DoctorDTO dto = new DoctorDTO();
		dto.setDoctorId(saved.getDoctorId());
		dto.setName(saved.getName());
		dto.setSpecialization(saved.getSpecialization());
		dto.setEmail(saved.getEmail());
		dto.setPhone(saved.getPhone());
		dto.setTotalAppointments(saved.getAppointments() == null ? 0 : saved.getAppointments().size());

		return dto;
	}

	public void deleteDoctor(int id) {

		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));

		doctorRepository.delete(doctor);
	}
}