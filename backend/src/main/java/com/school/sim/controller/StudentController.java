package com.school.sim.controller;

import com.school.sim.entity.Role;
import com.school.sim.entity.Student;
import com.school.sim.entity.User;
import com.school.sim.payload.request.StudentRequest;
import com.school.sim.repository.StudentRepository;
import com.school.sim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
@PreAuthorize("hasAuthority('OPERATOR') or hasAuthority('ADMIN')")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setGrade(request.getGrade());
        student.setClassName(request.getClassName());
        student.setEnrollmentYear(request.getEnrollmentYear());
        studentRepository.save(student);

        return ResponseEntity.ok("Student registered successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            userRepository.delete(student.getUser());
            return ResponseEntity.ok("Student deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }
}
