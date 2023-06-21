package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.simple.RestaurantDetailsResponse;
import peaksoft.dto.simple.SimpleResponse;

import java.util.List;


public interface RestaurantService {


    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse getRestaurantById(Long restaurantId);

    SimpleResponse deleteById(Long restaurantId);
    SimpleResponse updateRestaurant(Long restaurantId,RestaurantRequest restaurantRequest);


    List<RestaurantDetailsResponse> countUser(Long userId);
}
