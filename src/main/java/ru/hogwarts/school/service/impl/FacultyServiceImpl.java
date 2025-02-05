package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        oldFaculty.setName(faculty.getName());
        oldFaculty.setColor(faculty.getColor());
        return facultyRepository.save(oldFaculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> getFacultiesByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public List<Student> getStudentsById(Long id) {
        return Objects.requireNonNull(facultyRepository.findById(id).orElse(null)).getStudents();
    }

}
