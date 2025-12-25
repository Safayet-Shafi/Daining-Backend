package Daining.UserInformation.Service;

import Daining.UserInformation.DTO.ResponseModelDTO;
import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface UserInformationService {
    List<UserInformationDTO> getAllUsers();

    ResponseModelDTO postUser(UserInformationDTO userInformationDTO) throws NoSuchAlgorithmException;

    ResponseModelDTO updateUserPut(Long id, UserInformationDTO userInformationDTO)
            throws NoSuchAlgorithmException;

    ResponseModelDTO updateUserPatch(Long id, UserInformationDTO userInformationDTO)
            throws NoSuchAlgorithmException;

    ResponseModelDTO updateUserPatchDynamic(Long id, Map<String, Object> updates)
            throws NoSuchAlgorithmException;

    UserInformationDTO getUser(Long id);

    String getUserPasswordByusername(String username);



}
