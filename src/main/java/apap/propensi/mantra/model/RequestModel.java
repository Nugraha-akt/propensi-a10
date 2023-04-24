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
    // Created - Request baru dibuat - Udah dibuat, belum dikerjain (customer yang ngebuat, customer milih unit) - output: unit udah ada di request
    // Assigned - Assign driver ke request, sudah bisa download surat - output: driver udah di assign ke unit
    // In-Progress - berangkat - output: dapat diakses/manipulasi statusPerjalanan
    // Finished - request terkumpul, surat bisa diverifikasi - output: surat diverifikasi, request dapat direview
    //
    // luarscope:
    // harus direview
    // dicancel
    //
    // customer buat request (ditampilin list unit yang tersedia) - Created
    // request yang udah ada unit akan tampil di halaman manage driver untuk assign driver ke unit -
    // setelah punya unit & driver, ada tombol download untuk download surat (generate surat)

    @NotNull
    @Column(name = "status_perjalanan", nullable = false)
    private String statusPerjalanan;
    //  valuenya -> nama tempat
    // 'Started'
    // 'Nama-nama tempat' (cuma nama tempat pada suatu waktu)
    // 'Finished'

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

