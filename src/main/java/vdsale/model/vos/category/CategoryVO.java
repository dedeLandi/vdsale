package vdsale.model.vos.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class CategoryVO extends BasicVO {

    private static final long serialVersionUID = 6839708168773927897L;

    private int id;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String spotifyId;
    private String name;
}
