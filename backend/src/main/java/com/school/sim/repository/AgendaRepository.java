package com.school.sim.repository;

import com.school.sim.entity.Agenda;
import com.school.sim.entity.TargetAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByTargetAudienceInOrderByEventDateDesc(List<TargetAudience> audiences);
}
