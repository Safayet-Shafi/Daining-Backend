package Daining.UserInformation.Controller;

import Daining.UserInformation.DTO.ResponseModelDTO;
import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;
import Daining.UserInformation.Service.UserInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DAINING/v1/UserInformation")
public class UserInformationController {
    private final UserInformationService userInformationService;

    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @GetMapping()
    public List<UserInformationDTO> getAllUsers(){
        return userInformationService.getAllUsers();
    }

    @PostMapping("/userReg")
    ResponseModelDTO postUser(@RequestBody UserInformationDTO userInformationDTO) throws NoSuchAlgorithmException {
        System.out.println("userInformationDTO = " + userInformationDTO);
        return userInformationService.postUser(userInformationDTO);
    }

    @PutMapping("/update-put/{id}")
    public ResponseModelDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserInformationDTO userInformationDTO)
            throws NoSuchAlgorithmException {

        return userInformationService.updateUserPut(id, userInformationDTO);
    }

    @PatchMapping("/update-patch/{id}")
    public ResponseModelDTO patchUser(
            @PathVariable Long id,
            @RequestBody UserInformationDTO userInformationDTO)
            throws NoSuchAlgorithmException {

        return userInformationService.updateUserPatch(id, userInformationDTO);
    }

    @PatchMapping("/patch-dynamic/{id}")
    public ResponseModelDTO patchUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) throws NoSuchAlgorithmException {

        return userInformationService.updateUserPatchDynamic(id, updates);
    }



}
