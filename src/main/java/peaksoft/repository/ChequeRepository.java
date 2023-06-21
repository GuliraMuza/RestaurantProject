package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.entity.Cheque;

import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select " +
            "(CONCAT(u.lastName, ' ', u.firstName)," +
            "AVG(m.price)," +
            "r.service," +
            "(SELECT COALESCE(SUM(m.price * r.service / 100 ) + SUM(m.price), 0) FROM MenuItem m JOIN m.cheques c WHERE c.id = ?1)) " +
            "FROM Cheque c " +
            "JOIN c.user u " +
            "LEFT JOIN c.menuItems m " +
            "JOIN u.restaurant r " +
            "WHERE c.id = ?1 " +
            "GROUP BY CONCAT(u.lastName, ' ', u.firstName),r.service")
    Optional<ChequeResponse> sumAv(Long id);
}