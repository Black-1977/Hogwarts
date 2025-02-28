package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

    List<Faculty> getFacultiesByColor(String color);

    List<Faculty> getFacultiesByNameOrColor(String name, String color);

    List<Student> getStudentsById(Long id);

    String getLongestFacultyName();
}
