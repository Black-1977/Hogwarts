package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> getStudentsByAge(int age);

    List<Student> getStudentsByAgeBetween(int minAge, int maxAge);

    Faculty getFaculty(Long id);

    int getStudentsCount();

    int getAvgAgeOfStudents();

    List<Student> getLast5Students();

    List<String> getStudentNamesStartsWithA();

    Double getAvgAgeOfStudentsWithStreams();

    void printParallel();

    void printSynchronized();
}
