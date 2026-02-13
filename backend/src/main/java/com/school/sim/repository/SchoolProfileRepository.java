package com.school.sim.repository;

import com.school.sim.entity.SchoolProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolProfileRepository extends JpaRepository<SchoolProfile, Long> {
}
