package com.capgemini.model.controller;

import com.capgemini.model.dto.DoctorDTO;
import com.capgemini.model.entity.Doctor;
import com.capgemini.model.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@PostMapping
	public DoctorDTO createDoctor(@RequestBody Doctor doctor) {
		return doctorService.createDoctor(doctor);
	}

	@GetMapping
	public List<DoctorDTO> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	@GetMapping("/{id}")
	public DoctorDTO getDoctor(@PathVariable int id) {
		return doctorService.getDoctorById(id);
	}

	@PutMapping("/{id}")
	public DoctorDTO updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
		return doctorService.updateDoctor(id, doctor);
	}

	@DeleteMapping("/{id}")
	public String deleteDoctor(@PathVariable int id) {
		doctorService.deleteDoctor(id);
		return "Doctor deleted successfully";
	}
}