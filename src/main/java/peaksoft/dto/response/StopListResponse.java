package peaksoft.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Builder
@Getter
@Setter

public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;

    public StopListResponse(Long id, String reason, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
