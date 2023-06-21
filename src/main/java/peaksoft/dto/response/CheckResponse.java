package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CheckResponse {
    private String fullName;
    private List<MenuItemsResponse> menuItems;
    private Double averagePrice;
    private int service;
    private BigDecimal grandTotal;

    @Builder
    public CheckResponse(String fullName, Double averagePrice, int service, BigDecimal grandTotal) {
        this.fullName = fullName;
        this.averagePrice = averagePrice;
        this.service = service;
        this.grandTotal = grandTotal;
    }

    public CheckResponse() {
    }
}