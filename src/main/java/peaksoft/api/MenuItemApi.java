package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.pagination.MenuItemResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menu/items")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class MenuItemApi {

    private final MenuItemService menuItemService;

    @PostMapping("/{restaurantId}")
    public SimpleResponse saveMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenuItem(restaurantId, menuItemRequest);
    }

    @PostMapping("/{menuId}/{subCatId}")
    public SimpleResponse assignEmployees(@PathVariable Long subCatId, @PathVariable Long menuId, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.assignMenuItemToSubCategory(subCatId, menuId);
    }


    @GetMapping("/search")
    public MenuItemResponsePagination getAllMenuItems(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam int page,
            @RequestParam int size) {
        return menuItemService.searchAndPagination(text, page, size);
    }

    @GetMapping("/{menuItemId}")
    public MenuItemResponse getMenuItemById(@PathVariable Long menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCategory(@PathVariable Long id, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.updateMenuItem(id, menuItemRequest);
    }

    @DeleteMapping("/{menuItemId}")
    public SimpleResponse deleteMenuItemById(@PathVariable Long menuItemId) {
        return menuItemService.deleteMenuItemById(menuItemId);
    }

    @GetMapping("/sort")
    public List<MenuItemResponse> sortByMenuItemPriceAscOrDesc(@PathVariable Long restaurantId, @RequestParam(required = false, defaultValue = "asc") String ascOrDesc) {
        return menuItemService.getAllMenuItemBySortPrice(restaurantId, ascOrDesc);
    }

    @GetMapping("/filter")
    public List<MenuItemResponse> filterMenuItemByIsVegetarian(@RequestParam(required = false, defaultValue = "false") Boolean isVegetarian) {
        return menuItemService.getVegetarianMenuItems(isVegetarian);
    }


}
