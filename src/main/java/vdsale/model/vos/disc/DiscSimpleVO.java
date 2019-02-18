package vdsale.model.vos.disc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class DiscSimpleVO extends BasicVO {

    private static final long serialVersionUID = 4633569872852186692L;

    private int id;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String spotifyId;
    private String name;
}
