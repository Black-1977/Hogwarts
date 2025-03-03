package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        studentRepository.save(student);
        logger.info("Added student {}", student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        logger.info("Retrieved student {}", student);
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());
        logger.info("Updated student {}", oldStudent);
        return studentRepository.save(oldStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Deleted student {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        logger.info("Retrieving students by age {}", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Retrieved student between {} and {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Retrieving faculty {}", id);
        return Objects.requireNonNull(studentRepository.findById(id).orElse(null)).getFaculty();
    }

    @Override
    public int getStudentsCount() {
        logger.info("Retrieving students count");
        return studentRepository.getStudentsCount();
    }

    @Override
    public int getAvgAgeOfStudents() {
        logger.info("Retrieving average age of students");
        return studentRepository.getAvgAgeOfStudents();
    }

    @Override
    public List<Student> getLast5Students() {
        logger.info("Retrieving last 5 students");
        return studentRepository.getLast5Students();
    }

    @Override
    public List<String> getStudentNamesStartsWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(it -> it.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @Override
    public Double getAvgAgeOfStudentsWithStreams() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @Override
    public void printParallel() {
        List<Student> students = studentRepository.findAll();

        printStudentName(students.get(0));
        printStudentName(students.get(1));

        new Thread(() -> {
            printStudentName(students.get(2));
            printStudentName(students.get(3));
        }).start();

        new Thread(() -> {
            printStudentName(students.get(4));
            printStudentName(students.get(5));
        }).start();
    }

    @Override
    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();
        Object LOCK = new Object();

        printStudentName(students.get(0));
        printStudentName(students.get(1));

        new Thread(() -> {
            synchronized (LOCK) {
                printStudentName(students.get(2));
                printStudentName(students.get(3));
            }
        }).start();

        new Thread(() -> {
            synchronized (LOCK) {
                printStudentName(students.get(4));
                printStudentName(students.get(5));
            }
        }).start();
    }

    private void printStudentName(Student student) {
        System.out.println(student.getName());
    }
}
