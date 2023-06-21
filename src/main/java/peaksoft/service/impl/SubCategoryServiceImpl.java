package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.pagination.SubCategoryResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public SimpleResponse saveSubcategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
        if (subCategoryRepository.existsSubCategoryByName(subCategoryRequest.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("SubCategory with name : %s already exists", subCategoryRequest.getName()))
                    .build();
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("not"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.getName());
        category.getSubCategories().add(subCategory);
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s successfully saved!",
                        subCategoryRequest.getName()))
                .build();
    }


    @Override
    public SubCategoryResponse getSubCategoriesById(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("no"));
        return SubCategoryResponse.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategories(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("no"));
        Category category = subCategory.getCategory();
        if (category != null) {
            category.getSubCategories().remove(subCategory);
        }
        subCategoryRepository.delete(subCategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully updated!",
                        subCategory.getName()))
                .build();
    }


    @Override
    public SimpleResponse updateSubCategory(Long subCategoryId, SubCategoryRequest subCategoryReq) {
        SubCategory subCategory1 = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("not"));
        subCategory1.setName(subCategoryReq.getName());
        subCategoryRepository.save(subCategory1);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s successfully saved!",
                        subCategoryReq.getName()))
                .build();
    }

    @Override
    public Map<String, List<SubCategory>> findAllGroupByCategory() {
        return subCategoryRepository.findAll().stream()
                .collect(Collectors.groupingBy(subcategory -> subcategory.getCategory().getName()));    }


 @Override
 public List<SubCategoryResponse> getAllSubCategoriesByCategoryId(Long categoryId, String ascOrDesc) {
     Category category = categoryRepository.findById(categoryId).orElseThrow(
             () -> new NotFoundException("Category with id: " + categoryId + " not found!"));
     if (ascOrDesc.equals("asc")){
         return subCategoryRepository.getAllSubCategoriesByCategoryIdAndSortAsc(category);
     }
     else if (ascOrDesc.equals("desc")) {
         return subCategoryRepository.getAllSubCategoriesByCategoryIdAndSortDesc(category);
     }
     else {
         throw new BadRequestException("Conflict");
     }
 }


    @Override
    public SubCategoryResponsePagination getAllPagination(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize, Sort.by("name"));
        Page<SubCategoryResponse> allCategory=null;
//                subCategoryRepository.getAllSubCategory(pageable);
        return SubCategoryResponsePagination
                .builder()
                .subCategoryResponses(allCategory.getContent())
                .page(allCategory.getNumber()+1)
                .size(allCategory.getTotalPages())
                .build();
    }

}

