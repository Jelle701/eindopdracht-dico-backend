// src/main/java/com/example_jelle/backenddico/dto/onboarding/MedicineInfoDto.java
package com.example_jelle.backenddico.dto.onboarding;

import java.util.List;

public class MedicineInfoDto {
    private String usesMedication;
    private List<MedicationDetailDto> medicationDetails;

    // Getters en Setters
    public String getUsesMedication() { return usesMedication; }
    public void setUsesMedication(String usesMedication) { this.usesMedication = usesMedication; }

    public List<MedicationDetailDto> getMedicationDetails() { return medicationDetails; }
    public void setMedicationDetails(List<MedicationDetailDto> medicationDetails) { this.medicationDetails = medicationDetails; }
}