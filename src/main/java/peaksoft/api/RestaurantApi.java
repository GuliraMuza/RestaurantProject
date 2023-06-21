package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.service.RestaurantService;

@RestController
@RequestMapping("/resApi")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class RestaurantApi {
    private final RestaurantService restaurantService;


    @PostMapping()
    public SimpleResponse save(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/{restaurantId}")
    public RestaurantResponse getRestaurantById(@PathVariable Long restaurantId){
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateRestaurant(@PathVariable Long id,@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.updateRestaurant(id,restaurantRequest);
    }

    @DeleteMapping("/{restaurantId}")
    public SimpleResponse deleteRestaurantById(@PathVariable Long restaurantId){
        return restaurantService.deleteById(restaurantId);
    }


}
