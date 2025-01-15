package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private Long count = 0L;

    @Override
    public Student addStudent(Student student) {
        student.setId(++count);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return students.get(id);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        student.setId(id);
        students.put(id, student);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        students.remove(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return students.values()
                .stream()
                .filter(it -> it.getAge() == age)
                .toList();
    }
}
