package apap.propensi.mantra.helper;

import apap.propensi.mantra.model.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUnitHelper {
    private RequestModel requestModel;
    private List<CustomUnitPair> selectedUnit;
}
