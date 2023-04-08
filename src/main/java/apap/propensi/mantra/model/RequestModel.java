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
    @Column(nullable = false, name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "depart_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end")
    private LocalDateTime end;

    @NotNull
    @Column(name = "alasan", nullable = false)
    private String alasan;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "driver_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DriverModel driver;
}

