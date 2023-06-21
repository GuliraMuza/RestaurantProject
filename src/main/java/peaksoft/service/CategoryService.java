package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.pagination.CategoryResponsePagination;
import peaksoft.dto.simple.SimpleResponse;

import java.util.List;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long categoryId);
    SimpleResponse deleteCategories(Long categoryId);
    SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    CategoryResponsePagination searchAndPagination(String text, int page, int size);

}
