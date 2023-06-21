package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.pagination.CategoryResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.service.CategoryService;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryApi {
    private final CategoryService categoryService;


    @PostMapping()
    public SimpleResponse save(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }



    @GetMapping
    public CategoryResponsePagination getAllCompanies(@RequestParam (name = "text",required = false)String text,
                                                      @RequestParam int page,
                                                      @RequestParam int size){
        return categoryService.searchAndPagination(text,page,size);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable Long categoryId){
       return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCategory(@PathVariable Long id,@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id,categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public SimpleResponse deleteCategoryById(@PathVariable Long categoryId){
        return categoryService.deleteCategories(categoryId);
    }









}


