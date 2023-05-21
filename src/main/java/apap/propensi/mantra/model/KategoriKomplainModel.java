package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "kategori_komplain")
public class KategoriKomplainModel {
    public KategoriKomplainModel(KomplainModel komplain, KategoriKomplain kategori) {
//        this.komplain = komplain;
        this.kategori = kategori;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch= FetchType.EAGER)
//    @JoinColumn(name = "komplain_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private KomplainModel komplain;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategori", nullable = false)
    private KategoriKomplain kategori;
}
