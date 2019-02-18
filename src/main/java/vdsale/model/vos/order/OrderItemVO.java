package vdsale.model.vos.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vdsale.model.vos.BasicVO;
import vdsale.model.vos.disc.DiscVO;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderItemVO extends BasicVO {

    private static final long serialVersionUID = 5205406120124857230L;

    private int id;
    private DiscVO disc;
    private int qtd;
    private double unitPrice;
    private double total;
    private double cashback;
    private double cashbackTotal;

}
