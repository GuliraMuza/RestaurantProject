package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.UserResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse UserSave(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("User with email: %s already exists",userRequest.getEmail()))
                    .build();
        }
        if(userRequest.getRole().equals(Role.ADMIN)){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Forbidden")
                    .build();
        }
        if(userRequest.getRole().equals(Role.CHEF)) {
            Period between = Period.between(userRequest.getDateOfBirth(), LocalDate.now());
            if (between.getYears() > 45 || between.getYears() < 25) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("The cook must be between 25 and 45 years of age")
                        .build();
            }        }
        if(userRequest.getRole().equals(Role.WAITER)){
            Period bet = Period.between(userRequest.getDateOfBirth(), LocalDate.now());
            if(bet.getYears()>30 || bet.getYears()<18){
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Waiter must be between 18 and 30 years of age")
                        .build();
            }
        }
        if(userRequest.getRole().equals(Role.WAITER)){
            if(userRequest.getExperience()<1){
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Waiter experience must be between 1 and 10")
                        .build();
            }
        }
        if(userRequest.getRole().equals(Role.CHEF)){
            if(userRequest.getExperience()<2){
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Chef experience must be between 2 and 10")
                        .build();
            }
        }
        Restaurant restaurant=restaurantRepository.findById(1L).orElseThrow(()->new NotFoundException("Not"));
        var count=restaurant.getUsers().size();
        if(count>15){
          throw new BadRequestException("Not vacancy");
        }
        User user = new User();
        userMapToResponse(userRequest, user);
     //   restaurant.setNumberOfEmployees((byte)++count);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("")
                .build();

    }



    @Override
    public UserResponse getUserById(Long userId) {
        User user = jwtService.getAuthentication();
        User user1 = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is not found!"));
        if (user.getRole().equals(Role.ADMIN)){
            if (userRepository.existsById(userId)){
               userMapToResponse(user1);
            }
        }
        else {

        }

        return null;
    }


    @Override
    public SimpleResponse deleteUserById(Long id) {
        User user = jwtService.getAuthentication();
        User user1 = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " is not found!"));

        if (user.getRole().equals(Role.ADMIN)){
            if (userRepository.existsById(id)){
                Restaurant restaurant = user1.getRestaurant();
                restaurant.getUsers().remove(user1);
                restaurant.setNumberOfEmployees((byte) (restaurant.getNumberOfEmployees() - 1));
                userRepository.delete(user1);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Successfully deleted")
                        .build();
            }else throw new NotFoundException("User with id:"+id+" is does not exist...");
        }else {
            if (user.equals(user1)){
                if (userRepository.existsById(id)){
                    Restaurant restaurant = user1.getRestaurant();
                    restaurant.getUsers().remove(user1);
                    restaurant.setNumberOfEmployees((byte) (restaurant.getNumberOfEmployees() - 1));
                    userRepository.delete(user1);

                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.OK)
                            .message("Successfully deleted")
                            .build();
                }else throw new NotFoundException("User with id:"+id+" is does not exist...");
            }else throw new BadCredentialException("You can not get user with id:"+user1.getId());
        }
    }

    @Override
    public SimpleResponse updateUser(Long userId, UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("User with email: %s already exists", userRequest.getEmail()))
                    .build();
        }
        User user = jwtService.getAuthentication();
        User user1 = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is not found!"));
        if (user.getRole().equals(Role.ADMIN)) {
            if (userRepository.existsById(userId)) {
                userMapToResponse(userRequest, user);
                userRepository.save(user);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Successfully updated")
                        .build();
            } else throw new NotFoundException("User with id:" + userId + " is does not exist...");
        } else {
            if (user.equals(user1)) {
                if (userRepository.existsById(userId)) {
                    userMapToResponse(userRequest, user);
                    userRepository.save(user);
                }
            }
        }
        return null;
    }



    public void userMapToResponse(UserRequest userRequest, User user1) {
        user1.setFirstName(userRequest.getFirstName());
        user1.setLastName(userRequest.getLastName());
        user1.setDateOfBirth(userRequest.getDateOfBirth());
        user1.setEmail(userRequest.getEmail());
        user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user1.setExperience(userRequest.getExperience());
        user1.setPhoneNumber(userRequest.getPhoneNumber());
        user1.setRole(userRequest.getRole());

        userRepository.save(user1);
    }


    @Override
    public UserResponsePagination searchAndPagination(String text, int page, int size){
        Pageable pageable=  PageRequest.of(page-1,size);
        UserResponsePagination userResponsePagination=new UserResponsePagination();
        userResponsePagination.setUserResponses(view(search(text,pageable)));
        return  userResponsePagination;
    }

    public List<UserResponse>view(List<UserResponse> categories){
        List<UserResponse>userResponses=new ArrayList<>();
        for (UserResponse m:categories){
            userResponses.add(mapToResponse(m));
        }
        return userResponses;
    }

    private List<UserResponse> search(String text, Pageable pageable){
        String name=text == null  ? "" :text;
        return  userRepository.searchAndPagination(name.toUpperCase(), pageable);
    }

    @Override
    public SimpleResponse assignEmployees(Long userId, Long restaurantId, String word) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id not found"));
        if (word.equals("accepted")) {
            var count=restaurant.getUsers().size();
            if(count>15){
                throw new BadRequestException("Not vacancy");
            }
            restaurant.setNumberOfEmployees((byte)++count);
            user.setRestaurant(restaurant);
            restaurant.getUsers().add(user);
            userRepository.save(user);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Accepted to Restaurant!")
                    .build();
        } else if (word.equals("rejected")) {
            userRepository.delete(user);

        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("successfully deleted")
                .build();
    }/*  for (Long manuItem : chequeRequest.manuItems()) {
            MenuItem menuItem = menuItemRepository.findById(manuItem).orElseThrow(() -> new NotFoundException("This manu item is not found"));
            menuItem.addCheque(cheque);
            menuItemList.add(menuItem);
            sum += menuItem.getPrice();
        }*/


    public UserResponse mapToResponse(UserResponse userResponse){
        UserResponse user1=new UserResponse();
        user1.setFirstName(userResponse.getFirstName());
        user1.setLastName(userResponse.getLastName());
        user1.setDateOfBirth(userResponse.getDateOfBirth());
        user1.setEmail(userResponse.getEmail());
        user1.setPassword(userResponse.getPassword());
        user1.setExperience(userResponse.getExperience());
        user1.setPhoneNumber(userResponse.getPhoneNumber());
        user1.setRole(userResponse.getRole());
        return user1;
    }

    private void userMapToResponse( User user1) {
        user1.getId();
        user1.getFirstName();
        user1.getLastName();
        user1.getDateOfBirth();
        user1.getEmail();
        user1.getPassword();
        user1.getExperience();
        user1.getPhoneNumber();
        user1.getRole();
    }









/* if(restaurant.getUsers.equals(userId){
      userRepository.deleteById(userId)}*/



























      /*  @Override
    public SimpleResponse assignEmployees(UserAssignRequest userAssignRequest) {


        if (userAssignRequest.userId()==1){
            throw new AlreadyExistException("Conflict!!!");
        }
        Restaurant restaurant = restaurantRepository.findById(userAssignRequest.restaurantId()).orElseThrow(() -> new NoSuchElementException("Restaurant with id doesn't exists"));
        User user1 = userRepository.findById(userAssignRequest.userId()).orElseThrow(() -> new NoSuchElementException("User with id doesn't exists"));
        user1.setRestaurant(restaurant);
        restaurant.addEmployees(user1);
        userRepository.save(user1);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Jumushka kabyl alyndy").build();

    }*/
}
