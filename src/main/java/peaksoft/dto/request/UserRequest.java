package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PasswordValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String firstName;
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String lastName;
    @CreatedDate
    private LocalDate dateOfBirth;

    @EmailValid
    private String email;
    @PasswordValid
    private String password;
    @PhoneNumberValid
    @Pattern(regexp = "^\\+996\\d{9}$", message = "Phone number must start with +996 and contain 12 digits")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Min(value = 0)
    @PositiveOrZero
    private int experience;


}
