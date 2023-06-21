package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.CheckResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemsResponse;
import peaksoft.entity.Cheque;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("SELECT NEW peaksoft.dto.response.CheckResponse" +
            "(CONCAT(u.lastName, ' ', u.firstName)," +
            "CAST(AVG(m.price)AS double)," +
            "CAST(r.service AS int)," +
            "CAST((SELECT COALESCE(SUM(m.price * r.service / 100 ) + SUM(m.price), 0) FROM MenuItem m JOIN m.cheques c WHERE c.id = ?1)AS bigdecimal )) " +
            "FROM Cheque c " +
            "JOIN c.user u  " +
            "LEFT JOIN c.menuItems m  " +
            "JOIN u.restaurant r " +
            "WHERE c.id = ?1 " +
            "GROUP BY CONCAT(u.lastName, ' ', u.firstName),r.service")
    Optional<CheckResponse> getChequeId(Long chequeId);




    @Query("SELECT new peaksoft.dto.response.MenuItemsResponse(" +
            " m.name,m.image,m.price,m.description,m.isVegetarian) " +
            "FROM MenuItem m JOIN m.cheques c WHERE c.id = ?1")
    List<MenuItemsResponse> getAllMenuItemsByChequeId(Long chequeId);
}