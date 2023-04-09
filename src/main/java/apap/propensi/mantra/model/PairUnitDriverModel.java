package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "pair_unit_driver")
public class PairUnitDriverModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "driver_uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DriverModel driver;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "unit_uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UnitModel unit;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "request_uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;
}
