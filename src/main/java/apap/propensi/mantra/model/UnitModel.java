package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "unit")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnitModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(nullable = false, name = "plat_nomor")
    private String platNomor;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "driver_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DriverModel driver;
}


