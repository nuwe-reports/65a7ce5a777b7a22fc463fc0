
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception {
        Person d = new Doctor("Name", "surname", 38, "email@email.com");
        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(d))).andExpect(status().isCreated());
    }
    
    @Test
    void shouldGetAllDoctors() throws Exception {
        Doctor d1 = new Doctor("Name", "surname", 38, "email@email.com");
        Doctor d2 = new Doctor("Name", "surname", 38, "email@email.com");
        
        List<Doctor> doctors = new ArrayList<Doctor>();
        
        doctors.add(d1);
        doctors.add(d2);
        
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors")).andExpect(status().isOk());
        
    }
    @Test
    void shouldNotGetDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<Doctor>();


        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors")).andExpect(status().isNoContent());

    }
    
    @Test
    void shouldGetDoctorById() throws Exception{
        Doctor d1 = new Doctor("Name", "surname", 38, "email@email.com");
        d1.setId(1);
        Optional<Doctor> optDoctor = Optional.of(d1);
        
        assertThat(optDoctor).isPresent();
        assertThat(optDoctor.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);
        
        when(doctorRepository.findById(d1.getId())).thenReturn(optDoctor);
        mockMvc.perform(get("/api/doctors/"+d1.getId()))
        .andExpect(status().isOk());
    }
    @Test
    void shouldNotGetDoctorById() throws Exception{
        long id = 12;
        mockMvc.perform(get("/api/doctors/"+id))
        .andExpect(status().isNotFound());
    }
    @Test
    void shouldDeleteDoctor() throws Exception{
        Doctor d1 = new Doctor("Name", "surname", 38, "email@email.com");
        d1.setId(1);
        Optional<Doctor> optDoctor = Optional.of(d1);

        assertThat(optDoctor).isPresent();
        assertThat(optDoctor.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);

        when(doctorRepository.findById(d1.getId())).thenReturn(optDoctor);
        mockMvc.perform(delete("/api/doctors/"+d1.getId()))
        .andExpect(status().isOk());
    }
    
    @Test
    void shouldNotDeleteDoctor() throws Exception{
        long id = 12;
        mockMvc.perform(delete("/api/doctors/"+id))
        .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldDeleteAllDoctors() throws Exception {
        mockMvc.perform(delete("/api/doctors")).andExpect(status().isOk());
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatient() throws Exception {
        Patient p = new Patient("Name", "surname", 38, "email@email.com");
        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(p))).andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        Patient d1 = new Patient("Name", "surname", 38, "email@email.com");
        Patient d2 = new Patient("Name", "surname", 38, "email@email.com");

        List<Patient> patients = new ArrayList<Patient>();

        patients.add(d1);
        patients.add(d2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients")).andExpect(status().isOk());

    }
    @Test
    void shouldNotGetPatients() throws Exception {
        List<Patient> patients = new ArrayList<Patient>();


        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients")).andExpect(status().isNoContent());

    }

    @Test
    void shouldGetPatientById() throws Exception{
        Patient d1 = new Patient("Name", "surname", 38, "email@email.com");
        d1.setId(1);
        Optional<Patient> optPatient = Optional.of(d1);

        assertThat(optPatient).isPresent();
        assertThat(optPatient.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);

        when(patientRepository.findById(d1.getId())).thenReturn(optPatient);
        mockMvc.perform(get("/api/patients/"+d1.getId()))
        .andExpect(status().isOk());
    }
    @Test
    void shouldNotGetPatientById() throws Exception{
        long id = 12;
        mockMvc.perform(get("/api/patients/"+id))
        .andExpect(status().isNotFound());
    }
    @Test
    void shouldDeletePatient() throws Exception{
        Patient d1 = new Patient("Name", "surname", 38, "email@email.com");
        d1.setId(1);
        Optional<Patient> optPatient = Optional.of(d1);

        assertThat(optPatient).isPresent();
        assertThat(optPatient.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);

        when(patientRepository.findById(d1.getId())).thenReturn(optPatient);
        mockMvc.perform(delete("/api/patients/"+d1.getId()))
        .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatient() throws Exception{
        long id = 12;
        mockMvc.perform(delete("/api/patients/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception {
        mockMvc.perform(delete("/api/patients")).andExpect(status().isOk());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception {
        Room r = new Room("Name");
        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(r))).andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllRooms() throws Exception {
        Room r1 = new Room("Name1");
        Room r2 = new Room("Name2");

        List<Room> rooms = new ArrayList<Room>();

        rooms.add(r1);
        rooms.add(r2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms")).andExpect(status().isOk());

    }
    @Test
    void shouldNotGetRooms() throws Exception {
        List<Room> rooms = new ArrayList<Room>();


        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms")).andExpect(status().isNoContent());

    }

    @Test
    void shouldGetRoomByRoomName() throws Exception{
        Room r1 = new Room("Name");
        Optional<Room> optRoom = Optional.of(r1);

        assertThat(optRoom).isPresent();
        assertThat(optRoom.get().getRoomName()).isEqualTo(r1.getRoomName());

        when(roomRepository.findByRoomName(r1.getRoomName())).thenReturn(optRoom);
        mockMvc.perform(get("/api/rooms/"+r1.getRoomName()))
        .andExpect(status().isOk());
    }
    @Test
    void shouldNotGetRoomByRoomName() throws Exception{
        String name = "FakeName";
        mockMvc.perform(get("/api/rooms/"+name))
        .andExpect(status().isNotFound());
    }
    @Test
    void shouldDeleteRoom() throws Exception{
        Room r1 = new Room("Name");
        Optional<Room> optRoom = Optional.of(r1);

        assertThat(optRoom).isPresent();
        assertThat(optRoom.get().getRoomName()).isEqualTo(r1.getRoomName());

        when(roomRepository.findByRoomName(r1.getRoomName())).thenReturn(optRoom);
        mockMvc.perform(delete("/api/rooms/"+r1.getRoomName()))
        .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception{
        long id = 12;
        mockMvc.perform(delete("/api/rooms/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        mockMvc.perform(delete("/api/rooms")).andExpect(status().isOk());
    }

}
