package com.school.sim.service;

import com.school.sim.entity.SchoolProfile;
import com.school.sim.repository.SchoolProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolProfileService {
    @Autowired
    private SchoolProfileRepository schoolProfileRepository;

    public SchoolProfile getSchoolProfile() {
        List<SchoolProfile> profiles = schoolProfileRepository.findAll();
        if (profiles.isEmpty()) {
            return null;
        }
        return profiles.get(0);
    }

    public SchoolProfile updateSchoolProfile(SchoolProfile profile) {
        SchoolProfile existing = getSchoolProfile();
        if (existing == null) {
            return schoolProfileRepository.save(profile);
        }
        existing.setName(profile.getName());
        existing.setAddress(profile.getAddress());
        existing.setLogoUrl(profile.getLogoUrl());
        existing.setContactEmail(profile.getContactEmail());
        existing.setContactPhone(profile.getContactPhone());
        return schoolProfileRepository.save(existing);
    }
}
