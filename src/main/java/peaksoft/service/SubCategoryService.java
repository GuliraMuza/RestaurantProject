package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.pagination.SubCategoryResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Map;

public interface SubCategoryService{

        SimpleResponse saveSubcategory(Long subCategoryId, SubCategoryRequest subCategoryRequest);

        SubCategoryResponse getSubCategoriesById(Long subCategoryId);
        SimpleResponse deleteSubCategories(Long subCategoryId);


    SimpleResponse updateSubCategory(Long categoryId, SubCategoryRequest subCategoryReq);

    Map<String, List<SubCategory>> findAllGroupByCategory();


    List<SubCategoryResponse> getAllSubCategoriesByCategoryId(Long categoryId, String ascOrDesc);

    SubCategoryResponsePagination getAllPagination(int pageSize, int currentPage);
}
