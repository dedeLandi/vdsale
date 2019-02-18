package vdsale.model.vos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.person.PersonVO;
import vdsale.utils.CustomDateDeserializer;
import vdsale.utils.CustomDateSerializer;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderVO extends BasicVO {

    private static final long serialVersionUID = -8896855169671315587L;

    private int id;
    private PersonVO person;
    private List<OrderItemVO> items;
    private double total;
    private double cashbackTotal;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date creationDate;

}
