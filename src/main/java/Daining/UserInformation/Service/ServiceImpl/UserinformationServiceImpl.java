package Daining.UserInformation.Service.ServiceImpl;

import Daining.UserInformation.DTO.UserInformationDTO;
import Daining.UserInformation.Model.UserInformation;
import Daining.UserInformation.Repository.UserInfoRepo;
import Daining.UserInformation.Service.UserInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UserInformation> getAllUsers1() {
        List<UserInformation> userInformations =userInfoRepo.findAll();
        return userInformations;
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
