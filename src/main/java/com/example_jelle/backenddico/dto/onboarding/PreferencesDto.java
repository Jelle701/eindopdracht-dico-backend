// src/main/java/com/example_jelle/backenddico/dto/onboarding/PreferencesDto.java
package com.example_jelle.backenddico.dto.onboarding;

public class PreferencesDto {
    private String system;
    private String gender;
    private String weight;
    private String height;
    private String bmi;

    // Getters en Setters
    public String getSystem() { return system; }
    public void setSystem(String system) { this.system = system; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getBmi() { return bmi; }
    public void setBmi(String bmi) { this.bmi = bmi; }
}