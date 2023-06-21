package peaksoft.dto.simple;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ChequeOneDayWaiterResponse {

    private String waiterFullName;
    private int numberOfCheques;
    private int totalSumma;// общая сумма
    private LocalDate date;
}
