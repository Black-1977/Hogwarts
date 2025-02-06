package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FacultyControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void clearDatabase() throws Exception {
        facultyRepository.deleteAll();
    }

    @Test
    void testedAddFaculty() {
        Faculty faculty = new Faculty("Faculty1", "Black");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty", faculty, Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assert actualFaculty != null;
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }

    @Test
    void testedGetFaculty() {
        Faculty faculty = new Faculty("Faculty1", "Black");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class
        );

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Faculty actualFaculty = facultyResponseEntity.getBody();
        assert actualFaculty != null;
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }

    @Test
    void testedUpdateFaculty() {
        Faculty faculty = new Faculty("Faculty1", "Black");
        faculty = facultyRepository.save(faculty);

        Faculty newFaculty = new Faculty("Faculty2", "White");

        HttpEntity<Faculty> entity = new HttpEntity<>(newFaculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.PUT, entity, Faculty.class
        );

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Faculty actualFaculty = facultyResponseEntity.getBody();
        assert actualFaculty != null;
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), newFaculty.getName());
        assertEquals(actualFaculty.getColor(), newFaculty.getColor());
    }

    @Test
    void testedDeleteFaculty() {
        Faculty faculty = new Faculty("Faculty1", "Black");
        faculty = facultyRepository.save(faculty);

        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);

        restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE, entity, Faculty.class
        );
        Optional<Faculty> actualFaculty = facultyRepository.findById(faculty.getId());
        assertFalse(actualFaculty.isPresent());
    }

    @Test
    void getFacultiesByNameOrColor() {
    }

    @Test
    void getStudentsByFacultyId() {
    }
}