package vdsale.model.vos.order;

import org.springframework.data.domain.Page;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.OrderEntity;
import vdsale.model.entities.OrderItemEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.disc.DiscConverter;
import vdsale.model.vos.page.PageVO;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static vdsale.utils.DecimalUtils.formatTwoDecimalPlacesToDouble;

public class OrderItemConverter implements IBasicConverter<OrderItemEntity, OrderItemVO> {

    private static OrderItemConverter INSTANCE;

    private OrderItemConverter() {
    }

    public static OrderItemConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new OrderItemConverter();
        }
        return INSTANCE;
    }

    public Set<OrderItemEntity> toEntity(OrderEntity order, List<OrderItemVO> items) {

        return items.stream().map(item -> toEntity(order, item)).collect(Collectors.toSet());

    }

    public OrderItemEntity toEntity(OrderEntity order, OrderItemVO vo ){
        OrderItemEntity entity = toEntity(vo);
        entity.setOrder(order);
        return entity;
    }

    @Override
    public OrderItemEntity toEntity(OrderItemVO vo){
        var entity = new OrderItemEntity();

        entity.setId(vo.getId());
        entity.setDisc(DiscConverter.getInstance().toEntity(vo.getDisc()));
        entity.setQtd(vo.getQtd());
        entity.setUnitPrice(formatTwoDecimalPlacesToDouble(vo.getUnitPrice()));
        entity.setTotal(formatTwoDecimalPlacesToDouble(vo.getTotal()));
        entity.setCashback(formatTwoDecimalPlacesToDouble(vo.getCashback()));
        entity.setCashbackTotal(formatTwoDecimalPlacesToDouble(vo.getCashbackTotal()));

        return entity;
    }

    public List<OrderItemVO> fromEntitytoVO(Set<OrderItemEntity> items) {

        return items.stream().map(INSTANCE::fromEntityToVO).collect(Collectors.toList());

    }

    @Override
    @Deprecated
    public List<OrderItemVO> toVO(List<OrderItemEntity> all) {
        throw new MethodNotImplementedException("Method not supported");
    }

    @Override
    @Deprecated
    public PageVO<OrderItemVO> toVO(Page<OrderItemEntity> page, Function<OrderItemEntity, OrderItemVO> function) {
        throw new MethodNotImplementedException("Method not supported");
    }

    @Override
    public OrderItemVO fromEntityToVO(OrderItemEntity entity){
        var vo = new OrderItemVO();

        vo.setId(entity.getId());
        vo.setDisc(DiscConverter.getInstance().fromEntityToVO(entity.getDisc()));
        vo.setQtd(entity.getQtd());
        vo.setUnitPrice(formatTwoDecimalPlacesToDouble(entity.getUnitPrice()));
        vo.setTotal(formatTwoDecimalPlacesToDouble(entity.getTotal()));
        vo.setCashback(formatTwoDecimalPlacesToDouble(entity.getCashback()));
        vo.setCashbackTotal(formatTwoDecimalPlacesToDouble(entity.getCashbackTotal()));

        return vo;
    }

}
