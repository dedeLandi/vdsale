package vdsale.business.facades;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vdsale.business.services.CashbackService;
import vdsale.business.services.OrderService;
import vdsale.business.services.PersonService;
import vdsale.model.vos.disc.DiscVO;
import vdsale.model.vos.order.OrderItemSimpleVO;
import vdsale.model.vos.order.OrderItemVO;
import vdsale.model.vos.order.OrderSimpleVO;
import vdsale.model.vos.order.OrderVO;
import vdsale.model.vos.page.PageVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderFacade {

    private final DiscFacade discFacade;
    private final PersonService personService;
    private final OrderService orderService;
    private final CashbackService cashbackService;

    @Autowired
    public OrderFacade(DiscFacade discFacade, PersonService personService, OrderService orderService, CashbackService cashbackService) {
        this.discFacade = discFacade;
        this.personService = personService;
        this.orderService = orderService;
        this.cashbackService = cashbackService;
    }

    public PageVO<OrderVO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of orders");
        return orderService.getAll(pageRequest);
    }

    public PageVO<OrderVO> getAll(Date dateinit, Date datefinal, Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of orders by date");
        return orderService.getAll(dateinit, datefinal, pageRequest);
    }

    public OrderVO get(int id) {
        log.info("M=get, orderId={}, message=Get a specific order", id);
        return orderService.get(id);
    }

    public OrderVO save(OrderSimpleVO order) {
        log.info("M=save, orderId={}, message=Get a specific order", order);
        OrderVO orderToSave = convertSimpleOrderToComplexOrder(order);
        return orderService.save(orderToSave);
    }

    private OrderVO convertSimpleOrderToComplexOrder(OrderSimpleVO simple) {
        OrderVO complex = new OrderVO();

        Date creationDate = simple.getCreationDate() == null ? new Date() : simple.getCreationDate();
        complex.setCreationDate(creationDate);

        complex.setPerson(personService.findPersonByIdOrName(simple.getPerson().getId(), simple.getPerson().getName()));
        complex.setItems(convertSimpleOrderItemToComplexOrderItem(simple.getItems(), creationDate));

        orderService.calculateOrder(complex);

        return complex;
    }

    private List<OrderItemVO> convertSimpleOrderItemToComplexOrderItem(List<OrderItemSimpleVO> items, Date creationDate) {
        return items.stream().map(item -> convertSimpleOrderItemToComplexOrderItem(item, creationDate)).collect(Collectors.toList());
    }

    private OrderItemVO convertSimpleOrderItemToComplexOrderItem(OrderItemSimpleVO simple, Date creationDate) {
        var complex = new OrderItemVO();

        DiscVO disc = discFacade.getDisc(simple.getDisc());
        complex.setDisc(disc);
        complex.setUnitPrice(disc.getPrice());

        complex.setQtd(simple.getQtd());
        complex.setCashback(cashbackService.getCashbackByCategoryAndDate(disc.getCategory(), creationDate));

        orderService.calculateItem(complex);

        return complex;
    }

    public void delete(int id) {
        log.info("M=delete, orderId={}, message=Delete a specific order", id);
        orderService.delete(id);
    }

}
