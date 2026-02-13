package com.school.sim.controller;

import com.school.sim.entity.SchoolProfile;
import com.school.sim.service.SchoolProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/school")
public class SchoolProfileController {
    @Autowired
    private SchoolProfileService schoolProfileService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        SchoolProfile profile = schoolProfileService.getSchoolProfile();
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateProfile(@RequestBody SchoolProfile profile) {
        SchoolProfile updated = schoolProfileService.updateSchoolProfile(profile);
        return ResponseEntity.ok(updated);
    }
}
