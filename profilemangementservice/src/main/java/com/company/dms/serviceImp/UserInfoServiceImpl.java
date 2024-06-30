package com.company.dms.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.TokenDto;
import com.company.dms.dto.userdto.*;
import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.Role;
import com.company.dms.entity.User;
import com.company.dms.exception.*;
import com.company.dms.otherservicecall.LocationService;
import com.company.dms.repository.UserInfoRepo;
import com.company.dms.service.RetailerConnectionService;
import com.company.dms.service.RoleInfoService;
import com.company.dms.service.UserInfoService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import reactor.core.publisher.Mono;

import java.util.*;
import java.util.logging.Logger;

/**
 * Implementation class for UserInfoService interface. This class provides
 * functionalities related to user information management such as retrieving user
 * details, user registration, user login, user approval, user removal, and more.
 * It interacts with repositories, services, and other components to perform these operations.
 */
@LoggableClass
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final Logger logger = Logger.getLogger(UserInfoServiceImpl.class.getName());
    @Value("${my.password}")
    private String password;
    private final String[] roleNames;
    private final ModelMapper mapper;
    private final LocationService locationService;
    private final RoleInfoService roleInfoService;
    private final UserInfoRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserInfoServiceImpl(ModelMapper mapper,
                               LocationService locationService,
                               RoleInfoService roleInfoService,
                               UserInfoRepo userRepo,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mapper = mapper;
        this.locationService = locationService;
        this.roleInfoService = roleInfoService;
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        roleNames = new String[]{"admin","distributor","retailer"} ;
    }

