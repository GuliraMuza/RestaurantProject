package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.pagination.MenuItemResponsePagination;
import peaksoft.dto.simple.SimpleResponse;

import java.util.List;

public interface MenuItemService {

    SimpleResponse saveMenuItem(Long restaurantId , MenuItemRequest menuItemRequest);

    MenuItemResponse getMenuItemById(Long menuItemId);
    SimpleResponse deleteMenuItemById( Long menuItemId);
    SimpleResponse updateMenuItem( Long menuItemId, MenuItemRequest menuItemRequest);

    SimpleResponse assignMenuItemToSubCategory(Long menuItemId,Long subCategoryId);

    List<MenuItemResponse>getVegetarianMenuItems(Boolean isVegetarian);

    MenuItemResponsePagination searchAndPagination(String text, int page, int size);

    List<MenuItemResponse> getAllMenuItemBySortPrice(Long restaurantId, String ascOrDesc);
}
