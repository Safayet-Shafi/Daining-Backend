package Daining.UserInformation.Controller;

import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;
import Daining.UserInformation.Service.UserInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DAINING/v1/UserInformation")
public class UserInformationController {
    private final UserInformationService userInformationService;

    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @GetMapping("/test")
    public List<UserInformationDTO> getAllUsera(){
        return userInformationService.getAllUsers();
    }

    @GetMapping()
    public List<UserInformation> getAllUsera1(){
        return userInformationService.getAllUsers1();
    }



}
