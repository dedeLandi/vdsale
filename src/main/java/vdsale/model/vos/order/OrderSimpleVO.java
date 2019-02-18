package vdsale.model.vos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.constants.ValidationConstants;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.person.PersonVO;
import vdsale.utils.CustomDateDeserializer;
import vdsale.utils.CustomDateSerializer;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderSimpleVO  extends BasicVO {

    private static final long serialVersionUID = 6365269698286621365L;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private PersonVO person;
    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private List<OrderItemSimpleVO> items;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date creationDate;

}
