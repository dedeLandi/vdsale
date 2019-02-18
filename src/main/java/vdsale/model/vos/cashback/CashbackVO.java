package vdsale.model.vos.cashback;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.enums.Weekday;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.category.CategoryVO;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class CashbackVO extends BasicVO {

    private static final long serialVersionUID = 8985022530939562391L;

    private int id;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private CategoryVO category;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private Weekday weekday;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private double cashback;

}
