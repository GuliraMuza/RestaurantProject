package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.UserResponsePagination;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserApi {
    private final UserService userService;



    @PostMapping("/save")
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest) {
        return userService.UserSave(userRequest);
    }


    @GetMapping
    public UserResponsePagination getAllUsers(@RequestParam(name = "text", required = false) String text,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        return userService.searchAndPagination(text, page, size);
    }


    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }


    @PutMapping("/{id}")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }


    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/{restaurantId}/{userId}")
    public SimpleResponse assignEmployees(@PathVariable Long userId,@PathVariable Long restaurantId,@RequestParam(required = false,defaultValue = "accepted") String word) {
        return userService.assignEmployees(userId,restaurantId,word);
    }

}
