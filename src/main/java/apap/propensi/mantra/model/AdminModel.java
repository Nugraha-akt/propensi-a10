package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "adminUuid")
@Setter
@Getter
@AllArgsConstructor
public class AdminModel extends UserModel {

}

