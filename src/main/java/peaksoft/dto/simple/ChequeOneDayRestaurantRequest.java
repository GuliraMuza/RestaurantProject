package peaksoft.dto.simple;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Getter
@Setter
public class ChequeOneDayRestaurantRequest {
    private Long restaurantId;
    private LocalDate data;

}
