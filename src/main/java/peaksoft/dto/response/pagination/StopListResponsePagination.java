package peaksoft.dto.response.pagination;

import lombok.*;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopListResponsePagination {
    private     int size;
    private    int page;
    private List<StopListResponse> stopListResponses;

}
