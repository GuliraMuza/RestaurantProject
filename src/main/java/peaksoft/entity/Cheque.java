package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cheque_gen")
    @SequenceGenerator(name = "cheque_gen",sequenceName = "cheque_seq",allocationSize = 1)
    private Long id;
    private int priceTotal;
    private LocalDate createdAt;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH,PERSIST})
    private User user;

    @ManyToMany(mappedBy = "cheques",cascade = {PERSIST, DETACH,REFRESH,MERGE})
    private List<MenuItem> menuItems=new ArrayList<>();
}
