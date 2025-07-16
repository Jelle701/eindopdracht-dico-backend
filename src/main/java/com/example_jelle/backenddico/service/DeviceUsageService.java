package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.DeviceUsage;
import com.example_jelle.backenddico.repository.DeviceUsageRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceUsageService {

    private final DeviceUsageRepository repository;

    public DeviceUsageService(DeviceUsageRepository repository) {
        this.repository = repository;
    }

    public DeviceUsage save(DeviceUsage usage) {
        return repository.save(usage);
    }
}
