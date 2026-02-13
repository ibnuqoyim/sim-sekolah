package com.school.sim.controller;

import com.school.sim.entity.Announcement;
import com.school.sim.entity.TargetAudience;
import com.school.sim.entity.User;
import com.school.sim.repository.AnnouncementRepository;
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
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Announcement> getAllAnnouncements() {
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

        return announcementRepository.findByTargetAudienceInOrderByCreatedAtDesc(audiences);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OPERATOR') or hasAuthority('TEACHER')")
    public ResponseEntity<?> createAnnouncement(@RequestBody Announcement announcement) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User author = userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User not found."));

        announcement.setAuthor(author);
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return ResponseEntity.ok(savedAnnouncement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OPERATOR') or hasAuthority('TEACHER')")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long id) {
        if (!announcementRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        announcementRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
