package peaksoft.dto.response;

import lombok.*;


@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
