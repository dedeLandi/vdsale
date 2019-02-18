package vdsale.controllers;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.exceptions.DefaultError;
import vdsale.model.enums.Weekday;
import vdsale.model.repositories.CashbackRepository;
import vdsale.model.vos.cashback.CashbackVO;
import vdsale.model.vos.category.CategoryVO;
import vdsale.model.vos.page.PageVO;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.of;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqls/category.sql", "/sqls/cashback.sql"})
public class CashbackControllerTest extends BasicApplicationTest {

    @Autowired
    private CashbackRepository cashbackRepository;

    @Test
    public void testGetCashbackList(){
        ResponseEntity<PageVO<CashbackVO>> response = restTemplate.exchange("/cashback",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<CashbackVO>>() {});

        Assert.assertNotNull(response);

        PageVO<CashbackVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(7));

    }

    @ParameterizedTest()
    @MethodSource("getCashbackParameter")
    @DisplayName("Testing get Cashback")
    public void testGetCashback(int id, double expected){
        ResponseEntity<CashbackVO> response = restTemplate.exchange("/cashback/"+id, HttpMethod.GET, null, CashbackVO.class);

        Assert.assertNotNull(response);

        CashbackVO data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(expected, data.getCashback(), 0);

    }

    @Test
    public void testGetCashbackNotFound(){
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/cashback/99", HttpMethod.GET, null, HttpStatus.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    public void testSaveCashback(){

        Integer id = saveCashback();

        final var entity = cashbackRepository.findById(id);

        Assert.assertNotNull(entity);

    }

    @Test
    public void testDeletePerson(){
        int id = saveCashback();
        ResponseEntity<Integer> response = restTemplate.exchange("/cashback/"+id, HttpMethod.DELETE, null, Integer.class);

        Assert.assertNotNull(response);

        Integer data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(id, data.intValue());

    }

    @Test
    public void testDeletePersonNotFound(){
        ResponseEntity<DefaultError> response = restTemplate.exchange("/cashback/99", HttpMethod.DELETE, null, DefaultError.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        DefaultError error = response.getBody();

        Assert.assertNotNull(error);

        Assert.assertEquals("Dados não encontrados", error.getError());

    }

    private int saveCashback() {
        CashbackVO vo = createCashback();
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<CashbackVO> response = restTemplate.exchange("/cashback", HttpMethod.POST, request, CashbackVO.class);

        Assert.assertNotNull(response);

        CashbackVO responseData = response.getBody();

        Assert.assertNotNull(responseData);

        return responseData.getId();
    }

    private CashbackVO createCashback() {
        CashbackVO cashbackVO = new CashbackVO();
        cashbackVO.setCashback(50);
        cashbackVO.setWeekday(Weekday.FRIDAY);

        CategoryVO category = new CategoryVO();
        category.setId(1);
        category.setSpotifyId("pop");
        category.setName("Pop");

        cashbackVO.setCategory(category);

        return cashbackVO;
    }

    private static Stream<Arguments> getCashbackParameter(){
        return Stream.of(
                of(1, 25),
                of(3, 10),
                of(5, 13),
                of(7, 40)
        );
    }

}
