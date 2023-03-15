package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "managerUuid")
@Setter
@Getter
@AllArgsConstructor
public class ManagerModel extends UserModel {

}

