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
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void testedAddFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Faculty1", "Black");
        Faculty savedfaculty = new Faculty("Faculty2", "White");
        savedfaculty.setId(facultyId);

        when(facultyService.addFaculty(faculty)).thenReturn(savedfaculty);

        ResultActions perform = mockMvc.perform(
                post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty))
        );

        perform
                .andExpect(jsonPath("$.id").value(savedfaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedfaculty.getName()))
                .andExpect((jsonPath("$.color").value(savedfaculty.getColor()))
                );

        perform.andDo(print());
    }

    @Test
    void testedGetFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Faculty1", "Black");

        when(facultyService.getFaculty(facultyId)).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(
                get("/faculty/{id}", facultyId));

        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect((jsonPath("$.color").value(faculty.getColor()))
                );

        perform.andDo(print());
    }

    @Test
    void testedUpdateFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Faculty1", "Black");

        when(facultyService.updateFaculty(facultyId, faculty)).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(
                put("/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty))
        );

        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect((jsonPath("$.color").value(faculty.getColor()))
                );

        perform.andDo(print());
    }

    @Test
    void testedDeleteFaculty() throws Exception {
        Long facultyId = 1L;

        ResultActions perform = mockMvc.perform(delete("/faculty/{id}", facultyId));

        perform.andExpect(status().isOk());
    }

    @Test
    void testedGetFacultiesByNaneOrColor() throws Exception {
        String text = "Black";
        Faculty faculty = new Faculty("Faculty1", "Black");

        when(facultyService.getFacultiesByNameOrColor(text, text)).thenReturn(List.of(faculty));

        ResultActions perform = mockMvc.perform(
                get("/faculty")
                        .param("text", text)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty))
        );

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testedGetStudentsByFacultyId() throws Exception {
        Long facultyId = 1L;
        Student student = new Student("Ivan", 20);

        when(facultyService.getStudentsById(facultyId)).thenReturn(List.of(student));

        ResultActions perform = mockMvc.perform(
                get("/faculty/{id}/students", facultyId));

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }
}