package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryRequest {
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String name;
}
