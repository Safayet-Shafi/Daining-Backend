package Daining.UserInformation.Service.ServiceImpl;

import Daining.UserInformation.DTO.ResponseModelDTO;
import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;
import Daining.UserInformation.Repository.UserInfoRepo;
import Daining.UserInformation.Service.UserInformationService;
import org.modelmapper.ModelMapper;
//import org.springframework.data.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class UserinformationServiceImpl implements UserInformationService {

    private final ModelMapper mapper;
    private final UserInfoRepo userInfoRepo;

    public UserinformationServiceImpl(ModelMapper mapper, UserInfoRepo userInfoRepo) {
        this.mapper = mapper;
        this.userInfoRepo = userInfoRepo;
    }

    @Override
    public List<UserInformationDTO> getAllUsers() {
       List<UserInformation> userInformations =userInfoRepo.findAll();
        System.out.println("userInformations = " + userInformations);
       return userInformations.stream().map(user->maptoDTO(user)).toList();

    }

    @Override
    public ResponseModelDTO postUser(UserInformationDTO userInformationDTO) throws NoSuchAlgorithmException {
        System.out.println("userInformationDTO = " + userInformationDTO);
        String encryptedPass = null;
        ResponseModelDTO responseDTO = new ResponseModelDTO();
        UserInformation userInformation = maptoEntity(userInformationDTO);
        encryptedPass = customHash((String) userInformationDTO.getUsername(), (String) userInformationDTO.getPassword());
        System.out.println("encryptedPass = " + encryptedPass);
        userInformation.setPassword(encryptedPass);
        userInformation.setCreateTime(new Date());
        System.out.println("userInformation = " + userInformation);

        try{
            UserInformation userInformation1 = userInfoRepo.save(userInformation);
            responseDTO.setResponseCode(1);
            responseDTO.setResponseMessage("User Created Successfully");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public ResponseModelDTO updateUserPut(Long id, UserInformationDTO userInformationDTO) throws NoSuchAlgorithmException {
        ResponseModelDTO responseDTO = new ResponseModelDTO();

        UserInformation existingUser = userInfoRepo.findById(id).orElse(null);

        if (existingUser == null) {
            responseDTO.setResponseCode(0);
            responseDTO.setResponseMessage("User not found");
            return responseDTO;
        }

        // Update fields
        existingUser.setFirstName(userInformationDTO.getFirstName());
        existingUser.setLastName(userInformationDTO.getLastName());
        existingUser.setEmail(userInformationDTO.getEmail());
        existingUser.setUsername(userInformationDTO.getUsername());
        existingUser.setActiveFlag(userInformationDTO.getActiveFlag());

        // Update password only if provided
        if (userInformationDTO.getPassword() != null && !userInformationDTO.getPassword().isEmpty()) {
            String encryptedPass = customHash(userInformationDTO.getUsername(), userInformationDTO.getPassword());
            existingUser.setPassword(encryptedPass);
        }

        userInfoRepo.save(existingUser);

        responseDTO.setResponseCode(1);
        responseDTO.setResponseMessage("User updated successfully");

        return responseDTO;
    }

    @Override
    public ResponseModelDTO updateUserPatch(Long id, UserInformationDTO userInformationDTO) throws NoSuchAlgorithmException {
        ResponseModelDTO responseDTO = new ResponseModelDTO();

        UserInformation existingUser = userInfoRepo.findById(id).orElse(null);

        if (existingUser == null) {
            responseDTO.setResponseCode(0);
            responseDTO.setResponseMessage("User not found");
            return responseDTO;
        }

        // Partial updates (null check)
        if (userInformationDTO.getFirstName() != null) {
            existingUser.setFirstName(userInformationDTO.getFirstName());
        }

        if (userInformationDTO.getLastName() != null) {
            existingUser.setLastName(userInformationDTO.getLastName());
        }

        if (userInformationDTO.getEmail() != null) {
            existingUser.setEmail(userInformationDTO.getEmail());
        }

        if (userInformationDTO.getUsername() != null) {
            existingUser.setUsername(userInformationDTO.getUsername());
        }

        if (userInformationDTO.getActiveFlag() != null) {
            existingUser.setActiveFlag(userInformationDTO.getActiveFlag());
        }

        // Password update (hash only if provided)
        if (userInformationDTO.getPassword() != null && !userInformationDTO.getPassword().isEmpty()) {
            String encryptedPass = customHash(
                    userInformationDTO.getUsername() != null ? userInformationDTO.getUsername() : existingUser.getUsername(),
                    userInformationDTO.getPassword()
            );
            existingUser.setPassword(encryptedPass);
        }

        userInfoRepo.save(existingUser);

        responseDTO.setResponseCode(1);
        responseDTO.setResponseMessage("User updated successfully (PATCH)");

        return responseDTO;
    }

    @Override
    public ResponseModelDTO updateUserPatchDynamic(Long id,  Map<String, Object> updates) throws NoSuchAlgorithmException {
        ResponseModelDTO responseDTO = new ResponseModelDTO();

        UserInformation user = userInfoRepo.findById(id).orElse(null);

        if (user == null) {
            responseDTO.setResponseCode(0);
            responseDTO.setResponseMessage("User not found");
            return responseDTO;
        }

        updates.forEach((key, value) -> {

            // 1️⃣ Allow only safe fields
            if (!ALLOWED_FIELDS.contains(key)) {
                return;
            }

            // 2️⃣ Password needs special handling
            if ("password".equals(key)) {
                if (value != null && !value.toString().isEmpty()) {
                    try {
                        String encryptedPass = customHash(user.getUsername(), value.toString());
                        user.setPassword(encryptedPass);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
                return;
            }

            // 3️⃣ Dynamic field update
            Field field = ReflectionUtils.findField(UserInformation.class, key);
            if (field == null) return;

            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });

        userInfoRepo.save(user);

        responseDTO.setResponseCode(1);
        responseDTO.setResponseMessage("User updated successfully (PATCH)");

        return responseDTO;
    }

    private static final Set<String> ALLOWED_FIELDS = Set.of(
            "firstName",
            "lastName",
            "email",
            "username",
            "password",
            "activeFlag"
    );


    public String customHash(String val1, String val2) throws NoSuchAlgorithmException {
        String salt1 = "3T9T4QN423QC";
        String salt2 = "TMF0T9B3PN";
        String salt3 = "X6IW04WI";
        String salt = salt1 + salt3 + salt2;

        int lenVal1 = (int) Math.round(val1.length() / 2.0);
        int lenVal2 = (int) Math.round(val2.length() / 2.0);

        String inputString = salt.substring(19, 27) +  // SUBSTR(L_SALT, 20, 8)
                val2.substring(0, lenVal2) +
                salt.substring(9, 14) +    // SUBSTR(L_SALT, 10, 5)
                val2.substring(lenVal2) +
                salt.substring(14, 19) +   // SUBSTR(L_SALT, 15, 5)
                val1.substring(0, lenVal1) +
                salt.substring(3, 13) +    // SUBSTR(L_SALT, 4, 10)
                val1.substring(lenVal1);

        // Generate the MD5 hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(inputString.getBytes(StandardCharsets.UTF_8));

        // Convert the hash to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("hexString.substring(0, 20).toUpperCase() = " + hexString.substring(0, 20).toUpperCase());

        // Return the first 20 characters of the hash in uppercase
        return hexString.substring(0, 20).toUpperCase();
    }


    private UserInformationDTO maptoDTO(UserInformation userInformation) {
        UserInformationDTO userInformationDTO = mapper.map(userInformation, UserInformationDTO.class);
        return userInformationDTO;
    }

    private UserInformation maptoEntity(UserInformationDTO userInformationDTO) {
        UserInformation userInformation = mapper.map(userInformationDTO, UserInformation.class);
        return userInformation;
    }
}
