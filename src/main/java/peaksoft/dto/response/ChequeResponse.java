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
    private Long id;
    private String fullName;
    private List<MenuItem> itemResponseList;
    private int avaPrice;
    private int services;
    private int grandTotal;
}
