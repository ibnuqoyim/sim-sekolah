package com.school.sim.controller;

import com.school.sim.entity.Agenda;
import com.school.sim.entity.TargetAudience;
import com.school.sim.entity.User;
import com.school.sim.repository.AgendaRepository;
import com.school.sim.repository.UserRepository;
import com.school.sim.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/agendas")
public class AgendaController {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Agenda> getAllAgendas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        List<TargetAudience> audiences = new ArrayList<>();
        audiences.add(TargetAudience.ALL);

        if (role.equals("TEACHER") || role.equals("OPERATOR") || role.equals("ADMIN")) {
            audiences.add(TargetAudience.TEACHERS);
        }
        if (role.equals("STUDENT") || role.equals("OPERATOR") || role.equals("ADMIN")) {
            audiences.add(TargetAudience.STUDENTS);
        }

        return agendaRepository.findByTargetAudienceInOrderByEventDateDesc(audiences);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OPERATOR') or hasAuthority('TEACHER')")
    public ResponseEntity<?> createAgenda(@RequestBody Agenda agenda) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User author = userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User not found."));

        agenda.setAuthor(author);
        Agenda savedAgenda = agendaRepository.save(agenda);
        return ResponseEntity.ok(savedAgenda);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OPERATOR') or hasAuthority('TEACHER')")
    public ResponseEntity<?> deleteAgenda(@PathVariable Long id) {
        if (!agendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // In a real app, strict ownership checks might be needed for Teachers
        agendaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
