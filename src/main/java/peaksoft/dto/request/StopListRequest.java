package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
public class StopListRequest {
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String reason;
    @CreatedDate
    private LocalDate date;
    private Long menuItemId;
}
