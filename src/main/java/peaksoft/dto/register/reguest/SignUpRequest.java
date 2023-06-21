package peaksoft.dto.register.reguest;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfStart;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;

}