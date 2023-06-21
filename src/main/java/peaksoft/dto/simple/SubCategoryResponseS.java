package peaksoft.dto.simple;

import lombok.*;


@Builder
@Getter
@Setter

public class SubCategoryResponseS {
    private Long id;
    private String name;
    private String categoryName;
    private Long categoryId;

    public SubCategoryResponseS(Long id, String name, String categoryName, Long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public SubCategoryResponseS() {
    }
}
