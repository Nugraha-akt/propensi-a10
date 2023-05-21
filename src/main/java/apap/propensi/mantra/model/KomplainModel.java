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
    // 0 = "New"
    // 1 = "Closed"

    @Enumerated(EnumType.STRING)
    @Column(name = "kategori_komplain", nullable = false)
    private KategoriKomplain kategoriKomplain;

    @NotNull
    @Column(nullable = false, name = "isi")
    private String isi;

    @Column(name = "respon")
    private String respon;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "responded_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime respondedAt;

    @OneToOne
    @JoinColumn(name = "request_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RequestModel request;

//    @OneToMany(mappedBy = "komplain", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<KategoriKomplainModel> listKategori;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "customer_service_uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerServiceModel customerService;

//    @ManyToOne(fetch= FetchType.EAGER)
//    @JoinColumn(name = "request_id", nullable = true)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private RequestModel request;
}

