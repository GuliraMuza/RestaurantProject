package peaksoft.dto.simple;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class ChequeOneDayRestaurantResponse {
    private String restaurantName;
    private LocalDate date;
    private int numberCheque;
    private int priceAverage;
}
