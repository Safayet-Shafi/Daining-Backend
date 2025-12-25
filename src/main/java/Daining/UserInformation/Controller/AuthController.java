package Daining.UserInformation.Controller;

import Daining.UserInformation.DTO.AuthRequest;
import Daining.UserInformation.Service.JwtService;
import Daining.UserInformation.Service.ServiceImpl.UserinformationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserinformationServiceImpl userInformationService;

    String generatedPass="";
    String dbPass="";

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NoSuchAlgorithmException {
        System.out.println("authRequest = " + authRequest);
        generatedPass=userInformationService.customHash((String) authRequest.getUsername(), (String) authRequest.getPassword());
        dbPass=userInformationService.getUserPasswordByusername((String) authRequest.getUsername());

        if(generatedPass.equals(dbPass)){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else{

            return ("Error in UserId or Password");
        }


    }

}
