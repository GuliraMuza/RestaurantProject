package peaksoft.dto.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class MenuItemResponse {
    private Long id;
    private String name;
    private  String image;
    private int price;
    private String description;
    private Boolean isVegetarian;

    public MenuItemResponse(Long id, String name, String image, int price, String description, Boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
