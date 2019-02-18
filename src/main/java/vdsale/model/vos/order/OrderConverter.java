package vdsale.model.vos.order;

import org.springframework.data.domain.Page;
import vdsale.model.entities.OrderEntity;
import vdsale.model.vos.IBasicConverter;
import vdsale.model.vos.page.PageConverter;
import vdsale.model.vos.page.PageVO;
import vdsale.model.vos.person.PersonConverter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static vdsale.utils.DecimalUtils.formatTwoDecimalPlacesToDouble;

public class OrderConverter implements IBasicConverter<OrderEntity, OrderVO> {

    private static OrderConverter INSTANCE;

    private OrderConverter() {
    }

    public static OrderConverter getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new OrderConverter();
        }
        return INSTANCE;
    }


    @Override
    public OrderEntity toEntity(OrderVO vo) {
        var entity = new OrderEntity();

        entity.setId(vo.getId());
        entity.setPerson(new PersonConverter().toEntity(vo.getPerson()));
        entity.setItems(OrderItemConverter.getInstance().toEntity(entity, vo.getItems()));
        entity.setTotal(formatTwoDecimalPlacesToDouble(vo.getTotal()));
        entity.setCashbackTotal(formatTwoDecimalPlacesToDouble(vo.getCashbackTotal()));
        entity.setCreationDate(vo.getCreationDate());

        return entity;
    }

    @Override
    public OrderVO fromEntityToVO(OrderEntity entity) {
        var vo = new OrderVO();

        vo.setId(entity.getId());
        vo.setPerson(new PersonConverter().fromEntityToVO(entity.getPerson()));
        vo.setItems(OrderItemConverter.getInstance().fromEntitytoVO(entity.getItems()));
        vo.setTotal(formatTwoDecimalPlacesToDouble(entity.getTotal()));
        vo.setCashbackTotal(formatTwoDecimalPlacesToDouble(entity.getCashbackTotal()));
        vo.setCreationDate(entity.getCreationDate());

        return vo;
    }

    @Override
    public List<OrderVO> toVO(List<OrderEntity> all) {

        return Optional.ofNullable(all)
                .map(c -> c.stream()
                        .map(INSTANCE::fromEntityToVO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

    }

    @Override
    public PageVO<OrderVO> toVO(Page<OrderEntity> page, Function<OrderEntity, OrderVO> function) {
        return PageConverter.toVO(page, function);
    }

}
