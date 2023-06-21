package peaksoft.dto.response.pagination;

import lombok.*;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryResponsePagination {
     private     int size;
     private    int page;
     private List<SubCategoryResponse> subCategoryResponses;

}
