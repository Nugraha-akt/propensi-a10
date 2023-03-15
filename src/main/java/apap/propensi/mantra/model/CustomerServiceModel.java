package apap.propensi.mantra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer_service")
@PrimaryKeyJoinColumn(name = "customerServiceUuid")
@Setter
@Getter
@AllArgsConstructor
public class CustomerServiceModel extends UserModel {

}

