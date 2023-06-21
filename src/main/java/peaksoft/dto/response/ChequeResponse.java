package peaksoft.dto.response;

import lombok.*;
import peaksoft.entity.MenuItem;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChequeResponse {
    private String waiterName;
    private List<MenuItem> menuItems;
    private int priceTotal;
    private int services;
    private int grandTotal;
}
