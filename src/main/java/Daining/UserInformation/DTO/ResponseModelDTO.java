package Daining.UserInformation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModelDTO {
    private int responseCode;
    private String responseMessage;
}
