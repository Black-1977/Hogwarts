package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long count = 0L;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++count);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .toList();
    }
}
