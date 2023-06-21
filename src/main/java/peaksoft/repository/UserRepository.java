package peaksoft.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>getUserByEmail(String email);

     Boolean existsByEmail(String email);

    @Query("SELECT new  peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email, u.password, u.phoneNumber, u.role,u.experience) from User  u where  upper(u.firstName) like concat('%',:text,'%') " +
            "or upper(u.lastName) like concat('%',:text,'%') or  upper(u.lastName) like concat('%',:text,'%')  ")
    List<UserResponse> searchAndPagination(@Param("text")String text, Pageable pageable);

}