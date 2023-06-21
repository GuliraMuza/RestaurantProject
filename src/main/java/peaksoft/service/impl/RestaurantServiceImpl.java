package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.simple.RestaurantDetailsResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundException;
import peaksoft.exception.ValidationException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if (restaurantRepository.existsByName(restaurantRequest.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Restaurant with name : %s already exists", restaurantRequest.getName()))
                    .build();
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User with id: %s not found".formatted(email)));
        Restaurant restaurant =new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());
        restaurantRepository.save(restaurant);
        user.setRestaurant(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name : %s successfully saved ...!",restaurantRequest.getName()))
                .build();
    }

/*
    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if (restaurantRepository.existsByName(restaurantRequest.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Restaurant with name : %s already exists", restaurantRequest.getName()))
                    .build();
        }
        if (!restaurantRepository.findAll().isEmpty()) {
            throw new ValidationException("You mast save only 1 Restaurant");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequest.getName())
                .location(restaurantRequest.getLocation())
                .restType(restaurantRequest.getRestType())
                .service(restaurantRequest.getService())
                .numberOfEmployees(0).build();

        restaurantRepository.save(restaurant);

        user.setRestaurant(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully saved!",
                        restaurantRequest.getName()))
                .build();
    }*/

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Not"));
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .restType(restaurant.getRestType())
                .numberOfEmployees(restaurant.getNumberOfEmployees())
                .service(restaurant.getService())
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long restaurantId) {
        if(!restaurantRepository.existsById(restaurantId)){
            throw new NotFoundException("Restaurant with id:" +restaurantId+" not found!");
        }
        restaurantRepository.deleteById(restaurantId);
         return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with id : %s is deleted!", restaurantId)).build();
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Not"));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());

        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully updated!",
                        restaurantRequest.getName()))
                .build();

    }


/*
    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", restaurantId)));
        if (restaurant.getUsers().size() <= 15) {
            restaurant.setNumberOfEmployees(restaurant.getUsers().size());
            restaurantRepository.save(restaurant);

            return restaurantRepository.getRestaurantById(restaurantId).
                    orElseThrow(()->new NotFoundException(String.
                            format("Restaurant with id"+restaurantId+"doesn't exist")));
        } else {
            throw new AlreadyExistException("No vacancies");
        }
    }*/

    @Override
public List<RestaurantDetailsResponse> countUser(Long userId){
    return restaurantRepository.countUser(userId);
}}



