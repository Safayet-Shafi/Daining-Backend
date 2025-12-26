package Daining.UserInformation.Controller;

import Daining.UserInformation.DTO.AuthRequest;
import Daining.UserInformation.DTO.AuthResponse;
import Daining.UserInformation.Model.UserInformation;
import Daining.UserInformation.Repository.UserInfoRepo;
import Daining.UserInformation.Service.JwtService;
import Daining.UserInformation.Service.ServiceImpl.UserinformationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserinformationServiceImpl userInformationService;

    @Autowired
    private UserInfoRepo userInfoRepo;

    String generatedPass="";
    String dbPass="";

//    @PostMapping("/token")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NoSuchAlgorithmException {
//        System.out.println("authRequest = " + authRequest);
//        generatedPass=userInformationService.customHash((String) authRequest.getUsername(), (String) authRequest.getPassword());
//        dbPass=userInformationService.getUserPasswordByusername((String) authRequest.getUsername());
//        Optional<UserInformation> userInfo = userInfoRepo.findByUsername(authRequest.getUsername());
//        System.out.println("userInfo = " + userInfo);
//
//        if(generatedPass.equals(dbPass)){
//            return jwtService.generateToken(authRequest.getUsername());
//        }
//        else{
//
//            return ("Error in UserId or Password");
//        }
//
//
//    }


    @PostMapping("/token")
    public AuthResponse getToken(@RequestBody AuthRequest authRequest)
            throws NoSuchAlgorithmException {

        generatedPass = userInformationService.customHash(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        dbPass = userInformationService
                .getUserPasswordByusername(authRequest.getUsername());

        Optional<UserInformation> userInfoOpt =
                userInfoRepo.findByUsername(authRequest.getUsername());

        AuthResponse response = new AuthResponse();

        if (userInfoOpt.isPresent() && generatedPass.equals(dbPass)) {

            UserInformation userInfo = userInfoOpt.get(); // ✅ unwrap Optional
            response.setResponseCode(1);
            response.setToken(jwtService.generateToken(userInfo.getUsername()));
            response.setUsername(userInfo.getUsername());
            response.setFirstName(userInfo.getFirstName());
            response.setLastName(userInfo.getLastName());
            response.setEmail(userInfo.getEmail());
            return response;
        }
        else{

            response.setResponseCode(0);
            return response;
        }


    }

}
