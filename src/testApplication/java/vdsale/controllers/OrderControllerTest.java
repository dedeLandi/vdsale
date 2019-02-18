package vdsale.controllers;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.exceptions.DefaultError;
import vdsale.model.vos.disc.DiscSimpleVO;
import vdsale.model.vos.order.OrderItemSimpleVO;
import vdsale.model.vos.order.OrderSimpleVO;
import vdsale.model.vos.order.OrderVO;
import vdsale.model.vos.page.PageVO;
import vdsale.model.vos.person.PersonVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqls/person.sql", "/sqls/category.sql", "/sqls/cashback.sql", "/sqls/disc.sql", "/sqls/order.sql"})
public class OrderControllerTest extends BasicApplicationTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void testGetOrderList(){
        ResponseEntity<PageVO<OrderVO>> response = restTemplate.exchange("/order",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<OrderVO>>() {});

        Assert.assertNotNull(response);

        PageVO<OrderVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(1));
        Assert.assertThat(data.getContents().get(0).getItems(), Matchers.hasSize(1));
        Assert.assertThat(data.getContents().get(0).getItems().get(0).getDisc().getSpotifyId(), Matchers.equalTo("2kiDkXNxuQME25DEUWiNkw"));

    }

    @Test
    public void testGetOrder(){
        int orderId = 1;
        OrderVO order = getOrderVOById(orderId);

        Assert.assertThat(order.getPerson().getId(), Matchers.equalTo(1));

        Assert.assertNotNull(order.getItems());
        Assert.assertThat(order.getItems().size(), Matchers.equalTo(1));
        Assert.assertTrue(order.getItems().stream().anyMatch(i -> i.getDisc().getSpotifyId().equals("2kiDkXNxuQME25DEUWiNkw")));
    }

    @Test
    public void testGetOrderNotFound(){
        testGetNotFound(99);

    }

    private void testGetNotFound(int orderId) {
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/order/"+orderId, HttpMethod.GET, null, HttpStatus.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testSaveOrderWithoutCashback(){
        OrderSimpleVO vo = createOrder(formatDate("01-03-2019"));
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<OrderVO> response = restTemplate.exchange("/order", HttpMethod.POST, request, OrderVO.class);

        Assert.assertNotNull(response);

        OrderVO responseData = response.getBody();

        Assert.assertNotNull(responseData);

        testOrderEntity(responseData.getId(), vo, 51, 0);

    }

    @Test
    public void testSaveOrder(){
        OrderSimpleVO vo = createOrder(formatDate("04-03-2019"));
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<OrderVO> response = restTemplate.exchange("/order", HttpMethod.POST, request, OrderVO.class);

        Assert.assertNotNull(response);

        OrderVO responseData = response.getBody();

        Assert.assertNotNull(responseData);

        testOrderEntity(responseData.getId(), vo, 51, 3.57);

    }

    public void testOrderEntity(int orderId, OrderSimpleVO savedData, double total, double cashbackTotal) {
        OrderVO order = getOrderVOById(orderId);

        Assert.assertThat(order.getPerson().getId(), Matchers.equalTo(savedData.getPerson().getId()));
        Assert.assertThat(sdf.format(order.getCreationDate()), Matchers.equalTo(sdf.format(savedData.getCreationDate())));
        Assert.assertThat(order.getTotal(), Matchers.equalTo(total));
        Assert.assertThat(order.getCashbackTotal(), Matchers.equalTo(cashbackTotal));

        Assert.assertNotNull(order.getItems());
        Assert.assertThat(order.getItems().size(), Matchers.equalTo(savedData.getItems().size()));
        order.getItems().stream().forEach(item ->
            Assert.assertTrue(savedData.getItems().stream().anyMatch(i -> i.getDisc().getSpotifyId().equals(item.getDisc().getSpotifyId())))
        );
    }

    private OrderVO getOrderVOById(int orderId) {
        ResponseEntity<OrderVO> response = restTemplate.exchange("/order/" + orderId, HttpMethod.GET, null, OrderVO.class);

        Assert.assertNotNull(response);

        var order = response.getBody();

        Assert.assertNotNull(order);
        return order;
    }

    @Test
    public void testDeleteOrder(){
        ResponseEntity<Integer> response = restTemplate.exchange("/order/"+1, HttpMethod.DELETE, null, Integer.class);

        Assert.assertNotNull(response);

        Integer data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(1, data.intValue());

        testGetNotFound(1);

    }

    @Test
    public void testDeleteOrderNotFound(){
        ResponseEntity<DefaultError> response = restTemplate.exchange("/order/99", HttpMethod.DELETE, null, DefaultError.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));

        DefaultError error = response.getBody();

        Assert.assertNotNull(error);

        Assert.assertEquals("Dados não encontrados", error.getError());

    }

    private OrderSimpleVO createOrder(Date date) {
        OrderSimpleVO vo = new OrderSimpleVO();

        PersonVO person = new PersonVO();
        person.setId(1);
        vo.setPerson(person);

        vo.setCreationDate(date);

        List<OrderItemSimpleVO> items = new ArrayList<>();
        items.add(createOrderItem());
        vo.setItems(items);

        return vo;
    }

    private OrderItemSimpleVO createOrderItem() {
        OrderItemSimpleVO vo = new OrderItemSimpleVO();

        DiscSimpleVO disc = new DiscSimpleVO();
        disc.setSpotifyId("2kiDkXNxuQME25DEUWiNkw");
        vo.setDisc(disc);

        vo.setQtd(2);

        return vo;
    }

    private Date formatDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

}
