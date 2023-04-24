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
import java.util.List;

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
    @Column(name = "jenis", nullable = false)
    private String jenis;

    @NotNull
    @Column(name = "plat_nomor", nullable = false, unique = true)
    private String platNomor;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PairUnitDriverModel> listPairRequest;
}


