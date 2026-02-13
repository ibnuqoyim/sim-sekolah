package com.school.sim.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendas")
@Data
@NoArgsConstructor
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_audience", nullable = false)
    private TargetAudience targetAudience;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Agenda(String title, String description, LocalDateTime eventDate, User author, TargetAudience targetAudience) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.author = author;
        this.targetAudience = targetAudience;
    }
}
