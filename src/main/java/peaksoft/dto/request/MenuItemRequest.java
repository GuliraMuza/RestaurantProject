package peaksoft.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MenuItemRequest {
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private  String image;
    @Min(value = 0)
    @PositiveOrZero
    private int price;
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String description;

    private Boolean isVegetarian;
}
