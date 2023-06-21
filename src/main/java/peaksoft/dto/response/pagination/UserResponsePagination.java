package peaksoft.dto.response.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.UserResponse;

import java.util.List;
@Getter
@Setter
public class UserResponsePagination {

    private List<UserResponse>userResponses;
}
