package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_gen")
    @SequenceGenerator(name = "category_gen",sequenceName = "category_seq",allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy ="category",cascade = {ALL})
    private List<SubCategory>subCategories=new ArrayList<>();
}
