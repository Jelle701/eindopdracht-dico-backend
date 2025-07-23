// src/main/java/com/example_jelle/backenddico/dto/onboarding/OnboardingRequestDto.java
package com.example_jelle.backenddico.dto.onboarding;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;

// Dit is de hoofd-DTO die alle data van het onboardingproces bevat
public class OnboardingRequestDto {
    private String role;
    private PreferencesDto preferences;
    private MedicineInfoDto medicineInfo;
    private List<DeviceDto> diabeticDevices;


    @NotNull(message = "Email is verplicht")
    @Email(message = "Ongeldig e-mailadres")
    private String email;

    @NotNull(message = "Wachtwoord is verplicht")
    @Size(min = 6, message = "Wachtwoord moet minimaal 6 tekens zijn")
    private String password;

    // Getters en Setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public PreferencesDto getPreferences() { return preferences; }
    public void setPreferences(PreferencesDto preferences) { this.preferences = preferences; }

    public MedicineInfoDto getMedicineInfo() { return medicineInfo; }
    public void setMedicineInfo(MedicineInfoDto medicineInfo) { this.medicineInfo = medicineInfo; }

    public List<DeviceDto> getDiabeticDevices() { return diabeticDevices; }
    public void setDiabeticDevices(List<DeviceDto> diabeticDevices) { this.diabeticDevices = diabeticDevices; }
}