package com.zbiir.JwtService.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Valid

    @NotNull (message = "username exception")
    @NotBlank (message = "username exception")
    private String username;

    @NotNull (message = "role exception")
    @NotBlank (message = "role exception")
    private String role;
}
