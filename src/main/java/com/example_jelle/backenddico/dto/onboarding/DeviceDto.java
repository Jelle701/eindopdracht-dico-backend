// src/main/java/com/example_jelle/backenddico/dto/onboarding/DeviceDto.java
package com.example_jelle.backenddico.dto.onboarding;

public class DeviceDto {
    private String categorie;
    private String fabrikant;
    private String model;

    // Getters en Setters
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getFabrikant() { return fabrikant; }
    public void setFabrikant(String fabrikant) { this.fabrikant = fabrikant; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}