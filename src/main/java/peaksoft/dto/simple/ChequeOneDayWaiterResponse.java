package peaksoft.dto.simple;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ChequeOneDayWaiterResponse {

    private String waiterName;
    private int counterCheck;
    private int totalPrice;// общая сумма
    private LocalDate date;
}
