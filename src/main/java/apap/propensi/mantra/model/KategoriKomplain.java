package apap.propensi.mantra.model;


import lombok.Getter;

@Getter
public enum KategoriKomplain {

    SERVICE ("Layanan"),
    UNIT ("Unit"),
    DRIVER ("Driver"),
    TIME ("Waktu"),
    OTHER ("Lainnya");

    private final String name;

    KategoriKomplain(String name) {
        this.name = name;
    }
}

