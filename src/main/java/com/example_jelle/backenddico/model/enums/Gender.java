package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum voor de verschillende gender-opties.
 * De @JsonProperty annotatie zorgt ervoor dat de JSON-string (lowercase)
 * correct wordt omgezet naar de Java Enum (UPPERCASE).
 */
public enum Gender {
    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE,
    @JsonProperty("other")
    OTHER,
    @JsonProperty("prefer_not_to_say")
    PREFER_NOT_TO_SAY
}