package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "request")

public class RequestModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tujuan", nullable = false)
    private String tujuan;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "status_perjalanan", nullable = false)
    private String statusPerjalanan;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "depart_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departDate;

    @NotNull
    @Column(name = "return_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime returnDate;

    @NotNull
    @Column(name = "alasan", nullable = false)
    private String alasan;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PairUnitDriverModel> listPairRequest;
}

