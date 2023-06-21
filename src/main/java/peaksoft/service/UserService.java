package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pagination.UserResponsePagination;
import peaksoft.dto.simple.SimpleResponse;


public interface UserService {

    SimpleResponse UserSave(UserRequest userRequest);

    UserResponse getUserById(Long userId);

    SimpleResponse deleteUserById(Long id);

    SimpleResponse updateUser(Long useId, UserRequest userRequest);

    UserResponsePagination searchAndPagination(String text, int page, int size);

    SimpleResponse assignEmployees(Long userId, Long restaurantId, String word);
}
