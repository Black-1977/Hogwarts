package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public List<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("age")
    public List<Student> getStudentsByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyByStudentId(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("getStudentsCount")
    public int getStudentsCount(){
        return studentService.getStudentsCount();
    }

    @GetMapping("getAvgAgeOfStudents")
    public int getAvgAgeOfStudents(){
        return studentService.getAvgAgeOfStudents();
    }

    @GetMapping("getLast5Students")
    public List<Student> getLast5Students(){
        return studentService.getLast5Students();
    }

    @GetMapping("getStudentNamesStartsWithA")
    public List<String> getStudentNamesStartsWithA() { return studentService.getStudentNamesStartsWithA();}

    @GetMapping("getAvgAgeOfStudentsWithStreams")
    public Double getAvgAgeOfStudentsWithStreams() {return studentService.getAvgAgeOfStudentsWithStreams();}
}
