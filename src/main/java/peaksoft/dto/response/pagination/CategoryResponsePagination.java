package peaksoft.dto.response.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.CategoryResponse;


import java.util.List;
@Getter
@Setter
public class CategoryResponsePagination {
    private int size;
    private   int page;
    private List<CategoryResponse>categoryResponses;
}
