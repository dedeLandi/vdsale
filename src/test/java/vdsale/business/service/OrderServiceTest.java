package vdsale.business.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import vdsale.BasicUnitTest;
import vdsale.business.services.OrderService;
import vdsale.model.repositories.OrderRepository;
import vdsale.model.vos.order.OrderItemVO;
import vdsale.model.vos.order.OrderVO;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceTest extends BasicUnitTest {

    private OrderService orderService;

    private OrderRepository orderRepository;

    @Before
    public void before(){
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testCalculateWithoutItem(){


        OrderItemVO item = createBasicItem(5, 5, 0);

        orderService.calculateItem(item);

        Assert.assertEquals(25, item.getTotal(), 0.1);
        Assert.assertEquals(0, item.getCashback(), 0);
        Assert.assertEquals(0, item.getCashbackTotal(), 0);

    }

    @Test
    public void testCalculateItemCashback(){

        OrderItemVO item = createBasicItem(5, 5, 10);

        orderService.calculateItem(item);

        Assert.assertEquals(25, item.getTotal(), 0);
        Assert.assertEquals(2.5, item.getCashbackTotal(), 0);

    }

    @Test
    public void testCalculateOrder(){

        List<OrderItemVO> items = new ArrayList<>();

        items.add(createBasicItem(5, 5, 10));
        items.add(createBasicItem(10, 10, 5));

        OrderVO order = new OrderVO();//other values not important to this test
        order.setItems(items);

        orderService.calculateOrder(order);

        Assert.assertEquals(125, order.getTotal(), 0);
        Assert.assertEquals(7.5, order.getCashbackTotal(), 0);

    }

    @Test
    public void testCalculateWithoutOrder(){

        List<OrderItemVO> items = new ArrayList<>();

        items.add(createBasicItem(50, 50, 0));
        items.add(createBasicItem(100, 100, 0));

        OrderVO order = new OrderVO();//other values not important to this test
        order.setItems(items);

        orderService.calculateOrder(order);

        Assert.assertEquals(12500, order.getTotal(), 0);
        Assert.assertEquals(0, order.getCashbackTotal(), 0);

    }

    private OrderItemVO createBasicItem(double price, int qtd, double cashback) {
        OrderItemVO item = new OrderItemVO();//other values not important to this test
        item.setUnitPrice(price);
        item.setQtd(qtd);
        item.setCashback(cashback);
        return item;
    }

}
