package vdsale.controllers;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import vdsale.BasicApplicationTest;
import vdsale.model.vos.disc.ArtistVO;
import vdsale.model.vos.page.PageVO;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.of;

@Sql(value = "/sqls/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sqls/artist.sql")
public class ArtistControllerTest extends BasicApplicationTest {

    @Test
    public void testGetArtistList(){
        ResponseEntity<PageVO<ArtistVO>> response = restTemplate.exchange("/artist",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageVO<ArtistVO>>() {});

        Assert.assertNotNull(response);

        PageVO<ArtistVO> data = response.getBody();

        Assert.assertThat(data.getContents(), Matchers.hasSize(3));

    }

    @ParameterizedTest()
    @MethodSource("getArtistParameter")
    @DisplayName("Testing get Artist")
    public void testGetCashback(int id, String expected){
        ResponseEntity<ArtistVO> response = restTemplate.exchange("/artist/"+id, HttpMethod.GET, null, ArtistVO.class);

        Assert.assertNotNull(response);

        ArtistVO data = response.getBody();

        Assert.assertNotNull(data);

        Assert.assertEquals(expected, data.getName());

    }

    @Test
    public void testGetCashbackNotFound(){
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/artist/99", HttpMethod.GET, null, HttpStatus.class);

        Assert.assertNotNull(response);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    private static Stream<Arguments> getArtistParameter(){
        return Stream.of(
                of(1, "Raimundos"),
                of(2, "U2"),
                of(3, "Red Hot Chilli Pepper")
        );
    }

}
