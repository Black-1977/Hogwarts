package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());
        return studentRepository.save(oldStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(Long id) {
        return Objects.requireNonNull(studentRepository.findById(id).orElse(null)).getFaculty();
    }
}
