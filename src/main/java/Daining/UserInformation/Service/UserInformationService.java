package Daining.UserInformation.Service;

import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;

import java.util.List;

public interface UserInformationService {
    List<UserInformationDTO> getAllUsers();

    List<UserInformation> getAllUsers1();

}
