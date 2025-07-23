package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import com.example_jelle.backenddico.model.User;

import javax.persistence.*;

@Entity
public class DeviceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean usesCGM;
    private Boolean usesInsulinPump;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and setters ...
}
