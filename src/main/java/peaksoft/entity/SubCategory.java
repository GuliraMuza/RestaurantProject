package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "subCategories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "subCategory_gen")
    @SequenceGenerator(name = "subCategory_gen",sequenceName = "subCategory_seq",allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subCategory",cascade = {ALL})
    private List<MenuItem> menuItems=new ArrayList<>();

    @ManyToOne(cascade = {PERSIST, DETACH,MERGE,REFRESH})
    @JsonIgnore
    private Category category;
}
