package com.school.sim.controller;

import com.school.sim.entity.Role;
import com.school.sim.entity.Teacher;
import com.school.sim.entity.User;
import com.school.sim.payload.request.TeacherRequest;
import com.school.sim.repository.TeacherRepository;
import com.school.sim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teachers")
@PreAuthorize("hasAuthority('OPERATOR') or hasAuthority('ADMIN')")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody TeacherRequest request) {
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
        user.setRole(Role.TEACHER);
        userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setBio(request.getBio());
        teacher.setSubjects(request.getSubjects());
        teacherRepository.save(teacher);

        return ResponseEntity.ok("Teacher registered successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        return teacherRepository.findById(id).map(teacher -> {
            teacherRepository.delete(teacher);
            userRepository.delete(teacher.getUser()); // Data integrity? cascade? for now delete user too
            return ResponseEntity.ok("Teacher deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }
}
