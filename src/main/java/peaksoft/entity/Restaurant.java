package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.RestType;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Restaurant {
    @Id
    @SequenceGenerator(
            name = "restaurant_id_gen",
            sequenceName = "restaurant_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "restaurant_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestType restType;
    private Byte numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<MenuItem> menuItems=new ArrayList<>();
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<User> users=new ArrayList<>();

}
