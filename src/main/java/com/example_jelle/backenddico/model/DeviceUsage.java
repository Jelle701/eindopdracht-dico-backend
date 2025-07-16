package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
public class DeviceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean usesCGM;
    private Boolean usesInsulinPump;

    // Getters and setters
}
