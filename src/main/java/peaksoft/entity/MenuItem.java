package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "menuItem")
    @SequenceGenerator(name = "menuItem_gen",sequenceName = "menuItem_seq",allocationSize = 1)
    private Long id;
    private String name;
    private  String image;
    private int price;
    private String description;
    private Boolean isVegetarian;

    @ManyToMany(cascade = {ALL})
    private List<Cheque> cheques=new ArrayList<>();

    @OneToOne(mappedBy = "menuItem",cascade = {ALL})
    private StopList stopList;

    @ManyToOne(cascade = {PERSIST ,DETACH,REFRESH,MERGE})
    private SubCategory subCategory;

    @ManyToOne(cascade ={PERSIST,DETACH,REFRESH,MERGE} )
    private Restaurant restaurant;

   public void addCheque(Cheque cheque){
        cheques.add(cheque);
    }
}
