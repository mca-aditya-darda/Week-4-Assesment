package com.capgemini.model.dto;

import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Integer appointmentId;
    private String patientName;

    @Future
    private LocalDateTime scheduledTime;

    private String status;
}