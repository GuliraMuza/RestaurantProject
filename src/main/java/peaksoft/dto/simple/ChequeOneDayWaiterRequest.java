package peaksoft.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Setter
@Getter
@AllArgsConstructor
public class ChequeOneDayWaiterRequest {
    private Long waiterId;
    private LocalDate date;

}
