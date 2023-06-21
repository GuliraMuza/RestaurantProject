package peaksoft.dto.simple;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDetailsResponse {
    private Long id;
    private Long userCount;

    public RestaurantDetailsResponse(Long id, Long userCount) {
        this.id = id;
        this.userCount = userCount;
    }

    public RestaurantDetailsResponse() {
    }
}
