package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MenuItemsResponse {
    private String name;
    private String image;
    private int price;
    private String description;
    private Boolean isVegetarian;

    @Builder
    public MenuItemsResponse(String name, String image, int price, String description, Boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }

    public MenuItemsResponse() {
    }
}
