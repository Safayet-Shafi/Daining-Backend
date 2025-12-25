package Daining.UserInformation.Repository;

import Daining.UserInformation.Model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInformation,Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT PASSWORD\n" +
                    "  FROM USERINFORMATION\n" +
                    " WHERE username = :username"
    )
    String getUserPasswordByusername(@Param("username") String username);

    Optional<UserInformation> findByUsername(String username);
}
