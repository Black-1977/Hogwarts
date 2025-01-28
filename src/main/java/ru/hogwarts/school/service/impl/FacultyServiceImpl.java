package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }
}
