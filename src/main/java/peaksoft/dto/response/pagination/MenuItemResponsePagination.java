package peaksoft.dto.response.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;
@Getter
@Setter
public class MenuItemResponsePagination {
    private List<MenuItemResponse>menuItemResponses;
    private List<SubCategoryResponse>subCategoryResponses;
    private List<CategoryResponse > categoryResponses;


}
