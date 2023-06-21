package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Boolean existsSubCategoryByName(String name);

//    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from SubCategory  s")
//    Page<SubCategoryResponse> getAllSubCategory(Pageable pageable);

    @Query("SELECT new  peaksoft.dto.response.SubCategoryResponse(s.id,s.name)  from SubCategory  s where  upper(s.name) like concat('%',:text,'%')")
    List<SubCategoryResponse> searchAndPagination(@Param("text") String text, Pageable pageable);


    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from SubCategory s  order by s.name asc ")
    List<SubCategoryResponse> getAllSubCategoriesByCategoryIdAndSortAsc(Category category);

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from SubCategory s order by s.name desc  ")
    List<SubCategoryResponse> getAllSubCategoriesByCategoryIdAndSortDesc(Category category);


}