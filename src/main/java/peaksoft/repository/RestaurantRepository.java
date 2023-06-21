package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.simple.RestaurantDetailsResponse;
import peaksoft.entity.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  Boolean existsByName(String name);

  boolean existsById(Long id);




  @Query("SELECT count(r.numberOfEmployees) FROM Restaurant r  WHERE r.id= :restaurantId")
  int countEmployeesWorkingByRestaurantId(@Param("restaurantId") Long restaurantId);


  @Query("SELECT NEW peaksoft.dto.simple.RestaurantDetailsResponse(r.id, COUNT(u)) " +
          "FROM Restaurant r " +
          "JOIN r.users u " +
          "WHERE r.id = ?1 and u.role <> ('ADMIN') " +
          "GROUP BY r.id, r.name")
  List<RestaurantDetailsResponse> countUser(Long id);
}