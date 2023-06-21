package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;

import org.springframework.data.domain.Pageable;
import java.util.List;



@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Boolean existsCategoryByName(String name);



    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            "from MenuItem m  join m.subCategory s join s.category c where upper(m.name) like concat('%',:text,'%')" +
            " or  upper(c.name) like concat('%',:text,'%')" +
            "or  upper(s.name) like concat('%',:text,'%')")
    List<MenuItemResponse> searchAndPagination(@Param("text")String text, Pageable pageable);



    @Query(" select new   peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian), count(m.price) from MenuItem   m  order by m.price desc ")
    List<MenuItemResponse>getAllMenuItemsByPriceSortDesc(String ascOrDesc);

    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian), count(m.price) from MenuItem  m order by m.price asc  ")
    List<MenuItemResponse>getAllMenuItemsByPriceSortAsc(String ascOrDesc);



    @Query("SELECT NEW peaksoft.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "FROM MenuItem m " +
            "WHERE m.isVegetarian = :isVegetarian")
    List<MenuItemResponse> filter(@Param("isVegetarian") Boolean isVegetarian);

}
