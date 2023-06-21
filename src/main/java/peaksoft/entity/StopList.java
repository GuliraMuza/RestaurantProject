package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "stopLists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stopList_gen")
    @SequenceGenerator(name = "stopList_gen",sequenceName = "stopList_seq",allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    //TO DO mappedBy = "stopList",
    @OneToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE})
    private MenuItem menuItem;
}
