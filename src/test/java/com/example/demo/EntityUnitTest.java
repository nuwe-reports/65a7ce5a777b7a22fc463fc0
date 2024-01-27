package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     *
     * Who tests POJO's? there's no logic in there apart from the function overlaps in appointment which should be extracted
     * into a utils file or something like that. File name is not self-explanatory.
     */
    
    @Test
    void shouldOverlapAppointments(){
        p1 = new Patient("NombreP", "ApellidoP", 39, "email@email.com");
        d1 = new Doctor("NombreD", "ApellidoD", 29, "emailD@email.com");
        r1 = new Room("NombreR");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2024, 1, 27, 12, 20), LocalDateTime.now());
        a2 = new Appointment(p1, d1, r1, LocalDateTime.of(2024, 1, 27, 12, 20), LocalDateTime.now());
        
        assertThat(a1.overlaps(a2)).isTrue();
    }
    
    @Test
    void shouldNotOverlapAppointments(){
        p1 = new Patient("NombreP", "ApellidoP", 39, "email@email.com");
        d1 = new Doctor("NombreD", "ApellidoD", 29, "emailD@email.com");
        r1 = new Room("NombreR");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 1, 27, 12, 20), LocalDateTime.of(2023, 1, 27, 13, 30));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.of(2024, 3, 21, 11, 30), LocalDateTime.of(2024, 3, 21, 12, 30));

        assertThat(a1.overlaps(a2)).isFalse();
    }
}
