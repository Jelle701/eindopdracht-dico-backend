package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutputDto> registerUser(@Valid @RequestBody UserInputDto inputDto) {
        UserOutputDto result = userService.registerUser(inputDto);
        return ResponseEntity.ok(result);
    }
}
