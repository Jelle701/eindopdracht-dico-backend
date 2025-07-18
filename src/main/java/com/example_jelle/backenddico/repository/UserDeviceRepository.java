// src/main/java/com/example_jelle/backenddico/repository/UserDeviceRepository.java
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
}