package com.school.sim.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "school_profile")
public class SchoolProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String logoUrl;

    private String contactEmail;

    private String contactPhone;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
