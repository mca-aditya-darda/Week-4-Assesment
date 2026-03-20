package com.capgemini.model.dto;

import lombok.Data;

@Data
public class DoctorDTO {

	private Integer doctorId;
	private String name;
	private String specialization;
	private String email;
	private String phone;
	private int totalAppointments;
}