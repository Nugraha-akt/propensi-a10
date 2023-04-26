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
@Table(name = "surat")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuratModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "no_surat")
    private String noSurat;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "foto", nullable = true)
    private String foto;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "driver_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DriverModel driver;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "unit_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UnitModel unit;
}
