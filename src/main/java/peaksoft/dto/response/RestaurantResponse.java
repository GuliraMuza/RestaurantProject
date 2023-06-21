package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.RestType;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private RestType restType;
    private Byte numberOfEmployees;
    private int service;

}