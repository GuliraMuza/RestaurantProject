package peaksoft.dto.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubCategoryResponse {
    private Long id;
    private String name;

    public SubCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
