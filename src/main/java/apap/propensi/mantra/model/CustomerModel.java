package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "customerUuid")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel extends UserModel {
    @NotNull
    @Column(name = "asal_instansi", nullable = false)
    private String asalInstansi;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestModel> listRequest;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KomplainModel> listKomplain;
}

