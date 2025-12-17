package Daining.UserInformation.Repository;

import Daining.UserInformation.Model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInformation,Long> {
}
