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
@Table(name = "komplain")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KomplainModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "judul")
    private String judul;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(nullable = false, name = "keterangan")
    private String keterangan;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_service_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerServiceModel customerService;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;
}

