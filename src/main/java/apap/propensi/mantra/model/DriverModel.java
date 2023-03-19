package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "driver")
@PrimaryKeyJoinColumn(name = "driverUuid")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverModel extends UserModel {

    @NotNull
    @Size(max = 16)
    @Column(name = "sim", nullable = false)
    private String sim;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listRequest;
}

