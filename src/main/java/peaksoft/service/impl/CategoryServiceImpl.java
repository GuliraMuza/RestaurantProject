package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.pagination.CategoryResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsCategoryByName(categoryRequest.getName())) {
            return SimpleResponse.builder().
                    httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Category with name :%s already exists",
                            categoryRequest.getName())).build();
        }
        Category category=new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Category with name: %s "+categoryRequest.getName()+" successfully saved",category.getName()
        )).build();
    }




    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryResponse categoryResponse = categoryRepository.getCategoryById(categoryId).orElseThrow(
                () -> new NotFoundException("Category with id: " + categoryId + " not found!"));
        return CategoryResponse.builder()
                .id(categoryResponse.getId())
                .name(categoryResponse.getName())
                .build();
    }

    @Override
    public SimpleResponse deleteCategories(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category with id: " + categoryId + " not found!"));
        categoryRepository.delete(category);
        return new SimpleResponse("Category successfully deleted", HttpStatus.OK);
    }


    @Override
    public SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category with id: " + categoryId + " not found!"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return new SimpleResponse("Category successfully updated", HttpStatus.OK);

    }


    @Override
    public CategoryResponsePagination searchAndPagination(String text, int page, int size){
        Pageable pageable=  PageRequest.of(page-1,size);
        CategoryResponsePagination categoryResponsePagination=new CategoryResponsePagination();
        categoryResponsePagination.setCategoryResponses(view(search(text,pageable)));
        return  categoryResponsePagination;

    }

    public List<CategoryResponse>view(List<CategoryResponse> categories){
        List<CategoryResponse>categoryResponses=new ArrayList<>();
        for (CategoryResponse m:categories){
            categoryResponses.add(mapToResponse(m));
        }
        return categoryResponses;
    }

    private List<CategoryResponse> search(String text, Pageable pageable){
        String name=text == null  ? "" :text;
        return  categoryRepository.searchAndPagination(name.toUpperCase(), pageable);
    }


    public CategoryResponse mapToResponse(CategoryResponse category){
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
