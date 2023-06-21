package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.StopList;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {


    @Query("select count (*) from StopList s where s.date=:date and upper(s.menuItem.name) like upper(:menuItemName) ")
    Integer count(LocalDate date, String menuItemName);

    @Query("select new peaksoft.dto.response.StopListResponse(s.id,s.reason ,s.date)from StopList  s")
 Optional< StopListResponse >getStopListById(Long stopListId);
}