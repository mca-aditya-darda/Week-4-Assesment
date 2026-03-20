package com.capgemini.model.repository;

import com.capgemini.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByStatusOrderByScheduledTimeAsc(String status);
}