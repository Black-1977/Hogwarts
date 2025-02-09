package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;


    @Test
    void testedAddStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);
        Student savedStudent = new Student("Ivan", 20);
        savedStudent.setId(studentId);

        when(studentService.addStudent(student)).thenReturn(savedStudent);

        ResultActions perform = mockMvc.perform(
                post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );

        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect((jsonPath("$.age").value(savedStudent.getAge()))
                );

        perform.andDo(print());
    }

    @Test
    void testedGetStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);

        when(studentService.getStudent(studentId)).thenReturn(student);

        ResultActions perform = mockMvc.perform(
                get("/student/{id}", studentId));

        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect((jsonPath("$.age").value(student.getAge()))
                );

        perform.andDo(print());
    }

    @Test
    void testedUpdateStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);

        when(studentService.updateStudent(studentId, student)).thenReturn(student);

        ResultActions perform = mockMvc.perform(
                put("/student/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );

        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect((jsonPath("$.age").value(student.getAge()))
                );

        perform.andDo(print());
    }

    @Test
    void testedDeleteStudent() throws Exception {
        Long studentId = 1L;

        ResultActions perform = mockMvc.perform(delete("/student/{id}", studentId));

        perform.andExpect(status().isOk());
    }

    @Test
    void testedGetStudentsByAge() throws Exception {
        int age = 20;
        Student student = new Student(1L, "Ivan", 20);

        when(studentService.getStudentsByAge(age)).thenReturn(List.of(student));

        ResultActions perform = mockMvc.perform(
                get("/student")
                        .param("age", String.valueOf(age))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testedGetStudentsByAgeBetween() throws Exception {
        int minAge = 20;
        int maxAge = 26;
        Student student = new Student(1L, "Ivan", 20);

        when(studentService.getStudentsByAgeBetween(minAge, maxAge)).thenReturn(List.of(student));

        ResultActions perform = mockMvc.perform(
                get("/student/age")
                        .param("minAge", String.valueOf(minAge))
                        .param("maxAge", String.valueOf(maxAge))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testedGetFacultyByStudentId() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);
        Faculty faculty = new Faculty("Faculty1","Black");
        student.setFaculty(faculty);

        when(studentService.getFaculty(studentId)).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(
                get("/student/{id}/faculty", studentId));

        perform
                .andExpect(jsonPath("$.name").value(student.getFaculty().getName()))
                .andExpect((jsonPath("$.color").value(student.getFaculty().getColor())))
                .andDo(print());
    }
}