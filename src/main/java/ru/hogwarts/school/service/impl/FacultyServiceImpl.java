package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        logger.info("Faculty added: " + faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        logger.info("Faculty found: " + faculty);
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        oldFaculty.setName(faculty.getName());
        oldFaculty.setColor(faculty.getColor());
        logger.info("Faculty updated: " + oldFaculty);
        return facultyRepository.save(oldFaculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Faculty deleted: " + id);
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Faculty found by color: " + color);
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> getFacultiesByNameOrColor(String name, String color) {
        logger.info("Faculty found by name: " + name + " and color: " + color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public List<Student> getStudentsById(Long id) {
        logger.info("Student found by faculty id: " + id);
        return Objects.requireNonNull(facultyRepository.findById(id).orElse(null)).getStudents();
    }

    @Override
    public String getLongestFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }

}