//    Due to circular dependency of RetailerConnectionService and UserInfoService
//    used setter injection in constructor injection it is not working
    private RetailerConnectionService retailerConnectionService;
    @Autowired
    public void setRetailerConnectionService(RetailerConnectionService retailerConnectionService) {
        this.retailerConnectionService = retailerConnectionService;
    }

    /**
     * Retrieves user information based on the provided user ID.
     *
     * @param userId The ID of the user whose information is to be retrieved.
     * @return UserDto containing the user information.
     * @throws NoSuchUserPresentException if no user is found with the given user ID.
     */
    @LoggableMethod
    @Override
    public UserDto getUserInfo(Long userId) {
      User user = userRepo.findById(userId).orElseThrow(()->new NoSuchUserPresentException("User with ID " + userId + " not found"));
      return userToDto(user);
    }

    /**
     * Retrieves user information based on the provided email address.
     *
     * @param mail The email address of the user whose information is to be retrieved.
     * @return UserServiceResponseDto containing the user information.
     * @throws NoSuchUserPresentException if no user is found with the given email address.
     */
    @LoggableMethod
    @Override
    public UserServiceResponseDto getUserInfoByMail(String mail) {
        Optional<User> user = userRepo.findByEmail(mail);
        if(user.isEmpty()) throw new NoSuchUserPresentException("No user found");
        return userServiceResponseDtoToDto(user.get());
    }

    /**
     * Saves user information into the system after encoding the password.
     *
     * @param mail The email of the user.
     * @param password The password of the user.
     * @return UserDto The user data transfer object after saving.
     */
    @LoggableMethod
    @Override
    public UserDto saveUser(String mail, String password) {
        User user = findByEmail(mail);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userToDto(userRepo.save(user));
    }

    /**
     * Registers a new user based on the provided user registration data.
     *
     * @param userRegistrationDto The user registration data transfer object.
     * @return String The username of the registered user.
     * @throws AlreadyEmailExistException if the email is already registered.
     * @throws AlreadyEmailExistException if the mobile number is already registered.
     * @throws InvalidRoleException if an invalid role is provided.
     */
    @LoggableMethod
    @Override
    public String registration(UserRegistrationDto userRegistrationDto) {
        pinCodeCityMatching(userRegistrationDto.getPinCode(), userRegistrationDto.getArea()).block();
        Optional<User> optionalUser = userRepo.findByEmail(userRegistrationDto.getEmail());
        if(optionalUser.isPresent()){
            throw new AlreadyEmailExistException("Email must be unique, this email is already registered");
        }
        Optional<User> optionalUser1 = userRepo.findByMobile(userRegistrationDto.getMobile());
        if(optionalUser1.isPresent()){
            throw new AlreadyEmailExistException("Mobile number must be unique, this number is already registered");
        }
        if(roleNames[0].equalsIgnoreCase(userRegistrationDto.getRole())){
            throw new InvalidRoleException("You should choose either distributor or retailer as role");
        }
        User user = userRegistrationDtoToUser(userRegistrationDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleInfoService.findByRoleName(userRegistrationDto.getRole());
        user.setRole(role);
        user.setActive(true);
        return userRepo.save(user).getUsername();
    }

    /**
     * Validates user login credentials.
     *
     * @param userLoginDto The user login data transfer object.
     * @return Boolean True if login is successful, false otherwise.
     * @throws UserNotApprovedException if the user is not approved.
     */
    @LoggableMethod
    @Override
    public Boolean login(UserLoginDto userLoginDto) {
        User user = findByEmail(userLoginDto.getEmail());
        if(IsApproved.NOTAPPROVED.equals(user.getIsApproved()) && !user.isActive()) throw new UserNotApprovedException("You are not the part of organisation now");
        return bCryptPasswordEncoder.matches(userLoginDto.getPassword(),user.getPassword());
    }

    /**
     * Approves or rejects user requests based on the provided data.
     *
     * @param userId The ID of the approving user.
     * @param userDataDto The user data transfer object containing request details.
     * @throws NoSuchUserPresentException if the user is not found.
     * @throws NotActiveAnyMoreException if the user is not active.
     * @throws NotPermissionException if an invalid role is provided.
     */
    @LoggableMethod
    @Override
    public void userApproval(Long userId, UserDataDto userDataDto){
        User approverUser = userRepo.findByUserId(userId).orElseThrow(()->new NoSuchUserPresentException("No such user Found"));
        if(!approverUser.isActive()) throw new NotActiveAnyMoreException("you are not the part of organisation anymore");
        User requesterUser = userRepo.findByUserId(userDataDto.getUserId()).orElseThrow(()->new NoSuchUserPresentException("No such user Found"));
        if(!requesterUser.isActive()) throw new NotActiveAnyMoreException("This user is not the part of organisation anymore");
        if(roleNames[1].equalsIgnoreCase(userDataDto.getRole())){
            distributorApproval(requesterUser,userDataDto);
        }else if(roleNames[2].equalsIgnoreCase(userDataDto.getRole())) {
            retailerApproval(requesterUser);
        }else{
            throw new NotPermissionException("No permission for the role admin");
        }

    }

    /**
     * Retrieves all pending requests for a user with admin or distributor role.
     *
     * @param tokenDto The token data transfer object.
     * @return List<UserDto> The list of user data transfer objects.
     */
    @LoggableMethod
    @Override
    public List<UserDto> allRequest(TokenDto tokenDto) {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = new ArrayList<>();
        if (roleNames[0].equalsIgnoreCase(tokenDto.getRole().getName())){
            Role distributorRole = roleInfoService.findByRoleName(roleNames[1]);
            users = findUserByRoleAndIsActiveAndIsApproved(distributorRole,true,IsApproved.NOTAPPROVED);
            List<User> approvedUsers = findUserByRoleAndIsActiveAndIsApproved(distributorRole,true,IsApproved.APPROVED);
            List<User> copyOfUsersList = List.copyOf(users);
            for (User user : copyOfUsersList) {
                for (User approvedUser : approvedUsers) {
                    if (user.getArea().equalsIgnoreCase(approvedUser.getArea())) {
                        users.remove(user);
                    }
                }
            }
        }else if(roleNames[1].equalsIgnoreCase(tokenDto.getRole().getName())){
            User distributor = userRepo.findById(tokenDto.getUserId()).orElseThrow(()->new NoSuchUserPresentException("No user found"));
            if(distributor.getIsApproved().equals(IsApproved.NOTAPPROVED)) throw new UserNotApprovedException("you are not approved");
            if(!distributor.isActive()) throw new NotActiveAnyMoreException("you are not the part of company now");
            Role retailerRole = roleInfoService.findByRoleName(roleNames[2]);
            users = findUserByRoleAndAreaAndIsActiveAndIsApproved(retailerRole, distributor.getArea(),true,IsApproved.NOTAPPROVED);
            // Filters out approved users
            List<User> approvedUsers = findUserByRoleAndAreaAndIsActiveAndIsApproved(retailerRole, distributor.getArea(),true,IsApproved.APPROVED);
            List<User> copyOfUsersList = List.copyOf(users);
            for (User user : copyOfUsersList) {
                for (User approvedUser : approvedUsers) {
                    if (user.getPinCode().equalsIgnoreCase(approvedUser.getPinCode())) {
                        users.remove(user);
                    }
                }
            }
        }
        // Converts users to userDto and returns
        for (User user : users) {
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }

    /**
     * Removes a user based on the provided token and user data.
     *
     * @param tokenDto The token data transfer object.
     * @param userDataDto The user data transfer object.
     * @return UserDto The removed user data transfer object.
     * @throws AdminCanNotRemoveException if admin tries to remove a user.
     */
    @LoggableMethod
    @Override
    public UserDto removeUser(TokenDto tokenDto, UserDataDto userDataDto) {
        if(roleNames[2].equalsIgnoreCase(userDataDto.getRole())){
            return removeRetailer(tokenDto, userDataDto);
        }else if(roleNames[1].equalsIgnoreCase(userDataDto.getRole())){
            return removeDistributor(tokenDto, userDataDto);
        }
        throw new AdminCanNotRemoveException("Admin cannot be remove");
    }

    /**
     * Retrieves active and approved users.
     *
     * @return List<UserDto> The list of active and approved user data transfer objects.
     * @throws NoSuchUserPresentException if no active user is present.
     */
    @LoggableMethod
    @Override
    public List<UserDto> getActiveApprovedUsers() {
        List<User> users = userRepo.findByIsActiveAndIsApproved(true,IsApproved.APPROVED).orElseThrow(()->new NoSuchUserPresentException("No active user present exception"));
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users){
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }


    /**
     * Finds users based on role, active status, and approval status.
     *
     * @param role The role of the users to be found.
     * @param isActive The active status of the users.
     * @param isApproved The approval status of the users.
     * @return List<User> The list of users.
     * @throws NoRequestAvailableException if no request is found.
     */
    @LoggableMethod
    @Override
    public List<User> findUserByRoleAndIsActiveAndIsApproved(Role role, boolean isActive, IsApproved isApproved) {
        return userRepo.findByRoleAndIsActiveAndIsApproved(role,isActive,isApproved)
                .orElseThrow(()->new NoRequestAvailableException("No request found"));
    }


    /**
     * Matches pin code with city.
     *
     * @param pinCode The pin code.
     * @param enteredArea The entered area.
     * @return Mono<Void> A Mono representing completion of the operation.
     * @throws PincodeCityMismatchException if pin code and city do not match.
     */
    @LoggableMethod
    private Mono<Void> pinCodeCityMatching(Long pinCode, String enteredArea){
        return locationService.getCityByPincode(String.valueOf(pinCode))
                .flatMap(city -> {
                    String[] cityName = city.split(",");
                    String fetchedCity = cityName[0].trim();
                    if (!fetchedCity.equalsIgnoreCase(enteredArea)) {
                        throw new PincodeCityMismatchException("Entered wrong city or pinCode");
                    }
                    return Mono.empty();
                })
                .onErrorResume(PincodeCityMismatchException.class, Mono::error).then();
    }

    /**
     * Approves distributor request and manages retailer connections.
     *
     * @param distributor The distributor user.
     * @param userDataDto The user data transfer object.
     * @throws AlreadyDistributorOfThisAreaExist if distributor of the area already exists.
     */
    @LoggableMethod
    private void distributorApproval(User distributor, UserDataDto userDataDto){
        Role role = roleInfoService.findByRoleName(userDataDto.getRole().toLowerCase());
        Optional<User> optionalUser = userRepo.findByAreaAndRoleAndIsActiveAndIsApproved(userDataDto.getArea().toLowerCase(), role, true, IsApproved.APPROVED);
        if(optionalUser.isEmpty()){
            distributor.setActive(true);
            distributor.setIsApproved(IsApproved.APPROVED);
            userRepo.save(distributor);
            Role retailerRole = roleInfoService.findByRoleName(roleNames[2]);
            Optional<List<User>> retailer = userRepo.findByAreaAndRoleAndIsActive(userDataDto.getArea().toLowerCase(), retailerRole, true);
            List<User> activeApprovedRetailer = new ArrayList<>();
            if (retailer.isPresent()){
                DistributorToRetailer distributorConnection = new DistributorToRetailer();
                distributorConnection.setDistributor(distributor);
                for(User user : retailer.get()){
                    if(IsApproved.ONCEAPPROVED.equals(user.getIsApproved())){
                        activeApprovedRetailer.add(user);
                    }
                }
                distributorConnection.setRetailers(activeApprovedRetailer);
                retailerConnectionService.saveDistributorConnection(distributorConnection);
            }
            for(User user : activeApprovedRetailer){
                user.setIsApproved(IsApproved.APPROVED);
                userRepo.save(user);
            }
        }else{
            throw new AlreadyDistributorOfThisAreaExist("Distributor of this area already exist");
        }

    }

    /**
     * Retrieves users based on role, area, active status, and approval status.
     *
     * @param role The role of the users to be found.
     * @param area The area of the users to be found.
     * @param isActive The active status of the users.
     * @param isApproved The approval status of the users.
     * @return List<User> The list of users.
     * @throws NoRequestAvailableException if no request is found.
     */
    private List<User> findUserByRoleAndAreaAndIsActiveAndIsApproved(Role role, String area, boolean isActive, IsApproved isApproved){
        Optional<List<User>> retailerList = userRepo.findByRoleAndAreaAndIsActiveAndIsApproved(role,area,isActive,isApproved);
        if(retailerList.isEmpty()) throw new NoRequestAvailableException("No retailer request is present");
        return retailerList.get();
    }

    /**
     * Approves retailer request and manages distributor connections.
     *
     * @param user The retailer user.
     * @throws RetailerAlreadyExist if retailer already exists.
     */
    @LoggableMethod
    private void retailerApproval(User user){
        user.setIsApproved(IsApproved.APPROVED);
        Role distributorRole = roleInfoService.findByRoleName(roleNames[1]);
        User distributor = userRepo.findByAreaAndRoleAndIsActiveAndIsApproved(user.getArea(), distributorRole, true, IsApproved.APPROVED).orElseThrow(()->new NoSuchUserPresentException("Distributor Not Exist of this area"));
        DistributorToRetailer distributorConnection = retailerConnectionService.distributorToRetailerConnection(distributor);
        List<User> retailers = distributorConnection.getRetailers();
        if(retailers.contains(user)) throw new RetailerAlreadyExist("retailer already exist");
        boolean pinCodeRetailerExist = true ;
        Role role = roleInfoService.findByRoleName(roleNames[2]);
        Optional<List<User>> existRetailer = userRepo.findByRoleAndIsActiveAndIsApproved(role, true,IsApproved.APPROVED);
        if(existRetailer.isEmpty()) pinCodeRetailerExist = false;
        if(pinCodeRetailerExist){
            for(User existUser : existRetailer.get()){
                if(Objects.equals(existUser.getPinCode(), user.getPinCode())) throw new RetailerAlreadyExist("retailer of this pinCode already exist");
            }
        }
        retailers.add(user);
        distributorConnection.setRetailers(retailers);
        retailerConnectionService.saveDistributorConnection(distributorConnection);
        userRepo.save(user);
    }

    /**
     * Removes a retailer based on the provided token and user data.
     *
     * @param tokenDto The token data transfer object.
     * @param userDataDto The user data transfer object.
     * @return UserDto The removed retailer data transfer object.
     * @throws ConnectionNotFoundException if the user is not found in the connection.
     */
    @LoggableMethod
    private UserDto removeRetailer(TokenDto tokenDto, UserDataDto userDataDto){
        if(roleNames[1].equalsIgnoreCase(tokenDto.getRole().getName())){
            User distributor = userRepo.findById(tokenDto.getUserId()).orElseThrow(()->new NoSuchUserPresentException("No such distributor found"));
            if(distributor.getIsApproved().equals(IsApproved.NOTAPPROVED)) throw new UserNotApprovedException("You are not approved");
            DistributorToRetailer distributorToRetailer = retailerConnectionService.distributorToRetailerConnection(distributor);
            User retailer = userRepo.findById(userDataDto.getUserId()).orElseThrow(()->new NoSuchUserPresentException("No such retailer found"));
            if(!distributorToRetailer.getRetailers().contains(retailer)){
                throw new ConnectionNotFoundException("This user is not exist in your connection");
            }
            retailer.setIsApproved(IsApproved.NOTAPPROVED);
            retailer.setActive(false);
            User user = userRepo.save(retailer);
            distributorToRetailer.getRetailers().remove(retailer);
            retailerConnectionService.saveDistributorConnection(distributorToRetailer);
            return userToDto(user);
        }
        throw new NoAuthorizedException("you are not authorized to remove the retailer");
    }

    /**
     * Removes a distributor based on the provided token and user data.
     *
     * @param tokenDto The token data transfer object.
     * @param userDataDto The user data transfer object.
     * @return UserDto The removed distributor data transfer object.
     */
    @LoggableMethod
    private UserDto removeDistributor(TokenDto tokenDto, UserDataDto userDataDto){
        if(roleNames[0].equalsIgnoreCase(tokenDto.getRole().getName())){
            User distributor = userRepo.findById(userDataDto.getUserId()).orElseThrow(()->new NoSuchUserPresentException("No such retailer found"));
            distributor.setIsApproved(IsApproved.NOTAPPROVED);
            distributor.setActive(false);
            User user = userRepo.save(distributor);
            DistributorToRetailer distributorToRetailer = retailerConnectionService.distributorToRetailerConnection(distributor);
            List<User> retailers = distributorToRetailer.getRetailers();
            retailerConnectionService.removeDistributorRetailerConnection(distributor);
            for(User retailer : retailers){
                retailer.setIsApproved(IsApproved.ONCEAPPROVED);
                userRepo.save(retailer);
            }
            return userToDto(user);
        }
        throw new NoAuthorizedException("you are not authorized to remove the distributor");
    }

    /**
     * Finds user by email.
     *
     * @param email The email of the user.
     * @return User The user entity.
     * @throws InvalidEmailException if the email is invalid.
     */
    @LoggableMethod
    private User findByEmail(String email){
        return userRepo.findByEmail(email)
                .orElseThrow(()->new InvalidEmailException("Entered Invalid Email"));
    }

    @Override
    public void adminEntry(){
        User admin = new User();
        admin.setUserId(1L);
        admin.setPassword(password);
        admin.setIsApproved(IsApproved.APPROVED);
        admin.setUsername("shivam");
        admin.setEmail("choudharyshivam6784@gmail.com");
        admin.setAddress("Indore");
        admin.setArea("India");
        admin.setPinCode("452010");
        admin.setMobile(9993431346L);
        admin.setActive(true);
        admin.setCreatedAt(new Date());
        Role role = roleInfoService.findByRoleName(roleNames[0]);
        admin.setRole(role);
        userRepo.save(admin);
    }

    // Method to convert User to UserDto
    /**
     * Converts User entity to UserDto.
     *
     * @param user The user entity.
     * @return UserDto The user data transfer object.
     */
    private UserDto userToDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setPassword(null);
        return userDto;
    }

    /**
     * Converts UserServiceResponseDto to UserDto.
     *
     * @param user The user entity.
     * @return UserServiceResponseDto The user service response data transfer object.
     */
    private UserServiceResponseDto userServiceResponseDtoToDto(User user) {
        return mapper.map(user, UserServiceResponseDto.class);
    }

    /**
     * Converts UserRegistrationDto to User entity.
     *
     * @param userDto The user registration data transfer object.
     * @return User The user entity.
     */
    private User userRegistrationDtoToUser(UserRegistrationDto userDto) {
        return mapper.map(userDto, User.class);
    }
}

