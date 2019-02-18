package vdsale.model.vos.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PersonVO extends BasicVO {

    private static final long serialVersionUID = 2740160883523115639L;

    private int id;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String name;

}
