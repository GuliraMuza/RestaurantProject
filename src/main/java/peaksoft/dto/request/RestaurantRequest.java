package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.RestType;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class RestaurantRequest {
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String location;
    @Enumerated(EnumType.STRING)
    private RestType restType;

    private int service;
}
