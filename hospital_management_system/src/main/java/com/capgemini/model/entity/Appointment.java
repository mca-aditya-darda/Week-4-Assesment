package com.capgemini.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appointmentId;

	private String patientName;

	private LocalDateTime scheduledTime;

	private String status;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	@JsonBackReference
	private Doctor doctor;
}