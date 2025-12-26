package Daining.UserInformation.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Integer responseCode;
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
