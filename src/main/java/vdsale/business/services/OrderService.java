package vdsale.business.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vdsale.business.basicService.BasicCrudService;
import vdsale.exceptions.MethodNotImplementedException;
import vdsale.model.entities.OrderEntity;
import vdsale.model.repositories.OrderRepository;
import vdsale.model.vos.order.OrderConverter;
import vdsale.model.vos.order.OrderItemVO;
import vdsale.model.vos.order.OrderVO;
import vdsale.model.vos.page.PageVO;

import java.util.Date;

@Slf4j
@Service
public class OrderService extends BasicCrudService<OrderVO,
        OrderEntity,
        OrderConverter,
        OrderRepository> {

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository,OrderConverter.getInstance());
    }

    @Override
    @Deprecated
    public OrderVO update(OrderVO data) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    @Override
    protected String getEntitySimpleName() {
        return OrderEntity.class.getSimpleName();
    }

    @Override
    @Deprecated
    protected void updateData(OrderVO orderVO, OrderEntity orderEntity) {
        throw new MethodNotImplementedException("Method not supported.");
    }

    public void calculateItem(OrderItemVO orderItem) {

        double total = orderItem.getUnitPrice() * orderItem.getQtd();
        orderItem.setTotal(total);

        double cashbackTotal = total * (orderItem.getCashback() / 100);
        orderItem.setCashbackTotal(cashbackTotal);
    }

    public void calculateOrder(OrderVO order) {

        order.getItems().stream().filter(i -> i.getTotal() == 0).forEach(i -> calculateItem(i));

        double total = order.getItems().stream().mapToDouble(OrderItemVO::getTotal).sum();
        order.setTotal(total);

        double cashbackTotal = order.getItems().stream().mapToDouble(OrderItemVO::getCashbackTotal).sum();
        order.setCashbackTotal(cashbackTotal);
    }

    @Transactional
    public PageVO<OrderVO> getAll(Date dateinit, Date datefinal, Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName());
        Page<OrderEntity> all = repository.findAllByDate(dateinit, datefinal, pageRequest);
        return converter.toVO(all, converter::fromEntityToVO);
    }
}
