package vdsale.model.vos.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.disc.DiscSimpleVO;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderItemSimpleVO extends BasicVO {

    private static final long serialVersionUID = 9000510582748811972L;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private DiscSimpleVO disc;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private int qtd;

}
