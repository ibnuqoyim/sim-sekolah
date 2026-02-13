package com.school.sim.repository;

import com.school.sim.entity.Announcement;
import com.school.sim.entity.TargetAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByTargetAudienceInOrderByCreatedAtDesc(List<TargetAudience> audiences);
}
