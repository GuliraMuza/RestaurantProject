package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.pagination.MenuItemResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.*;
import peaksoft.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.*;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, MenuItemRequest menuItemRequest) {
        if (menuItemRepository.existsCategoryByName(menuItemRequest.getName())) {
            return SimpleResponse.builder().
                    httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Category with name :%s already exists",
                            menuItemRequest.getName())).build();
        }
       Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("dcv"));
        MenuItem menuItem=new MenuItem();
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setRestaurant(restaurant);
        menuItem.setIsVegetarian(false);
        menuItemRepository.save(menuItem);
        return   SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with name: %s successfully saved!",
                        menuItem.getName()))
                .build();
    }


    @Override
    public MenuItemResponse getMenuItemById(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException("dcv"));

        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .image(menuItem.getImage())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .isVegetarian(menuItem.getIsVegetarian())
                .build();
    }


    @Override
    public SimpleResponse deleteMenuItemById(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException("dcv"));
        User user = jwtService.getAuthentication();
        Restaurant restaurant=menuItem.getRestaurant();
        if (user.getRole().equals(Role.ADMIN)){
            for (MenuItem res: restaurant.getMenuItems()){
                if(restaurant!=null){
                    restaurant.getMenuItems().remove(menuItem);
                    menuItemRepository.delete(res);
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.OK)
                            .message("Successfully deleted")
                            .build();
                }
               /* res.getRestaurant().getMenuItems().remove(menuItem);*/
            }
            menuItemRepository.delete(menuItem);


        }
        else{ throw new BadCredentialException("You can not get user with id:"+user.getId());
    }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException("dcv"));
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setIsVegetarian(menuItemRequest.getIsVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse assignMenuItemToSubCategory(Long menuItemId, Long subCategoryId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException("dcv"));
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new NotFoundException("Not"));
        subCategory.getMenuItems().add(menuItem);
        menuItem.setSubCategory(subCategory);
        menuItemRepository.save(menuItem);
        return   SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with name: %s successfully assign!",
                        menuItem.getName()))
                .build();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemBySortPrice(Long restaurantId, String ascOrDesc) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        if (ascOrDesc.equals("asc")){
            return menuItemRepository.getAllMenuItemsByPriceSortAsc(String.valueOf(restaurant));
        }
        else if (ascOrDesc.equals("desc")) {
            return menuItemRepository.getAllMenuItemsByPriceSortDesc(String.valueOf(restaurant));
        }
        else {
            throw new BadRequestException("Conflict");
        }

    }


    @Override
    public List<MenuItemResponse>getVegetarianMenuItems(Boolean isVegetarian){
         return menuItemRepository.filter(isVegetarian);

    }




    @Override
    public MenuItemResponsePagination searchAndPagination(String text, int page, int size){
        Pageable pageable=  PageRequest.of(page-1,size);
        MenuItemResponsePagination menuItemResponsePagination=new MenuItemResponsePagination();
        menuItemResponsePagination.setMenuItemResponses(viewMenu(searchMenu(text,pageable)));
        menuItemResponsePagination.setSubCategoryResponses(viewSub(searchSub(text,pageable)));
        menuItemResponsePagination.setCategoryResponses(viewCat(searchCat(text,pageable)));
        return  menuItemResponsePagination;
    }



    public List<MenuItemResponse>viewMenu(List<MenuItemResponse> menuItemResponse){
        List<MenuItemResponse>menuItemResponses=new ArrayList<>();
        for (MenuItemResponse m:menuItemResponse){
            menuItemResponses.add(mapToResponseM(m));
        }
        return menuItemResponses;
    }
    public List<SubCategoryResponse>viewSub(List<SubCategoryResponse> subCategoryResponses){
        List<SubCategoryResponse>sub=new ArrayList<>();
        for (SubCategoryResponse m:subCategoryResponses){
            sub.add(mapToResponseS(m));
        }
        return sub;
    }

    public List<CategoryResponse>viewCat(List<CategoryResponse> categoryResponses){
        List<CategoryResponse>category=new ArrayList<>();
        for (CategoryResponse m:categoryResponses){
            category.add(mapToResponseC(m));
        }
        return category;
    }

    private List<MenuItemResponse> searchMenu(String text, Pageable pageable){
        String name=text == null  ? "" :text;
        List<MenuItemResponse> menuItemResponses = menuItemRepository.searchAndPagination(name.toUpperCase(), pageable);
         return menuItemResponses;
    }

    private List<SubCategoryResponse> searchSub(String text, Pageable pageable){
        String name=text == null  ? "" :text;
        List<SubCategoryResponse> subCategoryResponses= subCategoryRepository.searchAndPagination(name.toUpperCase(), pageable);
        return subCategoryResponses;
    }

    private List<CategoryResponse> searchCat(String text, Pageable pageable){
        String name=text == null  ? "" :text;
        List<CategoryResponse> categoryResponses= categoryRepository.searchAndPagination(name.toUpperCase(), pageable);
        return categoryResponses;
    }




    public MenuItemResponse mapToResponseM(MenuItemResponse menuItemResponse){
        MenuItemResponse menuItem=new MenuItemResponse();
        menuItem.setId(menuItemResponse.getId());
        menuItem.setName(menuItemResponse.getName());
        menuItem.setImage(menuItemResponse.getImage());
        menuItem.setDescription(menuItemResponse.getDescription());
        menuItem.setPrice(menuItemResponse.getPrice());
        menuItem.setIsVegetarian(false);
        return menuItem;
    }
    public CategoryResponse mapToResponseC(CategoryResponse category){
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    public SubCategoryResponse mapToResponseS(SubCategoryResponse category){
        SubCategoryResponse categoryResponse=new SubCategoryResponse();
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
