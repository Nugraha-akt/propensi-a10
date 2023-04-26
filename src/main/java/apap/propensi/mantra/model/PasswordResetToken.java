package apap.propensi.mantra.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String token;
    @OneToOne(targetEntity = UserModel.class, fetch = FetchType.EAGER)
    private UserModel user;
    @Column(nullable = false)
    private LocalDateTime expirationDate;
}
