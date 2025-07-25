package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserFlags;
import java.time.LocalDate;

public class UserOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private UserFlags flags;

    // Static factory method voor veilige conversie van Entiteit naar DTO
    public static UserOutputDto from(User user) {
        UserOutputDto dto = new UserOutputDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());
        dto.setFlags(user.getFlags());
        return dto;
    }

    // Getters en Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public UserFlags getFlags() { return flags; }
    public void setFlags(UserFlags flags) { this.flags = flags; }
}