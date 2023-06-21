package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.SubCategory;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subCategories")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class SubCategoryApi {
    public final SubCategoryService subCategoryService;

    @PostMapping("/{categoryId}")
    public SimpleResponse saveSub(@PathVariable Long categoryId,  @RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.saveSubcategory(categoryId,subCategoryRequest);
    }

    @GetMapping("/{subCategoryId}")
    public SubCategoryResponse getCategoryById(@PathVariable Long subCategoryId){
        return subCategoryService.getSubCategoriesById(subCategoryId);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateSubCategory(@PathVariable Long id,@RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.updateSubCategory(id,subCategoryRequest);
    }

    @DeleteMapping("/{subCategoryId}")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long subCategoryId){
        return subCategoryService.deleteSubCategories(subCategoryId);
    }

    @GetMapping("/find")
    public Map<String, List<SubCategory>> findAllGroupByCategory(){
        return subCategoryService.findAllGroupByCategory();
    }

    @GetMapping("/sort/{categoryId}")
    public List<SubCategoryResponse>getSortSubCategory(@PathVariable Long categoryId,@RequestParam String ascOrDesc){
        return subCategoryService.getAllSubCategoriesByCategoryId(categoryId, ascOrDesc);
    }

}
